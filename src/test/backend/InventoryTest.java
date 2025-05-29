package backend;

import dto.InventoryResponse;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;
import utils.ApiClient;
import com.fasterxml.jackson.databind.ObjectMapper;

import static org.apache.http.HttpStatus.*;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

public class InventoryTest extends BaseApiTest {

    ObjectMapper mapper = new ObjectMapper();

    @Test
    public void testInventoryStatusCodeAndContentType() {
        Response response = ApiClient.getInventory();

        assertEquals(response.getStatusCode(), SC_OK);
        assertEquals(response.getContentType(), "application/json");

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

}