package org.example.MessengerPackage.Servlets;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.MessengerPackage.Classes.Message;
import org.example.MessengerPackage.Classes.User;
import org.example.MessengerPackage.Dao.MessageDao;
import org.example.MessengerPackage.Dao.UserDao;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

public class MessageServlet extends HttpServlet {
    private static final String MESSENGER_HTML_PATH = "";

    private ObjectMapper mapper = new ObjectMapper();

    private final MessageDao messageDao = new MessageDao();
    private final UserDao userDao = new UserDao();

    public MessageServlet() throws SQLException {
    }


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String username = req.getParameter("username");
        String password = req.getParameter("password");

        System.out.println(username);
        System.out.println(password);

        ArrayList<Message> messages;

        resp.setContentType("application/json");

        if (username.isBlank() | username.isEmpty() | password.isBlank() | password.isEmpty()){
            System.out.println("doPost:: All input must be filled");
            resp.sendError(HttpServletResponse.SC_UNPROCESSABLE_CONTENT, "All input must be filled");
            return;
        }

        User currUser;

        try {
            currUser = userDao.getUser(username);
        } catch (SQLException e) {
            System.out.println("doPost:: Something went wrong please retry");
            resp.sendError(HttpServletResponse.SC_FORBIDDEN, "Something went wrong please retry");
            return;
        }

        if (currUser.getPassword() == null){
            System.out.println("doPost:: this user doesn't exist");
            resp.sendError(HttpServletResponse.SC_FORBIDDEN, "this user doesn't exist");
            return;
        }

        if (!currUser.getPassword().equals(password)){
            System.out.println("doPost:: wrong password, try again");
            resp.sendError(HttpServletResponse.SC_FORBIDDEN, "wrong password, try again");
            return;
        }
        try {
            messages = messageDao.getMessages(username);

        } catch (SQLException e) {
            System.out.println("doGet:: Something went wrong please retry1");
            resp.sendError(HttpServletResponse.SC_FORBIDDEN, "Something went wrong please retry");
            return;
        }

        mapper.writeValue(resp.getWriter(), messages);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String user = req.getParameter("user");
        String text = req.getParameter("text");

        if (user.isBlank() | user.isEmpty() | text.isBlank() | text.isEmpty()){
            System.out.println("doPost:: All input must be filled");
            resp.sendError(HttpServletResponse.SC_UNPROCESSABLE_CONTENT, "All input must be filled");
            return;
        }

        User currUser;

        try {
            currUser = userDao.getUser(user);
        } catch (SQLException e) {
            System.out.println("doPost:: Something went wrong please retry");
            resp.sendError(HttpServletResponse.SC_FORBIDDEN, "Something went wrong please retry");
            return;
        }

        if (currUser.getPassword() == null){
            System.out.println("doPost:: this user doesn't exist");
            resp.sendError(HttpServletResponse.SC_FORBIDDEN, "this user doesn't exist");
            return;
        }

        if (text.contains("\n")){
            System.out.println("doPost:: message you are sending shouldn't contain any new line!");
            resp.sendError(HttpServletResponse.SC_FORBIDDEN, "message you are sending shouldn't contain any new line!");
            return;
        }

        try {
            messageDao.addMessage(new Message(user, text));
        } catch (SQLException e) {
            System.out.println("doPost:: Something went wrong please retry");
            resp.sendError(HttpServletResponse.SC_FORBIDDEN, "Something went wrong please retry");
            return;
        }
    }

}

