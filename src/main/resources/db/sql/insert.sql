--
-- Déchargement des données de la table `app_param`
--

INSERT INTO `app_param` (`id`, `name_company`, `legal_form`, `phone1`, `phone2`, `phone3`, `currency`, `address`, `website`, `email`, `postal_code`, `logo`, `cachet`, `trade register`, `bank_account`, `head_office`, `activity`, `slogan`, `foot_page`, `header`) VALUES
(1, 'SOCIETE BIBLOS', 'sarl', '07 58 80 00 00', '', '', 'fcfa', '01 BP 429 Abjdjan 01 - Deux plateaux - Vallon - Cocody', '', '', '', 'assets/images/logo-biblos.png', NULL, '', '', '', 'Vente et Fourniture de Matériels Divers', 'la perfection a votre service', 'Siège sociale : Cococdy rue des jardins - 01 BP 429 Abjdjan 01- Tel: 07 58 80 00 00 - RCCM: CI-ABJ-2011-B-5712 - Compte Bancaire: GT Bank N°/ CI163 01202 000000046942-78', '');
COMMIT;

--
-- Déchargement des données de la table `article_family`
--

INSERT INTO `article_family` (`id`, `name`, `is_active`) VALUES
(1, 'Electromenager', 1);

--
-- Déchargement des données de la table `article_sub_family`
--

INSERT INTO `article_sub_family` (`id`, `name`, `family_id`, `is_active`) VALUES
(1, 'Ventilateur', 1, 1);
(2, 'Congelateur', 1, 1);
(3, 'Televiseur', 1, 1);

--
-- Déchargement des données de la table `bank`
--
    INSERT INTO `bank`(`id`, `name`, `email`, `postal_box`, `phone`, `is_active`) VALUES (1,"BICICI",NULL,NULL,NULL,1);
    INSERT INTO `bank`(`id`, `name`, `email`, `postal_box`, `phone`, `is_active`) VALUES (2,"NSIA BANQUE",NULL,NULL,NULL,1);
    INSERT INTO `bank`(`id`, `name`, `email`, `postal_box`, `phone`, `is_active`) VALUES (3,"SIB",NULL,NULL,NULL,1);
    INSERT INTO `bank`(`id`, `name`, `email`, `postal_box`, `phone`, `is_active`) VALUES (4,"SGBCI3",NULL,NULL,NULL,1);
    INSERT INTO `bank`(`id`, `name`, `email`, `postal_box`, `phone`, `is_active`) VALUES (5,"CITIBANK CI",NULL,NULL,NULL,1);
    INSERT INTO `bank`(`id`, `name`, `email`, `postal_box`, `phone`, `is_active`) VALUES (6,"BOA",NULL,NULL,NULL,1);
    INSERT INTO `bank`(`id`, `name`, `email`, `postal_box`, `phone`, `is_active`) VALUES (7,"BANQUE ATLANTIQUE (BACI)",NULL,NULL,NULL,1);
    INSERT INTO `bank`(`id`, `name`, `email`, `postal_box`, `phone`, `is_active`) VALUES (8,"ECOBANK",NULL,NULL,NULL,1);
    INSERT INTO `bank`(`id`, `name`, `email`, `postal_box`, `phone`, `is_active`) VALUES (9,"BHCI",NULL,NULL,NULL,1);
    INSERT INTO `bank`(`id`, `name`, `email`, `postal_box`, `phone`, `is_active`) VALUES (10,"BNI",NULL,NULL,NULL,1);
    INSERT INTO `bank`(`id`, `name`, `email`, `postal_box`, `phone`, `is_active`) VALUES (11,"STANDARD CHARTERED BANK",NULL,NULL,NULL,1);
    INSERT INTO `bank`(`id`, `name`, `email`, `postal_box`, `phone`, `is_active`) VALUES (12,"AFRILAND FIRST BANK",NULL,NULL,NULL,1);
    INSERT INTO `bank`(`id`, `name`, `email`, `postal_box`, `phone`, `is_active`) VALUES (13,"VERSUS BANK",NULL,NULL,NULL,1);
    INSERT INTO `bank`(`id`, `name`, `email`, `postal_box`, `phone`, `is_active`) VALUES (14,"ORABANK",NULL,NULL,NULL,1);
    INSERT INTO `bank`(`id`, `name`, `email`, `postal_box`, `phone`, `is_active`) VALUES (15,"BRIDGE BANK GROUP (BBG - CI)",NULL,NULL,NULL,1);
    INSERT INTO `bank`(`id`, `name`, `email`, `postal_box`, `phone`, `is_active`) VALUES (16,"UNITED BANK FOR AFRICA (UBA)",NULL,NULL,NULL,1);
    INSERT INTO `bank`(`id`, `name`, `email`, `postal_box`, `phone`, `is_active`) VALUES (17,"BSIC - COTE D’IVOIRE",NULL,NULL,NULL,1);
    INSERT INTO `bank`(`id`, `name`, `email`, `postal_box`, `phone`, `is_active`) VALUES (18,"BGFIBANK",NULL,NULL,NULL,1);
    INSERT INTO `bank`(`id`, `name`, `email`, `postal_box`, `phone`, `is_active`) VALUES (19,"BANQUE POPULAIRE",NULL,NULL,NULL,1);
    INSERT INTO `bank`(`id`, `name`, `email`, `postal_box`, `phone`, `is_active`) VALUES (20,"GTBANK",NULL,NULL,NULL,1);
    INSERT INTO `bank`(`id`, `name`, `email`, `postal_box`, `phone`, `is_active`) VALUES (21,"CORIS BANK INTERNATIONAL",NULL,NULL,NULL,1);
    INSERT INTO `bank`(`id`, `name`, `email`, `postal_box`, `phone`, `is_active`) VALUES (22,"BANQUE DE L’UNION (BDU-CI)",NULL,NULL,NULL,1);
    INSERT INTO `bank`(`id`, `name`, `email`, `postal_box`, `phone`, `is_active`) VALUES (23,"STANBIC BANK",NULL,NULL,NULL,1);
    INSERT INTO `bank`(`id`, `name`, `email`, `postal_box`, `phone`, `is_active`) VALUES (24,"BANQUE D'ABIDJAN",NULL,NULL,NULL,1);
    INSERT INTO `bank`(`id`, `name`, `email`, `postal_box`, `phone`, `is_active`) VALUES (25,"MANSA BANK",NULL,NULL,NULL,1);
    INSERT INTO `bank`(`id`, `name`, `email`, `postal_box`, `phone`, `is_active`) VALUES (26,"ORANGE BANK",NULL,NULL,NULL,1);

