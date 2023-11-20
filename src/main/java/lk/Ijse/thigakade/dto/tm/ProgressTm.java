package lk.Ijse.thigakade.dto.tm;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class ProgressTm {
    private String id;
    private String date;
    private String time;
    private String amount;
}
