package backend;

import com.fasterxml.jackson.databind.ObjectMapper;
import dto.OrderResponse;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;
import utils.ApiClient;

import static org.apache.http.HttpStatus.*;

public class GetOrderTest extends BaseApiTest {
    ObjectMapper mapper = new ObjectMapper();

    @Test
    public void testGetOrderById_success() throws Exception {
        Response response = ApiClient.getOrderById(1);
        Assert.assertEquals(response.getStatusCode(), SC_OK);

        OrderResponse order = mapper.readValue(response.asString(), OrderResponse.class);

        Assert.assertEquals(order.getId(), 1);
        Assert.assertEquals(order.getStatus(), "placed");
    }

    @Test
    public void testGetOrderById_invalidId() {
        Response response = ApiClient.getOrderById(-1); // або 0
        Assert.assertEquals(response.getStatusCode(), SC_BAD_REQUEST);
    }

    @Test
    public void testGetOrderById_notFound() {
        Response response = ApiClient.getOrderById(99999999);
        Assert.assertEquals(response.getStatusCode(), SC_NOT_FOUND);
    }
}
