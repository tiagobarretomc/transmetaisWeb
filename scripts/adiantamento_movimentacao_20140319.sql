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


ALTER TABLE `transmetais`.`adiantamento` 
ADD COLUMN `data` DATETIME NULL AFTER `data_pagamento`;

ALTER TABLE `transmetais`.`adiantamento` 
DROP FOREIGN KEY `fk_adiantamento_movimentacao`;
ALTER TABLE `transmetais`.`adiantamento` 
DROP COLUMN `movimentacao_id`,
CHANGE COLUMN `tipo_pagamento` `tipo_pagamento` VARCHAR(1) CHARACTER SET 'utf8' COLLATE 'utf8_unicode_ci' NULL ,
CHANGE COLUMN `data` `data` DATETIME NOT NULL ,
DROP INDEX `fk_adiantamento_movimentacao_idx` ;



ALTER TABLE `transmetais`.`movimentacao_adiantamento` 
DROP FOREIGN KEY `fk_movimentacao_adiantamento_adiantamento`;


ALTER TABLE `transmetais`.`adiantamento` 
CHANGE COLUMN `id` `id` BIGINT(20) NOT NULL AUTO_INCREMENT ;


ALTER TABLE `transmetais`.`movimentacao_adiantamento` 
ADD CONSTRAINT `fk_movimento_adiantamento_adiantamento`
  FOREIGN KEY (`adiantamento_id`)
  REFERENCES `transmetais`.`adiantamento` (`id`)
  ON DELETE NO ACTION
  ON UPDATE NO ACTION;





