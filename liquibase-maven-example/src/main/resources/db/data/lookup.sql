--liquibase formatted sql

--changeset marian:1

INSERT INTO LOOKUP(ID, NAME) VALUES (1, 'X11');
INSERT INTO LOOKUP(ID, NAME) VALUES (2, 'X23');