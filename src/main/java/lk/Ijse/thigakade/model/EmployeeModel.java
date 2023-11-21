package lk.Ijse.thigakade.model;

import lk.Ijse.thigakade.db.DbConnection;
import lk.Ijse.thigakade.dto.EmployeeDto;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class EmployeeModel {
    public static boolean deleteEmployee(String emp_id) throws SQLException {
        Connection connection =  DbConnection.getInstance().getConnection();

        String sql="DELETE FROM employee WHERE emp_id =?";
        PreparedStatement pstm = connection.prepareStatement(sql);
        pstm.setString(1,emp_id);

        return pstm.executeUpdate() >0;


    }

    public  List<EmployeeDto> loadAllEmployee() throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "SELECT * FROM employee";
        ResultSet resultSet = connection.prepareStatement(sql).executeQuery();

        List<EmployeeDto> empList = new ArrayList<>();

        while (resultSet.next()) {
            empList.add(new EmployeeDto(
                    resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getString(3),
                    resultSet.getString(4),
                    resultSet.getString(5),
                    resultSet.getString(6)
            ));
        }
        return empList;
    }



    public boolean saveEmployee(EmployeeDto dto) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "INSERT INTO employee VALUES(?,?,?,?,?,?)";
        PreparedStatement pstm = connection.prepareStatement(sql);

        pstm.setString(1,dto.getEmp_id());
        pstm.setString(2,dto.getFirst_name());
        pstm.setString(3,dto.getLast_name());
        pstm.setString(4,dto.getNic());
        pstm.setString(5,dto.getCity());
        pstm.setString(6,dto.getTel());

        boolean isSaved = pstm.executeUpdate() >0 ;

        return isSaved;

    }

    public EmployeeDto searchEmployee(String emp_id) throws SQLException {

        Connection connection=DbConnection.getInstance().getConnection();

        String sql = "SELECT * FROM employee WHERE emp_id = ?";
        PreparedStatement pstm = connection.prepareStatement(sql);
        pstm.setString(1,emp_id);

        ResultSet resultSet = pstm.executeQuery();

        EmployeeDto dto = null;

        if (resultSet.next()){
            String e_id = resultSet.getString(1);
            String e_first_name = resultSet.getString(2);
            String e_last_name = resultSet.getString(3);
            String e_nic = resultSet.getString(4);
            String e_city = resultSet.getString(5);
            String e_tel = resultSet.getString(6);

            dto = new EmployeeDto(e_id,e_first_name,e_last_name,e_nic,e_city,e_tel);
        }
        return dto;

    }

    public boolean updateEmployee(EmployeeDto dto) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();;

        String sql = "UPDATE employee SET first_name = ?,last_name = ?,nic = ?,city = ?,Contact_no =? WHERE emp_id= ?";
        PreparedStatement pstm = connection.prepareStatement(sql);

        pstm.setString(1,dto.getFirst_name());
        pstm.setString(2,dto.getLast_name());
        pstm.setString(3,dto.getNic());
        pstm.setString(4,dto.getCity());
        pstm.setString(5,dto.getTel());
        pstm.setString(6,dto.getEmp_id());

        return pstm.executeUpdate() >0 ;

    }



    public List<EmployeeDto> getAllEmployee() throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "SELECT * FROM employee";

        PreparedStatement pstm = connection.prepareStatement(sql);

        List<EmployeeDto> dtoList = new ArrayList<>();

        ResultSet resultSet = pstm.executeQuery();

        while (resultSet.next()){
            String e_id = resultSet.getString(1);
            String e_first_name = resultSet.getString(2);
            String  e_last_name = resultSet.getString(3);
            String e_nic = resultSet.getString(4);
            String e_city = resultSet.getString(5);
            String  e_tel = resultSet.getString(6);
            var dto = new EmployeeDto(e_id,e_first_name,e_last_name,e_nic,e_city,e_tel);
            dtoList.add(dto);

        }
        return dtoList;

    }
}
