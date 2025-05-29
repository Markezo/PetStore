package backend;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.javafaker.Faker;
import dto.DeleteResponse;
import dto.OrderRequest;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;
import utils.ApiClient;
import utils.TestDataGenerator;

import static org.apache.http.HttpStatus.*;
import static org.testng.Assert.assertEquals;

public class DeleteOrderTest extends BaseApiTest{
    ObjectMapper mapper = new ObjectMapper();
    OrderRequest order;
    Faker faker;

    @Test
    public void testDeleteOrderById() throws Exception {
        order = TestDataGenerator.generateFakeOrder();
        Response createResponse = ApiClient.postOrder(order);
        assertEquals(createResponse.getStatusCode(), SC_OK);

        Response deleteResponse = ApiClient.deleteOrderById(order.getId());
        assertEquals(deleteResponse.getStatusCode(), SC_OK);

        DeleteResponse parsed = mapper.readValue(deleteResponse.asString(), DeleteResponse.class);

        assertEquals(parsed.getCode(), SC_OK);
        assertEquals(parsed.getMessage(), String.valueOf(order.getId()));
        assertEquals(parsed.getType(), "unknown");
    }

    @Test
    public void testDeleteOrderInvalidId() {
        Response response = ApiClient.deleteOrderById(-1);
        Assert.assertEquals(response.getStatusCode(), SC_BAD_REQUEST);
    }

    @Test
    public void testDeleteOrderNotFound() {
        Response response = ApiClient.deleteOrderById(faker.number().numberBetween(200, 2000)); //just an assumption that there is no orders with such ids
        Assert.assertEquals(response.getStatusCode(), SC_NOT_FOUND);
    }
}