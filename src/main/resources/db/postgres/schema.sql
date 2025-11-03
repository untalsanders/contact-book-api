CREATE TABLE IF NOT EXISTS contacts (
    id INT SERIAL,
    firstname VARCHAR(255) NOT NULL,
    lastname VARCHAR(255) NOT NULL,
    phone VARCHAR(255) NOT NULL
);
