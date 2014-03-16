CREATE TABLE `centro_custo` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `numero` varchar(6) COLLATE utf8_unicode_ci NOT NULL,
  `descricao` varchar(45) COLLATE utf8_unicode_ci NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `numero_UNIQUE` (`numero`),
  UNIQUE KEY `descricao_UNIQUE` (`descricao`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

CREATE TABLE `centro_aplicacao` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `numero` varchar(6) COLLATE utf8_unicode_ci NOT NULL,
  `centro_custo_id` int(11) NOT NULL,
  `descricao` varchar(100) COLLATE utf8_unicode_ci NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `numero_UNIQUE` (`numero`),
  KEY `fk_centro_custo_centro_aplicacao_idx` (`centro_custo_id`),
  CONSTRAINT `fk_centro_custo_centro_aplicacao` FOREIGN KEY (`centro_custo_id`) REFERENCES `centro_custo` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;


ALTER TABLE `transmetais`.`fornecedor` 
ADD COLUMN `inscricao_estadual` VARCHAR(20) NULL AFTER `cep`;

ALTER TABLE `transmetais`.`conta` 
ADD COLUMN `Banco` VARCHAR(100) NULL AFTER `fornecedor_id`,
ADD COLUMN `agencia` VARCHAR(6) NULL AFTER `Banco`,
ADD COLUMN `conta_corrente` VARCHAR(18) NULL AFTER `agencia`;
