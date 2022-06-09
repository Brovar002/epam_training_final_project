USE track_db;

CREATE TABLE IF NOT EXISTS `track_db`.`user` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `login` VARCHAR(20) NOT NULL,
  `password` CHAR(64) NOT NULL,
  `email` VARCHAR(45) NOT NULL,
  `cash_account` INT DEFAULT '0' NOT NULL,
  `discount` INT DEFAULT '0' NOT NULL,
  `role` TINYINT DEFAULT '0' NOT NULL,
  `order_count` BIGINT DEFAULT '0' NOT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;

CREATE TABLE IF NOT EXISTS `track_db`.`audio_track` (
  `id` BIGINT NOT NULL AUTO_INCREMENT,
  `album_id` BIGINT NULL DEFAULT NULL,
  `name` VARCHAR(100) NOT NULL,
  `artist_name` VARCHAR(100) NOT NULL,
  `genre_id` VARCHAR(100) NOT NULL,
  `price` DECIMAL NOT NULL,
  `path` VARCHAR(1000) NOT NULL,
  `deleted` BOOLEAN DEFAULT(0) NOT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;

CREATE TABLE IF NOT EXISTS `track_db`.`order` (
  `id` BIGINT NOT NULL AUTO_INCREMENT,
  `audio_track_id` BIGINT NOT NULL,
  `user_id` INT NOT NULL,
  `price` DECIMAL NOT NULL,
  `date` VARCHAR(100) NOT NULL,
  INDEX `fk_Order_User1_idx` (`user_id` ASC),
  INDEX `fk_Order_AudioTrack1_idx` (`audio_track_id` ASC),
  PRIMARY KEY (`id`,`user_id`, `audio_track_id`),
 CONSTRAINT `Order_User1`
    FOREIGN KEY (`user_id`)
    REFERENCES `track_db`.`user` (`id`)
    ON DELETE RESTRICT
    ON UPDATE RESTRICT,
    CONSTRAINT `Order_AudioTrack1`
    FOREIGN KEY (`audio_track_id`)
    REFERENCES `track_db`.`audio_track` (`id`)
    ON DELETE RESTRICT
    ON UPDATE RESTRICT)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;

CREATE TABLE IF NOT EXISTS `track_db`.`genre` (
  `id` BIGINT NOT NULL AUTO_INCREMENT,
  `genre` VARCHAR(100) NOT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;

CREATE TABLE IF NOT EXISTS `track_db`.`comment` (
    `id` BIGINT NOT NULL AUTO_INCREMENT,
  `audio_track_id` BIGINT NOT NULL,
  `user_id` INT NOT NULL,
  `user_login` VARCHAR(20) NOT NULL,
  `text` VARCHAR(1000) NOT NULL,
  `date` VARCHAR(100) NOT NULL,
  INDEX `Comment_User1_idx` (`user_id` ASC),
  INDEX `Comment_AudioTrack1_idx` (`audio_track_id` ASC),
  PRIMARY KEY (`id`,`user_id`, `audio_track_id`),
 CONSTRAINT `fk_Comment_User1`
    FOREIGN KEY (`user_id`)
    REFERENCES `track_db`.`user` (`id`)
    ON DELETE RESTRICT
    ON UPDATE RESTRICT,
    CONSTRAINT `fk_Comment_AudioTrack1`
    FOREIGN KEY (`audio_track_id`)
    REFERENCES `track_db`.`audio_track` (`id`)
    ON DELETE RESTRICT
    ON UPDATE RESTRICT)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;