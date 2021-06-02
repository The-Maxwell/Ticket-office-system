package servlets;

import entities.FormIEntityDataParser;
import entities.IEntity;
import entities.UserEntity;
import services.ticketoffice.ITicketOfficeService;
import services.ticketoffice.TicketOfficeServiceImpl;

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
import java.util.List;

@WebServlet(name = "MyServlet")
@MultipartConfig
public class OperationServlet extends HttpServlet {

    private ITicketOfficeService ticketOfficeService;

    //private ReportsCreator reportsCreator;
    @Override
    public void init() throws ServletException {
        super.init();
        ticketOfficeService = new TicketOfficeServiceImpl();
        //reportsCreator = new ReportsCreator();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        RequestDispatcher requestDispatcher = null;
        String path = null;
        String result = null;
        String action = request.getParameter("act");
        response.setContentType("text/html;charset=UTF-8");
        switch (action) {
            case "Home":
                request.setAttribute("home", true);
                HttpSession session = request.getSession();
                int userId = (int) session.getAttribute("userId");
                UserEntity userEntity = (UserEntity) ticketOfficeService.searchEntity(String.valueOf(userId), "user");
                request.setAttribute("userInfo", userEntity.recieveEntityInfo());
                path = "views/main.jsp";
                break;
            case "Show":
                List<IEntity> list = ticketOfficeService.selectEntities(request.getParameter("table"));
                request.setAttribute("entities", list);
                request.setAttribute("table", request.getParameter("table"));
                IEntity entity = list.get(0);
                request.setAttribute(request.getParameter("table"), true);
                request.setAttribute("columnsName", entity.recieveColumnsName());
                path = "views/main.jsp";
                break;
            case "Add":
                String entityString = FormIEntityDataParser.getStringEntity(request);
                result = ticketOfficeService.insertEntity(entityString, request.getParameter("table"));
                System.out.println("result=" + result);
                System.out.println("table=" + request.getParameter("table"));
                if (result != null) {
                    response.setStatus(500);
                }
                path = "/work_with_db?act=Show&table=" + request.getParameter("table");
                break;
            case "Delete":
                result = ticketOfficeService.deleteEntity(request.getParameter("entityString"), request.getParameter("table"));
                if (result != null) {
                    PrintWriter printWriter = response.getWriter();
                    printWriter.println("<h2>Delete Error</h2>");
                    return;
                }
                path = "/work_with_db?act=Show&table=" + request.getParameter("table");
                break;
            case "Update":
                entityString = request.getParameter("entityString");
                result = ticketOfficeService.updateEntity(entityString, request.getParameter("table"));
                if (result != null) {
                    PrintWriter printWriter = response.getWriter();
                    printWriter.println("<h2>Update Error</h2>");
                    return;
                }
                path = "/work_with_db?act=Show&table=" + request.getParameter("table");
                break;
            case "Statistics":
                //reportsCreator.setRequest(request);
                request.setAttribute("statistics", true);
                path = "views/main.jsp";
                break;
            case "Search":
                String table = request.getParameter("table");
                list = null;
                switch (table) {
                    case "vehicle":
                        list = ticketOfficeService.searchBySpecificParams(table, request.getParameter("selType"));
                        break;
                    case "journary":
                        String dateTime = request.getParameter("dateAndTimeOfArrival1");
                        if (!dateTime.equals("")) {
                            dateTime = request.getParameter("dateAndTimeOfArrival1").replace('T', ' ') + ":00.0";
                        }
                        list = ticketOfficeService.searchBySpecificParams(table, request.getParameter("departurePoint1"), dateTime);
                        break;
                    case "receipt":
                        list = ticketOfficeService.searchBySpecificParams(table, request.getParameter("passenger"));
                        break;
                    case "ticket":
                        list = ticketOfficeService.searchBySpecificParams(table, request.getParameter("selCategory"));
                        break;
                    case "passenger":
                        list = ticketOfficeService.searchBySpecificParams(table, request.getParameter("selCat"));
                        break;
                    case "user":
                        list = ticketOfficeService.searchBySpecificParams(table, request.getParameter("lastName"), request.getParameter("firstName"),
                                request.getParameter("selRole"));
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
        doGet(req, resp);
    }
}
