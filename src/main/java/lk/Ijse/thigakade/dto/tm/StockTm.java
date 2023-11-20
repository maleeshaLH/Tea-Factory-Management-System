package lk.Ijse.thigakade.dto.tm;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data

public class StockTm {
    private String p_id;
    private String p_name;
    private String p_weight;
    private String p_qty;
}
