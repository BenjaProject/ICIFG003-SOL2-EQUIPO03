@echo off
docker exec -it postgres-database psql -U postgres -d petshop_db
