package lk.Ijse.thigakade.model;

import lk.Ijse.thigakade.db.DbConnection;
import lk.Ijse.thigakade.dto.CustomerDto;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CustomerModel {

    public boolean saveCustomer(CustomerDto dto) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "INSERT INTO customer VALUES(?, ?, ?, ?, ? , ?)";
        PreparedStatement pstm = connection.prepareStatement(sql);

        pstm.setString(1, dto.getId());
        pstm.setString(2, dto.getFirst_name());
        pstm.setString(3, dto.getLast_name());
        pstm.setString(4, dto.getTel());
        pstm.setString(5, dto.getAddress());
        pstm.setString(6, dto.getCity());

        boolean isSaved = pstm.executeUpdate() > 0;

        return isSaved;

    }

    public List<CustomerDto> getAllCustomers() throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "SELECT * FROM customer";
        PreparedStatement pstm = connection.prepareStatement(sql);

        List<CustomerDto> dtoList = new ArrayList<>();

        ResultSet resultSet = pstm.executeQuery();

        while (resultSet.next()) {
            String c_id = resultSet.getString(1);
            String c_first_name = resultSet.getString(2);
            String c_last_name = resultSet.getString(3);
            String c_tel = resultSet.getString(4);
            String c_address = resultSet.getString(5);
            String c_city = resultSet.getString(6);

            var dto = new CustomerDto(c_id, c_first_name, c_last_name, c_tel, c_address, c_city);
            dtoList.add(dto);
        }
        return dtoList;

    }

    public boolean updateCustomer(CustomerDto dto) throws SQLException {
       Connection connection = DbConnection.getInstance().getConnection();

        String sql = "UPDATE customer SET first_name = ?,last_name = ?, telephone_number = ?, address = ? ,city = ? WHERE id = ?";
        PreparedStatement pstm = connection.prepareStatement(sql);

        pstm.setString(1, dto.getFirst_name());
        pstm.setString(2,dto.getLast_name());
        pstm.setString(3, dto.getTel());
        pstm.setString(4, dto.getAddress());
        pstm.setString(5, dto.getCity());
        pstm.setString(6, dto.getId());

        return pstm.executeUpdate() > 0;

    }

    public static CustomerDto searchCustomer(String id) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "SELECT * FROM customer WHERE id = ?";
        PreparedStatement pstm = connection.prepareStatement(sql);
        pstm.setString(1, id);

        ResultSet resultSet = pstm.executeQuery();

        CustomerDto dto = null;

        if (resultSet.next()) {
            String c_id = resultSet.getString(1);
            String cfirst_name = resultSet.getString(2);
            String clast_name = resultSet.getString(3);
            String c_tel = resultSet.getString(4);
            String c_address = resultSet.getString(5);
            String c_city = resultSet.getString(6);

            dto = new CustomerDto(c_id, cfirst_name, clast_name, c_tel, c_address, c_city);
        }

        return dto;
    }


    public boolean deleteCustomer(String id) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "DELETE FROM customer WHERE id = ?";
        PreparedStatement pstm = connection.prepareStatement(sql);
        pstm.setString(1, id);

        return pstm.executeUpdate() > 0;

    }

    public List<CustomerDto> loadAllCustomers() throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "SELECT * FROM customer";
        ResultSet resultSet = connection.prepareStatement(sql).executeQuery();

        List<CustomerDto> cusList = new ArrayList<>();

        while (resultSet.next()) {
            cusList.add(new CustomerDto(
                    resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getString(3),
                    resultSet.getString(4),
                    resultSet.getString(5),
                    resultSet.getString(6)


            ));
        }
        return cusList;
    }


}

