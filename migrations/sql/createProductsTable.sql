CREATE TABLE IF NOT EXISTS Products
(
    id            BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    name          VARCHAR(255),
    weight        DECIMAL(10, 2),
    carbohydrates DECIMAL(10, 2),
    kcal          DECIMAL(10, 2),
    fats          DECIMAL(10, 2),
    proteins      DECIMAL(10, 2)
);