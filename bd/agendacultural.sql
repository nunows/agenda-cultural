-- MySQL Administrator dump 1.4
--
-- ------------------------------------------------------
-- Server version	5.1.58-1ubuntu1


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;


--
-- Create schema AgendaCultural
--

CREATE DATABASE IF NOT EXISTS AgendaCultural;
USE AgendaCultural;

--
-- Definition of table `AgendaCultural`.`eventos`
--

DROP TABLE IF EXISTS `AgendaCultural`.`eventos`;
CREATE TABLE  `AgendaCultural`.`eventos` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `id_local` int(11) NOT NULL,
  `id_tipo_evento` int(11) NOT NULL,
  `nome` varchar(20) NOT NULL,
  `datahora` datetime NOT NULL,
  `imagem_url` varchar(250) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `id_local` (`id_local`),
  KEY `id_tipo_evento` (`id_tipo_evento`),
  CONSTRAINT `local_constraint` FOREIGN KEY (`id_local`) REFERENCES `locais` (`id`),
  CONSTRAINT `tipo_evento_constraint` FOREIGN KEY (`id_tipo_evento`) REFERENCES `tipo_eventos` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=latin1 ROW_FORMAT=DYNAMIC;

--
-- Dumping data for table `AgendaCultural`.`eventos`
--

/*!40000 ALTER TABLE `eventos` DISABLE KEYS */;
LOCK TABLES `eventos` WRITE;
INSERT INTO `AgendaCultural`.`eventos` VALUES  (3,1,1,'Mão Morta','2011-11-18 20:00:00','teste.jpg'),
 (4,5,1,'Teste','2012-09-12 20:00:00','teste.jpg'),
 (5,5,2,'Peça de Teatro','2011-12-20 21:00:00','cartaz.jpg'),
 (6,1,1,'Deftones','2012-01-12 20:00:00','deftones.jpg');
UNLOCK TABLES;
/*!40000 ALTER TABLE `eventos` ENABLE KEYS */;


--
-- Definition of table `AgendaCultural`.`locais`
--

DROP TABLE IF EXISTS `AgendaCultural`.`locais`;
CREATE TABLE  `AgendaCultural`.`locais` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `nome` varchar(30) NOT NULL,
  `morada` varchar(60) NOT NULL,
  `latitude` float(10,6) NOT NULL,
  `longitude` float(10,6) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=latin1;

--
-- Dumping data for table `AgendaCultural`.`locais`
--

/*!40000 ALTER TABLE `locais` DISABLE KEYS */;
LOCK TABLES `locais` WRITE;
INSERT INTO `AgendaCultural`.`locais` VALUES  (1,'Coliseu dos Recreios','Rua das Portas de Santo Antão, Lisboa',38.716530,-9.140822),
 (3,'Teste','morada teste',1.220000,23.299999),
 (5,'ISCTE-IUL','av forças armadas',38.747936,-9.150930);
UNLOCK TABLES;
/*!40000 ALTER TABLE `locais` ENABLE KEYS */;


--
-- Definition of table `AgendaCultural`.`tipo_eventos`
--

DROP TABLE IF EXISTS `AgendaCultural`.`tipo_eventos`;
CREATE TABLE  `AgendaCultural`.`tipo_eventos` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `nome` varchar(20) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=latin1;

--
-- Dumping data for table `AgendaCultural`.`tipo_eventos`
--

/*!40000 ALTER TABLE `tipo_eventos` DISABLE KEYS */;
LOCK TABLES `tipo_eventos` WRITE;
INSERT INTO `AgendaCultural`.`tipo_eventos` VALUES  (1,'Concerto'),
 (2,'Teatro');
UNLOCK TABLES;
/*!40000 ALTER TABLE `tipo_eventos` ENABLE KEYS */;


--
-- Definition of procedure `AgendaCultural`.`SpEvento`
--

DROP PROCEDURE IF EXISTS `AgendaCultural`.`SpEvento`;

DELIMITER $$

/*!50003 SET @TEMP_SQL_MODE=@@SQL_MODE, SQL_MODE='' */ $$
CREATE DEFINER=`agenda`@`%` PROCEDURE  `AgendaCultural`.`SpEvento`(
IN id INT
)
BEGIN

SELECT eventos.id, eventos.nome, eventos.datahora, eventos.imagem_url, locais.nome, locais.morada, locais.latitude, locais.longitude, tipo_eventos.nome 
FROM eventos 
INNER JOIN locais ON eventos.id_local = locais.id
INNER JOIN tipo_eventos ON eventos.id_tipo_evento = tipo_eventos.id 
WHERE eventos.id = id ORDER BY datahora;

