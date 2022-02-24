# Spring-Boot CRM Application

## About
This is a simple spring-boot test application. 
The use case is based around a company trying to expose their enterprise CRM.
Customers can manage their profile (create, update, delete, etc.).
We need to create an API that can be used by different channels as well (mobile (app), web, etc.).

**Assumptions**

To make it simple, we just make 2 users for different channels.
In real-world, one User account = one Customer account and they can manage ONLY their profile.

## Requirements
This is build using Maven 3 and Java 1.8

## Usage
There are two options to run this. 
1. A default options that does not have any security
2. A secured spring-boot application

To run the application with security then run **./run.sh**

To run the application with security then run **./run.sh auth**

Once the HSQL Database Manager appear, then copy the insert sql in /data/data.sql to setup the two system users.
```
Web channel user          - webuser:password
Mobile (app) channel user - appuser:password
```

The application is running at [http://localhost:8080](http://localhost:8080).

The swagger-ui is at [http://localhost:8080/swagger-ui.html](http://localhost:8080/swagger-ui.html)

There are two endpoints for authentication.
```
/rest/login                    - login to the system to use the secure sites
/rest/logout                   - logout from the system
```

There are five endpoints for customer operations.
```
/secure/customer/add           - add a customer
/secure/customer/delete/{id}   - delete a customer by id
/secure/customer/get/{id}      - get a customer by id
/secure/customer/update        - update a customer
/secure/customers              - get all customers
```

## Creator

**John Rebada**

* <https://github.com/jantox>

## Copyright and license

The code is released under the [MIT license](LICENSE?raw=true).

---------------------------------------

Please feel free to send me some feedback or questions!
