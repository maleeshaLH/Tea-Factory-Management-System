package lk.Ijse.thigakade.model;

import lk.Ijse.thigakade.db.DbConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class SupplierOrderModel {


    public static boolean saveSupplierOrder(String s_id, String su_id, String description, Integer unit_price, Integer weight, Integer qty) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "INSERT INTO supplier_order VALUES(?, ?, ?,?,?,?)";
        PreparedStatement pstm = connection.prepareStatement(sql);
        pstm.setString(1, s_id);
        pstm.setString(2, su_id);
        pstm.setString(3,description);
        pstm.setInt(4,unit_price);
        pstm.setInt(5,weight);
        pstm.setInt(6,qty);

        return pstm.executeUpdate()>0;
    }

    public String generateNextOrderId() throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "SELECT s_id FROM supplier_order ORDER BY s_id DESC LIMIT 1";
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
            String[] split = currentOrderId.split("Su");
            int id = Integer.parseInt(split[1]);    //008
            id++;  //9
            return "O00" + id;
        }
        return "Su001";

    }
}
