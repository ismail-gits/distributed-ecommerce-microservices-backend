package com.ecommerce.microservices.product;


import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.testcontainers.containers.MongoDBContainer;
import org.testcontainers.utility.DockerImageName;

import java.math.BigDecimal;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ProductServiceApplicationTests {

	@LocalServerPort
	private int port;

	@ServiceConnection
	static MongoDBContainer mongoDBContainer = new MongoDBContainer(DockerImageName.parse("mongo:8.0"));

	@BeforeEach
	void setup() {
		RestAssured.baseURI = "http://localhost";
		RestAssured.port = port;
	}

	@AfterEach
	void reset() {
		RestAssured.reset();
	}

	// Spring boot 3.2+ starts this container automatically now
//	static  {
//		mongoDBContainer.start();
//	}

	// --- Utility Methods ---
	private String createProduct(BigDecimal price) {
		String requestBody = """
				{
				    "name": "%s",
				    "description": "%s",
				    "price": "%s"
				}
				""".formatted("iPhone 17 pro max", "iPhone 17 pro max is the latest and most advanced launch from Apple with multiple AI features and integrations", price);

		Response response = RestAssured
				.given()
				.contentType("application/json")
				.body(requestBody)
				.when()
				.post("/api/v1/product")
				.then()
				.statusCode(201)
				.extract()
				.response();

		return response.jsonPath().getString("id");
	}

	private void deleteProduct(String id) {
		RestAssured
				.given()
				.accept("application/json")
				.when()
				.delete("/api/v1/product/" + id)
				.then()
				.statusCode(204);
	}

	// --- Test Cases ---

	@Test
	@DisplayName("1. Should create product successfully")
	void shouldCreateProduct() {
		String requestBody = """
				{
				    "name": "iPhone 17 pro max",
				    "description": "iPhone 17 pro max is the latest and most advanced launch from Apple with multiple AI features and integrations",
				    "price": "179000"
				}
				""";

		RestAssured
				.given()
				.contentType("application/json")
				.body(requestBody)
				.when()
				.post("/api/v1/product")
				.then()
				.statusCode(201)
				.body("id", Matchers.notNullValue())
				.body("name", Matchers.equalTo("iPhone 17 pro max"))
				.body("description", Matchers.equalTo("iPhone 17 pro max is the latest and most advanced launch from Apple with multiple AI features and integrations"))
				.body("price", Matchers.equalTo(179000));
	}

	@Test
	@DisplayName("2. Should get all products")
	void shouldGetAllProducts() {
		String id = createProduct(
				BigDecimal.valueOf(179000)
		);

		RestAssured
				.given()
				.accept("application/json")
				.when()
				.get("api/v1/product")
				.then()
				.statusCode(200)
				.body("size()", Matchers.greaterThanOrEqualTo(1))
				.body("[0].name", Matchers.notNullValue());

		deleteProduct(id);
	}

	@Test
	@DisplayName("3. Should get product by id")
	void shouldGetProductById() {
		String id = createProduct(
				BigDecimal.valueOf(179000)
		);

		RestAssured
				.given()
				.accept("application/json")
				.when()
				.get("/api/v1/product/" + id)
				.then()
				.statusCode(200)
				.body("id", Matchers.equalTo(id))
				.body("name", Matchers.equalTo("iPhone 17 pro max"))
				.body("description", Matchers.equalTo("iPhone 17 pro max is the latest and most advanced launch from Apple with multiple AI features and integrations"))
				.body("price", Matchers.equalTo(179000));

		deleteProduct(id);
	}

	@Test
	@DisplayName("4. Should update product by id (PUT)")
	void shouldUpdateProductById() {
		String id = createProduct(
				BigDecimal.valueOf(179000)
		);

		String updatedBody = """
				{
				    "name": "iPhone 20 pro max",
				    "description": "iPhone 20 pro max is the latest and most advanced launch from Apple with multiple AI features and integrations",
				    "price": "132000"
				}
				""";

		RestAssured
				.given()
				.contentType("application/json")
				.body(updatedBody)
				.when()
				.put("/api/v1/product/" + id)
				.then()
				.statusCode(200)
				.body("id", Matchers.equalTo(id))
				.body("name", Matchers.equalTo("iPhone 20 pro max"))
				.body("description", Matchers.equalTo("iPhone 20 pro max is the latest and most advanced launch from Apple with multiple AI features and integrations"))
				.body("price", Matchers.equalTo(132000));

		deleteProduct(id);
	}

	@Test
	@DisplayName("5. Should partially update product by id (PATCH)")
	void shouldUpdateProductByIdPartial() {
		String id = createProduct(
				BigDecimal.valueOf(179000)
		);

		String patchBody = """
				{
				    "price": "239999"
				}
				""";

		RestAssured
				.given()
				.contentType("application/json")
				.body(patchBody)
				.when()
				.patch("/api/v1/product/" + id)
				.then()
				.statusCode(200)
				.body("price", Matchers.equalTo(239999));

		deleteProduct(id);
	}

	@Test
	@DisplayName("6. Should delete product by id")
	void deleteProductById() {
		String id = createProduct(
				BigDecimal.valueOf(179000)
		);

		RestAssured
				.given()
				.accept("application/json")
				.when()
				.delete("/api/v1/product/" + id)
				.then()
				.statusCode(204);

		RestAssured
				.given()
				.accept("application/json")
				.when()
				.delete("/api/v1/product/" + id)
				.then()
				.statusCode(404);
	}
}
