# PaymentModule

**I.	Prerequisites :**

A client has a bank account (Unique ID, name, phone number, e-mail address).
Bank sends payment invitation by e-mail giving to the client URL address where the client can make registration. 
This URL contains a token associated with the account.

**II.	Module functionalities:**

In registration URL there are two steps:

- In the first one the client have to enter again his account Unique Id, name, phone number, e-mail address. 
The system compares the data with these associated with the token. If does not match shows error message, else go to second step.

- Second step where he choose login password. The module makes validation for duplicate login and minimal password requirements.
If OK the module sends confirmation e-mail with URL contains activation token.
This URL leads to the Login page where after successful authentication the login will be activated.

In the first page the client can see:
1.	Header with his name:
2.	Balance of his duty.
3.	Possibility to make payment (whole amount or partly) using Sogenactif payment system.
4.	After successful payment, the amount of his duty decreases accordingly. 

**III.	Technologies:**

Spring Boot

Spring JDBC for persistence.

Java 8

Oracle database preferably.

