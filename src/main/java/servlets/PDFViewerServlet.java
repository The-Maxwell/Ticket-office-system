package servlets;

import report.ReportsCreator;
import utils.TicketOfficeDao;

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
        String path = null;
        switch (report){
            case "VehicheReport":
                path = ReportsCreator.lastVehicheReportPath;
                break;
            case "VehicheJournaryTicketReport":
                break;
            case "CategoryReport":
                break;
        }
        System.out.println("Path= " + path);
        FileInputStream fis = new FileInputStream(new File(path));

        // Fast way to copy a bytearray from InputStream to OutputStream
        org.apache.commons.io.IOUtils.copy(fis, response.getOutputStream());
        response.setContentType("application/pdf");
        response.setHeader("Content-Disposition", "inline; filename=" + report);
        response.flushBuffer();
    }
}
