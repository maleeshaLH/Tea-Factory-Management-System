package lk.Ijse.thigakade.model;

import lk.Ijse.thigakade.db.DbConnection;
import lk.Ijse.thigakade.dto.SupplierOrderDto;

import java.sql.Connection;
import java.sql.SQLException;

public class SupplierPlaceOrderModel {
    private final SupplierOrderModel supplierOrderModel = new SupplierOrderModel();
    private final RawStockModel rawStockModel = new RawStockModel();
    private final SupplierOrderDetailModel supplierOrderDetailModel = new SupplierOrderDetailModel();

    public boolean supplierOrder(SupplierOrderDto supplierOrderDto) throws SQLException {
        boolean result = false;
        Connection connection = null;
        try {
            connection = DbConnection.getInstance().getConnection();
            connection.setAutoCommit(false);

            boolean isOrderSaved = supplierOrderModel.saveSupplierOrder(
                    supplierOrderDto.getS_id(),
                    supplierOrderDto.getSu_id(),
                    supplierOrderDto.getDescription(),
                    supplierOrderDto.getUnit_price(),
                    supplierOrderDto.getWeight(),
                    supplierOrderDto.getQty()
            );
            if (isOrderSaved) {
                boolean isUpdated = rawStockModel.updateRawStock(supplierOrderDto.getTmList());
                if(isUpdated) {
                    boolean isOrderDetailSaved = supplierOrderDetailModel.saveOrderDetail(supplierOrderDto.getS_id(),supplierOrderDto.getTmList());
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



