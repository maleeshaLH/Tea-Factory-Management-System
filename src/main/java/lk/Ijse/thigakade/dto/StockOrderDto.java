package lk.Ijse.thigakade.dto;

import lk.Ijse.thigakade.dto.tm.StockOrderTm;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class StockOrderDto {
    private String P_id;
    private String rs_id;
    private  String description;
    private  int unitPrice;
    private  int weight;
    private  int qty;
    private List<StockOrderTm> tmList =new ArrayList<>();
}
