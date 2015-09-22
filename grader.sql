-- phpMyAdmin SQL Dump
-- version 3.5.1
-- http://www.phpmyadmin.net
--
-- Host: localhost
-- Generation Time: Jul 10, 2014 at 01:58 AM
-- Server version: 5.5.24-log
-- PHP Version: 5.3.13

SET SQL_MODE="NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

--
-- Database: `grader`
--

-- --------------------------------------------------------

--
-- Table structure for table `admin`
--

CREATE TABLE IF NOT EXISTS `admin` (
  `id` int(2) NOT NULL,
  `name` varchar(30) NOT NULL,
  `webmail` varchar(30) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id` (`id`),
  UNIQUE KEY `webmail` (`webmail`),
  KEY `webmail_2` (`webmail`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `admin`
--

INSERT INTO `admin` (`id`, `name`, `webmail`) VALUES
(1, 'admin', 'admin');

-- --------------------------------------------------------

--
-- Table structure for table `comments`
--

CREATE TABLE IF NOT EXISTS `comments` (
  `doc_id` int(9) NOT NULL,
  `q_id` int(3) NOT NULL,
  `comment` longtext NOT NULL,
  `type` int(2) NOT NULL,
  `by` varchar(30) NOT NULL,
  `seq` int(2) NOT NULL,
  UNIQUE KEY `doc_id_2` (`doc_id`,`q_id`,`seq`),
  KEY `doc_id` (`doc_id`),
  KEY `q_id` (`q_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `comments`
--

INSERT INTO `comments` (`doc_id`, `q_id`, `comment`, `type`, `by`, `seq`) VALUES
(75, 4, ' Wrong answer', 2, 'gkd', 1);

-- --------------------------------------------------------

--
-- Table structure for table `courseallotment`
--

CREATE TABLE IF NOT EXISTS `courseallotment` (
  `num` char(5) NOT NULL,
  `cc_id` int(3) NOT NULL,
  UNIQUE KEY `num_2` (`num`,`cc_id`),
  KEY `num` (`num`),
  KEY `cc_id` (`cc_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `courseallotment`
--

INSERT INTO `courseallotment` (`num`, `cc_id`) VALUES
('MA101', 18),
('MA102', 19);

-- --------------------------------------------------------

--
-- Table structure for table `courses`
--

CREATE TABLE IF NOT EXISTS `courses` (
  `num` char(5) NOT NULL,
  `name` varchar(80) NOT NULL,
  PRIMARY KEY (`num`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `courses`
--

INSERT INTO `courses` (`num`, `name`) VALUES
('MA101', 'Maths 1'),
('MA102', 'Maths 2');

-- --------------------------------------------------------

--
-- Table structure for table `docs`
--

CREATE TABLE IF NOT EXISTS `docs` (
  `doc_id` int(9) NOT NULL AUTO_INCREMENT,
  `stud_rollNo` int(8) NOT NULL,
  `p_id` int(6) NOT NULL,
  PRIMARY KEY (`doc_id`),
  KEY `p_id` (`p_id`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=81 ;

--
-- Dumping data for table `docs`
--

INSERT INTO `docs` (`doc_id`, `stud_rollNo`, `p_id`) VALUES
(74, 10012001, 11),
(75, 10012002, 11),
(76, 10012003, 11),
(77, 10012004, 11),
(78, 10012005, 11),
(79, 10012333, 11),
(80, 10012334, 11);

-- --------------------------------------------------------

--
-- Table structure for table `exam`
--

CREATE TABLE IF NOT EXISTS `exam` (
  `c_id` char(5) NOT NULL,
  `p_id` int(6) NOT NULL AUTO_INCREMENT,
  `p_name` varchar(30) NOT NULL,
  `tot_marks` decimal(4,2) NOT NULL DEFAULT '0.00',
  `tot_ques` int(3) NOT NULL DEFAULT '0',
  PRIMARY KEY (`p_id`),
  KEY `c_id` (`c_id`),
  KEY `c_id_2` (`c_id`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=13 ;

--
-- Dumping data for table `exam`
--

INSERT INTO `exam` (`c_id`, `p_id`, `p_name`, `tot_marks`, `tot_ques`) VALUES
('MA101', 11, 'Quiz 1', '5.00', 1),
('MA101', 12, 'QUiz 2', '0.00', 0);

-- --------------------------------------------------------

--
-- Table structure for table `extrasheetmap`
--

CREATE TABLE IF NOT EXISTS `extrasheetmap` (
  `doc_id` int(9) NOT NULL,
  `sheet` int(3) NOT NULL,
  `q_id` int(3) NOT NULL,
  UNIQUE KEY `doc_id_4` (`doc_id`,`sheet`,`q_id`),
  KEY `doc_id` (`doc_id`),
  KEY `q_id` (`q_id`),
  KEY `doc_id_2` (`doc_id`),
  KEY `doc_id_3` (`doc_id`),
  KEY `q_id_2` (`q_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `extrasheetspec`
--

CREATE TABLE IF NOT EXISTS `extrasheetspec` (
  `p_id` int(6) NOT NULL,
  `sheet_list_id` int(8) NOT NULL,
  UNIQUE KEY `sheet_list_id` (`sheet_list_id`),
  UNIQUE KEY `p_id_4` (`p_id`,`sheet_list_id`),
  KEY `sheet_list_id_2` (`sheet_list_id`),
  KEY `p_id` (`p_id`),
  KEY `p_id_2` (`p_id`),
  KEY `sheet_list_id_3` (`sheet_list_id`),
  KEY `p_id_3` (`p_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `faculty`
--

CREATE TABLE IF NOT EXISTS `faculty` (
  `id` int(3) NOT NULL AUTO_INCREMENT,
  `name` varchar(30) NOT NULL,
  `webmail` varchar(30) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `webmail` (`webmail`),
  KEY `webmail_2` (`webmail`),
  KEY `webmail_3` (`webmail`),
  KEY `webmail_4` (`webmail`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=20 ;

--
-- Dumping data for table `faculty`
--

INSERT INTO `faculty` (`id`, `name`, `webmail`) VALUES
(18, 'Gautam Kr Das', 'gkd'),
(19, 'kvs', 'kvs');

-- --------------------------------------------------------

--
-- Table structure for table `gradergroups`
--

CREATE TABLE IF NOT EXISTS `gradergroups` (
  `grader_gp_id` int(3) NOT NULL,
  `grader_id` int(3) NOT NULL,
  UNIQUE KEY `grader_gp_id_2` (`grader_gp_id`,`grader_id`),
  KEY `grader_gp_id` (`grader_gp_id`),
  KEY `grader_id` (`grader_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `gradergroups`
--

INSERT INTO `gradergroups` (`grader_gp_id`, `grader_id`) VALUES
(40, 18);

-- --------------------------------------------------------

--
-- Table structure for table `gradersallotment`
--

CREATE TABLE IF NOT EXISTS `gradersallotment` (
  `p_id` int(6) NOT NULL,
  `q_id` int(3) NOT NULL,
  `grader_gp_id` int(3) NOT NULL AUTO_INCREMENT,
  `reconcilor_id` int(3) DEFAULT NULL,
  `dist` float NOT NULL,
  PRIMARY KEY (`grader_gp_id`),
  KEY `q_id` (`q_id`),
  KEY `reconcilor_id` (`reconcilor_id`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=41 ;

--
-- Dumping data for table `gradersallotment`
--

INSERT INTO `gradersallotment` (`p_id`, `q_id`, `grader_gp_id`, `reconcilor_id`, `dist`) VALUES
(11, 4, 40, NULL, 100);

-- --------------------------------------------------------

--
-- Table structure for table `jobs`
--

CREATE TABLE IF NOT EXISTS `jobs` (
  `id` int(8) NOT NULL AUTO_INCREMENT,
  `grader_id` int(3) NOT NULL,
  `stage` int(2) NOT NULL,
  `status` tinyint(1) NOT NULL,
  `doc_id` int(9) NOT NULL,
  `q_id` int(3) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `doc_id` (`doc_id`),
  KEY `grader_id` (`grader_id`),
  KEY `grader_id_2` (`grader_id`),
  KEY `q_id` (`q_id`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=338 ;

--
-- Dumping data for table `jobs`
--

INSERT INTO `jobs` (`id`, `grader_id`, `stage`, `status`, `doc_id`, `q_id`) VALUES
(331, 18, 1, 2, 74, 4),
(332, 18, 1, 2, 75, 4),
(333, 18, 1, 2, 76, 4),
(334, 18, 1, 0, 77, 4),
(335, 18, 1, 0, 78, 4),
(336, 18, 1, 0, 79, 4),
(337, 18, 1, 0, 80, 4);

-- --------------------------------------------------------

--
-- Table structure for table `markingscheme`
--

CREATE TABLE IF NOT EXISTS `markingscheme` (
  `ms_id` int(8) NOT NULL,
  `marks` decimal(4,2) NOT NULL,
  `seq` int(3) NOT NULL,
  UNIQUE KEY `ms_id_2` (`ms_id`,`marks`,`seq`),
  KEY `ms_id` (`ms_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `markingscheme`
--

INSERT INTO `markingscheme` (`ms_id`, `marks`, `seq`) VALUES
(1, '5.00', 1);

-- --------------------------------------------------------

--
-- Table structure for table `marks`
--

CREATE TABLE IF NOT EXISTS `marks` (
  `ml_id` int(9) NOT NULL,
  `seq` int(2) NOT NULL,
  `marks` decimal(4,2) NOT NULL,
  UNIQUE KEY `ml_id_2` (`ml_id`,`seq`,`marks`),
  KEY `ml_id` (`ml_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `marks`
--

INSERT INTO `marks` (`ml_id`, `seq`, `marks`) VALUES
(124, 1, '5.00'),
(125, 1, '0.00'),
(126, 1, '5.00');

-- --------------------------------------------------------

--
-- Table structure for table `marksallotment`
--

CREATE TABLE IF NOT EXISTS `marksallotment` (
  `doc_id` int(9) NOT NULL,
  `q_id` int(3) NOT NULL,
  `ml_id` int(9) NOT NULL AUTO_INCREMENT,
  `grader_id` int(3) NOT NULL,
  `stage` int(2) NOT NULL,
  `viewing_status` int(2) NOT NULL DEFAULT '-1',
  PRIMARY KEY (`ml_id`),
  KEY `doc_id` (`doc_id`),
  KEY `doc_id_2` (`doc_id`),
  KEY `q_id` (`q_id`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=127 ;

--
-- Dumping data for table `marksallotment`
--

INSERT INTO `marksallotment` (`doc_id`, `q_id`, `ml_id`, `grader_id`, `stage`, `viewing_status`) VALUES
(74, 4, 124, 18, 1, 1),
(75, 4, 125, 18, 1, 0),
(76, 4, 126, 18, 1, 0);

-- --------------------------------------------------------

--
-- Table structure for table `qpaperspec`
--

CREATE TABLE IF NOT EXISTS `qpaperspec` (
  `p_id` int(6) NOT NULL,
  `q_id` int(3) NOT NULL AUTO_INCREMENT,
  `ms_id` int(8) NOT NULL,
  `sheet_list_id` int(8) NOT NULL,
  `tot_marks` float(4,2) DEFAULT NULL,
  `q_name` varchar(5) NOT NULL,
  PRIMARY KEY (`q_id`),
  UNIQUE KEY `ms_id` (`ms_id`),
  UNIQUE KEY `sheet_list_id` (`sheet_list_id`),
  KEY `p_id` (`p_id`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=5 ;

--
-- Dumping data for table `qpaperspec`
--

INSERT INTO `qpaperspec` (`p_id`, `q_id`, `ms_id`, `sheet_list_id`, `tot_marks`, `q_name`) VALUES
(11, 4, 1, 1, 5.00, '1');

-- --------------------------------------------------------

--
-- Table structure for table `registration`
--

CREATE TABLE IF NOT EXISTS `registration` (
  `c_num` char(5) NOT NULL,
  `s_roll_no` int(8) NOT NULL,
  UNIQUE KEY `c_num_2` (`c_num`,`s_roll_no`),
  KEY `c_num` (`c_num`),
  KEY `s_roll_no` (`s_roll_no`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `registration`
--

INSERT INTO `registration` (`c_num`, `s_roll_no`) VALUES
('MA101', 10012001),
('MA101', 10012002),
('MA101', 10012003),
('MA101', 10012004),
('MA101', 10012005),
('MA101', 10012333),
('MA101', 10012334);

-- --------------------------------------------------------

--
-- Table structure for table `sheets`
--

CREATE TABLE IF NOT EXISTS `sheets` (
  `sheet_list_id` int(8) NOT NULL,
  `sheet_id` int(3) NOT NULL,
  UNIQUE KEY `sheet_list_id_2` (`sheet_list_id`,`sheet_id`),
  KEY `sheet_list_id` (`sheet_list_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `sheets`
--

INSERT INTO `sheets` (`sheet_list_id`, `sheet_id`) VALUES
(1, 1),
(1, 2),
(1, 3),
(1, 4),
(1, 5);

-- --------------------------------------------------------

--
-- Table structure for table `students`
--

CREATE TABLE IF NOT EXISTS `students` (
  `roll_no` int(8) NOT NULL,
  `name` varchar(30) NOT NULL,
  `webmail` varchar(30) NOT NULL,
  PRIMARY KEY (`roll_no`),
  UNIQUE KEY `webmail_2` (`webmail`),
  KEY `webmail` (`webmail`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `students`
--

INSERT INTO `students` (`roll_no`, `name`, `webmail`) VALUES
(10012001, 'ABC', 'abc'),
(10012002, 'PQR', 'pqr'),
(10012003, 'MNO', 'mno'),
(10012004, 'DEF', 'def'),
(10012005, 'XYZ', 'xyz'),
(10012333, 'Tanay', 'tanay'),
(10012334, 'VBM', 'bhargava');

-- --------------------------------------------------------

--
-- Table structure for table `users`
--

CREATE TABLE IF NOT EXISTS `users` (
  `webmail` varchar(30) NOT NULL,
  `passwd` varchar(30) NOT NULL,
  `type` int(2) NOT NULL,
  PRIMARY KEY (`webmail`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `users`
--

INSERT INTO `users` (`webmail`, `passwd`, `type`) VALUES
('abc', 'passabc', 3),
('admin', 'passadmin', 1),
('aishwarya', 'passaishwarya', 3),
('akc', 'passakc', 2),
('bhargava', 'passbhargava', 3),
('chirag', 'passchirag', 3),
('def', 'passdef', 3),
('gkd', 'passgkd', 2),
('kvk', 'passkvk', 2),
('kvs', 'passkvs', 2),
('MNO', 'mno', 3),
('pqr', 'passpqr', 3),
('psm', 'passpsm', 2),
('tanay', 'passtanay', 3),
('xyz', 'passxyz', 3);

--
-- Constraints for dumped tables
--

--
-- Constraints for table `admin`
--
ALTER TABLE `admin`
  ADD CONSTRAINT `admin_ibfk_1` FOREIGN KEY (`webmail`) REFERENCES `users` (`webmail`) ON DELETE NO ACTION ON UPDATE CASCADE;

--
-- Constraints for table `comments`
--
ALTER TABLE `comments`
  ADD CONSTRAINT `comments_ibfk_1` FOREIGN KEY (`doc_id`) REFERENCES `docs` (`doc_id`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `comments_ibfk_2` FOREIGN KEY (`q_id`) REFERENCES `qpaperspec` (`q_id`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- Constraints for table `courseallotment`
--
ALTER TABLE `courseallotment`
  ADD CONSTRAINT `courseallotment_ibfk_1` FOREIGN KEY (`num`) REFERENCES `courses` (`num`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `courseallotment_ibfk_3` FOREIGN KEY (`cc_id`) REFERENCES `faculty` (`id`) ON UPDATE CASCADE;

--
-- Constraints for table `docs`
--
ALTER TABLE `docs`
  ADD CONSTRAINT `docs_ibfk_2` FOREIGN KEY (`p_id`) REFERENCES `exam` (`p_id`) ON UPDATE CASCADE;

--
-- Constraints for table `exam`
--
ALTER TABLE `exam`
  ADD CONSTRAINT `exam_ibfk_1` FOREIGN KEY (`c_id`) REFERENCES `courses` (`num`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Constraints for table `extrasheetmap`
--
ALTER TABLE `extrasheetmap`
  ADD CONSTRAINT `extrasheetmap_ibfk_1` FOREIGN KEY (`doc_id`) REFERENCES `docs` (`doc_id`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `extrasheetmap_ibfk_2` FOREIGN KEY (`q_id`) REFERENCES `qpaperspec` (`q_id`) ON UPDATE CASCADE;

--
-- Constraints for table `extrasheetspec`
--
ALTER TABLE `extrasheetspec`
  ADD CONSTRAINT `extrasheetspec_ibfk_2` FOREIGN KEY (`sheet_list_id`) REFERENCES `qpaperspec` (`sheet_list_id`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `extrasheetspec_ibfk_3` FOREIGN KEY (`p_id`) REFERENCES `exam` (`p_id`) ON UPDATE CASCADE;

--
-- Constraints for table `faculty`
--
ALTER TABLE `faculty`
  ADD CONSTRAINT `faculty_ibfk_1` FOREIGN KEY (`webmail`) REFERENCES `users` (`webmail`) ON DELETE NO ACTION ON UPDATE CASCADE;

--
-- Constraints for table `gradergroups`
--
ALTER TABLE `gradergroups`
  ADD CONSTRAINT `gradergroups_ibfk_1` FOREIGN KEY (`grader_gp_id`) REFERENCES `gradersallotment` (`grader_gp_id`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `gradergroups_ibfk_2` FOREIGN KEY (`grader_id`) REFERENCES `faculty` (`id`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Constraints for table `gradersallotment`
--
ALTER TABLE `gradersallotment`
  ADD CONSTRAINT `gradersallotment_ibfk_1` FOREIGN KEY (`q_id`) REFERENCES `qpaperspec` (`q_id`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Constraints for table `jobs`
--
ALTER TABLE `jobs`
  ADD CONSTRAINT `jobs_ibfk_1` FOREIGN KEY (`doc_id`) REFERENCES `docs` (`doc_id`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `jobs_ibfk_3` FOREIGN KEY (`grader_id`) REFERENCES `faculty` (`id`) ON UPDATE CASCADE,
  ADD CONSTRAINT `jobs_ibfk_4` FOREIGN KEY (`q_id`) REFERENCES `qpaperspec` (`q_id`) ON UPDATE CASCADE;

--
-- Constraints for table `markingscheme`
--
ALTER TABLE `markingscheme`
  ADD CONSTRAINT `markingscheme_ibfk_1` FOREIGN KEY (`ms_id`) REFERENCES `qpaperspec` (`ms_id`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Constraints for table `marks`
--
ALTER TABLE `marks`
  ADD CONSTRAINT `marks_ibfk_1` FOREIGN KEY (`ml_id`) REFERENCES `marksallotment` (`ml_id`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Constraints for table `marksallotment`
--
ALTER TABLE `marksallotment`
  ADD CONSTRAINT `marksallotment_ibfk_1` FOREIGN KEY (`doc_id`) REFERENCES `docs` (`doc_id`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `marksallotment_ibfk_2` FOREIGN KEY (`q_id`) REFERENCES `qpaperspec` (`q_id`) ON UPDATE CASCADE;

--
-- Constraints for table `qpaperspec`
--
ALTER TABLE `qpaperspec`
  ADD CONSTRAINT `qpaperspec_ibfk_1` FOREIGN KEY (`p_id`) REFERENCES `exam` (`p_id`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Constraints for table `registration`
--
ALTER TABLE `registration`
  ADD CONSTRAINT `registration_ibfk_1` FOREIGN KEY (`c_num`) REFERENCES `courses` (`num`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `registration_ibfk_2` FOREIGN KEY (`s_roll_no`) REFERENCES `students` (`roll_no`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Constraints for table `sheets`
--
ALTER TABLE `sheets`
  ADD CONSTRAINT `sheets_ibfk_1` FOREIGN KEY (`sheet_list_id`) REFERENCES `qpaperspec` (`sheet_list_id`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Constraints for table `students`
--
ALTER TABLE `students`
  ADD CONSTRAINT `students_ibfk_1` FOREIGN KEY (`webmail`) REFERENCES `users` (`webmail`) ON DELETE NO ACTION ON UPDATE CASCADE;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
