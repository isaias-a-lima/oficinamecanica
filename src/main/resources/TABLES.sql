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

--2024-07-25 17:41 - Brazil - Mateus Lima - Create parts table.
CREATE TABLE parts(
partid INT NOT NULL COMMENT 'Part identification number',
workshopid BIGINT NOT NULL COMMENT 'Workshop Identification number',
description VARCHAR(255) NOT NULL COMMENT 'Part description',
cost DECIMAL(7,2) NOT NULL COMMENT 'Part cost',
profit DECIMAL(7,2) NOT NULL COMMENT 'Part profit',
balance INT NOT NULL COMMENT 'Parts balance',
PRIMARY KEY(partid, workshopid),
CONSTRAINT fk_workshops_parts FOREIGN KEY (workshopid)
REFERENCES workshops(workshopid)
)COMMENT = 'Parts registration';

--2024-08-07 16:32 - Brazil - Mateus Lima - Alter parts table.
ALTER TABLE parts ADD COLUMN brand VARCHAR(100) NOT NULL COMMENT 'Part brand';

--2024-08-09 11:42 - Brazil - Mateus Lima - Create suppliers table.
CREATE TABLE suppliers(
supplierid INT NOT NULL COMMENT 'Supplier identification number',
workshopid BIGINT NOT NULL COMMENT 'Workshop Identification number',
name VARCHAR(100) NOT NULL COMMENT 'Supplier name',
landline VARCHAR(30) COMMENT 'Supplier landline number',
mobilephone VARCHAR(30) NOT NULL COMMENT 'Supplier mobilephone number',
email VARCHAR(100) NOT NULL COMMENT 'Supplier email',
postalcode VARCHAR(8) NOT NULL COMMENT 'Supplier postal code',
address VARCHAR(100) NOT NULL COMMENT 'Supplier address',
city VARCHAR(100) NOT NULL COMMENT 'Supplier city',
state VARCHAR(100) NOT NULL COMMENT 'Supplier state',
PRIMARY KEY(supplierid, workshopid),
CONSTRAINT fk_workshops_suppliers FOREIGN KEY (workshopid)
REFERENCES workshops(workshopid)
)COMMENT = 'Suppliers registration';

--2024-08-09 14:00 - Brazil - Mateus Lima - Create suppliers_parts table.
CREATE TABLE suppliers_parts(
workshopid BIGINT NOT NULL COMMENT 'Workshop identification number',
supplierid INT NOT NULL COMMENT 'Supplier identification number',
partid INT NOT NULL COMMENT 'Part identification number',
PRIMARY KEY(workshopid, supplierid, partid),
CONSTRAINT fk_workshops FOREIGN KEY (workshopid) REFERENCES workshops(workshopid),
CONSTRAINT fk_suppliers FOREIGN KEY (supplierid) REFERENCES suppliers(supplierid),
CONSTRAINT fk_parts FOREIGN KEY (partid) REFERENCES parts(partid)
)COMMENT = 'Suppliers_parts registration';

--2024-08-11 16:32 - Portugal - Isaias Lima - Alter customers table.
ALTER TABLE customers
ADD COLUMN  postalcode VARCHAR(8) NULL COMMENT 'Customer postal code',
ADD COLUMN address VARCHAR(100) NULL COMMENT 'Customer address',
ADD COLUMN city VARCHAR(100) NULL COMMENT 'Customer city',
ADD COLUMN state VARCHAR(100) NULL COMMENT 'Customer state';

--2024-08-12 15:11 - Brazil - Mateus Lima - Alter suppliers table.
ALTER TABLE suppliers
ADD COLUMN iddoc VARCHAR(14) NOT NULL COMMENT 'Supplier identification document'
AFTER workshopid;

--2024-08-12 15:42 - Brazil - Mateus Lima - Alter suppliers table.
ALTER TABLE suppliers
ADD COLUMN type CHAR(1) NOT NULL COMMENT 'Tax payer type';

--2024-08-12 16:50 - Brazil - Mateus Lima - Insert suppliers into suppliers table.
INSERT INTO suppliers(supplierid, workshopid, iddoc, name, landline, mobilephone, email, postalcode,
address, city, state, type)
VALUES(1, 1, '12345678000199', 'Paulinho Auto Peças', '+55 11 2222-3333', 
'+55 11 99999-4444', 'paulinhoap@teste.com', '02323-000', 'Rua do auto peças',
'São Paulo', 'SP', 'J');

INSERT INTO suppliers(supplierid, workshopid, iddoc, name, landline, mobilephone, email, postalcode,
address, city, state, type)
VALUES(2, 1, '12545678030188', 'Antonio Auto Peças', '+55 11 2222-3333', 
'+55 11 99999-4444', 'antonio@teste.com', '02323-000', 'Rua do auto peças',
'São Paulo', 'SP', 'J');

--2024-08-21 15:11 - Brazil - Mateus Lima - Create vehicles table.
CREATE TABLE vehicles(
vehicleid BIGINT NOT NULL AUTO_INCREMENT COMMENT "Vehicle identification number",
iddoc VARCHAR(14) NOT NULL COMMENT "Customer identification number",
workshopid BIGINT NOT NULL COMMENT "Workshop identification number",
plate VARCHAR(50) NOT NULL COMMENT "Plate number",
brand VARCHAR(50) NOT NULL COMMENT "Brand name",
model VARCHAR(50) NOT NULL COMMENT "Vehicle model",
manufacturing VARCHAR(9) NOT NULL COMMENT "Manufacturing year and model year",
engine VARCHAR(50) NOT NULL COMMENT "Vehicle engine",
observations VARCHAR(255) NOT NULL COMMENT "Vehicle observations",
active BOOL NOT NULL COMMENT "Identify if there is an active vehicle",
PRIMARY KEY(vehicleid),
CONSTRAINT fk_customers
_vehicles FOREIGN KEY(workshopid, iddoc) REFERENCES customers(workshopid, docid)
)COMMENT = "Vehicles registration";

--2024-09-04 16:24 - Brazil - Mateus Lima - Insert vehicles into vehicles table.
INSERT INTO vehicles(vehicleid, iddoc, workshopid, plate, brand, model, manufacturing,
engine, observations, active) VALUES(1, "22233344455", 1, "T1O2F8", "Volkswagen",
"polo", "2003", "1.6 103cv", "nenhuma", 1);

INSERT INTO vehicles(iddoc, workshopid, plate, brand, model, manufacturing,
engine, observations, active) VALUES("55566677788", 1, "T1O2F8", "Volkswagen",
"Polo", "2003/2004", "Mi 1.6 103cv", "nenhuma", true);

--2024-09-05 16:37 - Brazil - Mateus Lima - Update customer adress.
UPDATE customers set address = "Rua teste", city = "Testelândia", state = "SP", 
postalcode = "02323000" WHERE workshopid = 1;