--
-- Déchargement des données de la table `carrier`
--
INSERT INTO `carrier` (`id`, `name`, `phone`, `address`, `email`, `interlocutor_name`, `interlocutor_phone`, `is_active`) VALUES
(1, 'CMA CGM', '', '', '', '', '', 1);


--
-- Déchargement des données de la table `container_cost`
--
INSERT INTO `container_cost` (`id`, `name`, `is_active`, `description`) VALUES
(1, 'DOC+BSC', 1, ''),
(2, 'FRET', 1, ''),
(3, 'transit', 1, ''),
(4, 'sydam', 1, ''),
(5, 'acconage', 1, ''),
(6, 'echange BL', 1, ''),
(7, 'Sursalaire', 1, ''),
(8, 'livraison', 1, ''),
(9, 'mise a terre', 1, ''),
(10, 'phyto', 1, ''),
(11, 'caution', 1, ''),
(12, 'Assurance', 1, ''),
(13, 'ts douane', 1, ''),
(14, 'ts darriv', 1, ''),
(15, 'frais divers', 1, ''),
(16, 'ts medlog', 1, ''),
(17, 'COC', 1, ''),
(18, 'TVA', 1, ''),
(19, 'DDE', 1, ''),
(20, 'Douane', 1, '');

--
-- Déchargement des données de la table `container_type`
--
INSERT INTO `container_type`(`id`, `name`, `is_active`) VALUES (1, "20HC",1);
INSERT INTO `container_type`(`id`, `name`, `is_active`) VALUES (2, "40HC",1);
INSERT INTO `container_type`(`id`, `name`, `is_active`) VALUES (3, "45HC",1);

