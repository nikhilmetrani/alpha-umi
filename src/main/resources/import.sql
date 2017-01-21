USE usmdevdb;
DROP FUNCTION IF EXISTS fn_regexp_like;
CREATE FUNCTION fn_regexp_like (value1 nvarchar(4000),value2 nvarchar(4000)) RETURNS CHAR(1) DETERMINISTIC RETURN value1 REGEXP value2;

INSERT INTO AUTHORITY (ID, NAME) VALUES (1, 'ROLE_CONSUMER');
INSERT INTO AUTHORITY (ID, NAME) VALUES (2, 'ROLE_DEVELOPER');
INSERT INTO AUTHORITY (ID, NAME) VALUES (3, 'ROLE_ADMIN');
INSERT INTO AUTHORITY (ID, NAME) VALUES (4, 'ROLE_MANAGER');
INSERT INTO AUTHORITY (ID, NAME) VALUES (5, 'ROLE_MAINTAINER');

INSERT INTO Category (id, name) VALUES (1, 'Productivity');
INSERT INTO Category (id, name) VALUES (2, 'Photography');
INSERT INTO Category (id, name) VALUES (3, 'Entertainment');
INSERT INTO Category (id, name) VALUES (4, 'Education');
INSERT INTO Category (id, name) VALUES (5, 'Finance');
INSERT INTO Category (id, name) VALUES (6, 'Shopping');
INSERT INTO Category (id, name) VALUES (7, 'Social Networking');
INSERT INTO Category (id, name) VALUES (8, 'Utilities');

-- To create 5 developers
INSERT INTO `user` (`ID`,`EMAIL`,`ENABLED`,`FIRSTNAME`,`LASTPASSWORDRESETDATE`,`LASTNAME`,`PASSWORD`,`USERNAME`) VALUES (101,'developer1@usm.com',1,NULL,'2017-01-08 03:59:23',NULL,'$2a$10$AMt7mj5oYaIThHB0fL4v6.D4btzzy5jMRaLQaDRigmzWSqRuu/NoS','developer1@usm.com');
INSERT INTO `user` (`ID`,`EMAIL`,`ENABLED`,`FIRSTNAME`,`LASTPASSWORDRESETDATE`,`LASTNAME`,`PASSWORD`,`USERNAME`) VALUES (102,'developer2@usm.com',1,NULL,'2017-01-08 03:59:59',NULL,'$2a$10$2W3rT5Q6Mmfbi0lQc5DDVeppusmbb4Tut1RbScsMPDFvmdvgTXXla','developer2@usm.com');
INSERT INTO `user` (`ID`,`EMAIL`,`ENABLED`,`FIRSTNAME`,`LASTPASSWORDRESETDATE`,`LASTNAME`,`PASSWORD`,`USERNAME`) VALUES (103,'developer3@usm.com',1,NULL,'2017-01-08 04:00:10',NULL,'$2a$10$NBfbFW1sM7woPLtahK7ajOhOdTA3jciR3T/U6HAbFqVtFTrcDSG7u','developer3@usm.com');
INSERT INTO `user` (`ID`,`EMAIL`,`ENABLED`,`FIRSTNAME`,`LASTPASSWORDRESETDATE`,`LASTNAME`,`PASSWORD`,`USERNAME`) VALUES (104,'developer4@usm.com',1,NULL,'2017-01-08 04:00:22',NULL,'$2a$10$FVjS9nOUPHnXDC3WuDBMJ.kwxLIIFDjWsVqp.22XfGPkO1AcJCmG6','developer4@usm.com');
INSERT INTO `user` (`ID`,`EMAIL`,`ENABLED`,`FIRSTNAME`,`LASTPASSWORDRESETDATE`,`LASTNAME`,`PASSWORD`,`USERNAME`) VALUES (105,'developer5@usm.com',1,NULL,'2017-01-08 04:00:35',NULL,'$2a$10$vke5bYyTlun3VM5pPX.LLeyC9iMTdqGeETRjkTMVug.yiXaTqqvfS','developer5@usm.com');

