ALTER TABLE `transmetais`.`compra` 
DROP FOREIGN KEY `fk_compra_conta`;
ALTER TABLE `transmetais`.`compra` 
CHANGE COLUMN `conta_id` `conta_id` INT(11) NULL ;
ALTER TABLE `transmetais`.`compra` 
ADD CONSTRAINT `fk_compra_conta`
  FOREIGN KEY (`conta_id`)
  REFERENCES `transmetais`.`conta` (`id`)
  ON DELETE NO ACTION
  ON UPDATE NO ACTION;

  
  ALTER TABLE `transmetais`.`compra` 
DROP FOREIGN KEY `fk_compra_fornecedor_material`;
ALTER TABLE `transmetais`.`compra` 
CHANGE COLUMN `fornecedor_material_id` `fornecedor_material_id` INT(11) NULL ,
ADD COLUMN `fornecedor_id` BIGINT(20) NULL AFTER `num_nf`;
ALTER TABLE `transmetais`.`compra` 
ADD CONSTRAINT `fk_compra_fornecedor_material`
  FOREIGN KEY (`fornecedor_material_id`)
  REFERENCES `transmetais`.`fornecedor_material` (`id`)
  ON DELETE NO ACTION
  ON UPDATE NO ACTION;

  
  ALTER TABLE `transmetais`.`compra` 
ADD INDEX `fk_compra_fornecedor_idx1` (`fornecedor_id` ASC);
ALTER TABLE `transmetais`.`compra` 
ADD CONSTRAINT `fk_compra_fornecedor`
  FOREIGN KEY (`fornecedor_id`)
  REFERENCES `transmetais`.`fornecedor` (`id`)
  ON DELETE NO ACTION
  ON UPDATE NO ACTION;
  
  ALTER TABLE `transmetais`.`compra` 
CHANGE COLUMN `quantidade` `quantidade` VARCHAR(45) CHARACTER SET 'latin1' NULL ,
CHANGE COLUMN `preco` `preco` DECIMAL(5,3) NULL ;

ALTER TABLE `transmetais`.`item_compra` 
CHANGE COLUMN `id` `id` INT(11) NOT NULL AUTO_INCREMENT ;

ALTER TABLE `transmetais`.`item_compra` 
DROP FOREIGN KEY `fk_item_compra_compra`;
ALTER TABLE `transmetais`.`item_compra` 
CHANGE COLUMN `compra_id` `compra_id` INT(11) NULL ;
ALTER TABLE `transmetais`.`item_compra` 
ADD CONSTRAINT `fk_item_compra_compra`
  FOREIGN KEY (`compra_id`)
  REFERENCES `transmetais`.`compra` (`id`)
  ON DELETE NO ACTION
  ON UPDATE NO ACTION;
  

ALTER TABLE `transmetais`.`compra` 
ADD COLUMN `tipo_frete` VARCHAR(4) NULL AFTER `fornecedor_id`;


insert into item_compra(
    `material_id`,
    `valor`,
    `quantidade`,
    `preco`,
    `compra_id`)
select fm.material_id, c.valor,c.quantidade,fm.valor, c.id from  compra c 
inner join fornecedor_material fm on c.fornecedor_material_id = fm.id

update compra c, fornecedor_material fm 
set c.fornecedor_id = fm.fornecedor_id, c.tipo_frete = fm.tipo_frete
where c.fornecedor_material_id = fm.id;

ALTER TABLE `transmetais`.`compra` 
ADD COLUMN `status` VARCHAR(1) NULL DEFAULT NULL AFTER `tipo_frete`;


ALTER TABLE `transmetais`.`movimentacao` 
DROP FOREIGN KEY `fk_movimentacao_conta`;
ALTER TABLE `transmetais`.`movimentacao` 
CHANGE COLUMN `conta_id` `conta_id` INT(11) NULL ;
ALTER TABLE `transmetais`.`movimentacao` 
ADD CONSTRAINT `fk_movimentacao_conta`
  FOREIGN KEY (`conta_id`)
  REFERENCES `transmetais`.`conta` (`id`)
  ON DELETE NO ACTION
  ON UPDATE NO ACTION;
  
  
  ALTER TABLE `transmetais`.`movimentacao_compra` 
DROP FOREIGN KEY `fk_movimentacao_compra_movimentacao`;
ALTER TABLE `transmetais`.`movimentacao_compra` 
CHANGE COLUMN `movientacao_id` `movimentacao_id` BIGINT(20) NOT NULL ;
ALTER TABLE `transmetais`.`movimentacao_compra` 
ADD CONSTRAINT `fk_movimentacao_compra_movimentacao`
  FOREIGN KEY (`movimentacao_id`)
  REFERENCES `transmetais`.`movimentacao` (`id`)
  ON DELETE NO ACTION
  ON UPDATE NO ACTION;
  
  
  ALTER TABLE `transmetais`.`movimentacao` 
CHANGE COLUMN `situacao` `status` VARCHAR(1) CHARACTER SET 'utf8' COLLATE 'utf8_unicode_ci' NULL DEFAULT NULL ;


