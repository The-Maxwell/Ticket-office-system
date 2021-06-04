package servlets;

import entities.IEntity;
import entities.UserEntity;
import org.apache.commons.codec.digest.DigestUtils;
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
        String pwdDigest = DigestUtils.sha256Hex(password + IEntity.SAULT);
        HttpSession session = request.getSession();

        UserEntity userEntity = null;
        try {
            userEntity = (UserEntity) ticketOfficeDao.searchBySpecificParams("authorization", login, pwdDigest).get(0);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (userEntity != null && userEntity.getEmail().equals(login) && userEntity.getPassword().equals(pwdDigest)) {
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
