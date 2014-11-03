ALTER TABLE `transmetais`.`contaapagar_despesa` 
DROP FOREIGN KEY `fk_ contaapagar_despesa_contaapagar`,
DROP FOREIGN KEY `fk_ contaapagar_despesa_despesa`;
ALTER TABLE `transmetais`.`contaapagar_despesa` 
CHANGE COLUMN `contaapagar_id` `contaapagar_id` INT(11) NOT NULL ,
CHANGE COLUMN `despesa_id` `despesa_id` BIGINT(20) NOT NULL ,
ADD COLUMN `parcela_id` INT(11) NULL AFTER `despesa_id`,
ADD INDEX `fk_contaapagar_despesa_parcela_idx` (`parcela_id` ASC);
ALTER TABLE `transmetais`.`contaapagar_despesa` 
ADD CONSTRAINT `fk_ contaapagar_despesa_contaapagar`
  FOREIGN KEY (`contaapagar_id`)
  REFERENCES `transmetais`.`contaapagar` (`id`)
  ON DELETE NO ACTION
  ON UPDATE NO ACTION,
ADD CONSTRAINT `fk_ contaapagar_despesa_despesa`
  FOREIGN KEY (`despesa_id`)
  REFERENCES `transmetais`.`despesa` (`id`)
  ON DELETE NO ACTION
  ON UPDATE NO ACTION,
ADD CONSTRAINT `fk_contaapagar_despesa_parcela`
  FOREIGN KEY (`parcela_id`)
  REFERENCES `transmetais`.`parcela` (`id`)
  ON DELETE NO ACTION
  ON UPDATE NO ACTION;
