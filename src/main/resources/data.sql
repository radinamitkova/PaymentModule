-- Oracle queries
insert into clients(id, name, email, phone_number) values('client1','Denislav D','denislav_d@email.com','0887123321');
insert into clients(id, name, email, phone_number) values('client2','Ivan I','ivan_i@email.com','0883123321');
insert into clients(id, name, email, phone_number) values('client3','Momchil A','momchil_a@email.com','0884123321');
insert into clients(id, name, email, phone_number) values('client4','Radina M','radina_m@email.com','0885123321');
insert into clients(id, name, email, phone_number) values('client5','Stefan K','stefaka@email.com','0885123821');
insert into clients(id, name, email, phone_number) values('client6','Pesho M','payment.module.drim@abv.bg','0885123329');

-- -- password: 1111
-- INSERT INTO Login_Details (client_id, username, password, is_active)
-- VALUES('client2', 'vankata', '$2a$12$faodJ7R5UNAPlZtfDE.lPOOwWFnup2mDORVZOryQ.LJvTWOkVvQLS', 0);

-- password: 2222
INSERT INTO Login_Details (client_id, username, password, is_active)
VALUES('client3', 'momchi', '$2y$10$DBv/CYm3GYFWzKlYQhh7oOtuyU5NbUdeMXZLS22zAGzCxnaUOIL5e', 0);
INSERT INTO Login_Details (client_id, username, password, is_active)
VALUES('client4', 'radi', '$2a$12$faodJ7R5UNAPlZtfDE.lPOOwWFnup2mDORVZOryQ.LJvTWOkVvQLS', 1);

INSERT INTO Tokens(token_body, token_type, client_id, active_from, active_to)
VALUES('1test2token3inside45', 'ACTIVATION', 'client3', TO_DATE('2021-10-16 16:16:16', 'YYYY-MM-DD hh24:mi:ss'), TO_DATE('2022-10-16 16:16:16', 'YYYY-MM-DD hh24:mi:ss'));

INSERT INTO DUTY_ACCOUNT(client_id, currency, duty, start_date) VALUES ('client1','EUR', 1050, TO_DATE('2022-12-29', 'YYYY-MM-DD'));
INSERT INTO DUTY_ACCOUNT(client_id, currency, duty, start_date) VALUES ('client2','EUR', 7200, TO_DATE('2021-10-13', 'YYYY-MM-DD'));
INSERT INTO DUTY_ACCOUNT(client_id, currency, duty, start_date) VALUES ('client3','GBP', 1600, TO_DATE('2021-10-16', 'YYYY-MM-DD'));
INSERT INTO DUTY_ACCOUNT(client_id, currency, duty, start_date) VALUES ('client4','USD', 6800, TO_DATE('2021-03-29', 'YYYY-MM-DD'));
INSERT INTO DUTY_ACCOUNT(client_id, currency, duty, start_date) VALUES ('client5','USD', 12000, TO_DATE('2021-08-15', 'YYYY-MM-DD'));
INSERT INTO DUTY_ACCOUNT(client_id, currency, duty, start_date) VALUES ('client6','USD', 3000, TO_DATE('2021-07-15', 'YYYY-MM-DD'));

INSERT INTO Duty_History(duty_account_id, payment, time, transaction_reference) VALUES (4, 400.23, SYSDATE, '4564651231fdwf132132');
INSERT INTO Duty_History(duty_account_id, payment, time, transaction_reference) VALUES (4, 200.7688, TO_DATE('2020-12-30 10:25:55', 'YYYY-MM-DD hh24:mi:ss'), '456465124545wf132132');
INSERT INTO Duty_History(duty_account_id, payment, time, transaction_reference) VALUES (4, 125, TO_DATE('2022-01-01 16:30:00', 'YYYY-MM-DD hh24:mi:ss'), '456487987731fdwf132132');

