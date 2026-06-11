CREATE TABLE schedule (
    id BIGSERIAL PRIMARY KEY,
    doctor_id BIGINT,
    patient_id BIGINT,
    date_time TIMESTAMP NOT NULL,
    description VARCHAR(255),
    confirmed BOOLEAN NOT NULL DEFAULT FALSE,
    canceled BOOLEAN NOT NULL DEFAULT FALSE,
    cancellation_reason VARCHAR(255),
    CONSTRAINT fk_schedule_doctor FOREIGN KEY (doctor_id) REFERENCES doctor(id),
    CONSTRAINT fk_schedule_patient FOREIGN KEY (patient_id) REFERENCES patient(id)
);