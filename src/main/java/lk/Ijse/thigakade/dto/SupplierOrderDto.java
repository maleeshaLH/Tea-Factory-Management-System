package lk.Ijse.thigakade.dto;

import lk.Ijse.thigakade.dto.tm.SupplierOrderTm;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class SupplierOrderDto {
    private String s_id;
    private String su_id;
    private String description;
    private Integer unit_price;
    private Integer weight;
    private Integer qty;
    private List<SupplierOrderTm> tmList = new ArrayList<>();



}
