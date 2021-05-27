package entities;

import javax.servlet.http.HttpServletRequest;

public class FormIEntityDataParser {
    public static String getStringEntity(HttpServletRequest request){
        String table = request.getParameter("table");
        switch (table) {
            case "vehicle":
                return getStringJoin(request.getParameter("vehicleCode"),request.getParameter("vehicleType"),request.getParameter("numberOfSeats"),
                        request.getParameter("numberOfEconomyClassSeats"),request.getParameter("numberOfMediumClassSeats"),request.getParameter("numberOfLuxuryClassSeats"),
                        request.getParameter("vechileCompany"));
            case "journary":
                return getStringJoin(request.getParameter("departurePoint"), request.getParameter("destination"), request.getParameter("dateAndTimeOfArrival"),
                        request.getParameter("dateAndTimeOfDeparture"), request.getParameter("vechileId"));
            case "receipt":
                return getStringJoin(request.getParameter("dataAndTimeOfSale"), request.getParameter("dataAndTimeOfBooking"), request.getParameter("totalPrice"),
                        request.getParameter("passengerId"));
            case "ticket":
                return getStringJoin(request.getParameter("category"), request.getParameter("cost"), request.getParameter("sequenceNumber"),
                        request.getParameter("receiptId"), request.getParameter("journaryId"));
            case "passenger":
                return getStringJoin(request.getParameter("lastName"), request.getParameter("firstName"), request.getParameter("surname"),
                        request.getParameter("category"));
            default:
                return null;
        }
    }
    public static String getStringJoin(String ...values){
        System.out.println(String.join(",", values));
        return String.join(",", values);
    }
}
