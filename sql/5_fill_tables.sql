USE track_db;

INSERT INTO `user` (
	`id`,
	`login`,
	`password`,
	`role`,
    `email`
) VALUES (
	2,
	"user1",
	"EE11CBB19052E40B07AAC0CA060C23EE", /* MD5 хэш пароля "user" */
	0,
    "user1@gmail.com"
), (
	3,
	"user2",
	"EE11CBB19052E40B07AAC0CA060C23EE", /* MD5 хэш пароля "user" */
	0,
    "user2@gmail.com"
);

INSERT INTO `audio_track` 
(`id`,`album_id`,`name`,`artist_name`,`genre_id`,`price`, `path`, `deleted`) 
VALUES
(1, 1, "One", "Metallica", 1, 3, '/Users/brovar/Downloads/WALL.mp3', 0),
(2, 1, "To live is to die", "Metallica", 1, 3, '/Users/brovar/Downloads/WALL.mp3', 0);
 
INSERT INTO `order` 
(`id`,`audio_track_id`,`user_id`,`price`, `date`) 
VALUES 
(1, 2, 2, 3, "2021-10-22 17:04:52"),
(2, 1, 1, 3, "2021-10-23 15:04:52");

INSERT INTO `genre` 
(`id`,`genre`) 
VALUES 
(1, "TRASH METALL"),
(2, "ROCK"),
(3, "RAP");

INSERT INTO `comment` 
(`id`,`audio_track_id`,`user_id`,`user_login` ,`text`, `date`) 
VALUES 
(1, 2, 2, "user1", "Metallica one love <3", "2021-10-22 17:07:21"),
(2, 1, 1,"admin" , "This track is the top", "2021-10-23 17:04:52");