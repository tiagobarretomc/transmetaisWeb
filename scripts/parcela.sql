CREATE TABLE `transmetais`.`parcela` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `despesa_id` BIGINT(20) NOT NULL,
  `data_vencimento` DATETIME NOT NULL,
  `numero_cheque` VARCHAR(45) NULL,
  `valor` DECIMAL(9,3) NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_parcela_despesa_idx` (`despesa_id` ASC),
  CONSTRAINT `fk_parcela_despesa`
    FOREIGN KEY (`despesa_id`)
    REFERENCES `transmetais`.`despesa` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION);