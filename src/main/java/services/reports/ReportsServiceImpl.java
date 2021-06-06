package services.reports;

import utils.ReportCreator;

import javax.servlet.http.HttpServletRequest;

public class ReportsServiceImpl implements IReportsService {
    private ReportCreator reportCreator;

    public ReportsServiceImpl(){
        this.reportCreator = new ReportCreator();
    }

    public void setRequest(HttpServletRequest request) {
        reportCreator.setRequest(request);
    }

    public void createReports(){
        reportCreator.createReports();
    }

    public String createVehicleReport() {
        return reportCreator.createVehicleReport();
    }

    public String createVehicheJournaryTicketReport() {
        return reportCreator.createVehicheJournaryTicketReport();
    }

    public String createCategoryReport(){
        return reportCreator.createCategoryReport();
    }

    public String getLastReportsPath(String reportName){
        return reportCreator.getLastReportsPath(reportName);
    }

    public String getLastVehicheReportPath() {
        return reportCreator.getLastVehicheReportPath();
    }

    public String getLastVehicheJournaryTicketReportPath() {
        return reportCreator.getLastVehicheJournaryTicketReportPath();
    }

    public String getLastCategoryReportPath() {
        return reportCreator.getLastCategoryReportPath();
    }
}
