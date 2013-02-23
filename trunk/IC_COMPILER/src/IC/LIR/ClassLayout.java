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

/**
 * Represents an IC class' structure (field offsets and DV)
 * This class calculates the offsets and DV of each IC class
 * according to its superclass.
 * @author ehud
 *
 */
public class ClassLayout {
	private ICClass classDecl;
	
	private Integer methodOffset = 0;
	private Map<Method, Integer> methodOffsetMap = new LinkedHashMap<Method, Integer>();
	private Map<String, Method> methodPointerMap = new LinkedHashMap<String, Method>();
	private Map<Method, String> methodImplementingClass = new LinkedHashMap<Method, String>();
	
	private Integer fieldOffset = 1; // field 0 of class instance is DVPTR
	private Map<Field, Integer> fieldOffsetMap = new LinkedHashMap<Field, Integer>();
	private Map<String, Field> fieldPointerMap = new LinkedHashMap<String, Field>();
	
	/**
	 * A factory for class layouts. This is needed because each class layout
	 * is dependent of its parent's class layout.
	 */
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
			/* set the class layout to the inheriting class */
			classLayout.setClassDecl(classDecl);
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
			return classLayout;
		}
	}
	
	private ClassLayout() { }
	
	private ClassLayout(ICClass classDecl) {
		setClassDecl(classDecl);
		
		for (Method method : classDecl.getMethods()) {
			methodImplementingClass.put(method, classDecl.getName());
			insertMethod(method);
		}
		
		for (Field field : classDecl.getFields()) {
			insertField(field);
		}
	}
	/**
	 * Get the LIR representation of the DV of the class represented
	 * by this instance of ClassLayout
	 * @return Disptach vector string representation in LIR
	 */
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
		for (int i = 0; i < methodList.size(); ++i) {
			Method method = methodList.get(i);
			vector.append("_");
			vector.append(methodImplementingClass.get(method));
			vector.append("_");
			vector.append(method.getName());
			if (i < methodList.size() - 1) {
				vector.append(",");
			}
		}
		vector.append("]");
		return vector.toString();
	}
	/**
	 * Get a class' required allocation size in bytes
	 * @return IC class allocation size
	 */
	public int getObjectAllocSize() {
		return 4 * fieldOffset;
	}
	/**
	 * 
	 * @param name
	 * @return method object by name
	 */
	public Method getMethod(String name) {
		return methodPointerMap.get(name);
	}
	
	private void insertMethod(Method method) {
		methodOffsetMap.put(method, methodOffset++);
		methodPointerMap.put(method.getName(), method);
		methodImplementingClass.put(method, classDecl.getName());
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
	
	/**
	 * Get the offset of a method by name
	 * @param methodName
	 * @return method offset in DV
	 */
	public Integer getMethodOffset(String methodName) {
		return methodOffsetMap.get(methodPointerMap.get(methodName));
	}
	
	/**
	 * Get a field's offset in the class layout
	 * @param fieldName
	 * @return field offset in class layout
	 */
	public Integer getFieldOffset(String fieldName) {
		return fieldOffsetMap.get(fieldPointerMap.get(fieldName));
	}
	
	private void overrideMethod(Method method) {
		Integer methodOffset = methodOffsetMap.remove(methodPointerMap.remove(method.getName()));
		methodOffsetMap.put(method, methodOffset);
		methodPointerMap.put(method.getName(), method);
		methodImplementingClass.put(method, classDecl.getName());
	}
	
	/**
	 * Get ICClass object from ClassLayout instance
	 * @return
	 */
	public ICClass getClassDecl() {
		return classDecl;
	}

	private void setClassDecl(ICClass classDecl) {
		this.classDecl = classDecl;
	}
}
