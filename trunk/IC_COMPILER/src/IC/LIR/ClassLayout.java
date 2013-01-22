package IC.LIR;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import IC.SemanticError;
import IC.AST.*;

import IC.AST.ICClass;
import IC.Types.TypeTable;

public class ClassLayout {
	private ICClass classDecl;
	
	private Integer methodOffset = 0;
	private Map<Method, Integer> methodOffsetMap = new LinkedHashMap<Method, Integer>();
	private Map<String, Method> methodPointerMap = new LinkedHashMap<String, Method>();
	
	private Integer fieldOffset = 1; // field 0 of class instance is DVPTR
	private Map<Field, Integer> fieldOffsetMap = new LinkedHashMap<Field, Integer>();
	private Map<String, Field> fieldPointerMap = new LinkedHashMap<String, Field>();
	
	public static ClassLayout NewClassLayout(ICClass classDecl) {
		if (classDecl == null) return new ClassLayout();
		if (!classDecl.hasSuperClass()) {
			return new ClassLayout(classDecl);
		} else {
			ICClass parentClass = null;
			try {
				parentClass = TypeTable.getUserTypeByName(classDecl.getSuperClassName());
			} catch (SemanticError e) { /* This is never reached, correct 
							inheritance was enforced in semantic analysis phase */	
			}
			ClassLayout classLayout = NewClassLayout(parentClass);
			/* add son's field and methods */
			for (Method method : classDecl.getMethods()) {
				if (classLayout.hasMethod(method)) {
					/* this method overrides a parent method */
					classLayout.overrideMethod(method);
				} else {
					classLayout.insertMethod(method);
				}
			}
			for (Field field : classDecl.getFields()) {
				classLayout.insertField(field);
			}
			classLayout.setClassDecl(classDecl);
			return classLayout;
		}
	}
	
	private ClassLayout() { }
	
	private ClassLayout(ICClass classDecl) {
		setClassDecl(classDecl);
		
		for (Method method : classDecl.getMethods()) {
			insertMethod(method);
		}
		
		for (Field field : classDecl.getFields()) {
			insertField(field);
		}
	}
	
	public String getDispatchVector() {
		StringBuilder vector = new StringBuilder("_DV_" + classDecl.getName() + ": ");
		
		List<Method> methodList = new ArrayList<Method>(methodOffsetMap.keySet());
		Collections.sort(methodList, new Comparator<Method>() {
			@Override
			public int compare(Method method1, Method method2) {
				return methodOffsetMap.get(method1) -
						methodOffsetMap.get(method2);
			}
		});
		
		vector.append("[");
		for (Method method : methodList) {
			vector.append("_");
			vector.append(classDecl.getName());
			vector.append("_");
			vector.append(method.getName());
			vector.append(",");
		}
		vector.setCharAt(vector.length() - 1, ']');
		return vector.toString();
	}
	
	public int getObjectAllocSize() {
		return 4 * fieldOffset;
	}
	
	public Method getMethod(String name) {
		return methodPointerMap.get(name);
	}
	
	private void insertMethod(Method method) {
		methodOffsetMap.put(method, methodOffset++);
		methodPointerMap.put(method.getName(), method);
	}

	private void insertField(Field field) {
		fieldOffsetMap.put(field, fieldOffset++);
		fieldPointerMap.put(field.getName(), field);
	}
	
	private boolean hasMethod(Method method) {
		return methodPointerMap.containsKey(method.getName());
	}
	
	private boolean hasMethod(String methodName) {
		return methodPointerMap.containsKey(methodName);
	}
	
	public Integer getMethodOffset(String methodName) {
		return methodOffsetMap.get(methodPointerMap.get(methodName));
	}
	
	public Integer getFieldOffset(String fieldName) {
		return fieldOffsetMap.get(fieldPointerMap.get(fieldName));
	}
	
	private void overrideMethod(Method method) {
		Integer methodOffset = methodOffsetMap.remove(methodPointerMap.remove(method.getName()));
		methodOffsetMap.put(method, methodOffset);
		methodPointerMap.put(method.getName(), method);
	}
	
	public ICClass getClassDecl() {
		return classDecl;
	}

	private void setClassDecl(ICClass classDecl) {
		this.classDecl = classDecl;
	}
}
