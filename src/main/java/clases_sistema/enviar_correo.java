package clases_sistema;

/**
 * La clase enviar_correo permite enviar correos electrónicos a través de un servidor SMTP.
 * @autor Richard Soria
 */

// Importaciones necesarias
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

/**
 * Enviar correo electrónico a través de un servidor SMTP.
 * */
public class enviar_correo {

    // Definición de las credenciales del correo electrónico
    private final String correo = "rickimauri04@hotmail.com";
    private final String contrasena = "mdhhtafdcduuhsny"; // Asegúrate de usar una contraseña segura o una contraseña de aplicación

    /**
     * Método que obtiene las propiedades del servidor SMTP.
     * @return Propiedades del servidor SMTP.
     * */
    private Properties getProperties() {
        Properties properties = new Properties();
        properties.put("mail.smtp.host", "smtp.office365.com");
        properties.put("mail.smtp.port", "587");
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.smtp.ssl.protocols", "TLSv1.2");
        return properties;
    }

    /**
     * Método que envía un correo electrónico a través de un servidor SMTP.
     * @param destinatario Dirección de correo electrónico del destinatario.
     * @param asunto Asunto del correo electrónico.
     * @param mensaje Cuerpo del correo electrónico.
     * */
    public void enviarCorreo(String destinatario, String asunto, String mensaje) {
        Properties properties = getProperties();
        Session session = Session.getInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(correo, contrasena);
            }
        });
        try {
            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress(correo));
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(destinatario));
            message.setSubject(asunto);
            message.setText(mensaje);
            Transport.send(message);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }
}
