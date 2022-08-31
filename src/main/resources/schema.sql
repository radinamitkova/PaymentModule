---- Oracle queries
DROP TABLE Login_Details;
DROP TABLE Duty_History;
DROP TABLE tokens;
DROP TABLE Duty_Account;
DROP TABLE clients;

CREATE TABLE clients
(
    id           varchar(45) NOT NULL,
    name         varchar(45) NOT NULL,
    email        varchar(45) UNIQUE NOT NULL,
    phone_number varchar(45) UNIQUE NOT NULL,
    PRIMARY KEY (id)
);

CREATE TABLE Login_Details
(
    login_id  NUMBER GENERATED BY DEFAULT AS IDENTITY,
    client_id VARCHAR2(30) UNIQUE NOT NULL,
    username  VARCHAR2(30) UNIQUE NOT NULL,
    password  VARCHAR2(70) NOT NULL,
    is_active NUMBER NOT NULL,
    PRIMARY KEY (login_id),
    CONSTRAINT is_active_constraint CHECK (is_active in (0,1)),
    FOREIGN KEY (client_id) REFERENCES clients (id)
);

CREATE TABLE Duty_Account
(
    id             NUMBER GENERATED BY DEFAULT AS IDENTITY NOT NULL,
    client_id      VARCHAR(30)  UNIQUE NOT NULL,
    currency       VARCHAR(30)         NOT NULL,
    duty           NUMBER(10,2)        NOT NULL,
    start_date     date                NOT NULL,
    PRIMARY KEY (id),
    FOREIGN KEY (client_id) REFERENCES clients (id),
    CONSTRAINT currency_constraint CHECK (currency IN ('BGN', 'EUR', 'GBP', 'USD'))
);

CREATE TABLE Duty_History
(
    record_id       NUMBER GENERATED BY DEFAULT AS IDENTITY,
    duty_account_id NUMBER NOT NULL,
    payment         NUMBER(10,2) NOT NULL,
    time            DATE   NOT NULL,
    transaction_reference VARCHAR(45),
    PRIMARY KEY (record_id),
    FOREIGN KEY (duty_account_id) REFERENCES DUTY_ACCOUNT (id)
);

CREATE TABLE tokens
(
    id          NUMBER GENERATED BY DEFAULT AS IDENTITY,
    token_body       VARCHAR2(50) UNIQUE NOT NULL,
    token_type  VARCHAR(20) NOT NULL
        CONSTRAINT token_constraint CHECK (token_type IN ('REGISTRATION', 'ACTIVATION')),
    client_id   VARCHAR2(30) NOT NULL,
    active_from DATE,
    active_to   DATE,
    PRIMARY KEY (id),
    FOREIGN KEY (client_id) REFERENCES clients (id)
);

-- H2 queries
-- DROP TABLE IF EXISTS Login_Details cascade;
-- DROP TABLE IF EXISTS Duty_History cascade;
-- DROP TABLE IF EXISTS tokens cascade;
-- DROP TABLE IF EXISTS Bank_Account cascade;
-- DROP TABLE IF EXISTS clients cascade;
-- create table CLIENTS (
--                          ID varchar(45) not null,
--                          NAME varchar(45) not null,
--                          EMAIL varchar(45) default null,
--                          PHONE_NUMBER varchar(45),
--                          primary key (ID)
-- );
-- create table LOGIN_DETAILS (
--                                LOGIN_ID number not null generated by default as identity,
--                                CLIENT_ID varchar(30) not null,
--                                USERNAME varchar(30) not null,
--                                PASSWORD varchar(70) not null,
--                                ACTIVATED number not null,
--                                unique (CLIENT_ID),
--                                unique (USERNAME),
--                                constraint ACTIVATED_CONSTRAINT check (ACTIVATED in (0,1)),
--                                primary key (LOGIN_ID),
--                                foreign key (CLIENT_ID)
--                                    references CLIENTS (ID)
-- );
-- create table BANK_ACCOUNT (
--                               BANKACCOUNTID number not null,
--                               USERID varchar(30) not null,
--                               CURRENCYID varchar(30) not null,
--                               DUTY number(10, 2) not null,
--                               START_DATE timestamp not null,
--                               unique (USERID),
--                               unique (CURRENCYID),
--                               primary key (BANKACCOUNTID),
--                               foreign key (USERID)
--                                   references CLIENTS (ID)
-- );
-- create table DUTY_HISTORY (
--                               RECORD_ID number not null generated by default as identity,
--                               BANK_ACCOUNT_ID number not null,
--                               PAYMENT number(10, 2) not null,
--                               TIME timestamp not null,
--                               primary key (RECORD_ID),
--                               foreign key (BANK_ACCOUNT_ID)
--                                   references BANK_ACCOUNT (BANKACCOUNTID)
-- );
-- create table TOKENS (
--                         ID number not null generated by default as identity,
--                         TOKEN_BODY varchar(50) not null,
--                         TOKEN_TYPE varchar(20) not null,
--                         CLIENT_ID varchar(30) not null,
--                         ACTIVE_FROM timestamp,
--                         ACTIVE_TO timestamp,
--                         unique (TOKEN_BODY),
--                         constraint TOKEN_CONSTRAINT
--                             check (TOKEN_TYPE in (
--                                                   'REGISTRATION', 'ACTIVATION'
--                                 )),
--                         primary key (ID),
--                         foreign key (CLIENT_ID)
--                             references CLIENTS (ID)
-- );