package dto;

import lombok.Data;

@Data
public class DeleteResponse {
    private int code;
    private String type;
    private String message;
}
