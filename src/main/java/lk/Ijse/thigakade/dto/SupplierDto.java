package lk.Ijse.thigakade.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data

public class SupplierDto {
    private String s_id;
    private String s_name;
    private String s_location;
    private String s_email;
    private String s_tel;
}
