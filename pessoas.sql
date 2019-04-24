CREATE SCHEMA `banco` DEFAULT CHARACTER SET latin1 ;

CREATE TABLE `banco`.`pessoas` (
  `id_pessoa` INT NOT NULL,
  `nome_pessoa` VARCHAR(45) NULL,
  `cpf` VARCHAR(45) NULL,
  `email` VARCHAR(45) NULL,
  PRIMARY KEY (`id_pessoa`));
