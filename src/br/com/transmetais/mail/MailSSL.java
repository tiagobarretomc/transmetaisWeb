package br.com.transmetais.mail;

import java.util.Date;
import java.util.Properties;

import javax.mail.Address;
import javax.mail.Message;
import javax.mail.MessagingException;
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

        if(ssl){
	        props.put("mail.transport.protocol", "smtps");
	        props.put("mail.smtps.host", SMTP_HOST_NAME);
	        props.put("mail.smtps.auth", "true");
        }
        else{
        	props.put("mail.transport.protocol", "smtp");
        	props.put("mail.smtp.auth", "true");
			props.put("mail.smtp.host", SMTP_HOST_NAME);
			props.put("mail.smtp.user", SMTP_AUTH_USER);
			//deus eh quem sabe, evita o erro de javax.mail.MessagingException: 501 Syntax: HELO hostname
			props.put("mail.smtp.localhost", "toto");
        }
        
        //System.out.println(props.toString());
        Session mailSession = Session.getDefaultInstance(props, null);       
        mailSession.setDebug(debug);
        
        Transport transport = mailSession.getTransport();

        MimeMessage msg = new MimeMessage(mailSession);
        msg.setSubject(mail.getSubject() == null ? "" : mail.getSubject());
        //msg.setContent(mail.getMessage(), "text/html");
        //tava com problemas na acentuacao
        msg.setText(mail.getMessage(), "UTF-8", "html");
        msg.setSentDate(new Date());
        
        msg.setFrom(mail.getFrom());
        
        if(mail.getReply()!=null){
        	msg.setReplyTo(new Address[]{mail.getReply()});
        }
        
        for(String receive : mail.getReceivers()) {
        	msg.addRecipient(Message.RecipientType.TO, new InternetAddress(receive));
		}
        
        for(String copy : mail.getCopies()) {
        	msg.addRecipient(Message.RecipientType.CC, new InternetAddress(copy));
		}
        
        transport.connect(SMTP_HOST_NAME, SMTP_HOST_PORT, SMTP_AUTH_USER, SMTP_AUTH_PWD);
        

        transport.sendMessage(msg, msg.getRecipients(Message.RecipientType.TO));
        transport.close();
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
	
	
	public static void main(String[] args) throws Exception
	{
		//https://www.google.com/a/cpanel/autosbr.com/Dashboard
		Mail mail = Mail.createMail(new String[]{"wrlsportsnews@gmail.com"}, "SMTP teste", "Foi");
		mail.setFrom("Aroldo hehehe", "wrlsportsnews@autosbr.com");
		MailSSL.getInstance().send(mail);
		
//		MailSSL sender = new MailSSL("bruno", "010203","174.132.34.212" ,25,false);
//		sender.setDebug(true);
//		sender.send(mail);
		
		System.out.println("fim");
	}
}