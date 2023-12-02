package lk.Ijse.thigakade.model;



import lk.Ijse.thigakade.db.DbConnection;
import lk.Ijse.thigakade.dto.tm.SupplierOrderTm;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public class SupplierOrderDetailModel {

    public static boolean saveOrderDetail(String s_id, List<SupplierOrderTm> tmList) throws SQLException {
        for (SupplierOrderTm supplierOrderTm : tmList) {
            if(!saveOrderDetail(s_id, supplierOrderTm)) {
                return false;
            }
        }
        return true;

    }

    private static boolean saveOrderDetail(String s_id, SupplierOrderTm supplierOrderTm) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "INSERT INTO supplier_order_details VALUES(?, ?, ?, ? )";
        PreparedStatement pstm = connection.prepareStatement(sql);
        pstm.setString(1, s_id);
        pstm.setString(2, supplierOrderTm.getRs_id());
        pstm.setInt(3, supplierOrderTm.getUnit_price());
       // pstm.setDouble(4, supplierOrderTm.getWeight());
        pstm.setInt(4, supplierOrderTm.getQty());


        return pstm.executeUpdate() > 0;
    }



}
