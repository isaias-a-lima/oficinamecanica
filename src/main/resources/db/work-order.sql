--2024-12-26 16:04 - Brazil - Mateus Lima - Create work-order table.
CREATE TABLE WORK_ORDERS (
workshopid BIGINT NOT NULL COMMENT "Workshop identification number",
workorderid BIGINT NOT NULL COMMENT "Work order identification number",
vehicleid BIGINT NOT NULL COMMENT "Vehicle identification number",
budgetid BIGINT NOT NULL COMMENT "budget identification number",
openingdate DATE NOT NULL COMMENT "Work order opening date",
km BIGINT NOT NULL COMMENT "Vehicle km",
wostatus TINYINT NOT NULL COMMENT "Work order status",
paid BOOLEAN NOT NULL COMMENT "Is the work order paid or not paid?",
payform TINYINT NOT NULL COMMENT "Work order pay form",
installments TINYINT NOT NULL COMMENT "Work order installments",
amount DECIMAL(12, 2) NOT NULL COMMENT "Work order amount",
PRIMARY KEY(workshopid, workorderid),
CONSTRAINT fk_workshops_work_orders FOREIGN KEY(workshopid) REFERENCES workshops(workshopid),
CONSTRAINT fk_vehicles_work_orders FOREIGN KEY(vehicleid) REFERENCES vehicles(vehicleid),
CONSTRAINT fk_budgets_work_orders FOREIGN KEY(budgetid) REFERENCES budgets(budgetid)
)COMMENT = "WORK_ORDER";