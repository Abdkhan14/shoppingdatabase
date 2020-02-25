package com.b07.store;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import com.b07.database.helper.DatabaseInsertHelper;
import com.b07.database.helper.DatabaseSelectHelper;
import com.b07.database.helper.DatabaseUpdateHelper;
import com.b07.exceptions.NotloggedinException;
import com.b07.inventory.Item;
import com.b07.inventory.ItemImplement;
import com.b07.users.Customer;

public class ShoppingCart {
  private HashMap<Item, Integer> items = new HashMap<>();
  private Customer customer = new Customer(1, "", 1, "");
  private BigDecimal total = new BigDecimal("0.00");
  private static final BigDecimal TAXRATE = new BigDecimal("1.13");

  public ShoppingCart(Customer customer) {
    try {
      if (customer.authenticate(DatabaseSelectHelper.getPassword(customer.getId()))) {
        this.customer = customer;
      } else {
        throw new NotloggedinException();
      }
    } catch (NotloggedinException e) {
      System.out.println(
          customer.getName() + "not logged in. Customer has to be logged in to use ShoppingCart");
    }
  }

  public void setItemMap(HashMap<Item, Integer> items) {
    this.items = items;
  }

  public HashMap<Item, Integer> getItemMap() {
    return this.items;
  }

  public boolean containsItem(Item item) {
    List<Item> items = this.getItems();
    for (Item i : items) {
      if (item.getId() == i.getId()) {
        return true;
      }
    }
    return false;
  }

  public Item getKey(Item item) {
    Item i1 = new ItemImplement(1, "");
    for (Item i : this.getItems()) {
      if (i.getId() == item.getId()) {
        return i;
      }
    }
    return i1;
  }

  public void additem(Item item, int quantity) {
    if (this.containsItem(item)) {
      this.items.put(this.getKey(item), quantity + this.items.get(this.getKey(item)));
    } else {
      this.items.put(item, quantity);
    }
    BigDecimal b = new BigDecimal(quantity);
    this.total = this.total.add(item.getPrice().multiply(b));
  }

  public void removeItem(Item item, int quantity) {
    if (this.containsItem(item)) {
      int currentq = this.items.get(this.getKey(item));
      if (currentq - quantity <= 0) {
        this.items.remove(this.getKey(item));
      } else {
        this.items.put(this.getKey(item), currentq - quantity);
      }
    } else {
      System.out.println("The item doesn't belong in cart");
    }
    BigDecimal b = new BigDecimal(quantity);
    this.total = this.total.subtract(item.getPrice().multiply(b));
  }

  public int getQuantitybyItem(Item item) {
    int q = 0;
    if (this.items.containsKey(item)) {
      for (Item item1 : this.items.keySet()) {
        if (item1.equals(item)) {
          q = this.items.get(item1);
        }
      }
    }
    return q;
  }

  public List<Item> getItems() {
    List<Item> items = new ArrayList<>();
    for (Item item : this.items.keySet()) {
      items.add(item);
    }
    return items;
  }

  public BigDecimal getTaxRate() {
    return TAXRATE;
  }

  public BigDecimal getTotal() {
    return this.total;
  }

  public void clearCart() {
    this.items.clear();
  }

  public Customer getCustomer() {
    return this.customer;
  }

  public boolean checkout() {
    BigDecimal totalandtax = this.total.multiply(TAXRATE);
    if (!this.customer.equals(null)) {
      for (Item item1 : this.items.keySet()) {
        int InventoryQuantity = DatabaseSelectHelper.getInventoryQuantity(item1.getId());
        if (InventoryQuantity < this.items.get(item1)) {
          return false;
        } else {
          int saleId = DatabaseInsertHelper.insertSale(this.customer.getId(), totalandtax);
          for (Item item : this.items.keySet()) {
            DatabaseInsertHelper.insertItemizedSale(saleId, item.getId(), this.items.get(item));
            int Inventory_Quantity = DatabaseSelectHelper.getInventoryQuantity(item.getId());
            int User_Quantity = Inventory_Quantity - this.items.get(item);
            DatabaseUpdateHelper.updateInventoryQuantity(User_Quantity, item.getId());
            clearCart();
            return true;
          }
        }
      }
    }
    return false;
  }

}
