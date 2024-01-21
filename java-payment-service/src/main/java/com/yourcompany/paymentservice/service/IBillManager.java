package com.yourcompany.paymentservice.service;

import java.util.ArrayList;

import com.yourcompany.paymentservice.model.Bill;
import com.yourcompany.paymentservice.model.Customer;

public interface IBillManager {
  Bill createBill(Customer customer, String serviceType, double amount, String dueDate);

  void deleteBill(long billId);

  Bill updateBill(long billId, double newAmount);

  Bill viewBill(long billId);

  ArrayList<Bill> searchBillsByService(String serviceType);

  Bill payBill(Customer customer, long billId);

  ArrayList<Bill> payBills(Customer customer, long[] billIds);
}
