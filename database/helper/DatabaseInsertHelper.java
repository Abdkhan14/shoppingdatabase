package com.b07.database.helper;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import com.b07.database.DatabaseInserter;
import com.b07.exceptions.DatabaseInsertException;
import com.b07.users.Roles;
import com.b07.users.Validator;



public class DatabaseInsertHelper extends DatabaseInserter {

  public static int insertRole(String name) {
    // TODO Implement this method as stated on the assignment sheet
    // hint: You should be using these three lines in your final code
    Connection connection = DatabaseDriverHelper.connectOrCreateDataBase();
    try {
      int count = 0;
      for (Roles r : Roles.values()) {
        if (r.name() == name) {
          count = count + 1;
        }
      }
      if (count == 0) {
        throw new DatabaseInsertException();
      } else {
        if (Validator.isContainsRole(name)) {
          return getRoleId(name);
        }
        int roleId = DatabaseInserter.insertRole(name, connection);
        connection.close();
        return roleId;
      }
    } catch (DatabaseInsertException e) {
      System.out.println("Error: RuntimeError due to Invalid entry");
      System.out
          .println("Please put a valid name as parameter call method;returned default id value -1");
    } catch (SQLException e) {
      System.out.println("Failed to close connection");
    }
    try {
      connection.close();
    } catch (SQLException e) {
      System.out.println("Failed to close connection");
      return -1;
    }
    return -1;
  }

  public static int getRoleId(String name) {
    List<String> s = new ArrayList<>();
    s.add("ADMIN");
    s.add("EMPLOYEE");
    s.add("CUSTOMER");
    if (name.equals("ADMIN")) {
      return 1;
    } else if (name.equals("CUSTOMER")) {
      return 3;
    } else if (name.equals("EMPLOYEE")) {
      return 2;
    }
    return 0;
  }

  public static int insertNewUser(String name, int age, String address, String password) {
    // TODO Implement this method as stated on the assignment sheet
    // hint: You should be using these three lines in your final code
    Connection connection = DatabaseDriverHelper.connectOrCreateDataBase();
    try {
      if (name != null && name != "" && !(age <= 0) && address != null && address.length() <= 100
          && address != "" && password != null && password != "") {
        int userId = DatabaseInserter.insertNewUser(name, age, address, password, connection);
        connection.close();
        return userId;
      } else {
        throw new DatabaseInsertException();
      }
    } catch (DatabaseInsertException e) {
      System.out.println("Error: RuntimeError due to Invalid entry");
      System.out
          .println("Please put a valid name as parameter call method;returned default id value -1");
    } catch (SQLException e) {
      System.out.println("Failed to CLOSE connect, assigned -1");
    }
    try {
      connection.close();
    } catch (SQLException e) {
      System.out.println("Failed to CLOSE connect, assigned -1");
      return -1;
    }
    return -1;
  }

  public static int insertUserRole(int userId, int roleId) {
    // TODO Implement this method as stated on the assignment sheet
    // hint: You should be using these three lines in your final code
    Connection connection = DatabaseDriverHelper.connectOrCreateDataBase();
    try {
      // Validator.Validate_roleId(roleId);
      // Validator.Validate_userId(userId);
      int userRoleId = DatabaseInserter.insertUserRole(userId, roleId, connection);
      connection.close();
      return userRoleId;
    } catch (DatabaseInsertException e) {
      System.out.println("Failed to insert new user,check input values, assigned -1");
      System.out.println(
          "Please input valid roleId and userId or type exit to abort insertion, assigned -1");
    } catch (SQLException e) {
      System.out.println("Failed to CLOSE connect, assigned -1");
      System.out.println("Type exit to abort insertion, assigned -1");
    }
    try {
      connection.close();
    } catch (SQLException e) {
      System.err.println("Failed to CLOSE connect, assigned -1");
      return -1;
    }
    return -1;
  }

  public static int insertItem(String name, BigDecimal price) {
    // TODO Implement this method as stated on the assignment sheet
    // hint: You should be using these three lines in your final code
    Connection connection = DatabaseDriverHelper.connectOrCreateDataBase();
    try {
      String s = String.valueOf(price);
      String[] sp = s.split("\\.");
      if (name != null && name.length() <= 64 && price.doubleValue() > 0 && sp[1].length() == 2) {
        int itemId = DatabaseInserter.insertItem(name, price, connection);
        connection.close();
        return itemId;
      }
      System.out.println("Please enter a valid nameor price or enter exit to abort insertion");
    } catch (DatabaseInsertException e) {
      System.err.println("Failed to insert new user,check input values, assigned -1");
    } catch (SQLException e) {
      System.err.println("Failed to CLOSE connect, assigned -1");
    }
    try {
      connection.close();
    } catch (SQLException e) {
      System.err.println("Failed to CLOSE connect, assigned -1");
      return -1;
    }
    return -1;
  }

