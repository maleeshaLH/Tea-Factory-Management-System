package lk.Ijse.thigakade.model;

import lk.Ijse.thigakade.db.DbConnection;
import lk.Ijse.thigakade.dto.SupplierDto;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SupplierModel {
    public boolean saveSupplier(SupplierDto dto) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "INSERT INTO supplier VALUES(?,?,?,?,?)";
        PreparedStatement pstm = connection.prepareStatement(sql);

        pstm.setString(1,dto.getS_id());
        pstm.setString(2,dto.getS_name());
        pstm.setString(3,dto.getS_location());
        pstm.setString(4,dto.getS_email());
        pstm.setString(5,dto.getS_tel());

        boolean isSaved = pstm.executeUpdate() >0 ;

        return isSaved;


    }

    public boolean deleteSupplier(String id) throws SQLException {
        Connection connection =  DbConnection.getInstance().getConnection();

        String sql="DELETE FROM supplier WHERE su_id =?";
        PreparedStatement pstm = connection.prepareStatement(sql);
        pstm.setString(1,id);

        return pstm.executeUpdate() >0;

    }

    public boolean updateSupplier(SupplierDto dto) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "UPDATE supplier SET name = ?,loction = ?,email = ?,telephone_number = ? WHERE su_id =?";
        PreparedStatement pstm = connection.prepareStatement(sql);

        pstm.setString(1,dto.getS_name());
        pstm.setString(2,dto.getS_location());
        pstm.setString(3,dto.getS_email());
        pstm.setString(4,dto.getS_tel());
        pstm.setString(5,dto.getS_id());


        boolean isUpdate = pstm.executeUpdate() >0 ;

        return isUpdate;

    }

    public SupplierDto searchSupplier(String id) throws SQLException {
        Connection connection=DbConnection.getInstance().getConnection();

        String sql = "SELECT * FROM supplier WHERE su_id = ?";
        PreparedStatement pstm = connection.prepareStatement(sql);
        pstm.setString(1,id);

        ResultSet resultSet = pstm.executeQuery();

        SupplierDto dto = null;

        if (resultSet.next()){
            String s_id = resultSet.getString(1);
            String s_name = resultSet.getString(2);
            String s_location = resultSet.getString(3);
            String s_email = resultSet.getString(4);
            String s_tel = resultSet.getString(5);

            dto = new SupplierDto(s_id,s_name,s_location,s_email,s_tel);
        }
        return dto;

    }


    public List<SupplierDto> getAllSupplier() throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "SELECT * FROM supplier";
        PreparedStatement pstm = connection.prepareStatement(sql);

        List<SupplierDto> dtoList = new ArrayList<>();

        ResultSet resultSet = pstm.executeQuery();

        while (resultSet.next()) {
            String s_id = resultSet.getString(1);
            String s_name = resultSet.getString(2);
            String s_location = resultSet.getString(3);
            String s_email = resultSet.getString(4);
            String s_tel = resultSet.getString(5);

            var dto = new SupplierDto(s_id,s_name,s_location,s_email,s_tel);
            dtoList.add(dto);
        }
        return dtoList;


    }
}
