/***
 * Copyright (c) 2009 Caelum - www.caelum.com.br/opensource
 * All rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * 	http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package br.com.transmetais.controller;



import java.util.List;

import br.com.caelum.vraptor.Path;
import br.com.caelum.vraptor.Resource;
import br.com.caelum.vraptor.Result;
import br.com.transmetais.bean.Fornecedor;
import br.com.transmetais.dao.FornecedorDAO;
import br.com.transmetais.dao.commons.DAOException;
import br.com.transmetais.dao.impl.FornecedorDaoImpl;

@Resource
public class IndexController {

	private final Result result;
	
	private FornecedorDaoImpl dao;

	public IndexController(Result result, FornecedorDaoImpl dao) {
		this.result = result;
		this.dao = dao;
	}

	@Path("/")
	public void index() {
		result.include("variable", "VRaptor!");
		
		try {
			List<Fornecedor> lista = dao.findAll();
			//System.out.println();
			for (Fornecedor fornecedor : lista) {
				System.out.println(fornecedor.getNome());
			}
			
			result.include("fornecedores",lista);
		} catch (DAOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}

}
