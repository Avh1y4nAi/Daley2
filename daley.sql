-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: May 06, 2025 at 05:42 AM
-- Server version: 10.4.32-MariaDB
-- PHP Version: 8.2.12

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `daley`
--

-- --------------------------------------------------------

--
-- Table structure for table `payments`
--

CREATE TABLE `payments` (
  `paymentID` int(11) NOT NULL,
  `paymentName` varchar(100) DEFAULT NULL,
  `paymentDate` date DEFAULT NULL,
  `paymentType` varchar(50) DEFAULT NULL,
  `paymentAmount` decimal(15,2) DEFAULT NULL,
  `paymentStatus` varchar(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `payments`
--

INSERT INTO `payments` (`paymentID`, `paymentName`, `paymentDate`, `paymentType`, `paymentAmount`, `paymentStatus`) VALUES
(301, 'Advance Payment', '2024-05-01', 'Credit Card', 3000000.00, 'Completed'),
(302, 'Full Payment', '2024-05-15', 'Bank Transfer', 9000000.00, 'Pending');

-- --------------------------------------------------------

--
-- Table structure for table `properties`
--

CREATE TABLE `properties` (
  `propertyID` int(11) NOT NULL,
  `propertyName` varchar(100) DEFAULT NULL,
  `propertyPrice` decimal(15,2) DEFAULT NULL,
  `propertyAddress` varchar(255) DEFAULT NULL,
  `propertySize` varchar(50) DEFAULT NULL,
  `propertyType` varchar(50) DEFAULT NULL,
  `propertyStatus` varchar(20) DEFAULT NULL,
  `propertySpecification` text DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `properties`
--

INSERT INTO `properties` (`propertyID`, `propertyName`, `propertyPrice`, `propertyAddress`, `propertySize`, `propertyType`, `propertyStatus`, `propertySpecification`) VALUES
(201, 'Ocean Villa', 15000000.00, 'Beach Road, Kathmandu', '2000 sqft', 'Villa', 'Available', 'Sea-facing, 3 BHK'),
(202, 'City Apartment', 9000000.00, 'Downtown, Lalitpur', '1200 sqft', 'Apartment', 'Sold', '2 BHK, Fully Furnished');

-- --------------------------------------------------------

--
-- Table structure for table `propertyimages`
--

CREATE TABLE `propertyimages` (
  `propertyID` int(11) NOT NULL,
  `propertyImageID` int(11) NOT NULL,
  `propertyImage` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `propertyimages`
--

INSERT INTO `propertyimages` (`propertyID`, `propertyImageID`, `propertyImage`) VALUES
(201, 1, 'oceanvilla1.jpg'),
(201, 2, 'oceanvilla2.jpg'),
(202, 1, 'cityapt1.jpg');

-- --------------------------------------------------------

--
-- Table structure for table `userpropertyimages`
--

CREATE TABLE `userpropertyimages` (
  `userID` int(11) NOT NULL,
  `propertyID` int(11) NOT NULL,
  `propertyImageID` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `userpropertyimages`
--

INSERT INTO `userpropertyimages` (`userID`, `propertyID`, `propertyImageID`) VALUES
(101, 201, 1),
(101, 201, 2),
(102, 202, 1);

-- --------------------------------------------------------

--
-- Table structure for table `userpropertypayments`
--

CREATE TABLE `userpropertypayments` (
  `userID` int(11) NOT NULL,
  `propertyID` int(11) NOT NULL,
  `paymentID` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `userpropertypayments`
--

INSERT INTO `userpropertypayments` (`userID`, `propertyID`, `paymentID`) VALUES
(101, 201, 301),
(102, 202, 302);

-- --------------------------------------------------------

--
-- Table structure for table `userpropertyreviews`
--

CREATE TABLE `userpropertyreviews` (
  `userID` int(11) NOT NULL,
  `propertyID` int(11) NOT NULL,
  `propertyRating` int(11) DEFAULT NULL,
  `propertyReview` text DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `userpropertyreviews`
--

INSERT INTO `userpropertyreviews` (`userID`, `propertyID`, `propertyRating`, `propertyReview`) VALUES
(101, 201, 5, 'Amazing property, loved the view!'),
(102, 202, 4, 'Good location but a bit pricey.');

-- --------------------------------------------------------

--
-- Table structure for table `users`
--

CREATE TABLE `users` (
  `userID` int(11) NOT NULL,
  `firstName` varchar(50) DEFAULT NULL,
  `lastName` varchar(50) DEFAULT NULL,
  `email` varchar(100) DEFAULT NULL,
  `contactNumber` varchar(20) DEFAULT NULL,
  `password` varchar(100) DEFAULT NULL,
  `adminID` int(11) DEFAULT NULL,
  `userrole` varchar(100) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `users`
--

INSERT INTO `users` (`userID`, `firstName`, `lastName`, `email`, `contactNumber`, `password`, `adminID`, `userrole`) VALUES
(101, 'Alice', 'Smith', 'alice@example.com', '9800001001', 'pass1', 1, ''),
(102, 'Bob', 'Johnson', 'bob@example.com', '9800001002', 'pass2', 2, '');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `payments`
--
ALTER TABLE `payments`
  ADD PRIMARY KEY (`paymentID`);

--
-- Indexes for table `properties`
--
ALTER TABLE `properties`
  ADD PRIMARY KEY (`propertyID`);

--
-- Indexes for table `propertyimages`
--
ALTER TABLE `propertyimages`
  ADD PRIMARY KEY (`propertyID`,`propertyImageID`);

--
-- Indexes for table `userpropertyimages`
--
ALTER TABLE `userpropertyimages`
  ADD PRIMARY KEY (`userID`,`propertyID`,`propertyImageID`),
  ADD KEY `propertyID` (`propertyID`);

--
-- Indexes for table `userpropertypayments`
--
ALTER TABLE `userpropertypayments`
  ADD PRIMARY KEY (`userID`,`propertyID`,`paymentID`),
  ADD KEY `paymentID` (`paymentID`),
  ADD KEY `propertyID` (`propertyID`);

--
-- Indexes for table `userpropertyreviews`
--
ALTER TABLE `userpropertyreviews`
  ADD PRIMARY KEY (`userID`,`propertyID`),
  ADD KEY `propertyID` (`propertyID`);

--
-- Indexes for table `users`
--
ALTER TABLE `users`
  ADD PRIMARY KEY (`userID`);

--
-- Constraints for dumped tables
--

--
-- Constraints for table `propertyimages`
--
ALTER TABLE `propertyimages`
  ADD CONSTRAINT `propertyimages_ibfk_1` FOREIGN KEY (`propertyID`) REFERENCES `properties` (`propertyID`);

--
-- Constraints for table `userpropertyimages`
--
ALTER TABLE `userpropertyimages`
  ADD CONSTRAINT `userpropertyimages_ibfk_1` FOREIGN KEY (`userID`) REFERENCES `users` (`userID`),
  ADD CONSTRAINT `userpropertyimages_ibfk_2` FOREIGN KEY (`propertyID`) REFERENCES `properties` (`propertyID`);

--
-- Constraints for table `userpropertypayments`
--
ALTER TABLE `userpropertypayments`
  ADD CONSTRAINT `userpropertypayments_ibfk_1` FOREIGN KEY (`paymentID`) REFERENCES `payments` (`paymentID`),
  ADD CONSTRAINT `userpropertypayments_ibfk_2` FOREIGN KEY (`userID`) REFERENCES `users` (`userID`),
  ADD CONSTRAINT `userpropertypayments_ibfk_3` FOREIGN KEY (`propertyID`) REFERENCES `properties` (`propertyID`);

--
-- Constraints for table `userpropertyreviews`
--
ALTER TABLE `userpropertyreviews`
  ADD CONSTRAINT `userpropertyreviews_ibfk_1` FOREIGN KEY (`userID`) REFERENCES `users` (`userID`),
  ADD CONSTRAINT `userpropertyreviews_ibfk_2` FOREIGN KEY (`propertyID`) REFERENCES `properties` (`propertyID`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
