# BackendTest

Este proyecto es una API REST en Spring Boot que expone un endpoint para obtener productos similares a uno dado, integrando servicios mock externos.

## Requisitos

- Java 21
- Docker (opcional, para mocks y tests de integración)

## Instalación y ejecución

1. **Clona el repositorio:**

   ```bash
   git clone https://github.com/memm25ua/backendDevTest.git
   cd backendDevTest/backendtest
   ```

2. **Arranca los servicios mock (opcional, recomendado para pruebas):**

   ```bash
   docker-compose up -d simulado influxdb grafana
   ```

   Esto levanta los mocks en `localhost:3001`.

3. **Compila y ejecuta la aplicación:**

   ```bash
   ./mvnw spring-boot:run
   ```

   La API estará disponible en [http://localhost:5001](http://localhost:5001)

## Endpoint principal

- `GET /product/{productId}/similar`
  - Devuelve los detalles de los productos similares a `productId`.
  - Ejemplo: [http://localhost:5001/product/1/similar](http://localhost:5001/product/1/similar)

## Documentación OpenAPI/Swagger

Accede a la documentación interactiva en:

- [http://localhost:5001/swagger-ui.html](http://localhost:5001/swagger-ui.html)

## Tests

Para ejecutar los tests de integración (requiere Docker y los mocks levantados):

```bash
cd ..
docker-compose run --rm k6 run scripts/test.js
```

Puedes ver los resultados de rendimiento en:

- [http://localhost:3000/d/Le2Ku9NMk/k6-performance-test](http://localhost:3000/d/Le2Ku9NMk/k6-performance-test)

## Notas

- El puerto de la API es configurable en `src/main/resources/application.properties` (`server.port=5001`).
- La URL base de los servicios mock también es configurable (`external.api.base-url`).
