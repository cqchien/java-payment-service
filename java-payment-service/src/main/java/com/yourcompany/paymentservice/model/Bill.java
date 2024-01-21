package com.yourcompany.paymentservice.model;

import java.util.Date;

public class Bill {
  private static final String STATUS_PENDING = "PENDING";
  private static final String STATUS_PAID = "PAID";
  private static final String STATUS_UNPAID = "UNPAID";

  private long id;
  private Service service;
  private Customer customer;
  private double amount;
  private String status;
  private Date dueDate;

  public Bill(Customer customer, Service service, double amount, Date dueDate) {
    this.id = System.currentTimeMillis();
    this.customer = customer;
    this.service = service;
    this.amount = amount;
    this.status = STATUS_UNPAID;
    this.dueDate = dueDate;
  }

  public long getId() {
    return id;
  }

  public void setAmount(double amount) {
    this.amount = amount;
  }

  public void setStatus(String status) {
    this.status = status;
  }

  public String getStatus() {
    return status;
  }

  public boolean canUpdate() {
    return status.equals(STATUS_PENDING) || status.equals(STATUS_UNPAID);
  }

  public Service getService() {
    return service;
  }

  private void markAsPaid() {
    this.status = STATUS_PAID;
  }

  public double getAmount() {
    return amount;
  }

  public Date getDueDate() {
    return dueDate;
  }

  public void payBill() {
    this.markAsPaid();
    this.customer.deductFunds(this.amount);
  }

  @Override
  public String toString() {
    return "Bill{" +
        "id=" + id +
        ", service=" + service.getName() +
        ", customer=" + customer.getEmail() +
        ", amount=" + amount +
        ", status='" + status + '\'' +
        ", dueDate=" + dueDate +
        '}';
  }
}
