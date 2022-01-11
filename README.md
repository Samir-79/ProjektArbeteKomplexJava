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
"email": "sophia.thomas@yahoo.com",
"password": "Geeks@portal20",
"shoppingCart":{
"price":0.0,
"qty":0
},
"loggedInCustomer":{
}
}

-------------------------------------------------
localhost:8080/bokhandel/api/v1/customer/login
{
"email": "sophia.thomas@yahoo.com",
"password": "Geeks@portal20"
}
--------------------------------------------------
localhost:8080/bokhandel/api/v1/customer/update
{
"firstName": "Sophia",
"lastName": "Thomas",
"address": "Street 69",
"phone": "5468645483483",
"username": "SOTH",
"email": "sophia.thomas@yahoo.com",
"password": "Geeks@portal205g"
}
------------------------------------------------------
localhost:8080/bokhandel/api/v1/customer/deleteCustomer/id

------------------------------------------------------
localhost:8080/bokhandel/api/v1/employee/signup
{
"firstName": "Biniam",
"lastName": "Andersson",
"address": "Street 65",
"phone": "987654356",
"username": "BIAN",
"email": "biniam.andersson@bokhandel.se",
"password": "Geeks@portal2012a",
"loggedInEmployee":{
}
}
-------------------------------------------------------
localhost:8080/bokhandel/api/v1/employee/login
{
"email": "binaim.andersson@bokhandel.se",
"password": "Geeks@portal22"
}
-------------------------------------------------------
localhost:8080/bokhandel/api/v1/employee/update
{
"firstName": "Biniam",
"lastName": "Andersson",
"address": "Street 65",
"phone": "987654356",
"username": "BIAN",
"email": "biniam.andersson@bokhandel.se",
"password": "Geeks@portal2012a"
}
------------------------------------------------------------
localhost:8080/bokhandel/api/v1/employee/searchEmployee/id

------------------------------------------------------------
localhost:8080/bokhandel/api/v1/employee/searchCustomer/id

------------------------------------------------------------
localhost:8080/bokhandel/api/v1/employee/getListOfCustomer

------------------------------------------------------------
localhost:8080/bokhandel/api/v1/employee/deleteEmployee/id

------------------------------------------------------------
RabbitMQ Management login
username: guest
password: guest
------------------------------------------------------------