--
-- Déchargement des données de la table `country`
--
insert into country(id,name,iso_code) values(1,'Afghanistan','AFG');
insert into country(id,name,iso_code) values(2,'Albanie','ALB');
insert into country(id,name,iso_code) values(3,'Antarctique','ATA');
insert into country(id,name,iso_code) values(4,'Algérie','DZA');
insert into country(id,name,iso_code) values(5,'Samoa Américaines','ASM');
insert into country(id,name,iso_code) values(6,'Andorre','AND');
insert into country(id,name,iso_code) values(7,'Angola','AGO');
insert into country(id,name,iso_code) values(8,'Antigua-et-Barbuda','ATG');
insert into country(id,name,iso_code) values(9,'Azerbaïdjan','AZE');
insert into country(id,name,iso_code) values(10,'Argentine','ARG');
insert into country(id,name,iso_code) values(11,'Australie','AUS');
insert into country(id,name,iso_code) values(12,'Autriche','AUT');
insert into country(id,name,iso_code) values(13,'Bahamas','BHS');
insert into country(id,name,iso_code) values(14,'Bahreïn','BHR');
insert into country(id,name,iso_code) values(15,'Bangladesh','BGD');
insert into country(id,name,iso_code) values(16,'Arménie','ARM');
insert into country(id,name,iso_code) values(17,'Barbade','BRB');
insert into country(id,name,iso_code) values(18,'Belgique','BEL');
insert into country(id,name,iso_code) values(19,'Bermudes','BMU');
insert into country(id,name,iso_code) values(20,'Bhoutan','BTN');
insert into country(id,name,iso_code) values(21,'Bolivie','BOL');
insert into country(id,name,iso_code) values(22,'Bosnie-Herzégovine','BIH');
insert into country(id,name,iso_code) values(23,'Botswana','BWA');
insert into country(id,name,iso_code) values(24,'Île Bouvet','BVT');
insert into country(id,name,iso_code) values(25,'Brésil','BRA');
insert into country(id,name,iso_code) values(26,'Belize','BLZ');
insert into country(id,name,iso_code) values(27,'Territoire Britannique de l\'Océan Indien','IOT');
insert into country(id,name,iso_code) values(28,'Îles Salomon','SLB');
insert into country(id,name,iso_code) values(29,'Îles Vierges Britanniques','VGB');
insert into country(id,name,iso_code) values(30,'Brunéi Darussalam','BRN');
insert into country(id,name,iso_code) values(31,'Bulgarie','BGR');
insert into country(id,name,iso_code) values(32,'Myanmar','MMR');
insert into country(id,name,iso_code) values(33,'Burundi','BDI');
insert into country(id,name,iso_code) values(34,'Bélarus','BLR');
insert into country(id,name,iso_code) values(35,'Cambodge','KHM');
insert into country(id,name,iso_code) values(36,'Cameroun','CMR');
insert into country(id,name,iso_code) values(37,'Canada','CAN');
insert into country(id,name,iso_code) values(38,'Cap-vert','CPV');
insert into country(id,name,iso_code) values(39,'Îles Caïmanes','CYM');
insert into country(id,name,iso_code) values(40,'République Centrafricaine','CAF');
insert into country(id,name,iso_code) values(41,'Sri Lanka','LKA');
insert into country(id,name,iso_code) values(42,'Tchad','TCD');
insert into country(id,name,iso_code) values(43,'Chili','CHL');
insert into country(id,name,iso_code) values(44,'Chine','CHN');
insert into country(id,name,iso_code) values(45,'Taïwan','TWN');
insert into country(id,name,iso_code) values(46,'Île Christmas','CXR');
insert into country(id,name,iso_code) values(47,'Îles Cocos (Keeling)','CCK');
insert into country(id,name,iso_code) values(48,'Colombie','COL');
insert into country(id,name,iso_code) values(49,'Comores','COM');
insert into country(id,name,iso_code) values(50,'Mayotte','MYT');
insert into country(id,name,iso_code) values(51,'République du Congo','COG');
insert into country(id,name,iso_code) values(52,'République Démocratique du Congo','COD');
insert into country(id,name,iso_code) values(53,'Îles Cook','COK');
insert into country(id,name,iso_code) values(54,'Costa Rica','CRI');
insert into country(id,name,iso_code) values(55,'Croatie','HRV');
insert into country(id,name,iso_code) values(56,'Cuba','CUB');
insert into country(id,name,iso_code) values(57,'Chypre','CYP');
insert into country(id,name,iso_code) values(58,'République Tchèque','CZE');
insert into country(id,name,iso_code) values(59,'Bénin','BEN');
insert into country(id,name,iso_code) values(60,'Danemark','DNK');
insert into country(id,name,iso_code) values(61,'Dominique','DMA');
insert into country(id,name,iso_code) values(62,'République Dominicaine','DOM');
insert into country(id,name,iso_code) values(63,'Equateur','ECU');
insert into country(id,name,iso_code) values(64,'El Salvador','SLV');
insert into country(id,name,iso_code) values(65,'Guinée Equatoriale','GNQ');
insert into country(id,name,iso_code) values(66,'Ethiopie','ETH');
insert into country(id,name,iso_code) values(67,'Erythrée','ERI');
insert into country(id,name,iso_code) values(68,'Estonie','EST');
insert into country(id,name,iso_code) values(69,'Îles Féroé','FRO');
insert into country(id,name,iso_code) values(70,'Îles (malvinas) Falkland','FLK');
insert into country(id,name,iso_code) values(71,'Géorgie du Sud et les Îles Sandwich du Sud','SGS');
insert into country(id,name,iso_code) values(72,'Fidji','FJI');
insert into country(id,name,iso_code) values(73,'Finlande','FIN');
insert into country(id,name,iso_code) values(74,'Îles Ã…land','ALA');
insert into country(id,name,iso_code) values(75,'France','FRA');
insert into country(id,name,iso_code) values(76,'Guyane Française','GUF');
insert into country(id,name,iso_code) values(77,'Polynésie Française','PYF');
insert into country(id,name,iso_code) values(78,'Terres Australes Françaises','ATF');
insert into country(id,name,iso_code) values(79,'Djibouti','DJI');
insert into country(id,name,iso_code) values(80,'Gabon','GAB');
insert into country(id,name,iso_code) values(81,'Géorgie','GEO');
insert into country(id,name,iso_code) values(82,'Gambie','GMB');
insert into country(id,name,iso_code) values(83,'Territoire Palestinien Occupé','PSE');
insert into country(id,name,iso_code) values(84,'Allemagne','DEU');
insert into country(id,name,iso_code) values(85,'Ghana','GHA');
insert into country(id,name,iso_code) values(86,'Gibraltar','GIB');
insert into country(id,name,iso_code) values(87,'Kiribati','KIR');
insert into country(id,name,iso_code) values(88,'Grèce','GRC');
insert into country(id,name,iso_code) values(89,'Groenland','GRL');
insert into country(id,name,iso_code) values(90,'Grenade','GRD');
insert into country(id,name,iso_code) values(91,'Guadeloupe','GLP');
insert into country(id,name,iso_code) values(92,'Guam','GUM');
insert into country(id,name,iso_code) values(93,'Guatemala','GTM');
insert into country(id,name,iso_code) values(94,'Guinée','GIN');
insert into country(id,name,iso_code) values(95,'Guyana','GUY');
insert into country(id,name,iso_code) values(96,'Haïti','HTI');
insert into country(id,name,iso_code) values(97,'Îles Heard et Mcdonald','HMD');
insert into country(id,name,iso_code) values(98,'Saint-Siège (état de la Cité du Vatican)','VAT');
insert into country(id,name,iso_code) values(99,'Honduras','HND');
insert into country(id,name,iso_code) values(100,'Hong-Kong','HKG');
insert into country(id,name,iso_code) values(101,'Hongrie','HUN');
insert into country(id,name,iso_code) values(102,'Islande','ISL');
insert into country(id,name,iso_code) values(103,'Inde','IND');
insert into country(id,name,iso_code) values(104,'Indonésie','IDN');
insert into country(id,name,iso_code) values(105,'République Islamique d\'Iran','IRN');
insert into country(id,name,iso_code) values(106,'Iraq','IRQ');
insert into country(id,name,iso_code) values(107,'Irlande','IRL');
insert into country(id,name,iso_code) values(108,'Israël','ISR');
insert into country(id,name,iso_code) values(109,'Italie','ITA');
insert into country(id,name,iso_code) values(110,'Côte d\'Ivoire','CIV');
insert into country(id,name,iso_code) values(111,'Jamaïque','JAM');
insert into country(id,name,iso_code) values(112,'Japon','JPN');
insert into country(id,name,iso_code) values(113,'Kazakhstan','KAZ');
insert into country(id,name,iso_code) values(114,'Jordanie','JOR');
insert into country(id,name,iso_code) values(115,'Kenya','KEN');
insert into country(id,name,iso_code) values(116,'République Populaire Démocratique de Corée','PRK');
insert into country(id,name,iso_code) values(117,'République de Corée','KOR');
insert into country(id,name,iso_code) values(118,'Koweït','KWT');
insert into country(id,name,iso_code) values(119,'Kirghizistan','KGZ');
insert into country(id,name,iso_code) values(120,'République Démocratique Populaire Lao','LAO');
insert into country(id,name,iso_code) values(121,'Liban','LBN');
insert into country(id,name,iso_code) values(122,'Lesotho','LSO');
insert into country(id,name,iso_code) values(123,'Lettonie','LVA');
insert into country(id,name,iso_code) values(124,'Libéria','LBR');
insert into country(id,name,iso_code) values(125,'Jamahiriya Arabe Libyenne','LBY');
insert into country(id,name,iso_code) values(126,'Liechtenstein','LIE');
insert into country(id,name,iso_code) values(127,'Lituanie','LTU');
insert into country(id,name,iso_code) values(128,'Luxembourg','LUX');
insert into country(id,name,iso_code) values(129,'Macao','MAC');
insert into country(id,name,iso_code) values(130,'Madagascar','MDG');
insert into country(id,name,iso_code) values(131,'Malawi','MWI');
insert into country(id,name,iso_code) values(132,'Malaisie','MYS');
insert into country(id,name,iso_code) values(133,'Maldives','MDV');
insert into country(id,name,iso_code) values(134,'Mali','MLI');
insert into country(id,name,iso_code) values(135,'Malte','MLT');
insert into country(id,name,iso_code) values(136,'Martinique','MTQ');
insert into country(id,name,iso_code) values(137,'Mauritanie','MRT');
insert into country(id,name,iso_code) values(138,'Maurice','MUS');
insert into country(id,name,iso_code) values(139,'Mexique','MEX');
insert into country(id,name,iso_code) values(140,'Monaco','MCO');
insert into country(id,name,iso_code) values(141,'Mongolie','MNG');
insert into country(id,name,iso_code) values(142,'République de Moldova','MDA');
insert into country(id,name,iso_code) values(143,'Montserrat','MSR');
insert into country(id,name,iso_code) values(144,'Maroc','MAR');
insert into country(id,name,iso_code) values(145,'Mozambique','MOZ');
insert into country(id,name,iso_code) values(146,'Oman','OMN');
insert into country(id,name,iso_code) values(147,'Namibie','NAM');
insert into country(id,name,iso_code) values(148,'Nauru','NRU');
insert into country(id,name,iso_code) values(149,'Népal','NPL');
insert into country(id,name,iso_code) values(150,'Pays-Bas','NLD');
insert into country(id,name,iso_code) values(151,'Antilles Néerlandaises','ANT');
insert into country(id,name,iso_code) values(152,'Aruba','ABW');
insert into country(id,name,iso_code) values(153,'Nouvelle-Calédonie','NCL');
insert into country(id,name,iso_code) values(154,'Vanuatu','VUT');
insert into country(id,name,iso_code) values(155,'Nouvelle-Zélande','NZL');
insert into country(id,name,iso_code) values(156,'Nicaragua','NIC');
insert into country(id,name,iso_code) values(157,'Niger','NER');
insert into country(id,name,iso_code) values(158,'Nigéria','NGA');
insert into country(id,name,iso_code) values(159,'Niué','NIU');
insert into country(id,name,iso_code) values(160,'Île Norfolk','NFK');
insert into country(id,name,iso_code) values(161,'Norvège','NOR');
insert into country(id,name,iso_code) values(162,'Îles Mariannes du Nord','MNP');
insert into country(id,name,iso_code) values(163,'Îles Mineures Eloignées des Etats-Unis','UMI');
insert into country(id,name,iso_code) values(164,'Etats Fédérés de Micronésie','FSM');
insert into country(id,name,iso_code) values(165,'Îles Marshall','MHL');
insert into country(id,name,iso_code) values(166,'Palaos','PLW');
insert into country(id,name,iso_code) values(167,'Pakistan','PAK');
insert into country(id,name,iso_code) values(168,'Panama','PAN');
insert into country(id,name,iso_code) values(169,'Papouasie-Nouvelle-Guinée','PNG');
insert into country(id,name,iso_code) values(170,'Paraguay','PRY');
insert into country(id,name,iso_code) values(171,'Pérou','PER');
insert into country(id,name,iso_code) values(172,'Philippines','PHL');
insert into country(id,name,iso_code) values(173,'Pitcairn','PCN');
insert into country(id,name,iso_code) values(174,'Pologne','POL');
insert into country(id,name,iso_code) values(175,'Portugal','PRT');
insert into country(id,name,iso_code) values(176,'Guinée-Bissau','GNB');
insert into country(id,name,iso_code) values(177,'Timor-Leste','TLS');
insert into country(id,name,iso_code) values(178,'Porto Rico','PRI');
insert into country(id,name,iso_code) values(179,'Qatar','QAT');
insert into country(id,name,iso_code) values(180,'Réunion','REU');
insert into country(id,name,iso_code) values(181,'Roumanie','ROU');
insert into country(id,name,iso_code) values(182,'Fédération de Russie','RUS');
insert into country(id,name,iso_code) values(183,'Rwanda','RWA');
insert into country(id,name,iso_code) values(184,'Sainte-Hélène','SHN');
insert into country(id,name,iso_code) values(185,'Saint-Kitts-et-Nevis','KNA');
insert into country(id,name,iso_code) values(186,'Anguilla','AIA');
insert into country(id,name,iso_code) values(187,'Sainte-Lucie','LCA');
insert into country(id,name,iso_code) values(188,'Saint-Pierre-et-Miquelon','SPM');
insert into country(id,name,iso_code) values(189,'Saint-Vincent-et-les Grenadines','VCT');
insert into country(id,name,iso_code) values(190,'Saint-Marin','SMR');
insert into country(id,name,iso_code) values(191,'Sao Tomé-et-Principe','STP');
insert into country(id,name,iso_code) values(192,'Arabie Saoudite','SAU');
insert into country(id,name,iso_code) values(193,'Sénégal','SEN');
insert into country(id,name,iso_code) values(194,'Seychelles','SYC');
insert into country(id,name,iso_code) values(195,'Sierra Leone','SLE');
insert into country(id,name,iso_code) values(196,'Singapour','SGP');
insert into country(id,name,iso_code) values(197,'Slovaquie','SVK');
insert into country(id,name,iso_code) values(198,'Viet Nam','VNM');
insert into country(id,name,iso_code) values(199,'Slovénie','SVN');
insert into country(id,name,iso_code) values(200,'Somalie','SOM');
insert into country(id,name,iso_code) values(201,'Afrique du Sud','ZAF');
insert into country(id,name,iso_code) values(202,'Zimbabwe','ZWE');
insert into country(id,name,iso_code) values(203,'Espagne','ESP');
insert into country(id,name,iso_code) values(204,'Sahara Occidental','ESH');
insert into country(id,name,iso_code) values(205,'Soudan','SDN');
insert into country(id,name,iso_code) values(206,'Suriname','SUR');
insert into country(id,name,iso_code) values(207,'Svalbard etÎle Jan Mayen','SJM');
insert into country(id,name,iso_code) values(208,'Swaziland','SWZ');
insert into country(id,name,iso_code) values(209,'Suède','SWE');
insert into country(id,name,iso_code) values(210,'Suisse','CHE');
insert into country(id,name,iso_code) values(211,'République Arabe Syrienne','SYR');
insert into country(id,name,iso_code) values(212,'Tadjikistan','TJK');
insert into country(id,name,iso_code) values(213,'Thaïlande','THA');
insert into country(id,name,iso_code) values(214,'Togo','TGO');
insert into country(id,name,iso_code) values(215,'Tokelau','TKL');
insert into country(id,name,iso_code) values(216,'Tonga','TON');
insert into country(id,name,iso_code) values(217,'Trinité-et-Tobago','TTO');
insert into country(id,name,iso_code) values(218,'Emirats Arabes Unis','ARE');
insert into country(id,name,iso_code) values(219,'Tunisie','TUN');
insert into country(id,name,iso_code) values(220,'Turquie','TUR');
insert into country(id,name,iso_code) values(221,'Turkménistan','TKM');
insert into country(id,name,iso_code) values(222,'Îles Turks et Caïques','TCA');
insert into country(id,name,iso_code) values(223,'Tuvalu','TUV');
insert into country(id,name,iso_code) values(224,'Ouganda','UGA');
insert into country(id,name,iso_code) values(225,'Ukraine','UKR');
insert into country(id,name,iso_code) values(226,'L\'ex-République Yougoslave de Macédoine','MKD');
insert into country(id,name,iso_code) values(227,'Egypte','EGY');
insert into country(id,name,iso_code) values(228,'Royaume-Uni','GBR');
insert into country(id,name,iso_code) values(229,'Île de Man','IMN');
insert into country(id,name,iso_code) values(230,'République-Unie de Tanzanie','TZA');
insert into country(id,name,iso_code) values(231,'Etats-Unis','USA');
insert into country(id,name,iso_code) values(232,'Îles Vierges des Etats-Unis','VIR');
insert into country(id,name,iso_code) values(233,'Burkina Faso','BFA');
insert into country(id,name,iso_code) values(234,'Uruguay','URY');
insert into country(id,name,iso_code) values(235,'Ouzbékistan','UZB');
insert into country(id,name,iso_code) values(236,'Venezuela','VEN');
insert into country(id,name,iso_code) values(237,'Wallis et Futuna','WLF');
insert into country(id,name,iso_code) values(238,'Samoa','WSM');
insert into country(id,name,iso_code) values(239,'Yémen','YEM');
insert into country(id,name,iso_code) values(240,'Serbie-et-Monténégro','SCG');
insert into country(id,name,iso_code) values(241,'Zambie','ZMB');

