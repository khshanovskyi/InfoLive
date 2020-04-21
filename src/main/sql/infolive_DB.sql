-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema infolive
-- -----------------------------------------------------
DROP SCHEMA IF EXISTS `infolive` ;

-- -----------------------------------------------------
-- Schema infolive
-- -----------------------------------------------------
CREATE SCHEMA `infolive` DEFAULT CHARACTER SET utf8 ;
USE `infolive` ;

-- -----------------------------------------------------
-- Table `infolive`.`user`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `infolive`.`users` (
  `user_id` INT NOT NULL AUTO_INCREMENT,
  `email` VARCHAR(45) NOT NULL UNIQUE,
  `surname` VARCHAR(20) NOT NULL,
  `name` VARCHAR(20) NOT NULL,
  `patronymic` VARCHAR(20) NOT NULL,
  `phone_number` INT(15) NOT NULL,
  `region` VARCHAR(30) NOT NULL,
  `district` VARCHAR(30) NULL,
  `locality` VARCHAR(30) NOT NULL,
  `street` VARCHAR(30) NOT NULL,
  `house` VARCHAR(5) NOT NULL,
  `flat` INT(3) NULL,
  `postcode` INT(6) NOT NULL,
  `money` INT(10) NOT NULL,
  `password` VARCHAR(25) NOT NULL,
  `role` VARCHAR(5) NOT NULL,
  `state` VARCHAR(10) NOT NULL,
  PRIMARY KEY (`user_id`))
  ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;

-- -----------------------------------------------------
-- Table `infolive`.`media`
-- -----------------------------------------------------
CREATE TABLE `infolive`.`media` (
  `id_media` INT NOT NULL AUTO_INCREMENT,
  `media_name` VARCHAR(45) NOT NULL,
  `topic` VARCHAR(150) NOT NULL,
  `description` VARCHAR(500) NOT NULL,
  `uri_logo_img` VARCHAR(45) NOT NULL,
  `pdf_uri` VARCHAR(45) NOT NULL,
  `subscribers` INT(10) NOT NULL,
  `price` INT(5) NOT NULL,
  `publication_in_month` INT(2) NOT NULL,
  `update_date` DATE NOT NULL,
  `update_time` TIME NOT NULL,
  PRIMARY KEY (`id_media`))
  ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;

-- -----------------------------------------------------
-- Table `infolive`.`subscription`
-- -----------------------------------------------------
CREATE TABLE `infolive`.`subscription` (
  `id_subscription` INT NOT NULL AUTO_INCREMENT,
  `user_id` INT NOT NULL,
  `id_media` INT NOT NULL,
  `date_from` DATE NOT NULL,
  `date_to` DATE NOT NULL,
  PRIMARY KEY (`id_subscription`),
  INDEX `user_id_foreign_idx` (`user_id` ASC),
  INDEX `media_id_foreign_idx` (`id_media` ASC),
  CONSTRAINT `user_id_foreign`
    FOREIGN KEY (`user_id`)
    REFERENCES `infolive`.`user` (`user_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `media_id_foreign`
    FOREIGN KEY (`id_media`)
    REFERENCES `infolive`.`media` (`id_media`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;
