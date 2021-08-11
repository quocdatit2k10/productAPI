# "Product API" Project
CRUD system for product, product option and user.
This system uses Java spring boot and runs on AWS.

## Technical Specs
```
1. Language: Java 8
2. Framework: Spring Boot(Use JPA connect DB, JWT authenticate API)
3. DB: Mysql version 8.0
4. Infra: AWS 
Server: EC2 
DB: RDS Mysql 
S3 contains jar file
```

## Tools
```
1. Code Java: IntelliJIDEA
2. Check database: Mysql workbench
3. Deploy to EC2: Terminal
```

## GitHub
https://github.com/quocdatit2k10/productAPI/tree/master

## Getting started for application

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

## Source code
```
1. main/../common - contains Constant and error/message handler
2. main/../controllers - contains actions
3. main/../models - contains table
4. main/../payload - checks login information and responses token
5. main/../repositories - queries SQL
6. main/../security - checks authentication
7. main/../services - contains services
8. main/../validations - validates
9. resources/application.properties - config database
10. resources/message.properties - config message
11. sql - contains file sql
```

## Deploy to EC2, RDS and S3
1. Move to root project to create jar file
```
1. cd productAPI
2. install maven: brew install maven
3. create file jar: mvn clean install
   create success: /target/spring-boot-product-api-aws-exe.jar
```

2. Upload jar file to S3 
```
1. Create folder and make public
2. Upload jar file
3. Copy Object URL: ex https://yourbucketname.s3.ap-southeast-2.amazonaws.com/spring-boot-product-api-aws-exe.jar
```

3. Create MySQL
```
1. Access RDS and create MySQL
2. Version 8
3. Set username and password
4. Security settings 
 allow port MYSQL/Aurora	TCP	3306	0.0.0.0/0, and 
 sgr-0e2f5d200e82b3d07	–	All traffic	All	All (access from ec2)
5. Copy Endpoint: product.cmka8gqpfjlr.ap-northeast-1.rds.amazonaws.com
6. User database workbench access from local
```

4. Create EC2
```
1. Create Ec2 Linux 64-bit (x86)
2. Create new key use to access from local and deploy
3. Download pem file
```

5. EC2 login and deploy
```
1. Open teminal tool
2. cd to folder contain pem file
3. Change permit: chmod 400 yourfilename.pem
4. Login
   ssh -i "spring-boot-api.pem" ec2-user@ec2-3-112-40-236.ap-northeast-1.compute.amazonaws.com
5. move to root folder: sudo -i
6. check java version: java -version
We use java 1.8, so you should install 
7. Install java 1.8: sudo yum install java-1.8.0
8. Change default version: alternatives --config java
   enter number to choose version
9. Copy jar file from S3 to Ec2
   wget https://productxero.s3.ap-southeast-2.amazonaws.com/spring-boot-product-api-aws-exe.jar
10. Deploy: java -jar pring-boot-product-api-aws-exe.jar
*By default java use port 8080, make sure you have correct security settings
ex: add: sgr-0e86e0d77b86ce95f	IPv4	Custom TCP	TCP	8080

11. Success: copy url ec2-3-112-40-236.ap-northeast-1.compute.amazonaws.com and + port  8080 
ex: ec2-3-112-40-236.ap-northeast-1.compute.amazonaws.com:8080
```

## Database
**Product:**

```
{
    "id": "761cc07b-aead-4551-8d87-8f50752d162f",
    "name": "test full",
    "description": "description",
    "price": 10.00,
    "deliveryPrice": 2000.00,
    "created_At": "2021-08-09T22:51:05.337+00:00",
    "updated_At": "2021-08-09T22:51:05.337+00:00"
}
```

**ProductOption:**
```
{
    "id": "a8376cef-3c59-4e92-9fb5-9d678e9ec274",
    "productId": "761cc07b-aead-4551-8d87-8f50752d162f",
    "name": "option name1",
    "description": "description",
    "created_At": "2021-08-10T12:02:12.382+00:00",
    "updated_At": "2021-08-09T22:51:05.337+00:00"
}
```
**User:**
```
{
    "id": 3,
    "username": "danguyen",
    "fullName": "Dat Nguyen",
    "password": "$2a$10$zm1vsUfrbuPTTPfy/a8qu.k0RnygzoplMwmnazyAvl7.kMMotoGGG",
    "confirmPassword": "",
    "create_At": "2021-08-10T22:49:49.612+00:00",
    "updated_At": "2021-08-09T22:51:05.337+00:00"
}
```

## Test API
Token setting: 3000s at file productAPI/src/main/java/au/xero/product/common/Constant.java EXPIRATION_TIME