  public static int insertInventory(int itemId, int quantity) {
    // TODO Implement this method as stated on the assignment sheet
    // hint: You should be using these three lines in your final code
    Connection connection = DatabaseDriverHelper.connectOrCreateDataBase();
    try {
      Validator.Validate_itemId(itemId);
      Validator.Validate_quantity(quantity);
      int inventoryId = DatabaseInserter.insertInventory(itemId, quantity, connection);
      connection.close();
      return inventoryId;
    } catch (DatabaseInsertException e) {
      System.out.println("Failed to insert new user,check input values, assigned -1");
      System.out.println(
          "Please input valid itemId and quantity or type exit to abort insertion, assigned -1");
    } catch (SQLException e) {
      System.out.println("Failed to CLOSE connect, assigned -1");
      System.out.println("Type exit to abort insertion");
    }
    try {
      connection.close();
    } catch (SQLException e) {
      System.err.println("Failed to CLOSE connect, assigned -1");
    }
    return -1;
  }

  public static int insertSale(int userId, BigDecimal totalPrice) {
    // TODO Implement this method as stated on the assignment sheet
    // hint: You should be using these three lines in your final code
    Connection connection = DatabaseDriverHelper.connectOrCreateDataBase();
    try {
      Validator.Validate_userId(userId);
      int saleId = DatabaseInserter.insertSale(userId, totalPrice, connection);
      connection.close();
      return saleId;
    } catch (DatabaseInsertException e) {
      System.out.println("Invalid entry for totalPrice and userId, assigned default -1");
      System.out
          .println("Please enter valid total price and userId or type exit to abort insertion");
    } catch (SQLException e) {
      System.out.println("Failed to close connection, assigned default value -1");
    } catch (NullPointerException e) {
      System.out.println("Saleslog for itemizedSales doesnot exit thus can't validate totalprice");
    }
    try {
      connection.close();
    } catch (SQLException e) {
      System.out.println("Failed to close connection, assigned default value -1");
      return -1;
    }
    return -1;
  }

  // RETURNS THE ACCOUNT ID NUMBER OF GIVEN CUSTOMER
  public static int insertAccount(int customerId) {
    try {
      if (DatabaseSelectHelper.getUserDetails(customerId).equals(null)) {
        throw new DatabaseInsertException();
      }
      Connection connection = DatabaseDriverHelper.connectOrCreateDataBase();
      int accountId = DatabaseInserter.insertAccount(customerId, connection);
      connection.close();
      return accountId;
    } catch (DatabaseInsertException e) {
      System.out.println("Not valid userId put in to make account so assigned default 0");
      return 0;
    } catch (SQLException e) {
      System.out.println("FAILED TO CLOSE CONNECTION SO ASSIGNED 0");
      return 0;
    }
  }

  // RETURNS THE RECORD OF THE SHOPPING CART SAVED FOR LATER USE
  public static int insertAccountLine(int accountId, int itemId, int quantity) {
    try {
      if (!(accountId > 0) && !(itemId > 0) && !(quantity > 0)) {
        throw new DatabaseInsertException();
      }
      Connection connection = DatabaseDriverHelper.connectOrCreateDataBase();
      int accountRecordId =
          DatabaseInserter.insertAccountLine(accountId, itemId, quantity, connection);
      connection.close();
      return accountRecordId;
    } catch (DatabaseInsertException e) {
      System.out.println("NOT VALID INPUTS PUT IN TO MAKE ACCOUNTLINE SO ASSIGNED DEFAULT 0");
      return 0;
    } catch (SQLException e) {
      System.out.println("FAILED TO CLOSE CONNECTION SO ASSIGNED 0");
      return 0;
    }
  }

  public static int insertItemizedSale(int saleId, int itemId, int quantity) {
    // TODO Implement this method as stated on the assignment sheet
    // hint: You should be using these three lines in your final code
    Connection connection = DatabaseDriverHelper.connectOrCreateDataBase();
    try {
      Validator.Validate_saleid(saleId);
      Validator.Validate_itemId(itemId);
      Validator.Validate_quantity(quantity);
      int itemizedId = DatabaseInserter.insertItemizedSale(saleId, itemId, quantity, connection);
      connection.close();
      return itemizedId;
    } catch (DatabaseInsertException e) {
      System.out
          .println("Please enter valid total price and userId or type exit to abort insertion");
      System.out.println(
          "Please enter valid total price and userId to retry or type exit to abort insertion");
    } catch (SQLException e) {
      System.out.println("Failed to close connection, assigned default value -1");
      System.out.println("Type exit to leave insertion");
    }
    try {
      connection.close();
    } catch (SQLException e) {
      System.out.println("Failed to close connection, assigned default value -1");
    }
    return -1;
  }

}
