package dto;

import com.fasterxml.jackson.annotation.JsonAnySetter;
import java.util.HashMap;
import java.util.Map;

public class InventoryResponse {
    private final Map<String, Integer> statusMap = new HashMap<>();

    @JsonAnySetter
    public void setStatus(String key, Integer value) {
        statusMap.put(key, value);
    }

    public Map<String, Integer> getStatusMap() {
        return statusMap;
    }

    public Integer getValue(String key) {
        return statusMap.get(key);
    }
}
