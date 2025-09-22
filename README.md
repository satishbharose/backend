# backend
Backend project for to maintain voucher system


**Backend**

   * Requires Java 17 and Maven.
   * From the `backend` folder run:

     * `mvn spring-boot:run`
   * H2 console available at `http://localhost:8080/h2-console` (JDBC URL: `jdbc:h2:mem:fooddb`).
   * Default users created by bootstrap: `admin/password` (ADMIN) and `satish/password` (USER).
   * Login endpoint: `POST /api/auth/login` with JSON `{ "username": "...", "password": "..." }`. Returns a JWT.
