package com.b07.inventory;

import java.util.HashMap;

public class IventoryImplement implements Inventory {

  private HashMap<Item, Integer> itemmap;
  private int TotalItems;


  public IventoryImplement() {

  }

  public IventoryImplement(HashMap<Item, Integer> itemmap) {
    // this.TotalItems = TotalItems;
    this.itemmap = itemmap;
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

  @Override
  public void updateMap(Item item, Integer value) {
    // TODO Auto-generated method stub
    this.itemmap.replace(item, value);
  }

  @Override
  public int getTotalItems() {
    // TODO Auto-generated method stub
    return this.TotalItems;
  }

  @Override
  public void setTotalItems(int total) {
    // TODO Auto-generated method stub
    this.TotalItems = total;
  }
}
