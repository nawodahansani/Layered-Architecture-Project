package lk.ijse.fishmarketing.tm;

import javafx.scene.control.Button;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class CartTm {
    private String fishCode;
    private String fishName;
    private int qty;
    private double unitPrice;
    private double total;
    private Button btnRemove;
}
