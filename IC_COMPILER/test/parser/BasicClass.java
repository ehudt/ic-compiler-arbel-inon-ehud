class A {
	int a, b;
	int A(int a, int b){
		this.a = a;
		this.b = b;
	}

	int getA(){
		return a;
	}

	int getB(){
		return b;
	}

	int getThis(){
		return this;
	}
}


class Prog {
	static void main(string args[]){
		A a = new A(2, 3);
		print(a.getA());
		print(a.getB());
	}
}