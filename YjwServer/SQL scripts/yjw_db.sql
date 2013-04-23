# MySQL-Front 5.1  (Build 4.13)

/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE */;
/*!40101 SET SQL_MODE='STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES */;
/*!40103 SET SQL_NOTES='ON' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS */;
/*!40014 SET FOREIGN_KEY_CHECKS=0 */;


# Host: 10.60.37.54    Database: yjw_db
# ------------------------------------------------------
# Server version 5.0.67-community-nt

DROP DATABASE IF EXISTS `yjw_db`;
CREATE DATABASE `yjw_db` /*!40100 DEFAULT CHARACTER SET utf8 */;
USE `yjw_db`;

#
# Source for table yjw_chat
#

DROP TABLE IF EXISTS `yjw_chat`;
CREATE TABLE `yjw_chat` (
  `chat_id` int(20) NOT NULL auto_increment,
  `deal_id` int(10) default NULL,
  `from_phone` varchar(50) NOT NULL default '0',
  `to_user` varchar(50) NOT NULL default '0',
  `content` varchar(200) NOT NULL default '',
  `is_read` enum('0','1') NOT NULL default '0',
  `sub_time` timestamp NOT NULL default CURRENT_TIMESTAMP,
  PRIMARY KEY  (`chat_id`)
) ENGINE=InnoDB AUTO_INCREMENT=61 DEFAULT CHARSET=utf8;

#
# Dumping data for table yjw_chat
#

