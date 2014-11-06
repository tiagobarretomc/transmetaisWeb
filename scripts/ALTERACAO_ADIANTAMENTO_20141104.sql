ALTER TABLE `transmetais`.`adiantamento` 
ADD COLUMN `conta_id` INT(11) NULL AFTER `data`,
ADD INDEX `FK_ADIANTAMENTO_CONTA_idx` (`conta_id` ASC);
ALTER TABLE `transmetais`.`adiantamento` 
ADD CONSTRAINT `FK_ADIANTAMENTO_CONTA`
  FOREIGN KEY (`conta_id`)
  REFERENCES `transmetais`.`conta` (`id`)
  ON DELETE NO ACTION
  ON UPDATE NO ACTION;