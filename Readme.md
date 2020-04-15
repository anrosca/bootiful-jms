**Java message service intro**

*To run the PostgreSQL container, use:*
`docker run --name weather-reports -p 5432:5432 -e POSTGRES_PASSWORD=postgres -e POSTGRES_DB=weather-reports -d postgres  --max_prepared_transactions=100`

*To run the ActiveMQ container, use:*
`docker run -d rmohr/activemq`

*To migrate the db, use*
`mvn flyway:migrate`

*To start the app, use*
`mvn spring-boot:run`
