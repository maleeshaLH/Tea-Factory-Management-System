package lk.Ijse.thigakade.model;

import lk.Ijse.thigakade.db.DbConnection;
import lk.Ijse.thigakade.dto.SalaryDto;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class SalaryModel {
    public boolean saveSalary(String s_id, String emp_id, LocalDate date,String salary) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "INSERT INTO salary VALUES(?, ?, ?, ?)";
        PreparedStatement pstm = connection.prepareStatement(sql);

        pstm.setString(1, s_id);
        pstm.setString(2, emp_id);
        pstm.setDate(3, Date.valueOf(date));
        pstm.setString(4, salary);

        boolean isSaved = pstm.executeUpdate() > 0;

        return isSaved;

    }




    public List<SalaryDto> getAllCustomers() throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "SELECT * FROM salary";
        PreparedStatement pstm = connection.prepareStatement(sql);

        List<SalaryDto> dtoList = new ArrayList<>();

        ResultSet resultSet = pstm.executeQuery();

        while (resultSet.next()) {
            String s_id = resultSet.getString(1);
            String emp_id = resultSet.getString(2);
            LocalDate date = LocalDate.parse(resultSet.getString(3));
            String  salary = resultSet.getString(4);


            var dto = new SalaryDto(s_id,emp_id,date,salary);
            dtoList.add(dto);
        }
        return dtoList;

    }


    public String generateNextOrderIds() throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "SELECT s_id FROM salary ORDER BY s_id DESC LIMIT 1";
        ResultSet resultSet = connection.prepareStatement(sql).executeQuery();

        String currentOrderId = null;

        if (resultSet.next()) {
            currentOrderId = resultSet.getString(1);
            return splitOrderId(currentOrderId);
        }
        return splitOrderId(null);

    }

    private String splitOrderId(String currentOrderId) {
        if (currentOrderId != null) {
            String[] split = currentOrderId.split("S");
            int id = Integer.parseInt(split[1]);    //008
            id++;  //9
            return "S00" + id;
        }
        return "S001";

    }
}
