package com.b07.users;

import com.b07.database.helper.DatabaseSelectHelper;
import com.b07.security.PasswordHelpers;


public abstract class User {
  protected int id;
  protected String name;
  protected String address;
  protected int age;
  protected int roleId;
  protected boolean authenticated;

  public int getId() {
    return this.id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String getName() {
    return this.name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public int getAge() {
    return this.age;
  }

  public void setAge(int age) {
    this.age = age;
  }

  public String getAddress() {
    return this.address;
  }

  public void setAddress(String address) {
    this.address = address;
  }

  public int getRoleId() {
    return this.roleId;
  }

  public final boolean authenticate(String password) {
    try {
      String databasePassword = DatabaseSelectHelper.getPassword(this.id);
      this.authenticated = PasswordHelpers.comparePassword(databasePassword, password);
      return this.authenticated;
    } catch (NullPointerException e) {
      System.out.println("null pointer exception");
      return false;
    }

  }

}
