package com.b07.database.helper;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.SQLException;
import com.b07.database.DatabaseUpdater;

public class DatabaseUpdateHelper extends DatabaseUpdater {

  public static boolean updateRoleName(String name, int id) {
    // TODO Implement this method as stated on the assignment sheet (Strawberry)
    // hint: You should be using these three lines in your final code
    Connection connection = DatabaseDriverHelper.connectOrCreateDataBase();
    try {
      boolean complete = DatabaseUpdater.updateRoleName(name, id, connection);
      connection.close();
      return complete;
    } catch (SQLException e) {
      System.out.println("Failed to close connection, set false by default");
      return false;
    }
  }

  public static boolean updateUserName(String name, int userId) {
    Connection connection = DatabaseDriverHelper.connectOrCreateDataBase();
    try {
      boolean complete = DatabaseUpdater.updateUserName(name, userId, connection);
      connection.close();
      return complete;
    } catch (SQLException e) {
      System.out.println("Failed to close connection, set false by default");
      return false;
    }
  }

  public static boolean updateUserAge(int age, int userId) {
    Connection connection = DatabaseDriverHelper.connectOrCreateDataBase();
    try {
      boolean complete = DatabaseUpdater.updateUserAge(age, userId, connection);
      connection.close();
      return complete;
    } catch (SQLException e) {
      System.out.println("Failed to close connection, set false by default");
      return false;
    }
  }

  public static boolean updateUserAddress(String address, int userId) {
    Connection connection = DatabaseDriverHelper.connectOrCreateDataBase();
    try {
      boolean complete = DatabaseUpdater.updateUserAddress(address, userId, connection);
      connection.close();
      return complete;
    } catch (SQLException e) {
      System.out.println("Failed to close connection, set false by default");
      return false;
    }
  }

  public static boolean updateUserRole(int roleId, int userId) {
    try {
      Connection connection = DatabaseDriverHelper.connectOrCreateDataBase();
      boolean complete = DatabaseUpdater.updateUserRole(roleId, userId, connection);
      connection.close();
      return complete;
    } catch (SQLException e) {
      System.out.println("Failed to close connection, set false by default");
      return false;
    }
  }

  public static boolean updateItemName(String name, int itemId) {
    try {
      Connection connection = DatabaseDriverHelper.connectOrCreateDataBase();
      boolean complete = DatabaseUpdater.updateItemName(name, itemId, connection);
      connection.close();
      return complete;
    } catch (SQLException e) {
      System.out.println("Failed to close connection, set false by default");
      return false;
    }
  }

  public static boolean updateItemPrice(BigDecimal price, int itemId) {
    try {
      Connection connection = DatabaseDriverHelper.connectOrCreateDataBase();
      boolean complete = DatabaseUpdater.updateItemPrice(price, itemId, connection);
      connection.close();
      return complete;
    } catch (SQLException e) {
      System.out.println("Failed to close connection, set false by default");
      return false;
    }
  }

  public static boolean updateInventoryQuantity(int quantity, int itemId) {
    Connection connection = DatabaseDriverHelper.connectOrCreateDataBase();
    try {
      boolean complete = DatabaseUpdater.updateInventoryQuantity(quantity, itemId, connection);
      connection.close();
      return complete;
    } catch (SQLException e) {
      System.out.println("Failed to close connection, set false by default");
      return false;
    }
  }

}
