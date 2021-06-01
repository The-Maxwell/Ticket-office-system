package servlets;

import entities.UserEntity;
import utils.TicketOfficeDao;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet(name = "AuthorizationServlet")
@MultipartConfig
public class AuthorizationServlet extends HttpServlet {

    private TicketOfficeDao ticketOfficeDao;

    @Override
    public void init() throws ServletException {
        super.init();
        ticketOfficeDao = new TicketOfficeDao();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String login = request.getParameter("login");
        String password = request.getParameter("password");
        System.out.println(login + " " + password);
        //TODO Объект генерирующий хеш пароля
        //Encryption encryption = new Encryption();
        HttpSession session = request.getSession();

        //UsersService usersService = new UsersService();
        UserEntity userEntity = null;
        try {
            userEntity = (UserEntity) ticketOfficeDao.searchBySpecificParams("authorization", login, password).get(0);
            System.out.println(userEntity.recieveStringInfo());
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (userEntity != null && userEntity.getEmail().equals(login) && userEntity.getPassword().equals(password)) {
            session.setAttribute("userId", userEntity.getId());
            session.setAttribute("userEmail", userEntity.getEmail());
            session.setAttribute("userRole", userEntity.getRole());
            response.sendRedirect("/work_with_db?act=Home");
        } else {
            request.setAttribute("errorMessage", "Помилка авторизації!");
            request.getRequestDispatcher("index.jsp").forward(request, response);
        }
    }
}
