-- GestionAulas: estructura de la base de datos y datos mínimos.
-- No es obligatorio: la aplicación crea las tablas y los tipos de usuario al arrancar.
-- Úsalo para crear la base de datos a mano:  mysql -u root -p < mysql/gestoraulas.sql

CREATE DATABASE IF NOT EXISTS gestoraulas CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci;
USE gestoraulas;

SET FOREIGN_KEY_CHECKS = 0;

/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE IF NOT EXISTS `aula` (
  `idaula` bigint NOT NULL AUTO_INCREMENT,
  `activo` bit(1) DEFAULT NULL,
  `aireacondicionado` bit(1) DEFAULT NULL,
  `appletv` bit(1) DEFAULT NULL,
  `capacidad` int DEFAULT NULL,
  `descripcion` varchar(255) DEFAULT NULL,
  `hdmi` bit(1) DEFAULT NULL,
  `nenchufes` int DEFAULT NULL,
  `nombreaula` varchar(255) DEFAULT NULL,
  `proyector` bit(1) DEFAULT NULL,
  `smarttv` bit(1) DEFAULT NULL,
  `PLANTAidplanta` bigint DEFAULT NULL,
  `TIPOAULAidtipo` bigint DEFAULT NULL,
  PRIMARY KEY (`idaula`),
  KEY `FK1u65srmm32a0t16lml4scs11a` (`PLANTAidplanta`),
  KEY `FKaflsa8xg5j2f4rf7y7perr6cc` (`TIPOAULAidtipo`),
  CONSTRAINT `FK1u65srmm32a0t16lml4scs11a` FOREIGN KEY (`PLANTAidplanta`) REFERENCES `planta` (`idplanta`),
  CONSTRAINT `FKaflsa8xg5j2f4rf7y7perr6cc` FOREIGN KEY (`TIPOAULAidtipo`) REFERENCES `tipoaula` (`idtipo`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE IF NOT EXISTS `curso` (
  `idcurso` bigint NOT NULL AUTO_INCREMENT,
  `activo` bit(1) DEFAULT NULL,
  `descripcion` varchar(255) DEFAULT NULL,
  `FAMCURSOidfamcurso` bigint NOT NULL,
  PRIMARY KEY (`idcurso`),
  KEY `FK3q84qn4f2eof6k1debmc1axu3` (`FAMCURSOidfamcurso`),
  CONSTRAINT `FK3q84qn4f2eof6k1debmc1axu3` FOREIGN KEY (`FAMCURSOidfamcurso`) REFERENCES `famcurso` (`idfamcurso`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE IF NOT EXISTS `edificio` (
  `idedificio` bigint NOT NULL AUTO_INCREMENT,
  `activo` bit(1) DEFAULT NULL,
  `descripcion` varchar(255) DEFAULT NULL,
  `naulas` int DEFAULT NULL,
  `npuertasacceso` int DEFAULT NULL,
  `ubicacion` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`idedificio`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE IF NOT EXISTS `famcurso` (
  `idfamcurso` bigint NOT NULL AUTO_INCREMENT,
  `activo` bit(1) DEFAULT NULL,
  `descripcion` varchar(255) DEFAULT NULL,
  `SUBUSOAULAidsubuso` bigint NOT NULL,
  PRIMARY KEY (`idfamcurso`),
  KEY `FKm8ajv68k3c4t9eygw61xo3rcp` (`SUBUSOAULAidsubuso`),
  CONSTRAINT `FKm8ajv68k3c4t9eygw61xo3rcp` FOREIGN KEY (`SUBUSOAULAidsubuso`) REFERENCES `subusoaula` (`idsubuso`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE IF NOT EXISTS `planta` (
  `idplanta` bigint NOT NULL AUTO_INCREMENT,
  `activo` bit(1) DEFAULT NULL,
  `descripcion` varchar(255) DEFAULT NULL,
  `naulaauxiliares` int DEFAULT NULL,
  `naulasdocentes` int DEFAULT NULL,
  `EDIFICIOidedificio` bigint NOT NULL,
  PRIMARY KEY (`idplanta`),
  KEY `FKha5eokmgvfu98o6jylf8s84to` (`EDIFICIOidedificio`),
  CONSTRAINT `FKha5eokmgvfu98o6jylf8s84to` FOREIGN KEY (`EDIFICIOidedificio`) REFERENCES `edificio` (`idedificio`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE IF NOT EXISTS `reserva` (
  `idreserva` bigint NOT NULL AUTO_INCREMENT,
  `activo` bit(1) DEFAULT NULL,
  `fechafin` date DEFAULT NULL,
  `fechainicio` date DEFAULT NULL,
  `horafin` time DEFAULT NULL,
  `horainicio` time DEFAULT NULL,
  `validar` bit(1) DEFAULT NULL,
  `AULAidaula` bigint NOT NULL,
  `CURSOidcurso` bigint NOT NULL,
  `USUARIOidusuario` bigint NOT NULL,
  PRIMARY KEY (`idreserva`),
  KEY `FKrhe4fn6t7r8rhlkn7abjsgs5` (`AULAidaula`),
  KEY `FKomgeuomqiavfchmy47j0nbrve` (`CURSOidcurso`),
  KEY `FKkx3ov5i0jfxeufajv28t5xfu4` (`USUARIOidusuario`),
  CONSTRAINT `FKkx3ov5i0jfxeufajv28t5xfu4` FOREIGN KEY (`USUARIOidusuario`) REFERENCES `USUARIO` (`idusuario`),
  CONSTRAINT `FKomgeuomqiavfchmy47j0nbrve` FOREIGN KEY (`CURSOidcurso`) REFERENCES `curso` (`idcurso`),
  CONSTRAINT `FKrhe4fn6t7r8rhlkn7abjsgs5` FOREIGN KEY (`AULAidaula`) REFERENCES `aula` (`idaula`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE IF NOT EXISTS `subusoaula` (
  `idsubuso` bigint NOT NULL AUTO_INCREMENT,
  `activo` bit(1) DEFAULT NULL,
  `descripcion` varchar(255) DEFAULT NULL,
  `USOAULAiduso` bigint NOT NULL,
  PRIMARY KEY (`idsubuso`),
  KEY `FKcksff4st6qyy2yb0s459q3ftv` (`USOAULAiduso`),
  CONSTRAINT `FKcksff4st6qyy2yb0s459q3ftv` FOREIGN KEY (`USOAULAiduso`) REFERENCES `usoaula` (`iduso`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE IF NOT EXISTS `tipoaula` (
  `idtipo` bigint NOT NULL AUTO_INCREMENT,
  `activo` bit(1) DEFAULT NULL,
  `descripcion` varchar(255) DEFAULT NULL,
  `nombretipo` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`idtipo`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE IF NOT EXISTS `TIPOUSUARIO` (
  `idtipousuario` bigint NOT NULL AUTO_INCREMENT,
  `activo` bit(1) DEFAULT NULL,
  `descripcion` varchar(255) DEFAULT NULL,
  `tipo` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`idtipousuario`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE IF NOT EXISTS `usoaula` (
  `iduso` bigint NOT NULL AUTO_INCREMENT,
  `activo` bit(1) DEFAULT NULL,
  `descripcion` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`iduso`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE IF NOT EXISTS `USUARIO` (
  `idusuario` bigint NOT NULL AUTO_INCREMENT,
  `activo` bit(1) DEFAULT NULL,
  `apellido` varchar(255) DEFAULT NULL,
  `dni` varchar(255) DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `fechaactivacion` date DEFAULT NULL,
  `fechadesactivacion` date DEFAULT NULL,
  `nombre` varchar(255) DEFAULT NULL,
  `contrasenya` varchar(100) DEFAULT NULL,
  `telefono` varchar(255) DEFAULT NULL,
  `TIPOUSUARIOidtipousuario` bigint DEFAULT NULL,
  PRIMARY KEY (`idusuario`),
  KEY `FKjj75s1suajnl1cm8t2rjign0w` (`TIPOUSUARIOidtipousuario`),
  CONSTRAINT `FKjj75s1suajnl1cm8t2rjign0w` FOREIGN KEY (`TIPOUSUARIOidtipousuario`) REFERENCES `TIPOUSUARIO` (`idtipousuario`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

SET FOREIGN_KEY_CHECKS = 1;

-- Tipos de usuario: la seguridad asigna el rol según el id (1 = ADMIN, 3 = USER)
INSERT IGNORE INTO `TIPOUSUARIO` (`idtipousuario`, `tipo`, `descripcion`, `activo`) VALUES
  (1, 'ADMIN', 'Administrador', 1),
  (3, 'USER', 'Usuario', 1);
