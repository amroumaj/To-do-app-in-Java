#!/bin/bash

BASE_URL="http://localhost:8080/api/todos"

echo "=== Testing Todo API Endpoints ==="
echo

# 1. Get all todos (should be empty initially)
echo "1. GET all todos:"
curl -s -X GET "$BASE_URL" | jq .
echo

# 2. Create a new todo
echo "2. POST - Create a new todo:"
curl -s -X POST "$BASE_URL" \
  -H "Content-Type: application/json" \
  -d '{"title":"Learn Spring Boot","description":"Complete the Spring Boot tutorial"}' | jq .
echo

# 3. Create another todo
echo "3. POST - Create another todo:"
curl -s -X POST "$BASE_URL" \
  -H "Content-Type: application/json" \
  -d '{"title":"Build REST API","description":"Create a RESTful API with proper validation"}' | jq .
echo

# 4. Get all todos (should show 2 todos)
echo "4. GET all todos:"
curl -s -X GET "$BASE_URL" | jq .
echo

# 5. Get active todos (should show 2 active todos)
echo "5. GET active todos:"
curl -s -X GET "$BASE_URL/active" | jq .
echo

# 6. Get completed todos (should be empty)
echo "6. GET completed todos:"
curl -s -X GET "$BASE_URL/completed" | jq .
echo

# 7. Get todo by ID (assuming first todo has ID 1)
echo "7. GET todo by ID (1):"
curl -s -X GET "$BASE_URL/1" | jq .
echo

# 8. Toggle completion of first todo
echo "8. PATCH - Toggle completion of todo (1):"
curl -s -X PATCH "$BASE_URL/1/toggle" | jq .
echo

# 9. Get completed todos (should show 1 completed todo)
echo "9. GET completed todos after toggle:"
curl -s -X GET "$BASE_URL/completed" | jq .
echo

# 10. Update a todo
echo "10. PUT - Update todo (1):"
curl -s -X PUT "$BASE_URL/1" \
  -H "Content-Type: application/json" \
  -d '{"title":"Learn Spring Boot - UPDATED","description":"Completed the Spring Boot tutorial!","completed":true}' | jq .
echo

# 11. Test validation (empty title should fail)
echo "11. POST - Test validation (empty title):"
curl -s -X POST "$BASE_URL" \
  -H "Content-Type: application/json" \
  -d '{"title":"","description":"This should fail"}' | jq .
echo

# 12. Test non-existent resource
echo "12. GET - Test non-existent todo (999):"
curl -s -X GET "$BASE_URL/999" | jq .
echo

# 13. Delete a todo
echo "13. DELETE - Delete todo (2):"
curl -s -X DELETE "$BASE_URL/2" -w "\nHTTP Status: %{http_code}\n"
echo

# 14. Get all todos (should show 1 remaining todo)
echo "14. GET all todos after deletion:"
curl -s -X GET "$BASE_URL" | jq .
echo

echo "=== API Testing Complete ==="