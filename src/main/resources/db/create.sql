SET MODE PostgreSQL;

CREATE TABLE IF NOT EXISTS animals (
  id int PRIMARY KEY auto_increment,
   name VARCHAR,
   age INTEGER,
  endangered BOOLEAN,
  health VARCHAR
);

CREATE TABLE IF NOT EXISTS sightings (
  id int PRIMARY KEY auto_increment,
  rangerName VARCHAR,
  location VARCHAR
);