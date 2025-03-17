package org.example.MessengerPackage.Servlets;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.MessengerPackage.Classes.User;
import org.example.MessengerPackage.Dao.UserDao;

import java.io.IOException;
import java.sql.SQLException;
import java.util.UUID;

public class UserServlet extends HttpServlet {
    private static final String FORM_HTML_PATH = "";
    private final UserDao dao = new UserDao();

    public UserServlet() throws SQLException {
    }


    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String username = req.getParameter("username");
        String password = req.getParameter("password");

        if (username.isBlank() | username.isEmpty() | password.isBlank() | password.isEmpty()){
            System.out.println("doPost:: All input must be filled");
            resp.sendError(HttpServletResponse.SC_UNPROCESSABLE_CONTENT, "All input must be filled");
            return;
        }

        try {
            if (dao.findByUsername(username)){
                System.out.println("doPost:: user with same username already exists");
                resp.sendError(HttpServletResponse.SC_FORBIDDEN, "user with same username already exists");
                return;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        User user = new User(username, password);

        try {
            dao.addUser(user);
            resp.getWriter().write(1);
        } catch (SQLException e) {
            System.out.println("doPost:: Something went wrong please retry");
            resp.sendError(HttpServletResponse.SC_FORBIDDEN, "Something went wrong please retry");
            return;
        }

    }
}
