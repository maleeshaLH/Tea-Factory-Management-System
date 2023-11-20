package lk.Ijse.thigakade.model;

import lk.Ijse.thigakade.db.DbConnection;
import lk.Ijse.thigakade.dto.ProgressDto;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProgressModel {
    public boolean saveProgress(ProgressDto dto) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "INSERT INTO progress VALUES(?, ?, ?, ?)";
        PreparedStatement pstm = connection.prepareStatement(sql);

        pstm.setString(1, dto.getId());
        pstm.setString(2, dto.getDate());
        pstm.setString(3, dto.getTime());
        pstm.setString(4, dto.getAmount());

        boolean isSaved = pstm.executeUpdate() > 0;

        return isSaved;

    }

    public List<ProgressDto> getAllProgress() throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "SELECT * FROM progress";
        PreparedStatement pstm = connection.prepareStatement(sql);

        List<ProgressDto> dtoList = new ArrayList<>();

        ResultSet resultSet = pstm.executeQuery();

        while (resultSet.next()) {
            String p_id = resultSet.getString(1);
            String p_date = resultSet.getString(2);
            String p_time = resultSet.getString(3);
            String p_amount = resultSet.getString(4);

            var dto = new ProgressDto(p_id,p_date,p_time,p_amount);
            dtoList.add(dto);
        }
        return dtoList;
    }


}
