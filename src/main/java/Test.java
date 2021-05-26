
public class Test {
    public static void main(String[] args) {
        String s = "C:\\Users\\Admin\\IdeaProjects\\DBCursProject\\target\\DBCursProject-1.0-SNAPSHOT\\WEB-INF\\classes\\reports\\VehicheReport_2021_05_25_18_35_23.html";
        int i = s.indexOf("VehicheReport");
        System.out.println(i);
        System.out.println(s.charAt(i)+" "+s.charAt(i+1)+" "+s.charAt(i+2));
        System.out.println(s.substring(i));
    }
}
