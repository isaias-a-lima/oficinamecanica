--2024-12-26 16:04 - Brazil - Mateus Lima - Create work-order table.
CREATE TABLE WORK_ORDERS (
workorderid BIGINT NOT NULL COMMENT "Work order identification number",
budgetid BIGINT NOT NULL COMMENT "budget identification number",
openingdate DATE NOT NULL COMMENT "Work order opening date",
km BIGINT NOT NULL COMMENT "Vehicle km",
wostatus TINYINT NOT NULL COMMENT "Work order status",
amount DECIMAL(12, 2) NOT NULL COMMENT "Work order amount",
payform TINYINT NOT NULL COMMENT "Work order pay form",
payqty TINYINT NOT NULL DEFAULT 1 COMMENT "Installments quantity",
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
duedate DATE NOT NULL COMMENT "Installment due date",
payvalue DECIMAL(12,2) NOT NULL COMMENT "Installment payment value",
paydate DATE NOT NULL COMMENT "Installment payment date",
note VARCHAR(255) NULL COMMENT "Installment notes",
PRIMARY KEY(number, workorderid, budgetid, paytype),
CONSTRAINT fk_work_orders_wo_installments FOREIGN KEY(workorderid) REFERENCES work_orders(workorderid),
CONSTRAINT fk_budgets_wo_installments FOREIGN KEY(budgetid) REFERENCES budgets(budgetid)
)COMMENT = "WORK ORDER INSTALLMENTS";

INSERT INTO WORK_ORDERS(workorderid, budgetid, openingdate, km, wostatus,
amount, payform, payqty, paid)
VALUES(1, 1, "31-01-2025", 17000, , 500, "D", 10, false);