# BlogPlatform-API

BlogPlatform-API is a Spring Boot-based RESTful API server for managing a blogging platform. It provides endpoints for users, posts, comments, authentication, and other core features needed to build and scale a modern blog application.

## Features

- **User Management:** Register, authenticate, update profiles, and manage user roles.
- **Post Management:** Create, update, delete, and list blog posts with tagging and categorization.
- **Comment System:** Add, edit, delete, and moderate comments on posts.
- **Authentication & Authorization:** Secure JWT-based authentication and role-based access control.
- **Search & Filtering:** Easily search and filter posts by keywords, tags, or categories.
- **RESTful Endpoints:** Standard REST conventions for easy client integration.
- **Extensible:** Modular structure for adding more features.

## Getting Started

### Prerequisites

- Java 17+
- Maven 3.6+
- (Optional) Docker

### Installation

1. **Clone the repository:**
   ```sh
   git clone https://github.com/naman-sriv/BlogPlatform-API.git
   cd BlogPlatform-API
   ```

2. **Build the project:**
   ```sh
   mvn clean install
   ```

3. **Run the application:**
   ```sh
   mvn spring-boot:run
   ```

   The server will start on `http://localhost:8080`.

### Environment Configuration

Configure application settings in `src/main/resources/application.properties`:

- Database connection (default: H2, can be switched to MySQL/PostgreSQL)
- JWT secret keys
- Mail settings for notifications (optional)

## API Reference

Explore the API endpoints using [Swagger UI](http://localhost:8080/swagger-ui/) once the server is running.

- `POST /api/auth/register` - Register a new user
- `POST /api/auth/login` - Authenticate and get JWT token
- `GET /api/posts` - List all posts
- `POST /api/posts` - Create a new post
- `GET /api/posts/{id}` - Get post details
- `PUT /api/posts/{id}` - Update post
- `DELETE /api/posts/{id}` - Delete post
- `POST /api/posts/{id}/comments` - Add comment to post
- ...and more

See the [API Docs](http://localhost:8080/swagger-ui/) for full details.

## Project Structure

```
src/main/java/com/learning/blogplatformapi/
├── controller/        # REST controllers
├── model/             # JPA entities & DTOs
├── repository/        # Spring Data repositories
├── service/           # Business logic
├── security/          # Security & JWT
└── BlogPlatformApiApplication.java
```

## Contributing

Contributions are welcome! Please open issues or submit pull requests for improvements.

1. Fork the repo
2. Create your feature branch (`git checkout -b feature/my-feature`)
3. Commit your changes (`git commit -am 'Add some feature'`)
4. Push to the branch (`git push origin feature/my-feature`)
5. Open a Pull Request

## License

This project is licensed under the MIT License.

## Maintainer

- [Naman Srivastava](https://github.com/naman-sriv)

## Acknowledgements

- Spring Boot
- Swagger/OpenAPI
- JWT
- H2/MySQL/PostgreSQL
