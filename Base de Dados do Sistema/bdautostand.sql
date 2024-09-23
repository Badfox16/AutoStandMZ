-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Tempo de geração: 23/09/2024 às 21:47
-- Versão do servidor: 10.4.32-MariaDB
-- Versão do PHP: 8.2.12

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Banco de dados: `bdautostand`
--

-- --------------------------------------------------------

--
-- Estrutura para tabela `tbaluguer`
--

CREATE TABLE `tbaluguer` (
  `Codigo` int(11) NOT NULL,
  `IDCarro` int(11) DEFAULT NULL,
  `IDCliente` int(11) DEFAULT NULL,
  `Taxa` decimal(10,2) DEFAULT NULL,
  `Data_Aluguer` date DEFAULT NULL,
  `Data_Devolucao` date DEFAULT NULL,
  `Estado` varchar(20) DEFAULT 'Ativo'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Despejando dados para a tabela `tbaluguer`
--

INSERT INTO `tbaluguer` (`Codigo`, `IDCarro`, `IDCliente`, `Taxa`, `Data_Aluguer`, `Data_Devolucao`, `Estado`) VALUES
(1, 9, 1, 1750.00, '2024-09-23', '2024-09-30', 'Ativo'),
(2, 10, 3, 1000.00, '2024-09-23', '2024-10-03', 'Ativo');

-- --------------------------------------------------------

--
-- Estrutura para tabela `tbcarros`
--

CREATE TABLE `tbcarros` (
  `IDCarro` int(11) NOT NULL,
  `Marca` varchar(35) NOT NULL,
  `Modelo` varchar(35) NOT NULL,
  `Matricula` varchar(20) DEFAULT NULL,
  `Taxa_Diaria` decimal(10,2) DEFAULT NULL,
  `Status` enum('Disponível','Alugado') NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Despejando dados para a tabela `tbcarros`
--

INSERT INTO `tbcarros` (`IDCarro`, `Marca`, `Modelo`, `Matricula`, `Taxa_Diaria`, `Status`) VALUES
(7, 'Toyotaro', 'Mark II', 'MBH-14-94', 100.00, 'Alugado'),
(9, 'Nissan', 'Navarra', 'AEU-234-MP', 250.00, 'Alugado'),
(10, 'Nissan', 'Kuruma', 'BAJ-19-21', 100.00, 'Alugado'),
(11, 'Ford', 'Ranger', 'ACA-234-MC', 250.00, 'Disponível');

-- --------------------------------------------------------

--
-- Estrutura para tabela `tbclientes`
--

CREATE TABLE `tbclientes` (
  `IDCliente` int(11) NOT NULL,
  `Nome` varchar(100) DEFAULT NULL,
  `Apelido` varchar(100) DEFAULT NULL,
  `BI` varchar(30) DEFAULT NULL,
  `Telefone` varchar(15) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Despejando dados para a tabela `tbclientes`
--

INSERT INTO `tbclientes` (`IDCliente`, `Nome`, `Apelido`, `BI`, `Telefone`) VALUES
(1, 'Mutizinho', 'Maita', '701210019F', '845259845'),
(3, 'Isabel', 'Alcidio', '8954654651T', '879584561');

-- --------------------------------------------------------

--
-- Estrutura para tabela `tbusuarios`
--

CREATE TABLE `tbusuarios` (
  `IDUser` int(11) NOT NULL,
  `Nome` varchar(100) NOT NULL,
  `Apelido` varchar(100) NOT NULL,
  `Telefone` varchar(100) NOT NULL,
  `Username` varchar(30) NOT NULL,
  `Senha` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Despejando dados para a tabela `tbusuarios`
--

INSERT INTO `tbusuarios` (`IDUser`, `Nome`, `Apelido`, `Telefone`, `Username`, `Senha`) VALUES
(1, 'Mutizo', 'Maita2', '845698789', 'admin', 'admin'),
(3, 'Anderson', 'Maxemege', '845697845', 'quinho', 'quinho');

--
-- Índices para tabelas despejadas
--

--
-- Índices de tabela `tbaluguer`
--
ALTER TABLE `tbaluguer`
  ADD PRIMARY KEY (`Codigo`),
  ADD KEY `IDCarro` (`IDCarro`),
  ADD KEY `IDCliente` (`IDCliente`);

--
-- Índices de tabela `tbcarros`
--
ALTER TABLE `tbcarros`
  ADD PRIMARY KEY (`IDCarro`),
  ADD UNIQUE KEY `Matricula` (`Matricula`);

--
-- Índices de tabela `tbclientes`
--
ALTER TABLE `tbclientes`
  ADD PRIMARY KEY (`IDCliente`),
  ADD UNIQUE KEY `BI` (`BI`);

--
-- Índices de tabela `tbusuarios`
--
ALTER TABLE `tbusuarios`
  ADD PRIMARY KEY (`IDUser`),
  ADD UNIQUE KEY `Username` (`Username`);

--
-- AUTO_INCREMENT para tabelas despejadas
--

--
-- AUTO_INCREMENT de tabela `tbaluguer`
--
ALTER TABLE `tbaluguer`
  MODIFY `Codigo` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- AUTO_INCREMENT de tabela `tbcarros`
--
ALTER TABLE `tbcarros`
  MODIFY `IDCarro` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=12;

--
-- AUTO_INCREMENT de tabela `tbclientes`
--
ALTER TABLE `tbclientes`
  MODIFY `IDCliente` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- AUTO_INCREMENT de tabela `tbusuarios`
--
ALTER TABLE `tbusuarios`
  MODIFY `IDUser` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- Restrições para tabelas despejadas
--

--
-- Restrições para tabelas `tbaluguer`
--
ALTER TABLE `tbaluguer`
  ADD CONSTRAINT `tbaluguer_ibfk_1` FOREIGN KEY (`IDCarro`) REFERENCES `tbcarros` (`IDCarro`),
  ADD CONSTRAINT `tbaluguer_ibfk_2` FOREIGN KEY (`IDCliente`) REFERENCES `tbclientes` (`IDCliente`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
