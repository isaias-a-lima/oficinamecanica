--2025-05-08 17:21 - Brazil - Mateus Lima - Create inventory_mov table.
CREATE TABLE INVENTORY_MOV (
workshopid BIGINT NOT NULL COMMENT "Work order identification number",
inventoryid BIGINT NOT NULL COMMENT "Inventory identification number",
movdate DATE NOT NULL COMMENT "Mouvement date",
movtype TINYINT(1) NOT NULL COMMENT "Mouvement type",
docnumber VARCHAR(50) COMMENT "Document number",
supplierid INT COMMENT "Supplier identification number",
partid INT NOT NULL COMMENT "Part identification number",
qty int NOT NULL COMMENT "Mouvement quantity",
source TINYINT(1) COMMENT "Mouvement source",
PRIMARY KEY(workshopid, inventoryid),
CONSTRAINT fk_workshops_inventory_mov FOREIGN KEY(workshopid) REFERENCES workshops(workshopid),
CONSTRAINT fk_suppliers_inventory_mov FOREIGN KEY(supplierid, workshopid) REFERENCES suppliers(supplierid, workshopId),
CONSTRAINT fk_parts_inventory_mov FOREIGN KEY(partid, workshopid) REFERENCES parts(partid, workshopid)
)COMMENT = "INVENTORY_MOV";

--2026-03-25 12:00 - Brazil - Mateus Lima - Drop inventory_mov table.
DROP TABLE INVENTORY_MOV;

--2026-03-23 12:02 - Brazil - Mateus Lima - Create inventory_mov table.
CREATE TABLE INVENTORY_MOV (
workshopid BIGINT NOT NULL COMMENT "Work order identification number",
inventoryid BIGINT NOT NULL COMMENT "Inventory identification number",
movdate DATE NOT NULL COMMENT "Mouvement date",
movtype TINYINT(1) NOT NULL COMMENT "Mouvement type",
docnumber VARCHAR(50) COMMENT "Document number",
supplierid INT COMMENT "Supplier identification number",
partid INT NOT NULL COMMENT "Part identification number",
qty int NOT NULL COMMENT "Mouvement quantity",
source TINYINT(1) COMMENT "Mouvement source",
PRIMARY KEY(workshopid, inventoryid),
CONSTRAINT fk_workshops_inventory_mov FOREIGN KEY(workshopid) REFERENCES workshops(workshopid),
CONSTRAINT fk_suppliers_inventory_mov FOREIGN KEY(supplierid, workshopid) REFERENCES suppliers(supplierid, workshopid),
CONSTRAINT fk_parts_inventory_mov FOREIGN KEY(partid, workshopid) REFERENCES parts(partid, workshopid)
)COMMENT = "INVENTORY_MOV";

--2026-04-10 11:57 - Brazil - Mateus Lima - Insert into inventory_mov table.
insert into inventory_mov (workshopid, inventoryid, movdate, movtype, docnumber, supplierid, partid, qty, source)
values (1, 1, date('2026-03-10'), 1, 1001, 1, 1, 50, 1);

insert into inventory_mov (workshopid, inventoryid, movdate, movtype, docnumber, supplierid, partid, qty, source)
values (1, 2, date('2026-03-10'), 1, 1001, 1, 1, 100, 1);

insert into inventory_mov (workshopid, inventoryid, movdate, movtype, partid, qty, source)
values (1, 3, date('2026-03-11'), 2, 1, 150, 3);

insert into inventory_mov (workshopid, inventoryid, movdate, movtype, docnumber, supplierid, partid, qty, source)
values (1, 4, date('2026-03-12'), 1, 1001, 1, 1, 150, 1);

insert into inventory_mov (workshopid, inventoryid, movdate, movtype, docnumber, supplierid, partid, qty, source)
values (1, 5, date('2026-03-13'), 1, 1001, 1, 1, 150, 1);

insert into inventory_mov (workshopid, inventoryid, movdate, movtype, docnumber, partid, qty, source)
values (1, 6, date('2026-04-29'), 0, 1001, 1, 500, 0);
