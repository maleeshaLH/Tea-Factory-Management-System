package lk.Ijse.thigakade.model;

import lk.Ijse.thigakade.db.DbConnection;
import lk.Ijse.thigakade.dto.StockDto;
import lk.Ijse.thigakade.dto.tm.CartTm;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class StockModel {

    public boolean saveStock(StockDto dto) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "INSERT INTO prepared_stock VALUES(?,?,?,?)";
        PreparedStatement pstm = connection.prepareStatement(sql);

        pstm.setString(1,dto.getP_id());
        pstm.setString(2,dto.getP_name());
        pstm.setString(3,dto.getP_weight());
        pstm.setString(4,dto.getP_qty());

        boolean isSaved = pstm.executeUpdate() >0 ;

        return isSaved;


    }

    public boolean updateStock(StockDto dto) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "UPDATE prepared_stock SET name = ?,weight = ?,qty = ? WHERE p_id =?";
        PreparedStatement pstm = connection.prepareStatement(sql);

        pstm.setString(1,dto.getP_name());
        pstm.setString(2,dto.getP_weight());
        pstm.setString(3,dto.getP_qty());
        pstm.setString(4,dto.getP_id());


        boolean isUpdate = pstm.executeUpdate() >0 ;

        return isUpdate;



    }

    public StockDto searchStock(String id) throws SQLException {
        Connection connection=DbConnection.getInstance().getConnection();

        String sql = "SELECT * FROM prepared_stock WHERE p_id = ?";
        PreparedStatement pstm = connection.prepareStatement(sql);
        pstm.setString(1,id);

        ResultSet resultSet = pstm.executeQuery();

        StockDto dto = null;

        if (resultSet.next()){
            String p_id = resultSet.getString(1);
            String p_name = resultSet.getString(2);
            String p_weight = resultSet.getString(3);
            String p_qty = resultSet.getString(4);

            dto = new StockDto(p_id,p_name,p_weight,p_qty);
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

    public static List<StockDto> loadAllStock() throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "SELECT * FROM prepared_stock";
        PreparedStatement pstm = connection.prepareStatement(sql);

        ResultSet resultSet = pstm.executeQuery();

        List<StockDto> dtoList = new ArrayList<>();

        while (resultSet.next()){
            var dto = new StockDto(
              resultSet.getString(1),
              resultSet.getString(2),
              resultSet.getString(3),
              resultSet.getString(4)
            );
            dtoList.add(dto);
        }
        return dtoList;


    }

    public List<StockDto> getAllStock() throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "SELECT * FROM prepared_stock";
        PreparedStatement pstm = connection.prepareStatement(sql);

        List<StockDto> dtoList = new ArrayList<>();

        ResultSet resultSet = pstm.executeQuery();

        while (resultSet.next()) {
            String pr_id = resultSet.getString(1);
            String pr_name = resultSet.getString(2);
            String pr_weight = resultSet.getString(3);
            String pr_qty = resultSet.getString(4);


            var dto = new StockDto(pr_id,pr_name,pr_weight,pr_qty);
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

        String sql = "UPDATE qty SET p_id = qty - ? WHERE p_id = ?";
        PreparedStatement pstm = connection.prepareStatement(sql);
        pstm.setInt(1, cartTm.getQty());
        pstm.setString(2, cartTm.getP_id());

        return pstm.executeUpdate() > 0; //true
    }


}
