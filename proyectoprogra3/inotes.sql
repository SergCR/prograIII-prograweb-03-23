-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Nov 07, 2023 at 04:52 PM
-- Server version: 10.4.28-MariaDB
-- PHP Version: 8.0.28

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `inotes`
--

-- --------------------------------------------------------

--
-- Table structure for table `notes`
--

CREATE TABLE `notes` (
  `note_id` int(4) NOT NULL,
  `user_id` int(4) NOT NULL,
  `text` varchar(200) NOT NULL,
  `title` varchar(50) NOT NULL,
  `bg_color` varchar(8) NOT NULL,
  `pw_enabled` tinyint(1) NOT NULL,
  `note_password` varchar(20) NOT NULL,
  `note_category` varchar(20) NOT NULL,
  `note_shared_with_emails` varchar(500) NOT NULL,
  `created_date` date NOT NULL,
  `modified_date` date NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `notes`
--

INSERT INTO `notes` (`note_id`, `user_id`, `text`, `title`, `bg_color`, `pw_enabled`, `note_password`, `note_category`, `note_shared_with_emails`, `created_date`, `modified_date`) VALUES
(1, 1, 'Esta nota fue actualizada', 'el titulo tambien fue actualizado', '#FFFFFF', 0, '', '', 'ramon.ramirez@email.com', '2023-11-04', '2023-11-04'),
(2, 3, 'El texto de la nota va aqui3', 'Este seria el titulo de la nota3', '#000000', 1, 'p4ssw0rd', 'categoria de prueba', '', '2023-11-04', '2023-11-05'),
(3, 3, 'El texto de la nota va aqui3.1', 'Este seria el titulo de la nota3.1', '#FFFFFF', 0, '', '', 'ramon.ramirez@email.com,allan.alvarez@email.com', '2023-11-04', '2023-11-04'),
(5, 8, 'El texto de la nota va aqui 8', 'Este seria el titulo de la nota 8', '#FFFFFF', 0, '', '', 'allan.alvarez@email.com', '2023-11-04', '2023-11-04'),
(6, 8, 'El texto de la nota va aqui 8.1', 'Este seria el titulo de la nota 8.1', '#FFFFFF', 0, '', '', '', '2023-11-04', '2023-11-04'),
(7, 8, 'El texto de la nota va aqui 8.2', 'Este seria el titulo de la nota 8.2', '#FFFFFF', 0, '', '', '', '2023-11-04', '2023-11-04'),
(8, 8, 'El texto de la nota va aqui 8.3', 'Este seria el titulo de la nota 8.3', '#FFFFFF', 0, '', '', '', '2023-11-04', '2023-11-04');

-- --------------------------------------------------------

--
-- Table structure for table `users`
--

CREATE TABLE `users` (
  `user_id` int(4) NOT NULL,
  `nombre` varchar(20) NOT NULL,
  `apellido` varchar(20) NOT NULL,
  `password` varchar(20) NOT NULL,
  `email` varchar(50) NOT NULL,
  `created_date` date NOT NULL,
  `modified_date` date NOT NULL,
  `active` tinyint(1) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `users`
--

INSERT INTO `users` (`user_id`, `nombre`, `apellido`, `password`, `email`, `created_date`, `modified_date`, `active`) VALUES
(1, 'Hector', 'Hernandez', 'passwordAqui', 'hector.hernandez@email.com', '2023-11-03', '2023-11-03', 1),
(3, 'Javier', 'Jimenez', 'password', 'javier.jimenez@email.com', '2023-11-02', '2023-11-02', 1),
(4, 'Ramon', 'Ramirez', 'contrasena', 'ramon.ramirez@email.com', '2023-11-01', '2023-11-01', 1),
(8, 'Allan', 'Alvarez', 'passwordHere', 'allan.alvarez@email.com', '2023-11-03', '2023-11-03', 1);

--
-- Indexes for dumped tables
--

--
-- Indexes for table `notes`
--
ALTER TABLE `notes`
  ADD PRIMARY KEY (`note_id`);

--
-- Indexes for table `users`
--
ALTER TABLE `users`
  ADD PRIMARY KEY (`user_id`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `notes`
--
ALTER TABLE `notes`
  MODIFY `note_id` int(4) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=9;

--
-- AUTO_INCREMENT for table `users`
--
ALTER TABLE `users`
  MODIFY `user_id` int(4) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=9;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
