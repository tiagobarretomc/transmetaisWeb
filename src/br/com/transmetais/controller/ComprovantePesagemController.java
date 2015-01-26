package br.com.transmetais.controller;

import java.io.File;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;

import br.com.caelum.vraptor.Path;
import br.com.caelum.vraptor.Post;
import br.com.caelum.vraptor.interceptor.multipart.UploadedFile;
import br.com.transmetais.bean.Arquivo;
import br.com.transmetais.bean.ComprovantePesagem;
import br.com.transmetais.dao.ComprovantePesagemDAO;
import br.com.transmetais.dao.commons.DAOException;
import br.com.transmetais.dao.impl.ArquivoDaoImpl;
import br.com.transmetais.dao.impl.TipoVeiculoDaoImpl;
import br.com.transmetais.type.TipoFreteEnum;
import br.com.transmetais.util.EntityUtil;
import br.com.transmetais.util.FileUtil;

public abstract class ComprovantePesagemController<T extends ComprovantePesagem> extends BaseController<T, ComprovantePesagemDAO<T>> {
	private TipoVeiculoDaoImpl tipoVeiculoDaoImpl;
	
	
	protected void initForm(ComprovantePesagem bean)  {
		if(bean.getId() == null || bean.getId() == 0){
			bean.setDataEmissao(new Date());
			bean.setPercentualImpureza(1d);
		}
		try {
			result.include("tiposVeiculo", EntityUtil.retrieveCombo(tipoVeiculoDaoImpl.findAll(), "id", "descricao"));
		} catch (DAOException e) {
		}
		result.include("tiposFrete",TipoFreteEnum.values());
	}
	@Override
	protected void initFilter(T filter) {
		super.initFilter(filter);
	}
	@Post
	public void gravar(T bean, UploadedFile arquivo) {
		
		
		String extensao = null;
		if (arquivo != null){
			
		    String nomeArquivo = arquivo.getFileName();
		    
		    extensao = nomeArquivo.substring(nomeArquivo.lastIndexOf("."), nomeArquivo.length());
		    
			//String extensaoArquivo = arquivo.getFileName().split(".")[1];
			Arquivo arq = new Arquivo( nomeArquivo, extensao);
			bean.setArquivo(arq);
			
		}
		
		super.add(bean);
		
		if (arquivo != null)
			FileUtil.addFile(this.getClass().getAnnotation(Path.class).value()[0], bean.getId().toString() + extensao, arquivo.getFile());
		
	}
	
	
	public File downloadArquivo(String id) {
		  File file = new File(FileUtil.FOLDER_FILES_UPLOAD + "cpe/", id+".pdf");
		  return (file.exists()) ? file : new File(FileUtil.FOLDER_FILES_UPLOAD + "/default.jpg");
		}
	
	@Autowired
	public void setTipoVeiculoDaoImpl(TipoVeiculoDaoImpl tipoVeiculoDaoImpl) {
		this.tipoVeiculoDaoImpl = tipoVeiculoDaoImpl;
	}
	
	
}
