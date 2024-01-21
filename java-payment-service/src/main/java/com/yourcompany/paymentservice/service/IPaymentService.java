package com.yourcompany.paymentservice.service;

import java.util.ArrayList;

import com.yourcompany.paymentservice.model.Bill;
import com.yourcompany.paymentservice.model.Customer;
import com.yourcompany.paymentservice.model.Transaction;

public interface IPaymentService {
  Customer addFunds(String email, double amount);

  Customer addCustomer(String name, String email, String phone, double balance);

  Customer viewCustomer(String email);

  ArrayList<Transaction> viewCustomerTransactions(String email);

  Bill createBill(String customerEmail, String serviceType, double amount, String dueDate);

  void deleteBill(long billId);

  Bill updateBill(long billId, double newAmount);

  Bill viewBill(long billId);

  ArrayList<Bill> searchBillsByService(String serviceType);

  Bill payBill(String customerEmail, long billId);

  ArrayList<Bill> payBills(String customerEmail, long[] billIds);
}