-- H2 queries
-- insert into CLIENTS (ID, NAME, EMAIL, PHONE_NUMBER)
-- values (
--            'client1',
--            'Denislav D',
--            'denislav.delev@email.bg',
--            '0887123321'
--        );
-- insert into CLIENTS (ID, NAME, EMAIL, PHONE_NUMBER)
-- values (
--            'client2',
--            'Ivan I',
--            'iki.bul@email.com',
--            '0883123321'
--        );
-- insert into CLIENTS (ID, NAME, EMAIL, PHONE_NUMBER)
-- values (
--            'client3',
--            'Momchil A',
--            'momchil276@email.com',
--            '0884123321'
--        );
-- insert into CLIENTS (ID, NAME, EMAIL, PHONE_NUMBER)
-- values (
--            'client4',
--            'Radina M',
--            'radina.mitkovaaaa@email.com',
--            '0885123321'
--        );
--insert into TOKENS (TOKEN_BODY, TOKEN_TYPE, CLIENT_ID)
--values (
--           'ABC123',
--           'REGISTRATION',
--           'client1'
--       );
--insert into TOKENS (TOKEN_BODY, TOKEN_TYPE, CLIENT_ID)
--values (
--           'ABC456',
--           'ACTIVATION',
--           'client1'
--       );
--insert into TOKENS (TOKEN_BODY, TOKEN_TYPE, CLIENT_ID)
--values (
--           'EFG123',
--           'REGISTRATION',
--           'client2'
--       );
--insert into TOKENS (TOKEN_BODY, TOKEN_TYPE, CLIENT_ID)
--values (
--           'EFG456',
--           'ACTIVATION',
--           'client3'
--       );
--insert into LOGIN_DETAILS (CLIENT_ID, USERNAME, PASSWORD, ACTIVATED)
--values (
--           'client1',
--           'denio',
--           '1111',
--            1
--       );
--insert into LOGIN_DETAILS (CLIENT_ID, USERNAME, PASSWORD, ACTIVATED)
--values (
--           'client2',
--           'vanio',
--           '2222',
--            0
--       );
-- insert into BANK_ACCOUNT (BANKACCOUNTID, USERID, CURRENCYID, DUTY, START_DATE)
-- values (
--            1,
--            'client1',
--            '001637107',
--            1050,
--            to_date('2020-12-30 00:00:00', 'YYYY-MM-DD hh24:mi:ss')
--        );
-- insert into BANK_ACCOUNT (BANKACCOUNTID, USERID, CURRENCYID, DUTY, START_DATE)
-- values (
--            2,
--            'client2',
--            '001829828',
--            7200,
--            to_date('2021-10-16 00:00:00', 'YYYY-MM-DD hh24:mi:ss')
--        );
-- insert into BANK_ACCOUNT (BANKACCOUNTID, USERID, CURRENCYID, DUTY, START_DATE)
-- values (
--            3,
--            'client3',
--            '001773217',
--            1600,
--            to_date('2021-10-20 00:00:00', 'YYYY-MM-DD hh24:mi:ss')
--        );
-- insert into BANK_ACCOUNT (BANKACCOUNTID, USERID, CURRENCYID, DUTY, START_DATE)
-- values (
--            4,
--            'client4',
--            '001192618',
--            6800,
--            to_date('2021-10-21 00:00:00', 'YYYY-MM-DD hh24:mi:ss')
--        );
-- insert into DUTY_HISTORY (BANK_ACCOUNT_ID, PAYMENT, TIME)
-- values (
--            1,
--            400.23,
--            SYSDATE
--        );
-- insert into DUTY_HISTORY (BANK_ACCOUNT_ID, PAYMENT, TIME)
-- values (
--            1,
--            200.7688,
--            to_date('2020-12-30 10:25:55', 'YYYY-MM-DD hh24:mi:ss')
--        );
-- insert into DUTY_HISTORY (BANK_ACCOUNT_ID, PAYMENT, TIME)
-- values (
--            2,
--            125,
--            to_date('2022-01-01 16:30:00', 'YYYY-MM-DD hh24:mi:ss')
--        );