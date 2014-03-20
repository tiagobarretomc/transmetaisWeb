CREATE TABLE `transmetais`.`movimentacao_adiantamento` (
  `movimentacao_id` BIGINT(20) NOT NULL,
  `adiantamento_id` BIGINT(20) NOT NULL,
  PRIMARY KEY (`movimentacao_id`, `adiantamento_id`),
  INDEX `fk_movimentacao_adiantamento_adiantamento_idx` (`adiantamento_id` ASC),
  CONSTRAINT `fk_movimentacao_adiantamento_movimentacao`
    FOREIGN KEY (`movimentacao_id`)
    REFERENCES `transmetais`.`movimentacao` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_movimentacao_adiantamento_adiantamento`
    FOREIGN KEY (`adiantamento_id`)
    REFERENCES `transmetais`.`adiantamento` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION);
    
    
    
    CREATE TABLE `transmetais`.`movimentacao_compra` (
  `movientacao_id` BIGINT(20) NOT NULL,
  `compra_id` INT NOT NULL,
  PRIMARY KEY (`movientacao_id`, `compra_id`),
  INDEX `fk_movimentacao_compra_compra_idx` (`compra_id` ASC),
  CONSTRAINT `fk_movimentacao_compra_movimentacao`
    FOREIGN KEY (`movientacao_id`)
    REFERENCES `transmetais`.`movimentacao` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_movimentacao_compra_compra`
    FOREIGN KEY (`compra_id`)
    REFERENCES `transmetais`.`compra` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION);
    
    ALTER TABLE `transmetais`.`movimentacao` 
CHANGE COLUMN `valor` `valor` DECIMAL(18,2) NOT NULL ,
ADD COLUMN `valor_previsto` DECIMAL(18,2) NULL AFTER `consolidado`,
ADD COLUMN `data_pagamento` DATETIME NULL AFTER `valor_previsto`,
ADD COLUMN `situacao` VARCHAR(1) NULL AFTER `data_pagamento`;




