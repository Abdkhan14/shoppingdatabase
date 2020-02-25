package com.b07.database.helper;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import com.b07.database.DatabaseSelector;
import com.b07.inventory.Inventory;
import com.b07.inventory.Item;
import com.b07.inventory.ItemImplement;
import com.b07.inventory.IventoryImplement;
import com.b07.store.Sale;
import com.b07.store.SaleImpl;
import com.b07.store.SalesLog;
import com.b07.store.SaleslogImpl;
import com.b07.users.Admin;
import com.b07.users.Customer;
import com.b07.users.Employee;
import com.b07.users.User;
import com.b07.users.UserImplabs;

/*
 * TODO: Complete the below methods to be able to get information out of the database. TODO: The
 * given code is there to aide you in building your methods. You don't have TODO: to keep the exact
 * code that is given (for example, DELETE the System.out.println()) TODO: and decide how to handle
 * the possible exceptions
 */
public class DatabaseSelectHelper extends DatabaseSelector {

  public static List<Integer> getRoleIds() {
    List<Integer> l = new ArrayList<>();
    try {
      Connection connection = DatabaseDriverHelper.connectOrCreateDataBase();
      ResultSet results = DatabaseSelector.getRoles(connection);
      List<Integer> ids = new ArrayList<>();
      while (results.next()) {
        ids.add(results.getInt("ID"));
      }
      results.close();
      connection.close();
      return ids;
    } catch (SQLException e) {
      System.out
          .println("Failed to close connection, so assigned default empty list in getRoleIds");
      return l;
    }
  }

  public static String getRoleName(int roleId) {
    Connection connection = DatabaseDriverHelper.connectOrCreateDataBase();
    try {
      String role = DatabaseSelector.getRole(roleId, connection);
      connection.close();
      return role;
    } catch (SQLException e) {
      System.out
          .println("Failed to close connection, so assigned default empty string in getRoleName");
      return "";
    }
  }

  public static int getUserRoleId(int userId) {
    try {
      Connection connection = DatabaseDriverHelper.connectOrCreateDataBase();
      int roleId = DatabaseSelector.getUserRole(userId, connection);
      connection.close();
      return roleId;
    } catch (SQLException e) {
      // System.out.println("Failed to close connection, so assigned default -1 getUserRoleId");
      return -1;
    }
  }

  public static List<Integer> getUsersByRole(int roleId) {
    List<Integer> l = new ArrayList<>();
    try {
      Connection connection = DatabaseDriverHelper.connectOrCreateDataBase();
      ResultSet results = DatabaseSelector.getUsersByRole(roleId, connection);
      List<Integer> userIds = new ArrayList<>();
      while (results.next()) {
        System.out.println(results.getInt("USERID"));
        userIds.add(results.getInt("USERID"));
      }
      results.close();
      connection.close();
      return userIds;
    } catch (SQLException e) {
      System.err.println("Failed to close connection, so assigned default empty list");
      return l;
    }
  }

  public static List<User> getUsersDetails() {
    List<User> l = new ArrayList<>();
    try {
      Connection connection = DatabaseDriverHelper.connectOrCreateDataBase();
      ResultSet results = DatabaseSelector.getUsersDetails(connection);
      List<User> users = new ArrayList<>();
      while (results.next()) {
        int r = getUserRoleId(results.getInt("ID"));
        if (r == 1) {
          User user1 = new Admin(results.getInt("ID"), results.getString("NAME"),
              results.getInt("AGE"), results.getString("ADDRESS"));
          users.add(user1);
        } else if (r == 2) {
          User user2 = new Employee(results.getInt("ID"), results.getString("NAME"),
              results.getInt("AGE"), results.getString("ADDRESS"));
          users.add(user2);
        } else if (r == 3) {
          User user3 = new Customer(results.getInt("ID"), results.getString("NAME"),
              results.getInt("AGE"), results.getString("ADDRESS"));
          users.add(user3);
        }
      }
      results.close();
      connection.close();
      return users;
    } catch (SQLException e) {
      System.err.println("Failed to close connection, so assigned default empty list");
      return l;
    }
  }

