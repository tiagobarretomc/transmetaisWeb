CREATE TABLE `transmetais`.`tipo_veiculo` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `descricao` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`id`));

CREATE TABLE `comprovante_pesagem` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `dt_emissao` date NOT NULL,
  `nr_ticket` varchar(45) COLLATE utf8_unicode_ci DEFAULT NULL,
  `balanca` varchar(45) COLLATE utf8_unicode_ci DEFAULT NULL,
  `nm_transportador` varchar(45) COLLATE utf8_unicode_ci DEFAULT NULL,
  `placa_veiculo` varchar(45) COLLATE utf8_unicode_ci NOT NULL,
  `tipo_frete` varchar(45) COLLATE utf8_unicode_ci NOT NULL,
  `tipo_veiculo_id` int(11) NOT NULL,
  `tara_veiculo` decimal(10,5) NOT NULL,
  `peso_bruto_veiculo` decimal(10,5) NOT NULL,
  `peso_liq_veiculo` decimal(10,5) NOT NULL,
  `perc_impureza` decimal(10,5) NOT NULL,
  `peso_impureza` decimal(10,5) NOT NULL,
  `fornecedor_id` bigint(20) DEFAULT NULL,
  `cliente_id` bigint(20) DEFAULT NULL,
  `tipo` varchar(1) COLLATE utf8_unicode_ci NOT NULL,
  PRIMARY KEY (`id`),
  KEY `comprovante_pesagem_tp_veiculo_idx` (`tipo_veiculo_id`),
  KEY `comprovante_pesagem_cliente_idx` (`cliente_id`),
  CONSTRAINT `comprovante_pesagem_fornecedor` FOREIGN KEY (`tipo_veiculo_id`) REFERENCES `tipo_veiculo` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `comprovante_pesagem_cliente` FOREIGN KEY (`cliente_id`) REFERENCES `cliente` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `comprovante_pesagem_tp_veiculo` FOREIGN KEY (`tipo_veiculo_id`) REFERENCES `tipo_veiculo` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

 CREATE TABLE `item_pesagem` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `produto_id` int(11) DEFAULT NULL,
  `peso_perc` double NOT NULL,
  `peso_liquido` double NOT NULL,
  `comprov_pesagem_id` int(11) NOT NULL,
  `tipo` varchar(1) COLLATE utf8_unicode_ci NOT NULL,
  `material_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `item_pes_produto_idx` (`produto_id`),
  KEY `item_pesagem_comprovante_idx` (`comprov_pesagem_id`),
  KEY `item_pesagem_material_idx` (`material_id`),
  CONSTRAINT `item_pesagem_produto` FOREIGN KEY (`produto_id`) REFERENCES `produto` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `item_pesagem_material` FOREIGN KEY (`material_id`) REFERENCES `material` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `item_pesagem_comprovante` FOREIGN KEY (`comprov_pesagem_id`) REFERENCES `comprovante_pesagem` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;
