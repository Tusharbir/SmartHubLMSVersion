package cseb.tech.smarthublms;

import android.os.AsyncTask;
import android.util.Log;

import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.Properties;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class SMTPMailSender extends AsyncTask<Void,Void,Void> {

   private static final String[] SMTP_H1 = new String[1];
    private static final String[] SMTP_id = new String[1];
    private  static final String[] SMTP_accessRegion = new String[1];
    private static final String[] FROM_EMAIL = new String[1];

    private static String email1;
    private static String subject1;
    private static String nameS1;
    private static String branchS1;
    private static String otp1;

        @Override
        protected Void doInBackground(Void... voids) {
            try {
                String htmlContent = "<div style=\"font-family: Arial, sans-serif; padding: 40px 0; background-color: #f7f7f7; width: 100%; height: 100%;\">"
                        + "<div style=\"max-width: 600px; margin: 0 auto; background-color: #ffffff; padding: 20px 40px; border-radius: 8px; box-shadow: 0 2px 10px rgba(0, 0, 0, 0.1);\">"
                        + "<h2 style=\"color: #333333; border-bottom: 2px solid #eeeeee; padding-bottom: 10px; margin-bottom: 20px;\">Enrollment and Credentials as HOD</h2>"
                        + "<p style=\"color: #555555; font-size: 16px; line-height: 1.5; margin-bottom: 20px;\">Dear " + nameS1 + ",</p>"
                        + "<p style=\"color: #555555; font-size: 16px; line-height: 1.5; margin-bottom: 20px;\">Now enrolled as HOD for Department: " + branchS1 + "</p>"
                        + "<p style=\"color: #555555; font-size: 16px; line-height: 1.5; margin-bottom: 20px;\">Login with this email on which you received the message and OTP: " + otp1 + "</p>"
                        + "<p style=\"color: #555555; font-size: 16px; line-height: 1.5; margin-bottom: 20px;\">........................................................................................................</p>"
                        + "<p style=\"color: #555555; font-size: 16px; line-height: 1.5; margin-bottom: 20px;\">Please change your password after login.</p>"
                        + "<a href=\"[APP_DOWNLOAD_LINK]\" style=\"background-color: #4CAF50; color: white; padding: 12px 25px; text-decoration: none; display: inline-block; border-radius: 4px; margin-bottom: 20px;\">Download the App</a>"
                        + "<div style=\"border-top: 2px solid #eeeeee; padding-top: 20px; margin-top: 20px; text-align: center;\">"
                        + "<img src=\"https://drive.google.com/uc?export=view&id=1fDnRfCIuUWFJRUjbXjqE8pYiQCxaWT1k\" alt=\"Your App Logo\" style=\" width: 300px;  display: block; margin: 20px auto; margin-bottom: 10px;\" />"
                        + "<p style=\"color: #888888; font-size: 14px;\">SmartHub:UNI LMS</p>"
                        + "<h4 style=\"color: #888888; font-size: 14px;\">Powered By CSEB.TECH</p>"
                        + "<p style=\"color: #888888; font-size: 14px;\">Reply to this email if any error occurs</p>"
                        + "</div>"
                        + "</div>"
                        + "</div>";


                Properties props = new Properties();
                props.put("mail.smtp.host", SMTP_H1[0]);
                props.put("mail.smtp.port", "587");
                props.put("mail.smtp.auth", "true");
                props.put("mail.smtp.starttls.enable", "true");

                Session session = Session.getInstance(props,
                        new javax.mail.Authenticator() {
                            protected PasswordAuthentication getPasswordAuthentication() {
                                return new PasswordAuthentication(SMTP_id[0], SMTP_accessRegion[0]);
                            }
                        });

                Message message = new MimeMessage(session);
                message.setFrom(new InternetAddress(FROM_EMAIL[0]));
                message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(email1));
                message.setSubject(subject1);
                message.setContent(htmlContent, "text/html");

                Transport.send(message);
                System.out.println("Email sent successfully!");

            } catch (MessagingException e) {
                throw new RuntimeException(e);
            }
            // Your email sending logic here
            return null;
        }
    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        // Code to execute after email is sent (e.g., update UI)
    }

    public static void smtpMailSender(String email, String subject, String nameS, String branchS, String otp){

           email1=email;
           subject1=subject;
           nameS1=nameS;
           branchS1=branchS;
           otp1=otp;





        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("SMTP Email").get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
            for (QueryDocumentSnapshot document : task.getResult()) {

                SMTP_H1[0] =document.getString("smtpHostRegion");
                SMTP_id[0] =document.getString("usernameEmail");
                SMTP_accessRegion[0] = document.getString("smtpAccessRegion");
                FROM_EMAIL[0] = document.getString("emailId");

                Log.i("values fetched from smtp", SMTP_H1[0]);

                new SMTPMailSender().execute();






            }}
            else
                {
                    Log.i("didnt fetched any key","error");
                }

        });








    }
}
