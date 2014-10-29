CREATE TABLE `transmetais`.`movimentacao_despesa` (
  `movimentacao_id` BIGINT(20) NOT NULL,
  `despesa_id` BIGINT(20) NOT NULL,
  PRIMARY KEY (`movimentacao_id`, `despesa_id`),
  INDEX `fk_movimentacao_despesa_despesa_idx` (`despesa_id` ASC),
  CONSTRAINT `fk_movimentacao_despesa_despesa`
    FOREIGN KEY (`despesa_id`)
    REFERENCES `transmetais`.`despesa` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_movimentacao_despesa_movimentacao`
    FOREIGN KEY (`movimentacao_id`)
    REFERENCES `transmetais`.`movimentacao` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION);
