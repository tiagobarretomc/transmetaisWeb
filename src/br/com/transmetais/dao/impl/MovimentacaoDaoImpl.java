package br.com.transmetais.dao.impl;

import br.com.caelum.vraptor.ioc.Component;
import br.com.transmetais.bean.Movimentacao;
import br.com.transmetais.dao.MovimentacaoDAO;
import br.com.transmetais.dao.commons.CrudDAOJPA;

@Component
public class MovimentacaoDaoImpl extends CrudDAOJPA<Movimentacao> implements MovimentacaoDAO{

}
