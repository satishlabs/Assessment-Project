"# Assessment-Project" 

Microservices API Documentation
API Endpoints
Product Service
•	GET /admin/products/category/electronics?sortBy={price} – Fetch products in a category electronics.
•	GET /admin/products/category/menShirts?sortBy={availability} - Fetch products in a category menShirts.
•	/admin/products/category/electronics?sortBy=price
•	GET /admin/products/{id} – Get product details.
•	POST /admin/products – Add a new product.
•	DELETE /admin/products/{id} – Remove a product.
•	PUT /admin/products/{id} – Update product details.
Inventory Service
•	PATCH /inventory/{productId} – Update inventory.
•	GET /inventory/{productId} – Fetch inventory details.



1. Product Service (Port: 8081)
1.1 Add a Product
Endpoint: POST /admin/products
Request:
curl --location 'http://localhost:8081/admin/products' \
--header 'Content-Type: application/json' \
--data '{
      "name": "Men\'s Oxford Shirt",
      "brand": "Brand Name",
      "description": "Classic oxford shirt for casual or dressy occasions.",
      "price": {
        "currency": "USD",
        "amount": 54.99
      },
      "inventory": {
        "total": 60,
        "available": 50,
        "reserved": 10
      },
      "category": "menShirts",
      "attributes": [
        {
          "name": "Color",
          "value": "White"
        },
        {
          "name": "Size",
          "value": "L"
        }
      ]
    }'
1.2 Delete a Product
Endpoint: DELETE /admin/products/{id}
Request:
curl --location --request DELETE 'http://localhost:8081/admin/products/1'

1.3 Get Products by Category (Sorted by Availability)
Endpoint: GET /admin/products/category/{category}?sortBy=availability
Request:
curl --location 'http://localhost:8081/admin/products/category/menShirts?sortBy=availability'
1.4 Get Products by Category (Sorted by Price)
Endpoint: GET /admin/products/category/{category}?sortBy=price
Request:
curl --location 'http://localhost:8081/admin/products/category/electronics?sortBy=price'
1.5 Update a Product
Endpoint: PUT /admin/products/{id}
Request:
curl --location --request PUT 'http://localhost:8081/admin/products/8' \
--header 'Content-Type: application/json' \
--data '{
  "name": "Smartphone X",
  "brand": "Brand Name",
  "description": "Updated smartphone model",
  "price": {
    "currency": "USD",
    "amount": 899.99
  },
  "inventory": {
    "total": 50,
    "available": 45,
    "reserved": 5
  },
  "category": "electronics",
  "attributes": [
    {
      "name": "Color",
      "value": "Black"
    },
    {
      "name": "Storage",
      "value": "256GB"
    }
  ]
}'

1.6 Update a Product by price
Endpoint: PUT /admin/products/{id}
Request:
curl --location --request GET 'http://localhost:7000/inventory/7' \
--header 'Content-Type: application/json' \
--data '{
   "total": 0,
    "available": 25,
    "reserved": 25
}'

2.2 Add Inventory for a Product
Endpoint: POST /inventory
Request:
curl --location 'http://localhost:7000/inventory' \
--header 'Content-Type: application/json' \
--data '{"productId":3, "total":0, "available":0, "reserved":0}'
2.3 Get Inventory with Authorization
Endpoint: GET /inventory/{productId}
Request:
curl --location --request GET 'http://localhost:7000/inventory/11' \
--header 'Content-Type: application/json' \
--data '{
  "access_token": "eyJhbGciOiJIUzI1NiIsInR5c..."
}'
	
