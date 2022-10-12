-- phpMyAdmin SQL Dump
-- version 5.0.2
-- https://www.phpmyadmin.net/
--
-- Hôte : 127.0.0.1:3306
-- Généré le : mar. 25 mai 2021 à 20:05
-- Version du serveur :  8.0.21
-- Version de PHP : 7.3.21

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de données : `biblos`
--

-- --------------------------------------------------------

--
-- Structure de la table `app_param`
--

DROP TABLE IF EXISTS `app_param`;
CREATE TABLE IF NOT EXISTS `app_param` (
  `id` int NOT NULL,
  `header` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `foot_page` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `name_company` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `phone1` varchar(15) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `phone2` varchar(15) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `phone3` varchar(15) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `currency` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `address` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `website` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `email` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `postal_code` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `logo` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `cachet` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- Déchargement des données de la table `app_param`
--

INSERT INTO `app_param` (`id`, `header`, `foot_page`, `name_company`, `phone1`, `phone2`, `phone3`, `currency`, `address`, `website`, `email`, `postal_code`, `logo`, `cachet`) VALUES
(1, 'header', 'footer', 'Biblos', '0101010101', '0202020202', '0303030303', 'fcfa', 'Abidjan plateau', 'www.biblos.ci', 'info@biblos.ci', '01 abidajn', 'http://localhost:8080/application-parameter/logo.jpg', 'http://localhost:8080/application-parameter/cachet.jpg');

-- --------------------------------------------------------

--
-- Structure de la table `article`
--

DROP TABLE IF EXISTS `article`;
CREATE TABLE IF NOT EXISTS `article` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(100) COLLATE utf8mb4_unicode_ci NOT NULL,
  `reference` varchar(100) COLLATE utf8mb4_unicode_ci NOT NULL,
  `description` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
  `article_sub_family_id` int NOT NULL,
  `qty_in_stock` int NOT NULL DEFAULT '0',
  `qty_max` int NOT NULL DEFAULT '0',
  `qty_min` int NOT NULL DEFAULT '0',
  `qty_alert` int NOT NULL DEFAULT '0',
  `image_url` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `is_active` tinyint(1) NOT NULL DEFAULT '1',
  `stock_state` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `cost_price` double NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- Déchargement des données de la table `article`
--

INSERT INTO `article` (`id`, `name`, `reference`, `description`, `article_sub_family_id`, `qty_in_stock`, `qty_max`, `qty_min`, `qty_alert`, `image_url`, `is_active`, `stock_state`, `cost_price`) VALUES
(1, 'Split 1 cheveau', 'sts-2223', 'ffffffffff', 1, 150, 50, 500, 100, 'http://192.168.1.8:8080/article/image/sts-2223.jpg', 1, '\0', 1550),
(2, 'Congelateur', 'stcd', 'dddddddddddddd', 2, 150, 200, 24, 50, 'http://192.168.1.5:8080/article/image/stcd.jpg', 1, '\0', 260500);

-- --------------------------------------------------------

--
-- Structure de la table `article_cost`
--

DROP TABLE IF EXISTS `article_cost`;
CREATE TABLE IF NOT EXISTS `article_cost` (
  `id` int NOT NULL AUTO_INCREMENT,
  `article_id` int NOT NULL,
  `customer_family_id` int NOT NULL,
  `article_cost` double NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- Déchargement des données de la table `article_cost`
--

INSERT INTO `article_cost` (`id`, `article_id`, `customer_family_id`, `article_cost`) VALUES
(1, 1, 3, 320),
(2, 1, 1, 101),
(3, 2, 3, 400),
(4, 3, 3, 500),
(5, 1, 5, 353),
(6, 1, 7, 376),
(7, 1, 8, 411),
(8, 2, 1, 500),
(9, 2, 5, 0),
(10, 2, 7, 0),
(11, 2, 8, 0);

-- --------------------------------------------------------

--
-- Structure de la table `article_family`
--

DROP TABLE IF EXISTS `article_family`;
CREATE TABLE IF NOT EXISTS `article_family` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(50) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL,
  `is_active` tinyint(1) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- Déchargement des données de la table `article_family`
--

INSERT INTO `article_family` (`id`, `name`, `is_active`) VALUES
(1, 'eléctromenager', 1),
(2, 'test', 1),
(3, 'errr', 1),
(4, 'ffff', 1),
(5, 'rrrr', 1),
(6, 'ggggggggg', 1),
(7, 'azerty', 1);

-- --------------------------------------------------------

--
-- Structure de la table `article_ordered`
--

DROP TABLE IF EXISTS `article_ordered`;
CREATE TABLE IF NOT EXISTS `article_ordered` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `supplier_order_id` bigint NOT NULL,
  `article_id` int NOT NULL,
  `qty` int NOT NULL,
  `initial_unit_cost` double DEFAULT NULL,
  `final_unit_cost` double DEFAULT NULL,
  `forcast_date` date DEFAULT NULL,
  `delivery_date` date DEFAULT NULL,
  `ordering_state` char(1) COLLATE utf8mb4_unicode_ci NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- Déchargement des données de la table `article_ordered`
--

INSERT INTO `article_ordered` (`id`, `supplier_order_id`, `article_id`, `qty`, `initial_unit_cost`, `final_unit_cost`, `forcast_date`, `delivery_date`, `ordering_state`) VALUES
(2, 3, 1, 5000, 2200, 2200, '2021-05-21', '2021-05-21', 'a'),
(3, 4, 1, 5000, 2200, 2200, '2021-05-21', '2021-05-21', 'a'),
(4, 4, 1, 4000, 2300, 2100, '2021-05-21', '2021-05-21', 'b');

-- --------------------------------------------------------

--
-- Structure de la table `article_sub_family`
--

DROP TABLE IF EXISTS `article_sub_family`;
CREATE TABLE IF NOT EXISTS `article_sub_family` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(100) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL,
  `family_id` int NOT NULL,
  `is_active` tinyint(1) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- Déchargement des données de la table `article_sub_family`
--

INSERT INTO `article_sub_family` (`id`, `name`, `family_id`, `is_active`) VALUES
(1, 'televiseur', 1, 1),
(2, 'refrigerateur', 1, 1),
(3, 'ventilateur', 1, 0);

-- --------------------------------------------------------

--
-- Structure de la table `authority`
--

DROP TABLE IF EXISTS `authority`;
CREATE TABLE IF NOT EXISTS `authority` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(50) COLLATE utf8mb4_unicode_ci NOT NULL,
  `display_name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `resource_id` int NOT NULL,
  `is_active` tinyint(1) NOT NULL DEFAULT '1',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=61 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- Déchargement des données de la table `authority`
--

INSERT INTO `authority` (`id`, `name`, `display_name`, `resource_id`, `is_active`) VALUES
(1, 'menu:dashboard', 'acces au tableau de bord', 1, 1),
(2, 'menu:article', 'acces au menu article', 1, 1),
(3, 'menu:stock', 'acces au menu stock', 1, 1),
(4, 'menu:depot', 'acces au menu depot', 1, 1),
(5, 'menu:supplier_purchase', 'acces au menu achat fournisseur', 1, 1),
(6, 'menu:customer_sale', 'acces au menu vente client', 1, 1),
(7, 'menu:customer_payment', 'acces au menu reglement', 1, 1),
(8, 'menu_after_sale_service', 'acces au menu service apres vente', 1, 1),
(9, 'menu:history', 'acces au menu historique', 1, 1),
(10, 'menu_statistic', 'acces au menu statistique', 1, 1),
(11, 'menu:basic_files', 'acces au menu fichier de base', 1, 1),
(12, 'menu:user_managenement', 'acces au menu gestion des utilisateur', 1, 1),
(13, 'event_log:list', 'acces aux traces des evenements ', 1, 1),
(14, 'app_param:setting', 'acces aux parametres de l\'application', 1, 1),
(15, 'purchase:search_box', 'acces a la boite de recherche', 1, 1),
(16, 'menu:notification', 'acces aux notifications', 1, 1),
(17, 'menu:setting', 'acces aux parametres generaux', 1, 1),
(18, 'user:add', 'ajouter', 2, 1),
(19, 'user:update', 'modifier', 2, 1),
(20, 'user:reset_password', 'reinitialiser un mot de passe', 2, 1),
(21, 'user:list', 'lister', 2, 1),
(22, 'role:add', 'ajouter', 3, 1),
(23, 'role:update', 'modifier', 3, 1),
(24, 'role:list', 'lister', 3, 1),
(25, 'role:set_permission', 'attribuer permission', 3, 1),
(26, 'depot:add', 'ajouter ', 4, 1),
(27, 'depot:update', 'modifier', 4, 1),
(28, 'depot:list', 'lister', 4, 1),
(29, 'transport_vehicle:add', 'ajouter', 5, 1),
(30, 'transport_vehicle:update', 'modifier', 5, 1),
(31, 'transport_vehicle:list', 'lister', 5, 1),
(32, 'bank:add', 'ajouter', 6, 1),
(33, 'bank:update', 'modifier', 6, 1),
(34, 'bank:list', 'lister', 6, 1),
(35, 'supplier_family:add', 'ajouter', 7, 1),
(36, 'supplier_family:update', 'modifier', 7, 1),
(37, 'supplier_family:list', 'lister', 7, 1),
(38, 'supplier:add', 'ajouter', 8, 1),
(39, 'supplier:update', 'modifier', 8, 1),
(40, 'supplier:list', 'lister', 8, 1),
(41, 'supplier:access_balance', 'acces aux solde fournisseurs', 8, 1),
(42, 'customer_family:add', 'ajouter', 9, 1),
(43, 'customer_family:update', 'modifier', 9, 1),
(44, 'customer_family:list', 'lister', 9, 1),
(45, 'customer:add', 'ajouter', 10, 1),
(46, 'customer:update', 'modifier', 10, 1),
(47, 'customer:access_balance', 'acces aux solde clients', 10, 1),
(48, 'customer:list', 'lister', 10, 1),
(49, 'article_family:add', 'ajouter', 11, 1),
(50, 'article_family:update', 'modifier', 11, 1),
(51, 'article_family:list', 'lister', 11, 1),
(52, 'article_sub_family:add', 'ajouter', 12, 1),
(53, 'article_sub_family:update', 'modifier', 12, 1),
(54, 'article_sub_family:list', 'lister', 12, 1),
(55, 'article:add', 'ajouter', 13, 1),
(56, 'article:update', 'modifier', 13, 1),
(57, 'article:list', 'lister', 13, 1),
(58, 'article_cost_price:access', 'modifier le prix de revient', 13, 1),
(59, 'article_customer_cost:access', 'mofifier les pris clients', 13, 1),
(60, 'article_stock_value:access', 'voir la valeur du stock', 13, 1);

-- --------------------------------------------------------

--
-- Structure de la table `bank`
--

DROP TABLE IF EXISTS `bank`;
CREATE TABLE IF NOT EXISTS `bank` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `email` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `postal_box` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `phone` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `is_active` tinyint(1) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- Déchargement des données de la table `bank`
--

INSERT INTO `bank` (`id`, `name`, `email`, `postal_box`, `phone`, `is_active`) VALUES
(1, 'banque', 'a@gamil.com', '225', '111111', 1),
(2, 'versus bank', 'atsemathurinollele@gmail.com', '116 abj 01', '09604998', 1),
(3, 'SGBCI', 'a@gamil.com', '225', '111111', 0),
(4, 'BHCI', 'info@bhci.ci', 'string', 'string', 1),
(5, 'BICICI', 'bicic@gmail.com', 'BP12547', '078752125', 0),
(6, 'bnp paribas', 'bnp@gmail.fr', 'BP-033', '033 3512365445', 1),
(7, 'bamque populaire abidjan', 'qqqq@gmail.com', '12 ab abidjan 12', '06060606060', 0),
(8, 'test', 'eeee', 'eeee', 'eeee', 1),
(9, 'test2', 'ggg@gmail.com', '', '', 1);

-- --------------------------------------------------------

--
-- Structure de la table `country`
--

DROP TABLE IF EXISTS `country`;
CREATE TABLE IF NOT EXISTS `country` (
  `id` int NOT NULL,
  `name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `iso_code` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- Déchargement des données de la table `country`
--

INSERT INTO `country` (`id`, `name`, `iso_code`) VALUES
(1, 'Afghanistan', 'AFG'),
(2, 'Albanie', 'ALB'),
(3, 'Antarctique', 'ATA'),
(4, 'Algérie', 'DZA'),
(5, 'Samoa Américaines', 'ASM'),
(6, 'Andorre', 'AND'),
(7, 'Angola', 'AGO'),
(8, 'Antigua-et-Barbuda', 'ATG'),
(9, 'Azerbaïdjan', 'AZE'),
(10, 'Argentine', 'ARG'),
(11, 'Australie', 'AUS'),
(12, 'Autriche', 'AUT'),
(13, 'Bahamas', 'BHS'),
(14, 'Bahreïn', 'BHR'),
(15, 'Bangladesh', 'BGD'),
(16, 'Arménie', 'ARM'),
(17, 'Barbade', 'BRB'),
(18, 'Belgique', 'BEL'),
(19, 'Bermudes', 'BMU'),
(20, 'Bhoutan', 'BTN'),
(21, 'Bolivie', 'BOL'),
(22, 'Bosnie-Herzégovine', 'BIH'),
(23, 'Botswana', 'BWA'),
(24, 'Île Bouvet', 'BVT'),
(25, 'Brésil', 'BRA'),
(26, 'Belize', 'BLZ'),
(27, 'Territoire Britannique de l\'Océan Indien', 'IOT'),
(28, 'Îles Salomon', 'SLB'),
(29, 'Îles Vierges Britanniques', 'VGB'),
(30, 'Brunéi Darussalam', 'BRN'),
(31, 'Bulgarie', 'BGR'),
(32, 'Myanmar', 'MMR'),
(33, 'Burundi', 'BDI'),
(34, 'Bélarus', 'BLR'),
(35, 'Cambodge', 'KHM'),
(36, 'Cameroun', 'CMR'),
(37, 'Canada', 'CAN'),
(38, 'Cap-vert', 'CPV'),
(39, 'Îles Caïmanes', 'CYM'),
(40, 'République Centrafricaine', 'CAF'),
(41, 'Sri Lanka', 'LKA'),
(42, 'Tchad', 'TCD'),
(43, 'Chili', 'CHL'),
(44, 'Chine', 'CHN'),
(45, 'Taïwan', 'TWN'),
(46, 'Île Christmas', 'CXR'),
(47, 'Îles Cocos (Keeling)', 'CCK'),
(48, 'Colombie', 'COL'),
(49, 'Comores', 'COM'),
(50, 'Mayotte', 'MYT'),
(51, 'République du Congo', 'COG'),
(52, 'République Démocratique du Congo', 'COD'),
(53, 'Îles Cook', 'COK'),
(54, 'Costa Rica', 'CRI'),
(55, 'Croatie', 'HRV'),
(56, 'Cuba', 'CUB'),
(57, 'Chypre', 'CYP'),
(58, 'République Tchèque', 'CZE'),
(59, 'Bénin', 'BEN'),
(60, 'Danemark', 'DNK'),
(61, 'Dominique', 'DMA'),
(62, 'République Dominicaine', 'DOM'),
(63, 'Equateur', 'ECU'),
(64, 'El Salvador', 'SLV'),
(65, 'Guinée Equatoriale', 'GNQ'),
(66, 'Ethiopie', 'ETH'),
(67, 'Erythrée', 'ERI'),
(68, 'Estonie', 'EST'),
(69, 'Îles Féroé', 'FRO'),
(70, 'Îles (malvinas) Falkland', 'FLK'),
(71, 'Géorgie du Sud et les Îles Sandwich du Sud', 'SGS'),
(72, 'Fidji', 'FJI'),
(73, 'Finlande', 'FIN'),
(74, 'Îles Ã…land', 'ALA'),
(75, 'France', 'FRA'),
(76, 'Guyane Française', 'GUF'),
(77, 'Polynésie Française', 'PYF'),
(78, 'Terres Australes Françaises', 'ATF'),
(79, 'Djibouti', 'DJI'),
(80, 'Gabon', 'GAB'),
(81, 'Géorgie', 'GEO'),
(82, 'Gambie', 'GMB'),
(83, 'Territoire Palestinien Occupé', 'PSE'),
(84, 'Allemagne', 'DEU'),
(85, 'Ghana', 'GHA'),
(86, 'Gibraltar', 'GIB'),
(87, 'Kiribati', 'KIR'),
(88, 'Grèce', 'GRC'),
(89, 'Groenland', 'GRL'),
(90, 'Grenade', 'GRD'),
(91, 'Guadeloupe', 'GLP'),
(92, 'Guam', 'GUM'),
(93, 'Guatemala', 'GTM'),
(94, 'Guinée', 'GIN'),
(95, 'Guyana', 'GUY'),
(96, 'Haïti', 'HTI'),
(97, 'Îles Heard et Mcdonald', 'HMD'),
(98, 'Saint-Siège (état de la Cité du Vatican)', 'VAT'),
(99, 'Honduras', 'HND'),
(100, 'Hong-Kong', 'HKG'),
(101, 'Hongrie', 'HUN'),
(102, 'Islande', 'ISL'),
(103, 'Inde', 'IND'),
(104, 'Indonésie', 'IDN'),
(105, 'République Islamique d\'Iran', 'IRN'),
(106, 'Iraq', 'IRQ'),
(107, 'Irlande', 'IRL'),
(108, 'Israël', 'ISR'),
(109, 'Italie', 'ITA'),
(110, 'Côte d\'Ivoire', 'CIV'),
(111, 'Jamaïque', 'JAM'),
(112, 'Japon', 'JPN'),
(113, 'Kazakhstan', 'KAZ'),
(114, 'Jordanie', 'JOR'),
(115, 'Kenya', 'KEN'),
(116, 'République Populaire Démocratique de Corée', 'PRK'),
(117, 'République de Corée', 'KOR'),
(118, 'Koweït', 'KWT'),
(119, 'Kirghizistan', 'KGZ'),
(120, 'République Démocratique Populaire Lao', 'LAO'),
(121, 'Liban', 'LBN'),
(122, 'Lesotho', 'LSO'),
(123, 'Lettonie', 'LVA'),
(124, 'Libéria', 'LBR'),
(125, 'Jamahiriya Arabe Libyenne', 'LBY'),
(126, 'Liechtenstein', 'LIE'),
(127, 'Lituanie', 'LTU'),
(128, 'Luxembourg', 'LUX'),
(129, 'Macao', 'MAC'),
(130, 'Madagascar', 'MDG'),
(131, 'Malawi', 'MWI'),
(132, 'Malaisie', 'MYS'),
(133, 'Maldives', 'MDV'),
(134, 'Mali', 'MLI'),
(135, 'Malte', 'MLT'),
(136, 'Martinique', 'MTQ'),
(137, 'Mauritanie', 'MRT'),
(138, 'Maurice', 'MUS'),
(139, 'Mexique', 'MEX'),
(140, 'Monaco', 'MCO'),
(141, 'Mongolie', 'MNG'),
(142, 'République de Moldova', 'MDA'),
(143, 'Montserrat', 'MSR'),
(144, 'Maroc', 'MAR'),
(145, 'Mozambique', 'MOZ'),
(146, 'Oman', 'OMN'),
(147, 'Namibie', 'NAM'),
(148, 'Nauru', 'NRU'),
(149, 'Népal', 'NPL'),
(150, 'Pays-Bas', 'NLD'),
(151, 'Antilles Néerlandaises', 'ANT'),
(152, 'Aruba', 'ABW'),
(153, 'Nouvelle-Calédonie', 'NCL'),
(154, 'Vanuatu', 'VUT'),
(155, 'Nouvelle-Zélande', 'NZL'),
(156, 'Nicaragua', 'NIC'),
(157, 'Niger', 'NER'),
(158, 'Nigéria', 'NGA'),
(159, 'Niué', 'NIU'),
(160, 'Île Norfolk', 'NFK'),
(161, 'Norvège', 'NOR'),
(162, 'Îles Mariannes du Nord', 'MNP'),
(163, 'Îles Mineures Eloignées des Etats-Unis', 'UMI'),
(164, 'Etats Fédérés de Micronésie', 'FSM'),
(165, 'Îles Marshall', 'MHL'),
(166, 'Palaos', 'PLW'),
(167, 'Pakistan', 'PAK'),
(168, 'Panama', 'PAN'),
(169, 'Papouasie-Nouvelle-Guinée', 'PNG'),
(170, 'Paraguay', 'PRY'),
(171, 'Pérou', 'PER'),
(172, 'Philippines', 'PHL'),
(173, 'Pitcairn', 'PCN'),
(174, 'Pologne', 'POL'),
(175, 'Portugal', 'PRT'),
(176, 'Guinée-Bissau', 'GNB'),
(177, 'Timor-Leste', 'TLS'),
(178, 'Porto Rico', 'PRI'),
(179, 'Qatar', 'QAT'),
(180, 'Réunion', 'REU'),
(181, 'Roumanie', 'ROU'),
(182, 'Fédération de Russie', 'RUS'),
(183, 'Rwanda', 'RWA'),
(184, 'Sainte-Hélène', 'SHN'),
(185, 'Saint-Kitts-et-Nevis', 'KNA'),
(186, 'Anguilla', 'AIA'),
(187, 'Sainte-Lucie', 'LCA'),
(188, 'Saint-Pierre-et-Miquelon', 'SPM'),
(189, 'Saint-Vincent-et-les Grenadines', 'VCT'),
(190, 'Saint-Marin', 'SMR'),
(191, 'Sao Tomé-et-Principe', 'STP'),
(192, 'Arabie Saoudite', 'SAU'),
(193, 'Sénégal', 'SEN'),
(194, 'Seychelles', 'SYC'),
(195, 'Sierra Leone', 'SLE'),
(196, 'Singapour', 'SGP'),
(197, 'Slovaquie', 'SVK'),
(198, 'Viet Nam', 'VNM'),
(199, 'Slovénie', 'SVN'),
(200, 'Somalie', 'SOM'),
(201, 'Afrique du Sud', 'ZAF'),
(202, 'Zimbabwe', 'ZWE'),
(203, 'Espagne', 'ESP'),
(204, 'Sahara Occidental', 'ESH'),
(205, 'Soudan', 'SDN'),
(206, 'Suriname', 'SUR'),
(207, 'Svalbard etÎle Jan Mayen', 'SJM'),
(208, 'Swaziland', 'SWZ'),
(209, 'Suède', 'SWE'),
(210, 'Suisse', 'CHE'),
(211, 'République Arabe Syrienne', 'SYR'),
(212, 'Tadjikistan', 'TJK'),
(213, 'Thaïlande', 'THA'),
(214, 'Togo', 'TGO'),
(215, 'Tokelau', 'TKL'),
(216, 'Tonga', 'TON'),
(217, 'Trinité-et-Tobago', 'TTO'),
(218, 'Emirats Arabes Unis', 'ARE'),
(219, 'Tunisie', 'TUN'),
(220, 'Turquie', 'TUR'),
(221, 'Turkménistan', 'TKM'),
(222, 'Îles Turks et Caïques', 'TCA'),
(223, 'Tuvalu', 'TUV'),
(224, 'Ouganda', 'UGA'),
(225, 'Ukraine', 'UKR'),
(226, 'L\'ex-République Yougoslave de Macédoine', 'MKD'),
(227, 'Egypte', 'EGY'),
(228, 'Royaume-Uni', 'GBR'),
(229, 'Île de Man', 'IMN'),
(230, 'République-Unie de Tanzanie', 'TZA'),
(231, 'Etats-Unis', 'USA'),
(232, 'Îles Vierges des Etats-Unis', 'VIR'),
(233, 'Burkina Faso', 'BFA'),
(234, 'Uruguay', 'URY'),
(235, 'Ouzbékistan', 'UZB'),
(236, 'Venezuela', 'VEN'),
(237, 'Wallis et Futuna', 'WLF'),
(238, 'Samoa', 'WSM'),
(239, 'Yémen', 'YEM'),
(240, 'Serbie-et-Monténégro', 'SCG'),
(241, 'Zambie', 'ZMB');

-- --------------------------------------------------------

--
-- Structure de la table `customer`
--

DROP TABLE IF EXISTS `customer`;
CREATE TABLE IF NOT EXISTS `customer` (
  `id` int NOT NULL AUTO_INCREMENT,
  `customer_number` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `trade_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `phone` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `phone2` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `phone3` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `email` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `address` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `customer_family_id` int NOT NULL,
  `delivery_address` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `interlocutor_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `interlocutor_phone` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `customer_account_id` int DEFAULT NULL,
  `trade_register_number` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `corporate_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `is_active` tinyint(1) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- Déchargement des données de la table `customer`
--

INSERT INTO `customer` (`id`, `customer_number`, `trade_name`, `phone`, `phone2`, `phone3`, `email`, `address`, `customer_family_id`, `delivery_address`, `interlocutor_name`, `interlocutor_phone`, `customer_account_id`, `trade_register_number`, `corporate_name`, `is_active`) VALUES
(1, NULL, 'jumia', '', NULL, NULL, '', '', 1, NULL, '', '', 1, '', 'jumia', 1),
(2, NULL, 'afrique market', 'sssssssss', NULL, NULL, 'ddddd@gmail.com', 'ssssssss', 1, NULL, 'sssssss', 'sssssssssss', 2, '', 'afrique market', 0),
(3, 'CL-00003', 'vne', '', NULL, NULL, 'ddd@yyy.com', '', 7, NULL, '', '', 3, '', 'vne', 0),
(4, 'CL-00004', 'client 1', '', NULL, NULL, '', '', 3, NULL, '', '', 4, '', 'client 1', 1);

-- --------------------------------------------------------

--
-- Structure de la table `customer_account`
--

DROP TABLE IF EXISTS `customer_account`;
CREATE TABLE IF NOT EXISTS `customer_account` (
  `id` int NOT NULL AUTO_INCREMENT,
  `customer_id` int NOT NULL,
  `account_number` int NOT NULL,
  `balance` double NOT NULL DEFAULT '0',
  `ceiling_balance` double NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- Déchargement des données de la table `customer_account`
--

INSERT INTO `customer_account` (`id`, `customer_id`, `account_number`, `balance`, `ceiling_balance`) VALUES
(1, 1, 41100001, 2000000.5, 10000000),
(2, 2, 41100002, 1500000.7, 0),
(3, 3, 41100003, 350000, 0),
(4, 4, 41100004, 980000, 0);

-- --------------------------------------------------------

--
-- Structure de la table `customer_family`
--

DROP TABLE IF EXISTS `customer_family`;
CREATE TABLE IF NOT EXISTS `customer_family` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `is_active` tinyint(1) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- Déchargement des données de la table `customer_family`
--

INSERT INTO `customer_family` (`id`, `name`, `is_active`) VALUES
(1, 'grossiste', 1),
(3, 'super dealer', 1),
(4, 'detaillant', 0),
(5, 'gros', 1),
(6, 'deale', 0),
(7, 'super detaillant', 1),
(8, 'special promo', 1);

-- --------------------------------------------------------

--
-- Structure de la table `databasechangelog`
--

DROP TABLE IF EXISTS `databasechangelog`;
CREATE TABLE IF NOT EXISTS `databasechangelog` (
  `ID` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `AUTHOR` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `FILENAME` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `DATEEXECUTED` datetime NOT NULL,
  `ORDEREXECUTED` int NOT NULL,
  `EXECTYPE` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `MD5SUM` varchar(35) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `DESCRIPTION` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `COMMENTS` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `TAG` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `LIQUIBASE` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `CONTEXTS` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `LABELS` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `DEPLOYMENT_ID` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- Déchargement des données de la table `databasechangelog`
--

INSERT INTO `databasechangelog` (`ID`, `AUTHOR`, `FILENAME`, `DATEEXECUTED`, `ORDEREXECUTED`, `EXECTYPE`, `MD5SUM`, `DESCRIPTION`, `COMMENTS`, `TAG`, `LIQUIBASE`, `CONTEXTS`, `LABELS`, `DEPLOYMENT_ID`) VALUES
('1', 'dabre', 'db/changelog/changelog-1.0.xml', '2021-04-16 14:09:27', 1, 'EXECUTED', '8:030949f8384c4081468fef79184fcc7e', 'addColumn tableName=persons', '', NULL, '3.10.3', NULL, NULL, '8582167905'),
('2', 'dabre', 'db/changelog/changelog-1.0.xml', '2021-04-16 14:15:12', 2, 'EXECUTED', '8:835057e6f7bf9e2eefc16fc23f2d0fea', 'addColumn tableName=persons', '', NULL, '4.3.1', NULL, NULL, '8582512649');

-- --------------------------------------------------------

--
-- Structure de la table `databasechangeloglock`
--

DROP TABLE IF EXISTS `databasechangeloglock`;
CREATE TABLE IF NOT EXISTS `databasechangeloglock` (
  `ID` int NOT NULL,
  `LOCKED` bit(1) NOT NULL,
  `LOCKGRANTED` datetime DEFAULT NULL,
  `LOCKEDBY` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- Déchargement des données de la table `databasechangeloglock`
--

INSERT INTO `databasechangeloglock` (`ID`, `LOCKED`, `LOCKGRANTED`, `LOCKEDBY`) VALUES
(1, b'0', NULL, NULL);

-- --------------------------------------------------------

--
-- Structure de la table `depot`
--

DROP TABLE IF EXISTS `depot`;
CREATE TABLE IF NOT EXISTS `depot` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `is_active` tinyint(1) NOT NULL,
  `num_enreg` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `localisation` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `tel` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `cel` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `num_zone` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `user_id` bigint NOT NULL,
  `user_name` varchar(100) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `dept_principal` tinyint(1) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=15 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- Déchargement des données de la table `depot`
--

INSERT INTO `depot` (`id`, `name`, `is_active`, `num_enreg`, `localisation`, `tel`, `cel`, `num_zone`, `user_id`, `user_name`, `dept_principal`) VALUES
(0, 'Tous les depôt', 1, '105', 'abidjan', '09604998', '02700760', '01', 76, NULL, 0),
(1, 'depot anono', 1, '', '', '', '', '', 76, 'dabre adjara', 0),
(7, 'depot1', 0, '', '', '', '', '', 80, 'admin super', 0),
(8, 'depot cocody', 1, '105', 'abidjan cocody', '09604', '02700', '01111111111', 80, 'atse mathurin', 1),
(9, 'ffffffff', 0, 'fffff', 'fffffff', 'fffff', 'ffffffff', 'fffffffff', 72, 'hamed soumahoro', 0),
(10, 'depot test', 0, 'dep123', 'koumassi', '020202020', '0101010101', '1458', 76, 'mathurin atse', 1),
(11, 'depot Anyama', 1, '2512354', 'anyama', '2722122547', '0578856254', '6', 77, 'ahoussi kouassi', 0),
(12, 'depot', 0, '', 'marcory', '', '', '', 75, 'alassane doumbia', 0),
(13, 'depot2', 1, '', 'abobo', '', '', '', 77, 'ahoussi kouassi', 0),
(14, 'depot zonz 4 c', 0, '', 'marcory', '', '', '', 74, 'toto toto', 1);

-- --------------------------------------------------------

--
-- Structure de la table `depot-article`
--

DROP TABLE IF EXISTS `depot-article`;
CREATE TABLE IF NOT EXISTS `depot-article` (
  `id` int NOT NULL AUTO_INCREMENT,
  `depot_id` int NOT NULL,
  `article_id` int NOT NULL,
  `qte` int NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- --------------------------------------------------------

--
-- Structure de la table `event_log`
--

DROP TABLE IF EXISTS `event_log`;
CREATE TABLE IF NOT EXISTS `event_log` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `event` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
  `date` timestamp NOT NULL,
  `user_id` bigint NOT NULL,
  `user_name` varchar(100) COLLATE utf8mb4_unicode_ci NOT NULL,
  `category` varchar(100) COLLATE utf8mb4_unicode_ci NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=473 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- Déchargement des données de la table `event_log`
--

INSERT INTO `event_log` (`id`, `event`, `date`, `user_id`, `user_name`, `category`) VALUES
(1, 'creation du role: service achat', '2021-05-19 11:10:28', 0, 'admin super', 'role utilisateur'),
(2, 'modification du role: service achat', '2021-05-19 11:10:35', 0, 'admin super', 'role utilisateur'),
(3, 'modification du role: service achats', '2021-05-19 11:10:54', 0, 'admin super', 'role utilisateur'),
(4, 'creation de l\'utilisateur: atse ollele', '2021-05-19 11:17:11', 0, 'admin super', 'utilisateur'),
(5, 'modification du role: service achats', '2021-05-19 11:17:51', 0, 'admin super', 'role utilisateur'),
(6, 'Modification de l\'utilisateur: atse ollele', '2021-05-19 11:18:07', 0, 'admin super', 'utilisateur'),
(7, 'l\'utilisateur: Atse Ollele a changé son mot de passe', '2021-05-19 11:23:35', 128, 'Ollele Atse', 'utilisateur'),
(8, 'l\'utilisateur: Atse Ollele a changé son mot de passe', '2021-05-19 11:26:00', 0, 'admin super', 'utilisateur'),
(9, 'l\'utilisateur: Atse Ollele a changé son mot de passe', '2021-05-19 11:27:29', 128, 'Ollele Atse', 'utilisateur'),
(10, 'Modification de l\'utilisateur: Atse Ollele', '2021-05-19 11:43:47', 0, 'admin super', 'utilisateur'),
(11, 'Modification de l\'utilisateur: Atse Ollele', '2021-05-19 11:46:03', 0, 'admin super', 'utilisateur'),
(12, 'l\'utilisateur: Atse Ollele a changé son mot de passe', '2021-05-19 11:47:22', 0, 'admin super', 'utilisateur'),
(13, 'creation de la banque :bamque populaire', '2021-05-19 11:54:45', 0, 'admin super', 'Banque'),
(14, 'modification de la banque:bamque populaire abidjan', '2021-05-19 11:54:57', 0, 'admin super', 'Banque'),
(15, 'modification de la banque:bamque populaire abidjan', '2021-05-19 11:59:09', 0, 'admin super', 'Banque'),
(16, 'creation du véhicule de transport: gggggggggg', '2021-05-19 12:02:51', 0, 'admin super', 'véhicule de transport'),
(17, 'modification du vehicule de trasport: gggggggggg', '2021-05-19 12:03:02', 0, 'admin super', 'véhicule de transport'),
(18, 'ajout de la famille article:electromenager', '2021-05-19 12:14:11', 0, 'admin super', 'famille article'),
(19, 'modification de la famille article:eléctromenager', '2021-05-19 12:14:31', 0, 'admin super', 'famille article'),
(20, 'modification de la famille article:eléctromenager', '2021-05-19 12:14:47', 0, 'admin super', 'famille article'),
(21, 'ajout de la sous famille article:televiseur', '2021-05-19 12:15:44', 0, 'admin super', 'sous famille article'),
(22, 'ajout de la sous famille article:refrigerateur', '2021-05-19 12:15:54', 0, 'admin super', 'sous famille article'),
(23, 'ajout de la sous famille article:ventilateur', '2021-05-19 12:16:03', 0, 'admin super', 'sous famille article'),
(24, 'modification du depot qui a pour nom: depot test', '2021-05-19 12:22:57', 0, 'admin super', 'depot'),
(25, 'modification du depot qui a pour nom: depot test', '2021-05-19 12:23:06', 0, 'admin super', 'depot'),
(26, 'modification du depot qui a pour nom: depot test', '2021-05-19 12:23:12', 0, 'admin super', 'depot'),
(27, 'creation du depot qui a pour nom: depot', '2021-05-19 12:26:03', 0, 'admin super', 'depot'),
(28, 'modification du depot qui a pour nom: depot', '2021-05-19 12:27:04', 0, 'admin super', 'depot'),
(29, 'modification du depot qui a pour nom: depot', '2021-05-19 12:27:11', 0, 'admin super', 'depot'),
(30, 'creation du depot qui a pour nom: depot2', '2021-05-19 12:28:21', 0, 'admin super', 'depot'),
(31, 'ajout du fournisseur:abc corporate', '2021-05-19 12:30:10', 0, 'admin super', 'fournisseur'),
(32, 'ajout du fournisseur:eeeeee', '2021-05-19 12:31:25', 0, 'admin super', 'fournisseur'),
(33, 'creation de la famille fournisseur:fournisseurr2', '2021-05-19 12:36:29', 0, 'admin super', 'famille fournisseur'),
(34, 'modification de la famille fournisseur:fournisseurr2', '2021-05-19 12:36:39', 0, 'admin super', 'famille fournisseur'),
(35, 'ajout de l\'article de reference : sp-215', '2021-05-19 13:04:13', 0, 'admin super', 'article'),
(36, 'ajout de l\'article de reference : sp-214', '2021-05-19 13:06:13', 0, 'admin super', 'article'),
(37, 'modification des information de l\'article de reference:sp-214', '2021-05-19 13:20:07', 0, 'admin super', 'article'),
(38, 'ajout de l\'article de reference : sp-558', '2021-05-19 13:24:18', 0, 'admin super', 'article'),
(39, 'ajout de l\'article de reference : string13', '2021-05-19 13:42:58', 0, 'admin super', 'article'),
(40, 'modification des information de l\'article de reference:string13', '2021-05-19 14:03:50', 0, 'admin super', 'article'),
(41, 'ajout de l\'article de reference : article-0021', '2021-05-19 17:47:03', 0, 'admin super', 'article'),
(42, 'modification de la famille article:eléctromenager', '2021-05-19 18:21:54', 0, 'admin super', 'famille article'),
(43, 'modification de la famille article:eléctromenager', '2021-05-19 18:21:57', 0, 'admin super', 'famille article'),
(44, 'ajout de l\'article de reference : stf-2322', '2021-05-19 18:25:47', 0, 'admin super', 'article'),
(45, 'modification des information de l\'article de reference:stf-2322', '2021-05-19 18:31:00', 0, 'admin super', 'article'),
(46, 'creation de la banque :test', '2021-05-19 18:37:39', 0, 'admin super', 'Banque'),
(47, 'creation de la banque :test2', '2021-05-19 18:38:33', 0, 'admin super', 'Banque'),
(48, 'modification de la banque:test2', '2021-05-19 18:40:22', 0, 'admin super', 'Banque'),
(49, 'creation du depot qui a pour nom: depot zonz 4 c', '2021-05-19 18:42:59', 0, 'admin super', 'depot'),
(50, 'modification du depot qui a pour nom: depot zonz 4 c', '2021-05-19 18:44:00', 0, 'admin super', 'depot'),
(51, 'modification du depot qui a pour nom: depot zonz 4 c', '2021-05-19 18:44:05', 0, 'admin super', 'depot'),
(52, 'creation de la famille fournisseur:fournisseur2', '2021-05-19 18:45:56', 0, 'admin super', 'famille fournisseur'),
(53, 'modification de la famille fournisseur:fournisseur3', '2021-05-19 18:46:09', 0, 'admin super', 'famille fournisseur'),
(54, 'modification de la famille fournisseur:fournisseur3', '2021-05-19 18:46:16', 0, 'admin super', 'famille fournisseur'),
(55, 'l\'utilisateur: Mariam Traore  a changé son mot de passe', '2021-05-19 19:48:07', 0, 'admin super', 'utilisateur'),
(56, 'l\'utilisateur: Mariam Traore  a changé son mot de passe', '2021-05-19 19:49:43', 127, 'Traore  Mariam', 'utilisateur'),
(57, 'Modification de l\'utilisateur: Mariam Traore ', '2021-05-19 19:50:52', 0, 'admin super', 'utilisateur'),
(58, 'l\'utilisateur: Mariam Traore  a changé son mot de passe', '2021-05-19 19:52:08', 127, 'Traore  Mariam', 'utilisateur'),
(59, 'Modification de l\'utilisateur: Mariam Traore ', '2021-05-19 20:00:49', 0, 'admin super', 'utilisateur'),
(60, 'l\'utilisateur: Mariam Traore  a changé son mot de passe', '2021-05-19 20:03:52', 127, 'Traore  Mariam', 'utilisateur'),
(61, 'Modification de l\'utilisateur: Atse admin Ollele', '2021-05-19 20:04:42', 0, 'admin super', 'utilisateur'),
(62, 'Modification de l\'utilisateur: Atse admin admin', '2021-05-19 20:06:07', 0, 'admin super', 'utilisateur'),
(63, 'Modification de l\'utilisateur: Atse  ollele', '2021-05-19 20:18:26', 0, 'admin super', 'utilisateur'),
(64, 'creation de l\'utilisateur: ousmane Diarra', '2021-05-19 20:27:21', 0, 'admin super', 'utilisateur'),
(65, 'Modification de l\'utilisateur: Mariam Traore ', '2021-05-19 20:27:31', 0, 'admin super', 'utilisateur'),
(66, 'l\'utilisateur: Mariam Traore  a changé son mot de passe', '2021-05-19 20:35:42', 0, 'admin super', 'utilisateur'),
(67, 'Modification de l\'utilisateur: Mariam Traore ', '2021-05-19 20:36:39', 0, 'admin super', 'utilisateur'),
(68, 'l\'utilisateur: Mariam Traore  a changé son mot de passe', '2021-05-19 20:41:11', 127, 'Traore  Mariam', 'utilisateur'),
(69, 'Modification de l\'utilisateur: Mariam Traore ', '2021-05-19 20:42:52', 0, 'admin super', 'utilisateur'),
(70, 'l\'utilisateur: Mariam Traore  a changé son mot de passe', '2021-05-19 20:43:07', 127, 'Traore  Mariam', 'utilisateur'),
(71, 'Modification de l\'utilisateur: Mariam Traore ', '2021-05-19 20:46:30', 0, 'admin super', 'utilisateur'),
(72, 'l\'utilisateur: Mariam Traore  a changé son mot de passe', '2021-05-19 20:47:04', 127, 'Traore  Mariam', 'utilisateur'),
(73, 'l\'utilisateur: ousmane Diarra a changé son mot de passe', '2021-05-19 20:55:45', 0, 'admin super', 'utilisateur'),
(74, 'l\'utilisateur: Mariam Traore  a changé son mot de passe', '2021-05-19 20:56:38', 0, 'admin super', 'utilisateur'),
(75, 'l\'utilisateur: Mariam Traore  a changé son mot de passe', '2021-05-19 20:58:06', 127, 'Traore  Mariam', 'utilisateur'),
(76, 'Modification de l\'utilisateur: Mariam Traore ', '2021-05-19 21:04:46', 0, 'admin super', 'utilisateur'),
(77, 'l\'utilisateur: Mariam Traore  a changé son mot de passe', '2021-05-19 21:05:23', 127, 'Traore  Mariam', 'utilisateur'),
(78, 'l\'utilisateur: Mariam Traore  a changé son mot de passe', '2021-05-19 21:22:15', 0, 'admin super', 'utilisateur'),
(79, 'l\'utilisateur: Mariam Traore  a changé son mot de passe', '2021-05-19 21:25:30', 127, 'Traore  Mariam', 'utilisateur'),
(80, 'Modification de l\'utilisateur: Mariam Traore', '2021-05-19 21:26:07', 0, 'admin super', 'utilisateur'),
(81, 'Modification de l\'utilisateur: Mariam Traore', '2021-05-19 21:26:16', 0, 'admin super', 'utilisateur'),
(82, 'l\'utilisateur: Mariam Traore a changé son mot de passe', '2021-05-19 21:32:17', 127, 'Traore Mariam', 'utilisateur'),
(83, 'Modification de l\'utilisateur: Mariam Traore ', '2021-05-19 21:32:37', 0, 'admin super', 'utilisateur'),
(84, 'Modification de l\'utilisateur: Mariam Traore ', '2021-05-19 21:32:52', 0, 'admin super', 'utilisateur'),
(85, 'ajout de la famille article:test', '2021-05-19 21:50:41', 0, 'admin super', 'famille article'),
(86, 'ajout de la famille article:errr', '2021-05-19 21:51:26', 0, 'admin super', 'famille article'),
(87, 'ajout de la famille article:Ffff', '2021-05-19 21:51:47', 0, 'admin super', 'famille article'),
(88, 'ajout de la famille article:rrrr', '2021-05-19 21:52:58', 0, 'admin super', 'famille article'),
(89, 'ajout de la famille article:ggggggggg', '2021-05-19 21:54:47', 0, 'admin super', 'famille article'),
(90, 'ajout de la famille article:azerty', '2021-05-19 21:57:55', 0, 'admin super', 'famille article'),
(91, 'ajout de l\'article de reference : sts-2223', '2021-05-19 22:22:18', 0, 'Admin Super', 'article'),
(92, 'ajout de l\'article de reference : stcd', '2021-05-19 23:22:18', 0, 'Admin Super', 'article'),
(93, 'modification des information de l\'article de reference:stcd', '2021-05-19 23:22:41', 0, 'Admin Super', 'article'),
(449, 'modification des prix clients de l\'article de reference : sts-2223', '2021-05-20 04:18:23', 0, 'Admin Super', 'article'),
(450, 'modification des prix clients de l\'article de reference : sts-2223', '2021-05-20 04:18:46', 0, 'Admin Super', 'article'),
(451, 'modification des prix clients de l\'article de reference : sts-2223', '2021-05-20 04:24:55', 0, 'Admin Super', 'article'),
(452, 'modification des information de l\'article de reference:sts-2223', '2021-05-20 06:27:29', 0, 'Admin Super', 'article'),
(453, 'modification des information de l\'article de reference:stcd', '2021-05-20 06:28:30', 0, 'Admin Super', 'article'),
(454, 'modification des information de l\'article de reference:sts-2223', '2021-05-20 06:28:51', 0, 'Admin Super', 'article'),
(455, 'modification des information de l\'article de reference:sts-2223', '2021-05-21 10:47:18', 0, 'Adjaratou Dabre', 'article'),
(456, 'modification des information de l\'article de reference:sts-2223', '2021-05-21 10:53:08', 0, 'Adjaratou Dabre', 'article'),
(457, 'modification des information de l\'article de reference:sts-2223', '2021-05-21 10:53:26', 0, 'Adjaratou Dabre', 'article'),
(458, 'modification des information de l\'article de reference:sts-2223', '2021-05-21 10:53:38', 0, 'Adjaratou Dabre', 'article'),
(459, 'Creation d\'une plannification de l\'article:Congelateur pour l\'anneé: 2021', '2021-05-21 20:08:04', 0, 'Adjaratou Dabre', 'plannification'),
(460, 'Modification de la plannification de l\'article:Congelateur pour l\'anneé: 2021', '2021-05-21 20:11:25', 0, 'Adjaratou Dabre', 'plannification'),
(461, 'Modification de la plannification de l\'article:Congelateur pour l\'anneé: 2021', '2021-05-21 20:11:46', 0, 'Adjaratou Dabre', 'plannification'),
(462, 'Creation d\'une plannification de l\'article:Congelateur pour l\'anneé: 2021', '2021-05-21 22:32:58', 0, 'Adjaratou Dabre', 'plannification'),
(463, 'Creation d\'une plannification de l\'article:Split 1 cheveau pour l\'anneé: 2022', '2021-05-21 22:43:58', 0, 'Adjaratou Dabre', 'plannification'),
(464, 'Modification de la plannification de l\'article:Split 1 cheveau pour l\'anneé: 2022', '2021-05-21 22:57:45', 0, 'Adjaratou Dabre', 'plannification'),
(465, 'Modification de la plannification de l\'article:Split 1 cheveau pour l\'anneé: 2022', '2021-05-21 22:59:27', 0, 'Adjaratou Dabre', 'plannification'),
(466, 'Modification de la plannification de l\'article:Split 1 cheveau pour l\'anneé: 2022', '2021-05-21 23:00:05', 0, 'Adjaratou Dabre', 'plannification'),
(467, 'Modification de la plannification de l\'article:Split 1 cheveau pour l\'anneé: 2022', '2021-05-21 23:00:42', 0, 'Adjaratou Dabre', 'plannification'),
(468, 'Modification de la plannification de l\'article:Split 1 cheveau pour l\'anneé: 2021', '2021-05-21 23:01:40', 0, 'Adjaratou Dabre', 'plannification'),
(469, 'Modification de la plannification de l\'article:Congelateur pour l\'anneé: 2022', '2021-05-22 02:02:51', 0, 'Adjaratou Dabre', 'plannification'),
(470, 'Creation d\'une plannification de l\'article:Split 1 cheveau pour l\'anneé: 2021', '2021-05-22 02:05:13', 0, 'Adjaratou Dabre', 'plannification'),
(471, 'Creation d\'une plannification de l\'article:Congelateur pour l\'anneé: 2021', '2021-05-23 09:30:02', 0, 'Adjaratou Dabre', 'plannification'),
(472, 'modification de la famille fournisseur:SUPER FOURNISSEUR', '2021-05-25 19:16:12', 0, 'Adjaratou Dabre', 'famille fournisseur');

-- --------------------------------------------------------

--
-- Structure de la table `planning`
--

DROP TABLE IF EXISTS `planning`;
CREATE TABLE IF NOT EXISTS `planning` (
  `id` int NOT NULL AUTO_INCREMENT,
  `article_id` int NOT NULL,
  `year_id` int NOT NULL,
  `expected_quantity` int NOT NULL,
  `is_active` tinyint(1) NOT NULL,
  `stock_min_month` int NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- Déchargement des données de la table `planning`
--

INSERT INTO `planning` (`id`, `article_id`, `year_id`, `expected_quantity`, `is_active`, `stock_min_month`) VALUES
(1, 2, 2, 3200, 1, 2),
(2, 1, 2, 3200, 1, 2),
(3, 1, 1, 2000, 1, 3),
(4, 2, 1, 8000, 1, 3);

-- --------------------------------------------------------

--
-- Structure de la table `resource`
--

DROP TABLE IF EXISTS `resource`;
CREATE TABLE IF NOT EXISTS `resource` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(50) COLLATE utf8mb4_unicode_ci NOT NULL,
  `is_active` tinyint(1) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- Déchargement des données de la table `resource`
--

INSERT INTO `resource` (`id`, `name`, `is_active`) VALUES
(1, 'Menu', 1),
(2, 'Utilisateur', 1),
(3, 'Role', 1),
(4, 'Depot', 1),
(5, 'Vehicule de transport', 1),
(6, 'banque', 1),
(7, 'categorie fournisseur', 1),
(8, 'fournisseur', 1),
(9, 'categorie client', 1),
(10, 'client', 1),
(11, 'famille article', 1),
(12, 'sous famille article', 1),
(13, 'article', 1);

-- --------------------------------------------------------

--
-- Structure de la table `role`
--

DROP TABLE IF EXISTS `role`;
CREATE TABLE IF NOT EXISTS `role` (
  `id` tinyint NOT NULL AUTO_INCREMENT,
  `name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `authorities` text CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci,
  `is_active` tinyint(1) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- Déchargement des données de la table `role`
--

INSERT INTO `role` (`id`, `name`, `authorities`, `is_active`) VALUES
(0, 'super admin', 'menu:dashboard, menu:article, menu:stock, menu:depot, menu:supplier_purchase, menu:customer_sale, menu:customer_payment, menu_after_sale_service, menu:history, menu_statistic, menu:basic_files, menu:user_managenement, event_log:list, app_param:setting, purchase:search_box, menu:notification, menu:setting, user:add, user:update, user:reset_password, user:list, role:add, role:update, role:list', 1),
(1, 'admin', 'menu:dashboard, menu:article, menu:stock, menu:depot, menu:supplier_purchase, menu:customer_sale, menu:customer_payment, menu_after_sale_service, menu:history, menu_statistic, menu:basic_files, menu:user_managenement, event_log:list, app_param:setting, purchase:search_box, menu:notification, menu:setting, user:add, user:update, user:reset_password, user:list, role:add, role:update, role:list', 1);

-- --------------------------------------------------------

--
-- Structure de la table `season`
--

DROP TABLE IF EXISTS `season`;
CREATE TABLE IF NOT EXISTS `season` (
  `id` int NOT NULL AUTO_INCREMENT,
  `planning_id` int NOT NULL,
  `beginning_season` int NOT NULL,
  `end_season` int NOT NULL,
  `percentage` int NOT NULL,
  `is_active` tinyint(1) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=21 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- Déchargement des données de la table `season`
--

INSERT INTO `season` (`id`, `planning_id`, `beginning_season`, `end_season`, `percentage`, `is_active`) VALUES
(11, 2, 1, 3, 30, 1),
(12, 2, 4, 7, 40, 1),
(15, 1, 1, 3, 30, 1),
(16, 1, 4, 7, 40, 1),
(17, 3, 0, 2, 30, 1),
(18, 3, 3, 6, 50, 1),
(19, 4, 4, 7, 50, 1),
(20, 4, 8, 9, 10, 1);

-- --------------------------------------------------------

--
-- Structure de la table `supplier`
--

DROP TABLE IF EXISTS `supplier`;
CREATE TABLE IF NOT EXISTS `supplier` (
  `id` int NOT NULL AUTO_INCREMENT,
  `supplier_number` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `trade_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `phone` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `phone2` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `phone3` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `email` varchar(100) COLLATE utf8mb4_unicode_ci NOT NULL,
  `address` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `supplier_family_id` int NOT NULL,
  `interlocutor_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `interlocutor_phone` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `supplier_account_id` int DEFAULT NULL,
  `trade_register_number` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `corporate_name` varchar(100) COLLATE utf8mb4_unicode_ci NOT NULL,
  `is_active` tinyint(1) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=30 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- Déchargement des données de la table `supplier`
--

INSERT INTO `supplier` (`id`, `supplier_number`, `trade_name`, `phone`, `phone2`, `phone3`, `email`, `address`, `supplier_family_id`, `interlocutor_name`, `interlocutor_phone`, `supplier_account_id`, `trade_register_number`, `corporate_name`, `is_active`) VALUES
(18, 'four-00018', 'string15', 'string', 'string', 'string', 'string15', 'string', 1, 'string', 'string', 13, 'string', 'string15', 1),
(19, 'four-00019', 'string16', 'string', 'string', 'string', 'string16', 'string', 1, 'string', 'string', 14, 'string', 'string16', 1),
(22, 'four-00022', 'string191', 'string1', 'string1', 'string1', 'string19@gmail.com', 'string1', 1, 'string1', 'string', 17, 'string111', 'string191', 1),
(23, 'four-00023', 'vne', '1111111111111111', '1111111111111', '111111111111', 'info@vne-ci.com', 'abidjan', 1, 'toure mohamed', '010101010', 18, 'yuuiioo125nkl', 'vne', 1),
(25, 'F/S-00025', 'olympe', '', '', '', '', '', 2, '', '', 20, '', 'olympe', 0),
(26, 'F/S-00026', 'fourn1', '', '', '', '', '', 1, '', '', 21, '', 'fourn1', 1),
(27, 'F/S-00027', 'fourn 2', '', '', '', '', '', 2, '', '', 22, '', 'fourn 2', 1),
(28, 'F/S-00028', 'abc corporate', 'ssssssssss', 'sssssssssss', 'sssssssss', 'ssssssss@email.com', 'ssssssss', 2, 'ssssssss', 'sssssssssss', 23, 'sssssssss', 'abc corporate', 1),
(29, 'F/S-00029', 'eeeeee', '', '', '', '', '', 1, '', '', 24, '', 'eeeeeeeee', 1);

-- --------------------------------------------------------

--
-- Structure de la table `supplier_account`
--

DROP TABLE IF EXISTS `supplier_account`;
CREATE TABLE IF NOT EXISTS `supplier_account` (
  `id` int NOT NULL AUTO_INCREMENT,
  `supplier_id` int NOT NULL,
  `account_number` int NOT NULL,
  `balance` double NOT NULL DEFAULT '0',
  `ceiling_balance` double NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=25 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- Déchargement des données de la table `supplier_account`
--

INSERT INTO `supplier_account` (`id`, `supplier_id`, `account_number`, `balance`, `ceiling_balance`) VALUES
(1, 6, 1111, 0, 0),
(2, 7, 1111, 0, 0),
(3, 8, 8, 0, 0),
(4, 9, 41100009, 0, 0),
(5, 10, 41100010, 0, 0),
(6, 11, 41100011, 100000, 1000000),
(7, 12, 41100012, 100000, 1000000),
(8, 13, 40100013, 100000, 1000000),
(9, 14, 40100014, 100000, 1000000),
(10, 15, 40100015, 100000, 1000000),
(11, 16, 40100016, 100000, 1000000),
(12, 17, 40100017, 100000, 1000000),
(13, 18, 40100018, 110000, 1700000),
(14, 19, 40100019, 110000, 1700000),
(15, 20, 40100020, 110000, 1700000),
(16, 21, 40100021, 110000, 1700000),
(17, 22, 40100022, 110000, 1700000),
(18, 23, 40100023, 20000000, 30000000),
(19, 24, 40100024, 0, 0),
(20, 25, 40100025, 0, 0),
(21, 26, 40100026, 0, 0),
(22, 27, 40100027, 0, 0),
(23, 28, 40100028, 0, 0),
(24, 29, 40100029, 1200000.5, 0);

-- --------------------------------------------------------

--
-- Structure de la table `supplier_family`
--

DROP TABLE IF EXISTS `supplier_family`;
CREATE TABLE IF NOT EXISTS `supplier_family` (
  `id` int NOT NULL AUTO_INCREMENT,
  `supplier_name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `is_active` tinyint(1) NOT NULL DEFAULT '1',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- Déchargement des données de la table `supplier_family`
--

INSERT INTO `supplier_family` (`id`, `supplier_name`, `is_active`) VALUES
(1, 'SUPER FOURNISSEUR', 1),
(2, 'FOURNISSEUR', 1),
(3, 'FOURNISSEUR 1', 0),
(4, 'fournisseur3', 1),
(5, 'fournisseur2', 1);

-- --------------------------------------------------------

--
-- Structure de la table `supplier_order`
--

DROP TABLE IF EXISTS `supplier_order`;
CREATE TABLE IF NOT EXISTS `supplier_order` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `ordering_date` date NOT NULL,
  `supplier_id` int NOT NULL,
  `forcast_date` date DEFAULT NULL,
  `ordering_state` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `ordering_number` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `total_value_with_fret` double DEFAULT NULL,
  `total_value_without_fret` double DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- Déchargement des données de la table `supplier_order`
--

INSERT INTO `supplier_order` (`id`, `ordering_date`, `supplier_id`, `forcast_date`, `ordering_state`, `ordering_number`, `total_value_with_fret`, `total_value_without_fret`) VALUES
(1, '2021-05-21', 25, '2021-05-21', 'a', 'string', 0, 0),
(3, '2021-05-21', 25, '2021-05-21', 'a', 'string2', 0, 0),
(4, '2021-05-21', 25, '2021-05-21', 'a', 'string3', 0, 0);

-- --------------------------------------------------------

--
-- Structure de la table `supplier_order_loading`
--

DROP TABLE IF EXISTS `supplier_order_loading`;
CREATE TABLE IF NOT EXISTS `supplier_order_loading` (
  `id` int NOT NULL AUTO_INCREMENT,
  `article_ordered_id` bigint NOT NULL,
  `delivery_note_number` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `delivery_note_status` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `invoice_number` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `expected_delivery_date` date NOT NULL,
  `quantity` int NOT NULL,
  `clearance` double DEFAULT NULL,
  `container_number` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `laoding_date` date DEFAULT NULL,
  `container_type` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `ve` varchar(100) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `vw` varchar(100) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `declaration` varchar(100) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `circuit` varchar(100) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `shipper` varchar(100) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `transit` varchar(100) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `status` tinyint(1) DEFAULT NULL,
  `co` varchar(100) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `putting_into_use` varchar(100) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `released` tinyint(1) DEFAULT NULL,
  `sent_to` varchar(100) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `total_value_with_fret` double DEFAULT NULL,
  `total_value_without_fret` double DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- --------------------------------------------------------

--
-- Structure de la table `supplier_payment`
--

DROP TABLE IF EXISTS `supplier_payment`;
CREATE TABLE IF NOT EXISTS `supplier_payment` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `supplier_laoding_id` bigint NOT NULL,
  `payment_date` date NOT NULL,
  `payment_type` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `payment_amount` double NOT NULL,
  `payment _status` varchar(100) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `note` text COLLATE utf8mb4_unicode_ci,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- --------------------------------------------------------

--
-- Structure de la table `transport_vehicle`
--

DROP TABLE IF EXISTS `transport_vehicle`;
CREATE TABLE IF NOT EXISTS `transport_vehicle` (
  `id` int NOT NULL AUTO_INCREMENT,
  `driver_name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `driver_contact` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `register_num` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `chassis_num` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `vehicle_color` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `vehicle_brand` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `car_registration_doc` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `comment` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `is_active` tinyint(1) NOT NULL DEFAULT '1',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- Déchargement des données de la table `transport_vehicle`
--

INSERT INTO `transport_vehicle` (`id`, `driver_name`, `driver_contact`, `register_num`, `chassis_num`, `vehicle_color`, `vehicle_brand`, `car_registration_doc`, `comment`, `is_active`) VALUES
(1, 'string', 'string', 'string', 'string', 'string', 'string', 'string', 'string', 1),
(2, 'mathurin', NULL, 'aer11111', '11111', 'rouge', 'Rav4', '414f', 'ce vehicule est chargé de livrer les refrigerateurs', 1),
(3, 'madame', NULL, 'aer1', '11111785', 'rouge', 'Ben', '4y4f', 'ce vehicule est chargé de livrer les climatiseurs', 1),
(4, 'Soumahoro ', NULL, '111 modif', '11111 modif', 'vertmodif', 'phantom modif', '4141 modif', 'ce vehicule est chargé de livrer les televisions modif', 0),
(5, 'Soumahoro ', NULL, '225FA10 ', '11111 modif', 'vertmodif', 'phantom modif', '4141 modif', 'ce vehicule est chargé de livrer les televisions modif', 0),
(6, 'BAMBA', '0546252314', '21F1AJ25', NULL, NULL, NULL, '41254DFFE', 'la voiture doit aller au garage avant le 22', 1),
(7, 'BAMBd', '0546252312', '21F1AJ22', NULL, NULL, NULL, '41254DFF51', 'la voitur doit aller au garage avant le 22', 1),
(8, 'string', 'string', 'string', 'string', 'string', 'string', 'string', 'string', 1),
(9, 'BAMBA', '0546252314', '21F1AJ24', NULL, NULL, NULL, '41254DFFE', 'la voiture doit aller au garage avant le 22', 1),
(10, 'BAMBA', '0546252314', '21F1AJ27', NULL, NULL, NULL, '41254DFFE', 'la voiture doit aller au garage avant le 22', 1),
(11, 'cama mori', '0545368965', '247863-HD', NULL, NULL, NULL, '254136FV', 'véhicule propre sans egratignure', 0),
(12, 'toure mohmed', '0506125572', 'azerty123', NULL, NULL, NULL, 'aaaaaaaaaaaa', 'test test test test test', 0),
(13, 'soum hamed', '11111111111111111111', 'gggggggggg', NULL, NULL, NULL, 'gggggggggggg', 'bon chauffeur', 1);

-- --------------------------------------------------------

--
-- Structure de la table `user`
--

DROP TABLE IF EXISTS `user`;
CREATE TABLE IF NOT EXISTS `user` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `user_id` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `first_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `last_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `username` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `password` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `email` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `tel` varchar(50) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `depot_id` int NOT NULL,
  `profile_image_url` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `last_login_date` datetime DEFAULT NULL,
  `last_login_date_display` datetime DEFAULT NULL,
  `join_date` datetime NOT NULL,
  `role` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `role_ids` varchar(50) COLLATE utf8mb4_unicode_ci NOT NULL,
  `authorities` text COLLATE utf8mb4_unicode_ci,
  `is_active` tinyint(1) NOT NULL DEFAULT '1',
  `is_not_locked` tinyint(1) NOT NULL DEFAULT '0',
  `password_must_be_change` tinyint(1) NOT NULL DEFAULT '1',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- Déchargement des données de la table `user`
--

INSERT INTO `user` (`id`, `user_id`, `first_name`, `last_name`, `username`, `password`, `email`, `tel`, `depot_id`, `profile_image_url`, `last_login_date`, `last_login_date_display`, `join_date`, `role`, `role_ids`, `authorities`, `is_active`, `is_not_locked`, `password_must_be_change`) VALUES
(0, '1234567890', 'Dabre', 'adjaratou', 'adabre265', '$2y$10$mrPrYVxUrb4iZuKgFdSyMOvDIkazkXYeFk7nJQ7lbgQcbcZwVHlP2', 'adjaratoudabre22@gmail.com', '0506125572', 0, '', '2021-05-25 19:14:35', '2021-05-24 10:55:25', '2021-05-02 21:32:41', '', '0', 'tous', 1, 1, 0),
(1, '1234567891', 'Soumahoro', 'hamed', 'shamed225', '$2y$10$mrPrYVxUrb4iZuKgFdSyMOvDIkazkXYeFk7nJQ7lbgQcbcZwVHlP2', 'hamedsoum4023@gmail.com', '0787008315', 0, '', '2021-05-20 07:34:46', '2021-05-20 07:34:38', '2021-05-02 21:32:41', 'super admin', '0', 'menu:dashboard, menu:article, menu:stock, menu:depot, menu:supplier_purchase, menu:customer_sale, menu:customer_payment, menu_after_sale_service, menu:history, menu_statistic, menu:basic_files, menu:user_managenement, event_log:list, app_param:setting, purchase:search_box, menu:notification, menu:setting, user:add, user:update, user:reset_password, user:list, role:add, role:update, role:list', 1, 1, 0);

-- --------------------------------------------------------

--
-- Structure de la table `user_role`
--

DROP TABLE IF EXISTS `user_role`;
CREATE TABLE IF NOT EXISTS `user_role` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `user_id` bigint NOT NULL,
  `role_id` int NOT NULL,
  `is_active` tinyint(1) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- Déchargement des données de la table `user_role`
--

INSERT INTO `user_role` (`id`, `user_id`, `role_id`, `is_active`) VALUES
(1, 0, 0, 1),
(2, 1, 0, 1);

-- --------------------------------------------------------

--
-- Structure de la table `year`
--

DROP TABLE IF EXISTS `year`;
CREATE TABLE IF NOT EXISTS `year` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` int NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- Déchargement des données de la table `year`
--

INSERT INTO `year` (`id`, `name`) VALUES
(1, 2021),
(2, 2022);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
