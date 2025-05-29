package dto;

import lombok.Data;

@Data
public class OrderResponse {
    private long id;
    private long petId;
    private int quantity;
    private String shipDate;
    private String status;
    private boolean complete;
}
