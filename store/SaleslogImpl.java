package com.b07.store;

public class SaleslogImpl implements SalesLog {

  private int saleId;
  private int itemId;
  private int quantity;

  public SaleslogImpl() {

  }

  public SaleslogImpl(int saleId) {
    this.saleId = saleId;
  }

  public SaleslogImpl(int saleId, int itemId, int quantity) {
    this.itemId = itemId;
    this.saleId = saleId;
    this.quantity = quantity;
  }

  @Override
  public int getsaleId() {
    // TODO Auto-generated method stub
    return this.saleId;
  }

  @Override
  public void setsaleId(int saleid) {
    // TODO Auto-generated method stub
    this.saleId = saleid;
  }

  @Override
  public int getItemId() {
    // TODO Auto-generated method stub
    return this.itemId;
  }

  @Override
  public int getItemQuantity() {
    // TODO Auto-generated method stub
    return this.quantity;
  }

  @Override
  public void ItemQuantity(int quantity) {
    // TODO Auto-generated method stub
    this.quantity = quantity;
  }

  @Override
  public void setItemId(int itemId) {
    // TODO Auto-generated method stub
    this.itemId = itemId;
  }
}
