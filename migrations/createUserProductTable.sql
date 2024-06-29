CREATE TABLE IF NOT EXISTS User_Product
(
    id              BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    user_id          BIGINT,
    product_id       BIGINT,
    expiration_date DATE,
    FOREIGN KEY (user_id) REFERENCES Users (id) ON DELETE CASCADE ,
    FOREIGN KEY (product_id) REFERENCES Products (id) ON DELETE CASCADE
);