-- phpMyAdmin SQL Dump
-- version 5.2.0
-- https://www.phpmyadmin.net/
--
-- Servidor: 127.0.0.1
-- Tiempo de generación: 21-02-2023 a las 06:25:43
-- Versión del servidor: 10.4.24-MariaDB
-- Versión de PHP: 8.1.6

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de datos: `control_rampas`
--

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `plan_embarque`
--

CREATE TABLE `plan_embarque` (
  `id_emb` int(11) NOT NULL,
  `rampa` varchar(20) DEFAULT '',
  `trailer` varchar(20) NOT NULL,
  `plant_code` varchar(10) NOT NULL,
  `tipo_emb` varchar(50) NOT NULL,
  `accion` varchar(200) NOT NULL,
  `type_material` varchar(50) NOT NULL,
  `prog_date` date NOT NULL,
  `liberado` varchar(20) DEFAULT ''
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Volcado de datos para la tabla `plan_embarque`
--

INSERT INTO `plan_embarque` (`id_emb`, `rampa`, `trailer`, `plant_code`, `tipo_emb`, `accion`, `type_material`, `prog_date`, `liberado`) VALUES
(1, 'RAMPA #11', '15326353', '0997', '', 'Carga', 'N/A', '2023-02-10', ''),
(2, 'RAMPA #1', '15326353', '0911', 'Normal', 'Carga', 'N/A', '2023-02-10', '');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `rampas`
--

CREATE TABLE `rampas` (
  `id` int(11) NOT NULL,
  `rampa_num` varchar(20) NOT NULL,
  `status` tinyint(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Volcado de datos para la tabla `rampas`
--

INSERT INTO `rampas` (`id`, `rampa_num`, `status`) VALUES
(1, 'RAMPA #1', 1),
(2, 'RAMPA #2', 0),
(3, 'RAMPA #3', 0),
(4, 'RAMPA #4', 0),
(5, 'RAMPA #5', 0),
(6, 'RAMPA #6', 0),
(7, 'RAMPA #7', 0),
(8, 'RAMPA #8', 0),
(9, 'RAMPA #9', 0),
(10, 'RAMPA #10', 0),
(11, 'RAMPA #11', 0),
(12, 'RAMPA #12', 0),
(22, 'RAMPA #13', 0),
(23, 'RAMPA #14', 0);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `tb_image`
--

CREATE TABLE `tb_image` (
  `id_img` int(11) NOT NULL,
  `name` varchar(250) NOT NULL,
  `id_plan` int(250) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Volcado de datos para la tabla `tb_image`
--

INSERT INTO `tb_image` (`id_img`, `name`, `id_plan`) VALUES
(10, '1 (20).jpg', 1),
(11, '2.jpg', 2),
(12, '2 (1).jpg', 2),
(13, '2 (2).jpg', 2),
(38, 'foto_1 (4).jpg', 0),
(39, '2 (14).jpg', 2),
(40, '1 (23).jpg', 1),
(41, '1 (24).jpg', 1),
(42, '1 (25).jpg', 1),
(43, '1 (26).jpg', 1),
(44, '1 (27).jpg', 1),
(45, '1 (28).jpg', 1),
(46, '1 (29).jpg', 1),
(47, '1 (30).jpg', 1),
(48, '1 (31).jpg', 1);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `usuarios`
--

CREATE TABLE `usuarios` (
  `iduser_` int(11) NOT NULL,
  `username` varchar(50) NOT NULL,
  `email` varchar(250) NOT NULL,
  `password` varchar(50) NOT NULL,
  `photo` varchar(200) NOT NULL,
  `nombres` varchar(120) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Volcado de datos para la tabla `usuarios`
--

INSERT INTO `usuarios` (`iduser_`, `username`, `email`, `password`, `photo`, `nombres`) VALUES
(1, '255603', 'alberto.cruzloera@sewsus.com.mx', '255603', 'profile_image/Alberto Cruz Loera.jpg', 'Alberto Cruz Loera'),
(2, '255604', 'luis.hernandez@sewsus.com.mx', '255604', '', 'Luis Hernandez');

--
-- Índices para tablas volcadas
--

--
-- Indices de la tabla `plan_embarque`
--
ALTER TABLE `plan_embarque`
  ADD PRIMARY KEY (`id_emb`);

--
-- Indices de la tabla `rampas`
--
ALTER TABLE `rampas`
  ADD PRIMARY KEY (`id`);

--
-- Indices de la tabla `tb_image`
--
ALTER TABLE `tb_image`
  ADD PRIMARY KEY (`id_img`);

--
-- Indices de la tabla `usuarios`
--
ALTER TABLE `usuarios`
  ADD PRIMARY KEY (`iduser_`);

--
-- AUTO_INCREMENT de las tablas volcadas
--

--
-- AUTO_INCREMENT de la tabla `plan_embarque`
--
ALTER TABLE `plan_embarque`
  MODIFY `id_emb` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- AUTO_INCREMENT de la tabla `rampas`
--
ALTER TABLE `rampas`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=24;

--
-- AUTO_INCREMENT de la tabla `tb_image`
--
ALTER TABLE `tb_image`
  MODIFY `id_img` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=49;

--
-- AUTO_INCREMENT de la tabla `usuarios`
--
ALTER TABLE `usuarios`
  MODIFY `iduser_` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