--
-- Déchargement des données de la table `currency`
--
INSERT INTO `currency`(`id`, `name`, `symbol`) VALUES ('1','FRANC CFA','F CFA'),
('2','EURO','€'),
('3','DOLLAR','$'),
('4','YEN','¥'),
('5','YUAN','¥'),
('6','DINAR TUNISIEN','DT'),
('7','OUGUIYA','أوقية'),
('8','HRYVNIA','₴'),
('9','CEDI','₵'),
('10','NAIRA','N'),
('11','RAND','R'),
('12','ROUBLE RUSSE','₽'),
('13','LARI','₾'),
('14','WON','￦'),
('15','TUGRIK','₮'),
('16','RIYAL','riyal'),
('17','DIRHAM','DH'),
('18','LIVRE STERLING','£'),
('19','RUPIAH','ROUPIE'),
('20','KIP','₭'),
('21','RINGGIT','RM'),
('22','ROUPIE','Rs'),
('23','BAHT','฿'),
('24','DONG','đồng'),
('25','REAL BRESILIEN','R$');

--
-- Déchargement des données de la table `customer_family`
--
INSERT INTO `customer_family` (`id`, `name`, `is_active`) VALUES
(1, 'super dealer', 1),
(2, 'grossiste', 1),
(3, 'demi-grossiste', 1),
(4, 'Showroom', 1);

