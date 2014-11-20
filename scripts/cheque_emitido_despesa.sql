CREATE TABLE `transmetais`.`cheque_emitido_despesa` (
  `cheque_emitido_id` INT NOT NULL,
  `despesa_id` BIGINT(20) NOT NULL,
  PRIMARY KEY (`cheque_emitido_id`),
  INDEX `fk_cheque_emitido_despesa_despesa_idx` (`despesa_id` ASC),
  CONSTRAINT `fk_cheque_emitido_despesa_cheque_emitido`
    FOREIGN KEY (`cheque_emitido_id`)
    REFERENCES `transmetais`.`cheque_emitido` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_cheque_emitido_despesa_despesa`
    FOREIGN KEY (`despesa_id`)
    REFERENCES `transmetais`.`despesa` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION);