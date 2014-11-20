CREATE TABLE `transmetais`.`usuario` (
  `id` INT NOT NULL,
  `login` VARCHAR(45) NOT NULL ,
  `nome` VARCHAR(100) NOT NULL ,
  `senha` VARCHAR(100) NULL,
  `email` VARCHAR(100) NULL ,
  `senha_inicial` VARCHAR(100) NOT NULL ,
  `ativo` TINYINT(1) NOT NULL DEFAULT '1' ,
  PRIMARY KEY (`id`));

  CREATE TABLE `transmetais`.`usuario_grupo` (
  `id_usuario` INT NOT NULL,
  `id_grupo` INT NOT NULL,
  PRIMARY KEY (`id_usuario`, `id_grupo`));
