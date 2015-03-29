ALTER TABLE `transmetais`.`usuario_grupo` 
ADD COLUMN `id` INT(11) NOT NULL AUTO_INCREMENT AFTER `id_grupo`,
DROP PRIMARY KEY,
ADD PRIMARY KEY (`id`),
ADD UNIQUE INDEX `id_UNIQUE` (`id` ASC);

ALTER TABLE `transmetais`.`usuario_grupo` 
CHANGE COLUMN `id_grupo` `grupo` VARCHAR(15) NOT NULL ;
