-- phpMyAdmin SQL Dump
-- version 4.6.4
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Sep 09, 2019 at 09:54 PM
-- Server version: 5.7.14
-- PHP Version: 5.6.25

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `smdb`
--
CREATE DATABASE IF NOT EXISTS `smdb` DEFAULT CHARACTER SET utf8 COLLATE utf8_bin;
USE `smdb`;

-- --------------------------------------------------------

--
-- Table structure for table `assurance`
--

CREATE TABLE `assurance` (
  `ID` int(11) NOT NULL,
  `debut` date DEFAULT NULL,
  `fin` date DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

--
-- Dumping data for table `assurance`
--

INSERT INTO `assurance` (`ID`, `debut`, `fin`) VALUES
(1, '2018-01-01', '2018-01-01'),
(101, '2019-10-01', '2020-07-01');

-- --------------------------------------------------------

--
-- Table structure for table `paiement`
--

CREATE TABLE `paiement` (
  `ID` int(11) NOT NULL,
  `MOIS` date NOT NULL,
  `MONTANT` decimal(8,2) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

--
-- Dumping data for table `paiement`
--

INSERT INTO `paiement` (`ID`, `MOIS`, `MONTANT`) VALUES
(1, '2019-10-01', '-1.00'),
(1, '2019-11-01', '-1.00'),
(1, '2019-12-01', '-1.00'),
(1, '2020-01-01', '-1.00'),
(1, '2020-02-01', '-1.00'),
(1, '2020-03-01', '-1.00'),
(1, '2020-04-01', '-1.00'),
(1, '2020-05-01', '-1.00'),
(1, '2020-06-01', '-1.00'),
(1, '2020-07-01', '-1.00'),
(101, '2019-10-01', '-1.00'),
(101, '2019-11-01', '-1.00'),
(101, '2019-12-01', '-1.00'),
(101, '2020-01-01', '-1.00'),
(101, '2020-02-01', '-1.00'),
(101, '2020-03-01', '-1.00'),
(101, '2020-04-01', '-1.00'),
(101, '2020-05-01', '-1.00'),
(101, '2020-06-01', '-1.00'),
(101, '2020-07-01', '-1.00');

-- --------------------------------------------------------

--
-- Table structure for table `student`
--

CREATE TABLE `student` (
  `ID` int(11) NOT NULL,
  `NOM` varchar(30) COLLATE utf8_bin NOT NULL,
  `PRENOM` varchar(30) COLLATE utf8_bin NOT NULL,
  `DATE_NAIS` date DEFAULT NULL,
  `LIEU_NAIS` varchar(30) COLLATE utf8_bin NOT NULL,
  `PRENOM_PERE` varchar(30) COLLATE utf8_bin NOT NULL,
  `PROFESSION_PERE` varchar(30) COLLATE utf8_bin NOT NULL,
  `NOM_MERE` varchar(30) COLLATE utf8_bin NOT NULL,
  `PRENOM_MERE` varchar(30) COLLATE utf8_bin NOT NULL,
  `PROFESSION_MERE` varchar(30) COLLATE utf8_bin NOT NULL,
  `ADRESSE` varchar(1024) COLLATE utf8_bin NOT NULL,
  `NUM_TEL` varchar(30) COLLATE utf8_bin NOT NULL,
  `DATE_INSCRIPTION` date DEFAULT NULL,
  `IMG_PATH` varchar(1024) COLLATE utf8_bin DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

--
-- Dumping data for table `student`
--

INSERT INTO `student` (`ID`, `NOM`, `PRENOM`, `DATE_NAIS`, `LIEU_NAIS`, `PRENOM_PERE`, `PROFESSION_PERE`, `NOM_MERE`, `PRENOM_MERE`, `PROFESSION_MERE`, `ADRESSE`, `NUM_TEL`, `DATE_INSCRIPTION`, `IMG_PATH`) VALUES
(101, 'Toto', 'Titi', '2000-01-01', 'Batna', 'Ptoto', 'aaaa', 'Mtoto', 'mtoto', 'Néant', 'Batna', '0505050505', '2019-09-05', NULL);

-- --------------------------------------------------------

--
-- Table structure for table `subject`
--

CREATE TABLE `subject` (
  `idsubject` int(11) NOT NULL,
  `intitule` varchar(255) COLLATE utf8_bin NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

--
-- Dumping data for table `subject`
--

INSERT INTO `subject` (`idsubject`, `intitule`) VALUES
(1, 'Mathématiques'),
(2, 'Langue arabe');

-- --------------------------------------------------------

--
-- Table structure for table `teacher`
--

CREATE TABLE `teacher` (
  `ID` int(11) NOT NULL,
  `NOM` varchar(30) COLLATE utf8_bin NOT NULL,
  `PRENOM` varchar(30) COLLATE utf8_bin NOT NULL,
  `DATE_NAIS` date DEFAULT NULL,
  `LIEU_NAIS` varchar(30) COLLATE utf8_bin NOT NULL,
  `ADRESSE` varchar(1024) COLLATE utf8_bin NOT NULL,
  `NUM_TEL` varchar(30) COLLATE utf8_bin NOT NULL,
  `DATE_INSCRIPTION` date DEFAULT NULL,
  `IMG_PATH` varchar(1024) COLLATE utf8_bin DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

--
-- Dumping data for table `teacher`
--

INSERT INTO `teacher` (`ID`, `NOM`, `PRENOM`, `DATE_NAIS`, `LIEU_NAIS`, `ADRESSE`, `NUM_TEL`, `DATE_INSCRIPTION`, `IMG_PATH`) VALUES
(1, 'N_Ens1', 'P_Ens1', '1940-01-01', 'Batna', 'Batna', '0505050505', '2019-09-05', NULL);

--
-- Indexes for dumped tables
--

--
-- Indexes for table `assurance`
--
ALTER TABLE `assurance`
  ADD PRIMARY KEY (`ID`);

--
-- Indexes for table `paiement`
--
ALTER TABLE `paiement`
  ADD PRIMARY KEY (`ID`,`MOIS`);

--
-- Indexes for table `student`
--
ALTER TABLE `student`
  ADD PRIMARY KEY (`ID`);

--
-- Indexes for table `subject`
--
ALTER TABLE `subject`
  ADD PRIMARY KEY (`idsubject`);

--
-- Indexes for table `teacher`
--
ALTER TABLE `teacher`
  ADD PRIMARY KEY (`ID`);

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
