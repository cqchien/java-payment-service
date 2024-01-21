package com.yourcompany.paymentservice;

import java.util.Scanner;

import com.yourcompany.paymentservice.service.IPaymentService;
import com.yourcompany.paymentservice.service.PaymentService;

/**
 * Hello world!
 *
 */
public class App {
    private static final String ADD_CUSTOMER = "add_customer";
    private static final String VIEW_CUSTOMER = "view_customer";
    private static final String VIEW_CUSTOMER_TRANSACTION = "view_customer_transactions";
    private static final String ADD_FUNDS = "add_funds";
    private static final String CREATE_BILL = "create_bill";
    private static final String DELETE_BILL = "delete_bill";
    private static final String UPDATE_BILL = "update_bill";
    private static final String VIEW_BILL = "view_bill";
    private static final String SEARCH_BILLS = "search_bills";
    private static final String PAY_BILL = "pay_bill";
    private static final String PAY_BILLS = "pay_bills";
    private static final String EXIT = "exit";

    private static IPaymentService paymentService = new PaymentService();

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        System.out.println("Welcome to the Payment System!");

        while (true) {
            System.out.print("Input command: ");
            String input = scanner.nextLine().trim();

            if (input.equalsIgnoreCase("exit")) {
                System.out.println("Good bye!");
                break;
            }

            processCommand(input);
        }

        scanner.close();
    }

    private static void processCommand(String input) {
        String[] tokens = input.split("\\s+");
        String command = tokens[0];

        try {
            switch (command.toLowerCase()) {
                case ADD_CUSTOMER:
                    if (tokens.length >= 5) {
                        String name = tokens[1];
                        String email = tokens[2];
                        String phone = tokens[3];
                        double balance = Double.parseDouble(tokens[4]);

                        Object customer = paymentService.addCustomer(name, email, phone, balance);

                        System.out.println("Customer added: " + customer);
                    } else {
                        System.out.println("Invalid command. Usage: add_customer <name> <email> <phone> <balance>");
                    }
                    break;
                case VIEW_CUSTOMER:
                    if (tokens.length >= 2) {
                        String email = tokens[1];

                        Object customer = paymentService.viewCustomer(email);

                        System.out.println("Customer found: " + customer);
                    } else {
                        System.out.println("Invalid command. Usage: view_customer <email>");
                    }
                    break;
                case VIEW_CUSTOMER_TRANSACTION:
                    if (tokens.length >= 2) {
                        String email = tokens[1];

                        Object[] transactions = paymentService.viewCustomerTransactions(email).toArray();

                        System.out.println("Transactions found: " + transactions.length);
                        for (Object transaction : transactions) {
                            System.out.println(transaction);
                        }
                    } else {
                        System.out.println("Invalid command. Usage: view_customer_transactions <email>");
                    }
                    break;
                case ADD_FUNDS:
                    if (tokens.length >= 3) {
                        String email = tokens[1];
                        double amount = Double.parseDouble(tokens[2]);

                        paymentService.addFunds(email, amount);

                        System.out.println("Funds added successfully" + amount);
                    } else {
                        System.out.println("Invalid command. Usage: add_funds <email> <amount>");
                    }
                    break;
                case CREATE_BILL:
                    if (tokens.length >= 5) {
                        String email = tokens[1];
                        String serviceType = tokens[2];
                        double amount = Double.parseDouble(tokens[3]);
                        String dueDate = tokens[4];

                        Object bill = paymentService.createBill(email, serviceType, amount, dueDate);

                        System.out.println("Bill created: " + bill);
                    } else {
                        System.out.println(
                                "Invalid command. Usage: create_bill <email> <service_type> <amount> <due_date> <description>");
                    }
                    break;
                case VIEW_BILL:
                    if (tokens.length >= 2) {
                        long billId = Long.parseLong(tokens[1]);

                        Object bill = paymentService.viewBill(billId);

                        System.out.println("Bill found: " + bill);
                    } else {
                        System.out.println("Invalid command. Usage: view_bill <bill_id>");
                    }
                    break;
                case DELETE_BILL:
                    if (tokens.length >= 2) {
                        long billId = Long.parseLong(tokens[1]);

                        paymentService.deleteBill(billId);

                        System.out.println("Bill deleted successfully");
                    } else {
                        System.out.println("Invalid command. Usage: delete_bill <bill_id>");
                    }
                    break;
                case UPDATE_BILL:
                    if (tokens.length >= 3) {
                        long billId = Long.parseLong(tokens[1]);
                        double newAmount = Double.parseDouble(tokens[2]);

                        Object bill = paymentService.updateBill(billId, newAmount);

                        System.out.println("Bill updated: " + bill);
                    } else {
                        System.out.println("Invalid command. Usage: update_bill <bill_id> <new_amount>");
                    }
                    break;
                case SEARCH_BILLS:
                    if (tokens.length >= 2) {
                        String serviceType = tokens[1];

                        Object[] bills = paymentService.searchBillsByService(serviceType).toArray();

                        System.out.println("Bills found: " + bills.length);
                        for (Object bill : bills) {
                            System.out.println(bill);
                        }
                    } else {
                        System.out.println("Invalid command. Usage: search_bills <service_type>");
                    }
                    break;
                case PAY_BILL:
                    if (tokens.length >= 3) {
                        String email = tokens[1];
                        long billId = Long.parseLong(tokens[2]);

                        Object transaction = paymentService.payBill(email, billId);

                        System.out.println("Transaction created: " + transaction);
                    } else {
                        System.out.println("Invalid command. Usage: pay_bill <email> <bill_id>");
                    }
                    break;
                case PAY_BILLS:
                    if (tokens.length >= 3) {
                        long[] billIds = new long[tokens.length - 2];

                        for (int i = 2; i < tokens.length; i++) {
                            try {
                                long billId = Long.parseLong(tokens[i]);
                                billIds[i - 2] = billId;
                            } catch (NumberFormatException e) {
                                System.out.println("Invalid bill ID: " + tokens[i]);
                            }
                        }

                        String email = tokens[1];

                        Object[] transactions = paymentService.payBills(email, billIds).toArray();

                        System.out.println("Transactions created: " + transactions.length);
                        for (Object transaction : transactions) {
                            System.out.println(transaction);
                        }
                    } else {
                        System.out.println("Invalid command. Usage: pay_bills <email> <bill_id>");
                    }
                    break;
                case EXIT:
                    System.out.println("Good bye!");
                    System.exit(0);
                    break;
                default:
                    System.out.println("Unknown command. Please try again.");
            }
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}
