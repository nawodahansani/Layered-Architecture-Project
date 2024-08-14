package lk.ijse.fishmarketing.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class FishSpecies {
    private String id;
    private String name;
    private double marketPrice;
    private int qty;
    private String eId;
}
