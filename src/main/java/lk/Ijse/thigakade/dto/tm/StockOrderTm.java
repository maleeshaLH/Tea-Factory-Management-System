package lk.Ijse.thigakade.dto.tm;

import javafx.scene.control.Button;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode
public class StockOrderTm {
    private String PreparedStockId;
    private String RawStockId;
    private String Description;
    private int unitPrice;
    private int qty;
    private int total;
    private Button btn;
}
