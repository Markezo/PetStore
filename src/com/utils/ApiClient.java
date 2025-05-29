package utils;

import config.EnvironmentConfig;
import dto.OrderRequest;
import io.restassured.RestAssured;
import io.restassured.response.Response;

public class ApiClient {

    private static final String BASE_URI = EnvironmentConfig.getBaseUrl();
    private static final String STORE_ORDER = "/store/order";
    private static final String STORE_INVENTORY = "/store/inventory";

    public static Response postOrder(OrderRequest order) {
        return RestAssured
                .given()
                .baseUri(BASE_URI)
                .basePath(STORE_ORDER)
                .contentType("application/json")
                .accept("application/json")
                .body(order)
                .when()
                .post()
                .then()
                .extract()
                .response();
    }

    public static Response getOrderById(int orderId) {
        return RestAssured
                .given()
                .baseUri(BASE_URI)
                .basePath(STORE_ORDER + "/" + orderId)
                .accept("application/json")
                .when()
                .get()
                .then()
                .extract()
                .response();
    }

    public static Response deleteOrderById(int orderId) {
        return RestAssured
                .given()
                .baseUri(BASE_URI)
                .basePath(STORE_ORDER + "/" + orderId)
                .accept("application/json")
                .when()
                .delete()
                .then()
                .extract()
                .response();
    }

    public static Response getInventory() {
        return RestAssured
                .given()
                .baseUri(BASE_URI)
                .basePath(STORE_INVENTORY)
                .accept("application/json")
                .when()
                .get()
                .then()
                .extract()
                .response();
    }
}