-- To assign both "Developer" and "Consumer" authority to all 5 developers
INSERT INTO `user_authority` (`USER_ID`,`AUTHORITY_ID`) VALUES (101,1);
INSERT INTO `user_authority` (`USER_ID`,`AUTHORITY_ID`) VALUES (101,2);
INSERT INTO `user_authority` (`USER_ID`,`AUTHORITY_ID`) VALUES (102,1);
INSERT INTO `user_authority` (`USER_ID`,`AUTHORITY_ID`) VALUES (102,2);
INSERT INTO `user_authority` (`USER_ID`,`AUTHORITY_ID`) VALUES (103,1);
INSERT INTO `user_authority` (`USER_ID`,`AUTHORITY_ID`) VALUES (103,2);
INSERT INTO `user_authority` (`USER_ID`,`AUTHORITY_ID`) VALUES (104,1);
INSERT INTO `user_authority` (`USER_ID`,`AUTHORITY_ID`) VALUES (104,2);
INSERT INTO `user_authority` (`USER_ID`,`AUTHORITY_ID`) VALUES (105,1);
INSERT INTO `user_authority` (`USER_ID`,`AUTHORITY_ID`) VALUES (105,2);

-- To create 5 consumers
INSERT INTO `user` (`ID`,`EMAIL`,`ENABLED`,`FIRSTNAME`,`LASTPASSWORDRESETDATE`,`LASTNAME`,`PASSWORD`,`USERNAME`) VALUES (106,'consumer1@usm.com',1,NULL,'2017-01-08 04:30:24',NULL,'$2a$10$zEiACAqp7mJlQMxX0g1Q3eLxZEv6nxFN8t6OVTupLFR/oIrkfVGPC','consumer1@usm.com');
INSERT INTO `user` (`ID`,`EMAIL`,`ENABLED`,`FIRSTNAME`,`LASTPASSWORDRESETDATE`,`LASTNAME`,`PASSWORD`,`USERNAME`) VALUES (107,'consumer2@usm.com',1,NULL,'2017-01-08 04:30:36',NULL,'$2a$10$1Rv2jAZbkbBKKKz9vPMO/uUSDnLIgEihQSxa7nS5IrvtEvALPPmay','consumer2@usm.com');
INSERT INTO `user` (`ID`,`EMAIL`,`ENABLED`,`FIRSTNAME`,`LASTPASSWORDRESETDATE`,`LASTNAME`,`PASSWORD`,`USERNAME`) VALUES (108,'consumer3@usm.com',1,NULL,'2017-01-08 04:30:50',NULL,'$2a$10$TTQqNH1jzWncyfjxcxjHxOYWdxg7YHkyfaHYIvevCWytkVd2AGQZC','consumer3@usm.com');
INSERT INTO `user` (`ID`,`EMAIL`,`ENABLED`,`FIRSTNAME`,`LASTPASSWORDRESETDATE`,`LASTNAME`,`PASSWORD`,`USERNAME`) VALUES (109,'consumer4@usm.com',1,NULL,'2017-01-08 04:31:01',NULL,'$2a$10$pjHft3Vr1f9AT9nz70mWpeKmCrqH3pKn3csFPfbzrR/FfOdaCmrPy','consumer4@usm.com');
INSERT INTO `user` (`ID`,`EMAIL`,`ENABLED`,`FIRSTNAME`,`LASTPASSWORDRESETDATE`,`LASTNAME`,`PASSWORD`,`USERNAME`) VALUES (110,'consumer5@usm.com',1,NULL,'2017-01-08 04:31:13',NULL,'$2a$10$iT6WpHrmLM2mBXDvmXW.K.rFHdtly65Tc5f70V2hqh4QdmLlzOs0K','consumer5@usm.com');

