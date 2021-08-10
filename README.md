# Product API
The system Java spring boot run on AWS

##Technical
```
1. Language: Java 8
2. Framework: Spring Boot(Use JPA connect DB, JWT authenticate API)
3. Mysql version 8.0
4. AWS: With server EC2, DB RDS Mysql and S3 contain file jar
```

##Tool
```
1. IntelliJIDEA: Codes Java
2. Mysql workbench: Checks database
```

## Getting started for applicants

API endpoints:

1. `POST /register` - registers user(To generate token).
2. `POST /authenticate` - gets token with user.
3. `GET /products` - gets all products.
4. `GET /products?name={name}` - finds all products matching the specified name.
5. `GET /products/{id}` - gets the project that matches the specified ID - ID is a GUID.
6. `POST /products` - creates a new product.
7. `PUT /products/{id}` - updates a product.
8. `DELETE /products/{id}` - deletes a product and its options.
9. `GET /products/{id}/options` - finds all options for a specified product.
10. `GET /products/{id}/options/{optionId}` - finds the specified product option for the specified product.
11. `POST /products/{id}/options` - adds a new product option to the specified product.
12. `PUT /products/{id}/options/{optionId}` - updates the specified product option.
13. `DELETE /products/{id}/options/{optionId}` - deletes the specified product option.

## Introduction source code
```
1. common - contains Constant and error/message handler
2. controllers - contains actions
3. models - contains table
4. payload - checks login information and responses token
5. repositories - queries SQL
6. security - checks authentication
7. services - contains services
8. validations - validates
9. resources/application.properties - config database
10. resources/message.properties - config message
```

##Deploy to EC2, RDS and S3
1. Cd to root project create file jar
```
1. cd productAPI
2. install maven: brew install maven
3. create file jar: mvn clean install
   create success: /target/spring-boot-product-api-aws-exe.jar
```

2. Upload file jar to S3 
```
1. Create folder and make public
2. Upload file jar
3. Copy Object URL: ex https://yourbucketname.s3.ap-southeast-2.amazonaws.com/spring-boot-product-api-aws-exe.jar
```

3. Create MySQL
```
1. Access RDS and create MySQL
2. Version 8
3. Set username and passord
4. Setting security allow port MYSQL/Aurora	TCP	3306	0.0.0.0/0
                          and  sgr-0e2f5d200e82b3d07	–	All traffic	All	All (access from ec2)
5. Copy Endpoint: product.cmka8gqpfjlr.ap-northeast-1.rds.amazonaws.com
6. User database workbench access from local
```

4. Create EC2
```
1. Create Ec2 Linux 64-bit (x86)
2. Create new key use to access from local and deploy
3. Download file .pem
```

5. Login Ec2 and deploy
```
1. Open teminal
2. cd to folder contain file .pem
3. Change permit: chmod 400 yourfilename.pem
4. Login
   ssh -i "spring-boot-api.pem" ec2-user@ec2-3-112-40-236.ap-northeast-1.compute.amazonaws.com
5. move to root folder: sudo -i
6. check version java: java -version
We use java 1.8, so you should install 
7. Install java 1.8: sudo yum install java-1.8.0
8. Change defalt version: alternatives --config java
   enter number to choose version
9. Copy file jar from S3 to Ec2
   wget https://productxero.s3.ap-southeast-2.amazonaws.com/spring-boot-product-api-aws-exe.jar
10. Deploy: java -jar pring-boot-product-api-aws-exe.jar
*Default java use port 8080, make sure you set security
ex: add: sgr-0e86e0d77b86ce95f	IPv4	Custom TCP	TCP	8080

11. Success: copy url ec2-3-112-40-236.ap-northeast-1.compute.amazonaws.com and + port  8080 
ex: ec2-3-112-40-236.ap-northeast-1.compute.amazonaws.com:8080
```

##Test API
Token has been setting 300s at file productAPI/src/main/java/au/xero/product/common/Constant.java EXPIRATION_TIME
1. `POST /register` - registers user(To generate token).
```
POST http://localhost:8080/register
body 
{
    "fullName": "Dat Nguyen",//Not null
    "username": "danguyen",//Not null, not duplication
    "password": "123456",//Not null and lenght is less 6 character
    "confirmPassword": "123456"// Confirm Password must match with Passwords
}
```
2. `POST /authenticate` - gets token with user.
```
POST http://localhost:8080/authenticate
body 
{
    "username": "danguyen",//Not null
    "password": "123456"//Not null
}
```
3. `GET /products` - gets all products.
```
GET http://localhost:8080/products
Headers 
{
    "Authorization": Bearer eyJhbGciOiJIUzUxMiJ9.eyJmdWxsTmFtZ
}
```
4. `GET /products?name={name}` - finds all products matching the specified name.
```
GET http://localhost:8080/products?name=name product
Headers 
{
    "Authorization": Bearer eyJhbGciOiJIUzUxMiJ9.eyJmdWxsTmFtZ
}
```
5. `GET /products/{id}` - gets the project that matches the specified ID - ID is a GUID.
```
GET http://localhost:8080/products/173b9526-52ad-4085-b617-09c487df57fb
Headers 
{
    "Authorization": Bearer eyJhbGciOiJIUzUxMiJ9.eyJmdWxsTmFtZ
}
```
6. `POST /products` - creates a new product.
```
POST http://localhost:8080/products
Headers 
{
    "Authorization": Bearer eyJhbGciOiJIUzUxMiJ9.eyJmdWxsTmFtZ
}
body 
{
    "name": "test create 1",//Not mull
    "description": "description",
    "price": 10,
    "deliveryPrice": 2000.00
}
```
7. `PUT /products/{id}` - updates a product.
```
PUT http://localhost:8080/products/a0c44f7f-991f-4d2a-9512-4ebdf3b7402c
Headers 
{
    "Authorization": Bearer eyJhbGciOiJIUzUxMiJ9.eyJmdWxsTmFtZ
}
body 
{
    "name": "test create 1",//Not mull
    "description": "description",
    "price": 10,
    "deliveryPrice": 2000.00
}
```
8. `DELETE /products/{id}` - deletes a product and its options.
```
DELETE http://localhost:8080/products/a0c44f7f-991f-4d2a-9512-4ebdf3b7402c
Headers 
{
    "Authorization": Bearer eyJhbGciOiJIUzUxMiJ9.eyJmdWxsTmFtZ
}
```


9. `GET /products/{id}/options` - finds all options for a specified product.
```
GET http://localhost:8080/products/29f14b3b-1730-46a5-b492-f68a3681b66c/options/
Headers 
{
    "Authorization": Bearer eyJhbGciOiJIUzUxMiJ9.eyJmdWxsTmFtZ
}
```
10. `GET /products/{id}/options/{optionId}` - finds the specified product option for the specified product.
```
GET http://localhost:8080/products/29f14b3b-1730-46a5-b492-f68a3681b66c/options/022a721c-9508-406c-8362-fefdcd0a0d6c
Headers 
{
    "Authorization": Bearer eyJhbGciOiJIUzUxMiJ9.eyJmdWxsTmFtZ
}
```
11. `POST /products/{id}/options` - adds a new product option to the specified product.
```
POST http://localhost:8080/products/29f14b3b-1730-46a5-b492-f68a3681b66c/options
Headers 
{
    "Authorization": Bearer eyJhbGciOiJIUzUxMiJ9.eyJmdWxsTmFtZ
}
body 
{
    "name": "option name",
    "description": "description"
}
```
12. `PUT /products/{id}/options/{optionId}` - updates the specified product option.
```
PUT http://localhost:8080/products/29f14b3b-1730-46a5-b492-f68a3681b66c/options/022a721c-9508-406c-8362-fefdcd0a0d6c
Headers 
{
    "Authorization": Bearer eyJhbGciOiJIUzUxMiJ9.eyJmdWxsTmFtZ
}
body 
{
    "name": "option name edit",
    "description": "description"
}
```
13. `DELETE /products/{id}/options/{optionId}` - deletes the specified product option.
```
DELETE http://localhost:8080/products/29f14b3b-1730-46a5-b492-f68a3681b66c/options/022a721c-9508-406c-8362-fefdcd0a0d6c
Headers 
{
    "Authorization": Bearer eyJhbGciOiJIUzUxMiJ9.eyJmdWxsTmFtZ
}
body 
```