package servlets;

import services.reports.IReportsService;
import services.reports.ReportsServiceImpl;
import services.email.EmailServiceImpl;
import services.email.IEmailService;

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

    private IReportsService reportsService;
    private IEmailService emailService;

    @Override
    public void init() throws ServletException {
        super.init();
        reportsService = new ReportsServiceImpl();
        emailService = new EmailServiceImpl();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        RequestDispatcher requestDispatcher = null;
        String action = request.getParameter("act");
        reportsService.setRequest(request);
        switch (action){
            case "Generate":
                String generateReport = request.getParameter("generateReport");
                generateReport(generateReport);
                break;
            case "Mail":
                String sendReport = request.getParameter("sendReport");
                String email = request.getParameter("email");
                String header = request.getParameter("header");
                String message = request.getParameter("message");
                String pathToReport = reportsService.getLastReportsPath(sendReport);
                //Додати перевірки + дефолтні значення
                if(email.trim().equals("")) System.out.println("email=null");
                if(header.trim().equals("")) System.out.println("header=null");
                if(message.trim().equals("")) System.out.println("message=null");
                int index = pathToReport.indexOf(sendReport);
                String filename = pathToReport.substring(index);
                boolean result = emailService.sendReportToEmail(email, header, message, pathToReport, filename);
//                System.out.println("Result="+result);
                requestDispatcher = request.getRequestDispatcher("/work_with_db?act=Statistics");
                requestDispatcher.forward(request, response);
                break;
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String report = request.getParameter("report");
        reportsService.setRequest(request);
        //reportsCreator.createReports();
        String path = null;
        switch (report){
            case "VehicheReport":
                path = ReportsServiceImpl.lastVehicheReportPath;
                break;
            case "VehicheJournaryTicketReport":
                path = ReportsServiceImpl.lastVehicheJournaryTicketReportPath;
                break;
            case "CategoryReport":
                path = ReportsServiceImpl.lastCategoryReportPath;
                break;
        }
        if(path == null)
            path = reportsService.getLastReportsPath(report);

        FileInputStream fis = new FileInputStream(new File(path));
        org.apache.commons.io.IOUtils.copy(fis, response.getOutputStream());
        response.setContentType("application/pdf");
        response.setHeader("Content-Disposition", "inline; filename=" + report);
        response.flushBuffer();
    }

    protected void generateReport(String generateReport){
        switch (generateReport){
            case "generateVehicleReport":
                reportsService.createVehicleReport();
                break;
            case "generateVehicheJournaryTicketReport":
                reportsService.createVehicheJournaryTicketReport();
                break;
            case "generateCategoryReport":
                reportsService.createCategoryReport();
                break;
        }
    }
}
