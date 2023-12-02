package lk.Ijse.thigakade.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class PreparedstockDto {
    private String p_id;
    private String description;
    private int unit_price;
    private int weight;
    private int qty;
}
