CREATE TABLE `console_task_list`.`tasks` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(45) NULL,
  `date` DATE NOT NULL,
  `priority` INT NULL,
  `statusDone`  SMALLINT(1) NOT NULL DEFAULT 0 ,

  PRIMARY KEY (`id`));
