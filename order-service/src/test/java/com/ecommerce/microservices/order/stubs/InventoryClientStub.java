package com.ecommerce.microservices.order.stubs;

import static com.github.tomakehurst.wiremock.client.WireMock.*;

public class InventoryClientStub {

  public void stubInventoryCall(String skuCode, Integer quantity) {
    String responseBody = """
        {
          "skuCode": "%s",
          "inStock": true,
          "requestedQuantity": %d,
          "availableQuantity": 100,
          "message": "Stock available"
        }
        """.formatted(skuCode, quantity);

    stubFor(
        get(urlEqualTo("/api/v1/inventory?skuCode=%s&quantity=%d".formatted(skuCode, quantity)))
            .willReturn(aResponse()
                .withStatus(201)
                .withHeader("Content-Type", "application/json")
                .withBody(responseBody)
            )
    );
  }
}
