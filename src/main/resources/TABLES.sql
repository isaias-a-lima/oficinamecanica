--2024-06-10 01:25 - Portugal - Isaias Lima - Create mechanical_workshops database.
CREATE DATABASE mechanical_workshops;

--2024-06-10 01:30 - Portugal - Isaias Lima - Create users table.
CREATE TABLE users(
cpf BIGINT NOT NULL COMMENT 'User personal identification tax document',
name VARCHAR(100) NOT NULL COMMENT 'System full user name',
email VARCHAR(100) NOT NULL COMMENT 'System user email',
password VARCHAR(255) NOT NULL COMMENT 'System user password',
active BOOL COMMENT 'Identify if is an active user',
PRIMARY KEY(cpf)
)COMMENT = 'System users registration.';

--2024-06-10 01:35 - Portugal - Isaias Lima - Populate users table.
INSERT INTO users VALUES (11122233344,'Isaias Lima','isaias@teste.com','$2a$12$ThlKj4/EVxb2eJ3/Ahq4dOQpTJCOPhoEUmC3Ktk7nK3mpTTNdh5.m',true);

--2024-06-10 01:40 - Portugal - Isaias Lima - Create workshop table.
CREATE TABLE workshops(
workshopid BIGINT NOT NULL AUTO_INCREMENT COMMENT 'Workshop identification number',
cpf BIGINT NOT NULL COMMENT 'User personal identification tax document',
name VARCHAR(100) NOT NULL COMMENT 'Workshop name',
docid BIGINT NULL COMMENT 'Workshop identification document number',
image MEDIUMBLOB NULL COMMENT 'Image that contains a workshop logo',
active BOOL COMMENT 'Identify if is an active workshop',
PRIMARY KEY(workshopid),
CONSTRAINT fk_users_workshops FOREIGN KEY (cpf)
REFERENCES users(cpf)
)COMMENT = 'Mechanical workshops registration, related with system users.';

--2024-06-10 01:45 - Portugal - Isaias Lima - Populate workshops table.
INSERT INTO workshops VALUES(1,11122233344, 'Auto SB', 22333555000123,null, true);

--2024-06-10 01:50 - Portugal - Isaias Lima - Create customers table.
CREATE TABLE customers(
customerid BIGINT NOT NULL AUTO_INCREMENT COMMENT 'Customer identification number',
workshopid BIGINT NOT NULL COMMENT 'Workshop identification number',
docid BIGINT NULL COMMENT 'Customer personal identification tax document',
name VARCHAR(100) NOT NULL COMMENT 'Customer full name',
landline VARCHAR(30) NULL COMMENT 'Landline number',
mobilephone VARCHAR(30) NULL COMMENT 'Mobile phone number',
email VARCHAR(100) COMMENT 'Customer email address',
type CHAR(1) COMMENT 'Kind of customer. Available values: F for physical person, or J for company',
PRIMARY KEY(customerid),
CONSTRAINT fk_workshops_customers FOREIGN KEY (workshopid)
REFERENCES workshops(workshopid)
)COMMENT = 'Customers registration';

--2024-06-14 00:12 - Portugal - Isaias Lima - Populate customers table
INSERT INTO customers VALUES(1, 1, 55566677788, 'Marcio Brigido', '+55 11 2222-3333', '+55 11 93333-4444', 'mb@teste.com', 'F');

--2024-06-18 16:13 - Brazil - Mateus Lima - create services table
CREATE TABLE services(
serviceid BIGINT NOT NULL AUTO_INCREMENT COMMENT 'Service identification number',
workshopid BIGINT NOT NULL COMMENT 'Workshop Identification number',
description VARCHAR(255) NOT NULL COMMENT 'Service description',
cost DECIMAL(7,2) NOT NULL COMMENT 'Service cost',
PRIMARY KEY(serviceid),
CONSTRAINT fk_workshops_services FOREIGN KEY (workshopid)
REFERENCES workshops(workshopid)
)COMMENT = 'Services registration';

--2024-07-05 01:13 - Portugal - Isaias Lima - Recreate customers table.
DROP TABLE customers;

--2024-07-05 01:13 - Portugal - Isaias Lima - Recreate customers table.
CREATE TABLE customers(
workshopid BIGINT NOT NULL COMMENT 'Workshop identification number',
docid VARCHAR(14) NOT NULL COMMENT 'Customer personal identification tax document',
name VARCHAR(100) NOT NULL COMMENT 'Customer full name',
landline VARCHAR(30) NULL COMMENT 'Landline number',
mobilephone VARCHAR(30) NULL COMMENT 'Mobile phone number',
email VARCHAR(100) COMMENT 'Customer email address',
type CHAR(1) COMMENT 'Kind of customer. Available values: F for physical person, or J for company',
PRIMARY KEY(workshopid, docid),
CONSTRAINT fk_workshops_customers FOREIGN KEY (workshopid)
REFERENCES workshops(workshopid)
)COMMENT = 'Customers registration';

--2024-07-05 01:13 - Portugal - Isaias Lima - Recreate customers table.
INSERT INTO customers VALUES(1, '55566677788', 'Marcio Brigido', '+55 11 2222-3333', '+55 11 93333-4444', 'mb@teste.com', 'F');
INSERT INTO customers VALUES(1, '22233344455', 'Leandro Silva', '+55 11 3333-4444', '+55 11 93333-4444', 'ls@teste.com', 'F');

--2024-07-12 16:08 - Brazil - Mateus Lima - Recreate services table.
DROP TABLE services;

CREATE TABLE services(
serviceid INT NOT NULL COMMENT 'Service identification number',
workshopid BIGINT NOT NULL COMMENT 'Workshop Identification number',
description VARCHAR(255) NOT NULL COMMENT 'Service description',
cost DECIMAL(7,2) NOT NULL COMMENT 'Service cost',
PRIMARY KEY(serviceid, workshopid),
CONSTRAINT fk_workshops_services FOREIGN KEY (workshopid)
REFERENCES workshops(workshopid)
)COMMENT = 'Services registration';


