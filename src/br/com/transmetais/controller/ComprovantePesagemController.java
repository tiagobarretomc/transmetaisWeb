package br.com.transmetais.controller;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
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
	private ArquivoDaoImpl arquivoDaoImpl;
	
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
	public void gravar(T bean, UploadedFile uFile) {
		String nomeArquivo = uFile.getFileName().split(".")[0];
		String extensaoArquivo = uFile.getFileName().split(".")[1];
		Arquivo arquivo = new Arquivo(nomeArquivo, extensaoArquivo);
		bean.setArquivo(arquivo);
		super.add(bean);
		FileUtil.addFile(this.getClass().getAnnotation(Path.class).value()[0], arquivo.getId().toString(), uFile.getFile());
	}
	
	@Autowired
	public void setTipoVeiculoDaoImpl(TipoVeiculoDaoImpl tipoVeiculoDaoImpl) {
		this.tipoVeiculoDaoImpl = tipoVeiculoDaoImpl;
	}
	@Autowired
	public void setArquivoDaoImpl(ArquivoDaoImpl arquivoDaoImpl) {
		this.arquivoDaoImpl = arquivoDaoImpl;
	}
	
}
