FROM mysql:5.7

COPY src/main/resources/dump.sql /docker-entrypoint-initdb.d/servlet.sql