-- To assign "Consumer" authority to the 5 consumers
INSERT INTO `user_authority` (`USER_ID`,`AUTHORITY_ID`) VALUES (101,1);
INSERT INTO `user_authority` (`USER_ID`,`AUTHORITY_ID`) VALUES (102,1);
INSERT INTO `user_authority` (`USER_ID`,`AUTHORITY_ID`) VALUES (103,1);
INSERT INTO `user_authority` (`USER_ID`,`AUTHORITY_ID`) VALUES (104,1);
INSERT INTO `user_authority` (`USER_ID`,`AUTHORITY_ID`) VALUES (105,1);

-- To create 10 applications
-- Category: 1 = Uncategorized, Category 2 = Productivity
-- State: O = Staging, 1 = Published, 2 = Recalled
INSERT INTO `application` (`id`,`description`,`name`,`version`,`whatsNew`,`applicationpublishdate`,`state`,`category_id`,`developer_ID`) VALUES ('1','Dev 1 App 1','Dev1App1','1.0','nothing',NULL,1,1,101);
INSERT INTO `application` (`id`,`description`,`name`,`version`,`whatsNew`,`applicationpublishdate`,`state`,`category_id`,`developer_ID`) VALUES ('2','Dev 1 App 2','Dev1App2','1.0','nothing',NULL,1,2,101);
INSERT INTO `application` (`id`,`description`,`name`,`version`,`whatsNew`,`applicationpublishdate`,`state`,`category_id`,`developer_ID`) VALUES ('3','Dev 1 App 3','Dev1App3','1.0','nothing',NULL,1,3,101);
INSERT INTO `application` (`id`,`description`,`name`,`version`,`whatsNew`,`applicationpublishdate`,`state`,`category_id`,`developer_ID`) VALUES ('4','Dev 2 App 1','Dev2App1','1.0','nothing',NULL,1,4,102);
INSERT INTO `application` (`id`,`description`,`name`,`version`,`whatsNew`,`applicationpublishdate`,`state`,`category_id`,`developer_ID`) VALUES ('5','Dev 3 App 1','Dev3App1','1.0','nothing',NULL,1,5,103);
INSERT INTO `application` (`id`,`description`,`name`,`version`,`whatsNew`,`applicationpublishdate`,`state`,`category_id`,`developer_ID`) VALUES ('6','Dev 3 App 2','Dev3App2','1.0','nothing',NULL,1,6,103);
INSERT INTO `application` (`id`,`description`,`name`,`version`,`whatsNew`,`applicationpublishdate`,`state`,`category_id`,`developer_ID`) VALUES ('7','Dev 4 App 1','Dev4App1','1.0','nothing',NULL,1,7,104);
INSERT INTO `application` (`id`,`description`,`name`,`version`,`whatsNew`,`applicationpublishdate`,`state`,`category_id`,`developer_ID`) VALUES ('8','Dev 4 App 2','Dev4App2','1.0','nothing',NULL,1,8,104);
INSERT INTO `application` (`id`,`description`,`name`,`version`,`whatsNew`,`applicationpublishdate`,`state`,`category_id`,`developer_ID`) VALUES ('9','Dev 4 App 3','Dev4App3','1.0','nothing',NULL,1,1,104);
INSERT INTO `application` (`id`,`description`,`name`,`version`,`whatsNew`,`applicationpublishdate`,`state`,`category_id`,`developer_ID`) VALUES ('10','Dev 5 App 1','Dev5App1','1.0','nothing',NULL,1,2,105);


