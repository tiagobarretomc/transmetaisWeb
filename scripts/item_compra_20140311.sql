CREATE TABLE `item_compra` (
  `id` int(11) NOT NULL,
  `material_id` int(11) NOT NULL,
  `valor` varchar(45) COLLATE utf8_unicode_ci NOT NULL,
  `quantidade` decimal(9,2) NOT NULL,
  `preco` decimal(5,3) NOT NULL,
  `compra_id` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_item_compra_material_idx` (`material_id`),
  KEY `fk_item_compra_compra_idx` (`compra_id`),
  CONSTRAINT `fk_item_compra_material` FOREIGN KEY (`material_id`) REFERENCES `material` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_item_compra_compra` FOREIGN KEY (`compra_id`) REFERENCES `compra` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;
