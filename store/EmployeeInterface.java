package com.b07.store;

import java.sql.SQLException;
import com.b07.database.helper.DatabaseInsertHelper;
import com.b07.database.helper.DatabaseSelectHelper;
import com.b07.exceptions.DatabaseInsertException;
import com.b07.inventory.Inventory;
import com.b07.inventory.Item;
import com.b07.inventory.IventoryImplement;
import com.b07.users.Employee;
import com.b07.users.Validator;

public class EmployeeInterface {
  private Employee currentEmployee;
  private Inventory inventory = new IventoryImplement();

  public EmployeeInterface(Employee employee, Inventory inventory) {
    if (employee.authenticate(DatabaseSelectHelper.getPassword(employee.getId()))) {
      this.currentEmployee = employee;
    }
    this.inventory = inventory;
  }

  public EmployeeInterface(Inventory inventory) {
    this.inventory = inventory;
  }

  public void seCurrenttEmployee(Employee employee) {
    if (employee.authenticate(DatabaseSelectHelper.getPassword(employee.getId()))) {
      this.currentEmployee = employee;
    }
  }

  public boolean hasCurrentEmployee() {
    if (!this.currentEmployee.equals(null)) {
      return true;
    }
    return false;
  }

  public boolean restockInventory(Item item, int quantity) {
    this.inventory.updateMap(item, quantity);
    if (this.inventory.getItemMap().get(item) == quantity) {
      return true;
    }
    return false;
  }

  public int createCustomer(String name, int age, String address, String password) {

    int userId = -1;
    int roleId = -1;
    try {
      Validator.Validate_insertuser(name, age, address, password);
      userId = DatabaseInsertHelper.insertNewUser(name, age, address, password);
      roleId = DatabaseSelectHelper.getUserRoleId(userId);
      DatabaseInsertHelper.insertUserRole(userId, roleId);
      return userId;
    } catch (DatabaseInsertException e) {
      System.out
          .println("Failed to make new Customer as the inputs are invalid, assigned -1 as default");
    } catch (SQLException e) {
      System.out.println("Failed to close connection when inserting User assigned -1 as default");
      return userId;
    }
    return userId;

  }

  public int createEmployee(String name, int age, String address, String password) {
    int userId = -1;
    int roleId = -1;
    try {
      Validator.Validate_insertuser(name, age, address, password);
      userId = DatabaseInsertHelper.insertNewUser(name, age, address, password);
      roleId = DatabaseSelectHelper.getUserRoleId(userId);
      DatabaseInsertHelper.insertUserRole(userId, roleId);
      return userId;
    } catch (DatabaseInsertException e) {
      System.out
          .println("Failed to make new Employee as the inputs are invalid, assigned -1 as default");
    } catch (SQLException e) {
      System.out.println("Failed to close connection when inserting User assigned -1 as default");
      return userId;
    }
    return userId;
  }

}
