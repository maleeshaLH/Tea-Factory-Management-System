package lk.Ijse.thigakade.dto.tm;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class CustomerTm {
    private String id;
    private String first_name;
    private  String last_name;
    private String tel;
    private String address;
    private String city;
}
