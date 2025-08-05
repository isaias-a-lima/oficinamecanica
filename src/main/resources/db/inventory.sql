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