--
-- Table structure for table `job`
--

DROP TABLE IF EXISTS `job`;

CREATE TABLE `job` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `site` varchar(50) NOT NULL,
  `site_id` varchar(50) NOT NULL,
  `date` date NOT NULL,
  `title` varchar(250) NOT NULL,
  `location` varchar(100) NOT NULL,
  `salary` int(11) DEFAULT NULL,
  `currency` varchar(10) DEFAULT NULL,
  `company` varchar(100) NOT NULL,
  `description` varchar(15000) NOT NULL,
  `url` varchar(250) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `job_id_uindex` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8;
