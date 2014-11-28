package br.com.transmetais.controller;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.Date;

import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;

import br.com.caelum.vraptor.Post;
import br.com.caelum.vraptor.interceptor.multipart.UploadedFile;
import br.com.transmetais.bean.ComprovantePesagem;
import br.com.transmetais.dao.ComprovantePesagemDAO;
import br.com.transmetais.dao.commons.DAOException;
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
		super.add(bean);
	}
	private void adicionarArquivo(UploadedFile arquivo){
		String pathFile = FileUtil.FOLDER_FILES_UPLOAD;
		String fileName = null;
		try {
			 InputStream imagem = null;
			 
			 if (!(new File(pathFile)).exists()){
				 (new File(pathFile)).mkdirs();
			 }
			 pathFile = pathFile + "/";
			 fileName = arquivo.getFileName();
			 
			 FileUtil.writeUploadedResource(fileName, arquivo.getFile(),pathFile);
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	@Autowired
	public void setTipoVeiculoDaoImpl(TipoVeiculoDaoImpl tipoVeiculoDaoImpl) {
		this.tipoVeiculoDaoImpl = tipoVeiculoDaoImpl;
	}
	
}
