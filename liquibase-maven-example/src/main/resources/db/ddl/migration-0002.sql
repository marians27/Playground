--liquibase formatted sql

--changeset marian:1
create table LOOKUP_VALUE (
    id int primary key,
    lookup_id int,
    value varchar(255),
    FOREIGN KEY (lookup_id) REFERENCES LOOKUP(id)
);
--rollback drop table LOOKUP_VALUE;

--changeset marian:2 dbms:oracle
create sequence LOOKUP_VALUE_SEQ;
--rollback drop sequence LOOKUP_VALUE_SEQ;