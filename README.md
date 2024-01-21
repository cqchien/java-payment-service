# Java Payment Service

Welcome to the Java Payment Service application! This application allows you to manage customers, bills, and transactions for a payment system.

## Getting Started

### Prerequisites

- Java Development Kit (JDK) installed
- Maven build tool

### Build the Project

```bash
mvn clean install
```

### Run the Application
```bash
mvn exec:java
```

## Usage
The application supports various commands to perform actions. Here are some example commands:
### Add a customer:
```bash
add_customer <name> <email> <phone> <balance>
```

### View a customer:
```bash
view_customer <email>
```

### View a customer with transactions:
```bash
view_customer_transactions <email>
```

### Add Funds
```bash
add_funds <email> <amount>
```

### Create a bill
```bash
create_bill <email> <service_type> <amount> <due_date> <description>
```

### View a bill
```bash
view_bill <bill_id>
```

### Delete a bill
```bash
delete_bill <bill_id>
```

### Update a bill
```bash
update_bill <bill_id> <new_amount>
```

### Search bills
```bash
search_bills <service_type>
```

### Pay bill
```bash
pay_bill <email> <bill_id>
```

### Pay bills
```bash
pay_bills <email> <bill_id>  <bill_id>  <bill_id>
```
