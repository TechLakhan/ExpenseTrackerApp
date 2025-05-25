package co.in.HSBC.ExpenseTracker.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "Cars")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Car {

    @Id
    private ObjectId carId;

    private String brand;

    private String model;

    private Double pricePerDay;

    private boolean isAvailable;


}
