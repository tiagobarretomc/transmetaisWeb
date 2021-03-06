package br.com.transmetais.mail;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;

import br.com.caelum.vraptor.ioc.Component;
import br.com.caelum.vraptor.ioc.RequestScoped;
import br.com.transmetais.bean.Compra;
import br.com.transmetais.bean.Email;
import br.com.transmetais.bean.Usuario;
import br.com.transmetais.util.FreemarkerUtil;
import freemarker.template.TemplateException;

@Component
@RequestScoped
public class EmailService {
	
	private HttpServletRequest req;
	
	public EmailService(HttpServletRequest req){
		this.req = req;
	}
	
	public void enviarEmailContasAPagar(Usuario usuario){
		Map<String, Object> map = new HashMap<String, Object>();
		
		usuario = new Usuario();
		usuario.setNome("Tiago Barreto");
		usuario.setSenha("Tiago Barreto");
		usuario.setId(Long.getLong("1"));
		
		map.put("usuario", usuario);
		map.put("url", req.getContextPath());
		String corpoEmail = null;
		try {
			corpoEmail = FreemarkerUtil.parseTemplate(map, "templateEmailAlteracaoSenha.htm");
			
			Email email = new Email();
			email.setPara(new String[]{"tiago@tmetais.com"});
			email.setDe("valmir@tmetais.com");
			email.setAssunto("[Transmetais Web] - Relatório Diário de contas à pagar");
			email.setDeAlias("Transmetais Web");
			email.setMensagem(corpoEmail);
			email.enviar();
				
			 
			
			
			
		} 
		catch (TemplateException e) {
			
			e.printStackTrace();
		}
		catch (IOException e) {
			e.printStackTrace();
		}
		catch (MessagingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			
		}
		
	}
	
	public void enviarEmailCompraEmitida(Compra compra){
		Map<String, Object> map = new HashMap<String, Object>();
		
		
		map.put("compra", compra);
		map.put("url", req.getContextPath());
		String corpoEmail = null;
		try {
			corpoEmail = FreemarkerUtil.parseTemplate(map, "templateCompra.htm");
			
			Email email = new Email();
			email.setPara(new String[]{"tiago@tmetais.com"});
			email.setDe("transmetaisweb@tmetais.com");
			email.setAssunto("[Transmetais Web] - Cadastro de Compra");
			email.setDeAlias("Transmetais Web");
			email.setMensagem(corpoEmail);
			email.enviar();
				
			 
			
			
			
		} 
		catch (TemplateException e) {
			
			e.printStackTrace();
		}
		catch (IOException e) {
			e.printStackTrace();
		}
		catch (MessagingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			
		}
		
	}

}
