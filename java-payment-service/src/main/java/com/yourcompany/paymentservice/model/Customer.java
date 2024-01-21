package com.yourcompany.paymentservice.model;

import java.util.ArrayList;

public class Customer {
  private static final String TYPE_ADD_FUNDS = "ADD_FUNDS";
  private static final String TYPE_DEDUCT_FUNDS = "DEDUCT_FUNDS";

  private long id;
  private String name;
  private String email;
  private String phone;
  private double balance;
  private ArrayList<Transaction> transactions;

  public Customer(String name, String email, String phone, double balance) {
    this.id = System.currentTimeMillis();
    this.name = name;
    this.email = email;
    this.phone = phone;
    this.balance = balance;
    this.transactions = new ArrayList<>();
  }

  public double getBalance() {
    return balance;
  }

  public String getName() {
    return name;
  }

  public String getPhone() {
    return phone;
  }

  public ArrayList<Transaction> getTransactions() {
    return transactions;
  }

  private void addTransaction(Transaction transaction) {
    transactions.add(transaction);
  }

  public void addFunds(double amount) {
    Transaction newTransaction = new Transaction(this, TYPE_ADD_FUNDS, amount);
    addTransaction(newTransaction);
    balance += amount;
  }

  public void deductFunds(double amount) {
    if (balance >= amount) {
      Transaction newTransaction = new Transaction(this, TYPE_DEDUCT_FUNDS, amount);
      addTransaction(newTransaction);
      balance -= amount;
    }
  }

  public long getId() {
    return id;
  }

  public String getEmail() {
    return email;
  }

  @Override
  public String toString() {
    return "Customer{" +
        "id=" + id +
        ", name='" + name + '\'' +
        ", email='" + email + '\'' +
        ", balance=" + balance +
        '}';
  }
}
