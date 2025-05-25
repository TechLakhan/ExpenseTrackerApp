package co.in.HSBC.ExpenseTracker.entity;

import lombok.*;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document(collection = "Rentals")
@Data
@NoArgsConstructor
public class Rental {

    @Autowired
    private Car car;

    @Id
    private ObjectId rentalId;

    private double amount;

    @NonNull
    private String title;

    private String content;

    private LocalDateTime date;

    private double rentalDays;

    public double totalPrice() {
        return car.getPricePerDay()*rentalDays;
    }
}
