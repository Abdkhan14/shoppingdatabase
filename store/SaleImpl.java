package com.b07.store;

import java.math.BigDecimal;
import java.util.HashMap;
import com.b07.inventory.Item;
import com.b07.users.User;

public class SaleImpl implements Sale {

  private int id;
  private User user;
  private BigDecimal totalprice;
  private HashMap<Item, Integer> itemmap;

  public SaleImpl() {

  }

  public SaleImpl(int id) {
    this.id = id;
  }

  public SaleImpl(int id, HashMap<Item, Integer> itemmap) {
    this.id = id;
    this.itemmap = itemmap;

  }

  public SaleImpl(int id, User user, BigDecimal totalprice) {
    this.id = id;
    this.totalprice = totalprice;
    this.user = user;
  }

  @Override
  public int getId() {
    // TODO Auto-generated method stub
    return this.id;
  }

  @Override
  public void setId(int id) {
    // TODO Auto-generated method stub
    this.id = id;
  }

  @Override
  public User getUser() {
    // TODO Auto-generated method stub
    return this.user;
  }

  @Override
  public void setUser(User user) {
    // TODO Auto-generated method stub
    this.user = user;
  }

  @Override
  public BigDecimal getTotalPrice() {
    // TODO Auto-generated method stub
    return this.totalprice;
  }

  @Override
  public void setTotalPrice(BigDecimal price) {
    // TODO Auto-generated method stub
    this.totalprice = price;
  }

  @Override
  public HashMap<Item, Integer> getItemMap() {
    // TODO Auto-generated method stub
    return this.itemmap;
  }

  @Override
  public void setItemMap(HashMap<Item, Integer> itemMap) {
    // TODO Auto-generated method stub
    this.itemmap = itemMap;
  }
}
