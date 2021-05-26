package report;

import net.sf.dynamicreports.jasper.builder.JasperReportBuilder;
import net.sf.dynamicreports.report.builder.DynamicReports;
import net.sf.dynamicreports.report.builder.column.TextColumnBuilder;
import net.sf.dynamicreports.report.builder.component.Components;
import net.sf.dynamicreports.report.builder.datatype.DataTypes;
import net.sf.dynamicreports.report.builder.group.CustomGroupBuilder;
import net.sf.dynamicreports.report.builder.style.StyleBuilder;
import net.sf.dynamicreports.report.builder.subtotal.AggregationSubtotalBuilder;
import net.sf.dynamicreports.report.constant.GroupHeaderLayout;
import net.sf.dynamicreports.report.constant.HorizontalAlignment;
import net.sf.dynamicreports.report.constant.HorizontalTextAlignment;
import net.sf.dynamicreports.report.constant.VerticalAlignment;

import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.math.BigDecimal;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.Date;

import static net.sf.dynamicreports.report.builder.DynamicReports.*;

public class ReportsCreator{

    private String url = "jdbc:mysql://localhost/ticketoffice_c?serverTimezone=Europe/Moscow&allowPublicKeyRetrieval=true&useSSL=false";
    private String username = "root";
    private String password = "root";
    private String scriptSelectTemplate = "SELECT * FROM ";
    private SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy_MM_dd_HH_mm_ss");
    private HttpServletRequest request;

    public static String lastVehicheReportPath = null;
    public static String lastVehicheJournaryTicketReportPath = null;
    public static String lastCategoryReportPath = null;

    private StyleBuilder fontStyle = stl.style().setFontName("DejaVu Serif");

    public void setRequest(HttpServletRequest request) {
        this.request = request;
    }

    public String[] createReports(){
        return new String[]{createVehicleReport(), createVehicheJournaryTicketReport(), createCategoryReport()};

    }

