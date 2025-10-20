package com.ecommerce.microservices.order;

import com.ecommerce.microservices.order.entity.type.OrderStatusType;
import com.ecommerce.microservices.order.stubs.InventoryClientStub;
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
import org.springframework.cloud.contract.wiremock.AutoConfigureWireMock;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.utility.DockerImageName;

import java.math.BigDecimal;
import java.util.TimeZone;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureWireMock(port = 0)
class OrderServiceApplicationTests {

	@LocalServerPort
	private int port;

	@ServiceConnection
	static PostgreSQLContainer<?> postgreSQLContainer = new PostgreSQLContainer<>(DockerImageName.parse("postgres:18"));

	@BeforeEach
	void setup() {
		RestAssured.baseURI = "http://localhost";
		RestAssured.port = port;

		new InventoryClientStub().stubInventoryCall("iPhone_17_pro", 3);
	}

	@AfterEach
	void reset() {
		RestAssured.reset();
	}

	static {
		TimeZone.setDefault(TimeZone.getTimeZone("Asia/Kolkata"));
	}

	// --- Utility Methods ---

	private String createNewOrder(OrderStatusType orderStatus) {
		String requestBody = """
				{
					"skuCode": "%s",
					"price": "%s",
					"quantity": "%d",
					"orderStatus": "%s",
					"email": "%s"
				}
				""".formatted("iPhone_17_pro", 123999, 3, orderStatus, "mohammedismail@gmail.com");

		Response response = RestAssured
				.given()
				.contentType("application/json")
				.body(requestBody)
				.when()
				.post("/api/v1/order")
				.then()
				.statusCode(201)
				.extract()
				.response();

		return response.jsonPath().getString("id");
	}

	private void deleteOrder(String id) {
		RestAssured
				.given()
				.accept("application/json")
				.when()
				.delete("/api/v1/order/" + id)
				.then()
				.statusCode(204);
	}

	// --- Test Cases ---

	@Test
	@DisplayName("1. Should create order successfully")
	void shouldCreateOrder() {
		String requestBody = """
				{
					"skuCode": "iPhone_17_pro",
					"price": 123999,
					"quantity": 3,
					"orderStatus": "PLACED"
					"email": "mohammedismail@gmail.com"
				}
				""";

		RestAssured
				.given()
				.contentType("application/json")
				.body(requestBody)
				.when()
				.post("/api/v1/order")
				.then()
				.statusCode(201)
				.body("id", Matchers.notNullValue())
				.body("orderNumber", Matchers.notNullValue())
				.body("skuCode", Matchers.equalTo("iPhone_17_pro"))
				.body("price", Matchers.equalTo(123999))
				.body("quantity", Matchers.equalTo(3))
				.body("orderStatus", Matchers.equalTo(OrderStatusType.PLACED.name()))
				.body("email", Matchers.equalTo("mohammedismail@gmail.com"));
	}

	@Test
	@DisplayName("2. Should get all orders")
	void shouldGetAllOrders() {
		String id = createNewOrder(OrderStatusType.DELIVERED);

		RestAssured
				.given()
				.accept("application/json")
				.when()
				.get("/api/v1/order")
				.then()
				.statusCode(200)
				.body("size()", Matchers.greaterThanOrEqualTo(1))
				.body("[0].id", Matchers.notNullValue());

		deleteOrder(id);
	}

	@Test
	@DisplayName("3. Should get order by id")
	void shouldGetOrderById() {
		String id = createNewOrder(OrderStatusType.DELIVERED);

		RestAssured
				.given()
				.accept("application/json")
				.when()
				.get("/api/v1/order/" + id)
				.then()
				.statusCode(200)
				.body("id", Matchers.notNullValue())
				.body("orderNumber", Matchers.notNullValue())
				.body("skuCode", Matchers.equalTo("iPhone_17_pro"))
				.body("price", Matchers.equalTo(123999.0F))
				.body("quantity", Matchers.equalTo(3))
				.body("orderStatus", Matchers.equalTo(OrderStatusType.DELIVERED.name()));

		deleteOrder(id);
	}

	@Test
	@DisplayName("4. Should update order by id")
	void shouldUpdateOrderById() {
		String id = createNewOrder(OrderStatusType.SHIPPED);

		String updatedBody = """
				{
				    "orderStatus": "DELIVERED"
				}
				""";

		RestAssured
				.given()
				.contentType("application/json")
				.body(updatedBody)
				.when()
				.patch("/api/v1/order/" + id)
				.then()
				.statusCode(200)
				.body("id", Matchers.notNullValue())
				.body("orderNumber", Matchers.notNullValue())
				.body("skuCode", Matchers.equalTo("iPhone_17_pro"))
				.body("price", Matchers.equalTo(123999.0F))
				.body("quantity", Matchers.equalTo(3))
				.body("orderStatus", Matchers.equalTo(OrderStatusType.DELIVERED.name()));

		deleteOrder(id);
	}

	@Test
	@DisplayName("5. Should cancel order by id")
	void shouldCancelOrderById() {
		String id = createNewOrder(OrderStatusType.SHIPPED);

		RestAssured
				.given()
				.accept("application/json")
				.when()
				.patch("/api/v1/order/" + id + "/cancel")
				.then()
				.statusCode(200)
				.body("id", Matchers.notNullValue())
				.body("orderNumber", Matchers.notNullValue())
				.body("skuCode", Matchers.equalTo("iPhone_17_pro"))
				.body("price", Matchers.equalTo(123999.0F))
				.body("quantity", Matchers.equalTo(3))
				.body("orderStatus", Matchers.equalTo(OrderStatusType.CANCELLED.name()));

		deleteOrder(id);
	}

	@Test
	@DisplayName("6. Should get all orders by status")
	void shouldGetAllOrdersByStatus() {
		String id = createNewOrder(OrderStatusType.DELIVERED);

		RestAssured
				.given()
				.accept("application/json")
				.when()
				.get("/api/v1/order/status/DELIVERED")
				.then()
				.statusCode(200)
				.body("size()", Matchers.greaterThanOrEqualTo(1))
				.body("[0].id", Matchers.notNullValue())
				.body("[0].orderStatus", Matchers.equalTo(OrderStatusType.DELIVERED.name()));

		deleteOrder(id);
	}
}
