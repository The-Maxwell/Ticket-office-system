package services.email;

public interface IEmailService {
    String sendReportToEmail(String to, String header, String textMessage, String pathToReport, String filename);
}
