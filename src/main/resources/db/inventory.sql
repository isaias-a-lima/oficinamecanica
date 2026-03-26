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

--2026-03-25 12:13 - Brazil - Insert into inventory_mov.
INSERT INTO inventory_mov(workshopid, inventoryid, movdate, movtype, docnumber,
supplierid, partid, qty, source)
values(1, 1, "2026-03-25", 1, "11122233344", 1, 1, 1, 1);

--2026-03-25 12:15 - Brazil - Insert into inventory_mov.
INSERT INTO inventory_mov(workshopid, inventoryid, movdate, movtype, docnumber,
supplierid, partid, qty, source)
values(1, 2, "2026-03-25", 2, "22233344455", 1, 2, 1, 2);

--2026-03-25 23:07 - Brazil - Insert into inventory_mov.
INSERT INTO inventory_mov(workshopid, inventoryid, movdate, movtype, docnumber,
supplierid, partid, qty, source)
values(1, 3, "2026-03-19", 2, "33344455566", 1, 2, 1, 2);
