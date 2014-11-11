CREATE TABLE `despesa` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `descricao` varchar(255) COLLATE utf8_unicode_ci NOT NULL,
  `valor` varchar(45) COLLATE utf8_unicode_ci NOT NULL,
  `data_competencia` datetime NOT NULL,
  `data_vencimento` datetime NOT NULL,
  `data_pagamento` datetime DEFAULT NULL,
  `status` varchar(1) COLLATE utf8_unicode_ci NOT NULL,
  `centro_aplicacao_id` int(11) NOT NULL,
  `conta_contabil_id` int(11) NOT NULL,
  `forma_pagamento` varchar(1) COLLATE utf8_unicode_ci DEFAULT NULL,
  `modalidade_pagamento` varchar(1) COLLATE utf8_unicode_ci DEFAULT NULL,
  `conta_id` int(11) DEFAULT NULL,
  `fornecedor_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_despesa_centro_aplicacao_id_idx` (`centro_aplicacao_id`),
  KEY `fk_despesa_conta_contabil_idx` (`conta_contabil_id`),
  KEY `fk_despesa_conta_idx` (`conta_id`),
  KEY `fk_despesa_conta_contabil_idx1` (`fornecedor_id`),
  CONSTRAINT `fk_despesa_conta_contabil` FOREIGN KEY (`fornecedor_id`) REFERENCES `fornecedor` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_despesa_centro_aplicacao_id` FOREIGN KEY (`centro_aplicacao_id`) REFERENCES `centro_aplicacao` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_despesa_conta` FOREIGN KEY (`conta_id`) REFERENCES `conta` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=17 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;
	