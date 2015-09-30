package br.com.transmetais.mail;

import java.util.Properties;

import javax.mail.Address;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.apache.log4j.Logger;

import com.goshme.util.mail.Mail;

public class MailSSL {

	private static Logger logger = Logger.getLogger(MailSSL.class);
	
	private final String SMTP_AUTH_USER;
	private final String SMTP_AUTH_PWD;
	private final String SMTP_HOST_NAME;
	private final int SMTP_HOST_PORT;
	private boolean ssl = false;
	private boolean debug = false;
	
	
	static Properties mailServerProperties;
	static Session getMailSession;
	static MimeMessage generateMailMessage;
	
//	public MailSSL()
//	{
		//FIXME Logobada.tmp
//		this("contact@goshme.com", "qazcorora", "smtp.gmail.com", 465, true);
		
//	}
	
	public MailSSL(String smtpUser, String smtpPassword, String smtpHost, int smtpPort, boolean ssl)
	{
		this.SMTP_AUTH_USER = smtpUser;
		this.SMTP_AUTH_PWD = smtpPassword;
		this.SMTP_HOST_NAME = smtpHost;
		this.SMTP_HOST_PORT = smtpPort;
		this.ssl = ssl;
	}
	
	/** Retorna uma instancia do servidor de email padrao */
	public static MailSSL getInstance()
	{
		return new MailSSL("noreply", "noreply00", "69.16.206.120" , 25, false);
		//return new MailSSL("no-reply", "noreply00", "74.54.150.138" , 25, false);
			
	}
	
	private static MailSSL googleInstance = null;
	
	public static MailSSL getGoogleInstance(String googleUser, String googlePassword)
	{
		if(googleInstance==null){
			googleInstance = new MailSSL(googleUser, googlePassword, "smtp.gmail.com", 465, true);
		}
		return googleInstance;
	}	
	
	public void setDebug(boolean debug)
	{
		this.debug = debug;
	}

	public void sendAsTask(Mail mail)
	{
		class MailTask implements Runnable
		{	
			private Mail mail;
			
			public MailTask(Mail mail)
			{
				this.mail = mail;

			}
			
			public void run()
			{
				try {
					send(mail);
				}
				catch (Exception e) {
					 for(String receive : mail.getReceivers()) {
			        	logger.info("error ao enviar email para : "  + receive);
					}
					e.printStackTrace();
				}
			}
		}
		
		MailTask task = new MailTask(mail);
		new Thread(task).start();
	}

	
	
	public void send(Mail mail) throws MessagingException
    {
		Properties props = new Properties();
        /** Parâmetros de conexão com servidor Gmail */
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.socketFactory.port", "465");
        props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.port", "465");

        Session session = Session.getDefaultInstance(props,
                    new javax.mail.Authenticator() {
                         protected PasswordAuthentication getPasswordAuthentication() 
                         {
                               return new PasswordAuthentication("transmetaisweb@tmetais.com", "combatente");
                         }
                    });

        /** Ativa Debug para sessão */
        session.setDebug(true);

        try {
        	

              Message message = new MimeMessage(session);
              message.setFrom(new InternetAddress("transmetaisweb@tmetais.com")); //Remetente
              
              String destinatario = "";
              for (String dest : mail.getReceivers()) {
            	  if(destinatario!=""){
            		  destinatario = destinatario + "," + dest;
            	  }
            	  else{
            		  destinatario = dest;
            	  }
				
			}

              Address[] toUser = InternetAddress //Destinatário(s)
                         .parse(destinatario);  

              message.setRecipients(Message.RecipientType.TO, toUser);
              message.setSubject(mail.getSubject());//Assunto
             // message.setText();
              message.setContent (mail.getMessage(), "text/html");
              /**Método para enviar a mensagem criada*/
              Transport.send(message);

              

         } catch (MessagingException e) {
              throw new RuntimeException(e);
        }
    }	
	
//	public static void main(String[] args) throws Exception
//	{
//		StringBuilder textMsg = new StringBuilder();
//		textMsg.append("<br><br><br><center><b>Notificacaoo Automatica - Merger</b></center><br><br>");
//		textMsg.append("Sites rodando merger para adicionar no Goshme:<br><br><br>");
//		
//		for(int i = 0; i < 10; i++) {
//			textMsg.append(i + 1 + "- Site: XXXXXXXXXXXXX, Num. Doc Indexados: YYYYYY;<br>");
//		}
//		
//		textMsg.append("<br><br>");
//		textMsg.append("Gustavo Maia");
//		
//		String[] core = {"gustavo@goshme.com","tupy@goshme.com"};
//		
//		MailSSL n = MailSSL.getGoogleInstance("no-reply@jusbrasil.com.br", "noreply00");
//		Mail mail = Mail.createMail(core, "TESTE EMAIL JUS MINHA PICA", textMsg.toString());
//		mail.setFrom("Osvaldo Junior", "tupy@riachao.com");
//		n.send(mail);
//	}
	
	
	public static void main(String[] args) {
        
  }




}