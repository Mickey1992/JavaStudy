package test;

public class Person {
	public String name;
	public int age;
	public String address;
	
	public Person(String name, int age, String address){
		this.name = name;
		this.age = age;
		this.address = address;
	}
	
	public String getName()
	{
		return name;
	}
	
	public String getAddress()
	{
		return address;
	}
	
	public int getAge()
	{
		return age;
	}
}