LOCK TABLES `yjw_chat` WRITE;
/*!40000 ALTER TABLE `yjw_chat` DISABLE KEYS */;
INSERT INTO `yjw_chat` VALUES (18,15,'13818554170','13065511618','hi','1','2012-05-12 12:15:18');
INSERT INTO `yjw_chat` VALUES (19,15,'13065511618','','啊','0','2012-05-12 13:03:18');
INSERT INTO `yjw_chat` VALUES (20,15,'13065511618','13818554170','把','1','2012-05-12 13:04:39');
INSERT INTO `yjw_chat` VALUES (21,15,'13818554170','13065511618','hihi\n','1','2012-05-12 13:04:49');
INSERT INTO `yjw_chat` VALUES (22,16,'13818554170','18616833337','和他','1','2012-05-12 16:48:05');
INSERT INTO `yjw_chat` VALUES (23,17,'13817666149','18616833337','我们的，我们的','1','2012-05-12 16:59:38');
INSERT INTO `yjw_chat` VALUES (24,17,'18964632687','13817666149','那个性质的你','1','2012-05-12 17:01:57');
INSERT INTO `yjw_chat` VALUES (25,17,'18964632687','13817666149','不同意你们俩了。','1','2012-05-12 17:04:23');
INSERT INTO `yjw_chat` VALUES (26,17,'13817666149','18616833337','我们，不同样本次日前景气质量刑','1','2012-05-12 17:06:47');
INSERT INTO `yjw_chat` VALUES (27,16,'18616833337','13818554170','我们的，','1','2012-05-12 17:12:19');
INSERT INTO `yjw_chat` VALUES (28,16,'13818554170','18616833337','被扣','1','2012-05-12 17:12:32');
INSERT INTO `yjw_chat` VALUES (34,17,'13818554170','13818554170','吧','1','2012-08-17 23:28:37');
INSERT INTO `yjw_chat` VALUES (35,17,'13818554170','18616833337','o','1','2012-08-18 14:16:16');
INSERT INTO `yjw_chat` VALUES (36,55,'13818554170','13818554170','不','1','2012-08-20 13:58:55');
INSERT INTO `yjw_chat` VALUES (37,57,'13818554170','13818554170','啊','1','2012-08-20 14:00:21');
INSERT INTO `yjw_chat` VALUES (38,58,'13818554170','18616833337','123','1','2012-08-20 14:14:51');
INSERT INTO `yjw_chat` VALUES (39,57,'13818554170','13818554170','123','1','2012-08-20 14:17:20');
INSERT INTO `yjw_chat` VALUES (40,17,'13818554170','13818554170','123','1','2012-08-20 14:18:40');
INSERT INTO `yjw_chat` VALUES (41,59,'18616833337','13818554170','b','1','2012-08-20 14:30:36');
INSERT INTO `yjw_chat` VALUES (42,58,'18616833337','13818554170','123','1','2012-08-20 14:35:50');
INSERT INTO `yjw_chat` VALUES (43,58,'18616833337','13818554170','456','1','2012-08-20 14:36:03');
INSERT INTO `yjw_chat` VALUES (44,58,'13818554170','13818554170','11','1','2012-08-20 14:37:22');
INSERT INTO `yjw_chat` VALUES (45,58,'18616833337','13818554170','88','1','2012-08-20 14:37:43');
INSERT INTO `yjw_chat` VALUES (46,58,'18616833337','18616833337','123','1','2012-08-20 15:46:04');
INSERT INTO `yjw_chat` VALUES (47,58,'18616833337','13818554170','123','1','2012-08-20 15:46:24');
INSERT INTO `yjw_chat` VALUES (48,59,'13818554170','13818554170','1126','1','2012-08-20 15:48:56');
INSERT INTO `yjw_chat` VALUES (49,59,'18616833337','13818554170','123','1','2012-08-20 15:49:27');
INSERT INTO `yjw_chat` VALUES (50,59,'13818554170','18616833337','啊','1','2012-08-20 21:20:02');
INSERT INTO `yjw_chat` VALUES (51,59,'13818554170','18616833337','啊','1','2012-08-20 21:26:28');
INSERT INTO `yjw_chat` VALUES (52,59,'18616833337','13818554170','.ad','1','2012-08-20 21:26:57');
INSERT INTO `yjw_chat` VALUES (53,59,'13818554170','18616833337','呵呵','1','2012-08-21 16:35:24');
INSERT INTO `yjw_chat` VALUES (54,59,'18616833337','13818554170','123','1','2012-08-21 16:35:34');
INSERT INTO `yjw_chat` VALUES (55,58,'13818554170','18616833337','133','1','2012-08-21 16:36:43');
INSERT INTO `yjw_chat` VALUES (56,58,'18616833337','13818554170','134','1','2012-08-21 16:36:57');
INSERT INTO `yjw_chat` VALUES (57,58,'13818554170','18616833337','','0','2012-09-28 21:25:04');
INSERT INTO `yjw_chat` VALUES (58,21,'13818554170','13065511618','','0','2012-09-28 21:31:38');
INSERT INTO `yjw_chat` VALUES (59,61,'13818554170','15801952030','呵呵','1','2012-11-09 19:28:23');
INSERT INTO `yjw_chat` VALUES (60,61,'13818554170','15801952030','111','0','2012-11-30 16:14:18');
/*!40000 ALTER TABLE `yjw_chat` ENABLE KEYS */;
UNLOCK TABLES;

#
# Source for table yjw_deal
#

