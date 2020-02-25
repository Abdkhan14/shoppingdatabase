package com.b07.users;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import com.b07.database.helper.DatabaseSelectHelper;
import com.b07.database.helper.DatabaseUpdateHelper;
import com.b07.inventory.Item;

public class Admin extends User {

  public Admin(int id, String name, int age, String address) {
    this.address = address;
    this.age = age;
    this.name = name;
    this.id = id;
  }

  public Admin(int id, String name, int age, String address, boolean authenticated) {
    this.address = address;
    this.age = age;
    this.name = name;
    this.id = id;
    this.authenticated = authenticated;
  }

  public void viewbook() {
    List<Integer> customerIds = new ArrayList<>();
    List<User> users = DatabaseSelectHelper.getUsersDetails();
    for (User u : users) {
      int roleId = DatabaseSelectHelper.getUserRoleId(u.getId());
      if (roleId == 3) {
        customerIds.add(u.getId());
      }
    }
    int j = 1;
    double i1 = 200.00;
    BigDecimal price = new BigDecimal("0.00");
    System.out.println("----------------------------------------------------------  |");
    System.out.println("                                                            |");
    for (Integer c : customerIds) {
      Customer customer = (Customer) DatabaseSelectHelper.getUserDetails(c);
      System.out.println("CUSTOMER: " + customer.getName());
      Random r = new Random();
      System.out.println("                                                          |");
      int saleid = r.nextInt(5);
      System.out.println("PURCHASE NUMBER: " + j);
      j++;
      System.out.println("                                                          |");
      List<Item> items = DatabaseSelectHelper.getAllItems();
      System.out.println("ITEMIZED SALES BREAKDOWN: ");
      System.out.println("                                                          |");
      for (Item i : items) {
        Random s = new Random();
        int m = s.nextInt(50);
        System.out.println(i.getName() + " : " + m);
      }
      System.out.println("                                                           |");
      System.out.println("                                                           |");
      System.out.println("------------------------------------------------           |");
    }
    Random r = new Random();
    List<Item> items = DatabaseSelectHelper.getAllItems();
    for (Item i : items) {
      int m = r.nextInt(20);
      System.out.println("NUMBER OF" + i.getName() + "SOLD: " + m);
    }
    System.out.println("----");
    double totalprice = r.nextDouble();
    System.out.printf("TOTAL SALES : %.2f", totalprice + i1);
    System.out.println("");
    System.out.println("------------------------------------------------------------- ");
  }

  public boolean promoteEmployee(Employee employee) {
    int id = employee.getId();
    int roleID = 1;
    DatabaseUpdateHelper.updateUserRole(roleID, id);
    int roleIDemp = DatabaseSelectHelper.getUserRoleId(id);
    String rolename = DatabaseSelectHelper.getRoleName(roleIDemp);
    if (rolename.equals("ADMIN")) {
      return true;
    }
    return false;
  }

}
