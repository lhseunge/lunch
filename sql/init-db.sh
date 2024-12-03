#!/bin/bash
set -e

echo "START INIT-DB";

psql -v ON_ERROR_STOP=1 --username "$POSTGRES_USER" --dbname "$POSTGRES_DB" <<-EOSQL
  CREATE USER seungpro WITH PASSWORD 'seungpro';
  CREATE DATABASE lunch;
  GRANT ALL PRIVILEGES ON DATABASE lunch TO seungpro;
  ALTER DATABASE lunch OWNER TO seungpro;
  CREATE SCHEMA store;

#  \connect lunch seungpro
EOSQL