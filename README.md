Installation requirement
------------------------------------------------------------
Java JDK 11+
Docker
Maven
RabbitMQ
MySQl
Valfrit IDE som stödjer Java 
------------------------------------------------------------
RabbitMQ Management login
username: guest
password: guest
------------------------------------------------------------
Java Email Sender
username: projektbokhandel@gmail.com
password: odcdtxwlaxgmiime
------------------------------------------------------------
Endpoints/JSON: All API requests must be authenticated with basic auth, some requests are reserved for certain roles
AuthorController:
1. GET:localhost:8080/bokhandel/api/v1/author/getauthorbyid/{id}
2. GET:localhost:8080/bokhandel/api/v1/author/getauthorbyfirstname/{firstName}
3. GET:localhost:8080/bokhandel/api/v1/author/getauthorbylastname/{lastName}
4. GET:localhost:8080/bokhandel/api/v1/author/getauthorbyfullname/{firstName}/{lastName}
--------------------
PreAuthorize(ADMIN)
Basic Auth:
Username
Password
---------------------
5. PUT:localhost:8080/bokhandel/api/v1/author/updateauthor
{
"id": 5,
"firstName": "Unus",
"lastName": "Annus"
}
PreAuthorize(ADMIN)
Basic Auth:
Username
Password
---------------------
6. DELETE:localhost:8080/bokhandel/api/v1/author//deleteauthor/{id}
PreAuthorize(ADMIN)
Basic Auth:
Username
Password
---------------------
7. POST:localhost:8080/bokhandel/api/v1/book/addbook
{
"isbn13":"11111111111111111",
"title":"Jakten på julen",
"publishingDate":"2021-11-01",
"weight":448.0,
"pages":267,
"language":"Svenska",
"category":"Fantasy 9-12 år",
"price":123.0,
"authors":[
{
"firstName":"Matt",
"lastName":"Haig"
}
],
"publisher":{
"name":"Alfabeta"
},
"stock":{
"quantity":170,
"inStock":true
}
}
---------------------------------
8. GET:localhost:8080/bokhandel/api/v1/book/getbookbyisbn/{ISBN13}
9. GET:localhost:8080/bokhandel/api/v1/book/getbookbytitle/{title}
10. GET:localhost:8080/bokhandel/api/v1/book/getbookbylanguage/{language}
----------------------------------
11. GET:localhost:8080/bokhandel/api/v1/book/getbookbyid/{id}
PreAuthorize(ADMIN)
Basic Auth:
Username
Password
---------------------
12. DELETE:localhost:8080/bokhandel/api/v1/book/deletebook/{id}
PreAuthorize(ADMIN)
Basic Auth:
Username
Password
---------------------
13. GET:localhost:8080/bokhandel/api/v1/book/getlistofbooks
PreAuthorize(ADMIN)
Basic Auth:
Username
Password
---------------------
14. PATCH:localhost:8080/bokhandel/api/v1/book/updatebookinformation
PreAuthorize(ADMIN)
Basic Auth:
Username
Password
{
"id": 18,
"isbn13":"33333333333",
"title":"Jakten på julen",
"publishingDate":"2021-11-01",
"weight":448.0,
"pages":267,
"language":"Svenska",
"category":"Fantasy 9-12 år",
"price":59.0,
"authors":[
{
"id": 25,
"firstName":"Mattias",
"lastName":"Haig"
}
],
"publisher":{
"id": 26,
"name":"Alfabeta1"
},
"stock":{
"id": 20,
"quantity":150,
"inStock":true
}
}
---------------------
CustomerController:
15. POST:localhost:8080/bokhandel/api/v1/customer/signup
{
"firstName": "Philip",
"lastName": "Thomas",
"address": "Street 69",
"phone": "546864",
"username": "Phma",
"email": "philipt@bokhandel.se.com",
"password": "Geeks@portal20a2",
"shoppingCart":{
"price":0.0,
"qty":0
}
}
--------------------------
16. PUT:localhost:8080/bokhandel/api/v1/customer/update/{id}
{
"id":1,
"firstName":"biniam",
"lastName":"andersson",
"address":"överklassgata 10",
"phone":"12345678",
"username":"biniam_79",
"email":"biniam@gmail.com",
"password":"password_90",
"shoppingCart":{
"price":0.0,
"qty":0
}
}
-------------------------------
17. DELETE:localhost:8080/bokhandel/api/v1/customer/deleteCustomer/{id}
PreAuthorize(ADMIN)
Basic Auth:
Username
Password
-------------------------------
EmployeeController:
18. POST:localhost:8080/bokhandel/api/v1/employee/signup
{
"firstName": "Biniam",
"lastName": "Andersson",
"address": "Street 65",
"phone": "987654356",
"username": "BIAN",
"email": "biniam.andersson@bokhandel.se",
"password": "Geeks@portal2012a"
}
--------------------------------------
19. PUT:localhost:8080/bokhandel/api/v1/employee/update
PreAuthorize(ADMIN)
Basic Auth:
Username
Password
{
"id":1,
"firstName":"biniam",
"lastName":"andersson",
"address":"överklassgata 10",
"phone":"12345678",
"username":"biniam_79",
"email":"biniam@gmail.com",
"password":"password_90"
}
-------------------------------
20. DELETE:localhost:8080/bokhandel/api/v1/employee/deleteEmployee/{id}
PreAuthorize(ADMIN)
Basic Auth:
Username
Password
-------------------------------
21. GET:localhost:8080/bokhandel/api/v1/employee/getListOfCustomer
PreAuthorize(ADMIN)
Basic Auth:
Username
Password
-------------------------------
22. GET:localhost:8080/bokhandel/api/v1/employee/getListOfEmployee
PreAuthorize(ADMIN)
Basic Auth:
Username
Password
-------------------------------
23. GET:localhost:8080/bokhandel/api/v1/employee/searchEmployee/{id}
PreAuthorize(ADMIN)
Basic Auth:
Username
Password
-------------------------------
24. GET:localhost:8080/bokhandel/api/v1/employee/searchCustomer/{id}
PreAuthorize(ADMIN)
Basic Auth:
Username
Password
-------------------------------
OrdersController:
25. POST:localhost:8080/bokhandel/api/v1/createorder/userid/{userid}
{
"orderDate" :"29/12/21",
"shippingMethod":"Dhl",
"shippingAddress":"styrbjörnsvägen 14",
"payment":
{
"bankName":"swedbank",
"cardNumber":"1253647889",
"expiryMonth":6,
"expiryYear":2025,
"cvc":123,
"holderName":"Biniam Haile"
}
}
------------------------------------
26. PUT:localhost:8080/bokhandel/api/v1/createorder/updateorder
PreAuthorize(ADMIN)
Basic Auth:
Username
Password
{
"id":10,
"orderDate" :"13/01/2022",
"shippingMethod":"Postnord",
"shippingAddress":"styrbjörnsvägen 14",
"payment":
{
"id":11,
"bankName":"swedbank",
"cardNumber":"1253647889",
"expiryMonth":6,
"expiryYear":2025,
"cvc":123,
"holderName":"Biniam Haile"
}
}
-------------------------------
27. DELETE:localhost:8080/bokhandel/api/v1/createorder/deleteorder/{id}
PreAuthorize(ADMIN)
Basic Auth:
Username
Password
-------------------------------
28. GET:localhost:8080/bokhandel/api/v1/createorder/getorder/{id}
29. GET:localhost:8080/bokhandel/api/v1/createorder/getallorders
PreAuthorize(ADMIN)
Basic Auth:
Username
Password
-------------------------------
PublisherController:
30. PUT:localhost:8080/bokhandel/api/v1/publisher/updatepublisher
PreAuthorize(ADMIN)
Basic Auth:
Username
Password
{
"id": 4,
"name": "AlfabetaDelta"
}
-------------------------------
31. GET:localhost:8080/bokhandel/api/v1/publisher/getlistofpublishers
PreAuthorize(ADMIN)
Basic Auth:
Username
Password
-------------------------------
32. GET:localhost:8080/bokhandel/api/v1/publisher/getpublisherbyid/{id}
PreAuthorize(ADMIN)
Basic Auth:
Username
Password
-------------------------------
33. GET:localhost:8080/bokhandel/api/v1/publisher/getpublisherbyname/{name}
-------------------------------
ShoppingCartController:
34. PUT:localhost:8080/bokhandel/api/v1/shoppingcart/addbooks/bookid/{bookid}/username/{username}/{qty}
---------------------------------
35. PUT:localhost:8080/bokhandel/api/v1/shoppingcart/updateCartItem/{cartid}/quantity/{qty}
---------------------------------
36. PUT:localhost:8080/bokhandel/api/v1/shoppingcart/removeBookFromCart/{cartid}/quantity/{qty}
----------------------------------
37. DELETE:localhost:8080/bokhandel/api/v1/shoppingcart/removecartItem/{id}
--------------------------------
StockController:
38. PUT:localhost:8080/bokhandel/api/v1/stock/updatestock
{
"id": 4,
"quantity": 250,
"inStock": true
}
39. GET:localhost:8080/bokhandel/api/v1/stock/getstockbyid/{id}
40. GET:localhost:8080/bokhandel/api/v1/stock/getAllStocks
41. DELETE:localhost:8080/bokhandel/api/v1/stock/removeStock/{id}
PreAuthorize(ADMIN)
Basic Auth:
Username
Password
-------------------------------
How to use API request:
1. Create Customer with API call nr 15. Register with your own email address to get signup confirmation email and order confirmation.
2. Create Employee with API call nr 18.
3. Add one or several books to the database with API call nr 7.
4. Add desired copies of book to a customers cartItem with API call nr 34.
5. Increase amount of copies in a customers cartItem with API call nr 35.
6. Decrease amount of copies in a customers cartItem with API call nr 36.
7. Remove book from the cartItem with API call nr 37.
8. Create order for all books in the shoppingCart with API call nr 25.
9. Done.