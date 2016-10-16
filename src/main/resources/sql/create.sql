CREATE TABLE `console_task_list`.`tasks` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(45) NULL,
  `date` DATE NOT NULL,
  `priority` INT NULL,
  `statusDone`  SMALLINT(1) NOT NULL DEFAULT 0 ,

  PRIMARY KEY (`id`));

CREATE TABLE `sql7140215`.`tasks_archive` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(45) NOT NULL,
  `date` VARCHAR(45) NOT NULL,
  `priority` INT NOT NULL,
  `statusDone` SMALLINT(1) NOT NULL,
  `archivedOn` TIMESTAMP NOT NULL,
  `originalId` INT NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `id_UNIQUE` (`id` ASC));
