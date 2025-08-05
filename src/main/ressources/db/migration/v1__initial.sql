CREATE TABLE person (
                        id BIGINT PRIMARY KEY AUTO_INCREMENT,
                        email VARCHAR(255) NOT NULL,
                        full_name VARCHAR(255) NOT NULL,
                        dtype VARCHAR(31) -- Pour l'héritage Donor/Beneficiary
);

CREATE TABLE payment (
                         id VARCHAR(255) PRIMARY KEY,
                         date TIMESTAMP NOT NULL,
                         amount DECIMAL(10,2) NOT NULL,
                         method VARCHAR(50) NOT NULL,
                         status VARCHAR(20) NOT NULL
);

CREATE TABLE donation (
                          id BIGINT PRIMARY KEY AUTO_INCREMENT,
                          donor_id BIGINT NOT NULL,
                          payment_id VARCHAR(255) NOT NULL,
                          FOREIGN KEY (donor_id) REFERENCES person(id),
                          FOREIGN KEY (payment_id) REFERENCES payment(id)
);

CREATE TABLE help (
                      id BIGINT PRIMARY KEY AUTO_INCREMENT,
                      beneficiary_id BIGINT NOT NULL,
                      payment_id VARCHAR(255) NOT NULL,
                      accident_description TEXT,
                      FOREIGN KEY (beneficiary_id) REFERENCES person(id),
                      FOREIGN KEY (payment_id) REFERENCES payment(id)
);