1. `POST /register` - registers user(To generate token).
```
POST http://localhost:8080/register
body 
{
    "fullName": "Dat Nguyen",
    "username": "danguyen",
    "password": "123456",
    "confirmPassword": "123456"

Validation:
    fullName: Not null
    username: Not null, not duplication
    password: Not null and length is less 6 character
    confirmPassword: Confirm Password must match with Passwords
    
Response
{
    "data": {
        "id": 4,
        "username": "danguyen",
        "fullName": "Dat Nguyen",
        "password": "$2a$10$1t1/7fKtQnbiEoKae9PDa.3B8aqN67RGxwyLAuUkx/xyRPy4tEROu",
        "confirmPassword": "",
        "create_At": "2021-08-11T05:34:02.523+00:00",
        "update_At": null
    },
    "message": "Successfully created"
}
```
2. `POST /authenticate` - gets token with user.
```
POST http://localhost:8080/authenticate
body 
{
    "username": "danguyen",
    "password": "123456"
}

Validation
    username: Not null
    password: Not null
    username and passord must correct

Response
{
    "success": true,
    "token": "Bearer eyJhbGciOiJIUzUxMiJ9.eyJmdWxsTmFtZSI6IkRhdCBOZ3V5ZW4iLCJpZCI6IjQiLCJleHAiOjE2Mjg2NjM3OTksImlhdCI6MTYyODY2MDc5OSwidXNlcm5hbWUiOiJkYW5ndXllbiJ9.Ga9X7TP2UYnTRqN0EFYN3Fjl2ud1lCDuauMmnjrdxoBZCMFUhH8OXLbqBXgPKqMqtFxQU1V7pQ8uGrm844kmNQ"
}
```
3. `GET /products` - gets all products.
```
GET http://localhost:8080/products
Headers 
{
    "Authorization": Bearer eyJhbGciOiJIUzUxMiJ9.eyJmdWxsTmFtZ
}

Validation
    Check token
    Check data is exist
    
Response
{
    "data": [
        {
            "id": "761cc07b-aead-4551-8d87-8f50752d162f",
            "name": "test full",
            "description": "description",
            "price": 10.00,
            "deliveryPrice": 2000.00,
            "created_At": "2021-08-09T22:51:05.337+00:00",
            "updated_At": null
        },
        {
            "id": "7e0b2315-9eba-4177-b1cd-b0062b18b95e",
            "name": "test full 1",
            "description": "description",
            "price": 10.00,
            "deliveryPrice": 2000.00,
            "created_At": "2021-08-09T22:50:46.825+00:00",
            "updated_At": null
        }
    ]
}
```
4. `GET /products?name={name}` - finds all products matching the specified name.
```
GET http://localhost:8080/products?name=test full 1
Headers 
{
    "Authorization": Bearer eyJhbGciOiJIUzUxMiJ9.eyJmdWxsTmFtZ
}

Validation
    Check token
    Check data is exist
    
Response
{
    "data": [
        {
            "id": "7e0b2315-9eba-4177-b1cd-b0062b18b95e",
            "name": "test full 1",
            "description": "description",
            "price": 10.00,
            "deliveryPrice": 2000.00,
            "created_At": "2021-08-09T22:50:46.825+00:00",
            "updated_At": null
        }
    ]
}
```
5. `GET /products/{id}` - gets the project that matches the specified ID - ID is a GUID.
```
GET http://localhost:8080/products/7e0b2315-9eba-4177-b1cd-b0062b18b95e
Headers 
{
    "Authorization": Bearer eyJhbGciOiJIUzUxMiJ9.eyJmdWxsTmFtZ
}

Validation
    Check token
    Check data is exist
    
Response
{
    "data": {
        "id": "7e0b2315-9eba-4177-b1cd-b0062b18b95e",
        "name": "test full 1",
        "description": "description",
        "price": 10.00,
        "deliveryPrice": 2000.00,
        "created_At": "2021-08-09T22:50:46.825+00:00",
        "updated_At": null
    }
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
    "name": "test create 1",
    "description": "description",
    "price": 10,
    "deliveryPrice": 2000.00
}

Validation
    Check token
    name: Not null
    
Response
{
    "data": {
        "id": "9a153329-89dd-4dcd-ab77-e36b15b4f158",
        "name": "test create 1",
        "description": "description",
        "price": 10.00,
        "deliveryPrice": 2000.00,
        "created_At": "2021-08-11T06:06:58.194+00:00",
        "updated_At": null
    },
    "message": "Successfully created"
}
```
7. `PUT /products/{id}` - updates a product.
```
PUT http://localhost:8080/products/9a153329-89dd-4dcd-ab77-e36b15b4f158
Headers 
{
    "Authorization": Bearer eyJhbGciOiJIUzUxMiJ9.eyJmdWxsTmFtZ
}
body 
{
    {
        "name": "test create edit",
        "description": "description edit",
        "price": 20.00,
        "deliveryPrice": 3000.00
    }
}
Validation
    Check token
    name: Not null
    Check format id
    
Response
{
    "data": {
        "id": "9a153329-89dd-4dcd-ab77-e36b15b4f158",
        "name": "test create edit",
        "description": "description edit",
        "price": 20.00,
        "deliveryPrice": 3000.00,
        "created_At": null,
        "updated_At": "2021-08-11T06:12:03.780+00:00"
    },
    "message": "Product with ID: (9a153329-89dd-4dcd-ab77-e36b15b4f158) has been updated"
}
```
8. `DELETE /products/{id}` - deletes a product and its options.
```
DELETE http://localhost:8080/products/9a153329-89dd-4dcd-ab77-e36b15b4f158
Headers 
{
    "Authorization": Bearer eyJhbGciOiJIUzUxMiJ9.eyJmdWxsTmFtZ
}

Validation
    Check token
    Check product id is exist
    Check format id
    
Response
{
    "message": "Product with ID: (29f14b3b-1730-46a5-b492-f68a3681b66c) has been deleted"
}
```




