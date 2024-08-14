package lk.ijse.fishmarketing.tm;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class FishSpeciesTm {
    private String id;
    private String name;
    private double marketPrice;
    private int qty;
    private String eId;
}
