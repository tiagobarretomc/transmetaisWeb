CREATE TABLE `parcela` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `despesa_id` bigint(20) NOT NULL,
  `data_vencimento` datetime NOT NULL,
  `numero_cheque` varchar(45) COLLATE utf8_unicode_ci DEFAULT NULL,
  `valor` decimal(9,3) NOT NULL,
  `status` varchar(1) COLLATE utf8_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_parcela_despesa_idx` (`despesa_id`),
  CONSTRAINT `fk_parcela_despesa` FOREIGN KEY (`despesa_id`) REFERENCES `despesa` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=22 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