  public static HashMap<Integer, Integer> getAccountDetails(int accountId) {
    HashMap<Integer, Integer> h = new HashMap<>();
    try {
      Connection connection = DatabaseDriverHelper.connectOrCreateDataBase();
      ResultSet results = DatabaseSelector.getAccountDetails(accountId, connection);
      while (results.next()) {
        h.put(results.getInt("itemId"), results.getInt("quantity"));
      }
      results.close();
      connection.close();
      return h;
    } catch (SQLException e) {
      System.out
          .println("FAILED TO CLOSE CONNECTION SO ASSIGNED EMPTY HashMap IN GETACCOUNTSDETAILS");
      return h;
    }
  }

  public static List<Integer> getUserAccounts(int userId) {
    List<Integer> a = new ArrayList<>();
    try {
      Connection connection = DatabaseDriverHelper.connectOrCreateDataBase();
      ResultSet results = DatabaseSelector.getUserAccounts(userId, connection);
      while (results.next()) {
        a.add(results.getInt("id"));
      }
      results.close();
      connection.close();
      return a;
    } catch (SQLException e) {
      System.out.println("FAILED TO CLOSE CONNECTION SO ASSIGNED EMPTY HashMap IN GETUSERACCOUNTS");
      return a;
    }
  }

  public static User getUserDetails(int userId) {
    User u = new UserImplabs();
    try {
      Connection connection = DatabaseDriverHelper.connectOrCreateDataBase();
      ResultSet results = DatabaseSelector.getUserDetails(userId, connection);
      User user = null;
      while (results.next()) {
        String name = getRoleName(getUserRoleId(results.getInt("ID")));
        if (name.equals("ADMIN")) {
          user = new Admin(results.getInt("ID"), results.getString("NAME"), results.getInt("AGE"),
              results.getString("ADDRESS"));
        } else if (name.equals("EMPLOYEE")) {
          user = new Employee(results.getInt("ID"), results.getString("NAME"),
              results.getInt("AGE"), results.getString("ADDRESS"));
        } else if (name.equals("CUSTOMER")) {
          user = new Customer(results.getInt("ID"), results.getString("NAME"),
              results.getInt("AGE"), results.getString("ADDRESS"));
        }
      }
      results.close();
      connection.close();
      return user;
    } catch (SQLException e) {
      System.err
          .println("Failed to close connection,so assigned default User with empty information");
      return u;
    }
  }

  public static String getPassword(int userId) {
    try {
      Connection connection = DatabaseDriverHelper.connectOrCreateDataBase();
      String password = DatabaseSelector.getPassword(userId, connection);
      connection.close();
      return password;
    } catch (SQLException e) {
      System.err.println("Failed to close connection, so assigned default User with empty string");
      return "";
    }
  }

  public static List<Item> getAllItems() {
    List<Item> l = new ArrayList<>();
    try {
      Connection connection = DatabaseDriverHelper.connectOrCreateDataBase();
      ResultSet results = DatabaseSelector.getAllItems(connection);
      List<Item> items = new ArrayList<>();
      while (results.next()) {
        Item item = new ItemImplement(results.getInt("ID"), results.getString("NAME"),
            new BigDecimal(results.getString("PRICE")));
        items.add(item);
      }
      results.close();
      connection.close();
      return items;
    } catch (SQLException e) {
      System.err.println("Failed to close connection, so assigned default User with empty string");
      return l;
    }
  }

  public static Item getItem(int itemId) {
    Item it = new ItemImplement();
    try {
      Connection connection = DatabaseDriverHelper.connectOrCreateDataBase();
      ResultSet results = DatabaseSelector.getItem(itemId, connection);
      Item item = null;
      while (results.next()) {
        item = new ItemImplement(results.getInt("ID"), results.getString("NAME"),
            new BigDecimal(results.getString("PRICE")));
      }
      results.close();
      connection.close();
      return item;
    } catch (SQLException e) {

      System.err.println("Failed to close connection, so assigned default Item empty values");
      return it;
    }
  }

