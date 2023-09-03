FROM postgres:14

COPY sql/0CreateDB.sql /docker-entrypoint-initdb.d/
COPY sql/1INSERT_BANK_SQL.sql /docker-entrypoint-initdb.d/
COPY sql/2INSERT_USER_SQL.sql /docker-entrypoint-initdb.d/
COPY sql/3INSERT_ACCOUNT_SQL.sql /docker-entrypoint-initdb.d/
