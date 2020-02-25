package com.b07.users;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import com.b07.database.helper.DatabaseInsertHelper;
import com.b07.database.helper.DatabaseSelectHelper;
import com.b07.exceptions.DatabaseInsertException;
import com.b07.exceptions.SalesIdplusItemnotUniqueException;
import com.b07.inventory.Item;
import com.b07.store.Sale;
import com.b07.store.SalesLog;

public class Validator {

  // VALIDATES TOTAL PRICE WITH GIVEN CONDITIONS
  public static void Validate_total_price(BigDecimal total)
      throws SQLException, DatabaseInsertException, SalesIdplusItemnotUniqueException {
    int Totalquantity = 0;
    SalesLog saleslog = DatabaseSelectHelper.getItemizedSales();
    if (saleslog.equals(null)) {
      throw new NullPointerException();
    } else {
      int currentsaleId = saleslog.getsaleId();
      Sale sale = DatabaseSelectHelper.getSaleById(currentsaleId);
      HashMap<Item, Integer> itemmap = sale.getItemMap();
      BigDecimal totalprice = new BigDecimal("0.00");
      for (Item item : itemmap.keySet()) {
        Totalquantity = itemmap.get(item);
        totalprice.add(item.getPrice().multiply(new BigDecimal(Totalquantity)));
      }
      if (Validate_saleuniqueness(currentsaleId, itemmap)) {
        if (!totalprice.equals(total)) {
          throw new DatabaseInsertException();
        }
      } else {
        throw new SalesIdplusItemnotUniqueException();
      }
    }
  }

  public static boolean Validate_saleuniqueness(int saleID, HashMap<Item, Integer> itemmap) {
    ArrayList<Integer> list = new ArrayList<>();
    for (Item item : itemmap.keySet()) {
      list.add(saleID + item.getId());
    }
    if (distinctValues(list)) {
      return true;
    }
    return false;
  }

  public static boolean distinctValues(ArrayList<Integer> list) {
    for (int i = 0; i < list.size() - 1; i++) {
      for (int j = i + 1; j < list.size(); j++) {
        if (list.get(i) == list.get(j)) {
          return false;
        }
      }
    }
    return true;
  }

  // CHECKS IF ROLEID EXISTS IN A ROLES TABLE
  public static void Validate_roleId(int roleId) throws SQLException, DatabaseInsertException {
    if (roleId >= 0 && roleId < 4) {
      throw new DatabaseInsertException();
    }
  }

  // CHECKS IF USERID EXISTS IN THE USERS TABLE
  public static void Validate_userId(int userId) throws SQLException, DatabaseInsertException {
    if (userId <= 0) {
      throw new DatabaseInsertException();
    }
  }

  // CHECKS IF ITEMID ALREADY EXISTS IN ITEM TABLE
  public static void Validate_itemId(int itemId) throws SQLException, DatabaseInsertException {
    if (itemId <= 0) {
      throw new DatabaseInsertException();
    }
  }

  // CHECKS IF INPUT QUANTITY HAS A PROGRAM VALID VALUE
  public static void Validate_quantity(int quantity) throws SQLException, DatabaseInsertException {
    if (quantity < 0) {
      throw new DatabaseInsertException();
    }
  }

  // CHECKS IF SALEID EXISTS IN SALES TABLE
  public static void Validate_saleid(int saleId) throws SQLException, DatabaseInsertException {
    Sale sale = DatabaseSelectHelper.getSaleById(saleId);
    if (sale.equals(null)) {
      throw new DatabaseInsertException();
    }
  }

  // CHECKS IF THE INPUTS OF INSERTS OF USER ARE VALID
  public static void Validate_insertuser(String name, int age, String address, String password)
      throws SQLException, DatabaseInsertException {
    int userId = DatabaseInsertHelper.insertNewUser(name, age, address, password);
    if (userId == -1) {
      throw new DatabaseInsertException();
    }
  }

  // CHECKS IF ROLENAME ALREADY HAS AN ASSIGNED ROLEID
  public static boolean isContainsRole(String roleName) {
    List<Integer> roleIds = DatabaseSelectHelper.getRoleIds();
    for (Integer roleId : roleIds) {
      if (DatabaseSelectHelper.getRoleName(roleId).equals(roleName)) {
        return true;
      }
    }
    return false;
  }

}
