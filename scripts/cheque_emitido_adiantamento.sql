CREATE TABLE `transmetais`.`cheque_emitido_adiantamento` (
  `cheque_emitido_id` INT NOT NULL,
  `adiantamento_id` BIGINT(20) NOT NULL,
  PRIMARY KEY (`cheque_emitido_id`),
  INDEX `fk_chque_emitido_adiantamento_adiantamento_idx` (`adiantamento_id` ASC),
  CONSTRAINT `fk_chque_emitido_adiantamento_chque_emitido`
    FOREIGN KEY (`cheque_emitido_id`)
    REFERENCES `transmetais`.`cheque_emitido` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_chque_emitido_adiantamento_adiantamento`
    FOREIGN KEY (`adiantamento_id`)
    REFERENCES `transmetais`.`adiantamento` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION);
