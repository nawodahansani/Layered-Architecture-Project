package lk.ijse.fishmarketing.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderDetailDTO {
    private String orderId;
    private String fishId;
    private int qty;
    private double unitPrice;
}
