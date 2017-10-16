package test;

public class A {
	public String a;
	public int b;
	public A(){
		System.out.println("A");
	}
	public A(String a){
		System.out.println(a);
	}
	public void fun1(){
		fun3();
		System.out.println("fun1___A");
	}
	
	public void fun2(){
		System.out.println("fun2___A");
	}
	
	public void fun3(){
		System.out.println("fun3___A");
	}
}
