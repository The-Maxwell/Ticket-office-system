package servlets;

import report.ReportsCreator;
import utils.TicketOfficeDao;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

@WebServlet(name = "PDFViewerServlet")
public class PDFViewerServlet extends HttpServlet {

    private ReportsCreator reportsCreator;

    @Override
    public void init() throws ServletException {
        super.init();
        reportsCreator = new ReportsCreator();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String report = request.getParameter("report");
        response.setContentType("text/html;charset=UTF-8");
        reportsCreator.setRequest(request);
        reportsCreator.createReports();
        String path = null;
        switch (report){
            case "VehicheReport":
                path = ReportsCreator.lastVehicheReportPath;
                break;
            case "VehicheJournaryTicketReport":
                path = ReportsCreator.lastVehicheJournaryTicketReportPath;
                break;
            case "CategoryReport":
                path = ReportsCreator.lastCategoryReportPath;
                break;
        }
        if(path == null)
            path = reportsCreator.getLastReportsPath(report);

        FileInputStream fis = new FileInputStream(new File(path));
        org.apache.commons.io.IOUtils.copy(fis, response.getOutputStream());
        response.setContentType("application/pdf");
        response.setHeader("Content-Disposition", "inline; filename=" + report);
        response.flushBuffer();
//        System.out.println("REQ PATH ="+path);
//        int index = path.indexOf("\\WEB-INF");
//        String iPath = path.substring(index);
//        RequestDispatcher rd = request.getRequestDispatcher(iPath);
//        rd.include(request, response);
    }
}
