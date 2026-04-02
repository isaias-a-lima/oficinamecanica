--2025-08-16 1:00 - Portugal - Isaias Lima - Add the RECEIVABLES table to the data base
CREATE TABLE receivables (
receivableid BIGINT NOT NULL COMMENT 'Receivable identification number',
workshopid BIGINT NOT NULL COMMENT 'Workshop identification number',
duedate DATE NOT NULL COMMENT 'Receivable due date',
payvalue DECIMAL(12,2) NOT NULL COMMENT 'Receivable value',
paytype TINYINT NOT NULL COMMENT 'Receivable type.',
note VARCHAR(255) NULL COMMENT 'Receivable notes',
paydate DATE NULL COMMENT 'Receivable date',
outsourcepay TINYINT(1) NOT NULL DEFAULT 0 COMMENT 'Is an outsource pay?',
supplierid INT DEFAULT NULL COMMENT 'Supplier identification number',
PRIMARY KEY(receivableid, workshopid),
CONSTRAINT fk_workshops__receivables FOREIGN KEY(workshopid) REFERENCES workshops(workshopid),
CONSTRAINT fk_suppliers__receivables FOREIGN KEY(supplierid, workshopid) REFERENCES suppliers(supplierid, workshopid)
)COMMENT = 'RECEIVABLES';