    private String createVehicleReport() {
        try(Connection connection = DriverManager.getConnection(url, username, password)) {
            JasperReportBuilder report = DynamicReports.report();
            TextColumnBuilder<String> vehicleCodeColumn = col.column("Vehicle code", "Vehicle_code", DataTypes.stringType());
            TextColumnBuilder<String> vehicleTypeColomn = col.column("Vehicle type", "Vehicle_type", DataTypes.stringType());
            vehicleTypeColomn.setHorizontalTextAlignment(HorizontalTextAlignment.RIGHT);
            TextColumnBuilder<Integer> numberSeatColumn = col.column("Number of seats", "Number_of_seats", DataTypes.integerType());
            TextColumnBuilder<Integer> numberSeatEconomColumn = col.column("Number of economy class seats", "Number_of_economy_class_seats", DataTypes.integerType());
            TextColumnBuilder<Integer> numberSeatMediumColumn = col.column("Number of medium class seats", "Number_of_medium_class_seats", DataTypes.integerType());
            TextColumnBuilder<Integer> numberSeatLuxuryColumn = col.column("Number of luxury class seats", "Number_of_luxury_class_seats", DataTypes.integerType());
            TextColumnBuilder<String> vehicleCompanyColumn = col.column("Vechile company", "Vechile_company", DataTypes.stringType());

            AggregationSubtotalBuilder<Long> vehicleSum = sbt.count(vehicleCodeColumn).setLabel("Всього трансп. засобів");
            AggregationSubtotalBuilder<Number> avgNumberSeat = sbt.avg(numberSeatColumn).setLabel("Середня кількість місць").setShowInColumn(numberSeatColumn);
            AggregationSubtotalBuilder<Long> vehicleCompanyDistSum = sbt.distinctCount(vehicleCompanyColumn).setLabel("Всього унікальних трансп. компаній").setShowInColumn(vehicleCompanyColumn);
            report.setTemplate(Templates.reportTemplate)
                    .columns(
                            vehicleCodeColumn,
                            vehicleTypeColomn,
                            numberSeatColumn,
                            numberSeatEconomColumn,
                            numberSeatMediumColumn,
                            numberSeatLuxuryColumn,
                            vehicleCompanyColumn.setStyle(fontStyle)
                    ).subtotalsAtSummary(
                    vehicleSum,
                    vehicleCompanyDistSum,
                    avgNumberSeat
                    )
                    .title(
                            Templates.createTitleComponent("Vehicle Report")
                    ).pageFooter(Components.pageXofY())
                    .setDataSource("SELECT * FROM vehicle", connection);
            try {
                String dateTime = simpleDateFormat.format(new Date(System.currentTimeMillis()));
                String relativePath = request.getServletContext().getRealPath("/WEB-INF/classes/reports");
                report.toPdf(new FileOutputStream(relativePath+"\\VehicheReport_" + dateTime + ".pdf"));
                lastVehicheReportPath = relativePath + "\\VehicheReport_" + dateTime + ".pdf";
                saveLastReportsPath("VehicheReport");
                return relativePath + "\\VehicheReport_" + dateTime + ".pdf";
            } catch (Exception e) {
                e.printStackTrace();
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }
    private String createVehicheJournaryTicketReport() {
        try(Connection connection = DriverManager.getConnection(url, username, password)) {
            StyleBuilder groupStyle = stl.style().bold().setFontName("DejaVu Serif");;
            CustomGroupBuilder vehicleGroup = grp.group("Vehicle_code", String.class)
                    .setStyle(groupStyle)
                    .setTitle("Номер транспортного засобу:")
                    .setTitleStyle(groupStyle)
                    .setTitleWidth(90)
                    .setHeaderLayout(GroupHeaderLayout.TITLE_AND_VALUE);
            CustomGroupBuilder journaryGroup = grp.group("Journary_number", Integer.class)
                    .setStyle(groupStyle)
                    .setTitle("Номер рейсу:")
                    .setTitleStyle(groupStyle)
                    .setTitleWidth(90)
                    .setHeaderLayout(GroupHeaderLayout.TITLE_AND_VALUE);

            JasperReportBuilder report = DynamicReports.report();
            TextColumnBuilder<String> vehicleCodeColumn = col.column("Vehicle code", "Vehicle_code", DataTypes.stringType());
            TextColumnBuilder<String> vehicleCompanyColumn = col.column("Vechile company", "Vechile_company", DataTypes.stringType());

            TextColumnBuilder<Integer> journaryNumberColumn = col.column("Journary number", "Journary_number", DataTypes.integerType());
            TextColumnBuilder<String> departurePointColomn = col.column("Departure point", "Departure_point", DataTypes.stringType());
            TextColumnBuilder<String> destinationColomn = col.column("Destination", "Destination", DataTypes.stringType());

            TextColumnBuilder<Integer> ticketNumberColumn = col.column("Ticket number", "Ticket_number", DataTypes.integerType());
            TextColumnBuilder<String> categoryColomn = col.column("Category", "Category", DataTypes.stringType());
            categoryColomn.setHorizontalTextAlignment(HorizontalTextAlignment.RIGHT);
            TextColumnBuilder<BigDecimal> costColomn = col.column("Cost", "Cost", DataTypes.bigDecimalType());
            TextColumnBuilder<Integer> sequenceNumberColumn = col.column("Sequence_number", "Sequence_number", DataTypes.integerType());

            AggregationSubtotalBuilder<Long> ticketJourCount = sbt.count(ticketNumberColumn).setLabel("Загальна кількість квитків:");
            AggregationSubtotalBuilder<BigDecimal> maxCost = sbt.max(costColomn).setLabel("Максимальна ціна");
            AggregationSubtotalBuilder<BigDecimal> costSum = sbt.sum(costColomn).setLabel("Загальна ціна");
            AggregationSubtotalBuilder<Number> avgSequenceNumber = sbt.avg(sequenceNumberColumn).setLabel("Середнє значення місця");

            AggregationSubtotalBuilder<Long> jourCount = sbt.distinctCount(journaryNumberColumn).setLabel("Загальна кількість рейсів:").setShowInColumn(ticketNumberColumn);
            AggregationSubtotalBuilder<Long> distDeparture = sbt.distinctCount(departurePointColomn).setLabel("Кількість унікальних точок відправки").setShowInColumn(categoryColomn);
            AggregationSubtotalBuilder<Long> distDestination = sbt.distinctCount(destinationColomn).setLabel("Кількість унікальних точок прибуття").setShowInColumn(costColomn);

            AggregationSubtotalBuilder<Long> vehicleSum = sbt.distinctCount(vehicleCodeColumn).setLabel("Всього трансп. засобів").setShowInColumn(ticketNumberColumn);
            AggregationSubtotalBuilder<Long> vehicleCompanyDistSum = sbt.distinctCount(vehicleCompanyColumn).setLabel("Всього унікальних трансп. компаній").setShowInColumn(ticketNumberColumn);

            report.setTemplate(Templates.reportTemplate)
                    .columns(
                            ticketNumberColumn,
                            categoryColomn,
                            costColomn,
                            sequenceNumberColumn
                    )
                    .groupBy(vehicleGroup, journaryGroup)
                    .subtotalsAtFirstGroupFooter(jourCount, distDeparture, distDestination)
                    .subtotalsAtLastGroupFooter(ticketJourCount, maxCost, costSum, avgSequenceNumber)
                    .subtotalsAtSummary(
                            vehicleSum,
                            vehicleCompanyDistSum
                    )
                    .title(Templates.createTitleComponent("Vehicles, journaries, tickets Report"))
                    .pageFooter(Templates.footerComponent)
                    .setDataSource("SELECT v.*, j.*, t.* \n" +
                            "FROM vehicle v, journary j, ticket t\n" +
                            "WHERE v.Vehicle_code=j.Vechile_id AND j.Journary_number=t.Journary_id\n" +
                            "ORDER BY v.Vehicle_code, j.Journary_number\n", connection);
            try {
                String dateTime = simpleDateFormat.format(new Date(System.currentTimeMillis()));
                String relativePath = request.getServletContext().getRealPath("/WEB-INF/classes/reports");
                report.toPdf(new FileOutputStream(relativePath+"\\VehicheJournaryTicketReport_" + dateTime + ".pdf"));
                lastVehicheJournaryTicketReportPath = relativePath + "\\VehicheJournaryTicketReport_" + dateTime + ".pdf";
                saveLastReportsPath("VehicheJournaryTicketReport");
                return relativePath + "\\VehicheJournaryTicketReport_" + dateTime + ".pdf";
            } catch (Exception e) {
                e.printStackTrace();
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }

    private String createCategoryReport(){
        try(Connection connection = DriverManager.getConnection(url, username, password)) {
            JasperReportBuilder report = DynamicReports.report();
            TextColumnBuilder<String> categoryColumn = DynamicReports.col.column("Category", "Category", DataTypes.stringType());
            categoryColumn.setStyle(fontStyle);
            categoryColumn.setHorizontalTextAlignment(HorizontalTextAlignment.CENTER);
            TextColumnBuilder<Integer> categoryCountColumn = DynamicReports.col.column("Count", "categoryCount", DataTypes.integerType());

            report.setTemplate(Templates.reportTemplate)
                    .columns(
                            categoryColumn,
                            categoryCountColumn
                    ).setColumnStyle(DynamicReports.stl.style().setVerticalAlignment(VerticalAlignment.MIDDLE).setHorizontalAlignment(HorizontalAlignment.CENTER))
                    .columnGrid(

                    ).summary(
                    DynamicReports.cht.pie3DChart()
                            .setTitle("Category chart")
                            .setKey(categoryColumn)
                            .series(
                                    DynamicReports.cht.serie(categoryCountColumn)),
                    DynamicReports.cht.bar3DChart()
                            .setTitle("Category chart 2")
                            .setCategory(categoryColumn)
                            .series(
                                    DynamicReports.cht.serie(categoryCountColumn)
                            )
                            .setCategoryAxisFormat(
                                    DynamicReports.cht.axisFormat().setLabel("Category"))
            )
                    .highlightDetailEvenRows()
                    .title(Templates.createTitleComponent("Category Report"))
                    .pageFooter(Templates.footerComponent)
                    .setDataSource("SELECT Category, COUNT(*) AS 'categoryCount'\n" +
                            "FROM passenger\n" +
                            "GROUP BY Category", connection);
            try {
                String dateTime = simpleDateFormat.format(new Date(System.currentTimeMillis()));
                String relativePath = request.getServletContext().getRealPath("/WEB-INF/classes/reports");
                report.toPdf(new FileOutputStream(relativePath+"\\CategoryReport_" + dateTime + ".pdf"));
                lastCategoryReportPath = relativePath + "\\CategoryReport_" + dateTime + ".pdf";
                saveLastReportsPath("CategoryReport");
                return relativePath + "\\CategoryReport_" + dateTime + ".pdf";
            } catch (Exception e) {
                e.printStackTrace();
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }

    public void saveLastReportsPath(String reportName){
        String relativePath = request.getServletContext().getRealPath("/WEB-INF/classes/reports");
        LastReportsPath p;
        if(new File(relativePath + "/reports_path.obj").exists()) {
            try(ObjectInputStream ois = new ObjectInputStream(new FileInputStream(relativePath + "/reports_path.obj")))
            {
                p=(LastReportsPath)ois.readObject();
            }
            catch(Exception ex){
                System.out.println(ex.getMessage());
                p = new LastReportsPath();
            }
        }
        else p = new LastReportsPath();
        try(ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(relativePath + "/reports_path.obj")))
        {
            switch (reportName){
                case "VehicheReport":
                    p.setLastVehicheReportPath(lastVehicheReportPath);
                    break;
                case "VehicheJournaryTicketReport":
                    p.setLastVehicheJournaryTicketReportPath(lastVehicheJournaryTicketReportPath);
                    break;
                case "CategoryReport":
                    p.setLastCategoryReportPath(lastCategoryReportPath);
                    break;
            }
            oos.writeObject(p);
        }
        catch(Exception ex){
            System.out.println(ex.getMessage());
        }
    }
    public String getLastReportsPath(String reportName){
        String relativePath = request.getServletContext().getRealPath("/WEB-INF/classes/reports");
        try(ObjectInputStream ois = new ObjectInputStream(new FileInputStream(relativePath + "/reports_path.obj")))
        {
            LastReportsPath p = (LastReportsPath)ois.readObject();
            switch (reportName){
                case "VehicheReport":
                    return p.getLastVehicheReportPath();
                case "VehicheJournaryTicketReport":
                    return p.getLastVehicheJournaryTicketReportPath();
                case "CategoryReport":
                    return p.getLastCategoryReportPath();
            }
        }
        catch(Exception ex){
            System.out.println(ex.getMessage());
            ex.printStackTrace();
        }
        return null;
    }
}
