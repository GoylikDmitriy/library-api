# Library Web API

This project is a CRUD Web API developed using **Spring Cloud, Spring MVC, Spring Data JPA, PostgreSQL, Docker**, to simulate a library system. It allows to perform various operations such as creating, updating, deleting, and retrieving book information. 

## Project Setup

To run the project:

1. Clone this repository:
   
   ```bash
   git clone https://github.com/GoylikDmitriy/library-api.git
   ```

2. Start the project using the following command in root folder:

   ```docker-compose
   docker-compose up
   ```

## Swagger Documentation
### Book-Service

To access the Swagger documentation for the book-service use the following URLs: `/api/book/swagger-ui.html` and `/api/book/v3/api-docs` for docs.

### Library-Service

To access the Swagger documentation for the library-service use the following URLs: `/api/library/swagger-ui.html` and `/api/library/v3/api-docs` for docs.

## Main Functionality (book-service)

The Web API provides the following main functionality for managing books:

1. Retrieve a list of all books: GET `/api/book/all`

2. Retrieve a specific book by its ID: GET `/api/book/{id}`

3. Retrieve a book by its ISBN: GET `/api/book/isbn/{isbn}`

4. Add a new book: POST `/api/book/add`
   - also send ID of the added book to the library-service.

5. Update information about an existing book: PUT `/api/book/{id}`

6. Delete a book: DELETE `/api/book/{id}`

## Additional Functionality (library-service)

In addition to the main CRUD operations, the Web API includes an additional service called library-service for tracking the availability of books. The functionality is as follows:

1. When a new book is added using the book-service, a request containing the book ID is sent to the library-service.
2. The library-service stores the following information for each book:
   - Book ID.
   - Time when the book was borrowed.
   - Time when the book needs to be returned.

The Web API provides the following additional functionality:

1. Retrieve a list of available books: GET `/api/library/available-books`
     
2. Update borrowed and return times of the book with specified ID: PUT `/api/library/{id}`
