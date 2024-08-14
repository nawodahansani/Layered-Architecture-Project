package lk.ijse.fishmarketing.tm;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class CustomerTm {
    private String id;
    private String name;
    private String address;
    private String tel;
}
