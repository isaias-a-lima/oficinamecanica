--2024-11-24 23:00 - Portugal - Isaias Lima - Create table SUPPLIERS_PARTS.
CREATE TABLE suppliers_parts (
supplierid INT NOT NULL COMMENT 'Supplier identification number',
supp_workshopid BIGINT NOT NULL COMMENT 'Workshop identification number from supplier',
partid INT NOT NULL COMMENT 'Part identification number',
part_workshopid BIGINT NOT NULL COMMENT 'Workshop identification number from part',
PRIMARY KEY(supplierid, supp_workshopid, partid, part_workshopid),
CONSTRAINT fk_suppliers_suppliers_parts FOREIGN KEY (supplierid) REFERENCES suppliers(supplierid),
CONSTRAINT fk_supp_workshop_suppliers_parts FOREIGN KEY (supp_workshopid) REFERENCES workshops(workshopid),
CONSTRAINT fk_parts_suppliers_parts FOREIGN KEY (partid) REFERENCES parts(partid),
CONSTRAINT fk_part_workshop_suppliers_parts FOREIGN KEY (part_workshopid) REFERENCES workshops(workshopid)
)COMMENT = 'Suppliers_parts registration';