package lk.Ijse.thigakade.model;

import lk.Ijse.thigakade.db.DbConnection;
import lk.Ijse.thigakade.dto.UserDto;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserModel {

    public static boolean saveUser(UserDto userDto) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        PreparedStatement pstm = connection.prepareStatement("INSERT INTO user VALUES (?,?,?) ");

        pstm.setString(1,userDto.getUsername());
        pstm.setString(2, userDto.getEmail());
        pstm.setString(3, userDto.getPassword());

        return pstm.executeUpdate()>0;
    }

    public static boolean saveUser(String username, String password) {
        try {
            Connection connection = DbConnection.getInstance().getConnection();
            String sql ="SELECT password FROM user WHERE username = ?";

            PreparedStatement pstm = connection.prepareStatement(sql);
            pstm.setString(1,username);

            ResultSet resultSet = pstm.executeQuery();

            if (resultSet.next()){
                if (password.equals(resultSet.getString(1))){
                    return true;
                }
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return false;
    }



}
