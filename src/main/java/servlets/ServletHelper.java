package servlets;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class ServletHelper {
    public void setResponseText(HttpServletResponse response, int status, String result) throws IOException {
        response.setContentType("text/plain");
        response.setStatus(status);
        response.getWriter().write(result);
    }

    public boolean checkOnAccess(HttpServletRequest request, HttpServletResponse response) throws IOException {
        HttpSession session = request.getSession();
        if (session.getAttribute("userRole").toString().equals("Seller")) {
            response.setContentType("text/html;charset=UTF-8");
            response.getWriter().write("Access denied!");
            return false;
        }
        return true;
    }
}
