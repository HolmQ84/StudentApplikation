DROP TABLE IF EXISTS STUDENT;

CREATE TABLE STUDENT
(
    id     LONG          PRIMARY KEY,
    name   VARCHAR(25)   NOT NULL,
    mail   VARCHAR(25)   NOT NULL
);

CREATE TABLE studentGRpc
(
    studentId   LONG    PRIMARY KEY,
    studentName     VARCHAR(25)     NOT NULL,
    studentMail     VARCHAR(25)     NOT NULL
)