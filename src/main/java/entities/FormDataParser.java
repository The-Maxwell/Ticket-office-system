package entities;

import javax.servlet.http.HttpServletRequest;

public class FormDataParser {
    public static String getEtringEntityFromRequest(HttpServletRequest request){
        return null;
    }

    public static String getStringEntity(String ...values){
        System.out.println(String.join(",", values));
        return String.join(",", values);
    }
}
