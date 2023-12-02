package lk.Ijse.thigakade.model;

import lk.Ijse.thigakade.db.DbConnection;
import lk.Ijse.thigakade.dto.RawStockDto;
import lk.Ijse.thigakade.dto.tm.StockOrderTm;
import lk.Ijse.thigakade.dto.tm.SupplierOrderTm;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class RawStockModel {


    public static List<RawStockDto> loadAllRawStock() throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "SELECT * FROM  raw_stock";
        ResultSet resultSet = connection.prepareStatement(sql).executeQuery();

        List<RawStockDto> cusList = new ArrayList<>();

        while (resultSet.next()) {
            cusList.add(new RawStockDto(
                    resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getInt(3),
                    resultSet.getInt(4),
                    resultSet.getInt(5)
            ));
        }
        return cusList;
    }



    public boolean saveRawStock(RawStockDto dto) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "INSERT INTO raw_stock VALUES(?, ?, ?, ?,?)";
        PreparedStatement pstm = connection.prepareStatement(sql);

        pstm.setString(1, dto.getRs_id());
        pstm.setString(2, dto.getDescription());
        pstm.setInt(3,dto.getUnit_price());
        pstm.setInt(4, dto.getWeight());
        pstm.setInt(5, dto.getQuality());


        boolean isSaved = pstm.executeUpdate() > 0;

        return isSaved;

    }

    public List<RawStockDto> getAllRawStock() throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "SELECT * FROM raw_stock";
        PreparedStatement pstm = connection.prepareStatement(sql);

        List<RawStockDto> dtoList = new ArrayList<>();

        ResultSet resultSet = pstm.executeQuery();

        while (resultSet.next()) {
            String ra_id = resultSet.getString(1);
            String name = resultSet.getString(2);
            int unic_price =resultSet.getInt(3);
            int weight = resultSet.getInt(4);
            int quality = resultSet.getInt(5);


            var dto = new RawStockDto(ra_id,name,unic_price,weight,quality);
            dtoList.add(dto);
        }
        return dtoList;

    }

    public boolean deleteRawStock(String id) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "DELETE FROM raw_stock WHERE rs_id = ?";
        PreparedStatement pstm = connection.prepareStatement(sql);
        pstm.setString(1, id);

        return pstm.executeUpdate() > 0;

    }

    public boolean updateRawStock(RawStockDto dto) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "UPDATE raw_stock SET description = ?,unit_price = ? ,weight = ?, quality = ? WHERE rs_id = ?";
        PreparedStatement pstm = connection.prepareStatement(sql);

        pstm.setString(1, dto.getDescription());
        pstm.setInt(2,dto.getUnit_price());
        pstm.setInt(3,dto.getWeight());
        pstm.setInt(4, dto.getQuality());
        pstm.setInt(5, dto.getQuality());


        return pstm.executeUpdate() > 0;


    }


    public RawStockDto searchRawStock(String id) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "SELECT * FROM raw_stock WHERE rs_id = ?";
        PreparedStatement pstm = connection.prepareStatement(sql);
        pstm.setString(1, id);

        ResultSet resultSet = pstm.executeQuery();

        RawStockDto dto = null;

        if (resultSet.next()) {
            String rs_id = resultSet.getString(1);
            String rs_name = resultSet.getString(2);
            int unic_price = resultSet.getInt(3);
            int weight = resultSet.getInt(4);
            int quality = resultSet.getInt(5);


            dto = new RawStockDto(rs_id,rs_name,unic_price,weight,quality);
        }

        return dto;

    }
    public static boolean updateRawStock(List<SupplierOrderTm> tmList) throws SQLException {
        for (SupplierOrderTm supplierOrderTm : tmList) {
            if(!updateQty(supplierOrderTm)) {
                return false;
            }
        }
        return true;


    }

    private static boolean updateQty(SupplierOrderTm supplierOrderTm) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "UPDATE raw_stock SET qty = qty - ? WHERE rs_id = ?";
        PreparedStatement pstm = connection.prepareStatement(sql);
        pstm.setInt(1, supplierOrderTm.getQty());
        pstm.setString(2, supplierOrderTm.getS_id());

        return pstm.executeUpdate() > 0; //true
    }

    public List<RawStockDto> loadAllStockNames() throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "SELECT * FROM raw_stock";
        PreparedStatement pstm = connection.prepareStatement(sql);

        ResultSet resultSet = pstm.executeQuery();

        List<RawStockDto> dtoList = new ArrayList<>();

        while (resultSet.next()) {
            var dto = new RawStockDto(
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

    public boolean updateRawStockDetails(List<StockOrderTm> tmList) throws SQLException {
        for (StockOrderTm stockOrderTm : tmList) {
            if(!updateStockOrder(stockOrderTm)) {
                return false;
            }
        }
        return true;

    }

    private boolean updateStockOrder(StockOrderTm stockOrderTm) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "UPDATE raw_stock SET qty = qty - ? WHERE rs_id = ?";
        PreparedStatement pstm = connection.prepareStatement(sql);
        pstm.setInt(1, stockOrderTm.getQty());
        pstm.setString(2, stockOrderTm.getRawStockId());

        return pstm.executeUpdate() > 0; //true

    }

    public RawStockDto searchRawStocks(String rs_id) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();
        String sql = "SELECT * FROM raw_stock WHERE description = ?";

        PreparedStatement pstm = connection.prepareStatement(sql);
        pstm.setString(1, rs_id);

        ResultSet resultSet = pstm.executeQuery();

        RawStockDto dto = null;

        if (resultSet.next()) {
            String r_id = resultSet.getString(1);
            String rs_name = resultSet.getString(2);
            int unic_price = resultSet.getInt(3);
            int weight = resultSet.getInt(4);
            int quality = resultSet.getInt(5);


            dto = new RawStockDto(r_id,rs_name,unic_price,weight,quality);
        }

        return dto;

    }
}
