CREATE TABLE user (
  id BIGINT NOT NULL,
   name VARCHAR(255) NULL,
   email VARCHAR(255) NULL,
   password VARCHAR(255) NULL,
   `role` VARCHAR(255) NULL,
   is_active BIT(1) NULL,
   login_attempt_counter INT NULL,
   mobile VARCHAR(255) NULL,
   created_on datetime NULL,
   CONSTRAINT pk_user PRIMARY KEY (id)
);