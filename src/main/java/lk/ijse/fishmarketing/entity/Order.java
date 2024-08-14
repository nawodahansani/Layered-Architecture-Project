package lk.ijse.fishmarketing.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Order {
    private String orderId;
    private Date date;
    private String customerId;

}
