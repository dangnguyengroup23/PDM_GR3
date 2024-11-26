
CREATE DATABASE bankSystem;

USE bankSystem;



-- Entity: Customer (Primary Table)
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

-- Table 2: signuptwo (renamed to CustomerDetails)
-- Entity: Customer Details

CREATE TABLE CustomerDetails (
    form_no VARCHAR(30) PRIMARY KEY,  -- Primary key for one-to-one relationship
    religion VARCHAR(30),
    category VARCHAR(30),
    income VARCHAR(30),
    education VARCHAR(30),
    occupation VARCHAR(30),
    pan VARCHAR(30),
    aadhar VARCHAR(60),
    seniorcitizen VARCHAR(30),
    existing VARCHAR(30),
    FOREIGN KEY (form_no) REFERENCES Customer(form_no)
    -- Each customer in `Customer` has only one corresponding set of details in `CustomerDetails`.
);

-- Table 3: signupthree (renamed to Account)
-- Entity: Account
-- Relationship: OWNS (One-to-One with Customer)
CREATE TABLE Account (
    form_no VARCHAR(30) PRIMARY KEY,  -- Primary key for one-to-one relationship with Customer
    account_type VARCHAR(40),
    card_number VARCHAR(30) UNIQUE,
    pin VARCHAR(10) UNIQUE,
    facility VARCHAR(200),
    FOREIGN KEY (form_no) REFERENCES Customer(form_no)
    -- Each customer in `Customer` has only one corresponding account in `Account`.
);

-- Table 4: login (Login Information)
-- Entity: Login Information
-- Relationships:
--   - AUTHORIZED_BY (One-to-One with Account)
--   - HAS_LOGIN (One-to-One with Customer)
CREATE TABLE Login (
    form_no VARCHAR(30),
    card_number VARCHAR(30),
    pin VARCHAR(10),
    FOREIGN KEY (form_no) REFERENCES Customer(form_no),
    FOREIGN KEY (card_number) REFERENCES Account(card_number)
    -- Each account in `Account` is associated with one set of login credentials in `Login`.
    -- Each customer in `Customer` is associated with one set of login credentials in `Login`.
);

use bankSystem;  
CREATE TABLE Bank (
    transaction_id VARCHAR(30)  PRIMARY KEY,
    card_number VARCHAR(30),
    pin VARCHAR(10),
    date VARCHAR(100),
    type VARCHAR(20),
	amount  VARCHAR(20) ,
    interest VARCHAR(10) null,
    FOREIGN KEY (pin) REFERENCES Account(pin)
    -- Each account in `Account` can have multiple transactions in `Transaction`.
);

-- use bankSystem;
-- CREATE TABLE Loan (   
-- 	loan_id VARCHAR(30)  PRIMARY KEY,
-- 	pin VARCHAR(10) , 
-- 	card_number VARCHAR(30),
-- 	amount  VARCHAR(20),   
-- 	interest VARCHAR(10),  
-- 	date VARCHAR(100), 
-- 	FOREIGN KEY (card_number) REFERENCES Account(card_number) ON DELETE CASCADE,
--     FOREIGN KEY (loan_id) REFERENCES Bank(transaction_id) ON DELETE CASCADE
--   );



-- Select statements to verify tables
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
-- Use bankSystem;
-- SELECT * FROM Loan;
-- Use bankSystem;
-- select * from bank where card_number = 1409963073231026;
