CREATE TABLE patient_address (
  id INT NOT NULL,
   village VARCHAR(255) NULL,
   district VARCHAR(255) NULL,
   address VARCHAR(255) NULL,
   CONSTRAINT pk_patientaddress PRIMARY KEY (id)
);
CREATE TABLE patient_contact (
  id INT NOT NULL,
   mobile VARCHAR(255) NULL,
   email VARCHAR(255) NULL,
   CONSTRAINT pk_patientcontact PRIMARY KEY (id)
);
CREATE TABLE patient (
  uuid char(36) NOT NULL,
   patient_id VARCHAR(255) NULL,
   name VARCHAR(255) NULL,
   father_name VARCHAR(255) NULL,
   age INT NULL,
   patient_contact_id INT NULL,
   patient_address_id INT NULL,
   date datetime NULL,
   CONSTRAINT pk_patient PRIMARY KEY (uuid)
);

ALTER TABLE patient ADD CONSTRAINT FK_PATIENT_ON_PATIENT_ADDRESS FOREIGN KEY (patient_address_id) REFERENCES patient_address (id);

ALTER TABLE patient ADD CONSTRAINT FK_PATIENT_ON_PATIENT_CONTACT FOREIGN KEY (patient_contact_id) REFERENCES patient_contact (id);
