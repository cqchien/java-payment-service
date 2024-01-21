package com.yourcompany.paymentservice;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import com.yourcompany.paymentservice.service.BillManager;
import com.yourcompany.paymentservice.service.PaymentService;
import com.yourcompany.paymentservice.model.Customer;

import java.util.ArrayList;

import com.yourcompany.paymentservice.model.Bill;
import com.yourcompany.paymentservice.model.Service;

/**
 * Unit test for simple App.
 */
public class AppTest
        extends TestCase {

    /**
     * Create the test case
     *
     * @param testName name of the test case
     */
    public AppTest(String testName) {
        super(testName);
    }

    /**
     * @return the suite of tests being tested
     */
    public static Test suite() {
        return new TestSuite(AppTest.class);
    }

    // Test cases for create new customer
    public void testCreateCustomer() {
        PaymentService paymentService = new PaymentService();

        Customer customer = paymentService.addCustomer("John", "john@gmail.com", "0123456789", 1000);
        assertEquals("John", customer.getName());
        assertEquals("john@gmail.com", customer.getEmail());
        assertEquals("0123456789", customer.getPhone());
        assertEquals(1000.0, customer.getBalance());
    }

    // Test cases for add funds
    public void testAddFundSuccess() {
        PaymentService paymentService = new PaymentService();

        Customer customer = paymentService.addCustomer("John", "john@gmail.com", "0123456789", 1000);
        customer = paymentService.addFunds("john@gmail.com", 20);
        assertEquals("John", customer.getName());
        assertEquals("john@gmail.com", customer.getEmail());
        assertEquals(1020.0, customer.getBalance());
    }

    public void testAddFundsInvalidEmail() {
        PaymentService paymentService = new PaymentService();

        try {
            Customer customer = paymentService.addFunds("chien@gmail.com", 20);
        } catch (Exception e) {
            assertEquals("Customer with email chien@gmail.com does not exist", e.getMessage());
        }
    }

    public void testAddFundsNegativeAmount() {
        PaymentService paymentService = new PaymentService();

        try {
            Customer customer = paymentService.addCustomer("John", "john@gmail.com", "0123456789", 1000);
            customer = paymentService.addFunds("john@gmail.com", -20);
        } catch (Exception e) {
            assertEquals("Amount must be greater than 0", e.getMessage());
        }
    }

    // Test cases for create bill
    public void testCreateBillSuccess() {
        PaymentService paymentService = new PaymentService();

        Customer customer = paymentService.addCustomer("John", "john@gmail.com", "0123456789", 1000);
        Bill bill = paymentService.createBill("john@gmail.com", "ELECTRICITY", 100, "21/01/2024");
        assertEquals("ELECTRICITY", bill.getService().getName());
        assertEquals(100.0, bill.getAmount());
        assertEquals("UNPAID", bill.getStatus());
    }

    public void testCreateBillInvalidEmail() {
        PaymentService paymentService = new PaymentService();

        try {
            Bill bill = paymentService.createBill("chien@gmail.com", "ELECTRICITY", 100, "21/01/2024");
        } catch (Exception e) {
            assertEquals("Customer with email chien@gmail.com does not exist", e.getMessage());
        }
    }

    public void testViewBillSuccess() {
        PaymentService paymentService = new PaymentService();

        Customer customer = paymentService.addCustomer("John", "john@gmail.com", "0123456789", 1000);
        Bill bill = paymentService.createBill("john@gmail.com", "ELECTRICITY", 100, "21/01/2024");
        Bill existedBill = paymentService.viewBill(bill.getId());
        assertEquals("ELECTRICITY", existedBill.getService().getName());
        assertEquals(100.0, existedBill.getAmount());
        assertEquals("UNPAID", existedBill.getStatus());
    }

    public void testUpdateBillSuccess() {
        PaymentService paymentService = new PaymentService();

        Customer customer = paymentService.addCustomer("John", "john@gmail.com", "0123456789", 1000);
        Bill bill = paymentService.createBill("john@gmail.com", "ELECTRICITY", 100, "21/01/2024");
        Bill updatedBill = paymentService.updateBill(bill.getId(), 200);
        assertEquals("ELECTRICITY", updatedBill.getService().getName());
        assertEquals(200.0, updatedBill.getAmount());
        assertEquals("UNPAID", updatedBill.getStatus());
    }

    public void testDeleteBillSuccess() {
        PaymentService paymentService = new PaymentService();

        Customer customer = paymentService.addCustomer("John", "john@gmail.com", "0123456789", 1000);
        Bill bill = paymentService.createBill("john@gmail.com", "ELECTRICITY", 100, "21/01/2024");
        paymentService.deleteBill(bill.getId());
        try {
            Bill existedBill = paymentService.viewBill(bill.getId());
        } catch (Exception e) {
            assertEquals("Bill with id " + bill.getId() + " does not exist", e.getMessage());
        }
    }

    public void testPayBillSuccess() {
        PaymentService paymentService = new PaymentService();

        Customer customer = paymentService.addCustomer("John", "john@gmail.com", "0123456789", 1000);
        Bill bill = paymentService.createBill("john@gmail.com", "ELECTRICITY", 100, "21/01/2024");
        Bill paidBill = paymentService.payBill("john@gmail.com", bill.getId());
        assertEquals("ELECTRICITY", paidBill.getService().getName());
        assertEquals(100.0, paidBill.getAmount());
        assertEquals("PAID", paidBill.getStatus());
        assertEquals(900.0, customer.getBalance());
        assertEquals(100.0, customer.getTransactions().get(0).getAmount());
    }

    public void testPayBillsSuccess() {
        PaymentService paymentService = new PaymentService();

        Customer customer = paymentService.addCustomer("John", "john@gmail.com", "0123456789", 1000);
        Bill bill1 = paymentService.createBill("john@gmail.com", "ELECTRICITY", 100, "21/01/2024");
        Bill bill2 = paymentService.createBill("john@gmail.com", "WATER", 200, "22/01/2024");
        Bill bill3 = paymentService.createBill("john@gmail.com", "WATER", 700, "23/01/2024");
        long[] billIds = { bill1.getId(), bill2.getId(), bill3.getId() };
        ArrayList<Bill> paidBills = paymentService.payBills("john@gmail.com" , billIds);
    }
}
