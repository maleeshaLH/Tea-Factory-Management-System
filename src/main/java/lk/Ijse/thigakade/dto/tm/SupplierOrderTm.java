package lk.Ijse.thigakade.dto.tm;

import lombok.*;

import java.awt.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
public class SupplierOrderTm {
    private String s_id;
    private String rs_id;
    private int unit_price;
    private int qty;
    private int total;
    private Button btn;

    public SupplierOrderTm(String rs_id, String s_id, int unitPrice, int qty, int total, javafx.scene.control.Button btn) {

    }
}
