-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Jun 29, 2025 at 12:30 PM
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
-- Database: `db_train_ticket_system`
--

-- --------------------------------------------------------

--
-- Table structure for table `carriage_list`
--

CREATE TABLE `carriage_list` (
  `carriage_list_id` int(11) NOT NULL,
  `compartment_id` int(11) NOT NULL,
  `trip_id` int(11) NOT NULL,
  `stt` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `carriage_list`
--

INSERT INTO `carriage_list` (`carriage_list_id`, `compartment_id`, `trip_id`, `stt`) VALUES
(73, 1, 19, 1),
(74, 1, 19, 2),
(75, 2, 19, 3),
(76, 2, 19, 4),
(77, 3, 19, 5),
(78, 1, 20, 1),
(79, 1, 20, 2),
(80, 2, 20, 3),
(81, 2, 20, 4),
(82, 3, 20, 5),
(83, 1, 21, 1),
(84, 1, 21, 2),
(85, 1, 21, 3),
(86, 2, 21, 4),
(87, 2, 21, 5),
(88, 3, 21, 6),
(89, 1, 22, 1),
(90, 2, 22, 2),
(91, 2, 22, 3),
(92, 3, 22, 4),
(93, 3, 22, 5),
(94, 3, 22, 6),
(95, 1, 23, 1),
(96, 1, 23, 2),
(97, 2, 23, 3),
(98, 2, 23, 4),
(99, 3, 23, 5),
(100, 3, 23, 6),
(101, 1, 24, 1),
(102, 1, 24, 2),
(103, 2, 24, 3),
(104, 2, 24, 4),
(105, 3, 24, 5),
(106, 3, 24, 6);

-- --------------------------------------------------------

--
-- Table structure for table `compartment`
--

CREATE TABLE `compartment` (
  `compartment_id` int(11) NOT NULL,
  `compartment_name` varchar(255) NOT NULL,
  `class_factor` decimal(38,2) NOT NULL,
  `seat_count` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `compartment`
--

INSERT INTO `compartment` (`compartment_id`, `compartment_name`, `class_factor`, `seat_count`) VALUES
(1, 'Ngồi mềm điều hòa', 1.00, 32),
(2, 'Giường nằm khoang 6 điều hòa', 1.20, 30),
(3, 'Giường nằm khoang 4 điều hòa', 1.40, 20);

-- --------------------------------------------------------

--
-- Table structure for table `customer`
--

CREATE TABLE `customer` (
  `customer_id` int(11) NOT NULL,
  `email` varchar(255) NOT NULL,
  `phone` varchar(255) NOT NULL,
  `cccd` varchar(255) NOT NULL,
  `fullname` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `customer`
--

INSERT INTO `customer` (`customer_id`, `email`, `phone`, `cccd`, `fullname`) VALUES
(9, 'hoang@example.com', '0987654321', '038909885091', 'Trịnh Huy Hoàng'),
(10, 'huy290623@gmail.com', '0987654321', '038909885091', 'Trịnh Huy Hoàng'),
(11, 'huy290623@gmail.com', '0987654321', '038909885091', 'Trịnh Huy Hoàng'),
(12, 'huy290623@gmail.com', '0987654321', '038909885091', 'Trịnh Huy Hoàng');

-- --------------------------------------------------------

--
-- Table structure for table `passenger`
--

CREATE TABLE `passenger` (
  `passenger_id` int(11) NOT NULL,
  `fullname` varchar(255) NOT NULL,
  `cccd` varchar(255) NOT NULL,
  `dob` datetime(6) NOT NULL,
  `ticket_type_id` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `passenger`
--

INSERT INTO `passenger` (`passenger_id`, `fullname`, `cccd`, `dob`, `ticket_type_id`) VALUES
(11, 'Trịnh Huy Hoàng', '038909885091', '2025-04-14 10:27:04.000000', 4),
(12, 'Trịnh Huy Hoàng', '038909885091', '2025-04-15 13:59:46.000000', 4),
(13, 'Trịnh Huy Hoàng', '038909885091', '2025-04-15 14:04:05.000000', 4),
(14, 'Trịnh Huy Hoàng', '038909885091', '2025-04-15 14:04:27.000000', 4);

-- --------------------------------------------------------

--
-- Table structure for table `reservation_code`
--

CREATE TABLE `reservation_code` (
  `reservation_code_id` int(11) NOT NULL,
  `total_amount` decimal(38,2) NOT NULL,
  `created_at` datetime(6) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `reservation_code`
--

INSERT INTO `reservation_code` (`reservation_code_id`, `total_amount`, `created_at`) VALUES
(7, 742500.00, '2025-04-14 10:27:04.000000'),
(8, 742500.00, '2025-04-15 13:59:46.000000'),
(9, 742500.00, '2025-04-15 14:04:05.000000'),
(10, 742500.00, '2025-04-15 14:04:27.000000');

-- --------------------------------------------------------

--
-- Table structure for table `route`
--

CREATE TABLE `route` (
  `route_id` int(11) NOT NULL,
  `route_name` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `route`
--

INSERT INTO `route` (`route_id`, `route_name`) VALUES
(1, 'Hà Nội - Sài Gòn'),
(2, 'Sài Gòn - Hà Nội');

-- --------------------------------------------------------

--
-- Table structure for table `seat`
--

CREATE TABLE `seat` (
  `seat_id` int(11) NOT NULL,
  `seat_number` varchar(255) NOT NULL,
  `floor` int(11) NOT NULL DEFAULT 1,
  `seat_factor` decimal(38,2) NOT NULL,
  `carriage_list_id` int(11) NOT NULL,
  `seat_status` varchar(255) NOT NULL DEFAULT 'AVAILABLE'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `seat`
--

INSERT INTO `seat` (`seat_id`, `seat_number`, `floor`, `seat_factor`, `carriage_list_id`, `seat_status`) VALUES
(1483, '1A', 1, 1.00, 73, 'Available'),
(1484, '1B', 1, 1.00, 73, 'Available'),
(1485, '1C', 1, 1.00, 73, 'Available'),
(1486, '1D', 1, 1.00, 73, 'Available'),
(1487, '2A', 1, 1.00, 73, 'Available'),
(1488, '2B', 1, 1.00, 73, 'Available'),
(1489, '2C', 1, 1.00, 73, 'Available'),
(1490, '2D', 1, 1.00, 73, 'Available'),
(1491, '3A', 1, 1.00, 73, 'Available'),
(1492, '3B', 1, 1.00, 73, 'Available'),
(1493, '3C', 1, 1.00, 73, 'Available'),
(1494, '3D', 1, 1.00, 73, 'Available'),
(1495, '4A', 1, 1.00, 73, 'Available'),
(1496, '4B', 1, 1.00, 73, 'Available'),
(1497, '4C', 1, 1.00, 73, 'Available'),
(1498, '4D', 1, 1.00, 73, 'Available'),
(1499, '5A', 1, 1.00, 73, 'Available'),
(1500, '5B', 1, 1.00, 73, 'Available'),
(1501, '5C', 1, 1.00, 73, 'Available'),
(1502, '5D', 1, 1.00, 73, 'Available'),
(1503, '6A', 1, 1.00, 73, 'Available'),
(1504, '6B', 1, 1.00, 73, 'Available'),
(1505, '6C', 1, 1.00, 73, 'Available'),
(1506, '6D', 1, 1.00, 73, 'Available'),
(1507, '7A', 1, 1.00, 73, 'Available'),
(1508, '7B', 1, 1.00, 73, 'Available'),
(1509, '7C', 1, 1.00, 73, 'Available'),
(1510, '7D', 1, 1.00, 73, 'Available'),
(1511, '8A', 1, 1.00, 73, 'Available'),
(1512, '8B', 1, 1.00, 73, 'Available'),
(1513, '8C', 1, 1.00, 73, 'Available'),
(1514, '8D', 1, 1.00, 73, 'Available'),
(1515, '1A', 1, 1.00, 74, 'Available'),
(1516, '1B', 1, 1.00, 74, 'Available'),
(1517, '1C', 1, 1.00, 74, 'Available'),
(1518, '1D', 1, 1.00, 74, 'Available'),
(1519, '2A', 1, 1.00, 74, 'Available'),
(1520, '2B', 1, 1.00, 74, 'Available'),
(1521, '2C', 1, 1.00, 74, 'Available'),
(1522, '2D', 1, 1.00, 74, 'Available'),
(1523, '3A', 1, 1.00, 74, 'Available'),
(1524, '3B', 1, 1.00, 74, 'Available'),
(1525, '3C', 1, 1.00, 74, 'Available'),
(1526, '3D', 1, 1.00, 74, 'Available'),
(1527, '4A', 1, 1.00, 74, 'Available'),
(1528, '4B', 1, 1.00, 74, 'Available'),
(1529, '4C', 1, 1.00, 74, 'Available'),
(1530, '4D', 1, 1.00, 74, 'Available'),
(1531, '5A', 1, 1.00, 74, 'Available'),
(1532, '5B', 1, 1.00, 74, 'Available'),
(1533, '5C', 1, 1.00, 74, 'Available'),
(1534, '5D', 1, 1.00, 74, 'Available'),
(1535, '6A', 1, 1.00, 74, 'Available'),
(1536, '6B', 1, 1.00, 74, 'Available'),
(1537, '6C', 1, 1.00, 74, 'Available'),
(1538, '6D', 1, 1.00, 74, 'Available'),
(1539, '7A', 1, 1.00, 74, 'Available'),
(1540, '7B', 1, 1.00, 74, 'Available'),
(1541, '7C', 1, 1.00, 74, 'Available'),
(1542, '7D', 1, 1.00, 74, 'Available'),
(1543, '8A', 1, 1.00, 74, 'Available'),
(1544, '8B', 1, 1.00, 74, 'Available'),
(1545, '8C', 1, 1.00, 74, 'Available'),
(1546, '8D', 1, 1.00, 74, 'Available'),
(1547, '1A', 1, 1.00, 75, 'Available'),
(1548, '1B', 2, 1.20, 75, 'Available'),
(1549, '1C', 3, 1.40, 75, 'Available'),
(1550, '2A', 1, 1.00, 75, 'Available'),
(1551, '2B', 2, 1.20, 75, 'Available'),
(1552, '2C', 3, 1.40, 75, 'Available'),
(1553, '3A', 1, 1.00, 75, 'Available'),
(1554, '3B', 2, 1.20, 75, 'Available'),
(1555, '3C', 3, 1.40, 75, 'Available'),
(1556, '4A', 1, 1.00, 75, 'Available'),
(1557, '4B', 2, 1.20, 75, 'Available'),
(1558, '4C', 3, 1.40, 75, 'Available'),
(1559, '5A', 1, 1.00, 75, 'Available'),
(1560, '5B', 2, 1.20, 75, 'Available'),
(1561, '5C', 3, 1.40, 75, 'Available'),
(1562, '6A', 1, 1.00, 75, 'Available'),
(1563, '6B', 2, 1.20, 75, 'Available'),
(1564, '6C', 3, 1.40, 75, 'Available'),
(1565, '7A', 1, 1.00, 75, 'Available'),
(1566, '7B', 2, 1.20, 75, 'Available'),
(1567, '7C', 3, 1.40, 75, 'Available'),
(1568, '8A', 1, 1.00, 75, 'Available'),
(1569, '8B', 2, 1.20, 75, 'Available'),
(1570, '8C', 3, 1.40, 75, 'Available'),
(1571, '9A', 1, 1.00, 75, 'Available'),
(1572, '9B', 2, 1.20, 75, 'Available'),
(1573, '9C', 3, 1.40, 75, 'Available'),
(1574, '10A', 1, 1.00, 75, 'Available'),
(1575, '10B', 2, 1.20, 75, 'Available'),
(1576, '10C', 3, 1.40, 75, 'Available'),
(1577, '1A', 1, 1.00, 76, 'Available'),
(1578, '1B', 2, 1.20, 76, 'Available'),
(1579, '1C', 3, 1.40, 76, 'Available'),
(1580, '2A', 1, 1.00, 76, 'Available'),
(1581, '2B', 2, 1.20, 76, 'Available'),
(1582, '2C', 3, 1.40, 76, 'Available'),
(1583, '3A', 1, 1.00, 76, 'Available'),
(1584, '3B', 2, 1.20, 76, 'Available'),
(1585, '3C', 3, 1.40, 76, 'Available'),
(1586, '4A', 1, 1.00, 76, 'Available'),
(1587, '4B', 2, 1.20, 76, 'Available'),
(1588, '4C', 3, 1.40, 76, 'Available'),
(1589, '5A', 1, 1.00, 76, 'Available'),
(1590, '5B', 2, 1.20, 76, 'Available'),
(1591, '5C', 3, 1.40, 76, 'Available'),
(1592, '6A', 1, 1.00, 76, 'Available'),
(1593, '6B', 2, 1.20, 76, 'Available'),
(1594, '6C', 3, 1.40, 76, 'Available'),
(1595, '7A', 1, 1.00, 76, 'Available'),
(1596, '7B', 2, 1.20, 76, 'Available'),
(1597, '7C', 3, 1.40, 76, 'Available'),
(1598, '8A', 1, 1.00, 76, 'Available'),
(1599, '8B', 2, 1.20, 76, 'Available'),
(1600, '8C', 3, 1.40, 76, 'Available'),
(1601, '9A', 1, 1.00, 76, 'Available'),
(1602, '9B', 2, 1.20, 76, 'Available'),
(1603, '9C', 3, 1.40, 76, 'Available'),
(1604, '10A', 1, 1.00, 76, 'Available'),
(1605, '10B', 2, 1.20, 76, 'Available'),
(1606, '10C', 3, 1.40, 76, 'Available'),
(1607, '1A', 1, 1.00, 77, 'Available'),
(1608, '1B', 2, 1.20, 77, 'Available'),
(1609, '2A', 1, 1.00, 77, 'Available'),
(1610, '2B', 2, 1.20, 77, 'Available'),
(1611, '3A', 1, 1.00, 77, 'Available'),
(1612, '3B', 2, 1.20, 77, 'Available'),
(1613, '4A', 1, 1.00, 77, 'Available'),
(1614, '4B', 2, 1.20, 77, 'Available'),
(1615, '5A', 1, 1.00, 77, 'Available'),
(1616, '5B', 2, 1.20, 77, 'Available'),
(1617, '6A', 1, 1.00, 77, 'Available'),
(1618, '6B', 2, 1.20, 77, 'Available'),
(1619, '7A', 1, 1.00, 77, 'Available'),
(1620, '7B', 2, 1.20, 77, 'Available'),
(1621, '8A', 1, 1.00, 77, 'Available'),
(1622, '8B', 2, 1.20, 77, 'Available'),
(1623, '9A', 1, 1.00, 77, 'Available'),
(1624, '9B', 2, 1.20, 77, 'Available'),
(1625, '10A', 1, 1.00, 77, 'Available'),
(1626, '10B', 2, 1.20, 77, 'Available'),
(1627, '1A', 1, 1.00, 78, 'Available'),
(1628, '1B', 1, 1.00, 78, 'Available'),
(1629, '1C', 1, 1.00, 78, 'Available'),
(1630, '1D', 1, 1.00, 78, 'Available'),
(1631, '2A', 1, 1.00, 78, 'Available'),
(1632, '2B', 1, 1.00, 78, 'Available'),
(1633, '2C', 1, 1.00, 78, 'Available'),
(1634, '2D', 1, 1.00, 78, 'Available'),
(1635, '3A', 1, 1.00, 78, 'Available'),
(1636, '3B', 1, 1.00, 78, 'Available'),
(1637, '3C', 1, 1.00, 78, 'Available'),
(1638, '3D', 1, 1.00, 78, 'Available'),
(1639, '4A', 1, 1.00, 78, 'Available'),
(1640, '4B', 1, 1.00, 78, 'Available'),
(1641, '4C', 1, 1.00, 78, 'Available'),
(1642, '4D', 1, 1.00, 78, 'Available'),
(1643, '5A', 1, 1.00, 78, 'Available'),
(1644, '5B', 1, 1.00, 78, 'Available'),
(1645, '5C', 1, 1.00, 78, 'Available'),
(1646, '5D', 1, 1.00, 78, 'Available'),
(1647, '6A', 1, 1.00, 78, 'Available'),
(1648, '6B', 1, 1.00, 78, 'Available'),
(1649, '6C', 1, 1.00, 78, 'Available'),
(1650, '6D', 1, 1.00, 78, 'Available'),
(1651, '7A', 1, 1.00, 78, 'Available'),
(1652, '7B', 1, 1.00, 78, 'Available'),
(1653, '7C', 1, 1.00, 78, 'Available'),
(1654, '7D', 1, 1.00, 78, 'Available'),
(1655, '8A', 1, 1.00, 78, 'Available'),
(1656, '8B', 1, 1.00, 78, 'Available'),
(1657, '8C', 1, 1.00, 78, 'Available'),
(1658, '8D', 1, 1.00, 78, 'Available'),
(1659, '1A', 1, 1.00, 79, 'Available'),
(1660, '1B', 1, 1.00, 79, 'Available'),
(1661, '1C', 1, 1.00, 79, 'Available'),
(1662, '1D', 1, 1.00, 79, 'Available'),
(1663, '2A', 1, 1.00, 79, 'Available'),
(1664, '2B', 1, 1.00, 79, 'Available'),
(1665, '2C', 1, 1.00, 79, 'Available'),
(1666, '2D', 1, 1.00, 79, 'Available'),
(1667, '3A', 1, 1.00, 79, 'Available'),
(1668, '3B', 1, 1.00, 79, 'Available'),
(1669, '3C', 1, 1.00, 79, 'Available'),
(1670, '3D', 1, 1.00, 79, 'Available'),
(1671, '4A', 1, 1.00, 79, 'Available'),
(1672, '4B', 1, 1.00, 79, 'Available'),
(1673, '4C', 1, 1.00, 79, 'Available'),
(1674, '4D', 1, 1.00, 79, 'Available'),
(1675, '5A', 1, 1.00, 79, 'Available'),
(1676, '5B', 1, 1.00, 79, 'Available'),
(1677, '5C', 1, 1.00, 79, 'Available'),
(1678, '5D', 1, 1.00, 79, 'Available'),
(1679, '6A', 1, 1.00, 79, 'Available'),
(1680, '6B', 1, 1.00, 79, 'Available'),
(1681, '6C', 1, 1.00, 79, 'Available'),
(1682, '6D', 1, 1.00, 79, 'Available'),
(1683, '7A', 1, 1.00, 79, 'Available'),
(1684, '7B', 1, 1.00, 79, 'Available'),
(1685, '7C', 1, 1.00, 79, 'Available'),
(1686, '7D', 1, 1.00, 79, 'Available'),
(1687, '8A', 1, 1.00, 79, 'Available'),
(1688, '8B', 1, 1.00, 79, 'Available'),
(1689, '8C', 1, 1.00, 79, 'Available'),
(1690, '8D', 1, 1.00, 79, 'Available'),
(1691, '1A', 1, 1.00, 80, 'Available'),
(1692, '1B', 2, 1.20, 80, 'Available'),
(1693, '1C', 3, 1.40, 80, 'Available'),
(1694, '2A', 1, 1.00, 80, 'Available'),
(1695, '2B', 2, 1.20, 80, 'Available'),
(1696, '2C', 3, 1.40, 80, 'Available'),
(1697, '3A', 1, 1.00, 80, 'Available'),
(1698, '3B', 2, 1.20, 80, 'Available'),
(1699, '3C', 3, 1.40, 80, 'Available'),
(1700, '4A', 1, 1.00, 80, 'Available'),
(1701, '4B', 2, 1.20, 80, 'Available'),
(1702, '4C', 3, 1.40, 80, 'Available'),
(1703, '5A', 1, 1.00, 80, 'Available'),
(1704, '5B', 2, 1.20, 80, 'Available'),
(1705, '5C', 3, 1.40, 80, 'Available'),
(1706, '6A', 1, 1.00, 80, 'Available'),
(1707, '6B', 2, 1.20, 80, 'Available'),
(1708, '6C', 3, 1.40, 80, 'Available'),
(1709, '7A', 1, 1.00, 80, 'Available'),
(1710, '7B', 2, 1.20, 80, 'Available'),
(1711, '7C', 3, 1.40, 80, 'Available'),
(1712, '8A', 1, 1.00, 80, 'Available'),
(1713, '8B', 2, 1.20, 80, 'Available'),
(1714, '8C', 3, 1.40, 80, 'Available'),
(1715, '9A', 1, 1.00, 80, 'Available'),
(1716, '9B', 2, 1.20, 80, 'Available'),
(1717, '9C', 3, 1.40, 80, 'Available'),
(1718, '10A', 1, 1.00, 80, 'Available'),
(1719, '10B', 2, 1.20, 80, 'Available'),
(1720, '10C', 3, 1.40, 80, 'Available'),
(1721, '1A', 1, 1.00, 81, 'Available'),
(1722, '1B', 2, 1.20, 81, 'Available'),
(1723, '1C', 3, 1.40, 81, 'Available'),
(1724, '2A', 1, 1.00, 81, 'Available'),
(1725, '2B', 2, 1.20, 81, 'Available'),
(1726, '2C', 3, 1.40, 81, 'Available'),
(1727, '3A', 1, 1.00, 81, 'Available'),
(1728, '3B', 2, 1.20, 81, 'Available'),
(1729, '3C', 3, 1.40, 81, 'Available'),
(1730, '4A', 1, 1.00, 81, 'Available'),
(1731, '4B', 2, 1.20, 81, 'Available'),
(1732, '4C', 3, 1.40, 81, 'Available'),
(1733, '5A', 1, 1.00, 81, 'Available'),
(1734, '5B', 2, 1.20, 81, 'Available'),
(1735, '5C', 3, 1.40, 81, 'Available'),
(1736, '6A', 1, 1.00, 81, 'Available'),
(1737, '6B', 2, 1.20, 81, 'Available'),
(1738, '6C', 3, 1.40, 81, 'Available'),
(1739, '7A', 1, 1.00, 81, 'Available'),
(1740, '7B', 2, 1.20, 81, 'Available'),
(1741, '7C', 3, 1.40, 81, 'Available'),
(1742, '8A', 1, 1.00, 81, 'Available'),
(1743, '8B', 2, 1.20, 81, 'Available'),
(1744, '8C', 3, 1.40, 81, 'Available'),
(1745, '9A', 1, 1.00, 81, 'Available'),
(1746, '9B', 2, 1.20, 81, 'Available'),
(1747, '9C', 3, 1.40, 81, 'Available'),
(1748, '10A', 1, 1.00, 81, 'Available'),
(1749, '10B', 2, 1.20, 81, 'Available'),
(1750, '10C', 3, 1.40, 81, 'Available'),
(1751, '1A', 1, 1.00, 82, 'Available'),
(1752, '1B', 2, 1.20, 82, 'Available'),
(1753, '2A', 1, 1.00, 82, 'Available'),
(1754, '2B', 2, 1.20, 82, 'Available'),
(1755, '3A', 1, 1.00, 82, 'Available'),
(1756, '3B', 2, 1.20, 82, 'Available'),
(1757, '4A', 1, 1.00, 82, 'Available'),
(1758, '4B', 2, 1.20, 82, 'Available'),
(1759, '5A', 1, 1.00, 82, 'Available'),
(1760, '5B', 2, 1.20, 82, 'Available'),
(1761, '6A', 1, 1.00, 82, 'Available'),
(1762, '6B', 2, 1.20, 82, 'Available'),
(1763, '7A', 1, 1.00, 82, 'Available'),
(1764, '7B', 2, 1.20, 82, 'Available'),
(1765, '8A', 1, 1.00, 82, 'Available'),
(1766, '8B', 2, 1.20, 82, 'Available'),
(1767, '9A', 1, 1.00, 82, 'Available'),
(1768, '9B', 2, 1.20, 82, 'Available'),
(1769, '10A', 1, 1.00, 82, 'Available'),
(1770, '10B', 2, 1.20, 82, 'Available'),
(1771, '1A', 1, 1.00, 83, 'Available'),
(1772, '1B', 1, 1.00, 83, 'Available'),
(1773, '1C', 1, 1.00, 83, 'Available'),
(1774, '1D', 1, 1.00, 83, 'Available'),
(1775, '2A', 1, 1.00, 83, 'Available'),
(1776, '2B', 1, 1.00, 83, 'Available'),
(1777, '2C', 1, 1.00, 83, 'Available'),
(1778, '2D', 1, 1.00, 83, 'Available'),
(1779, '3A', 1, 1.00, 83, 'Available'),
(1780, '3B', 1, 1.00, 83, 'Available'),
(1781, '3C', 1, 1.00, 83, 'Available'),
(1782, '3D', 1, 1.00, 83, 'Available'),
(1783, '4A', 1, 1.00, 83, 'Available'),
(1784, '4B', 1, 1.00, 83, 'Available'),
(1785, '4C', 1, 1.00, 83, 'Available'),
(1786, '4D', 1, 1.00, 83, 'Available'),
(1787, '5A', 1, 1.00, 83, 'Available'),
(1788, '5B', 1, 1.00, 83, 'Available'),
(1789, '5C', 1, 1.00, 83, 'Available'),
(1790, '5D', 1, 1.00, 83, 'Available'),
(1791, '6A', 1, 1.00, 83, 'Available'),
(1792, '6B', 1, 1.00, 83, 'Available'),
(1793, '6C', 1, 1.00, 83, 'Available'),
(1794, '6D', 1, 1.00, 83, 'Available'),
(1795, '7A', 1, 1.00, 83, 'Available'),
(1796, '7B', 1, 1.00, 83, 'Available'),
(1797, '7C', 1, 1.00, 83, 'Available'),
(1798, '7D', 1, 1.00, 83, 'Available'),
(1799, '8A', 1, 1.00, 83, 'Available'),
(1800, '8B', 1, 1.00, 83, 'Available'),
(1801, '8C', 1, 1.00, 83, 'Available'),
(1802, '8D', 1, 1.00, 83, 'Available'),
(1803, '1A', 1, 1.00, 84, 'Available'),
(1804, '1B', 1, 1.00, 84, 'Available'),
(1805, '1C', 1, 1.00, 84, 'Available'),
(1806, '1D', 1, 1.00, 84, 'Available'),
(1807, '2A', 1, 1.00, 84, 'Available'),
(1808, '2B', 1, 1.00, 84, 'Available'),
(1809, '2C', 1, 1.00, 84, 'Available'),
(1810, '2D', 1, 1.00, 84, 'Available'),
(1811, '3A', 1, 1.00, 84, 'Available'),
(1812, '3B', 1, 1.00, 84, 'Available'),
(1813, '3C', 1, 1.00, 84, 'Available'),
(1814, '3D', 1, 1.00, 84, 'Available'),
(1815, '4A', 1, 1.00, 84, 'Available'),
(1816, '4B', 1, 1.00, 84, 'Available'),
(1817, '4C', 1, 1.00, 84, 'Available'),
(1818, '4D', 1, 1.00, 84, 'Available'),
(1819, '5A', 1, 1.00, 84, 'Available'),
(1820, '5B', 1, 1.00, 84, 'Available'),
(1821, '5C', 1, 1.00, 84, 'Available'),
(1822, '5D', 1, 1.00, 84, 'Available'),
(1823, '6A', 1, 1.00, 84, 'Available'),
(1824, '6B', 1, 1.00, 84, 'Available'),
(1825, '6C', 1, 1.00, 84, 'Available'),
(1826, '6D', 1, 1.00, 84, 'Available'),
(1827, '7A', 1, 1.00, 84, 'Available'),
(1828, '7B', 1, 1.00, 84, 'Available'),
(1829, '7C', 1, 1.00, 84, 'Available'),
(1830, '7D', 1, 1.00, 84, 'Available'),
(1831, '8A', 1, 1.00, 84, 'Available'),
(1832, '8B', 1, 1.00, 84, 'Available'),
(1833, '8C', 1, 1.00, 84, 'Available'),
(1834, '8D', 1, 1.00, 84, 'Available'),
(1835, '1A', 1, 1.00, 85, 'Available'),
(1836, '1B', 1, 1.00, 85, 'Available'),
(1837, '1C', 1, 1.00, 85, 'Available'),
(1838, '1D', 1, 1.00, 85, 'Available'),
(1839, '2A', 1, 1.00, 85, 'Available'),
(1840, '2B', 1, 1.00, 85, 'Available'),
(1841, '2C', 1, 1.00, 85, 'Available'),
(1842, '2D', 1, 1.00, 85, 'Available'),
(1843, '3A', 1, 1.00, 85, 'Available'),
(1844, '3B', 1, 1.00, 85, 'Available'),
(1845, '3C', 1, 1.00, 85, 'Available'),
(1846, '3D', 1, 1.00, 85, 'Available'),
(1847, '4A', 1, 1.00, 85, 'Available'),
(1848, '4B', 1, 1.00, 85, 'Available'),
(1849, '4C', 1, 1.00, 85, 'Available'),
(1850, '4D', 1, 1.00, 85, 'Available'),
(1851, '5A', 1, 1.00, 85, 'Available'),
(1852, '5B', 1, 1.00, 85, 'Available'),
(1853, '5C', 1, 1.00, 85, 'Available'),
(1854, '5D', 1, 1.00, 85, 'Available'),
(1855, '6A', 1, 1.00, 85, 'Available'),
(1856, '6B', 1, 1.00, 85, 'Available'),
(1857, '6C', 1, 1.00, 85, 'Available'),
(1858, '6D', 1, 1.00, 85, 'Available'),
(1859, '7A', 1, 1.00, 85, 'Available'),
(1860, '7B', 1, 1.00, 85, 'Available'),
(1861, '7C', 1, 1.00, 85, 'Available'),
(1862, '7D', 1, 1.00, 85, 'Available'),
(1863, '8A', 1, 1.00, 85, 'Available'),
(1864, '8B', 1, 1.00, 85, 'Available'),
(1865, '8C', 1, 1.00, 85, 'Available'),
(1866, '8D', 1, 1.00, 85, 'Available'),
(1867, '1A', 1, 1.00, 86, 'Available'),
(1868, '1B', 2, 1.20, 86, 'Available'),
(1869, '1C', 3, 1.40, 86, 'Available'),
(1870, '2A', 1, 1.00, 86, 'Available'),
(1871, '2B', 2, 1.20, 86, 'Available'),
(1872, '2C', 3, 1.40, 86, 'Available'),
(1873, '3A', 1, 1.00, 86, 'Available'),
(1874, '3B', 2, 1.20, 86, 'Available'),
(1875, '3C', 3, 1.40, 86, 'Available'),
(1876, '4A', 1, 1.00, 86, 'Available'),
(1877, '4B', 2, 1.20, 86, 'Available'),
(1878, '4C', 3, 1.40, 86, 'Available'),
(1879, '5A', 1, 1.00, 86, 'Available'),
(1880, '5B', 2, 1.20, 86, 'Available'),
(1881, '5C', 3, 1.40, 86, 'Available'),
(1882, '6A', 1, 1.00, 86, 'Available'),
(1883, '6B', 2, 1.20, 86, 'Available'),
(1884, '6C', 3, 1.40, 86, 'Available'),
(1885, '7A', 1, 1.00, 86, 'Available'),
(1886, '7B', 2, 1.20, 86, 'Available'),
(1887, '7C', 3, 1.40, 86, 'Available'),
(1888, '8A', 1, 1.00, 86, 'Available'),
(1889, '8B', 2, 1.20, 86, 'Available'),
(1890, '8C', 3, 1.40, 86, 'Available'),
(1891, '9A', 1, 1.00, 86, 'Available'),
(1892, '9B', 2, 1.20, 86, 'Available'),
(1893, '9C', 3, 1.40, 86, 'Available'),
(1894, '10A', 1, 1.00, 86, 'Available'),
(1895, '10B', 2, 1.20, 86, 'Available'),
(1896, '10C', 3, 1.40, 86, 'Available'),
(1897, '1A', 1, 1.00, 87, 'Available'),
(1898, '1B', 2, 1.20, 87, 'Available'),
(1899, '1C', 3, 1.40, 87, 'Available'),
(1900, '2A', 1, 1.00, 87, 'Available'),
(1901, '2B', 2, 1.20, 87, 'Available'),
(1902, '2C', 3, 1.40, 87, 'Available'),
(1903, '3A', 1, 1.00, 87, 'Available'),
(1904, '3B', 2, 1.20, 87, 'Available'),
(1905, '3C', 3, 1.40, 87, 'Available'),
(1906, '4A', 1, 1.00, 87, 'Available'),
(1907, '4B', 2, 1.20, 87, 'Available'),
(1908, '4C', 3, 1.40, 87, 'Available'),
(1909, '5A', 1, 1.00, 87, 'Available'),
(1910, '5B', 2, 1.20, 87, 'Available'),
(1911, '5C', 3, 1.40, 87, 'Available'),
(1912, '6A', 1, 1.00, 87, 'Available'),
(1913, '6B', 2, 1.20, 87, 'Available'),
(1914, '6C', 3, 1.40, 87, 'Available'),
(1915, '7A', 1, 1.00, 87, 'Available'),
(1916, '7B', 2, 1.20, 87, 'Available'),
(1917, '7C', 3, 1.40, 87, 'Available'),
(1918, '8A', 1, 1.00, 87, 'Available'),
(1919, '8B', 2, 1.20, 87, 'Available'),
(1920, '8C', 3, 1.40, 87, 'Available'),
(1921, '9A', 1, 1.00, 87, 'Available'),
(1922, '9B', 2, 1.20, 87, 'Available'),
(1923, '9C', 3, 1.40, 87, 'Available'),
(1924, '10A', 1, 1.00, 87, 'Available'),
(1925, '10B', 2, 1.20, 87, 'Available'),
(1926, '10C', 3, 1.40, 87, 'Available'),
(1927, '1A', 1, 1.00, 88, 'Available'),
(1928, '1B', 2, 1.20, 88, 'Available'),
(1929, '2A', 1, 1.00, 88, 'Available'),
(1930, '2B', 2, 1.20, 88, 'Available'),
(1931, '3A', 1, 1.00, 88, 'Available'),
(1932, '3B', 2, 1.20, 88, 'Available'),
(1933, '4A', 1, 1.00, 88, 'Available'),
(1934, '4B', 2, 1.20, 88, 'Available'),
(1935, '5A', 1, 1.00, 88, 'Available'),
(1936, '5B', 2, 1.20, 88, 'Available'),
(1937, '6A', 1, 1.00, 88, 'Available'),
(1938, '6B', 2, 1.20, 88, 'Available'),
(1939, '7A', 1, 1.00, 88, 'Available'),
(1940, '7B', 2, 1.20, 88, 'Available'),
(1941, '8A', 1, 1.00, 88, 'Available'),
(1942, '8B', 2, 1.20, 88, 'Available'),
(1943, '9A', 1, 1.00, 88, 'Available'),
(1944, '9B', 2, 1.20, 88, 'Available'),
(1945, '10A', 1, 1.00, 88, 'Available'),
(1946, '10B', 2, 1.20, 88, 'Available'),
(1947, '1A', 1, 1.00, 89, 'Available'),
(1948, '1B', 1, 1.00, 89, 'Available'),
(1949, '1C', 1, 1.00, 89, 'Available'),
(1950, '1D', 1, 1.00, 89, 'Available'),
(1951, '2A', 1, 1.00, 89, 'Available'),
(1952, '2B', 1, 1.00, 89, 'Available'),
(1953, '2C', 1, 1.00, 89, 'Available'),
(1954, '2D', 1, 1.00, 89, 'Available'),
(1955, '3A', 1, 1.00, 89, 'Available'),
(1956, '3B', 1, 1.00, 89, 'Available'),
(1957, '3C', 1, 1.00, 89, 'Available'),
(1958, '3D', 1, 1.00, 89, 'Available'),
(1959, '4A', 1, 1.00, 89, 'Available'),
(1960, '4B', 1, 1.00, 89, 'Available'),
(1961, '4C', 1, 1.00, 89, 'Available'),
(1962, '4D', 1, 1.00, 89, 'Available'),
(1963, '5A', 1, 1.00, 89, 'Available'),
(1964, '5B', 1, 1.00, 89, 'Available'),
(1965, '5C', 1, 1.00, 89, 'Available'),
(1966, '5D', 1, 1.00, 89, 'Available'),
(1967, '6A', 1, 1.00, 89, 'Available'),
(1968, '6B', 1, 1.00, 89, 'Available'),
(1969, '6C', 1, 1.00, 89, 'Available'),
(1970, '6D', 1, 1.00, 89, 'Available'),
(1971, '7A', 1, 1.00, 89, 'Available'),
(1972, '7B', 1, 1.00, 89, 'Available'),
(1973, '7C', 1, 1.00, 89, 'Available'),
(1974, '7D', 1, 1.00, 89, 'Available'),
(1975, '8A', 1, 1.00, 89, 'Available'),
(1976, '8B', 1, 1.00, 89, 'Available'),
(1977, '8C', 1, 1.00, 89, 'Available'),
(1978, '8D', 1, 1.00, 89, 'Available'),
(1979, '1A', 1, 1.00, 90, 'Available'),
(1980, '1B', 2, 1.20, 90, 'Available'),
(1981, '1C', 3, 1.40, 90, 'Available'),
(1982, '2A', 1, 1.00, 90, 'Available'),
(1983, '2B', 2, 1.20, 90, 'Available'),
(1984, '2C', 3, 1.40, 90, 'Available'),
(1985, '3A', 1, 1.00, 90, 'Available'),
(1986, '3B', 2, 1.20, 90, 'Available'),
(1987, '3C', 3, 1.40, 90, 'Available'),
(1988, '4A', 1, 1.00, 90, 'Available'),
(1989, '4B', 2, 1.20, 90, 'Available'),
(1990, '4C', 3, 1.40, 90, 'Available'),
(1991, '5A', 1, 1.00, 90, 'Available'),
(1992, '5B', 2, 1.20, 90, 'Available'),
(1993, '5C', 3, 1.40, 90, 'Available'),
(1994, '6A', 1, 1.00, 90, 'Available'),
(1995, '6B', 2, 1.20, 90, 'Available'),
(1996, '6C', 3, 1.40, 90, 'Available'),
(1997, '7A', 1, 1.00, 90, 'Available'),
(1998, '7B', 2, 1.20, 90, 'Available'),
(1999, '7C', 3, 1.40, 90, 'Available'),
(2000, '8A', 1, 1.00, 90, 'Available'),
(2001, '8B', 2, 1.20, 90, 'Available'),
(2002, '8C', 3, 1.40, 90, 'Available'),
(2003, '9A', 1, 1.00, 90, 'Available'),
(2004, '9B', 2, 1.20, 90, 'Available'),
(2005, '9C', 3, 1.40, 90, 'Available'),
(2006, '10A', 1, 1.00, 90, 'Available'),
(2007, '10B', 2, 1.20, 90, 'Available'),
(2008, '10C', 3, 1.40, 90, 'Available'),
(2009, '1A', 1, 1.00, 91, 'Available'),
(2010, '1B', 2, 1.20, 91, 'Available'),
(2011, '1C', 3, 1.40, 91, 'Available'),
(2012, '2A', 1, 1.00, 91, 'Available'),
(2013, '2B', 2, 1.20, 91, 'Available'),
(2014, '2C', 3, 1.40, 91, 'Available'),
(2015, '3A', 1, 1.00, 91, 'Available'),
(2016, '3B', 2, 1.20, 91, 'Available'),
(2017, '3C', 3, 1.40, 91, 'Available'),
(2018, '4A', 1, 1.00, 91, 'Available'),
(2019, '4B', 2, 1.20, 91, 'Available'),
(2020, '4C', 3, 1.40, 91, 'Available'),
(2021, '5A', 1, 1.00, 91, 'Available'),
(2022, '5B', 2, 1.20, 91, 'Available'),
(2023, '5C', 3, 1.40, 91, 'Available'),
(2024, '6A', 1, 1.00, 91, 'Available'),
(2025, '6B', 2, 1.20, 91, 'Available'),
(2026, '6C', 3, 1.40, 91, 'Available'),
(2027, '7A', 1, 1.00, 91, 'Available'),
(2028, '7B', 2, 1.20, 91, 'Available'),
(2029, '7C', 3, 1.40, 91, 'Available'),
(2030, '8A', 1, 1.00, 91, 'Available'),
(2031, '8B', 2, 1.20, 91, 'Available'),
(2032, '8C', 3, 1.40, 91, 'Available'),
(2033, '9A', 1, 1.00, 91, 'Available'),
(2034, '9B', 2, 1.20, 91, 'Available'),
(2035, '9C', 3, 1.40, 91, 'Available'),
(2036, '10A', 1, 1.00, 91, 'Available'),
(2037, '10B', 2, 1.20, 91, 'Available'),
(2038, '10C', 3, 1.40, 91, 'Available'),
(2039, '1A', 1, 1.00, 92, 'Available'),
(2040, '1B', 2, 1.20, 92, 'Available'),
(2041, '2A', 1, 1.00, 92, 'Available'),
(2042, '2B', 2, 1.20, 92, 'Available'),
(2043, '3A', 1, 1.00, 92, 'Available'),
(2044, '3B', 2, 1.20, 92, 'Available'),
(2045, '4A', 1, 1.00, 92, 'Available'),
(2046, '4B', 2, 1.20, 92, 'Available'),
(2047, '5A', 1, 1.00, 92, 'Available'),
(2048, '5B', 2, 1.20, 92, 'Available'),
(2049, '6A', 1, 1.00, 92, 'Available'),
(2050, '6B', 2, 1.20, 92, 'Available'),
(2051, '7A', 1, 1.00, 92, 'Available'),
(2052, '7B', 2, 1.20, 92, 'Available'),
(2053, '8A', 1, 1.00, 92, 'Available'),
(2054, '8B', 2, 1.20, 92, 'Available'),
(2055, '9A', 1, 1.00, 92, 'Available'),
(2056, '9B', 2, 1.20, 92, 'Available'),
(2057, '10A', 1, 1.00, 92, 'Available'),
(2058, '10B', 2, 1.20, 92, 'Available'),
(2059, '1A', 1, 1.00, 93, 'Available'),
(2060, '1B', 2, 1.20, 93, 'Available'),
(2061, '2A', 1, 1.00, 93, 'Available'),
(2062, '2B', 2, 1.20, 93, 'Available'),
(2063, '3A', 1, 1.00, 93, 'Available'),
(2064, '3B', 2, 1.20, 93, 'Available'),
(2065, '4A', 1, 1.00, 93, 'Available'),
(2066, '4B', 2, 1.20, 93, 'Available'),
(2067, '5A', 1, 1.00, 93, 'Available'),
(2068, '5B', 2, 1.20, 93, 'Available'),
(2069, '6A', 1, 1.00, 93, 'Available'),
(2070, '6B', 2, 1.20, 93, 'Available'),
(2071, '7A', 1, 1.00, 93, 'Available'),
(2072, '7B', 2, 1.20, 93, 'Available'),
(2073, '8A', 1, 1.00, 93, 'Available'),
(2074, '8B', 2, 1.20, 93, 'Available'),
(2075, '9A', 1, 1.00, 93, 'Available'),
(2076, '9B', 2, 1.20, 93, 'Available'),
(2077, '10A', 1, 1.00, 93, 'Available'),
(2078, '10B', 2, 1.20, 93, 'Available'),
(2079, '1A', 1, 1.00, 94, 'Available'),
(2080, '1B', 2, 1.20, 94, 'Available'),
(2081, '2A', 1, 1.00, 94, 'Available'),
(2082, '2B', 2, 1.20, 94, 'Available'),
(2083, '3A', 1, 1.00, 94, 'Available'),
(2084, '3B', 2, 1.20, 94, 'Available'),
(2085, '4A', 1, 1.00, 94, 'Available'),
(2086, '4B', 2, 1.20, 94, 'Available'),
(2087, '5A', 1, 1.00, 94, 'Available'),
(2088, '5B', 2, 1.20, 94, 'Available'),
(2089, '6A', 1, 1.00, 94, 'Available'),
(2090, '6B', 2, 1.20, 94, 'Available'),
(2091, '7A', 1, 1.00, 94, 'Available'),
(2092, '7B', 2, 1.20, 94, 'Available'),
(2093, '8A', 1, 1.00, 94, 'Available'),
(2094, '8B', 2, 1.20, 94, 'Available'),
(2095, '9A', 1, 1.00, 94, 'Available'),
(2096, '9B', 2, 1.20, 94, 'Available'),
(2097, '10A', 1, 1.00, 94, 'Available'),
(2098, '10B', 2, 1.20, 94, 'Available'),
(2099, '1A', 1, 1.00, 95, 'Available'),
(2100, '1B', 1, 1.00, 95, 'Available'),
(2101, '1C', 1, 1.00, 95, 'Available'),
(2102, '1D', 1, 1.00, 95, 'Available'),
(2103, '2A', 1, 1.00, 95, 'Available'),
(2104, '2B', 1, 1.00, 95, 'Available'),
(2105, '2C', 1, 1.00, 95, 'Available'),
(2106, '2D', 1, 1.00, 95, 'Available'),
(2107, '3A', 1, 1.00, 95, 'Available'),
(2108, '3B', 1, 1.00, 95, 'Available'),
(2109, '3C', 1, 1.00, 95, 'Available'),
(2110, '3D', 1, 1.00, 95, 'Available'),
(2111, '4A', 1, 1.00, 95, 'Available'),
(2112, '4B', 1, 1.00, 95, 'Available'),
(2113, '4C', 1, 1.00, 95, 'Available'),
(2114, '4D', 1, 1.00, 95, 'Available'),
(2115, '5A', 1, 1.00, 95, 'Available'),
(2116, '5B', 1, 1.00, 95, 'Available'),
(2117, '5C', 1, 1.00, 95, 'Available'),
(2118, '5D', 1, 1.00, 95, 'Available'),
(2119, '6A', 1, 1.00, 95, 'Available'),
(2120, '6B', 1, 1.00, 95, 'Available'),
(2121, '6C', 1, 1.00, 95, 'Available'),
(2122, '6D', 1, 1.00, 95, 'Available'),
(2123, '7A', 1, 1.00, 95, 'Available'),
(2124, '7B', 1, 1.00, 95, 'Available'),
(2125, '7C', 1, 1.00, 95, 'Available'),
(2126, '7D', 1, 1.00, 95, 'Available'),
(2127, '8A', 1, 1.00, 95, 'Available'),
(2128, '8B', 1, 1.00, 95, 'Available'),
(2129, '8C', 1, 1.00, 95, 'Available'),
(2130, '8D', 1, 1.00, 95, 'Available'),
(2131, '1A', 1, 1.00, 96, 'Available'),
(2132, '1B', 1, 1.00, 96, 'Available'),
(2133, '1C', 1, 1.00, 96, 'Available'),
(2134, '1D', 1, 1.00, 96, 'Available'),
(2135, '2A', 1, 1.00, 96, 'Available'),
(2136, '2B', 1, 1.00, 96, 'Available'),
(2137, '2C', 1, 1.00, 96, 'Available'),
(2138, '2D', 1, 1.00, 96, 'Available'),
(2139, '3A', 1, 1.00, 96, 'Available'),
(2140, '3B', 1, 1.00, 96, 'Available'),
(2141, '3C', 1, 1.00, 96, 'Available'),
(2142, '3D', 1, 1.00, 96, 'Available'),
(2143, '4A', 1, 1.00, 96, 'Available'),
(2144, '4B', 1, 1.00, 96, 'Available'),
(2145, '4C', 1, 1.00, 96, 'Available'),
(2146, '4D', 1, 1.00, 96, 'Available'),
(2147, '5A', 1, 1.00, 96, 'Available'),
(2148, '5B', 1, 1.00, 96, 'Available'),
(2149, '5C', 1, 1.00, 96, 'Available'),
(2150, '5D', 1, 1.00, 96, 'Available'),
(2151, '6A', 1, 1.00, 96, 'Available'),
(2152, '6B', 1, 1.00, 96, 'Available'),
(2153, '6C', 1, 1.00, 96, 'Available'),
(2154, '6D', 1, 1.00, 96, 'Available'),
(2155, '7A', 1, 1.00, 96, 'Available'),
(2156, '7B', 1, 1.00, 96, 'Available'),
(2157, '7C', 1, 1.00, 96, 'Available'),
(2158, '7D', 1, 1.00, 96, 'Available'),
(2159, '8A', 1, 1.00, 96, 'Available'),
(2160, '8B', 1, 1.00, 96, 'Available'),
(2161, '8C', 1, 1.00, 96, 'Available'),
(2162, '8D', 1, 1.00, 96, 'Available'),
(2163, '1A', 1, 1.00, 97, 'Available'),
(2164, '1B', 2, 1.20, 97, 'Available'),
(2165, '1C', 3, 1.40, 97, 'Available'),
(2166, '2A', 1, 1.00, 97, 'Available'),
(2167, '2B', 2, 1.20, 97, 'Available'),
(2168, '2C', 3, 1.40, 97, 'Available'),
(2169, '3A', 1, 1.00, 97, 'Available'),
(2170, '3B', 2, 1.20, 97, 'Available'),
(2171, '3C', 3, 1.40, 97, 'Available'),
(2172, '4A', 1, 1.00, 97, 'Available'),
(2173, '4B', 2, 1.20, 97, 'Available'),
(2174, '4C', 3, 1.40, 97, 'Available'),
(2175, '5A', 1, 1.00, 97, 'Available'),
(2176, '5B', 2, 1.20, 97, 'Available'),
(2177, '5C', 3, 1.40, 97, 'Available'),
(2178, '6A', 1, 1.00, 97, 'Available'),
(2179, '6B', 2, 1.20, 97, 'Available'),
(2180, '6C', 3, 1.40, 97, 'Available'),
(2181, '7A', 1, 1.00, 97, 'Available'),
(2182, '7B', 2, 1.20, 97, 'Available'),
(2183, '7C', 3, 1.40, 97, 'Available'),
(2184, '8A', 1, 1.00, 97, 'Available'),
(2185, '8B', 2, 1.20, 97, 'Available'),
(2186, '8C', 3, 1.40, 97, 'Available'),
(2187, '9A', 1, 1.00, 97, 'Available'),
(2188, '9B', 2, 1.20, 97, 'Available'),
(2189, '9C', 3, 1.40, 97, 'Available'),
(2190, '10A', 1, 1.00, 97, 'Available'),
(2191, '10B', 2, 1.20, 97, 'Available'),
(2192, '10C', 3, 1.40, 97, 'Available'),
(2193, '1A', 1, 1.00, 98, 'Available'),
(2194, '1B', 2, 1.20, 98, 'Available'),
(2195, '1C', 3, 1.40, 98, 'Available'),
(2196, '2A', 1, 1.00, 98, 'Available'),
(2197, '2B', 2, 1.20, 98, 'Available'),
(2198, '2C', 3, 1.40, 98, 'Available'),
(2199, '3A', 1, 1.00, 98, 'Available'),
(2200, '3B', 2, 1.20, 98, 'Available'),
(2201, '3C', 3, 1.40, 98, 'Available'),
(2202, '4A', 1, 1.00, 98, 'Available'),
(2203, '4B', 2, 1.20, 98, 'Available'),
(2204, '4C', 3, 1.40, 98, 'Available'),
(2205, '5A', 1, 1.00, 98, 'Available'),
(2206, '5B', 2, 1.20, 98, 'Available'),
(2207, '5C', 3, 1.40, 98, 'Available'),
(2208, '6A', 1, 1.00, 98, 'Available'),
(2209, '6B', 2, 1.20, 98, 'Available'),
(2210, '6C', 3, 1.40, 98, 'Available'),
(2211, '7A', 1, 1.00, 98, 'Available'),
(2212, '7B', 2, 1.20, 98, 'Available'),
(2213, '7C', 3, 1.40, 98, 'Available'),
(2214, '8A', 1, 1.00, 98, 'Available'),
(2215, '8B', 2, 1.20, 98, 'Available'),
(2216, '8C', 3, 1.40, 98, 'Available'),
(2217, '9A', 1, 1.00, 98, 'Available'),
(2218, '9B', 2, 1.20, 98, 'Available'),
(2219, '9C', 3, 1.40, 98, 'Available'),
(2220, '10A', 1, 1.00, 98, 'Available'),
(2221, '10B', 2, 1.20, 98, 'Available'),
(2222, '10C', 3, 1.40, 98, 'Available'),
(2223, '1A', 1, 1.00, 99, 'Available'),
(2224, '1B', 2, 1.20, 99, 'Available'),
(2225, '2A', 1, 1.00, 99, 'Available'),
(2226, '2B', 2, 1.20, 99, 'Available'),
(2227, '3A', 1, 1.00, 99, 'Available'),
(2228, '3B', 2, 1.20, 99, 'Available'),
(2229, '4A', 1, 1.00, 99, 'Available'),
(2230, '4B', 2, 1.20, 99, 'Available'),
(2231, '5A', 1, 1.00, 99, 'Available'),
(2232, '5B', 2, 1.20, 99, 'Available'),
(2233, '6A', 1, 1.00, 99, 'Available'),
(2234, '6B', 2, 1.20, 99, 'Available'),
(2235, '7A', 1, 1.00, 99, 'Available'),
(2236, '7B', 2, 1.20, 99, 'Available'),
(2237, '8A', 1, 1.00, 99, 'Available'),
(2238, '8B', 2, 1.20, 99, 'Available'),
(2239, '9A', 1, 1.00, 99, 'Available'),
(2240, '9B', 2, 1.20, 99, 'Available'),
(2241, '10A', 1, 1.00, 99, 'Available'),
(2242, '10B', 2, 1.20, 99, 'Available'),
(2243, '1A', 1, 1.00, 100, 'Available'),
(2244, '1B', 2, 1.20, 100, 'Available'),
(2245, '2A', 1, 1.00, 100, 'Available'),
(2246, '2B', 2, 1.20, 100, 'Available'),
(2247, '3A', 1, 1.00, 100, 'Available'),
(2248, '3B', 2, 1.20, 100, 'Available'),
(2249, '4A', 1, 1.00, 100, 'Available'),
(2250, '4B', 2, 1.20, 100, 'Available'),
(2251, '5A', 1, 1.00, 100, 'Available'),
(2252, '5B', 2, 1.20, 100, 'Available'),
(2253, '6A', 1, 1.00, 100, 'Available'),
(2254, '6B', 2, 1.20, 100, 'Available'),
(2255, '7A', 1, 1.00, 100, 'Available'),
(2256, '7B', 2, 1.20, 100, 'Available'),
(2257, '8A', 1, 1.00, 100, 'Available'),
(2258, '8B', 2, 1.20, 100, 'Available'),
(2259, '9A', 1, 1.00, 100, 'Available'),
(2260, '9B', 2, 1.20, 100, 'Available'),
(2261, '10A', 1, 1.00, 100, 'Available'),
(2262, '10B', 2, 1.20, 100, 'Available'),
(2263, '1A', 1, 1.00, 101, 'Available'),
(2264, '1B', 1, 1.00, 101, 'Available'),
(2265, '1C', 1, 1.00, 101, 'Available'),
(2266, '1D', 1, 1.00, 101, 'Available'),
(2267, '2A', 1, 1.00, 101, 'Available'),
(2268, '2B', 1, 1.00, 101, 'Available'),
(2269, '2C', 1, 1.00, 101, 'Available'),
(2270, '2D', 1, 1.00, 101, 'Available'),
(2271, '3A', 1, 1.00, 101, 'Available'),
(2272, '3B', 1, 1.00, 101, 'Available'),
(2273, '3C', 1, 1.00, 101, 'Available'),
(2274, '3D', 1, 1.00, 101, 'Available'),
(2275, '4A', 1, 1.00, 101, 'Available'),
(2276, '4B', 1, 1.00, 101, 'Available'),
(2277, '4C', 1, 1.00, 101, 'Available'),
(2278, '4D', 1, 1.00, 101, 'Available'),
(2279, '5A', 1, 1.00, 101, 'Available'),
(2280, '5B', 1, 1.00, 101, 'Available'),
(2281, '5C', 1, 1.00, 101, 'Available'),
(2282, '5D', 1, 1.00, 101, 'Available'),
(2283, '6A', 1, 1.00, 101, 'Available'),
(2284, '6B', 1, 1.00, 101, 'Available'),
(2285, '6C', 1, 1.00, 101, 'Available'),
(2286, '6D', 1, 1.00, 101, 'Available'),
(2287, '7A', 1, 1.00, 101, 'Available'),
(2288, '7B', 1, 1.00, 101, 'Available'),
(2289, '7C', 1, 1.00, 101, 'Available'),
(2290, '7D', 1, 1.00, 101, 'Available'),
(2291, '8A', 1, 1.00, 101, 'Available'),
(2292, '8B', 1, 1.00, 101, 'Available'),
(2293, '8C', 1, 1.00, 101, 'Available'),
(2294, '8D', 1, 1.00, 101, 'Available'),
(2295, '1A', 1, 1.00, 102, 'Available'),
(2296, '1B', 1, 1.00, 102, 'Available'),
(2297, '1C', 1, 1.00, 102, 'Available'),
(2298, '1D', 1, 1.00, 102, 'Available'),
(2299, '2A', 1, 1.00, 102, 'Available'),
(2300, '2B', 1, 1.00, 102, 'Available'),
(2301, '2C', 1, 1.00, 102, 'Available'),
(2302, '2D', 1, 1.00, 102, 'Available'),
(2303, '3A', 1, 1.00, 102, 'Available'),
(2304, '3B', 1, 1.00, 102, 'Available'),
(2305, '3C', 1, 1.00, 102, 'Available'),
(2306, '3D', 1, 1.00, 102, 'Available'),
(2307, '4A', 1, 1.00, 102, 'Available'),
(2308, '4B', 1, 1.00, 102, 'Available'),
(2309, '4C', 1, 1.00, 102, 'Available'),
(2310, '4D', 1, 1.00, 102, 'Available'),
(2311, '5A', 1, 1.00, 102, 'Available'),
(2312, '5B', 1, 1.00, 102, 'Available'),
(2313, '5C', 1, 1.00, 102, 'Available'),
(2314, '5D', 1, 1.00, 102, 'Available'),
(2315, '6A', 1, 1.00, 102, 'Available'),
(2316, '6B', 1, 1.00, 102, 'Available'),
(2317, '6C', 1, 1.00, 102, 'Available'),
(2318, '6D', 1, 1.00, 102, 'Available'),
(2319, '7A', 1, 1.00, 102, 'Available'),
(2320, '7B', 1, 1.00, 102, 'Available'),
(2321, '7C', 1, 1.00, 102, 'Available'),
(2322, '7D', 1, 1.00, 102, 'Available'),
(2323, '8A', 1, 1.00, 102, 'Available'),
(2324, '8B', 1, 1.00, 102, 'Available'),
(2325, '8C', 1, 1.00, 102, 'Available'),
(2326, '8D', 1, 1.00, 102, 'Available'),
(2327, '1A', 1, 1.00, 103, 'Available'),
(2328, '1B', 2, 1.20, 103, 'Available'),
(2329, '1C', 3, 1.40, 103, 'Available'),
(2330, '2A', 1, 1.00, 103, 'Available'),
(2331, '2B', 2, 1.20, 103, 'Available'),
(2332, '2C', 3, 1.40, 103, 'Available'),
(2333, '3A', 1, 1.00, 103, 'Available'),
(2334, '3B', 2, 1.20, 103, 'Available'),
(2335, '3C', 3, 1.40, 103, 'Available'),
(2336, '4A', 1, 1.00, 103, 'Available'),
(2337, '4B', 2, 1.20, 103, 'Available'),
(2338, '4C', 3, 1.40, 103, 'Available'),
(2339, '5A', 1, 1.00, 103, 'Available'),
(2340, '5B', 2, 1.20, 103, 'Available'),
(2341, '5C', 3, 1.40, 103, 'Available'),
(2342, '6A', 1, 1.00, 103, 'Available'),
(2343, '6B', 2, 1.20, 103, 'Available'),
(2344, '6C', 3, 1.40, 103, 'Available'),
(2345, '7A', 1, 1.00, 103, 'Available'),
(2346, '7B', 2, 1.20, 103, 'Available'),
(2347, '7C', 3, 1.40, 103, 'Available'),
(2348, '8A', 1, 1.00, 103, 'Available'),
(2349, '8B', 2, 1.20, 103, 'Available'),
(2350, '8C', 3, 1.40, 103, 'Available'),
(2351, '9A', 1, 1.00, 103, 'Available'),
(2352, '9B', 2, 1.20, 103, 'Available'),
(2353, '9C', 3, 1.40, 103, 'Available'),
(2354, '10A', 1, 1.00, 103, 'Available'),
(2355, '10B', 2, 1.20, 103, 'Available'),
(2356, '10C', 3, 1.40, 103, 'Available'),
(2357, '1A', 1, 1.00, 104, 'Available'),
(2358, '1B', 2, 1.20, 104, 'Available'),
(2359, '1C', 3, 1.40, 104, 'Available'),
(2360, '2A', 1, 1.00, 104, 'Available'),
(2361, '2B', 2, 1.20, 104, 'Available'),
(2362, '2C', 3, 1.40, 104, 'Available'),
(2363, '3A', 1, 1.00, 104, 'Available'),
(2364, '3B', 2, 1.20, 104, 'Available'),
(2365, '3C', 3, 1.40, 104, 'Available'),
(2366, '4A', 1, 1.00, 104, 'Available'),
(2367, '4B', 2, 1.20, 104, 'Available'),
(2368, '4C', 3, 1.40, 104, 'Available'),
(2369, '5A', 1, 1.00, 104, 'Available'),
(2370, '5B', 2, 1.20, 104, 'Available'),
(2371, '5C', 3, 1.40, 104, 'Available'),
(2372, '6A', 1, 1.00, 104, 'Available'),
(2373, '6B', 2, 1.20, 104, 'Available'),
(2374, '6C', 3, 1.40, 104, 'Available'),
(2375, '7A', 1, 1.00, 104, 'Available'),
(2376, '7B', 2, 1.20, 104, 'Available'),
(2377, '7C', 3, 1.40, 104, 'Available'),
(2378, '8A', 1, 1.00, 104, 'Available'),
(2379, '8B', 2, 1.20, 104, 'Available'),
(2380, '8C', 3, 1.40, 104, 'Available'),
(2381, '9A', 1, 1.00, 104, 'Available'),
(2382, '9B', 2, 1.20, 104, 'Available'),
(2383, '9C', 3, 1.40, 104, 'Available'),
(2384, '10A', 1, 1.00, 104, 'Available'),
(2385, '10B', 2, 1.20, 104, 'Available'),
(2386, '10C', 3, 1.40, 104, 'Available'),
(2387, '1A', 1, 1.00, 105, 'Available'),
(2388, '1B', 2, 1.20, 105, 'Available'),
(2389, '2A', 1, 1.00, 105, 'Available'),
(2390, '2B', 2, 1.20, 105, 'Available'),
(2391, '3A', 1, 1.00, 105, 'Available'),
(2392, '3B', 2, 1.20, 105, 'Available'),
(2393, '4A', 1, 1.00, 105, 'Available'),
(2394, '4B', 2, 1.20, 105, 'Available'),
(2395, '5A', 1, 1.00, 105, 'Available'),
(2396, '5B', 2, 1.20, 105, 'Available'),
(2397, '6A', 1, 1.00, 105, 'Available'),
(2398, '6B', 2, 1.20, 105, 'Available'),
(2399, '7A', 1, 1.00, 105, 'Available'),
(2400, '7B', 2, 1.20, 105, 'Available'),
(2401, '8A', 1, 1.00, 105, 'Available'),
(2402, '8B', 2, 1.20, 105, 'Available'),
(2403, '9A', 1, 1.00, 105, 'Available'),
(2404, '9B', 2, 1.20, 105, 'Available'),
(2405, '10A', 1, 1.00, 105, 'Available'),
(2406, '10B', 2, 1.20, 105, 'Available'),
(2407, '1A', 1, 1.00, 106, 'Available'),
(2408, '1B', 2, 1.20, 106, 'Available'),
(2409, '2A', 1, 1.00, 106, 'Available'),
(2410, '2B', 2, 1.20, 106, 'Available'),
(2411, '3A', 1, 1.00, 106, 'Available'),
(2412, '3B', 2, 1.20, 106, 'Available'),
(2413, '4A', 1, 1.00, 106, 'Available'),
(2414, '4B', 2, 1.20, 106, 'Available'),
(2415, '5A', 1, 1.00, 106, 'Available'),
(2416, '5B', 2, 1.20, 106, 'Available'),
(2417, '6A', 1, 1.00, 106, 'Available'),
(2418, '6B', 2, 1.20, 106, 'Available'),
(2419, '7A', 1, 1.00, 106, 'Available'),
(2420, '7B', 2, 1.20, 106, 'Available'),
(2421, '8A', 1, 1.00, 106, 'Available'),
(2422, '8B', 2, 1.20, 106, 'Available'),
(2423, '9A', 1, 1.00, 106, 'Available'),
(2424, '9B', 2, 1.20, 106, 'Available'),
(2425, '10A', 1, 1.00, 106, 'Available'),
(2426, '10B', 2, 1.20, 106, 'Available');

-- --------------------------------------------------------

--
-- Table structure for table `station`
--

CREATE TABLE `station` (
  `station_id` int(11) NOT NULL,
  `station_name` varchar(255) NOT NULL,
  `location` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `station`
--

INSERT INTO `station` (`station_id`, `station_name`, `location`) VALUES
(1, 'Hà Nội', 'Hà Nội'),
(2, 'Phủ Lý', 'Hà Nam'),
(3, 'Nam Định', 'Nam Định'),
(4, 'Ninh Bình', 'Ninh Bình'),
(5, 'Bỉm Sơn', 'Thanh Hóa'),
(6, 'Thanh Hóa', 'Thanh Hóa'),
(7, 'Minh Khôi', 'Thanh Hóa'),
(8, 'Chợ Sy', 'Nghệ An'),
(9, 'Vinh', 'Nghệ An'),
(10, 'Yên Trung', 'Nghệ An'),
(11, 'Hương Phố', 'Hà Tĩnh'),
(12, 'Đồng Lê', 'Quảng Bình'),
(13, 'Minh Lệ', 'Quảng Bình'),
(14, 'Đồng Hới', 'Quảng Bình'),
(15, 'Mỹ Đức', 'Quảng Bình'),
(16, 'Mỹ Trạch', 'Quảng Bình'),
(17, 'Đông Hà', 'Quảng Trị'),
(18, 'Huế', 'Thừa Thiên Huế'),
(19, 'Đà Nẵng', 'Đà Nẵng'),
(20, 'Tam Kỳ', 'Quảng Nam'),
(21, 'Quảng Ngãi', 'Quảng Ngãi'),
(22, 'Diêu Trì', 'Bình Định'),
(23, 'La Hai', 'Phú Yên'),
(24, 'Tuy Hòa', 'Phú Yên'),
(25, 'Giã', 'Khánh Hòa'),
(26, 'Ninh Hòa', 'Khánh Hòa'),
(27, 'Nha Trang', 'Khánh Hòa'),
(28, 'Tháp Chàm', 'Ninh Thuận'),
(29, 'Bình Thuận', 'Bình Thuận'),
(30, 'Suối Kiết', 'Bình Thuận'),
(31, 'Long Khánh', 'Đồng Nai'),
(32, 'Biên Hòa', 'Đồng Nai'),
(33, 'Dĩ An', 'Bình Dương'),
(34, 'Sài Gòn', 'TP.HCM');

-- --------------------------------------------------------

--
-- Table structure for table `ticket`
--

CREATE TABLE `ticket` (
  `ticket_id` int(11) NOT NULL,
  `purchase_time` datetime(6) NOT NULL,
  `price` decimal(38,2) DEFAULT NULL,
  `discount` decimal(38,2) DEFAULT NULL,
  `total_price` decimal(38,2) DEFAULT NULL,
  `ticket_status` varchar(255) NOT NULL,
  `trip_id` int(11) NOT NULL,
  `seat_id` int(11) NOT NULL,
  `departure_station_id` int(11) NOT NULL,
  `arrival_station_id` int(11) NOT NULL,
  `passenger_id` int(11) DEFAULT NULL,
  `customer_id` int(11) DEFAULT NULL,
  `reservation_code_id` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `ticket`
--

INSERT INTO `ticket` (`ticket_id`, `purchase_time`, `price`, `discount`, `total_price`, `ticket_status`, `trip_id`, `seat_id`, `departure_station_id`, `arrival_station_id`, `passenger_id`, `customer_id`, `reservation_code_id`) VALUES
(11, '2025-04-14 10:27:04.000000', 825000.00, 0.10, 742500.00, 'Booked', 19, 1483, 1, 34, 11, 9, 7),
(12, '2025-04-15 13:59:46.000000', 825000.00, 0.10, 742500.00, 'Booked', 19, 1485, 1, 34, 12, 10, 8),
(13, '2025-04-15 14:04:05.000000', 825000.00, 0.10, 742500.00, 'Booked', 20, 1627, 1, 34, 13, 11, 9),
(14, '2025-04-15 14:04:27.000000', 825000.00, 0.10, 742500.00, 'Booked', 20, 1628, 1, 34, 14, 12, 10);

-- --------------------------------------------------------

--
-- Table structure for table `ticket_type`
--

CREATE TABLE `ticket_type` (
  `ticket_type_id` int(11) NOT NULL,
  `ticket_type_name` varchar(255) NOT NULL,
  `discount_rate` decimal(38,2) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `ticket_type`
--

INSERT INTO `ticket_type` (`ticket_type_id`, `ticket_type_name`, `discount_rate`) VALUES
(1, 'Người lớn', 0.00),
(2, 'Trẻ em', 40.00),
(3, 'Sinh viên', 20.00),
(4, 'Người cao tuổi', 30.00);

-- --------------------------------------------------------

--
-- Table structure for table `train`
--

CREATE TABLE `train` (
  `train_id` int(11) NOT NULL,
  `train_name` varchar(255) NOT NULL,
  `route_id` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `train`
--

INSERT INTO `train` (`train_id`, `train_name`, `route_id`) VALUES
(1, 'SE1', 1),
(2, 'SE2', 2),
(3, 'SE3', 1),
(4, 'SE4', 2),
(5, 'SE5', 1),
(6, 'SE6', 2),
(7, 'SE7', 1),
(8, 'SE8', 2),
(9, 'SE9', 1),
(10, 'SE02', 1);

-- --------------------------------------------------------

--
-- Table structure for table `train_schedule`
--

CREATE TABLE `train_schedule` (
  `train_schedule_id` int(11) NOT NULL,
  `station_id` int(11) NOT NULL,
  `train_id` int(11) NOT NULL,
  `arrival_time` time(6) DEFAULT NULL,
  `departure_time` time(6) DEFAULT NULL,
  `ordinal_number` int(11) NOT NULL,
  `distance` decimal(38,2) NOT NULL,
  `day` int(11) NOT NULL DEFAULT 1
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `train_schedule`
--

INSERT INTO `train_schedule` (`train_schedule_id`, `station_id`, `train_id`, `arrival_time`, `departure_time`, `ordinal_number`, `distance`, `day`) VALUES
(1, 1, 1, '06:00:00.000000', '06:05:00.000000', 1, 0.00, 1),
(2, 2, 1, '07:00:00.000000', '07:02:00.000000', 2, 50.00, 1),
(3, 3, 1, '07:55:00.000000', '07:57:00.000000', 3, 100.00, 1),
(4, 4, 1, '08:50:00.000000', '08:52:00.000000', 4, 150.00, 1),
(5, 5, 1, '09:45:00.000000', '09:46:00.000000', 5, 200.00, 1),
(6, 6, 1, '10:40:00.000000', '10:45:00.000000', 6, 250.00, 1),
(7, 7, 1, '11:35:00.000000', '11:36:00.000000', 7, 300.00, 1),
(8, 8, 1, '12:30:00.000000', '12:31:00.000000', 8, 350.00, 1),
(9, 9, 1, '13:25:00.000000', '13:30:00.000000', 9, 400.00, 1),
(10, 10, 1, '14:25:00.000000', '14:26:00.000000', 10, 450.00, 1),
(11, 11, 1, '15:20:00.000000', '15:21:00.000000', 11, 500.00, 1),
(12, 12, 1, '16:15:00.000000', '16:17:00.000000', 12, 550.00, 1),
(13, 13, 1, '17:10:00.000000', '17:11:00.000000', 13, 600.00, 1),
(14, 14, 1, '18:00:00.000000', '18:05:00.000000', 14, 650.00, 1),
(15, 15, 1, '18:55:00.000000', '18:56:00.000000', 15, 700.00, 1),
(16, 16, 1, '19:45:00.000000', '19:46:00.000000', 16, 750.00, 1),
(17, 17, 1, '20:35:00.000000', '20:40:00.000000', 17, 800.00, 1),
(18, 18, 1, '21:30:00.000000', '21:35:00.000000', 18, 850.00, 1),
(19, 19, 1, '22:30:00.000000', '22:40:00.000000', 19, 900.00, 1),
(20, 20, 1, '23:30:00.000000', '23:31:00.000000', 20, 950.00, 1),
(21, 21, 1, '00:25:00.000000', '00:26:00.000000', 21, 1000.00, 2),
(22, 22, 1, '01:20:00.000000', '01:25:00.000000', 22, 1050.00, 2),
(23, 23, 1, '02:15:00.000000', '02:16:00.000000', 23, 1100.00, 2),
(24, 24, 1, '03:05:00.000000', '03:07:00.000000', 24, 1150.00, 2),
(25, 25, 1, '04:00:00.000000', '04:01:00.000000', 25, 1200.00, 2),
(26, 26, 1, '04:55:00.000000', '04:56:00.000000', 26, 1250.00, 2),
(27, 27, 1, '05:45:00.000000', '05:50:00.000000', 27, 1300.00, 2),
(28, 28, 1, '06:40:00.000000', '06:41:00.000000', 28, 1350.00, 2),
(29, 29, 1, '07:35:00.000000', '07:36:00.000000', 29, 1400.00, 2),
(30, 30, 1, '08:30:00.000000', '08:31:00.000000', 30, 1450.00, 2),
(31, 31, 1, '09:20:00.000000', '09:21:00.000000', 31, 1500.00, 2),
(32, 32, 1, '10:10:00.000000', '10:11:00.000000', 32, 1550.00, 2),
(33, 33, 1, '11:00:00.000000', '11:01:00.000000', 33, 1600.00, 2),
(34, 34, 1, '11:50:00.000000', '11:50:00.000000', 34, 1650.00, 2),
(35, 34, 2, '06:00:00.000000', '06:05:00.000000', 1, 0.00, 1),
(36, 33, 2, '06:55:00.000000', '06:56:00.000000', 2, 50.00, 1),
(37, 32, 2, '07:45:00.000000', '07:46:00.000000', 3, 100.00, 1),
(38, 31, 2, '08:35:00.000000', '08:36:00.000000', 4, 150.00, 1),
(39, 30, 2, '09:25:00.000000', '09:26:00.000000', 5, 200.00, 1),
(40, 29, 2, '10:15:00.000000', '10:16:00.000000', 6, 250.00, 1),
(41, 28, 2, '11:05:00.000000', '11:06:00.000000', 7, 300.00, 1),
(42, 27, 2, '11:55:00.000000', '12:00:00.000000', 8, 350.00, 1),
(43, 26, 2, '12:50:00.000000', '12:51:00.000000', 9, 400.00, 1),
(44, 25, 2, '13:40:00.000000', '13:41:00.000000', 10, 450.00, 1),
(45, 24, 2, '14:30:00.000000', '14:31:00.000000', 11, 500.00, 1),
(46, 23, 2, '15:20:00.000000', '15:21:00.000000', 12, 550.00, 1),
(47, 22, 2, '16:10:00.000000', '16:11:00.000000', 13, 600.00, 1),
(48, 21, 2, '17:00:00.000000', '17:01:00.000000', 14, 650.00, 1),
(49, 20, 2, '17:50:00.000000', '17:51:00.000000', 15, 700.00, 1),
(50, 19, 2, '18:40:00.000000', '18:41:00.000000', 16, 750.00, 1),
(51, 18, 2, '19:30:00.000000', '19:31:00.000000', 17, 800.00, 1),
(52, 17, 2, '20:20:00.000000', '20:21:00.000000', 18, 850.00, 1),
(53, 16, 2, '21:10:00.000000', '21:11:00.000000', 19, 900.00, 1),
(54, 15, 2, '22:00:00.000000', '22:01:00.000000', 20, 950.00, 1),
(55, 14, 2, '22:50:00.000000', '22:51:00.000000', 21, 1000.00, 1),
(56, 13, 2, '23:40:00.000000', '23:41:00.000000', 22, 1050.00, 1),
(57, 12, 2, '00:30:00.000000', '00:31:00.000000', 23, 1100.00, 1),
(58, 11, 2, '01:20:00.000000', '01:21:00.000000', 24, 1150.00, 1),
(59, 10, 2, '02:10:00.000000', '02:11:00.000000', 25, 1200.00, 1),
(60, 9, 2, '03:00:00.000000', '03:01:00.000000', 26, 1250.00, 1),
(61, 8, 2, '03:50:00.000000', '03:51:00.000000', 27, 1300.00, 1),
(62, 7, 2, '04:40:00.000000', '04:41:00.000000', 28, 1350.00, 1),
(63, 6, 2, '05:30:00.000000', '05:31:00.000000', 29, 1400.00, 2),
(64, 5, 2, '06:20:00.000000', '06:21:00.000000', 30, 1450.00, 2),
(65, 4, 2, '07:10:00.000000', '07:11:00.000000', 31, 1500.00, 2),
(66, 3, 2, '08:00:00.000000', '08:01:00.000000', 32, 1550.00, 2),
(67, 2, 2, '08:50:00.000000', '08:51:00.000000', 33, 1600.00, 2),
(68, 1, 2, '09:40:00.000000', '09:41:00.000000', 34, 1650.00, 2),
(69, 1, 3, '09:00:00.000000', '09:00:00.000000', 1, 0.00, 1),
(70, 2, 3, '10:30:00.000000', '10:32:00.000000', 2, 50.00, 1),
(71, 3, 3, '12:15:00.000000', '12:17:00.000000', 3, 100.00, 1),
(72, 4, 3, '13:45:00.000000', '13:47:00.000000', 4, 150.00, 1),
(73, 5, 3, '15:15:00.000000', '15:17:00.000000', 5, 200.00, 1),
(74, 6, 3, '16:45:00.000000', '16:47:00.000000', 6, 250.00, 1),
(75, 7, 3, '18:15:00.000000', '18:17:00.000000', 7, 300.00, 1),
(76, 8, 3, '19:45:00.000000', '19:47:00.000000', 8, 350.00, 1),
(77, 9, 3, '21:15:00.000000', '21:17:00.000000', 9, 400.00, 1),
(78, 10, 3, '22:45:00.000000', '22:47:00.000000', 10, 450.00, 1),
(79, 11, 3, '00:15:00.000000', '00:17:00.000000', 11, 500.00, 1),
(80, 12, 3, '01:45:00.000000', '01:47:00.000000', 12, 550.00, 1),
(81, 13, 3, '03:15:00.000000', '03:17:00.000000', 13, 600.00, 1),
(82, 14, 3, '04:45:00.000000', '04:47:00.000000', 14, 650.00, 1),
(83, 15, 3, '06:15:00.000000', '06:17:00.000000', 15, 700.00, 1),
(84, 16, 3, '07:45:00.000000', '07:47:00.000000', 16, 750.00, 1),
(85, 17, 3, '09:15:00.000000', '09:17:00.000000', 17, 800.00, 1),
(86, 18, 3, '10:45:00.000000', '10:47:00.000000', 18, 850.00, 1),
(87, 19, 3, '12:15:00.000000', '12:17:00.000000', 19, 900.00, 1),
(88, 20, 3, '13:45:00.000000', '13:47:00.000000', 20, 950.00, 1),
(89, 21, 3, '15:15:00.000000', '15:17:00.000000', 21, 1000.00, 1),
(90, 22, 3, '16:45:00.000000', '16:47:00.000000', 22, 1050.00, 1),
(91, 23, 3, '18:15:00.000000', '18:17:00.000000', 23, 1100.00, 1),
(92, 24, 3, '19:45:00.000000', '19:47:00.000000', 24, 1150.00, 1),
(93, 25, 3, '21:15:00.000000', '21:17:00.000000', 25, 1200.00, 2),
(94, 26, 3, '22:45:00.000000', '22:47:00.000000', 26, 1250.00, 2),
(95, 27, 3, '00:15:00.000000', '00:17:00.000000', 27, 1300.00, 2),
(96, 28, 3, '01:45:00.000000', '01:47:00.000000', 28, 1350.00, 2),
(97, 29, 3, '03:15:00.000000', '03:17:00.000000', 29, 1400.00, 2),
(98, 30, 3, '04:45:00.000000', '04:47:00.000000', 30, 1450.00, 2),
(99, 31, 3, '06:15:00.000000', '06:17:00.000000', 31, 1500.00, 2),
(100, 32, 3, '07:45:00.000000', '07:47:00.000000', 32, 1550.00, 2),
(101, 33, 3, '09:15:00.000000', '09:17:00.000000', 33, 1600.00, 2),
(102, 34, 3, '10:45:00.000000', '10:45:00.000000', 34, 1650.00, 2),
(103, 34, 4, '21:00:00.000000', '21:00:00.000000', 1, 0.00, 1),
(104, 33, 4, '22:30:00.000000', '22:32:00.000000', 2, 50.00, 1),
(105, 32, 4, '00:15:00.000000', '00:17:00.000000', 3, 100.00, 1),
(106, 31, 4, '01:45:00.000000', '01:47:00.000000', 4, 150.00, 1),
(107, 30, 4, '03:15:00.000000', '03:17:00.000000', 5, 200.00, 1),
(108, 29, 4, '04:45:00.000000', '04:47:00.000000', 6, 250.00, 1),
(109, 28, 4, '06:15:00.000000', '06:17:00.000000', 7, 300.00, 1),
(110, 27, 4, '07:45:00.000000', '07:47:00.000000', 8, 350.00, 1),
(111, 26, 4, '09:15:00.000000', '09:17:00.000000', 9, 400.00, 1),
(112, 25, 4, '10:45:00.000000', '10:47:00.000000', 10, 450.00, 1),
(113, 24, 4, '12:15:00.000000', '12:17:00.000000', 11, 500.00, 1),
(114, 23, 4, '13:45:00.000000', '13:47:00.000000', 12, 550.00, 1),
(115, 22, 4, '15:15:00.000000', '15:17:00.000000', 13, 600.00, 1),
(116, 21, 4, '16:45:00.000000', '16:47:00.000000', 14, 650.00, 1),
(117, 20, 4, '18:15:00.000000', '18:17:00.000000', 15, 700.00, 1),
(118, 19, 4, '19:45:00.000000', '19:47:00.000000', 16, 750.00, 1),
(119, 18, 4, '21:15:00.000000', '21:17:00.000000', 17, 800.00, 1),
(120, 17, 4, '22:45:00.000000', '22:47:00.000000', 18, 850.00, 1),
(121, 16, 4, '00:15:00.000000', '00:17:00.000000', 19, 900.00, 1),
(122, 15, 4, '01:45:00.000000', '01:47:00.000000', 20, 950.00, 1),
(123, 14, 4, '03:15:00.000000', '03:17:00.000000', 21, 1000.00, 1),
(124, 13, 4, '04:45:00.000000', '04:47:00.000000', 22, 1050.00, 1),
(125, 12, 4, '06:15:00.000000', '06:17:00.000000', 23, 1100.00, 1),
(126, 11, 4, '07:45:00.000000', '07:47:00.000000', 24, 1150.00, 1),
(127, 10, 4, '09:15:00.000000', '09:17:00.000000', 25, 1200.00, 1),
(128, 9, 4, '10:45:00.000000', '10:47:00.000000', 26, 1250.00, 1),
(129, 8, 4, '12:15:00.000000', '12:17:00.000000', 27, 1300.00, 1),
(130, 7, 4, '13:45:00.000000', '13:47:00.000000', 28, 1350.00, 1),
(131, 6, 4, '15:15:00.000000', '15:17:00.000000', 29, 1400.00, 2),
(132, 5, 4, '16:45:00.000000', '16:47:00.000000', 30, 1450.00, 2),
(133, 4, 4, '18:15:00.000000', '18:17:00.000000', 31, 1500.00, 2),
(134, 3, 4, '19:45:00.000000', '19:47:00.000000', 32, 1550.00, 2),
(135, 2, 4, '21:15:00.000000', '21:17:00.000000', 33, 1600.00, 2),
(136, 1, 4, '22:45:00.000000', '22:45:00.000000', 34, 1650.00, 2),
(137, 1, 5, '15:00:00.000000', '15:00:00.000000', 1, 0.00, 1),
(138, 2, 5, '16:30:00.000000', '16:32:00.000000', 2, 50.00, 1),
(139, 3, 5, '18:15:00.000000', '18:17:00.000000', 3, 100.00, 1),
(140, 4, 5, '19:45:00.000000', '19:47:00.000000', 4, 150.00, 1),
(141, 5, 5, '21:15:00.000000', '21:17:00.000000', 5, 200.00, 1),
(142, 6, 5, '22:45:00.000000', '22:47:00.000000', 6, 250.00, 1),
(143, 7, 5, '00:15:00.000000', '00:17:00.000000', 7, 300.00, 1),
(144, 8, 5, '01:45:00.000000', '01:47:00.000000', 8, 350.00, 1),
(145, 9, 5, '03:15:00.000000', '03:17:00.000000', 9, 400.00, 1),
(146, 10, 5, '04:45:00.000000', '04:47:00.000000', 10, 450.00, 1),
(147, 11, 5, '06:15:00.000000', '06:17:00.000000', 11, 500.00, 1),
(148, 12, 5, '07:45:00.000000', '07:47:00.000000', 12, 550.00, 1),
(149, 13, 5, '09:15:00.000000', '09:17:00.000000', 13, 600.00, 1),
(150, 14, 5, '10:45:00.000000', '10:47:00.000000', 14, 650.00, 1),
(151, 15, 5, '12:15:00.000000', '12:17:00.000000', 15, 700.00, 1),
(152, 16, 5, '13:45:00.000000', '13:47:00.000000', 16, 750.00, 1),
(153, 17, 5, '15:15:00.000000', '15:17:00.000000', 17, 800.00, 1),
(154, 18, 5, '16:45:00.000000', '16:47:00.000000', 18, 850.00, 1),
(155, 19, 5, '18:15:00.000000', '18:17:00.000000', 19, 900.00, 1),
(156, 20, 5, '19:45:00.000000', '19:47:00.000000', 20, 950.00, 1),
(157, 21, 5, '21:15:00.000000', '21:17:00.000000', 21, 1000.00, 1),
(158, 22, 5, '22:45:00.000000', '22:47:00.000000', 22, 1050.00, 1),
(159, 23, 5, '00:15:00.000000', '00:17:00.000000', 23, 1100.00, 2),
(160, 24, 5, '01:45:00.000000', '01:47:00.000000', 24, 1150.00, 2),
(161, 25, 5, '03:15:00.000000', '03:17:00.000000', 25, 1200.00, 2),
(162, 26, 5, '04:45:00.000000', '04:47:00.000000', 26, 1250.00, 2),
(163, 27, 5, '06:15:00.000000', '06:17:00.000000', 27, 1300.00, 2),
(164, 28, 5, '07:45:00.000000', '07:47:00.000000', 28, 1350.00, 2),
(165, 29, 5, '09:15:00.000000', '09:17:00.000000', 29, 1400.00, 2),
(166, 30, 5, '10:45:00.000000', '10:47:00.000000', 30, 1450.00, 2),
(167, 31, 5, '12:15:00.000000', '12:17:00.000000', 31, 1500.00, 2),
(168, 32, 5, '13:45:00.000000', '13:47:00.000000', 32, 1550.00, 2),
(169, 33, 5, '15:15:00.000000', '15:17:00.000000', 33, 1600.00, 2),
(170, 34, 5, '16:45:00.000000', '16:45:00.000000', 34, 1650.00, 2),
(171, 34, 6, '03:00:00.000000', '03:00:00.000000', 1, 0.00, 1),
(172, 33, 6, '04:30:00.000000', '04:32:00.000000', 2, 50.00, 1),
(173, 32, 6, '06:15:00.000000', '06:17:00.000000', 3, 100.00, 1),
(174, 31, 6, '07:45:00.000000', '07:47:00.000000', 4, 150.00, 1),
(175, 30, 6, '09:15:00.000000', '09:17:00.000000', 5, 200.00, 1),
(176, 29, 6, '10:45:00.000000', '10:47:00.000000', 6, 250.00, 1),
(177, 28, 6, '12:15:00.000000', '12:17:00.000000', 7, 300.00, 1),
(178, 27, 6, '13:45:00.000000', '13:47:00.000000', 8, 350.00, 1),
(179, 26, 6, '15:15:00.000000', '15:17:00.000000', 9, 400.00, 1),
(180, 25, 6, '16:45:00.000000', '16:47:00.000000', 10, 450.00, 1),
(181, 24, 6, '18:15:00.000000', '18:17:00.000000', 11, 500.00, 1),
(182, 23, 6, '19:45:00.000000', '19:47:00.000000', 12, 550.00, 1),
(183, 22, 6, '21:15:00.000000', '21:17:00.000000', 13, 600.00, 1),
(184, 21, 6, '22:45:00.000000', '22:47:00.000000', 14, 650.00, 1),
(185, 20, 6, '00:15:00.000000', '00:17:00.000000', 15, 700.00, 1),
(186, 19, 6, '01:45:00.000000', '01:47:00.000000', 16, 750.00, 1),
(187, 18, 6, '03:15:00.000000', '03:17:00.000000', 17, 800.00, 1),
(188, 17, 6, '04:45:00.000000', '04:47:00.000000', 18, 850.00, 1),
(189, 16, 6, '06:15:00.000000', '06:17:00.000000', 19, 900.00, 1),
(190, 15, 6, '07:45:00.000000', '07:47:00.000000', 20, 950.00, 1),
(191, 14, 6, '09:15:00.000000', '09:17:00.000000', 21, 1000.00, 1),
(192, 13, 6, '10:45:00.000000', '10:47:00.000000', 22, 1050.00, 1),
(193, 12, 6, '12:15:00.000000', '12:17:00.000000', 23, 1100.00, 1),
(194, 11, 6, '13:45:00.000000', '13:47:00.000000', 24, 1150.00, 1),
(195, 10, 6, '15:15:00.000000', '15:17:00.000000', 25, 1200.00, 1),
(196, 9, 6, '16:45:00.000000', '16:47:00.000000', 26, 1250.00, 1),
(197, 8, 6, '18:15:00.000000', '18:17:00.000000', 27, 1300.00, 1),
(198, 7, 6, '19:45:00.000000', '19:47:00.000000', 28, 1350.00, 1),
(199, 6, 6, '21:15:00.000000', '21:17:00.000000', 29, 1400.00, 1),
(200, 5, 6, '22:45:00.000000', '22:47:00.000000', 30, 1450.00, 1),
(201, 4, 6, '23:00:00.000000', '23:05:00.000000', 31, 1500.00, 1),
(202, 3, 6, '00:00:00.000000', '00:04:00.000000', 32, 1550.00, 2),
(203, 2, 6, '01:15:00.000000', '01:17:00.000000', 33, 1600.00, 2),
(204, 1, 6, '02:45:00.000000', '02:45:00.000000', 34, 1650.00, 2),
(205, 1, 7, '18:00:00.000000', '18:00:00.000000', 1, 0.00, 1),
(206, 2, 7, '18:47:00.000000', '18:54:00.000000', 2, 50.00, 1),
(207, 3, 7, '19:41:00.000000', '19:48:00.000000', 3, 100.00, 1),
(208, 4, 7, '20:35:00.000000', '20:42:00.000000', 4, 150.00, 1),
(209, 5, 7, '21:29:00.000000', '21:36:00.000000', 5, 200.00, 1),
(210, 6, 7, '22:23:00.000000', '22:30:00.000000', 6, 250.00, 1),
(211, 7, 7, '23:17:00.000000', '23:24:00.000000', 7, 300.00, 1),
(212, 8, 7, '00:11:00.000000', '00:18:00.000000', 8, 350.00, 2),
(213, 9, 7, '01:05:00.000000', '01:12:00.000000', 9, 400.00, 2),
(214, 10, 7, '01:59:00.000000', '02:06:00.000000', 10, 450.00, 2),
(215, 11, 7, '02:53:00.000000', '03:00:00.000000', 11, 500.00, 2),
(216, 12, 7, '03:47:00.000000', '03:54:00.000000', 12, 550.00, 2),
(217, 13, 7, '04:41:00.000000', '04:48:00.000000', 13, 600.00, 2),
(218, 14, 7, '05:35:00.000000', '05:42:00.000000', 14, 650.00, 2),
(219, 15, 7, '06:29:00.000000', '06:36:00.000000', 15, 700.00, 2),
(220, 16, 7, '07:23:00.000000', '07:30:00.000000', 16, 750.00, 2),
(221, 17, 7, '08:17:00.000000', '08:24:00.000000', 17, 800.00, 2),
(222, 18, 7, '09:11:00.000000', '09:18:00.000000', 18, 850.00, 2),
(223, 19, 7, '10:05:00.000000', '10:12:00.000000', 19, 900.00, 2),
(224, 20, 7, '10:59:00.000000', '11:06:00.000000', 20, 950.00, 2),
(225, 21, 7, '11:53:00.000000', '12:00:00.000000', 21, 1000.00, 2),
(226, 22, 7, '12:47:00.000000', '12:54:00.000000', 22, 1050.00, 2),
(227, 23, 7, '13:41:00.000000', '13:48:00.000000', 23, 1100.00, 2),
(228, 24, 7, '14:35:00.000000', '14:42:00.000000', 24, 1150.00, 2),
(229, 25, 7, '15:29:00.000000', '15:36:00.000000', 25, 1200.00, 2),
(230, 26, 7, '16:23:00.000000', '16:30:00.000000', 26, 1250.00, 2),
(231, 27, 7, '17:17:00.000000', '17:24:00.000000', 27, 1300.00, 2),
(232, 28, 7, '18:11:00.000000', '18:18:00.000000', 28, 1350.00, 2),
(233, 29, 7, '19:05:00.000000', '19:12:00.000000', 29, 1400.00, 2),
(234, 30, 7, '19:59:00.000000', '20:06:00.000000', 30, 1450.00, 2),
(235, 31, 7, '20:53:00.000000', '21:00:00.000000', 31, 1500.00, 2),
(236, 32, 7, '21:47:00.000000', '21:54:00.000000', 32, 1550.00, 2),
(237, 33, 7, '22:41:00.000000', '22:48:00.000000', 33, 1600.00, 2),
(238, 34, 7, '23:35:00.000000', '00:00:00.000000', 34, 1650.00, 2),
(239, 34, 8, '00:00:00.000000', '18:00:00.000000', 1, 0.00, 1),
(240, 33, 8, '18:54:00.000000', '19:01:00.000000', 2, 50.00, 1),
(241, 32, 8, '19:55:00.000000', '20:02:00.000000', 3, 100.00, 1),
(242, 31, 8, '20:56:00.000000', '21:03:00.000000', 4, 150.00, 1),
(243, 30, 8, '21:57:00.000000', '22:04:00.000000', 5, 200.00, 1),
(244, 29, 8, '22:58:00.000000', '23:05:00.000000', 6, 250.00, 1),
(245, 28, 8, '23:59:00.000000', '00:06:00.000000', 7, 300.00, 2),
(246, 27, 8, '01:00:00.000000', '01:07:00.000000', 8, 350.00, 2),
(247, 26, 8, '02:01:00.000000', '02:08:00.000000', 9, 400.00, 2),
(248, 25, 8, '03:02:00.000000', '03:09:00.000000', 10, 450.00, 2),
(249, 24, 8, '04:03:00.000000', '04:10:00.000000', 11, 500.00, 2),
(250, 23, 8, '05:04:00.000000', '05:11:00.000000', 12, 550.00, 2),
(251, 22, 8, '06:05:00.000000', '06:12:00.000000', 13, 600.00, 2),
(252, 21, 8, '07:06:00.000000', '07:13:00.000000', 14, 650.00, 2),
(253, 20, 8, '08:07:00.000000', '08:14:00.000000', 15, 700.00, 2),
(254, 19, 8, '09:08:00.000000', '09:15:00.000000', 16, 750.00, 2),
(255, 18, 8, '10:09:00.000000', '10:16:00.000000', 17, 800.00, 2),
(256, 17, 8, '11:10:00.000000', '11:17:00.000000', 18, 850.00, 2),
(257, 16, 8, '12:11:00.000000', '12:18:00.000000', 19, 900.00, 2),
(258, 15, 8, '13:12:00.000000', '13:19:00.000000', 20, 950.00, 2),
(259, 14, 8, '14:13:00.000000', '14:20:00.000000', 21, 1000.00, 2),
(260, 13, 8, '15:14:00.000000', '15:21:00.000000', 22, 1050.00, 2),
(261, 12, 8, '16:15:00.000000', '16:22:00.000000', 23, 1100.00, 2),
(262, 11, 8, '17:16:00.000000', '17:23:00.000000', 24, 1150.00, 2),
(263, 10, 8, '18:17:00.000000', '18:24:00.000000', 25, 1200.00, 2),
(264, 9, 8, '19:18:00.000000', '19:25:00.000000', 26, 1250.00, 2),
(265, 8, 8, '20:19:00.000000', '20:26:00.000000', 27, 1300.00, 2),
(266, 7, 8, '21:20:00.000000', '21:27:00.000000', 28, 1350.00, 2),
(267, 6, 8, '22:21:00.000000', '22:28:00.000000', 29, 1400.00, 2),
(268, 5, 8, '23:22:00.000000', '23:29:00.000000', 30, 1450.00, 2),
(269, 4, 8, '00:23:00.000000', '00:30:00.000000', 31, 1500.00, 2),
(270, 3, 8, '01:24:00.000000', '01:31:00.000000', 32, 1550.00, 2),
(271, 2, 8, '02:25:00.000000', '02:32:00.000000', 33, 1600.00, 2),
(272, 1, 8, '03:26:00.000000', '00:00:00.000000', 34, 1650.00, 2);

-- --------------------------------------------------------

--
-- Table structure for table `trip`
--

CREATE TABLE `trip` (
  `trip_id` int(11) NOT NULL,
  `train_id` int(11) NOT NULL,
  `base_price` decimal(38,2) NOT NULL,
  `trip_date` date NOT NULL,
  `trip_status` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `trip`
--

INSERT INTO `trip` (`trip_id`, `train_id`, `base_price`, `trip_date`, `trip_status`) VALUES
(19, 3, 500.00, '2025-06-15', 'Scheduled'),
(20, 1, 500.00, '2025-06-15', 'Scheduled'),
(21, 5, 500.00, '2025-06-15', 'Scheduled'),
(22, 7, 700.00, '2025-06-15', 'Scheduled'),
(23, 1, 100.00, '2025-05-08', 'Scheduled'),
(24, 2, 100.00, '2025-05-08', 'Scheduled');

-- --------------------------------------------------------

--
-- Table structure for table `users`
--

CREATE TABLE `users` (
  `user_id` int(11) NOT NULL,
  `email` varchar(255) NOT NULL,
  `user_name` varchar(255) NOT NULL,
  `password` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `users`
--

INSERT INTO `users` (`user_id`, `email`, `user_name`, `password`) VALUES
(1, 'admin@gmail.com', 'admin', '$2a$10$8HwEjqqHQQ14tOfj1RggcOxFcYK8Zu8NMAWFtnECVJ2GZ7MpwCFyO');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `carriage_list`
--
ALTER TABLE `carriage_list`
  ADD PRIMARY KEY (`carriage_list_id`),
  ADD KEY `compartment_id` (`compartment_id`),
  ADD KEY `trip_id` (`trip_id`);

--
-- Indexes for table `compartment`
--
ALTER TABLE `compartment`
  ADD PRIMARY KEY (`compartment_id`);

--
-- Indexes for table `customer`
--
ALTER TABLE `customer`
  ADD PRIMARY KEY (`customer_id`);

--
-- Indexes for table `passenger`
--
ALTER TABLE `passenger`
  ADD PRIMARY KEY (`passenger_id`),
  ADD KEY `ticket_type_id` (`ticket_type_id`);

--
-- Indexes for table `reservation_code`
--
ALTER TABLE `reservation_code`
  ADD PRIMARY KEY (`reservation_code_id`);

--
-- Indexes for table `route`
--
ALTER TABLE `route`
  ADD PRIMARY KEY (`route_id`);

--
-- Indexes for table `seat`
--
ALTER TABLE `seat`
  ADD PRIMARY KEY (`seat_id`),
  ADD KEY `carriage_list_id` (`carriage_list_id`);

--
-- Indexes for table `station`
--
ALTER TABLE `station`
  ADD PRIMARY KEY (`station_id`);

--
-- Indexes for table `ticket`
--
ALTER TABLE `ticket`
  ADD PRIMARY KEY (`ticket_id`),
  ADD KEY `passenger_id` (`passenger_id`),
  ADD KEY `customer_id` (`customer_id`),
  ADD KEY `reservation_code_id` (`reservation_code_id`),
  ADD KEY `trip_id` (`trip_id`),
  ADD KEY `seat_id` (`seat_id`),
  ADD KEY `departure_station_id` (`departure_station_id`),
  ADD KEY `arrival_station_id` (`arrival_station_id`);

--
-- Indexes for table `ticket_type`
--
ALTER TABLE `ticket_type`
  ADD PRIMARY KEY (`ticket_type_id`);

--
-- Indexes for table `train`
--
ALTER TABLE `train`
  ADD PRIMARY KEY (`train_id`),
  ADD KEY `route_id` (`route_id`);

--
-- Indexes for table `train_schedule`
--
ALTER TABLE `train_schedule`
  ADD PRIMARY KEY (`train_schedule_id`),
  ADD KEY `station_id` (`station_id`),
  ADD KEY `train_id` (`train_id`);

--
-- Indexes for table `trip`
--
ALTER TABLE `trip`
  ADD PRIMARY KEY (`trip_id`),
  ADD KEY `train_id` (`train_id`);

--
-- Indexes for table `users`
--
ALTER TABLE `users`
  ADD PRIMARY KEY (`user_id`),
  ADD UNIQUE KEY `email` (`email`),
  ADD UNIQUE KEY `user_name` (`user_name`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `carriage_list`
--
ALTER TABLE `carriage_list`
  MODIFY `carriage_list_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=107;

--
-- AUTO_INCREMENT for table `compartment`
--
ALTER TABLE `compartment`
  MODIFY `compartment_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- AUTO_INCREMENT for table `customer`
--
ALTER TABLE `customer`
  MODIFY `customer_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=13;

--
-- AUTO_INCREMENT for table `passenger`
--
ALTER TABLE `passenger`
  MODIFY `passenger_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=15;

--
-- AUTO_INCREMENT for table `reservation_code`
--
ALTER TABLE `reservation_code`
  MODIFY `reservation_code_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=11;

--
-- AUTO_INCREMENT for table `route`
--
ALTER TABLE `route`
  MODIFY `route_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- AUTO_INCREMENT for table `seat`
--
ALTER TABLE `seat`
  MODIFY `seat_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2427;

--
-- AUTO_INCREMENT for table `station`
--
ALTER TABLE `station`
  MODIFY `station_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=35;

--
-- AUTO_INCREMENT for table `ticket`
--
ALTER TABLE `ticket`
  MODIFY `ticket_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=18;

--
-- AUTO_INCREMENT for table `ticket_type`
--
ALTER TABLE `ticket_type`
  MODIFY `ticket_type_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;

--
-- AUTO_INCREMENT for table `train`
--
ALTER TABLE `train`
  MODIFY `train_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=11;

--
-- AUTO_INCREMENT for table `train_schedule`
--
ALTER TABLE `train_schedule`
  MODIFY `train_schedule_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=273;

--
-- AUTO_INCREMENT for table `trip`
--
ALTER TABLE `trip`
  MODIFY `trip_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=25;

--
-- AUTO_INCREMENT for table `users`
--
ALTER TABLE `users`
  MODIFY `user_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `carriage_list`
--
ALTER TABLE `carriage_list`
  ADD CONSTRAINT `carriage_list_ibfk_1` FOREIGN KEY (`compartment_id`) REFERENCES `compartment` (`compartment_id`),
  ADD CONSTRAINT `carriage_list_ibfk_2` FOREIGN KEY (`trip_id`) REFERENCES `trip` (`trip_id`);

--
-- Constraints for table `passenger`
--
ALTER TABLE `passenger`
  ADD CONSTRAINT `passenger_ibfk_1` FOREIGN KEY (`ticket_type_id`) REFERENCES `ticket_type` (`ticket_type_id`);

--
-- Constraints for table `seat`
--
ALTER TABLE `seat`
  ADD CONSTRAINT `seat_ibfk_1` FOREIGN KEY (`carriage_list_id`) REFERENCES `carriage_list` (`carriage_list_id`);

--
-- Constraints for table `ticket`
--
ALTER TABLE `ticket`
  ADD CONSTRAINT `ticket_ibfk_1` FOREIGN KEY (`passenger_id`) REFERENCES `passenger` (`passenger_id`),
  ADD CONSTRAINT `ticket_ibfk_2` FOREIGN KEY (`customer_id`) REFERENCES `customer` (`customer_id`),
  ADD CONSTRAINT `ticket_ibfk_3` FOREIGN KEY (`reservation_code_id`) REFERENCES `reservation_code` (`reservation_code_id`),
  ADD CONSTRAINT `ticket_ibfk_4` FOREIGN KEY (`trip_id`) REFERENCES `trip` (`trip_id`),
  ADD CONSTRAINT `ticket_ibfk_5` FOREIGN KEY (`seat_id`) REFERENCES `seat` (`seat_id`),
  ADD CONSTRAINT `ticket_ibfk_6` FOREIGN KEY (`departure_station_id`) REFERENCES `station` (`station_id`),
  ADD CONSTRAINT `ticket_ibfk_7` FOREIGN KEY (`arrival_station_id`) REFERENCES `station` (`station_id`);

--
-- Constraints for table `train`
--
ALTER TABLE `train`
  ADD CONSTRAINT `train_ibfk_1` FOREIGN KEY (`route_id`) REFERENCES `route` (`route_id`);

--
-- Constraints for table `train_schedule`
--
ALTER TABLE `train_schedule`
  ADD CONSTRAINT `train_schedule_ibfk_1` FOREIGN KEY (`station_id`) REFERENCES `station` (`station_id`),
  ADD CONSTRAINT `train_schedule_ibfk_2` FOREIGN KEY (`train_id`) REFERENCES `train` (`train_id`);

--
-- Constraints for table `trip`
--
ALTER TABLE `trip`
  ADD CONSTRAINT `trip_ibfk_1` FOREIGN KEY (`train_id`) REFERENCES `train` (`train_id`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
