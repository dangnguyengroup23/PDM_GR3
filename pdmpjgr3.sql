
CREATE DATABASE bankSystem;

USE bankSystem;



CREATE TABLE Customer (
    form_no VARCHAR(30) PRIMARY KEY, 
    name VARCHAR(30),
    father_name VARCHAR(30),
    DOB VARCHAR(30),
    gender VARCHAR(30),
    email VARCHAR(60),
    marital_status VARCHAR(30),
    address VARCHAR(60),
    city VARCHAR(30),
    pincode VARCHAR(30),
    state VARCHAR(50)
);



CREATE TABLE CustomerDetails (
    form_no VARCHAR(30) PRIMARY KEY,  
    religion VARCHAR(30),
    income VARCHAR(30),
    education VARCHAR(30),
    occupation VARCHAR(30),
    pan VARCHAR(30),
    aadhar VARCHAR(60),
    seniorcitizen VARCHAR(30),
    existing VARCHAR(30),
    FOREIGN KEY (form_no) REFERENCES Customer(form_no)
    
);


CREATE TABLE Account (
    form_no VARCHAR(30) PRIMARY KEY, 
    account_type VARCHAR(40),
    card_number VARCHAR(30) UNIQUE,
    pin VARCHAR(10) UNIQUE ,
    facility VARCHAR(200),
    balance DOUBLE,
    FOREIGN KEY (form_no) REFERENCES Customer(form_no) ON DELETE CASCADE
   
);


CREATE TABLE Login (
    form_no VARCHAR(30),
    card_number VARCHAR(30),
    pin VARCHAR(10),
    FOREIGN KEY (form_no) REFERENCES Customer(form_no) ON DELETE CASCADE,
    FOREIGN KEY (card_number) REFERENCES Account(card_number) ON DELETE CASCADE
    
);

use bankSystem;  
CREATE TABLE Bank (
    transaction_id VARCHAR(30)  PRIMARY KEY,
    card_number VARCHAR(30),
    pin VARCHAR(10),
    date VARCHAR(100),
    type VARCHAR(20),
	amount  DOUBLE  ,
    interest VARCHAR(10) null,
    FOREIGN KEY (card_number) REFERENCES Account(card_number) ON DELETE CASCADE
-- 	FOREIGN KEY (pin) REFERENCES Account(pin) ON DELETE CASCADE
   
);

use bankSystem;
SELECT * FROM Customer;
Use bankSystem;
SELECT * FROM CustomerDetails;
Use bankSystem;
SELECT * FROM Account  ;
Use bankSystem;
SELECT * FROM Login;
Use bankSystem;
SELECT * FROM Bank order by date desc;

