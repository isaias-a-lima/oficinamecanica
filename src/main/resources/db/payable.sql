--2025-02-20 17:20 - Brazil - Mateus Lima - create payable table.
CREATE TABLE PAYABLE (
id BIGINT NOT NULL COMMENT "Payable identification number.",
workshopid BIGINT NOT NULL COMMENT "Workshop identification number.",
creation DATE NOT NULL COMMENT "Payable creation date.",
description VARCHAR(100) NOT NULL COMMENT "Payable description.",
docnumber VARCHAR(100) NULL COMMENT "Source document number",
duedate DATE NOT NULL COMMENT "Payable due date",
payvalue DECIMAL(12, 2) NOT NULL COMMENT "Pay value",
paydate DATE NULL COMMENT "Pay date",
categoryid INT COMMENT "Category identification number",
PRIMARY KEY(id, workshopid),
CONSTRAINT fk_workshops_payable FOREIGN KEY (workshopid) REFERENCES workshops(workshopid),
CONSTRAINT fk__mov_category__payable FOREIGN KEY (categoryid) REFERENCES mov_category(categoryid)
)COMMENT = "PAYABLE";

--2025-02-20 17:38 - Brazil - Mateus Lima - create mov_category table.
CREATE TABLE MOV_CATEGORY(
categoryid INT NOT NULL COMMENT "Category identification number",
workshopid BIGINT NOT NULL COMMENT "Workshop identification number",
creation DATE NOT NULL COMMENT "Creation date",
description VARCHAR(100) NOT NULL COMMENT "Category description",
categtype INT NOT NULL COMMENT "Category type",
activated BOOLEAN NOT NULL COMMENT "Activated or not?",
PRIMARY KEY(categoryid, workshopid),
CONSTRAINT fk_workshops_mov_category FOREIGN KEY (workshopid) REFERENCES workshops(workshopid)
)COMMENT = "MOV_CATEGORY";

--2025-02-28 17:05 - Brazil - Mateus Lima - insert into mov_category.
INSERT INTO MOV_CATEGORY(categoryid, workshopid, creation, description,
categtype, activated) VALUES(1, 1, "2025-02-28", "Contas de consumo",
1, true);

--2025-02-28 17:13 - Brazil - Mateus Lima - insert into mov_category.
INSERT INTO MOV_CATEGORY(categoryid, workshopid, creation, description,
categtype, activated) VALUES(2, 1, "2025-02-28", "Contas fixas",
1, true);

--2025-03-25 17:00 - Brazil - Mateus Lima - insert into payable.
INSERT INTO PAYABLE(id, workshopid, creation, description, docnumber,
duedate, payvalue, paydate, categoryid) VALUES(1, 1, "2025-03-25", "Contas a pagar",
"0000111", "2025-03-30", 100.00, "2025-03-27", 2);

--2025-03-26 17:00 - Brazil - Mateus Lima - insert into payable.
INSERT INTO PAYABLE(id, workshopid, creation, description, docnumber,
duedate, payvalue, paydate, categoryid) VALUES(2, 1, "2025-03-25", "Contas a pagar diferentes",
"12345", "2025-03-30", 120.00, "2025-03-27", 1);