CREATE DATABASE `nettyDB`;

CREATE TABLE `User` (
  `id` bigint(20) NOT NULL,
  `date` varchar(255) DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

INSERT INTO `User` VALUES (1,'30.03.1985','radul@gmail.com','Radu'),(3,'30.02.1986','georgel@gmaill.com','George'),(4,'30.03.1955','matei@gmaill.com','Matei');