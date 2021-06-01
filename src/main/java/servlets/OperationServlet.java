package servlets;

import entities.*;
import report.ReportsCreator;
import utils.TicketOfficeDao;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.List;

@WebServlet(name = "MyServlet")
@MultipartConfig
public class OperationServlet extends HttpServlet {

    private TicketOfficeDao ticketOfficeDao;
    private ReportsCreator reportsCreator;
    @Override
    public void init() throws ServletException {
        super.init();
        ticketOfficeDao = new TicketOfficeDao();
        reportsCreator = new ReportsCreator();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        RequestDispatcher requestDispatcher = null;
        String path = null;
        String result = null;
        String action = request.getParameter("act");
        response.setContentType("text/html;charset=UTF-8");
        System.out.println("Action= " + action);
        switch (action){
            case "Home":
                request.setAttribute("home", true);
                HttpSession session = request.getSession();
                int userId = (int) session.getAttribute("userId");
                UserEntity userEntity = (UserEntity)ticketOfficeDao.searchEntity(String.valueOf(userId), "user");
//                this.lastName = lastName;
//                this.firstName = firstName;
//                this.surname = surname;
//                this.email = email;
//                this.age = Integer.parseInt(age);;
//                this.role = role;
//                this.phoneNumber = phoneNumber;
                request.setAttribute("userInfo", userEntity.recieveEntityInfo());
                System.out.println("userId=" + userId);
                path = "views/main.jsp";
                break;
            case "Show":
                List<IEntity> list = ticketOfficeDao.selectEntities(request.getParameter("table"));
                request.setAttribute("entities", list);
                request.setAttribute("table", request.getParameter("table"));
                IEntity entity = list.get(0);
                request.setAttribute(request.getParameter("table"), true);
                request.setAttribute("columnsName", entity.recieveColumnsName());
                path = "views/main.jsp";
                break;
            case "Add":
                String entityString = FormIEntityDataParser.getStringEntity(request);
                System.out.println(entityString);
                System.out.println("Table= " + request.getParameter("table"));
                result = ticketOfficeDao.insertEntity(entityString, request.getParameter("table"));
                if(result != null){
                    PrintWriter printWriter = response.getWriter();
                    printWriter.println("<h2>Insert Error</h2>");
                    return;
                }
                path = "/work_with_db?act=Show&table=" + request.getParameter("table");
                break;
            case "Delete":
                System.out.println(request.getParameter("entityString") + " " + request.getParameter("table"));
                result = ticketOfficeDao.deleteEntity(request.getParameter("entityString"), request.getParameter("table"));
                if(result != null){
                    PrintWriter printWriter = response.getWriter();
                    printWriter.println("<h2>Delete Error</h2>");
                    return;
                }
                path = "/work_with_db?act=Show&table=" + request.getParameter("table");
                break;
            case "Update":
                entityString = request.getParameter("entityString");
                System.out.println("Table= " + request.getParameter("table"));
                System.out.println("entityString="+entityString);
                result = ticketOfficeDao.updateEntity(entityString, request.getParameter("table"));
                if (result != null) {
                    PrintWriter printWriter = response.getWriter();
                    printWriter.println("<h2>Update Error</h2>");
                    return;
                }
                System.out.println(result);
                path = "/work_with_db?act=Show&table=" + request.getParameter("table");
                break;
            case "Statistics":
                reportsCreator.setRequest(request);
                request.setAttribute("statistics", true);
                path = "views/main.jsp";
                break;
            case "Search":
                String table = request.getParameter("table");
                list = null;
                switch (table){
                    case "vehicle":
                        list = ticketOfficeDao.searchBySpecificParams(table, request.getParameter("selType"));
                        break;
                    case "journary":
                        String dateTime = request.getParameter("dateAndTimeOfArrival1");
                        if (!dateTime.equals("")){
                            dateTime = request.getParameter("dateAndTimeOfArrival1").replace('T',' ')+":00.0";
                        }
                        System.out.println("dateTime=" + dateTime);
                        list = ticketOfficeDao.searchBySpecificParams(table, request.getParameter("departurePoint1"), dateTime);
                        break;
                    case "receipt":
                        System.out.println("request.getParameter(\"passenger\")"+request.getParameter("passenger"));
                        list = ticketOfficeDao.searchBySpecificParams(table, request.getParameter("passenger"));
                        break;
                    case "ticket":
                        list = ticketOfficeDao.searchBySpecificParams(table, request.getParameter("selCategory"));
                        break;
                    case "passenger":
                        list = ticketOfficeDao.searchBySpecificParams(table, request.getParameter("selCat"));
                        break;
                    case "user":
                        list = ticketOfficeDao.searchBySpecificParams(table, request.getParameter("lastName"), request.getParameter("firstName")
                                , request.getParameter("selRole"));
                        break;
                }
                request.setAttribute("entities", list);
                request.setAttribute("table", request.getParameter("table"));
                entity = list.get(0);
                request.setAttribute(table, true);
                request.setAttribute("columnsName", entity.recieveColumnsName());
                path = "views/main.jsp";
                break;
            case "SignOut":
                session = request.getSession();
                session.invalidate();
                response.sendRedirect("index.jsp");
                return;
        }
        requestDispatcher = request.getRequestDispatcher(path);
        requestDispatcher.forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//        RequestDispatcher requestDispatcher = null;
//        String path = null;
//        boolean result = false;
//        String action = req.getParameter("act");
//        resp.setContentType("text/html;charset=UTF-8");
//        System.out.println("Action= " +action);
        doGet(req, resp);
    }
}
