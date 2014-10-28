ALTER TABLE `transmetais`.`contaapagar_despesa` 
ADD COLUMN `parcela_id` INT(11) NULL AFTER `despesa_id`,
ADD INDEX `fk_contaapagar_despesa_parcela_idx` (`parcela_id` ASC);
ALTER TABLE `transmetais`.`contaapagar_despesa` 
ADD CONSTRAINT `fk_contaapagar_despesa_parcela`
  FOREIGN KEY (`parcela_id`)
  REFERENCES `transmetais`.`parcela` (`id`)
  ON DELETE NO ACTION
  ON UPDATE NO ACTION;
