--2024-11-11 17:25 - Brazil - Mateus Lima - Create budget-items-services table.
CREATE TABLE BUDG_ITEM_SERV (
serviceitemid BIGINT NOT NULL AUTO_INCREMENT COMMENT "Service item identification number",
budgetid BIGINT NOT NULL COMMENT "Budget identification number",
serviceid INT(11) NOT NULL COMMENT "Service identification number",
quantity INT NOT NULL COMMENT "Services quantity",
cost DECIMAL(10, 2) NOT NULL COMMENT "Services cost",
discount DECIMAL(3, 2) NOT NULL COMMENT "Service discount",
PRIMARY KEY (serviceitemid),
CONSTRAINT fk_budgets_budg_item_serv FOREIGN KEY (budgetid) REFERENCES budgets(budgetid),
CONSTRAINT fk_services_budg_item_serv FOREIGN KEY (serviceid) REFERENCES services(serviceid)
)COMMENT = "BUDG_ITEM_SERV";

--2024-16-11 15:55 - Brazil - Mateus Lima - Drop budget-items-services table.
DROP TABLE BUDG_ITEM_SERV;

--2024-11-16 15:55 - Brazil - Mateus Lima - Drop budget-items-services table.
CREATE TABLE BUDG_ITEM_SERV (
serviceitemid BIGINT NOT NULL COMMENT "Service item identification number",
budgetid BIGINT NOT NULL COMMENT "Budget identification number",
serviceid_part1 INT NOT NULL COMMENT "Service identification number",
serviceid_part2 BIGINT NOT NULL COMMENT "Workshop identification number",
quantity INT NOT NULL COMMENT "Services quantity",
cost DECIMAL(10, 2) NOT NULL COMMENT "Services cost",
discount DECIMAL(5, 2) NOT NULL COMMENT "Service discount",
PRIMARY KEY (serviceitemid, budgetId),
CONSTRAINT fk_budgets_budg_item_serv FOREIGN KEY (budgetid) REFERENCES budgets(budgetid),
CONSTRAINT fk_services1_budg_item_serv FOREIGN KEY (serviceid_part1) REFERENCES services(serviceid),
CONSTRAINT fk_services2_budg_item_serv FOREIGN KEY (serviceid_part2) REFERENCES services(workshopid)
)COMMENT = "BUDG_ITEM_SERV";

--2024-11-25 17:18 - Brazil - Mateus Lima - Insert into budget-item-services table.
INSERT INTO BUDG_ITEM_SERV(serviceitemid, budgetid, serviceid_part1, serviceid_part2,  quantity, cost, discount)
VALUES(1, 2, 2, 1, 1, 90, 10);

--2024-11-30 11:26 - Brazil - Mateus Lima - Insert into budget-item-services table.
INSERT INTO BUDG_ITEM_SERV(serviceitemid, budgetid, serviceid_part1, serviceid_part2,  quantity, cost, discount)
VALUES(2, 2, 1, 1, 1, 100, 10);