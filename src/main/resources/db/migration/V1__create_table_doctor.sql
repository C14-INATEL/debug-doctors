CREATE TABLE doctor (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    specialty VARCHAR(255) NOT NULL,
    crm VARCHAR(255) NOT NULL,
    shift_start TIME NOT NULL,
    shift_end TIME NOT NULL
);