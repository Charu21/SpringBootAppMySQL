CREATE TABLE item (
  id CHAR(36),
  name VARCHAR(20),
  description VARCHAR(200),
  type VARCHAR(20) NOT NULL,
  cost DOUBLE NOT NULL CHECK (cost > 0.0),
  created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
  updated_at DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  deleted_at DATETIME DEFAULT NULL,
   PRIMARY KEY(id),
   CONSTRAINT name_description_unique UNIQUE(name, description)
);

INSERT INTO item VALUES ('c1bbd5cd-0db5-4af4-b9ad-61da0a6acbf1', 'Hockey Stick', 'It is a wooden stick with length varying from 26 inches to 38.5 inches depending on height of the player.', 'HOCKEY_STICKS', 20.00, default,default,default);
INSERT INTO item VALUES ('71431b55-e504-41fd-918c-80aa42c86c54', 'Hockey Skates', 'A hockey skate has a rigid boot typically made of a synthetic material—plastic.','HOCKEY_SKATES', 50.00,default,default,default);
INSERT INTO item VALUES ('7abb3017-6ea8-4f69-9a90-7eef1cb17c8a', 'Hockey Pads', 'The pads are worn to protect knees and lower part of limbs.', 'HOCKEY_PADS', 45.00,default,default,default);

