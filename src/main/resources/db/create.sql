SET MODE PostgreSQL;

CREATE TABLE IF NOT EXISTS animals (
  id int PRIMARY KEY auto_increment,
   name VARCHAR,
   age INTEGER,
  endangered BOOLEAN,
  health VARCHAR
);
INSERT INTO animals (name,age,endangered,health) VALUES ('elephant',50,'true','ill');

CREATE TABLE IF NOT EXISTS sightings (
  id int PRIMARY KEY auto_increment,
  rangerName VARCHAR,
  location VARCHAR
);
INSERT INTO sightings (rangerName,location) VALUES ('Joe','NewYork');