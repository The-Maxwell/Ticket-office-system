package servlets;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "RedsrectServlet")
public class RedirectServlet extends HttpServlet {

    private String[] arrPatternAction = new String[]{"Enter the data about the new object in the field according to the pattern.<br />" +
            "Use a comma delimiter. Do not put spaces before and after the comma.<br /><br />",
    "Enter or change object data according to a pattern.<br />Use a comma delimiter. Do not put spaces before and after the comma.<br /><br />",
    "Input ID of elemets to delete.<br /><br"};
    private String[] arrPatternsOfRecords = new String[]
            {"Vehicle_code,Vehicle_type,Number_of_seats,Number_of_economy_class_seats,Number_of_medium_class_seats,Number_of_luxury_class_seats,Vechile_company<br /><br />" +
                    "2,bus,50,40,10,0,Автолюкс", "Journary_number,Departure_point,Destination,Date_and_time_of_arrival,Date_and_time_of_departure,Vehicle_id<br /><br />" +
                    "2,Чернігів,Київ,2020-01-25 09:00:00.0,2020-01-25 13:00:00.0,1","Receipt_code,Data_and_time_of_sale,Data_and_time_of_booking,Total_price,Passenger_id<br /><br />" +
                    "4,2020-03-06 18:54:06.0,2020-04-17 12:37:49.0,200.00,4","Ticket_number,Category,Cost,Sequence_number,Reciept_id,Journary_id<br /><br />" +
                    "4,econom,200.00,34,4,14","Passenger_code,Last_name,First_name,Surname,Category<br /><br />3,Бойко,Петро,Олексійович,Без пільг"};

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        StringBuilder sb = new StringBuilder();
        switch (request.getParameter("act")){
            case "Add":
                sb.append(arrPatternAction[0]);
                break;
            case "Update":
                sb.append(arrPatternAction[1]);
                break;
            case "Delete":
                sb.append(arrPatternAction[2]);
                break;
        }
        if(!request.getParameter("act").equals("delete")){
            switch (request.getParameter("table")){
                case "journary":
                    sb.append(arrPatternsOfRecords[1]);
                    break;
                case "vehicle":
                    sb.append(arrPatternsOfRecords[0]);
                    break;
                case "receipt":
                    sb.append(arrPatternsOfRecords[2]);
                    break;
                case "passenger":
                    sb.append(arrPatternsOfRecords[4]);
                    break;
                case "ticket":
                    sb.append(arrPatternsOfRecords[3]);
                    break;
            }
        }

        request.setAttribute("pattern1", sb.toString());
        request.setAttribute("title", request.getParameter("table"));
        request.setAttribute("buttonValue", request.getParameter("act"));

        RequestDispatcher requestDispatcher = request.getRequestDispatcher("views/add.jsp");
        requestDispatcher.forward(request, response);
    }
}