END $$
/*!50003 SET SESSION SQL_MODE=@TEMP_SQL_MODE */  $$

DELIMITER ;

--
-- Definition of procedure `AgendaCultural`.`SpEventos`
--

DROP PROCEDURE IF EXISTS `AgendaCultural`.`SpEventos`;

DELIMITER $$

/*!50003 SET @TEMP_SQL_MODE=@@SQL_MODE, SQL_MODE='' */ $$
CREATE DEFINER=`agenda`@`%` PROCEDURE  `AgendaCultural`.`SpEventos`(
IN many INT
)
BEGIN

SET SQL_SELECT_LIMIT = many; 

SELECT eventos.id, eventos.nome, eventos.datahora, eventos.imagem_url, locais.nome, locais.morada, locais.latitude, locais.longitude, tipo_eventos.nome 
FROM eventos 
INNER JOIN locais ON eventos.id_local = locais.id 
INNER JOIN tipo_eventos ON eventos.id_tipo_evento = tipo_eventos.id 
ORDER BY datahora;

SET SQL_SELECT_LIMIT = DEFAULT; 

END $$
/*!50003 SET SESSION SQL_MODE=@TEMP_SQL_MODE */  $$

DELIMITER ;

--
-- Definition of procedure `AgendaCultural`.`SpEventosNearBy`
--

DROP PROCEDURE IF EXISTS `AgendaCultural`.`SpEventosNearBy`;

DELIMITER $$

/*!50003 SET @TEMP_SQL_MODE=@@SQL_MODE, SQL_MODE='' */ $$
CREATE DEFINER=`agenda`@`%` PROCEDURE  `AgendaCultural`.`SpEventosNearBy`(
IN lat FLOAT(10,6),
IN lon FLOAT(10,6),
IN distance INT
)
BEGIN

SELECT eventos.id, eventos.nome, eventos.datahora, eventos.imagem_url, locais.nome, locais.morada, locais.latitude, locais.longitude, tipo_eventos.nome, 
(6371 * acos(cos(radians(lat)) * cos(radians(locais.latitude)) * cos(radians(locais.longitude) - radians(lon)) + sin( radians(lat) ) * sin(radians(locais.latitude)))) AS distancia 
FROM eventos INNER JOIN locais ON eventos.id_local = locais.id 
INNER JOIN tipo_eventos ON eventos.id_tipo_evento = tipo_eventos.id 
HAVING distancia <= distance ORDER BY distancia, datahora;

END $$
/*!50003 SET SESSION SQL_MODE=@TEMP_SQL_MODE */  $$

DELIMITER ;

--
-- Definition of procedure `AgendaCultural`.`SpEventosTipo`
--

DROP PROCEDURE IF EXISTS `AgendaCultural`.`SpEventosTipo`;

DELIMITER $$

/*!50003 SET @TEMP_SQL_MODE=@@SQL_MODE, SQL_MODE='' */ $$
CREATE DEFINER=`agenda`@`%` PROCEDURE  `AgendaCultural`.`SpEventosTipo`(
IN id INT,
IN many INT
)
BEGIN

SET SQL_SELECT_LIMIT = many; 

SELECT eventos.id, eventos.nome, eventos.datahora, eventos.imagem_url, locais.nome, locais.morada, locais.latitude, locais.longitude, tipo_eventos.nome 
FROM eventos 
INNER JOIN locais ON eventos.id_local = locais.id 
INNER JOIN tipo_eventos ON eventos.id_tipo_evento = tipo_eventos.id 
WHERE tipo_eventos.id = id ORDER BY datahora;

SET SQL_SELECT_LIMIT = DEFAULT; 

END $$
/*!50003 SET SESSION SQL_MODE=@TEMP_SQL_MODE */  $$

DELIMITER ;

--
-- Definition of procedure `AgendaCultural`.`SpTipoEventos`
--

DROP PROCEDURE IF EXISTS `AgendaCultural`.`SpTipoEventos`;

DELIMITER $$

/*!50003 SET @TEMP_SQL_MODE=@@SQL_MODE, SQL_MODE='' */ $$
CREATE DEFINER=`agenda`@`%` PROCEDURE  `AgendaCultural`.`SpTipoEventos`()
BEGIN
	SELECT id, nome FROM tipo_eventos ORDER BY nome;
END $$
/*!50003 SET SESSION SQL_MODE=@TEMP_SQL_MODE */  $$

DELIMITER ;



/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