--
-- Déchargement des données de la table `driver`
--
INSERT INTO `driver` (`id`, `first_name`, `last_name`, `phone`, `is_active`) VALUES
(1, 'KASSI', 'HASSOUA', '', 1),
(2, 'DIALLO', 'ABOULAYE', '', 1),
(3, 'KY', 'SALIA', '', 1),
(4, 'DIALLO', 'YOUSSOUF', '', 1),
(5, 'KOUAKOU', 'KOUAME SERGE ALAIN', '', 1),
(6, 'TRAORE', 'KASSOUM', '', 1),
(7, 'LAGARTON', 'YEO', '', 1),
(8, 'SAKO', 'DJAKARIA', '', 1),
(9, 'X HAMADOU MOUSSA X', 'X KAMIA X', '', 1),
(10, 'BAMBA', 'PEKIGNAMI SEYDOU', '', 1),
(11, 'KONE', 'TIEKOURA', '', 1),
(14, 'BROU GBAGBA', 'FULGENCE', '', 1),
(15, 'NOM', ':', '', 1),
(16, 'TOKOU', 'BONI PATRICE', '', 1),
(18, 'TRAORE', 'IBRAHIM', '', 1),
(19, 'SIDIBE', 'MOUSSA', '', 0),
(20, 'DORE', 'KASSIM', '', 1);

