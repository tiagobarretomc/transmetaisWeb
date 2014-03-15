CREATE TABLE `centro_custo` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `numero` varchar(6) COLLATE utf8_unicode_ci NOT NULL,
  `descricao` varchar(45) COLLATE utf8_unicode_ci NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `numero_UNIQUE` (`numero`),
  UNIQUE KEY `descricao_UNIQUE` (`descricao`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;
