package servlets;

import services.email.EmailServiceImpl;
import services.email.IEmailService;
import services.reports.IReportsService;
import services.reports.ReportsServiceImpl;

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
    private ServletHelper servletHelper;

    @Override
    public void init() throws ServletException {
        super.init();
        reportsService = new ReportsServiceImpl();
        emailService = new EmailServiceImpl();
        servletHelper = new ServletHelper();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (!servletHelper.checkOnAccess(request, response)) return;
        String action = request.getParameter("act");
        reportsService.setRequest(request);
        switch (action) {
            case "Generate":
                String generateReport = request.getParameter("generateReport");
                String result = generateReport(generateReport);
                if (result != null) {
                    servletHelper.setResponseText(response, 500, result);
                    return;
                } else {
                    servletHelper.setResponseText(response, 200, generateReport + " successfully generated!");
                    return;
                }
            case "Mail":
                String sendReport = request.getParameter("sendReport");
                String email = request.getParameter("email");
                String header = request.getParameter("header");
                String message = request.getParameter("message");
                String pathToReport = reportsService.getLastReportsPath(sendReport);
                if (header.trim().equals("")) header = sendReport;
                if (message.trim().equals("")) message = "?????? ???????????????????????? ???????? - " + sendReport;
                if (pathToReport == null) {
                    servletHelper.setResponseText(response, 500, "Error sending report!");
                    return;
                }
                int index = pathToReport.indexOf(sendReport);
                String filename = pathToReport.substring(index);
                result = emailService.sendReportToEmail(email, header, message, pathToReport, filename);
                if (result != null) {
                    servletHelper.setResponseText(response, 500, result);
                } else {
                    servletHelper.setResponseText(response, 200, "Email successfully sent!");
                }
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (!servletHelper.checkOnAccess(request, response)) return;
        String report = request.getParameter("report");
        reportsService.setRequest(request);
        //reportsCreator.createReports();
        String path = null;
        switch (report) {
            case "VehicheReport":
                path = reportsService.getLastVehicheReportPath();
                break;
            case "VehicheJournaryTicketReport":
                path = reportsService.getLastVehicheJournaryTicketReportPath();
                break;
            case "CategoryReport":
                path = reportsService.getLastCategoryReportPath();
                break;
        }
        if (path == null)
            path = reportsService.getLastReportsPath(report);
        if (path == null) {
            response.setContentType("text/html;charset=UTF-8");
            response.getWriter().write("Report not found! Please generate a report!");
            return;
        }
        FileInputStream fis = new FileInputStream(new File(path));
        org.apache.commons.io.IOUtils.copy(fis, response.getOutputStream());
        response.setContentType("application/pdf");
        response.setHeader("Content-Disposition", "inline; filename=" + report);
        response.flushBuffer();
    }

    protected String generateReport(String generateReport) {
        String result = null;
        switch (generateReport) {
            case "generateVehicleReport":
                result = reportsService.createVehicleReport();
                break;
            case "generateVehicheJournaryTicketReport":
                result = reportsService.createVehicheJournaryTicketReport();
                break;
            case "generateCategoryReport":
                result = reportsService.createCategoryReport();
                break;
        }
        return result;
    }
}
