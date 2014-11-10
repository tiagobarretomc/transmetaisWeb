CREATE TABLE `contaapagar` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `descricao` varchar(255) COLLATE utf8_unicode_ci NOT NULL,
  `data_prevista` datetime DEFAULT NULL,
  `valor` decimal(12,3) NOT NULL,
  `status` varchar(1) COLLATE utf8_unicode_ci NOT NULL,
  `data_pagamento` datetime DEFAULT NULL,
  `conta_id` int(11) DEFAULT NULL,
  `juros` decimal(9,3) DEFAULT NULL,
  `valor_total` decimal(12,3) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `contasapagar_conta_idx` (`conta_id`),
  CONSTRAINT `contasapagar_conta` FOREIGN KEY (`conta_id`) REFERENCES `conta` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=36 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;
