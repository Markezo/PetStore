package utils;

import com.github.javafaker.Faker;
import dto.OrderRequest;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

public class TestDataGenerator {

    private static final Faker faker = new Faker();

    public static OrderRequest generateFakeOrder() {
        return OrderRequest.builder()
                .id(faker.number().numberBetween(1000, 9999))
                .petId(faker.number().numberBetween(1, 100))
                .quantity(faker.number().numberBetween(1, 5))
                .shipDate(ZonedDateTime.now().format(DateTimeFormatter.ISO_INSTANT))
                .status("placed")
                .complete(faker.bool().bool())
                .build();
    }
}
