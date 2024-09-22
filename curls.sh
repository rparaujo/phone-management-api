#!/bin/bash

curl -X POST "http://localhost:8080/api/v1/phonerecords" \
     -H "Content-Type: application/json" \
     -d '{"name":"John Doe", "phoneNumber":"4155551234"}'

sleep 1

curl -X POST "http://localhost:8080/api/v1/phonerecords" \
     -H "Content-Type: application/json" \
     -d '{"name":"Jane Doe", "phoneNumber":"2025550173"}'

sleep 1

curl -X POST "http://localhost:8080/api/v1/phonerecords" \
     -H "Content-Type: application/json" \
     -d '{"name":"Adam Something", "phoneNumber":"4155550198"}'

sleep 1

# Get all phone records
echo "Getting all records"
curl -X GET "http://localhost:8080/api/v1/phonerecords" \
     -H "Content-Type: application/json"

sleep 1
echo
echo

# Get a phone record by ID
echo "Getting the record with ID=3"
curl -X GET "http://localhost:8080/api/v1/phonerecords/3" \
     -H "Content-Type: application/json"