--
-- Déchargement des données de la table `entry_type`
--
INSERT INTO `entry_type` (`id`, `name`, `is_active`, `observation`) VALUES
(1, 'Versement client', 1, ' Type a utiliser pour les versements clients');


--
-- Déchargement des données de la table `financial_charge`
--

INSERT INTO `financial_charge` (`id`, `name`) VALUES
(1, 'Electricité'),
(2, 'Loyer'),
(3, 'Salaire');


--
-- Déchargement des données de la table `freight_forwarder`
--
INSERT INTO `freight_forwarder` (`id`, `name`, `address`, `email`, `phone`, `fax`, `approval_number`, `is_active`) VALUES
(1, 'TGR', '01 BP 8093 ABIDJAN 01', '', '', '', '00114G', 1);


--
-- Déchargement des données de la table `invoice_tax`
--
INSERT INTO `invoice_tax` (`id`, `name`, `value`) VALUES
(1, 'tva', 18),
(2, 'airsi', 7.5);

--
-- Déchargement des données de la table `port`
--
INSERT INTO `port` (`id`, `name`, `is_active`) VALUES
(1, 'NINGBO', 1);

--
-- Déchargement des données de la table `sequence`
--

INSERT INTO `sequence` (`id`, `name`, `value`, `last_year`) VALUES
(1, 'asset', 0, 2021),
(2, 'entry', 0, 2021),
(3, 'estimate', 0, 2021),
(4, 'invoice', 0, 2021),
(5, 'sales_delivery', 0, 2021),
(6, 'transfer', 0, 2021),
(7, 'inventory', 0, 2021),
(8, 'inventoryCustomer', 0, 2021),
(9, 'customerOrder', 0, 2021),
(10, 'stock_output', 0, 2021),
(11, 'stock_entry', 0, 2021);


