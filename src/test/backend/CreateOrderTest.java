package backend;

import com.fasterxml.jackson.databind.ObjectMapper;
import dto.OrderRequest;
import dto.OrderResponse;
import io.restassured.response.Response;
import org.testng.annotations.Test;
import utils.ApiClient;
import utils.TestDataGenerator;

import static org.apache.http.HttpStatus.*;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

public class CreateOrderTest extends BaseApiTest{
    ObjectMapper mapper = new ObjectMapper();
    OrderRequest order;

    @Test
    public void testCreateValidOrder() throws Exception {
        order = TestDataGenerator.generateFakeOrder();

        Response response = ApiClient.postOrder(order);

        assertEquals(response.getStatusCode(), SC_OK);
        OrderResponse orderResponse = mapper.readValue(response.asString(), OrderResponse.class);

        assertEquals(orderResponse.getId(), order.getId());
        assertEquals(orderResponse.getStatus(), "placed");
        assertTrue(orderResponse.isComplete());
    }

    @Test
    public void testCreateInvalidOrder() {
        Response response = ApiClient.postOrder(null);

        assertTrue(response.getStatusCode() == SC_BAD_REQUEST || response.getStatusCode() == SC_INTERNAL_SERVER_ERROR);
    }
}