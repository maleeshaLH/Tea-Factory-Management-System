package lk.Ijse.thigakade.model;

import lk.Ijse.thigakade.db.DbConnection;
import lk.Ijse.thigakade.dto.PreparedstockDto;
import lk.Ijse.thigakade.dto.tm.CartTm;
import lk.Ijse.thigakade.dto.tm.StockOrderTm;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class StockModel {

    public static boolean updatePreparedStock(List<StockOrderTm> tmList) throws SQLException {
        for (StockOrderTm stockOrderTm : tmList) {
            if(!updatePreparedQty(stockOrderTm)) {
                return false;
            }
        }
        return true;

    }

    private static boolean updatePreparedQty(StockOrderTm stockOrderTm) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();
        System.out.println("3");
        String sql = "UPDATE prepared_stock SET qty = qty + ? WHERE p_id = ?";
        PreparedStatement pstm = connection.prepareStatement(sql);
        pstm.setInt(1, stockOrderTm.getQty());
        pstm.setString(2, stockOrderTm.getPreparedStockId());

        return pstm.executeUpdate() > 0; //true
    }



    public boolean saveStock(PreparedstockDto dto) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "INSERT INTO prepared_stock VALUES(?,?,?,?,?)";
        PreparedStatement pstm = connection.prepareStatement(sql);

        pstm.setString(1,dto.getP_id());
        pstm.setString(2,dto.getDescription());
        pstm.setInt(3,dto.getUnit_price());
        pstm.setInt(4,dto.getWeight());
        pstm.setInt(5,dto.getQty());

        boolean isSaved = pstm.executeUpdate() >0 ;

        return isSaved;


    }

    public boolean updateStock(PreparedstockDto dto) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "UPDATE prepared_stock SET description = ?,unit_price =? ,weight = ?,qty = ? WHERE p_id =?";
        PreparedStatement pstm = connection.prepareStatement(sql);

        pstm.setString(1,dto.getP_id());
        pstm.setString(2,dto.getDescription());
        pstm.setInt(3,dto.getUnit_price());
        pstm.setInt(4,dto.getWeight());
        pstm.setInt(5,dto.getQty());


        boolean isUpdate = pstm.executeUpdate() >0 ;

        return isUpdate;



    }

    public PreparedstockDto searchStock(String id) throws SQLException {
        Connection connection=DbConnection.getInstance().getConnection();

        String sql = "SELECT * FROM prepared_stock WHERE p_id = ?";
        PreparedStatement pstm = connection.prepareStatement(sql);
        pstm.setString(1,id);

        ResultSet resultSet = pstm.executeQuery();

        PreparedstockDto dto = null;

        if (resultSet.next()){
            String p_id = resultSet.getString(1);
            String p_name = resultSet.getString(2);
            Integer p_unitPrice =resultSet.getInt(3);
            Integer p_weight = resultSet.getInt(4);
            Integer p_qty = resultSet.getInt(5);

            dto = new PreparedstockDto(p_id,p_name,p_unitPrice,p_weight,p_qty);
        }
        return dto;

    }

    public boolean deleteStock(String id) throws SQLException {
        Connection connection =  DbConnection.getInstance().getConnection();

        String sql="DELETE FROM prepared_stock WHERE p_id =?";
        PreparedStatement pstm = connection.prepareStatement(sql);
        pstm.setString(1,id);

        return pstm.executeUpdate() >0;


    }

    public static List<PreparedstockDto> loadAllStock() throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "SELECT * FROM prepared_stock";
        PreparedStatement pstm = connection.prepareStatement(sql);

        ResultSet resultSet = pstm.executeQuery();

        List<PreparedstockDto> dtoList = new ArrayList<>();

        while (resultSet.next()){
            var dto = new PreparedstockDto(
              resultSet.getString(1),
              resultSet.getString(2),
              resultSet.getInt(3),
              resultSet.getInt(4),
              resultSet.getInt(5)
            );
            dtoList.add(dto);
        }
        return dtoList;


    }

    public List<PreparedstockDto> getAllStock() throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "SELECT * FROM prepared_stock";
        PreparedStatement pstm = connection.prepareStatement(sql);

        List<PreparedstockDto> dtoList = new ArrayList<>();

        ResultSet resultSet = pstm.executeQuery();

        while (resultSet.next()) {
            String pr_id = resultSet.getString(1);
            String pr_name = resultSet.getString(2);
            Integer pr_unitPrice = resultSet.getInt(3);
            Integer pr_weight = resultSet.getInt(4);
            Integer pr_qty = resultSet.getInt(5);


            var dto = new PreparedstockDto(pr_id,pr_name,pr_unitPrice,pr_weight,pr_qty);
            dtoList.add(dto);
        }
        return dtoList;

    }

    public boolean updateStock(List<CartTm> tmList) throws SQLException {
        for (CartTm cartTm : tmList) {
            if(!updateQty(cartTm)) {
                return false;
            }
        }
        return true;

    }

    private boolean updateQty(CartTm cartTm) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "UPDATE prepared_stock SET qty = qty - ? WHERE p_id = ?";
        PreparedStatement pstm = connection.prepareStatement(sql);
        pstm.setInt(1, cartTm.getQty());
        pstm.setString(2, cartTm.getP_id());

        return pstm.executeUpdate() > 0; //true
    }


    public List<PreparedstockDto> loadAllStockIds() throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "SELECT * FROM prepared_stock";
        PreparedStatement pstm = connection.prepareStatement(sql);

        ResultSet resultSet = pstm.executeQuery();

        List<PreparedstockDto> dtoList = new ArrayList<>();

        while (resultSet.next()) {
            var dto = new PreparedstockDto(
                    resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getInt(3),
                    resultSet.getInt(4),
                    resultSet.getInt(5)
            );

            dtoList.add(dto);
        }

        return dtoList;

    }


}
