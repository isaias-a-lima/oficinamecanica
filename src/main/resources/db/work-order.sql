--2024-12-26 16:04 - Brazil - Mateus Lima - Create work-order table.
CREATE TABLE WORK_ORDERS (
workorderid BIGINT NOT NULL COMMENT "Work order identification number",
budgetid BIGINT NOT NULL COMMENT "budget identification number",
openingdate DATE NOT NULL COMMENT "Work order opening date",
km BIGINT NOT NULL COMMENT "Vehicle km",
wostatus TINYINT NOT NULL COMMENT "Work order status",
amount DECIMAL(12, 2) NOT NULL COMMENT "Work order amount",
payform TINYINT NULL COMMENT "Work order pay form",
payqty TINYINT NULL COMMENT "Installments quantity",
paid BOOLEAN NULL COMMENT "Is the work order paid or not paid?",
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
workorder_id BIGINT NOT NULL COMMENT 'Work order identification number',
budget_id BIGINT NOT NULL COMMENT 'Budget identification number',
service_id INT NOT NULL COMMENT 'Service identification number',
workshop_id BIGINT NOT NULL COMMENT 'Workshop identification number',
quantity INT NOT NULL COMMENT 'Items quantity',
itemvalue DECIMAL(12, 2) NOT NULL COMMENT 'Item value',
discount DECIMAL(5,2) NOT NULL COMMENT 'Service item discount',
PRIMARY KEY (itemid, workorder_id, budget_id),
CONSTRAINT fk_work_orders__wo_service_items FOREIGN KEY (workorder_id) REFERENCES work_orders(workorderid),
CONSTRAINT fk_budgets__wo_service_items FOREIGN KEY (budget_id) REFERENCES budgets(budgetid),
CONSTRAINT fk_services1__wo_service_items FOREIGN KEY (service_id) REFERENCES services(serviceid),
CONSTRAINT fk_services2__wo_service_items FOREIGN KEY (workshop_id) REFERENCES services(workshopid)
)COMMENT = 'WORK ORDER SERVICE ITEMS';

--2025-02-07 20:17 - Portugal - Isaias Lima - Creation of wo_parts_items table.
CREATE TABLE WO_PARTS_ITEMS (
itemid BIGINT NOT NULL COMMENT 'Work order part item identification number',
workorder_id BIGINT NOT NULL COMMENT 'Work order identification number',
budget_id BIGINT NOT NULL COMMENT 'Budget identification number',
part_id INT NOT NULL COMMENT 'Part identification number',
workshop_id BIGINT NOT NULL COMMENT 'Workshop identification number',
quantity INT NOT NULL COMMENT 'Items quantity',
itemValue DECIMAL(12, 2) NOT NULL COMMENT 'Item value',
discount DECIMAL(5, 2) NOT NULL COMMENT 'Item discount',
PRIMARY KEY (itemid, workorder_id, budget_id),
CONSTRAINT fk_work_orders__wo_parts_items FOREIGN KEY (workorder_id) REFERENCES work_orders(workorderid),
CONSTRAINT fk_budgets__wo_parts_items FOREIGN KEY (budget_id) REFERENCES budgets(budgetid),
CONSTRAINT fk_parts1__wo_parts_items FOREIGN KEY (part_id) REFERENCES parts(partid),
CONSTRAINT fk_parts2__wo_parts_items FOREIGN KEY (workshop_id) REFERENCES parts(workshopid)
) COMMENT = 'WORK ORDER PART ITEMS';

--2025-03-05 00:50 - Portugal - Isaias Lima - Remove payform column
ALTER TABLE WORK_ORDERS DROP COLUMN payform;

--2025-03-05 00:50 - Portugal - Isaias Lima - Add payform column
ALTER TABLE WO_INSTALLMENTS ADD payform TINYINT NULL COMMENT 'Installment pay form' AFTER payvalue;

--2025-03-31 01:20 - Portugal - Isaias Lima - Creation of payments table.
CREATE TABLE PAYMENTS (
paynumber INT NOT NULL COMMENT "Payment number",
workorderid BIGINT NOT NULL COMMENT "Work order identification number",
budgetid BIGINT NOT NULL COMMENT "budget identification number",
duedate DATE NOT NULL COMMENT "Payment due date",
payvalue DECIMAL(12,2) NOT NULL COMMENT "Payment value",
paytype TINYINT NOT NULL COMMENT "Payment type.",
note VARCHAR(255) NULL COMMENT "Payment notes",
paydate DATE NULL COMMENT "Payment date",
PRIMARY KEY(paynumber, workorderid, budgetid),
CONSTRAINT fk_work_orders__payments FOREIGN KEY(workorderid) REFERENCES work_orders(workorderid),
CONSTRAINT fk_budgets__payments FOREIGN KEY(budgetid) REFERENCES budgets(budgetid)
)COMMENT = "WORK ORDER PAYMENTS";

--2025-03-31 01:20 - Portugal - Isaias Lima - Migration of payments from wo_installments.
INSERT INTO PAYMENTS (paynumber, workorderid, budgetid, duedate, payvalue, paytype, note, paydate)
SELECT 	inst.number, inst.workorderid, inst.budgetid, inst.duedate, inst.payvalue,
		IFNULL(inst.payform, 0), inst.note, inst.paydate
FROM WO_INSTALLMENTS inst
--WHERE NOT (inst.number = 1 AND inst.workorderid = 10 AND inst.budgetid = 46 AND inst.payvalue > 50)
;

--2025-04-16 17:50 - Brazil - Mateus Lima - Insert into payments.
INSERT INTO PAYMENTS (paynumber, workorderid, budgetid, duedate, payvalue, paytype, note, paydate)
VALUES (1, 1, 1, '2025-04-01', 100, 1, 'teste', null);

--2025-04-16 18:03 - Brazil - Mateus Lima - Update duedate in payments.
UPDATE PAYMENTS SET duedate = '2025-04-20' WHERE paynumber = 1;