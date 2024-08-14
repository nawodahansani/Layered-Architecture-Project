package lk.ijse.fishmarketing.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class FishSpeciesDTO {
    private String id;
    private String name;
    private int qty;
    private double marketPrice;
    private String eId;
}
