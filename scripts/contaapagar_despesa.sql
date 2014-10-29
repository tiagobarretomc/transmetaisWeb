ALTER TABLE `transmetais`.`contaapagar_despesa` 
DROP FOREIGN KEY `fk_contaapagar_despesa_parcela`;
ALTER TABLE `transmetais`.`contaapagar_despesa` 
DROP COLUMN `parcela_id`,
DROP INDEX `fk_contaapagar_despesa_parcela_idx` ;
