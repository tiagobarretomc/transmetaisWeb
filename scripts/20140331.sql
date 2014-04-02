ALTER TABLE `transmetais`.`movimentacao` 
DROP FOREIGN KEY `fk_movimentacao_venda`,
DROP FOREIGN KEY `fk_movimentacao_compra`;
ALTER TABLE `transmetais`.`movimentacao` 
DROP COLUMN `descricao`,
DROP COLUMN `status`,
DROP COLUMN `data_pagamento`,
DROP COLUMN `valor_previsto`,
DROP COLUMN `consolidado`,
DROP COLUMN `compra_id`,
DROP COLUMN `venda_id`,
DROP INDEX `fk_movimentacao_venda_idx` ,
DROP INDEX `fk_mevimentacao_compra_idx` ;


CREATE TABLE `contaapagar` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `descricao` varchar(255) COLLATE utf8_unicode_ci NOT NULL,
  `data_prevista` datetime NOT NULL,
  `valor` varchar(45) COLLATE utf8_unicode_ci NOT NULL,
  `status` varchar(1) COLLATE utf8_unicode_ci NOT NULL,
  `data_pagamento` datetime DEFAULT NULL,
  `conta_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `contasapagar_conta_idx` (`conta_id`),
  CONSTRAINT `contasapagar_conta` FOREIGN KEY (`conta_id`) REFERENCES `conta` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;


  CREATE TABLE `contaapagar_adiantamento` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `contaapagar_id` int(11) NOT NULL,
  `adiantamento_id` bigint(20) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `contasapagar_adiantamento_adiantamento_idx` (`adiantamento_id`),
  KEY `contasapagar_adiantamento_contasapagar_idx` (`contaapagar_id`),
  CONSTRAINT `contasapagar_adiantamento_contasapagar` FOREIGN KEY (`contaapagar_id`) REFERENCES `contaapagar` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `contasapagar_adiantamento_adiantamento` FOREIGN KEY (`adiantamento_id`) REFERENCES `adiantamento` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;


    
    CREATE TABLE `contaapagar_compra` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `contaapagar_id` int(11) NOT NULL,
  `compra_id` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `contasapagar_compra_compra_idx` (`compra_id`),
  KEY `contasapagar_compra_contasapagar_idx` (`contaapagar_id`),
  CONSTRAINT `contasapagar_compra_contasapagar` FOREIGN KEY (`contaapagar_id`) REFERENCES `contaapagar` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `contasapagar_compra_compra` FOREIGN KEY (`compra_id`) REFERENCES `compra` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;


    
    