  public static Inventory getInventory() {
    Inventory inv = new IventoryImplement();
    try {
      Connection connection = DatabaseDriverHelper.connectOrCreateDataBase();
      ResultSet results = DatabaseSelector.getInventory(connection);
      while (results.next()) {
        HashMap<Item, Integer> itemmap = new HashMap<>();
        itemmap.put(getItem(results.getInt("ITEMID")), results.getInt("QUANTITY"));
        inv = new IventoryImplement(itemmap);
      }
      results.close();
      connection.close();
      return inv;
    } catch (SQLException e) {
      System.err.println("Failed to close connection, so assigned default empty Inventory");
      return inv;
    }
  }

  public static int getInventoryQuantity(int itemId) {
    try {
      Connection connection = DatabaseDriverHelper.connectOrCreateDataBase();
      int quantity = DatabaseSelector.getInventoryQuantity(itemId, connection);
      connection.close();
      return quantity;
    } catch (SQLException e) {
      System.err.println("Failed to close connection, so assigned default -1");
      return -1;
    }
  }

  public static SalesLog getSales() {
    SalesLog s = new SaleslogImpl();
    try {
      Connection connection = DatabaseDriverHelper.connectOrCreateDataBase();
      ResultSet results = DatabaseSelector.getSales(connection);
      while (results.next()) {
        s = new SaleslogImpl(results.getInt("ID"));
      }
      results.close();
      connection.close();
      return s;
    } catch (SQLException e) {
      System.err.println("Failed to close connection, so assigned empty SalesLog");
      return s;
    }
  }

  public static Sale getSaleById(int saleId) {
    Sale s = new SaleImpl();
    try {
      Connection connection = DatabaseDriverHelper.connectOrCreateDataBase();
      ResultSet results = DatabaseSelector.getSaleById(saleId, connection);
      Sale sale = null;
      while (results.next()) {
        sale = new SaleImpl(saleId, DatabaseSelectHelper.getUserDetails(results.getInt("USERID")),
            new BigDecimal(results.getString("TOTALPRICE")));
      }
      results.close();
      connection.close();
      return sale;
    } catch (SQLException e) {
      System.err.println("Failed to close connection, so assigned empty Sales object");
      return s;
    }
  }

  public static List<Sale> getSalesToUser(int userId) {
    List<Sale> s = new ArrayList<>();
    try {
      Connection connection = DatabaseDriverHelper.connectOrCreateDataBase();
      ResultSet results = DatabaseSelectHelper.getSalesToUser(userId, connection);
      List<Sale> sales = new ArrayList<>();
      Sale sale = null;
      while (results.next()) {
        sale = new SaleImpl(results.getInt("ID"),
            DatabaseSelectHelper.getUserDetails(results.getInt("USERID")),
            new BigDecimal(results.getString("TOTALPRICE")));
        sales.add(sale);
      }
      results.close();
      connection.close();
      return sales;
    } catch (SQLException e) {
      System.err.println("Failed to close connection, so assigned empty Sales list");
      return s;
    }
  }

  public static Sale getItemizedSaleById(int saleId) {
    Sale s = new SaleImpl();
    try {
      Connection connection = DatabaseDriverHelper.connectOrCreateDataBase();
      ResultSet results = DatabaseSelector.getItemizedSaleById(saleId, connection);
      HashMap<Item, Integer> saleItems = new HashMap<>();
      while (results.next()) {
        saleItems.put(DatabaseSelectHelper.getItem(results.getInt("ID")),
            results.getInt("QUANTITY"));
      }
      Sale saleimp = new SaleImpl(saleId, saleItems);
      results.close();
      connection.close();
      return saleimp;
    } catch (SQLException e) {
      System.err.println("Failed to close connection, so assigned empty Sale object");
      return s;
    }
  }

  public static SalesLog getItemizedSales() {
    SalesLog s = null;
    try {
      Connection connection = DatabaseDriverHelper.connectOrCreateDataBase();
      ResultSet results = DatabaseSelector.getItemizedSales(connection);
      while (results.next()) {
        s = new SaleslogImpl(results.getInt("SALEID"), results.getInt("ITEMID"),
            results.getInt("QUANTITY"));
      }
      results.close();
      connection.close();
      return s;
    } catch (SQLException e) {
      System.err.println("Failed to close connection, so assigned empty Saleslog object");
      return s;
    }
  }

}