9. `GET /products/{id}/options` - finds all options for a specified product.
```
GET http://localhost:8080/products/9a153329-89dd-4dcd-ab77-e36b15b4f158/options
Headers 
{
    "Authorization": Bearer eyJhbGciOiJIUzUxMiJ9.eyJmdWxsTmFtZ
}

Validation
    Check token
    Check product id is exist
    Check format id
    
Response
{
    "data": [
        {
            "id": "327d7428-1cf2-4078-be6b-5267c565148a",
            "productId": "9a153329-89dd-4dcd-ab77-e36b15b4f158",
            "name": "option name",
            "description": "description",
            "created_At": "2021-08-11T06:18:28.855+00:00",
            "updated_At": null
        },
        {
            "id": "cfb81911-2d94-4104-a2e6-7db898dc8992",
            "productId": "9a153329-89dd-4dcd-ab77-e36b15b4f158",
            "name": "option name2",
            "description": "description",
            "created_At": "2021-08-11T06:19:44.585+00:00",
            "updated_At": null
        }
    ]
}
```
10. `GET /products/{id}/options/{optionId}` - finds the specified product option for the specified product.
```
GET http://localhost:8080/products/9a153329-89dd-4dcd-ab77-e36b15b4f158/options/327d7428-1cf2-4078-be6b-5267c565148a
Headers 
{
    "Authorization": Bearer eyJhbGciOiJIUzUxMiJ9.eyJmdWxsTmFtZ
}

Validation
    Check token
    Check product id is exist
    check product option id is exist
    Check format id
    
Response
{
    "data": {
        "id": "327d7428-1cf2-4078-be6b-5267c565148a",
        "productId": "9a153329-89dd-4dcd-ab77-e36b15b4f158",
        "name": "option name",
        "description": "description",
        "created_At": "2021-08-11T06:18:28.855+00:00",
        "updated_At": null
    }
}
```
11. `POST /products/{id}/options` - adds a new product option to the specified product.
```
POST http://localhost:8080/products/9a153329-89dd-4dcd-ab77-e36b15b4f158/options
Headers 
{
    "Authorization": Bearer eyJhbGciOiJIUzUxMiJ9.eyJmdWxsTmFtZ
}
body 
{
    "name": "option name",
    "description": "description"
}

Validation
    Check token
    name: Not null
    Check product Id is exist
    check format id

Response
{
    "data": {
        "id": "327d7428-1cf2-4078-be6b-5267c565148a",
        "productId": "9a153329-89dd-4dcd-ab77-e36b15b4f158",
        "name": "option name",
        "description": "description",
        "created_At": "2021-08-11T06:18:28.855+00:00",
        "updated_At": null
    },
    "message": "Successfully created"
}
```
12. `PUT /products/{id}/options/{optionId}` - updates the specified product option.
```
PUT http://localhost:8080/products/9a153329-89dd-4dcd-ab77-e36b15b4f158/options/327d7428-1cf2-4078-be6b-5267c565148a
Headers 
{
    "Authorization": Bearer eyJhbGciOiJIUzUxMiJ9.eyJmdWxsTmFtZ
}
body 
{
    "name": "option name edit",
    "description": "description edit"
}

Validation
    Check token
    name: Not null
    Check product Id is exist
    Check product id is exist
    check format id

Response
{
    "data": {
        "id": "327d7428-1cf2-4078-be6b-5267c565148a",
        "productId": "9a153329-89dd-4dcd-ab77-e36b15b4f158",
        "name": "option name edit",
        "description": "description edit",
        "created_At": null,
        "updated_At": "2021-08-11T06:19:28.855+00:00"
    },
    "message": "Successfully created"
}
```
13. `DELETE /products/{id}/options/{optionId}` - deletes the specified product option.
```
DELETE http://localhost:8080/products/9a153329-89dd-4dcd-ab77-e36b15b4f158/options/327d7428-1cf2-4078-be6b-5267c565148a
Headers 
{
   "Authorization": Bearer eyJhbGciOiJIUzUxMiJ9.eyJmdWxsTmFtZ
}
       
Validation
   Check token
   name: Not null
   Check product Id is exist
   Check product id is exist
   check format id

Response
{
    "message": "Product option with ID: (327d7428-1cf2-4078-be6b-5267c565148a) has been deleted"
}
```