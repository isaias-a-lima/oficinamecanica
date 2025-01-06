--2024-07-25 17:41 - Brazil - Mateus Lima - Create parts table.
CREATE TABLE parts(
partid INT NOT NULL COMMENT 'Part identification number',
workshopid BIGINT NOT NULL COMMENT 'Workshop Identification number',
description VARCHAR(255) NOT NULL COMMENT 'Part description',
cost DECIMAL(7,2) NOT NULL COMMENT 'Part cost',
profit DECIMAL(7,2) NOT NULL COMMENT 'Part profit',
balance INT NOT NULL COMMENT 'Parts balance',
PRIMARY KEY(partid, workshopid),
CONSTRAINT fk_workshops_parts FOREIGN KEY (workshopid)
REFERENCES workshops(workshopid)
)COMMENT = 'Parts registration';

--2024-08-07 16:32 - Brazil - Mateus Lima - Alter parts table.
ALTER TABLE parts ADD COLUMN brand VARCHAR(100) NOT NULL COMMENT 'Part brand';

--2024-12-30 22:00 - Portugal - Isaias Lima - Add new columns.
ALTER TABLE parts
ADD COLUMN fits VARCHAR(255) NOT NULL COMMENT 'It describe which cars the part fits' AFTER description,
ADD COLUMN manufacturercode VARCHAR(100) NULL COMMENT 'Manufacturer code' AFTER fits
;