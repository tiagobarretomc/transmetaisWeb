CREATE TABLE `cheque_emitido` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `conta_id` int(11) NOT NULL,
  `data` datetime NOT NULL,
  `valor` decimal(9,3) NOT NULL,
  `numero_cheque` varchar(15) COLLATE utf8_unicode_ci NOT NULL,
  `status` varchar(1) COLLATE utf8_unicode_ci DEFAULT 'A',
  `data_status` datetime DEFAULT NULL,
  `motivo_cancelamento` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_cheque_emitido_conta_idx` (`conta_id`),
  CONSTRAINT `fk_cheque_emitido_conta` FOREIGN KEY (`conta_id`) REFERENCES `conta_bancaria` (`conta_id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;
