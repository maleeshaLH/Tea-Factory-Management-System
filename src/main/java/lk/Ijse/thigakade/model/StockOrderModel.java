package lk.Ijse.thigakade.model;

import lk.Ijse.thigakade.db.DbConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class StockOrderModel {

    public boolean saveStockOrder(String p_id, String rs_id, String description, int unitPrice, int weight, int qty) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();
        System.out.println("2");
        String sql = "INSERT INTO stock_details VALUES(?, ?, ? ,? ,? ,?)";
        PreparedStatement pstm = connection.prepareStatement(sql);
        pstm.setString(1, p_id);
        pstm.setString(2, rs_id);
        pstm.setString(3, description);
        pstm.setInt(4,unitPrice);
        pstm.setInt(5,weight);
        pstm.setInt(6,qty);

        return pstm.executeUpdate() > 0;
    }

}
