CREATE TABLE IF NOT EXISTS Posts (
   id INT NOT NULL,
   user_id INT NOT NULL,
   title varchar(250),
   body varchar(250),
   PRIMARY KEY (id)
);