--
-- Déchargement des données de la table `shipper`
--
INSERT INTO `shipper` (`id`, `name`, `phone`, `address`, `email`, `interlocutor_name`, `interlocutor_phone`, `is_active`) VALUES (4, 'NINGO CHANGER ', '', '', '', '', '', 1);


--
-- Déchargement des données de la table `stock_output_reason`
--
INSERT INTO `stock_output_reason` (`id`, `name`, `is_active`) VALUES
(1, 'ERREUR DE SAISIE', 1);

--
-- Déchargement des données de la table `transport_vehicle`
--

INSERT INTO `transport_vehicle` (`id`, `driver_name`, `driver_contact`, `register_num`, `chassis_num`, `vehicle_color`, `vehicle_brand`, `car_registration_doc`, `comment`, `is_active`) VALUES
(1, NULL, NULL, '3029HC01', 'TYBFE659H6DS04826', 'BLANC', 'MITSUBISHI CANTER', 'CG30032237', NULL, 1),
(2, NULL, NULL, '5765GF01', 'TYPBB631E4DR00484', 'BLANC', 'MITSUBISHI CAMIONNET', 'CG456527/13', NULL, 1),
(3, NULL, NULL, 'HORS SERVICE 6289FT0', 'TYBFE659H6DN00018', 'BLANC', 'MITSUBISHI FE659', 'CG30016227', NULL, 1),
(4, NULL, NULL, '2407HG01', 'TYBFE85PH6DT01228', 'BLANC', 'MITSUBISHI CANTER', 'CG10144715', NULL, 1),
(5, NULL, NULL, '1309HN01', 'TYBFE85BJ6DU21117', 'BLANC', 'MITSUBICHI FUSO 7C15', 'CG10193451', NULL, 1),
(6, NULL, NULL, '2941HG01', 'XLRAS75PC0E759628', 'BLANC', 'DAF', 'CG10145314', NULL, 1),
(7, NULL, NULL, '4090HN01', 'TYBFE85PJ60T044379', 'Blanc', 'MITSUBISHI CANTER 7C', 'CG10196112', NULL, 1),
(8, NULL, NULL, 'HORS SERVICE 1840HN0', 'TYBFB631E4DN00325', 'BLANC', 'MITSUBISHI CANTER', 'CG10193764', NULL, 1),
(9, NULL, NULL, '5512GS01', '', 'ORANGE', 'SPRINTER ', 'CG30016226', NULL, 1),
(10, NULL, NULL, '1439HN01', 'WDB9026721R136941', 'BLANC', 'SPRINTER 208CDI', '', NULL, 1),
(11, NULL, NULL, '5889HP01', 'TYBF83BG4DT00370', 'BLANC', 'MITSUBISHI CANTER', 'CG10205245', NULL, 1),
(12, NULL, NULL, '2696HG01', 'TYBFE85BJ6DU17156', 'BLANC', 'MITSUBISHI / CANTER', 'CG10145090', NULL, 1),
(13, NULL, NULL, 'X', '', '', '', '', NULL, 1),
(14, NULL, NULL, '2294GY01', 'TYBFE659H6DS16089', 'BLANC', 'MITSUBISHI CANTER', 'CG30016517', NULL, 1),
(15, NULL, NULL, '4746HS01', 'TYBFB634E4DS24774', 'BLANC', 'MITSUBISHI CANTER', 'CG30044884', NULL, 1),
(16, NULL, NULL, 'IMM:', '', '', '', '', NULL, 1),
(17, NULL, NULL, '1553HZ01', 'TYBFB631E4DN00351', 'BLANC', 'MITSUBISHI CANTER ', 'CG30046989', NULL, 1),
(18, NULL, NULL, '7006JC01', 'WDB9700151K491670', 'BLANC', 'MERCEDES 815', 'CG24021556', NULL, 1),
(19, NULL, NULL, '8579HX01', 'XLRTE85XC0E557804', 'BLANC', 'DAF / TRACTEUR ROUTI', 'CG22065093', NULL, 1),
(20, NULL, NULL, '1348HU01', 'WDB94440331K817938', 'BLEU', 'TRACTEUR ROUTIER', 'CG22065091', NULL, 1),
(21, NULL, NULL, '2268JZ01', '', '', 'TRACTEUR ROUTIER DAF', 'CG10384136', NULL, 1);


