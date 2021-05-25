package report;

import java.io.Serializable;

public class LastReportsPath implements Serializable {
    private String lastVehicheReportPath;
    private String lastVehicheJournaryTicketReportPath;
    private String lastCategoryReportPath;

    public String getLastVehicheReportPath() {
        return lastVehicheReportPath;
    }

    public void setLastVehicheReportPath(String lastVehicheReportPath) {
        this.lastVehicheReportPath = lastVehicheReportPath;
    }

    public String getLastVehicheJournaryTicketReportPath() {
        return lastVehicheJournaryTicketReportPath;
    }

    public void setLastVehicheJournaryTicketReportPath(String lastVehicheJournaryTicketReportPath) {
        this.lastVehicheJournaryTicketReportPath = lastVehicheJournaryTicketReportPath;
    }

    public String getLastCategoryReportPath() {
        return lastCategoryReportPath;
    }

    public void setLastCategoryReportPath(String lastCategoryReportPath) {
        this.lastCategoryReportPath = lastCategoryReportPath;
    }
}
