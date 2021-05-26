package servlets;

import report.ReportsCreator;
import services.EmailService;
import utils.TicketOfficeDao;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

@WebServlet(name = "PDFViewerServlet")
@MultipartConfig
public class PDFViewerServlet extends HttpServlet {

    private ReportsCreator reportsCreator;

    @Override
    public void init() throws ServletException {
        super.init();
        reportsCreator = new ReportsCreator();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        RequestDispatcher requestDispatcher = null;
        String action = request.getParameter("act");
        reportsCreator.setRequest(request);
        switch (action){
            case "Generate":
                String generateReport = request.getParameter("generateReport");
                generateReport(generateReport);
                break;
            case "Mail":
                String sendReport = request.getParameter("sendReport");
                String email = request.getParameter("email");
                String message = request.getParameter("message");
                String pathToReport = reportsCreator.getLastReportsPath(sendReport);
                int index = pathToReport.indexOf(sendReport);
                String filename = pathToReport.substring(index);
                boolean result = EmailService.sendReportToEmail(email, message, pathToReport, filename);
//                System.out.println("Result="+result);
                requestDispatcher = request.getRequestDispatcher("/work_with_db?act=Statistics");
                requestDispatcher.forward(request, response);
                break;
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String report = request.getParameter("report");
        reportsCreator.setRequest(request);
        //reportsCreator.createReports();
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
    }

    protected void generateReport(String generateReport){
        switch (generateReport){
            case "generateVehicleReport":
                reportsCreator.createVehicleReport();
                break;
            case "generateVehicheJournaryTicketReport":
                reportsCreator.createVehicheJournaryTicketReport();
                break;
            case "generateCategoryReport":
                reportsCreator.createCategoryReport();
                break;
        }
    }
}
