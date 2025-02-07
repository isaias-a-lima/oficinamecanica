--2024-12-26 16:04 - Brazil - Mateus Lima - Create work-order table.
CREATE TABLE WORK_ORDERS (
workorderid BIGINT NOT NULL COMMENT "Work order identification number",
budgetid BIGINT NOT NULL COMMENT "budget identification number",
openingdate DATE NOT NULL COMMENT "Work order opening date",
km BIGINT NOT NULL COMMENT "Vehicle km",
wostatus TINYINT NOT NULL COMMENT "Work order status",
amount DECIMAL(12, 2) NOT NULL COMMENT "Work order amount",
payform TINYINT NULL COMMENT "Work order pay form",
payqty TINYINT NULL DEFAULT 1 COMMENT "Installments quantity",
paid BOOLEAN NOT NULL COMMENT "Is the work order paid or not paid?",
PRIMARY KEY(workorderid, budgetid),
CONSTRAINT fk_budgets_work_orders FOREIGN KEY(budgetid) REFERENCES budgets(budgetid)
)COMMENT = "WORK ORDERS";

--2025-01-20 22:00 - Portugal - Isaias Lima - Creation of service order table.
CREATE TABLE WO_INSTALLMENTS (
number BIGINT NOT NULL COMMENT "Installment number",
workorderid BIGINT NOT NULL COMMENT "Work order identification number",
budgetid BIGINT NOT NULL COMMENT "budget identification number",
paytype TINYINT NOT NULL COMMENT "Payment type. Default pay or Interest pay",
duedate DATE NULL COMMENT "Installment due date",
payvalue DECIMAL(12,2) NOT NULL COMMENT "Installment payment value",
paydate DATE NULL COMMENT "Installment payment date",
note VARCHAR(255) NULL COMMENT "Installment notes",
PRIMARY KEY(number, workorderid, budgetid, paytype),
CONSTRAINT fk_work_orders_wo_installments FOREIGN KEY(workorderid) REFERENCES work_orders(workorderid),
CONSTRAINT fk_budgets_wo_installments FOREIGN KEY(budgetid) REFERENCES budgets(budgetid)
)COMMENT = "WORK ORDER INSTALLMENTS";

INSERT INTO WORK_ORDERS(workorderid, budgetid, openingdate, km, wostatus,
amount, payform, payqty, paid)
VALUES(1, 1, "2025-02-03", 17000, 0, 0, 0, 1, false);

INSERT INTO WORK_ORDERS(workorderid, budgetid, openingdate, km, wostatus,
amount, payform, payqty, paid)
VALUES(2, 1, "2025-02-04", 23000, 0, 0, 0, 1, false);

--2025-02-07 15:20 - Brazil - Mateus Lima - Creation of wo_service_items table.
CREATE TABLE WO_SERVICE_ITEMS (
itemid BIGINT NOT NULL COMMENT 'Work order item identification number',
workorderid BIGINT NOT NULL COMMENT 'Work order identification number',
budgetid BIGINT NOT NULL COMMENT 'Budget identification number',
serviceid INT NOT NULL COMMENT 'Service identification number',
quantity INT NOT NULL COMMENT 'Items quantity',
itemvalue DECIMAL(12, 2) NOT NULL COMMENT 'Item value',
discount DECIMAL(5,2) NOT NULL COMMENT 'Service item discount',
PRIMARY KEY (itemid, workorderid, budgetid),
CONSTRAINT fk_work_orders_wo_service_items FOREIGN KEY (workorderid) REFERENCES work_orders(workorderid),
CONSTRAINT fk_budgets_wo_service_items FOREIGN KEY (budgetid) REFERENCES budgets(budgetid),
CONSTRAINT fk_services_wo_service_items FOREIGN KEY (serviceid) REFERENCES services(serviceid)
)COMMENT = 'WORK ORDER SERVICE ITEMS';