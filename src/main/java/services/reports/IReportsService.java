package services.reports;

import javax.servlet.http.HttpServletRequest;

public interface IReportsService {
    void setRequest(HttpServletRequest request);
    String createVehicleReport();
    String createVehicheJournaryTicketReport();
    String createCategoryReport();
    void saveLastReportsPath(String reportName);
    String getLastReportsPath(String reportName);
}
