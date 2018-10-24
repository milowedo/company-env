CREATE DATABASE IF NOT EXISTS `spring_hibernate` /*!40100 DEFAULT CHARACTER SET latin1 */;
/*GRANT ALL ON spring_hibernate.* TO 'mack'@'localhost'; /* requires root privileges */
USE `spring_hibernate`;

-- Table structure for table `user`
DROP TABLE IF EXISTS `users`;
CREATE TABLE IF NOT EXISTS `users` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(50) NOT NULL,
  `password` char(60) not null, /*fixed 60char bcrypted pass*/
  `first_name` varchar(45) DEFAULT NULL,
  `last_name` varchar(45) DEFAULT NULL,
  `email` varchar(45) DEFAULT NULL,
  `enabled` tinyint(1) default 1,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;

alter table `users`
  add unique key (`username`);


DROP TABLE IF EXISTS `authorities_table`;
CREATE TABLE IF NOT EXISTS `authorities_table` (
  `id` int(11) NOT NULL auto_increment,
  `authority` varchar(50) not null,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB auto_increment=1 DEFAULT CHARSET=utf8;

alter table `authorities_table`
  add unique key `authority_UK` (`authority`);


DROP TABLE IF EXISTS `users_authorities`;
CREATE TABLE IF NOT EXISTS `users_authorities` (
  `user_id` int(11) NOT NULL,
  `authority_id` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

alter table `users_authorities`
  add primary key (`user_id`, `authority_id`),

  add constraint `user_FK` foreign key (`user_id`)
references `users` (`id`)
  ON DELETE CASCADE ON UPDATE CASCADE,

  add constraint `authority_FK` foreign key (`authority_id`)
references `authorities_table` (`id`)
  ON DELETE CASCADE ON UPDATE CASCADE;


-- Inserting data for tables

LOCK TABLES `users` WRITE;
INSERT INTO `users` VALUES
                           (1,'admin', '$2a$10$2Ba0BsUs/CabZwwMMhi3ae4OjT9pNgH95N9ykmFfCL76752yrhlI6', 'Milosz','Szwedo','miloszszwedo@gmail.com',1),
                           (2,	'GarciaMarquez',	'$2a$10$qNS97GtlEUoNm6x/Cz2qzetNZe5ZNguRJhc/rAhxrkLugk9g1MRay'	'Ana'	'gabriel'	'anamiariajosediantonio@admin.com',	1),
                           (3,	'AmericanForgery',	'$2a$10$gsU5eqbO268bz2kmetp8UuZpe8tHt0MOOSQloyTrMWjnzk.0jyfjW'	'Robert'	'Childan'	'wherewereyou@gmail.com',	1),
                           (4,	'JOHNNY0911',	'$2a$10$5vflXJl9g2kxe03CCCt65uZIkhKZeBvnK53dC4XJmo/Z3J8dnyp/e'	'Jasper'	'Hancock'	'gibbtt@adminss.com'	,1),
                           (6,	'Scientist101',	'$2a$10$8wBu0y7XOF5L88GbEmD/v.oLA7F2AVFW.S7EQOVq/PMwO18jBqWqe'	'Iljon'	'Tichy'	'tichy@gmail.com',	1),
                           (7,	'I_dont_understand',	'$2a$10$poiJkWphu0yEdfJNZDM0uePGa/3nZ2kyVxZmYtHI.Acnh3Ty6efEm'	'Joseph'	'K.'	'helpme@abbys.com',	1);

LOCK TABLES `authorities_table` WRITE;
INSERT INTO `authorities_table` VALUES
                                       (1,	'ROLE_ADMIN'),
                                       (2,	'ROLE_ELSE'),
                                       (4,	'ROLE_HR'),
                                       (3,	'ROLE_IT');

LOCK TABLES `users_authorities` WRITE;
INSERT INTO `users_authorities` VALUES
                                       (1,	1),
                                       (6,	2),
                                       (2,	3),
                                       (3,	3),
                                       (4,	4),
                                       (7,	4);
UNLOCK TABLES;

