package com.b07.users;

public class Customer extends User {

  public Customer(int id, String name, int age, String address) {
    this.address = address;
    this.age = age;
    this.name = name;
    this.id = id;
  }

  public Customer(int id, String name, int age, String address, boolean authenticated) {
    this.address = address;
    this.age = age;
    this.name = name;
    this.id = id;
    this.authenticated = authenticated;
  }
}
