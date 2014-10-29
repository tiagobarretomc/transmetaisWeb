CREATE TABLE `transmetais`.`contaapagar_parcela` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `parcela_id` INT NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_contaapagar_parcela_parcela_idx` (`parcela_id` ASC),
  CONSTRAINT `fk_contaapagar_parcela_parcela`
    FOREIGN KEY (`parcela_id`)
    REFERENCES `transmetais`.`parcela` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION);