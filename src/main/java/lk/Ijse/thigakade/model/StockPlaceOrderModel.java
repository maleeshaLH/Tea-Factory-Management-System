package lk.Ijse.thigakade.model;

import lk.Ijse.thigakade.db.DbConnection;
import lk.Ijse.thigakade.dto.StockOrderDto;

import java.sql.Connection;
import java.sql.SQLException;

public class StockPlaceOrderModel {
    private  final StockOrderModel stockOrderModel = new StockOrderModel();
    private final StockModel stockModel =new StockModel();
    private final RawStockModel rawStockModel = new RawStockModel();
    public boolean stockOrder(StockOrderDto stockOrderDto) throws SQLException {
        boolean result = false;
        Connection connection = null;
        try {
            connection = DbConnection.getInstance().getConnection();
            connection.setAutoCommit(false);

            boolean isOrderSaved = stockOrderModel.saveStockOrder(
                    stockOrderDto.getP_id(),
                    stockOrderDto.getRs_id(),
                    stockOrderDto.getDescription(),
                    stockOrderDto.getUnitPrice(),
                    stockOrderDto.getWeight(),
                    stockOrderDto.getQty()
            );
            if (isOrderSaved) {
                boolean isUpdated = stockModel.updatePreparedStock(stockOrderDto.getTmList());
                if(isUpdated) {
                    boolean isOrderDetailSaved = rawStockModel.updateRawStockDetails(stockOrderDto.getTmList());
                    if(isOrderDetailSaved) {
                        connection.commit();
                        result = true;
                    }
                }
            }
        } catch (SQLException e) {
            connection.rollback();
        } finally {
            connection.setAutoCommit(true);
        }
        return result;

    }
}
