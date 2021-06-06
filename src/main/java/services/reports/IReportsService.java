package services.reports;

import javax.servlet.http.HttpServletRequest;

public interface IReportsService {
    void setRequest(HttpServletRequest request);
    String createVehicleReport();
    String createVehicheJournaryTicketReport();
    String createCategoryReport();
    String getLastReportsPath(String reportName);
    String getLastVehicheReportPath();
    String getLastVehicheJournaryTicketReportPath();
    String getLastCategoryReportPath();
}
