CREATE TABLE BUDGET_ITEM_PART (
itemid BIGINT NOT NULL COMMENT "Part item order number",
budgetid BIGINT NOT NULL COMMENT "Budget identification number",
part_id INT NOT NULL COMMENT "Part identification number",
workshop_id BIGINT NOT NULL COMMENT "Workshop identification number",
quantity INT NOT NULL COMMENT "Parts quantity",
cost DECIMAL(12, 2) NOT NULL COMMENT "Part cost",
discount DECIMAL(5, 2) NOT NULL COMMENT "Part discount",
PRIMARY KEY (itemid, budgetid),
CONSTRAINT fk_budgets_budget_item_part FOREIGN KEY (budgetid) REFERENCES budgets(budgetid),
CONSTRAINT fk_part1_budget_item_part FOREIGN KEY (part_id) REFERENCES parts(partid),
CONSTRAINT fk_part2_budget_item_part FOREIGN KEY (workshop_id) REFERENCES parts(workshopid)
)COMMENT = "BUDGET_ITEM_PART";