CREATE TABLE albums (id BIGINT AUTO_INCREMENT, band VARCHAR(255), title VARCHAR(255), release_date date, label VARCHAR(100), genre VARCHAR(100), CONSTRAINT pk_albums PRIMARY KEY (id));