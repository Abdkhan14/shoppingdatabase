package com.b07.inventory;

import java.math.BigDecimal;

public class ItemImplement implements Item {

  private int id;

  private String name;

  private BigDecimal price;

  public ItemImplement() {


  }

  public ItemImplement(int id, String name) {
    this.id = id;
    this.name = name;
  }

  public ItemImplement(int id, String name, BigDecimal price) {
    this.id = id;
    this.name = name;
    this.price = price;
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
  public String getName() {
    // TODO Auto-generated method stub
    return this.name;
  }

  @Override
  public void setName(String name) {
    // TODO Auto-generated method stub
    this.name = name;
  }

  @Override
  public BigDecimal getPrice() {
    // TODO Auto-generated method stub
    return this.price;
  }

  @Override
  public void setPrice(BigDecimal price) {
    // TODO Auto-generated method stub
    this.price = price;
  }
}
