package servlets;

import entities.FormDataParser;
import entities.IEntity;
import report.ReportsCreator;
import utils.TicketOfficeDao;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
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
        boolean result = false;
        String action = request.getParameter("act");
        response.setContentType("text/html;charset=UTF-8");
        System.out.println("Action= " + action);
        switch (action){
            case "Show":
                List<IEntity> list = ticketOfficeDao.selectEntities(request.getParameter("table"));
                request.setAttribute("entities", list);
                request.setAttribute("table", request.getParameter("table"));
                IEntity entity = list.get(0);
                request.setAttribute(request.getParameter("table"), true);
                request.setAttribute("columnsName", entity.recieveColumnsName());
                path = "index.jsp";
                break;
            case "Add":
                FormDataParser.getStringEntity(request.getParameter("vehicleCode"),request.getParameter("vehicleType"),request.getParameter("numberOfSeats"),
                        request.getParameter("numberOfEconomyClassSeats"),request.getParameter("numberOfMediumClassSeats"),request.getParameter("numberOfLuxuryClassSeats"),
                        request.getParameter("vechileCompany"));
                System.out.println("Table= " + request.getParameter("table"));
                //result = ticketOfficeDao.insertEntity(request.getParameter("entityString"), request.getParameter("table"));
                if(!result){
                    PrintWriter printWriter = response.getWriter();
                    printWriter.println("<h2>Insert Error</h2>");
                    return;
                }
                path = "/work_with_db?act=Show&table=" + request.getParameter("table");
                System.out.println(path);
                break;
            case "Delete":
                System.out.println(request.getParameter("entityString") + " " + request.getParameter("table"));
//                result = ticketOfficeDao.deleteEntity(request.getParameter("entityString"), request.getParameter("table"));
                if(!result){
                    PrintWriter printWriter = response.getWriter();
                    printWriter.println("<h2>Delete Error</h2>");
                    return;
                }
                path = "/work_with_db?act=Show&table=" + request.getParameter("table");
                break;
            case "Update":
                //result = ticketOfficeDao.updateEntity(request.getParameter("entityString"), request.getParameter("table"));
                if(!result){
                    PrintWriter printWriter = response.getWriter();
                    printWriter.println("<h2>Update Error</h2>");
                    return;
                }
                path = "/work_with_db?act=Show&table=" + request.getParameter("table");
                break;
            case "Statistics":
                reportsCreator.setRequest(request);
                System.out.println("Statistics");
                request.setAttribute("statistics", true);
                path = "index.jsp";
                break;
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
