package br.com.transmetais.dao.impl;

import br.com.caelum.vraptor.ioc.Component;
import br.com.transmetais.bean.GrupoMaterial;
import br.com.transmetais.dao.GrupoMaterialDAO;
import br.com.transmetais.dao.commons.CrudDAOJPA;

@Component
public class GrupoMaterialDaoImpl extends CrudDAOJPA<GrupoMaterial> implements GrupoMaterialDAO{

}
