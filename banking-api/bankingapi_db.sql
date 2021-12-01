create user bankingapi with password 'password';
create database bankingapidb with template=template0 owner=bankingapi;
alter default privileges grant all on tables to bankingapi;
alter default privileges grant all on sequences to bankingapi;

CREATE TABLE public.ba_users (
	user_id int4 NOT NULL,
	tckn varchar NOT NULL,
	first_name varchar(50) NOT NULL,
	last_name varchar(50) NOT NULL,
	email varchar(50) NOT NULL,
	"password" text NOT NULL,
	CONSTRAINT ba_users_pkey PRIMARY KEY (user_id)
);

CREATE TABLE public.ba_accounts (
	account_id int4 NOT NULL,
	title varchar(50) NOT NULL,
	description varchar(50) NOT NULL,
	user_id int4 NOT NULL,
	balance int4 NULL DEFAULT 0,
	currency varchar(5) NULL DEFAULT 'TRY'::character varying,
	CONSTRAINT ba_accounts_pkey PRIMARY KEY (account_id),
	CONSTRAINT account_users_fk FOREIGN KEY (user_id) REFERENCES public.ba_users(user_id)
);
CREATE INDEX ba_accounts_account_id_idx ON public.ba_accounts USING btree (account_id, user_id, title, description, balance, currency);

CREATE TABLE public.ba_transactions (
	transaction_id int4 NOT NULL,
	sndr_account_id int4 NOT NULL,
	sndr_user_id int4 NOT NULL,
	rcpt_account_id int4 NOT NULL,
	rcpt_user_id int4 NOT NULL,
	amount int4 NOT NULL,
	note varchar(50) NOT NULL,
	transaction_date int8 NOT NULL,
	CONSTRAINT ba_transactions_pkey PRIMARY KEY (transaction_id),
	CONSTRAINT trans_act_fk FOREIGN KEY (sndr_account_id) REFERENCES public.ba_accounts(account_id),
	CONSTRAINT trans_users_fk FOREIGN KEY (sndr_user_id) REFERENCES public.ba_users(user_id)
);

