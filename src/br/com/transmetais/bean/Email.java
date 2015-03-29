package br.com.transmetais.bean;

import javax.mail.MessagingException;

import br.com.transmetais.mail.MailSSL;

import com.goshme.util.mail.Mail;

public class Email {
	private String[] para;
	private String   assunto;
	private String 	 mensagem;
	private String   de;
	private String   deAlias;
	
	public String[] getPara() {
		return para;
	}

	public void setPara(String[] para) {
		this.para = para;
	}

	public String getAssunto() {
		return assunto;
	}

	public void setAssunto(String assunto) {
		this.assunto = assunto;
	}

	public String getMensagem() {
		return mensagem;
	}

	public void setMensagem(String mensagem) {
		this.mensagem = mensagem;
	}

	public String getDe() {
		return de;
	}

	public void setDe(String de) {
		this.de = de;
	}

	public String getDeAlias() {
		return deAlias;
	}

	public void setDeAlias(String deAlias) {
		this.deAlias = deAlias;
	}

	public void enviar() throws MessagingException{
		Mail mail = Mail.createMail(this.para, this.assunto, this.mensagem);
		mail.setFrom(this.deAlias, this.de);
		MailSSL.getInstance().send(mail);

	}
}
