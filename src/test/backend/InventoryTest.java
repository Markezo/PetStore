import dto.InventoryResponse;
import dto.OrderRequest;
import dto.OrderResponse;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;
import utils.ApiClient;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

public class InventoryTest {

    OrderRequest order;

    @Test
    public void testInventoryStatusCodeAndContentType() {
        Response response = ApiClient.getInventory();

        assertEquals(response.getStatusCode(), 200);
        assertEquals(response.getContentType(), "application/json");

        ObjectMapper mapper = new ObjectMapper();
        try {
            InventoryResponse inventory = mapper.readValue(response.asString(), InventoryResponse.class);

            assertTrue(inventory.getStatusMap().containsKey("available"));
            assertTrue(inventory.getStatusMap().containsKey("sold"));

            Integer available = inventory.getValue("available");
            System.out.println("Available count: " + available);
            assertTrue(available >= 0);

        } catch (Exception e) {
            Assert.fail("JSON parsing went wrong: " + e.getMessage());
        }
    }

    @Test
    public void testCreateValidOrder() throws Exception {
        order = OrderRequest.builder()
                .id(2)
                .petId(0)
                .quantity(1)
                .shipDate(ZonedDateTime.now().format(DateTimeFormatter.ISO_INSTANT))
                .status("placed")
                .complete(true)
                .build();

        Response response = ApiClient.postOrder(order);

        assertEquals(response.getStatusCode(), 200);

        ObjectMapper mapper = new ObjectMapper();
        OrderResponse orderResponse = mapper.readValue(response.asString(), OrderResponse.class);

        assertEquals(orderResponse.getId(), order.getId());
        assertEquals(orderResponse.getStatus(), "placed");
        assertTrue(orderResponse.isComplete());
    }

    @Test
    public void testCreateInvalidOrder() {
        Response response = RestAssured
                .given()
                .baseUri("https://petstore.swagger.io/v2")
                .basePath("/store/order")
                .contentType("application/json")
                .accept("application/json")
                .body("{}") // або null
                .post()
                .then()
                .extract()
                .response();

        assertTrue(response.getStatusCode() == 400 || response.getStatusCode() == 500);
    }

    @Test
    public void testGetOrderById_success() throws Exception {
        long orderId = 1;

        Response response = ApiClient.getOrderById(orderId);
        Assert.assertEquals(response.getStatusCode(), 200);

        ObjectMapper mapper = new ObjectMapper();
        OrderResponse order = mapper.readValue(response.asString(), OrderResponse.class);

        Assert.assertEquals(order.getId(), orderId);
        Assert.assertEquals(order.getStatus(), "placed");
    }

    @Test
    public void testGetOrderById_invalidId() {
        Response response = ApiClient.getOrderById(-1); // або 0
        Assert.assertEquals(response.getStatusCode(), 400);
    }

    @Test
    public void testGetOrderById_notFound() {
        Response response = ApiClient.getOrderById(99999999);
        Assert.assertEquals(response.getStatusCode(), 404);
    }
}
