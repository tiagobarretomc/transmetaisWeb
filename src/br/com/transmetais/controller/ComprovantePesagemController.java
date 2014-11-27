package br.com.transmetais.controller;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;

import br.com.caelum.vraptor.Post;
import br.com.caelum.vraptor.Result;
import br.com.caelum.vraptor.interceptor.multipart.UploadedFile;
import br.com.transmetais.bean.ComprovantePesagem;
import br.com.transmetais.dao.ComprovantePesagemDAO;
import br.com.transmetais.dao.commons.DAOException;
import br.com.transmetais.dao.impl.TipoVeiculoDaoImpl;
import br.com.transmetais.type.TipoFreteEnum;
import br.com.transmetais.util.EntityUtil;

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
		// TODO Auto-generated method stub
		super.add(bean);
	}
	@Autowired
	public void setTipoVeiculoDaoImpl(TipoVeiculoDaoImpl tipoVeiculoDaoImpl) {
		this.tipoVeiculoDaoImpl = tipoVeiculoDaoImpl;
	}
	
}
