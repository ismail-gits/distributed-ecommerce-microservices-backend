package com.ecommerce.microservices.inventory;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import io.restassured.RestAssured;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.utility.DockerImageName;

import java.util.TimeZone;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class InventoryServiceApplicationTests {

	@LocalServerPort
	private int port;

	@ServiceConnection
	static PostgreSQLContainer<?> postgreSQLContainer = new PostgreSQLContainer<>(DockerImageName.parse("postgres:18"));

	@BeforeEach
	void setup() {
		RestAssured.baseURI = "http://localhost";
		RestAssured.port = port;
	}

	@AfterEach
	void reset() {
		RestAssured.reset();
	}

	static {
		TimeZone.setDefault(TimeZone.getTimeZone("Asia/Kolkata"));
	}

	@Test
	@DisplayName("1. Should check if stock exists in inventory")
	void shouldCheckIsInStock() {
		RestAssured
				.given()
				.accept("application/json")
				.when()
				.get("/api/v1/inventory?skuCode=iphone_17_pro_max&quantity=10")
				.then()
				.statusCode(200)
				.body("skuCode", Matchers.equalTo("iphone_17_pro_max"))
				.body("inStock", Matchers.equalTo(true))
				.body("requestedQuantity", Matchers.equalTo(10))
				.body("availableQuantity", Matchers.equalTo(15))
				.body("message", Matchers.equalTo("Stock available"));
	}
}
