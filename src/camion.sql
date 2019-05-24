SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";
CREATE TABLE `combustible` (`idCombustible` int(11) NOT NULL,`idViaje` int(11) NOT NULL,`litros` double NOT NULL,`kilometros` int(11) NOT NULL,`precio` double NOT NULL,`fecha` datetime NOT NULL) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
INSERT INTO `combustible` (`idCombustible`, `idViaje`, `litros`, `kilometros`, `precio`, `fecha`) VALUES (1, 1, 10, 645500, 13.1, '2019-05-23 20:22:23'),(2, 2, 50, 10000, 60.6, '2019-05-23 20:27:35'),(3, 3, 50, 650000, 60, '2019-05-23 20:30:31'),(4, 4, 50, 650000, 60, '2019-05-23 20:30:31'),(5, 4, 600, 156000, 800.9, '2019-05-23 20:33:10'),(6, 5, 50, 650000, 65.3, '2019-05-23 20:42:07'),(7, 6, 123, 955000, 150, '2019-05-23 20:50:51');
CREATE TABLE `lugar` (`idLugar` int(11) NOT NULL,`ciudad` varchar(30) COLLATE utf8_bin NOT NULL,`direccion` varchar(50) COLLATE utf8_bin NOT NULL,`nDireccion` int(8) NOT NULL) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
INSERT INTO `lugar` (`idLugar`, `ciudad`, `direccion`, `nDireccion`) VALUES(1, 'Manises', 'Cuenca', 15),(2, 'Torrent', 'Cami Reial', 116),(3, 'Paterna', 'Carrer del Comte', 89),(4, 'Montgat', 'Av. del Cid', 260),(5, 'Madrid', 'Pedrera', 109);
CREATE TABLE `peaje` (`idPeaje` int(11) NOT NULL,`idViaje` int(11) NOT NULL,`costo` double NOT NULL,`fecha` datetime NOT NULL) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
INSERT INTO `peaje` (`idPeaje`, `idViaje`, `costo`, `fecha`) VALUES(1, 1, 12.5, '2019-05-23 20:22:01'),(2, 2, 12.5, '2019-05-23 20:27:19'),(3, 2, 10.2, '2019-05-23 20:27:23'),(4, 3, 15, '2019-05-23 20:30:33'),(5, 3, 10, '2019-05-23 20:30:35'),(6, 3, 6, '2019-05-23 20:30:38'),(7, 4, 15, '2019-05-23 20:30:33'),(8, 4, 10, '2019-05-23 20:30:35'),(9, 4, 6, '2019-05-23 20:30:38'),(10, 4, 12.2, '2019-05-23 20:32:39'),(11, 4, 12.2, '2019-05-23 20:32:42'),(12, 4, 6.2, '2019-05-23 20:32:49'),(13, 6, 45, '2019-05-23 20:50:33'),(14, 6, 65.2, '2019-05-23 20:50:36');
CREATE TABLE `viaje` (`idViaje` int(11) NOT NULL,`tipo` varchar(11) COLLATE utf8_bin NOT NULL,`duracion` int(11) NOT NULL,`duracionTotal` int(11) NOT NULL,`idSalida` int(11) NOT NULL,`idLlegada` int(11) NOT NULL,`kilometos` int(11) NOT NULL,`fechaLlegada` datetime NOT NULL,`fechaSalida` datetime NOT NULL) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
INSERT INTO `viaje` (`idViaje`, `tipo`, `duracion`, `duracionTotal`, `idSalida`, `idLlegada`, `kilometos`, `fechaLlegada`, `fechaSalida`) VALUES (1, 'Viaje Corto', 36, 12504, 2, 2, 600, '2019-01-16 20:22:32', '2019-01-16 21:27:56'),(2, 'Lona/Frigo', 29, 9560, 1, 3, 551, '2019-05-23 20:27:41', '2019-05-23 20:27:13'),(3, 'Cisterna', 30, 3645, 4, 1, 352, '2019-05-23 20:30:46', '2019-05-23 20:30:16'),(4, 'Lona/Frigo', 41, 4533, 3, 5, 398, '2019-01-09 13:26:36', '2019-01-16 17:31:38'),(5, 'Cisterna', 33, 8620, 5, 4, 357, '2019-05-23 20:42:25', '2019-05-23 20:41:53'),(6, 'Viaje Corto', 37, 37, 5, 3, 987, '2019-05-23 20:51:06', '2019-05-23 20:50:29');
ALTER TABLE `combustible` ADD PRIMARY KEY (`idCombustible`), ADD KEY `fkViaje` (`idViaje`);
ALTER TABLE `lugar` ADD PRIMARY KEY (`idLugar`);
ALTER TABLE `peaje` ADD PRIMARY KEY (`idPeaje`), ADD KEY `fkViajePeaje` (`idViaje`);
ALTER TABLE `viaje` ADD PRIMARY KEY (`idViaje`), ADD KEY `fkSalida` (`idSalida`), ADD KEY `fkLlegada` (`idLlegada`);
ALTER TABLE `combustible` MODIFY `idCombustible` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=8;
ALTER TABLE `lugar` MODIFY `idLugar` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;
ALTER TABLE `peaje` MODIFY `idPeaje` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=15;
ALTER TABLE `viaje` MODIFY `idViaje` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=7;
ALTER TABLE `combustible` ADD CONSTRAINT `fkViaje` FOREIGN KEY (`idViaje`) REFERENCES `viaje` (`idViaje`);
ALTER TABLE `peaje` ADD CONSTRAINT `fkViajePeaje` FOREIGN KEY (`idViaje`) REFERENCES `viaje` (`idViaje`);
ALTER TABLE `viaje` ADD CONSTRAINT `fkLlegada` FOREIGN KEY (`idLlegada`) REFERENCES `lugar` (`idLugar`), ADD CONSTRAINT `fkSalida` FOREIGN KEY (`idSalida`) REFERENCES `lugar` (`idLugar`);
COMMIT;