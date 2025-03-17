package org.example.MessengerPackage.Dao;

import org.example.MessengerPackage.Classes.User;

import java.sql.*;
import java.util.ArrayList;

public class UserDao {
    private final Connector connector = Connector.getInstance();
    private final Connection con = connector.getCon();
    public UserDao() throws SQLException {
    }
    private final String GET_BY_USERNAME = "SELECT * FROM public.\"user\" WHERE \"username\" = ?";
    private final String GET_ALL = "SELECT * FROM public.\"user\"";
    private final String ADD = "INSERT INTO public.\"user\" (\"username\", \"password\") VALUES (?, ?)";
    private final String UPDATE = "UPDATE public.\"user\" SET \"password\" = ? WHERE \"username\" = ?";
    private final String DELETE = "DELETE FROM public.\"user\" WHERE \"id\" = ?";

    public User getUser(String username) throws SQLException {
        PreparedStatement stmt = con.prepareStatement(GET_BY_USERNAME);
        stmt.setString(1, username);
        ResultSet rs = stmt.executeQuery();

        User user = new User();

        if (rs.next()){
            user = new User(rs.getString(1), rs.getString(2));
        }

        return user;
    }

    public boolean findByUsername(String username) throws SQLException {
        PreparedStatement stmt = con.prepareStatement(GET_BY_USERNAME);
        stmt.setString(1, username);
        ResultSet rs = stmt.executeQuery();

        return rs.next();
    }



    public ArrayList<User> getUsers() throws SQLException {
        Statement stmt = con.createStatement();
        ResultSet rs = stmt.executeQuery(GET_ALL);

        ArrayList<User> users = new ArrayList<>();

        while (rs.next()){
            User user = new User(rs.getString(1), rs.getString(2));
            users.add(user);
        }

        return users;
    }

    public void addUser(User user) throws SQLException {
        PreparedStatement stmt = con.prepareStatement(ADD);

        stmt.setString(1, user.getUserName());
        stmt.setString(2, user.getPassword());

        stmt.execute();

    }

    public void updateUser(User user) throws SQLException {
        PreparedStatement stmt = con.prepareStatement(UPDATE);

        stmt.setString(1, user.getPassword());
        stmt.setString(2, user.getUserName());

        stmt.execute();

    }

    public void deleteUser(String username) throws SQLException {

        PreparedStatement stmt = con.prepareStatement(DELETE);
        stmt.setString(1, username);
        stmt.execute();

    }
}
