package org.example.MessengerPackage.Dao;


import org.example.MessengerPackage.Classes.Message;
import org.example.MessengerPackage.Classes.User;

import java.sql.*;
import java.util.ArrayList;

public class MessageDao {
    private final Connector connector = Connector.getInstance();
    private final Connection con = connector.getCon();

    public MessageDao() throws SQLException {
    }

    private final String GET_MESSAGES = "SELECT * FROM public.\"message\" WHERE \"user\" = ?";
    private final String ADD = "INSERT INTO public.\"message\" (\"user\", \"text\") VALUES (?, ?)";
    private final String DELETE = "DELETE FROM  public.\"message\" WHERE \"user\" = ?";

    public ArrayList<Message> getMessages(String toUser) throws SQLException {
        PreparedStatement stmt = con.prepareStatement(GET_MESSAGES);

        stmt.setString(1, toUser);

        ResultSet rs = stmt.executeQuery();

        ArrayList<Message> messages = new ArrayList<>();

        while (rs.next()){
            Message message = new Message(rs.getString(1), rs.getString(2));
            messages.add(message);
        }

        return messages;
    }

    public void addMessage(Message message) throws SQLException {
        PreparedStatement stmt = con.prepareStatement(ADD);

        stmt.setString(1, message.getUser());
        stmt.setString(2, message.getText());

        stmt.execute();

    }


    public void deleteMessage(String user) throws SQLException {

        PreparedStatement stmt = con.prepareStatement(DELETE);
        stmt.setString(1, user);
        stmt.execute();

    }

}
