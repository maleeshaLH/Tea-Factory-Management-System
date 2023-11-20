package lk.Ijse.thigakade.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class EmployeeDto {
    private  String emp_id;
    private String first_name;
    private String last_name;
    private String nic;
    private String city;
    private String tel;
}
