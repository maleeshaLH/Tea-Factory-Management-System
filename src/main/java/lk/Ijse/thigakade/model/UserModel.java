package lk.Ijse.thigakade.model;

import lk.Ijse.thigakade.db.DbConnection;
import lk.Ijse.thigakade.dto.UserDto;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class UserModel {

    public static boolean saveUser(UserDto userDto) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        PreparedStatement pstm = connection.prepareStatement("INSERT INTO user VALUES (?,?,?) ");

        pstm.setString(1,userDto.getUsername());
        pstm.setString(2, userDto.getPassword());
        pstm.setString(3, userDto.getEmail());

        return pstm.executeUpdate()>0;
    }


}
