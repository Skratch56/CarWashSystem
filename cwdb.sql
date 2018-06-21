-- phpMyAdmin SQL Dump
-- version 4.6.5.2
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Jun 21, 2018 at 08:07 PM
-- Server version: 10.1.21-MariaDB
-- PHP Version: 5.6.30

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `cwdb`
--

-- --------------------------------------------------------

--
-- Table structure for table `allocation`
--

CREATE TABLE `allocation` (
  `Service_Code` int(11) NOT NULL,
  `Equipment_ID` int(11) NOT NULL,
  `Description` varchar(30) NOT NULL,
  `qty` varchar(30) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `allocation`
--

INSERT INTO `allocation` (`Service_Code`, `Equipment_ID`, `Description`, `qty`) VALUES
(1, 1, 'asdfasdf', ''),
(2, 1, 'Cleaningthecar', ''),
(3, 1, 'Cleaning', ''),
(4, 4, 'asasd', '2'),
(4, 5, 'asasd', '2'),
(6, 4, 'asdasd', '2'),
(6, 5, 'asdasd', '2'),
(7, 4, 'asasdas', '2'),
(7, 5, 'asasdas', '2'),
(8, 4, 'asdasdasd', '20'),
(9, 4, 'asdasd', '10'),
(9, 5, 'asdasd', '10'),
(9, 6, 'asdasd', '200'),
(9, 7, 'asdasd', '200');

-- --------------------------------------------------------

--
-- Table structure for table `assignment`
--

CREATE TABLE `assignment` (
  `booking_no` int(11) NOT NULL,
  `employee_id` int(11) NOT NULL,
  `description` varchar(30) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `assignment`
--

INSERT INTO `assignment` (`booking_no`, `employee_id`, `description`) VALUES
(52, 1, NULL),
(52, 3, NULL),
(52, 4, NULL),
(53, 1, NULL),
(53, 3, NULL),
(53, 4, NULL);

-- --------------------------------------------------------

--
-- Table structure for table `booking`
--

CREATE TABLE `booking` (
  `booking_no` int(11) NOT NULL,
  `booking_date` varchar(30) NOT NULL,
  `total_amount` varchar(30) NOT NULL DEFAULT '0',
  `vehicle_reg_no` varchar(30) NOT NULL,
  `customer_id` int(11) NOT NULL,
  `employee_id` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `booking`
--

INSERT INTO `booking` (`booking_no`, `booking_date`, `total_amount`, `vehicle_reg_no`, `customer_id`, `employee_id`) VALUES
(5, '2018-05-11', '0', '14242312255511', 6, 1),
(6, '2018-05-11', '0', '14242312255511', 6, 1),
(7, '2018-05-11', '0', '14242312255511', 6, 1),
(8, '2018-05-15', '0', 'A 8724 GP', 13, 1),
(9, '2018-05-21', '0', 'GG78HHGP', 10, 1),
(10, '2018-05-21', '0', 'GG78HHGP', 6, 1),
(11, '2018-05-21', '0', 'GG78HHGP', 6, 1),
(12, '2018-05-21', '0', '8975 A', 17, 1),
(13, '2018-05-22', '0', '123456GP', 18, 1),
(14, '2018-05-23', '90', '8975 A', 11, 1),
(17, '2018-05-31', '0', 'TT 667 AB', 12, 1),
(18, '2018-05-31', '0', 'TT 667 AB', 6, 1),
(19, '2018-05-31', '0', '015 VWS', 6, 1),
(20, '2018-05-31', '90', 'TT 667 AB', 18, 3),
(21, '2018-05-31', '90', 'EDNA B', 12, 3),
(22, '2018-05-31', '95', 'TT 667 AB', 11, 1),
(23, '2018-06-02', '90', '8975 A', 12, 3),
(24, '2018-06-04', '480', '015 VWS', 11, 1),
(25, '2018-06-05', '95', 'AGG77GP', 16, 12),
(26, '2018-06-05', '0', 'TT 667 AB', 19, 12),
(27, '2018-06-05', '75', 'TT 667 AB', 29, 35),
(28, '2018-06-05', '75', '5589 DD', 16, 33),
(29, '2018-06-05', '0', '14242312255511', 12, 12),
(30, '2018-06-05', '450', '015 VWS', 11, 32),
(31, '2018-06-05', '75', '14242312255511', 15, 35),
(32, '2018-06-05', '110', '14242312255511', 6, 35),
(33, '2018-06-05', '80', 'TT 667 AB', 30, 31),
(34, '2018-06-05', '105', 'TT 667 AB', 32, 36),
(35, '2018-06-05', '155', 'EDNA B', 32, 12),
(36, '2018-06-06', '450', 'ZRW 174 GP', 33, 7),
(37, '2018-06-06', '90', '123456GP', 16, 32),
(38, '2018-06-06', '90', '123456GP', 15, 8),
(39, '2018-06-07', '495', '8888 ZZ GP', 34, 36),
(40, '2018-06-07', '90', '56H HH L', 35, 12),
(41, '2018-06-07', '90', 'TT 568 NW', 36, 8),
(42, '2018-06-07', '50', '5589 DD', 15, 7),
(43, '2018-06-07', '60', '14242312255511', 13, 32),
(44, '2018-06-08', '30', '123456GP', 6, 36),
(45, '2018-06-08', '155', 'AA22BBGP', 10, 35),
(52, '2018-06-13', '0', '123456GP', 6, 35),
(53, '2018-06-13', '0', '123456GP', 6, 35);

-- --------------------------------------------------------

--
-- Table structure for table `cashreceipt`
--

CREATE TABLE `cashreceipt` (
  `cashreceiptno` int(11) NOT NULL,
  `amountreceived` varchar(30) NOT NULL,
  `bookingno` int(11) NOT NULL,
  `employeeno` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `cashreceipt`
--

INSERT INTO `cashreceipt` (`cashreceiptno`, `amountreceived`, `bookingno`, `employeeno`) VALUES
(1, '200', 20, 3),
(2, '100', 21, 3),
(3, '500', 22, 3),
(4, '100', 23, 1),
(5, '600', 24, 1),
(6, '200', 25, 1),
(7, '100', 27, 1),
(8, '100', 27, 1),
(9, '200', 28, 1),
(10, '500', 30, 1),
(11, '200', 31, 1),
(12, '200', 31, 1),
(13, '400', 32, 1),
(14, '400', 32, 1),
(15, '400', 32, 1),
(16, '580', 33, 1),
(17, '580', 33, 1),
(18, '200', 34, 1),
(19, '200', 35, 1),
(20, '600', 36, 1),
(21, '600', 36, 1),
(22, '100', 37, 1),
(23, '100', 38, 1),
(24, '500', 39, 1),
(25, '100', 40, 1),
(26, '100', 41, 1),
(27, '100', 42, 1),
(28, '50', 44, 1),
(29, '200', 45, 1);

-- --------------------------------------------------------

--
-- Table structure for table `employee`
--

CREATE TABLE `employee` (
  `employee_id` int(11) NOT NULL,
  `firstname` varchar(50) NOT NULL,
  `surname` varchar(50) NOT NULL,
  `empType` varchar(30) NOT NULL,
  `dob` date DEFAULT NULL,
  `id` varchar(13) DEFAULT NULL,
  `passport` varchar(14) DEFAULT NULL,
  `gender` varchar(10) NOT NULL,
  `cellphone` varchar(13) DEFAULT NULL,
  `email` varchar(80) DEFAULT NULL,
  `salary` double DEFAULT NULL,
  `house_number` int(11) DEFAULT NULL,
  `street_name` varchar(100) DEFAULT NULL,
  `city` varchar(50) DEFAULT NULL,
  `postal_code` varchar(5) DEFAULT NULL,
  `username` varchar(50) DEFAULT NULL,
  `password` varchar(32) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `employee`
--

INSERT INTO `employee` (`employee_id`, `firstname`, `surname`, `empType`, `dob`, `id`, `passport`, `gender`, `cellphone`, `email`, `salary`, `house_number`, `street_name`, `city`, `postal_code`, `username`, `password`) VALUES
(1, 'Greg', 'Corner', 'Manager', '1994-04-10', '9404105692089', 'none', 'Male', '0820431141', 'skratch56@gmail.com', 15000, 2, 'General Froneman', 'Vanderbiljpark', '1900', 'teamabc', '774337'),
(3, 'Trey', 'Kotze', 'Cashier', '1996-05-31', '9605315692089', '7743370', 'Male', '0820431141', 'skratch5@gmail.com', 15000, 2, 'general', 'Johannesburg', '1900', 'trey11', '7743370'),
(4, 'Gregory', 'Voorster', 'Cashier', '1980-06-18', '6809095596087', 'malwaregreg', 'Male', '0762800206', 'gregory89@bbox.co.za', 8000, 56, 'piet retief', 'Germiston', '1989', 'greg89', 'malwaregreg'),
(5, 'Gretta', 'Souami', 'Cashier', '1985-06-13', 'none', 'gretta1985', 'Female', '0720226967', 'grettas@gmail.com', 8000, 25, 'de villiers', 'Johannesburg', '1988', 'mrsG', 'gretta1985'),
(6, 'Hensley', 'Ernandez', 'Washer', '1986-04-08', '4563217895', 'hensley1986', 'Male', '0799478569', 'Hernandez@yahoo.com', 5000, 45, 'albertina sisulu', 'Johannesburg', '1921', 'hensley', 'hensley1986'),
(7, 'Petunia', 'Mbalogani', 'Washer', '1994-10-05', '3896987410', 'petunia1994', 'Female', '0719031971', 'petunia@gmail.com', 5000, 17, 'susman', 'Mshongo', '1845', 'petunia', 'petunia1994'),
(8, 'Stella', 'McArthur', 'Washer', '1984-02-14', '5641233214', 'stella1984', 'Female', '0749077166', 'none', 5000, 13, 'president steyn', 'Bophelong', '2130', 'Stella', 'stella1984'),
(12, 'Themba', 'Senokwane', 'Washer', '1998-05-01', '8748745698', '', 'Male', '0813806517', '', 5000, 36, 'melbourne', 'Rayton', '2158', '', ''),
(26, 'Paseka', ' Letsie', 'Washer', '1989-12-15', '4569874321', 'not applicable', 'Male', '0665679667', 'letsie@gmail.com', 5000, 34, 'frikkie meyer', 'Vanderbijlpark', '1911', 'letsie', 'yori'),
(31, 'Ingrid', 'Masekela', 'Washer', '1995-06-04', '547932540216', '16gtre4532189', 'Female', '0639505495', 'maseki@gmail.com', 5000, 47, 'hoffman', 'Vereeniging', '1970', 'ingrid', 'maseki1995'),
(32, 'Ashton', 'Grey', 'Washer', '1994-12-25', '5874321495', '000000000', 'Male', '08111410430', 'ashton@gmail.com', 5000, 12, 'james cook', 'Vanderbijlpark', '1911', 'ashton', 'grey1994'),
(33, 'Andy', 'Obiang', 'Washer', '1988-02-08', '3258987463', '00000000000', 'Male', '0747166904', 'obiang@yahoo.com', 5000, 57, 'steyn', 'Vanderbijlpark', '1911', 'andy23', 'andy5432'),
(35, 'Robert', 'Thato', 'Washer', '1983-06-27', '4587954325369', '000000', 'Male', '0783559997', 'thato@hotmail.com', 5000, 14, 'kruger', 'Boipatong', '1911', 'tharo', 'robert88'),
(36, 'Andre', 'Pistorius', 'Washer', '1984-06-29', '458700365741', '0000', 'Male', '0639872525', 'pistorius29@gmail.com', 5000, 65, 'christaan de wet', 'Vanderbijlpark', '1911', 'pistorius', 'andre1984');

-- --------------------------------------------------------

--
-- Table structure for table `employeebooking`
--

CREATE TABLE `employeebooking` (
  `booking_no` int(11) NOT NULL,
  `employee_id` int(11) NOT NULL,
  `description` varchar(30) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `equipment`
--

CREATE TABLE `equipment` (
  `equipment_id` int(11) NOT NULL,
  `equip_description` varchar(30) NOT NULL,
  `equipment_type` varchar(30) NOT NULL,
  `qtyinstock` varchar(30) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `equipment`
--

INSERT INTO `equipment` (`equipment_id`, `equip_description`, `equipment_type`, `qtyinstock`) VALUES
(4, 'Washing powder for luxury cars', ' scented detergent', '28'),
(5, 'used for regular wash', 'sponge', '28'),
(6, 'used to clean interior', 'vacuum', '30'),
(7, 'used for all services', 'spray gun', '30'),
(8, 'have poly bristles', 'brush', '30'),
(9, 'engine degreaser', 'biodegradable detergent', '30'),
(10, 'cleans, shines and wax', 'splash and dash sponge', '30'),
(11, 'used for all services', 'bucket', '30'),
(12, 'used to clean exterior', 'detergent', '30'),
(13, 'used to perfume after cleaning', 'air freshener scent', '30'),
(14, 'to polish tyres after cleaning', 'tyre polish', '30');

-- --------------------------------------------------------

--
-- Table structure for table `ownership`
--

CREATE TABLE `ownership` (
  `vehicle_reg_no` varchar(15) NOT NULL,
  `customer_id` int(11) NOT NULL,
  `ownership_type` varchar(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `ownership`
--

INSERT INTO `ownership` (`vehicle_reg_no`, `customer_id`, `ownership_type`) VALUES
('015 VWS', 6, 'First owner'),
('015 VWS', 11, 'Second owner'),
('123456GP', 6, 'Second owner'),
('123456GP', 11, 'First owner'),
('123456GP', 15, 'Second owner'),
('123456GP', 16, 'First owner'),
('123456GP', 18, 'First owner'),
('14242312255511', 6, 'First owner'),
('14242312255511', 12, 'First owner'),
('14242312255511', 13, 'Second owner'),
('14242312255511', 15, 'Second owner'),
('5589 DD', 15, 'Second owner'),
('5589 DD', 16, 'Second owner'),
('56H HH L', 35, 'Second owner'),
('8888 ZZ GP', 34, 'First owner'),
('8975 A', 11, 'First owner'),
('8975 A', 12, 'First owner'),
('8975 A', 17, 'Second owner'),
('A 8724 GP', 13, 'First owner'),
('AA22BBGP', 10, 'First owner'),
('AGG77GP', 16, 'Second owner'),
('EDNA B', 12, 'First owner'),
('EDNA B', 32, 'First owner'),
('GG78HHGP', 6, 'Second owner'),
('GG78HHGP', 10, 'Second owner'),
('TT 568 NW', 36, 'First owner'),
('TT 667 AB', 6, 'First owner'),
('TT 667 AB', 10, 'First owner'),
('TT 667 AB', 11, 'Second owner'),
('TT 667 AB', 12, 'First owner'),
('TT 667 AB', 18, 'First owner'),
('TT 667 AB', 19, 'First owner'),
('TT 667 AB', 24, 'First owner'),
('TT 667 AB', 29, 'Second owner'),
('TT 667 AB', 30, 'First owner'),
('TT 667 AB', 32, 'First owner'),
('tt h88', 12, 'First owner'),
('ZRW 174 GP', 33, 'First owner');

-- --------------------------------------------------------

--
-- Table structure for table `performance`
--

CREATE TABLE `performance` (
  `Service_Code` int(11) NOT NULL,
  `Booking_No` int(11) NOT NULL,
  `Description` varchar(30) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `performance`
--

INSERT INTO `performance` (`Service_Code`, `Booking_No`, `Description`) VALUES
(2, 5, 'asdfasdf'),
(2, 11, 'Car Wash'),
(2, 12, 'regular customer'),
(2, 14, 'clean and dry'),
(2, 15, 'Regular customer'),
(3, 11, 'Car Wash'),
(3, 12, 'regular customer'),
(4, 16, 'returning customer'),
(4, 20, 'THerer'),
(4, 23, 'sdfghjkl'),
(4, 24, 'uuuuuuuuuu'),
(4, 27, 'fast and furious'),
(4, 28, 'booking completed'),
(4, 31, 'ggggg'),
(4, 33, 'bronze package'),
(4, 37, 'Bronze Package'),
(4, 40, 'bronze'),
(4, 41, 'bronze package'),
(4, 44, 'Bronze'),
(5, 32, 'silver package'),
(5, 34, 'bronze'),
(5, 35, 'Silver Package'),
(5, 37, 'Bronze Package'),
(5, 40, 'bronze'),
(5, 41, 'bronze package'),
(5, 43, 'bronze'),
(5, 45, 'silver package'),
(6, 21, 'New'),
(6, 25, 'BOOKING APPROVED'),
(6, 34, 'bronze'),
(6, 38, 'Bronze Package'),
(6, 45, 'silver package'),
(7, 22, 'new cust'),
(7, 27, 'fast and furious'),
(7, 28, 'booking completed'),
(7, 31, 'ggggg'),
(7, 35, 'Silver Package'),
(7, 38, 'Bronze Package'),
(7, 39, 'Gold package'),
(8, 29, 'Wash my car'),
(8, 30, 'hhhhhh'),
(8, 36, 'Gold package'),
(8, 39, 'Gold package'),
(9, 25, 'BOOKING APPROVED'),
(9, 29, 'Wash my car'),
(9, 32, 'silver package'),
(9, 33, 'bronze package'),
(9, 35, 'Silver Package'),
(9, 42, 'bronze package'),
(9, 45, 'silver package');

-- --------------------------------------------------------

--
-- Table structure for table `service`
--

CREATE TABLE `service` (
  `Service_Code` int(11) NOT NULL,
  `Service_Type` varchar(30) NOT NULL,
  `Service_Cost` decimal(10,0) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `service`
--

INSERT INTO `service` (`Service_Code`, `Service_Type`, `Service_Cost`) VALUES
(4, 'Wash And Go', '30'),
(5, 'full wash', '60'),
(6, 'Wash and dry', '45'),
(7, 'Dash and Vacuum', '45'),
(8, 'Valet', '450'),
(9, 'Engine wash', '50');

-- --------------------------------------------------------

--
-- Table structure for table `tblcustomer`
--

CREATE TABLE `tblcustomer` (
  `customer_id` int(11) NOT NULL,
  `firstname` varchar(50) NOT NULL,
  `surname` varchar(50) NOT NULL,
  `gender` varchar(10) NOT NULL,
  `cellphone_number` varchar(13) DEFAULT NULL,
  `house_no` int(11) DEFAULT NULL,
  `street` varchar(100) DEFAULT NULL,
  `city` varchar(100) DEFAULT NULL,
  `zip` varchar(5) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `tblcustomer`
--

INSERT INTO `tblcustomer` (`customer_id`, `firstname`, `surname`, `gender`, `cellphone_number`, `house_no`, `street`, `city`, `zip`) VALUES
(6, 'Trey', 'Maluka', 'Male', '0820431141', 2, 'Froneman', 'East London', '27'),
(10, 'Thubalethu', 'Mambane', 'Male', '0820431100', 22, 'general', 'Cape Town', '27'),
(11, 'Joseph', 'Ariyibi', 'Male', '0840114566', 27, 'Andries', 'East London', '27'),
(12, 'Thabang', 'Senokwane', 'Male', '0824414566', 5, 'Andries', 'Cape Town', '27'),
(13, 'Patrick', 'Mabunda', 'Male', '0812245689', 21, 'Andries', 'East London', '27'),
(15, 'Thubalethu', 'Maria', 'Male', '0712260004', 12, 'Fred', 'Cape Town', '27'),
(16, 'Trey', 'Trailo', 'Male', '0785542266', 27, 'Dian', 'Cape Town', '27'),
(17, 'posha', 'william', 'Male', '089365479', 56, 'gilbert', 'Bloemfontein', '27'),
(29, 'Huli', 'Bandra', 'Female', '0745747474', 20, 'Sandra', 'Johannesburg', '4000'),
(30, 'Estelle', 'Thibault', 'Female', '0848352664', 56, 'Kruger', 'Alexandra', '27'),
(31, 'Alida', 'thabo', 'Male', '0789635874', 54, 'froneman', 'Alexandra', '27'),
(32, 'Sofia', 'Nku', 'Female', '0729987702', 23, 'KrugerDrive', 'Lenasia', '27'),
(33, 'Patricia', 'Mokoena', 'Female', '0790226963', 45, 'Marlboro', 'Roodepoort', '27'),
(34, 'Andrea', 'Porter', 'Female', '0739348798', 87, 'Susman', 'Soweto', '2198'),
(35, 'Amelia', 'Brown', 'Female', '0799635689', 51, 'portman', 'Vereeniging', '27'),
(36, 'Andrew', 'Tchonkeu', 'Male', '0780782144', 2, 'froneman', 'Alexandra', '27');

-- --------------------------------------------------------

--
-- Table structure for table `vehicle`
--

CREATE TABLE `vehicle` (
  `vehicle_reg_no` varchar(15) NOT NULL,
  `brand` varchar(50) NOT NULL,
  `vehicle_type` varchar(50) DEFAULT NULL,
  `color` varchar(30) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `vehicle`
--

INSERT INTO `vehicle` (`vehicle_reg_no`, `brand`, `vehicle_type`, `color`) VALUES
('123456GP', 'Jeep', 'hatchback', 'blue'),
('14242312255511', 'Lexus', 'Sedan', 'Blue'),
('5589 DD', 'volkswaggen', 'Bakkie', 'red'),
('5698_56', 'toyota', 'sedan', 'red'),
('56H HH L', 'Toyota', 'Double Cab Bakkie', 'blue'),
('675 AA', 'BMW', 'Avanza', 'blue'),
('8888 ZZ GP', 'BMW', 'x5', 'Red'),
('8975 A', 'bmw', 'sedan', 'red'),
('A 8724 GP', 'VolksWagen', 'HatchBack', 'Grey'),
('AA B5 GP', 'Toyota', 'Normal Car', 'Blue'),
('AA22BBGP', 'AlphaRomeo', 'Sedan', 'Black'),
('adsfasdf123123', 'Lexus', 'Sedan', 'Black'),
('AGG77GP', 'Toyota', 'Normal Car', 'Blue'),
('BB45BBMP', 'Toyota', 'HatchBack', 'Red'),
('BBII22GP', 'Lexus', 'Bucky', 'Blue'),
('BVW 015 GP', 'TOYOTA', 'Sedan', 'BLUE'),
('DSM 032 NW', 'Volkswagen', 'Bakkie', 'Black'),
('EDNA B', 'VolksWagen', 'HatchBack', 'Black'),
('EY 6390 J', 'Ford', '4x4', 'silver'),
('GG78HHGP', 'Datsun', 'Sedan', 'Blue'),
('HH100', 'BMW', 'Avanza', 'RED'),
('JH900', 'BMW', 'Avanza', 'BLUE'),
('MOT 478 GP', 'Toyota', 'Bakkie', 'Grey'),
('TT 568 NW', 'BMW', 'x5', 'silver'),
('TT 667 AB', 'BMW', 'SEDAN', 'blue'),
('tt h88', 'quantum', '4x4', 'white'),
('ZRW 174 GP', 'Mahindra', 'Bakkie', 'Green');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `allocation`
--
ALTER TABLE `allocation`
  ADD PRIMARY KEY (`Service_Code`,`Equipment_ID`);

--
-- Indexes for table `assignment`
--
ALTER TABLE `assignment`
  ADD PRIMARY KEY (`booking_no`,`employee_id`);

--
-- Indexes for table `booking`
--
ALTER TABLE `booking`
  ADD PRIMARY KEY (`booking_no`);

--
-- Indexes for table `cashreceipt`
--
ALTER TABLE `cashreceipt`
  ADD PRIMARY KEY (`cashreceiptno`);

--
-- Indexes for table `employee`
--
ALTER TABLE `employee`
  ADD PRIMARY KEY (`employee_id`),
  ADD UNIQUE KEY `id` (`id`),
  ADD UNIQUE KEY `passport` (`passport`),
  ADD UNIQUE KEY `email` (`email`),
  ADD UNIQUE KEY `username` (`username`);

--
-- Indexes for table `employeebooking`
--
ALTER TABLE `employeebooking`
  ADD PRIMARY KEY (`booking_no`,`employee_id`);

--
-- Indexes for table `equipment`
--
ALTER TABLE `equipment`
  ADD PRIMARY KEY (`equipment_id`);

--
-- Indexes for table `ownership`
--
ALTER TABLE `ownership`
  ADD PRIMARY KEY (`vehicle_reg_no`,`customer_id`);

--
-- Indexes for table `performance`
--
ALTER TABLE `performance`
  ADD PRIMARY KEY (`Service_Code`,`Booking_No`);

--
-- Indexes for table `service`
--
ALTER TABLE `service`
  ADD PRIMARY KEY (`Service_Code`);

--
-- Indexes for table `tblcustomer`
--
ALTER TABLE `tblcustomer`
  ADD PRIMARY KEY (`customer_id`),
  ADD UNIQUE KEY `cellphone_number` (`cellphone_number`);

--
-- Indexes for table `vehicle`
--
ALTER TABLE `vehicle`
  ADD PRIMARY KEY (`vehicle_reg_no`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `booking`
--
ALTER TABLE `booking`
  MODIFY `booking_no` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=54;
--
-- AUTO_INCREMENT for table `cashreceipt`
--
ALTER TABLE `cashreceipt`
  MODIFY `cashreceiptno` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=30;
--
-- AUTO_INCREMENT for table `employee`
--
ALTER TABLE `employee`
  MODIFY `employee_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=37;
--
-- AUTO_INCREMENT for table `equipment`
--
ALTER TABLE `equipment`
  MODIFY `equipment_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=15;
--
-- AUTO_INCREMENT for table `service`
--
ALTER TABLE `service`
  MODIFY `Service_Code` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=10;
--
-- AUTO_INCREMENT for table `tblcustomer`
--
ALTER TABLE `tblcustomer`
  MODIFY `customer_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=37;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
