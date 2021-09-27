DROP TABLE IF EXISTS STUDENT;

CREATE TABLE STUDENT
(
    id     LONG          PRIMARY KEY,
    name   VARCHAR(50)   NOT NULL,
    mail   VARCHAR(50)   NOT NULL
);

CREATE TABLE studentGRpc
(
    id   LONG    PRIMARY KEY,
    phone     VARCHAR(25)     NOT NULL,
    address     VARCHAR(50)     NOT NULL
)