--To create 5 Active Featured applications
--And, create 4 Inactive Featured applications
INSERT INTO `featuredapplication` (`id`,`createBy`,`creationDate`,`lastUpdateBy`,`lastUpdateDate`,`featureAppState`,`unFeatureDate`,`application_id`) VALUES (101,'maintainer','2017-01-14 08:04:28','maintainer','2017-01-14 08:04:28',0,NULL,'1');
INSERT INTO `featuredapplication` (`id`,`createBy`,`creationDate`,`lastUpdateBy`,`lastUpdateDate`,`featureAppState`,`unFeatureDate`,`application_id`) VALUES (102,'maintainer','2017-01-14 08:04:29','maintainer','2017-01-14 08:04:29',0,NULL,'2');
INSERT INTO `featuredapplication` (`id`,`createBy`,`creationDate`,`lastUpdateBy`,`lastUpdateDate`,`featureAppState`,`unFeatureDate`,`application_id`) VALUES (103,'maintainer','2017-01-14 08:04:29','maintainer','2017-01-14 08:04:29',0,NULL,'3');
INSERT INTO `featuredapplication` (`id`,`createBy`,`creationDate`,`lastUpdateBy`,`lastUpdateDate`,`featureAppState`,`unFeatureDate`,`application_id`) VALUES (104,'maintainer','2017-01-14 08:04:29','maintainer','2017-01-14 08:04:29',0,NULL,'4');
INSERT INTO `featuredapplication` (`id`,`createBy`,`creationDate`,`lastUpdateBy`,`lastUpdateDate`,`featureAppState`,`unFeatureDate`,`application_id`) VALUES (105,'maintainer','2017-01-14 08:04:29','maintainer','2017-01-14 08:04:29',0,NULL,'5');
INSERT INTO `featuredapplication` (`id`,`createBy`,`creationDate`,`lastUpdateBy`,`lastUpdateDate`,`featureAppState`,`unFeatureDate`,`application_id`) VALUES (106,'maintainer','2017-01-14 08:04:28','maintainer','2017-01-14 08:04:28',1,NULL,'6');
INSERT INTO `featuredapplication` (`id`,`createBy`,`creationDate`,`lastUpdateBy`,`lastUpdateDate`,`featureAppState`,`unFeatureDate`,`application_id`) VALUES (107,'maintainer','2017-01-14 08:04:29','maintainer','2017-01-14 08:04:29',1,NULL,'7');
INSERT INTO `featuredapplication` (`id`,`createBy`,`creationDate`,`lastUpdateBy`,`lastUpdateDate`,`featureAppState`,`unFeatureDate`,`application_id`) VALUES (108,'maintainer','2017-01-14 08:04:29','maintainer','2017-01-14 08:04:29',1,NULL,'8');
INSERT INTO `featuredapplication` (`id`,`createBy`,`creationDate`,`lastUpdateBy`,`lastUpdateDate`,`featureAppState`,`unFeatureDate`,`application_id`) VALUES (109,'maintainer','2017-01-14 08:04:29','maintainer','2017-01-14 08:04:29',1,NULL,'9');

INSERT INTO `review` (`id`,`createBy`,`creationDate`,`lastUpdateBy`,`lastUpdateDate`,`description`,`featured`,`title`,`application_id`,`consumer_ID`) VALUES (101,'consumer1@usm.com','2016-12-12 00:00:00','consumer5@usm.com','2016-12-12 00:00:00','Test Description1',1,'Test review1','3',106);
INSERT INTO `review` (`id`,`createBy`,`creationDate`,`lastUpdateBy`,`lastUpdateDate`,`description`,`featured`,`title`,`application_id`,`consumer_ID`) VALUES (102,'consumer2@usm.com','2016-12-12 00:00:00','consumer4@usm.com','2016-12-12 00:00:00','Test Description2',0,'Test review2','3',107);
INSERT INTO `review` (`id`,`createBy`,`creationDate`,`lastUpdateBy`,`lastUpdateDate`,`description`,`featured`,`title`,`application_id`,`consumer_ID`) VALUES (103,'consumer3@usm.com','2016-12-12 00:00:00','consumer6@usm.com','2016-12-12 00:00:00','Test Description3',1,'Test review3','3',108);
