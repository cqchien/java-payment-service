package com.yourcompany.paymentservice.model;

import java.util.Date;

public class Transaction {
  private long id;
  private Customer customer;
  private String type;
  private double amount;
  private Date paymentDate;

  public Transaction(Customer customer, String type, double amount) {
    this.id = System.currentTimeMillis();
    this.customer = customer;
    this.type = type;
    this.amount = amount;
    this.paymentDate = new Date();
  }

  public long getId() {
    return id;
  }

  public double getAmount() {
    return amount;
  }

  @Override
  public String toString() {
    return "Transaction{" +
        "id=" + id +
        ", customer=" + customer.getEmail() +
        ", type='" + type + '\'' +
        ", amount=" + amount +
        ", paymentDate=" + paymentDate +
        '}';
  }
}
