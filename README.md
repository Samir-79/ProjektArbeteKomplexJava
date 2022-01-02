Installation requirement
------------------------
Java JDK 17+
Docker
Maven
RabbitMQ
Valfrit IDE som stödjer Java 
-------------------------------
localhost:8080/bokhandel/api/v1/customer/signup
{
"firstName": "Sophia",
"lastName": "Thomas",
"address": "Street 69",
"phone": "546864",
"username": "SOTH",
"email": "philipm606@gmail.com",
"password": "password",
"shoppingCart":{
"price":0.0,
"qty":0
}
}
-------------------------------------------------
localhost:8080/bokhandel/api/v1/customer/login
{
"email": "sophia.thomas@mail.com",
"password": "password"
}
--------------------------------------------------
localhost:8080/bokhandel/api/v1/customer/update
{
"firstName": "Sophia",
"lastName": "Thomas",
"address": "Street 69",
"phone": "5468645483483",
"username": "SOTH",
"email": "sophia.thomas@mail.com",
"password": "password123456"
}
localhost:8080/bokhandel/api/v1/customer/deleteCustomer/id
------------------------------------------------------
localhost:8080/bokhandel/api/v1/employee/signup
{
"firstName": "Biniam",
"lastName": "Andersson",
"address": "Street 87",
"phone": "987654356",
"username": "BIAN",
"email": "biniam.andersson@mail.com",
"password": "password75"
}
localhost:8080/bokhandel/api/v1/employee/login
{
"email": "biniam.andersson@mail.com",
"password": "password75"
}
localhost:8080/bokhandel/api/v1/employee/update
{
"firstName": "Biniam",
"lastName": "Andersson",
"address": "Street 65",
"phone": "987654356",
"username": "BIAN",
"email": "biniam.andersson@mail.com",
"password": "password3432"
}
localhost:8080/bokhandel/api/v1/employee/searchEmployee/id
localhost:8080/bokhandel/api/v1/employee/searchCustomer/id
localhost:8080/bokhandel/api/v1/employee/getListOfCustomer
localhost:8080/bokhandel/api/v1/employee/deleteEmployee/id
------------------------------------------------------
RabbitMQ Management login
username: guest
password: guest
------------------------------------------------------