-- liquibase formatted sql

-- changeset karybekov:1
CREATE TABLE IF NOT EXISTS animal_shelter (
    id BIGINT GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
    address VARCHAR(255),
    work_schedule VARCHAR(255),
    name VARCHAR(100)
);

CREATE TABLE IF NOT EXISTS pet (
    id BIGINT GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
    nick VARCHAR(255),
    animal_shelter_id BIGINT REFERENCES animal_shelter(id)
);

CREATE TABLE IF NOT EXISTS pet_report (
    id BIGINT GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
    diet VARCHAR(255),
    well_being VARCHAR(255),
    behaviour_change VARCHAR(255),
    pet_id BIGINT REFERENCES pet(id)
);

CREATE TABLE IF NOT EXISTS photo (
    id BIGINT GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
    file_path VARCHAR(255),
    file_size BIGINT,
    media_type VARCHAR(255),
    data BYTEA,
    animal_shelter_id BIGINT REFERENCES animal_shelter(id),
    pet_id BIGINT REFERENCES pet(id),
    pet_report_id BIGINT REFERENCES pet_report(id),
    CONSTRAINT check_one_empty CHECK (
        (animal_shelter_id IS NULL AND pet_id IS NULL AND pet_report_id IS NOT NULL) OR
        (animal_shelter_id IS NULL AND pet_id IS NOT NULL AND pet_report_id IS NULL) OR
        (animal_shelter_id IS NOT NULL AND pet_id IS NULL AND pet_report_id IS NULL)
    )
);

CREATE TABLE bot_user (
    id BIGINT GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
    cht_id BIGINT NOT NULL,
    role VARCHAR(50) NOT NULL,
    phone INT,
    name VARCHAR(255),
    pet_id BIGINT REFERENCES pet(id)
);

ALTER TABLE animal_shelter ADD COLUMN address_photo_id BIGINT REFERENCES photo(id);
ALTER TABLE pet ADD COLUMN address_photo_id BIGINT REFERENCES photo(id);
ALTER TABLE pet ADD COLUMN owner_id BIGINT REFERENCES bot_user(id);

-- changeset karybekov:2
ALTER TABLE photo DROP COLUMN data;

-- changeset karybekov:3
ALTER TABLE pet ADD COLUMN photo_id BIGINT REFERENCES photo(id);

--changeset karybekov:4
ALTER TABLE pet DROP COLUMN address_photo_id;

ALTER TABLE pet ADD COLUMN IF NOT EXISTS photo_id BIGINT REFERENCES photo(id);

