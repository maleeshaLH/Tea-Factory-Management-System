package lk.Ijse.thigakade.model;

import lk.Ijse.thigakade.db.DbConnection;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;

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


   /* public List<SalaryDto> getAllSalarys() throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "SELECT * FROM salary";
        PreparedStatement pstm = connection.prepareStatement(sql);

        List<SalaryDto> dtoList = new ArrayList<>();

        ResultSet resultSet =pstm.executeQuery();

        while (resultSet.next()){
            String s_id = resultSet.getString(1);
            String emp_id =resultSet.getString(2);
            Date date = resultSet.getDate(3);
            String s_salary = resultSet.getString(4);

            var dto = new SalaryDto(s_id,emp_id,date,s_salary);
            dtoList.add(dto);
        }
            return dtoList;

    }*/
}
