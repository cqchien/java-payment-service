package com.yourcompany.paymentservice.service;

import java.util.ArrayList;

import com.yourcompany.paymentservice.model.Bill;
import com.yourcompany.paymentservice.model.Customer;
import com.yourcompany.paymentservice.model.Transaction;

public class PaymentService implements IPaymentService {
  private ArrayList<Customer> customers;
  private BillManager billManager;

  public PaymentService() {
    this.customers = new ArrayList<>();
    this.billManager = new BillManager();
  }

  public ArrayList<Customer> getCustomers() {
    return customers;
  }

  public ArrayList<Transaction> viewCustomerTransactions(String email) {
    Customer customer = findCustomerByEmail(email);
    if (customer == null) {
      throw new IllegalArgumentException("Customer with email " + email + " does not exist");
    }

    return customer.getTransactions();
  }

  /**
   * Pay bills
   * 
   * @param customerEmail customer's email
   * @param billIds       bill's ids
   * @throws IllegalArgumentException if customer does not exist
   * @throws IllegalArgumentException if bill does not exist
   * @throws IllegalArgumentException if amount is negative
   * @throws IllegalArgumentException if customer does not have enough balance
   * @throws IllegalArgumentException if bill is already paid
   * @return ArrayList<Bill>
   */
  public ArrayList<Bill> payBills(String customerEmail, long[] billIds) {
    Customer customer = findCustomerByEmail(customerEmail);
    if (customer == null) {
      throw new IllegalArgumentException("Customer with email " + customerEmail + " does not exist");
    }

    return billManager.payBills(customer, billIds);
  }

  /**
   * Pay bill
   * 
   * @param customerEmail customer's email
   * @param billId        bill's id
   * @throws IllegalArgumentException if customer does not exist
   * @throws IllegalArgumentException if bill does not exist
   * @throws IllegalArgumentException if amount is negative
   * @throws IllegalArgumentException if customer does not have enough balance
   * @throws IllegalArgumentException if bill is already paid
   * @return Bill
   */
  public Bill payBill(String customerEmail, long billId) {
    Customer customer = findCustomerByEmail(customerEmail);
    if (customer == null) {
      throw new IllegalArgumentException("Customer with email " + customerEmail + " does not exist");
    }

    return billManager.payBill(customer, billId);
  }

  /**
   * Create new bill
   * 
   * @param customerEmail customer's email
   * @param string        service type
   * @param amount        amount
   * @param dueDate       due date
   * @throws IllegalArgumentException if customer does not exist
   * @return Bill
   */
  public Bill createBill(String customerEmail, String serviceType, double amount, String dueDate) {
    Customer customer = findCustomerByEmail(customerEmail);
    if (customer == null) {
      throw new IllegalArgumentException("Customer with email " + customerEmail + " does not exist");
    }

    return billManager.createBill(customer, serviceType, amount, dueDate);
  }

  /**
   * Delete bill
   * 
   * @param billId bill's id
   * @throws IllegalArgumentException if bill does not exist
   * @return boolean
   */
  public void deleteBill(long billId) {
    billManager.deleteBill(billId);
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
    return billManager.updateBill(billId, newAmount);
  }

  /**
   * View bill
   * 
   * @param billId bill's id
   * @throws IllegalArgumentException if bill does not exist
   * @return Bill
   */
  public Bill viewBill(long billId) {
    return billManager.viewBill(billId);
  }

  public Customer viewCustomer(String email) {
    Customer customer = findCustomerByEmail(email);
    if (customer == null) {
      throw new IllegalArgumentException("Customer with email " + email + " does not exist");
    }

    return customer;
  }

  /**
   * Search bills by service type
   * 
   * @param String service type
   * @return ArrayList<Bill>
   */
  public ArrayList<Bill> searchBillsByService(String serviceType) {
    return billManager.searchBillsByService(serviceType);
  }

  /**
   * Add funds to customer's balance
   * 
   * @param email  customer's email
   * @param amount amount to add
   * @throws IllegalArgumentException if customer does not exist
   * @throws IllegalArgumentException if amount is negative
   * @return void
   */
  public Customer addFunds(String email, double amount) {
    Customer customer = findCustomerByEmail(email);
    if (customer == null) {
      throw new IllegalArgumentException("Customer with email " + email + " does not exist");
    }

    if (amount < 0) {
      throw new IllegalArgumentException("Amount must be greater than 0");
    }

    customer.addFunds(amount);
    return customer;
  }

  /**
   * Add new customer
   * 
   * @param name    customer's name
   * @param email   customer's email
   * @param phone   customer's phone
   * @param balance customer's balance
   * @throws IllegalArgumentException if name is empty
   * @throws IllegalArgumentException if email is empty
   * @throws IllegalArgumentException if balance is negative
   * @throws IllegalArgumentException if customer with email already exists
   * @return Customer
   */
  public Customer addCustomer(String name, String email, String phone, double balance) {
    if (name == null || name.isEmpty()) {
      throw new IllegalArgumentException("Name cannot be empty");
    }

    if (email == null || email.isEmpty()) {
      throw new IllegalArgumentException("Email cannot be empty");
    }

    if (balance < 0) {
      throw new IllegalArgumentException("Balance cannot be negative");
    }

    Customer existedCustomer = findCustomerByEmail(email);
    if (existedCustomer != null) {
      throw new IllegalArgumentException("Customer with email " + email + " already exists");
    }

    return addNewCustomer(name, email, phone, balance);
  }

  private Customer findCustomerByEmail(String email) {
    for (Customer customer : customers) {
      if (customer.getEmail().equalsIgnoreCase(email)) {
        return customer;
      }
    }

    return null;
  }

  private Customer addNewCustomer(String name, String email, String phone, double balance) {
    customers.add(new Customer(name, email, phone, balance));
    return findCustomerByEmail(email);
  }
}
