package com.b07.users;

public class Employee extends User {

  public Employee(int id, String name, int age, String address) {
    this.address = address;
    this.age = age;
    this.name = name;
    this.id = id;
  }

  public Employee(int id, String name, int age, String address, boolean authenticated) {
    this.address = address;
    this.age = age;
    this.name = name;
    this.id = id;
    this.authenticated = authenticated;
  }

}
