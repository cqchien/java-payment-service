package com.yourcompany.paymentservice.service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;

import com.yourcompany.paymentservice.model.Bill;
import com.yourcompany.paymentservice.model.Customer;
import com.yourcompany.paymentservice.model.ElectricityService;
import com.yourcompany.paymentservice.model.Service;
import com.yourcompany.paymentservice.model.WaterService;

enum ServiceType {
  WATER, ELECTRICITY;
}

public class BillManager implements IBillManager {
  private ArrayList<Bill> bills;

  public BillManager() {
    this.bills = new ArrayList<>();
  }

  /**
   * Create new bill
   * 
   * @param customer
   * @param serviceType
   * @param amount
   * @param dueDate
   * @throws IllegalArgumentException if customer does not exist
   * @return Bill
   */
  public Bill createBill(Customer customer, String serviceType, double amount, String dueDate) {
    try {
      Service service = this.getServiceByType(ServiceType.valueOf(serviceType));
      SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
      Date date = sdf.parse(dueDate);

      Bill newBill = new Bill(customer, service, amount, date);
      bills.add(newBill);
      return newBill;
    } catch (Exception e) {
      throw new IllegalArgumentException(e.getMessage());
    }
  }

  /**
   * Delete bill
   * 
   * @param billId bill's id
   * @throws IllegalArgumentException if bill does not exist
   * @return boolean
   */
  public void deleteBill(long billId) {
    Bill billToDelete = findBillById(billId);
    if (billToDelete == null) {
      throw new IllegalArgumentException("Bill with id " + billId + " does not exist");
    }

    bills.remove(billToDelete);
  }

  /**
   * Update bill
   * 
   * @param billId    bill's id
   * @param newAmount new amount
   * @throws IllegalArgumentException if bill does not exist
   * @throws IllegalArgumentException if bill is already processed
   * @throws IllegalArgumentException if amount is negative
   * @return Bill
   */
  public Bill updateBill(long billId, double newAmount) {
    Bill billToUpdate = findBillById(billId);
    if (billToUpdate == null) {
      throw new IllegalArgumentException("Bill with id " + billId + " does not exist");
    }

    if (!billToUpdate.canUpdate()) {
      throw new IllegalArgumentException("Bill with id " + billId + " is already processed");
    }

    if (newAmount < 0) {
      throw new IllegalArgumentException("Amount must be greater than 0");
    }

    billToUpdate.setAmount(newAmount);
    return billToUpdate;
  }

  /**
   * View bill
   * 
   * @param billId bill's id
   * @throws IllegalArgumentException if bill does not exist
   * @return Bill
   */
  public Bill viewBill(long billId) {
    Bill bill = findBillById(billId);
    if (bill == null) {
      throw new IllegalArgumentException("Bill with id " + billId + " does not exist");
    }

    return bill;
  }

  /**
   * Search bills by service type
   * 
   * @param serviceType service type
   * @throws IllegalArgumentException if service type is invalid
   * @return ArrayList<Bill>
   */
  public ArrayList<Bill> searchBillsByService(String serviceType) {
    return findBillsByService(ServiceType.valueOf(serviceType));
  }

  /**
   * Pay bill
   * 
   * @param customer customer
   * @param billId   bill's id
   * @param amount   amount to pay
   * @throws IllegalArgumentException if bill does not exist
   * @throws IllegalArgumentException if bill is already processed
   * @throws IllegalArgumentException if amount is less than bill's amount
   * @throws IllegalArgumentException if customer has insufficient funds
   * @return Bill
   */
  public Bill payBill(Customer customer, long billId) {
    Bill billToPay = findBillById(billId);
    if (billToPay == null) {
      throw new IllegalArgumentException("Bill with id " + billId + " does not exist");
    }

    if (!billToPay.canUpdate()) {
      throw new IllegalArgumentException("Bill with id " + billId + " is already processed");
    }

    if (customer.getBalance() < billToPay.getAmount()) {
      throw new IllegalArgumentException(
          "Customer balance must be greater than or equal the amount of bill: " + billToPay.getAmount());
    }

    billToPay.payBill();
    return billToPay;
  }

  /**
   * Pay bills
   * 
   * @param customer customer
   * @param billIds  bill's ids
   * @throws IllegalArgumentException if bill does not exist
   * @throws IllegalArgumentException if bill is already processed
   * @throws IllegalArgumentException if amount is less than bill's amount
   * @throws IllegalArgumentException if customer has insufficient funds
   * @return ArrayList<Bill>
   */
  public ArrayList<Bill> payBills(Customer customer, long[] billIds) {
    ArrayList<Bill> billsToPay = new ArrayList<>();
    for (long billId : billIds) {
      Bill billToPay = findBillById(billId);
      if (billToPay == null) {
        throw new IllegalArgumentException("Bill with id " + billId + " does not exist");
      }

      if (!billToPay.canUpdate()) {
        continue;
      }

      billsToPay.add(billToPay);
    }

    Collections.sort(billsToPay, Comparator.comparing(Bill::getDueDate));

    for (Bill billToPay : billsToPay) {
      if (customer.getBalance() < billToPay.getAmount()) {
        throw new IllegalArgumentException(
            "Customer balance must be greater than or equal the amount of bill: " + billToPay.getAmount());
      }

      billToPay.payBill();
    }

    return billsToPay;
  }

  private ArrayList<Bill> findBillsByService(ServiceType serviceType) {
    ArrayList<Bill> matchingBills = new ArrayList<>();
    for (Bill bill : bills) {
      if (bill.getService().getName().equalsIgnoreCase(serviceType.toString())) {
        matchingBills.add(bill);
      }
    }

    return matchingBills;
  }

  private Bill findBillById(long id) {
    for (Bill bill : bills) {
      if (bill.getId() == id) {
        return bill;
      }
    }

    return null;
  }

  private Service getServiceByType(ServiceType type) {
    switch (type) {
      case WATER:
        return new WaterService();
      case ELECTRICITY:
        return new ElectricityService();
      default:
        throw new IllegalArgumentException("This service type is unsupported");
    }
  }
}