DROP TABLE IF EXISTS `yjw_deal`;
CREATE TABLE `yjw_deal` (
  `id` int(11) NOT NULL auto_increment,
  `user_id` varchar(255) default NULL,
  `title` varchar(255) default NULL,
  `content` text,
  `fee` float(10,2) default NULL,
  `commission` float(10,2) default NULL,
  `timestamp` timestamp NULL default CURRENT_TIMESTAMP on update CURRENT_TIMESTAMP,
  `valid_period` datetime default NULL,
  `status` enum('Close','Success','Talk') NOT NULL default 'Talk',
  `expire_date` date default NULL,
  `req_confirm` enum('Yes','No') NOT NULL default 'No',
  PRIMARY KEY  (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=63 DEFAULT CHARSET=utf8;

#
# Dumping data for table yjw_deal
#

LOCK TABLES `yjw_deal` WRITE;
/*!40000 ALTER TABLE `yjw_deal` DISABLE KEYS */;
INSERT INTO `yjw_deal` VALUES (15,'12','嘉定','',2,800,'2012-05-12 12:15:06',NULL,'Talk',NULL,'No');
INSERT INTO `yjw_deal` VALUES (16,'13','我们，','',10,1500,'2012-05-12 16:47:35',NULL,'Talk',NULL,'No');
INSERT INTO `yjw_deal` VALUES (17,'13','186到138','',2,200,'2012-05-12 16:58:51',NULL,'Talk',NULL,'Yes');
INSERT INTO `yjw_deal` VALUES (18,'14','138到186','',11,11111,'2012-05-22 20:44:07',NULL,'Talk',NULL,'No');
INSERT INTO `yjw_deal` VALUES (19,'11','这是什么','这是谁',111,11,'2012-05-28 21:30:49',NULL,'Talk',NULL,'No');
INSERT INTO `yjw_deal` VALUES (20,'11','这是什么','这是谁',111,11,'2012-05-28 21:31:31',NULL,'Talk',NULL,'No');
INSERT INTO `yjw_deal` VALUES (21,'11','需要确认的生意','',2,800,'2012-05-29 21:12:25',NULL,'Talk',NULL,'Yes');
INSERT INTO `yjw_deal` VALUES (22,'11','这是什么','这是谁',111,111,'2012-05-29 21:34:02',NULL,'Talk',NULL,'Yes');
INSERT INTO `yjw_deal` VALUES (23,'14','138到186转189','',3,3333,'2012-06-03 09:08:09',NULL,'Talk',NULL,'No');
INSERT INTO `yjw_deal` VALUES (25,'12','才打三分','iiiii',1,32,'2012-08-18 15:21:12',NULL,'Talk',NULL,'Yes');
INSERT INTO `yjw_deal` VALUES (26,'11','才打三分','iiiii',1,32,'2012-08-18 15:21:12',NULL,'Talk',NULL,'Yes');
INSERT INTO `yjw_deal` VALUES (55,'12','aa','123\n',2,100,'2012-08-18 15:23:34',NULL,'Talk',NULL,'Yes');
INSERT INTO `yjw_deal` VALUES (56,'11','11','',11,0,'2012-08-20 20:00:00',NULL,'Talk',NULL,'No');
INSERT INTO `yjw_deal` VALUES (57,'11','info ','',100,0,'2012-08-18 14:13:30',NULL,'Talk',NULL,'Yes');
INSERT INTO `yjw_deal` VALUES (58,'13','138186','',20,0,'2012-08-20 14:14:05',NULL,'Talk',NULL,'Yes');
INSERT INTO `yjw_deal` VALUES (59,'11','bbb12','',20,0,'2012-08-20 14:29:44',NULL,'Talk',NULL,'Yes');
INSERT INTO `yjw_deal` VALUES (61,'11','特区了\n','',556,0,'2012-11-09 19:27:57',NULL,'Talk',NULL,'Yes');
INSERT INTO `yjw_deal` VALUES (62,'11','1','',1,0,'2012-11-30 16:10:45',NULL,'Talk',NULL,'Yes');
/*!40000 ALTER TABLE `yjw_deal` ENABLE KEYS */;
UNLOCK TABLES;

#
# Source for table yjw_deal_frw
#

DROP TABLE IF EXISTS `yjw_deal_frw`;
CREATE TABLE `yjw_deal_frw` (
  `deal_id` int(10) NOT NULL default '0',
  `for_user_id` int(10) default NULL,
  `phone_number` varchar(50) default '0',
  `times` datetime NOT NULL default '0000-00-00 00:00:00'
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

#
# Dumping data for table yjw_deal_frw
#

LOCK TABLES `yjw_deal_frw` WRITE;
/*!40000 ALTER TABLE `yjw_deal_frw` DISABLE KEYS */;
/*!40000 ALTER TABLE `yjw_deal_frw` ENABLE KEYS */;
UNLOCK TABLES;

#
# Source for table yjw_deal_intr
#

DROP TABLE IF EXISTS `yjw_deal_intr`;
CREATE TABLE `yjw_deal_intr` (
  `user_id` int(10) NOT NULL default '0',
  `deal_id` int(10) NOT NULL default '0',
  `phone_number` varchar(50) NOT NULL default '',
  `times` timestamp NULL default CURRENT_TIMESTAMP on update CURRENT_TIMESTAMP,
  `type` enum('FWD','NEW') default NULL,
  `rec_status` enum('new','accept','decline','none') NOT NULL default 'new'
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

#
# Dumping data for table yjw_deal_intr
#

LOCK TABLES `yjw_deal_intr` WRITE;
/*!40000 ALTER TABLE `yjw_deal_intr` DISABLE KEYS */;
INSERT INTO `yjw_deal_intr` VALUES (12,15,'13818554170','2012-05-12 12:15:06','NEW','accept');
INSERT INTO `yjw_deal_intr` VALUES (13,16,'13818554170','2012-05-12 16:47:35','NEW','accept');
INSERT INTO `yjw_deal_intr` VALUES (13,17,'13818554170','2012-05-12 16:58:51','NEW','accept');
INSERT INTO `yjw_deal_intr` VALUES (13,17,'13817666149','2012-05-12 16:58:51','NEW','accept');
INSERT INTO `yjw_deal_intr` VALUES (14,17,'18964632687','2012-05-12 16:59:59','FWD','new');
INSERT INTO `yjw_deal_intr` VALUES (11,17,'13916277423','2012-05-20 10:01:41','FWD','new');
INSERT INTO `yjw_deal_intr` VALUES (14,17,'18964632687','2012-05-20 14:43:30','FWD','new');
INSERT INTO `yjw_deal_intr` VALUES (14,18,'18616833337','2012-05-22 20:44:08','NEW','new');
INSERT INTO `yjw_deal_intr` VALUES (11,19,'13917774194','2012-05-28 21:30:58','NEW','new');
INSERT INTO `yjw_deal_intr` VALUES (11,19,'1293312','2012-05-28 21:30:58','NEW','new');
INSERT INTO `yjw_deal_intr` VALUES (11,19,'321312','2012-05-28 21:30:58','NEW','new');
INSERT INTO `yjw_deal_intr` VALUES (11,20,'13917774194','2012-05-28 21:31:40','NEW','none');
INSERT INTO `yjw_deal_intr` VALUES (11,20,'1293312','2012-05-28 21:31:40','NEW','none');
INSERT INTO `yjw_deal_intr` VALUES (11,20,'321312','2012-05-28 21:31:40','NEW','none');
INSERT INTO `yjw_deal_intr` VALUES (11,21,'13065511618','2012-05-29 21:12:25','NEW','new');
INSERT INTO `yjw_deal_intr` VALUES (11,22,'13917774194','2012-05-29 21:34:02','NEW','new');
INSERT INTO `yjw_deal_intr` VALUES (12,21,'13917774194','2012-05-29 21:59:57','FWD','new');
INSERT INTO `yjw_deal_intr` VALUES (12,21,'13656850567','2012-05-29 22:01:43','FWD','accept');
INSERT INTO `yjw_deal_intr` VALUES (14,23,'18616833337','2012-06-03 09:08:09','NEW','none');
INSERT INTO `yjw_deal_intr` VALUES (13,23,'18964632687','2012-06-03 09:08:43','FWD','new');
INSERT INTO `yjw_deal_intr` VALUES (12,25,'13818554170','2012-08-18 15:45:59','NEW','accept');
INSERT INTO `yjw_deal_intr` VALUES (12,25,'13065511618',NULL,'NEW','new');
INSERT INTO `yjw_deal_intr` VALUES (11,26,'13818554170','2012-08-18 15:45:51','NEW','accept');
INSERT INTO `yjw_deal_intr` VALUES (11,26,'13065511618',NULL,'NEW','new');
INSERT INTO `yjw_deal_intr` VALUES (11,27,'13818554107',NULL,'NEW','new');
INSERT INTO `yjw_deal_intr` VALUES (11,28,'13818554107',NULL,'NEW','new');
INSERT INTO `yjw_deal_intr` VALUES (11,28,'18801930296',NULL,'NEW','new');
INSERT INTO `yjw_deal_intr` VALUES (11,29,'13818554107',NULL,'NEW','new');
INSERT INTO `yjw_deal_intr` VALUES (11,30,'18801930296',NULL,'NEW','new');
INSERT INTO `yjw_deal_intr` VALUES (11,31,'18801930296',NULL,'NEW','new');
INSERT INTO `yjw_deal_intr` VALUES (11,32,'13887239277',NULL,'NEW','new');
INSERT INTO `yjw_deal_intr` VALUES (11,32,'18801930296',NULL,'NEW','new');
INSERT INTO `yjw_deal_intr` VALUES (11,33,'16765651234',NULL,'NEW','new');
INSERT INTO `yjw_deal_intr` VALUES (11,33,'18916564758',NULL,'NEW','new');
INSERT INTO `yjw_deal_intr` VALUES (11,34,'1880193029',NULL,'NEW','new');
INSERT INTO `yjw_deal_intr` VALUES (11,35,'18801930296',NULL,'NEW','new');
INSERT INTO `yjw_deal_intr` VALUES (11,36,'16765651234',NULL,'NEW','new');
INSERT INTO `yjw_deal_intr` VALUES (11,36,'18916564758',NULL,'NEW','new');
INSERT INTO `yjw_deal_intr` VALUES (11,37,'13818554172',NULL,'NEW','new');
INSERT INTO `yjw_deal_intr` VALUES (11,38,'18801930296',NULL,'NEW','new');
INSERT INTO `yjw_deal_intr` VALUES (11,39,'12344567',NULL,'NEW','new');
INSERT INTO `yjw_deal_intr` VALUES (11,40,'18801930296',NULL,'NEW','new');
INSERT INTO `yjw_deal_intr` VALUES (11,41,'18801930296',NULL,'NEW','new');
INSERT INTO `yjw_deal_intr` VALUES (11,42,'18801930296',NULL,'NEW','new');
INSERT INTO `yjw_deal_intr` VALUES (11,43,'18801930296',NULL,'NEW','new');
INSERT INTO `yjw_deal_intr` VALUES (11,44,'188109219',NULL,'NEW','new');
INSERT INTO `yjw_deal_intr` VALUES (11,44,'120930',NULL,'NEW','new');
INSERT INTO `yjw_deal_intr` VALUES (11,45,'123',NULL,'NEW','new');
INSERT INTO `yjw_deal_intr` VALUES (11,46,'123',NULL,'NEW','new');
INSERT INTO `yjw_deal_intr` VALUES (11,47,'21333',NULL,'NEW','new');
INSERT INTO `yjw_deal_intr` VALUES (11,48,'13818554172',NULL,'NEW','new');
INSERT INTO `yjw_deal_intr` VALUES (11,49,'13818554172',NULL,'NEW','new');
INSERT INTO `yjw_deal_intr` VALUES (11,50,'133',NULL,'NEW','new');
INSERT INTO `yjw_deal_intr` VALUES (11,50,'123',NULL,'NEW','new');
INSERT INTO `yjw_deal_intr` VALUES (11,51,'123',NULL,'NEW','new');
INSERT INTO `yjw_deal_intr` VALUES (11,51,'12344',NULL,'NEW','new');
INSERT INTO `yjw_deal_intr` VALUES (11,52,'213',NULL,'NEW','new');
INSERT INTO `yjw_deal_intr` VALUES (11,52,'333',NULL,'NEW','new');
INSERT INTO `yjw_deal_intr` VALUES (11,53,'13818554172',NULL,'NEW','new');
INSERT INTO `yjw_deal_intr` VALUES (11,55,'13818554170','2012-08-18 15:21:12','NEW','accept');
INSERT INTO `yjw_deal_intr` VALUES (11,56,'18616833337','2012-08-18 14:32:16','NEW','new');
INSERT INTO `yjw_deal_intr` VALUES (11,57,'13818554170','2012-08-18 15:46:47','NEW','accept');
INSERT INTO `yjw_deal_intr` VALUES (13,58,'13818554170','2012-08-20 14:14:43','NEW','accept');
INSERT INTO `yjw_deal_intr` VALUES (11,59,'18616833337','2012-08-20 14:30:19','NEW','accept');
INSERT INTO `yjw_deal_intr` VALUES (11,58,'18616833337','2012-08-20 14:35:40','FWD','accept');
INSERT INTO `yjw_deal_intr` VALUES (55,60,'18616833337','2012-10-16 14:26:46','NEW','new');
INSERT INTO `yjw_deal_intr` VALUES (11,61,'15801952030','2012-11-09 19:29:05','NEW','accept');
INSERT INTO `yjw_deal_intr` VALUES (60,61,'18616833337','2012-11-09 19:29:59','FWD','new');
INSERT INTO `yjw_deal_intr` VALUES (11,62,'18616833337','2012-11-30 16:10:45','NEW','new');
/*!40000 ALTER TABLE `yjw_deal_intr` ENABLE KEYS */;
UNLOCK TABLES;

#
# Source for table yjw_deal_rec
#

DROP TABLE IF EXISTS `yjw_deal_rec`;
CREATE TABLE `yjw_deal_rec` (
  `deal_id` int(11) NOT NULL default '0',
  `cellphone` varchar(50) default NULL,
  `send_timestamp` datetime default NULL,
  `rec_user_id` int(11) default NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

#
# Dumping data for table yjw_deal_rec
#

LOCK TABLES `yjw_deal_rec` WRITE;
/*!40000 ALTER TABLE `yjw_deal_rec` DISABLE KEYS */;
/*!40000 ALTER TABLE `yjw_deal_rec` ENABLE KEYS */;
UNLOCK TABLES;

#
# Source for table yjw_user
#

DROP TABLE IF EXISTS `yjw_user`;
CREATE TABLE `yjw_user` (
  `id` int(11) NOT NULL auto_increment,
  `name` varchar(50) default NULL,
  `cellphone` varchar(50) NOT NULL default '',
  `password` varchar(50) default NULL,
  `timestamps` timestamp NULL default CURRENT_TIMESTAMP,
  `email` varchar(50) default 'null',
  `sid` varchar(50) default NULL,
  `isActive` smallint(1) default '0',
  PRIMARY KEY  (`id`),
  UNIQUE KEY `cellphone` (`cellphone`)
) ENGINE=InnoDB AUTO_INCREMENT=66 DEFAULT CHARSET=utf8;

#
# Dumping data for table yjw_user
#

LOCK TABLES `yjw_user` WRITE;
/*!40000 ALTER TABLE `yjw_user` DISABLE KEYS */;
INSERT INTO `yjw_user` VALUES (11,'陶','13818554170','1234','2012-04-14 20:37:16','null','4313344070361043716668',1);
INSERT INTO `yjw_user` VALUES (12,'小小','13065511618','1234','2012-04-14 20:37:37','null','913344070577763737212',1);
INSERT INTO `yjw_user` VALUES (13,'半人马','18616833337','1234','2012-04-15 16:46:43','null','9713344796034794643423',1);
INSERT INTO `yjw_user` VALUES (14,'沙王','13817666149','1234','2012-04-15 17:01:04','null','95133448046422914114',1);
INSERT INTO `yjw_user` VALUES (15,'詹姆斯','13671864361','1234','2012-04-22 10:58:38','null','9413350635182485838439',1);
INSERT INTO `yjw_user` VALUES (17,'null','18964632687','1234','2012-05-05 10:03:36','null','581336183416424336531',1);
INSERT INTO `yjw_user` VALUES (18,'null','13656850567','1234','2012-05-29 22:01:03','null','7133830006387313204',1);
INSERT INTO `yjw_user` VALUES (21,'11','13917773631','12345','2012-06-06 23:26:55','null','981338996425957275565',1);
INSERT INTO `yjw_user` VALUES (22,'kuxiao','13917774195','1234','2012-06-08 22:24:45',NULL,'6413391654847152444782',1);
INSERT INTO `yjw_user` VALUES (32,'小林','18717863529','123456','2012-06-09 22:00:10',NULL,'921339250410972010813',1);
INSERT INTO `yjw_user` VALUES (33,'陶亦恒','13818554179','123456','2012-06-10 17:21:14',NULL,'2013393200749882114162',1);
INSERT INTO `yjw_user` VALUES (36,'qwe','18801930296','1234','2012-06-10 17:21:14',NULL,'6313423436342551354977',1);
INSERT INTO `yjw_user` VALUES (37,NULL,'12345678912',NULL,NULL,NULL,'271342364034099535485',0);
INSERT INTO `yjw_user` VALUES (39,NULL,'00225',NULL,NULL,NULL,'1013500306795873119709',0);
INSERT INTO `yjw_user` VALUES (59,'殷','13122042627','1234','2012-10-18 23:19:55','null','7813505735957851955935',1);
INSERT INTO `yjw_user` VALUES (60,'马龙','15801952030','1234','2012-11-09 19:22:46','null','6113524601667382246694',1);
INSERT INTO `yjw_user` VALUES (61,NULL,'123',NULL,'2012-11-29 11:01:41','null','271354158101000141234',0);
INSERT INTO `yjw_user` VALUES (62,NULL,'555',NULL,'2012-11-29 11:04:28','null','341354158268406428517',0);
INSERT INTO `yjw_user` VALUES (63,NULL,'777',NULL,'2012-11-29 11:36:19','null','6413541601797653619481',0);
INSERT INTO `yjw_user` VALUES (64,'latest ','5555','1234','2012-11-29 12:48:07','null','381354164487468487398',1);
INSERT INTO `yjw_user` VALUES (65,'c','66666','1234','2012-11-29 12:59:54','null','581354165194375595459',1);
/*!40000 ALTER TABLE `yjw_user` ENABLE KEYS */;
UNLOCK TABLES;

#
# Source for table yjw_validation
#

DROP TABLE IF EXISTS `yjw_validation`;
CREATE TABLE `yjw_validation` (
  `sid` varchar(50) NOT NULL default '0',
  `validation_code` varchar(100) default NULL,
  `timestamps` timestamp NULL default CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

#
# Dumping data for table yjw_validation
#

LOCK TABLES `yjw_validation` WRITE;
/*!40000 ALTER TABLE `yjw_validation` DISABLE KEYS */;
INSERT INTO `yjw_validation` VALUES ('5913392072722691129','716778','2012-06-09 10:01:12');
INSERT INTO `yjw_validation` VALUES ('01339207470894430934','894651','2012-06-09 10:04:30');
INSERT INTO `yjw_validation` VALUES ('951339207552066552339','897365','2012-06-09 10:05:52');
INSERT INTO `yjw_validation` VALUES ('8813392079608471240223','614843','2012-06-09 10:12:40');
INSERT INTO `yjw_validation` VALUES ('7413408914807405120539','771115','2012-06-28 21:51:36');
INSERT INTO `yjw_validation` VALUES ('851341339891711245178','785326',NULL);
INSERT INTO `yjw_validation` VALUES ('6813414817560554916945','812284',NULL);
INSERT INTO `yjw_validation` VALUES ('2313414822551335735379','958151',NULL);
INSERT INTO `yjw_validation` VALUES ('2813423431019075189','735174',NULL);
INSERT INTO `yjw_validation` VALUES ('51342343449360104941','159819',NULL);
INSERT INTO `yjw_validation` VALUES ('6313423436342551354977','297975',NULL);
INSERT INTO `yjw_validation` VALUES ('3813500563079783827482','371484',NULL);
INSERT INTO `yjw_validation` VALUES ('271354158101000141234','528269',NULL);
INSERT INTO `yjw_validation` VALUES ('341354158268406428517','657598',NULL);
INSERT INTO `yjw_validation` VALUES ('6413541601797653619481','165487',NULL);
/*!40000 ALTER TABLE `yjw_validation` ENABLE KEYS */;
UNLOCK TABLES;

/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
