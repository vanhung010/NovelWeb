package project.projectmanagernovel.util;

import jakarta.mail.*;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;

import java.util.Properties;
import java.util.Random;

public class EmailUtil {
    private static final String MY_EMAIL = "vanhungx84765@gmail.com";
    private static final String MY_PASSWORD = "pbzuezlwiiiegpqm";

    public static String generateOTP(){
        Random random = new Random();
        int otp = 100000+random.nextInt(900000);
        return String.valueOf(otp);
    }

    public static boolean sendEmail(String toEmail, String otp){
       boolean isSuccess = false;
        //cấu hình thông số máy chủ emaiil
        Properties properties = new Properties();
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.smtp.host", "smtp.gmail.com");
        properties.put("mail.smtp.port", "587");

        Session session = Session.getInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(MY_EMAIL, MY_PASSWORD);
            }
        });

        try {
            //Soạn thư
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(MY_EMAIL)); // Người gửi
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toEmail)); // Người nhận
            message.setSubject("Mã xác nhận khôi phục mật khẩu - NovelWeb"); // Tiêu đề thư


            // Nội dung thư (Hỗ trợ viết mã HTML cho đẹp)
            String htmlContent = "<div style='font-family: Arial, sans-serif; padding: 20px; text-align: center;'>"
                    + "<h2>Chào bạn,</h2>"
                    + "<p>Bạn đã yêu cầu đặt lại mật khẩu tại NovelWeb.</p>"
                    + "<p>Mã xác minh (OTP) của bạn là:</p>"
                    + "<h1 style='color: #007bff; background: #f4f4f4; padding: 10px; display: inline-block; border-radius: 5px;'>"
                    + otp + "</h1>"
                    + "<p>Mã này sẽ hết hạn sau 5 phút. Vui lòng không chia sẻ mã này cho bất kỳ ai.</p>"
                    + "</div>";

            message.setContent(htmlContent, "text/html; charset=utf-8");

            Transport.send(message);
            isSuccess = true;

        } catch (MessagingException e) {
           e.printStackTrace();
        }
return isSuccess;
    }

}
