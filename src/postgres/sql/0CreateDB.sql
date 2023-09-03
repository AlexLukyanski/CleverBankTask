-- Database: CleverBankDB

-- DROP DATABASE IF EXISTS "CleverBankDB";

CREATE DATABASE "CleverBankDB"
    WITH
    OWNER = postgres
    ENCODING = 'UTF8'
    LC_COLLATE = 'en_US.utf8'
    LC_CTYPE = 'en_US.utf8'
    TABLESPACE = pg_default
    CONNECTION LIMIT = -1
    IS_TEMPLATE = False;
	
	-- Table: public.account

-- DROP TABLE IF EXISTS public.account;
-- Table: public.bank

-- DROP TABLE IF EXISTS public.bank;

CREATE TABLE IF NOT EXISTS public.bank
(
    b_id integer NOT NULL GENERATED ALWAYS AS IDENTITY ( INCREMENT 1 START 1 MINVALUE 1 MAXVALUE 2147483647 CACHE 1 ),
    b_name character varying COLLATE pg_catalog."default" NOT NULL,
    CONSTRAINT bank_pkey PRIMARY KEY (b_id),
    CONSTRAINT bank_b_name_key UNIQUE (b_name)
)

TABLESPACE pg_default;

ALTER TABLE IF EXISTS public.bank
    OWNER to postgres;

CREATE TABLE IF NOT EXISTS public.users
(
    ui_id integer NOT NULL GENERATED ALWAYS AS IDENTITY ( INCREMENT 1 START 1 MINVALUE 1 MAXVALUE 2147483647 CACHE 1 ),
    ui_name character varying COLLATE pg_catalog."default" NOT NULL,
    ui_surname character varying COLLATE pg_catalog."default" NOT NULL,
    ui_patronymic character varying COLLATE pg_catalog."default",
    ui_dateofbirth date NOT NULL,
    ui_phonenumber character varying COLLATE pg_catalog."default" NOT NULL,
    ui_email character varying COLLATE pg_catalog."default" NOT NULL,
    CONSTRAINT user_info_pkey PRIMARY KEY (ui_id),
    CONSTRAINT users_ui_email_key UNIQUE (ui_email),
    CONSTRAINT users_ui_phonenumber_key UNIQUE (ui_phonenumber)
)

TABLESPACE pg_default;

ALTER TABLE IF EXISTS public.users
    OWNER to postgres;

CREATE TABLE IF NOT EXISTS public.account
(
    a_id integer NOT NULL GENERATED ALWAYS AS IDENTITY ( INCREMENT 1 START 1 MINVALUE 1 MAXVALUE 2147483647 CACHE 1 ),
    a_number character varying COLLATE pg_catalog."default" NOT NULL,
    a_balance numeric NOT NULL,
    a_bank integer NOT NULL,
    a_user integer NOT NULL,
    CONSTRAINT account_pkey PRIMARY KEY (a_id),
    CONSTRAINT account_a_number_key UNIQUE (a_number),
    CONSTRAINT account_a_bank_fkey FOREIGN KEY (a_bank)
        REFERENCES public.bank (b_id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
        NOT VALID,
    CONSTRAINT account_a_user_fkey FOREIGN KEY (a_user)
        REFERENCES public.users (ui_id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
        NOT VALID
)

TABLESPACE pg_default;

ALTER TABLE IF EXISTS public.account
    OWNER to postgres;
	
	-- Table: public.transactions

-- DROP TABLE IF EXISTS public.transactions;

CREATE TABLE IF NOT EXISTS public.transactions
(
    t_id bigint NOT NULL GENERATED ALWAYS AS IDENTITY ( INCREMENT 1 START 1 MINVALUE 1 MAXVALUE 9223372036854775807 CACHE 1 ),
    t_type character varying COLLATE pg_catalog."default" NOT NULL,
    t_datetime timestamp without time zone NOT NULL,
    t_amount numeric NOT NULL,
    t_account integer NOT NULL,
    CONSTRAINT transactions_pkey PRIMARY KEY (t_id),
    CONSTRAINT transctions_t_account_fkey FOREIGN KEY (t_account)
        REFERENCES public.account (a_id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
        NOT VALID
)

TABLESPACE pg_default;

ALTER TABLE IF EXISTS public.transactions
    OWNER to postgres;
	
	-- Table: public.users

-- DROP TABLE IF EXISTS public.users;

