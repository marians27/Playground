--liquibase formatted sql

--changeset marian:1
create table LOOKUP (
    id int primary key,
    name varchar(255)
);
--rollback drop table LOOKUP;

--changeset marian:2 dbms:oracle
create sequence LOOKUP_SEQ;
--rollback drop sequence LOOKUP_SEQ;