CREATE TABLE `transmetais`.`cheque_emitido_compra` (
  `cheque_emitido_id` INT NOT NULL,
  `compra_id` INT(11) NOT NULL,
  PRIMARY KEY (`cheque_emitido_id`),
  INDEX `fk_cheque_emitido_compra_compra_idx` (`compra_id` ASC),
  CONSTRAINT `fk_cheque_emitido_compra_cheque_emitido`
    FOREIGN KEY (`cheque_emitido_id`)
    REFERENCES `transmetais`.`cheque_emitido` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_cheque_emitido_compra_compra`
    FOREIGN KEY (`compra_id`)
    REFERENCES `transmetais`.`compra` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION);