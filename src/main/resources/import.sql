USE usmdevdb;
DROP FUNCTION IF EXISTS fn_regexp_like;
CREATE FUNCTION fn_regexp_like (value1 nvarchar(4000),value2 nvarchar(4000)) RETURNS CHAR(1) DETERMINISTIC RETURN value1 REGEXP value2;

INSERT INTO AUTHORITY (ID, NAME) VALUES (1, 'ROLE_CONSUMER');
INSERT INTO AUTHORITY (ID, NAME) VALUES (2, 'ROLE_DEVELOPER');
INSERT INTO AUTHORITY (ID, NAME) VALUES (3, 'ROLE_ADMIN');
INSERT INTO AUTHORITY (ID, NAME) VALUES (4, 'ROLE_MANAGER');
INSERT INTO AUTHORITY (ID, NAME) VALUES (5, 'ROLE_MAINTAINER');

INSERT INTO Category (id, name) VALUES (101, 'Productivity');
INSERT INTO Category (id, name) VALUES (102, 'Photography');
INSERT INTO Category (id, name) VALUES (103, 'Entertainment');
INSERT INTO Category (id, name) VALUES (104, 'Education');
INSERT INTO Category (id, name) VALUES (105, 'Finance');
INSERT INTO Category (id, name) VALUES (106, 'Shopping');
INSERT INTO Category (id, name) VALUES (107, 'Social Networking');
INSERT INTO Category (id, name) VALUES (108, 'Utilities');

-- To create 5 developers
INSERT INTO `USER` (`ID`,`EMAIL`,`ENABLED`,`FIRSTNAME`,`LASTPASSWORDRESETDATE`,`LASTNAME`,`PASSWORD`,`USERNAME`) VALUES (101,'developer1@usm.com',1,NULL,'2017-01-08 03:59:23',NULL,'$2a$10$AMt7mj5oYaIThHB0fL4v6.D4btzzy5jMRaLQaDRigmzWSqRuu/NoS','developer1@usm.com');
INSERT INTO `USER` (`ID`,`EMAIL`,`ENABLED`,`FIRSTNAME`,`LASTPASSWORDRESETDATE`,`LASTNAME`,`PASSWORD`,`USERNAME`) VALUES (102,'developer2@usm.com',1,NULL,'2017-01-08 03:59:59',NULL,'$2a$10$2W3rT5Q6Mmfbi0lQc5DDVeppusmbb4Tut1RbScsMPDFvmdvgTXXla','developer2@usm.com');
INSERT INTO `USER` (`ID`,`EMAIL`,`ENABLED`,`FIRSTNAME`,`LASTPASSWORDRESETDATE`,`LASTNAME`,`PASSWORD`,`USERNAME`) VALUES (103,'developer3@usm.com',1,NULL,'2017-01-08 04:00:10',NULL,'$2a$10$NBfbFW1sM7woPLtahK7ajOhOdTA3jciR3T/U6HAbFqVtFTrcDSG7u','developer3@usm.com');
INSERT INTO `USER` (`ID`,`EMAIL`,`ENABLED`,`FIRSTNAME`,`LASTPASSWORDRESETDATE`,`LASTNAME`,`PASSWORD`,`USERNAME`) VALUES (104,'developer4@usm.com',1,NULL,'2017-01-08 04:00:22',NULL,'$2a$10$FVjS9nOUPHnXDC3WuDBMJ.kwxLIIFDjWsVqp.22XfGPkO1AcJCmG6','developer4@usm.com');
INSERT INTO `USER` (`ID`,`EMAIL`,`ENABLED`,`FIRSTNAME`,`LASTPASSWORDRESETDATE`,`LASTNAME`,`PASSWORD`,`USERNAME`) VALUES (105,'developer5@usm.com',1,NULL,'2017-01-08 04:00:35',NULL,'$2a$10$vke5bYyTlun3VM5pPX.LLeyC9iMTdqGeETRjkTMVug.yiXaTqqvfS','developer5@usm.com');

-- To assign both "Developer" and "Consumer" authority to all 5 developers
INSERT INTO `USER_AUTHORITY` (`USER_ID`,`AUTHORITY_ID`) VALUES (101,1);
INSERT INTO `USER_AUTHORITY` (`USER_ID`,`AUTHORITY_ID`) VALUES (101,2);
INSERT INTO `USER_AUTHORITY` (`USER_ID`,`AUTHORITY_ID`) VALUES (102,1);
INSERT INTO `USER_AUTHORITY` (`USER_ID`,`AUTHORITY_ID`) VALUES (102,2);
INSERT INTO `USER_AUTHORITY` (`USER_ID`,`AUTHORITY_ID`) VALUES (103,1);
INSERT INTO `USER_AUTHORITY` (`USER_ID`,`AUTHORITY_ID`) VALUES (103,2);
INSERT INTO `USER_AUTHORITY` (`USER_ID`,`AUTHORITY_ID`) VALUES (104,1);
INSERT INTO `USER_AUTHORITY` (`USER_ID`,`AUTHORITY_ID`) VALUES (104,2);
INSERT INTO `USER_AUTHORITY` (`USER_ID`,`AUTHORITY_ID`) VALUES (105,1);
INSERT INTO `USER_AUTHORITY` (`USER_ID`,`AUTHORITY_ID`) VALUES (105,2);

-- To create 5 consumers
INSERT INTO `USER` (`ID`,`EMAIL`,`ENABLED`,`FIRSTNAME`,`LASTPASSWORDRESETDATE`,`LASTNAME`,`PASSWORD`,`USERNAME`) VALUES (106,'consumer1@usm.com',1,NULL,'2017-01-08 04:30:24',NULL,'$2a$10$zEiACAqp7mJlQMxX0g1Q3eLxZEv6nxFN8t6OVTupLFR/oIrkfVGPC','consumer1@usm.com');
INSERT INTO `USER` (`ID`,`EMAIL`,`ENABLED`,`FIRSTNAME`,`LASTPASSWORDRESETDATE`,`LASTNAME`,`PASSWORD`,`USERNAME`) VALUES (107,'consumer2@usm.com',1,NULL,'2017-01-08 04:30:36',NULL,'$2a$10$1Rv2jAZbkbBKKKz9vPMO/uUSDnLIgEihQSxa7nS5IrvtEvALPPmay','consumer2@usm.com');
INSERT INTO `USER` (`ID`,`EMAIL`,`ENABLED`,`FIRSTNAME`,`LASTPASSWORDRESETDATE`,`LASTNAME`,`PASSWORD`,`USERNAME`) VALUES (108,'consumer3@usm.com',1,NULL,'2017-01-08 04:30:50',NULL,'$2a$10$TTQqNH1jzWncyfjxcxjHxOYWdxg7YHkyfaHYIvevCWytkVd2AGQZC','consumer3@usm.com');
INSERT INTO `USER` (`ID`,`EMAIL`,`ENABLED`,`FIRSTNAME`,`LASTPASSWORDRESETDATE`,`LASTNAME`,`PASSWORD`,`USERNAME`) VALUES (109,'consumer4@usm.com',1,NULL,'2017-01-08 04:31:01',NULL,'$2a$10$pjHft3Vr1f9AT9nz70mWpeKmCrqH3pKn3csFPfbzrR/FfOdaCmrPy','consumer4@usm.com');
INSERT INTO `USER` (`ID`,`EMAIL`,`ENABLED`,`FIRSTNAME`,`LASTPASSWORDRESETDATE`,`LASTNAME`,`PASSWORD`,`USERNAME`) VALUES (110,'consumer5@usm.com',1,NULL,'2017-01-08 04:31:13',NULL,'$2a$10$iT6WpHrmLM2mBXDvmXW.K.rFHdtly65Tc5f70V2hqh4QdmLlzOs0K','consumer5@usm.com');

-- To assign "Consumer" authority to the 5 consumers
INSERT INTO `USER_AUTHORITY` (`USER_ID`,`AUTHORITY_ID`) VALUES (101,1);
INSERT INTO `USER_AUTHORITY` (`USER_ID`,`AUTHORITY_ID`) VALUES (102,1);
INSERT INTO `USER_AUTHORITY` (`USER_ID`,`AUTHORITY_ID`) VALUES (103,1);
INSERT INTO `USER_AUTHORITY` (`USER_ID`,`AUTHORITY_ID`) VALUES (104,1);
INSERT INTO `USER_AUTHORITY` (`USER_ID`,`AUTHORITY_ID`) VALUES (105,1);

-- To create 2 employees: Site Manager and Site Maintainer
INSERT INTO `USER` (`ID`,`EMAIL`,`ENABLED`,`FIRSTNAME`,`LASTPASSWORDRESETDATE`,`LASTNAME`,`PASSWORD`,`USERNAME`) VALUES (111,'manager1@usm.com',1,NULL,'2017-01-08 04:30:24',NULL,'$2a$10$p0k2y/vQER2o58mzuPK2fu9UrJSEc2nZtC2PHvBTfxijMfx3VzRmK','manager1@usm.com');
INSERT INTO `USER` (`ID`,`EMAIL`,`ENABLED`,`FIRSTNAME`,`LASTPASSWORDRESETDATE`,`LASTNAME`,`PASSWORD`,`USERNAME`) VALUES (112,'maintainer1@usm.com',1,NULL,'2017-01-08 04:30:36',NULL,'$2a$10$zJKhQK00/YSXf4BUFyZyY.HCAeZCmTnlItyEZAdIswpYdoHiv0DJu','maintainer1@usm.com');

-- To assign manager and maintainer authority to the 2 employees
INSERT INTO `USER_AUTHORITY` (`USER_ID`,`AUTHORITY_ID`) VALUES (111,4);   -- Site Manager
INSERT INTO `USER_AUTHORITY` (`USER_ID`,`AUTHORITY_ID`) VALUES (112,5);   -- Site Maintainer

-- To create 10 applications
-- Category: 1 = Uncategorized, Category 2 = Productivity
-- State: O = Staging, 1 = Published, 2 = Recalled
INSERT INTO `Application` (`id`,`description`,`name`,`version`,`whatsNew`,`applicationpublishdate`,`state`,`category_id`,`developer_ID`) VALUES ('1','Dev 1 App 1','Dev1App1','1.0','nothing',NULL,1,101,101);
INSERT INTO `Application` (`id`,`description`,`name`,`version`,`whatsNew`,`applicationpublishdate`,`state`,`category_id`,`developer_ID`) VALUES ('2','Dev 1 App 2','Dev1App2','1.0','nothing',NULL,1,102,101);
INSERT INTO `Application` (`id`,`description`,`name`,`version`,`whatsNew`,`applicationpublishdate`,`state`,`category_id`,`developer_ID`) VALUES ('3','Dev 1 App 3','Dev1App3','1.0','nothing',NULL,1,103,101);
INSERT INTO `Application` (`id`,`description`,`name`,`version`,`whatsNew`,`applicationpublishdate`,`state`,`category_id`,`developer_ID`) VALUES ('4','Dev 2 App 1','Dev2App1','1.0','nothing',NULL,1,104,102);
INSERT INTO `Application` (`id`,`description`,`name`,`version`,`whatsNew`,`applicationpublishdate`,`state`,`category_id`,`developer_ID`) VALUES ('5','Dev 3 App 1','Dev3App1','1.0','nothing',NULL,1,105,103);
INSERT INTO `Application` (`id`,`description`,`name`,`version`,`whatsNew`,`applicationpublishdate`,`state`,`category_id`,`developer_ID`) VALUES ('6','Dev 3 App 2','Dev3App2','1.0','nothing',NULL,1,106,103);
INSERT INTO `Application` (`id`,`description`,`name`,`version`,`whatsNew`,`applicationpublishdate`,`state`,`category_id`,`developer_ID`) VALUES ('7','Dev 4 App 1','Dev4App1','1.0','nothing',NULL,1,107,104);
INSERT INTO `Application` (`id`,`description`,`name`,`version`,`whatsNew`,`applicationpublishdate`,`state`,`category_id`,`developer_ID`) VALUES ('8','Dev 4 App 2','Dev4App2','1.0','nothing',NULL,1,108,104);
INSERT INTO `Application` (`id`,`description`,`name`,`version`,`whatsNew`,`applicationpublishdate`,`state`,`category_id`,`developer_ID`) VALUES ('9','Dev 4 App 3','Dev4App3','1.0','nothing',NULL,1,101,104);
INSERT INTO `Application` (`id`,`description`,`name`,`version`,`whatsNew`,`applicationpublishdate`,`state`,`category_id`,`developer_ID`) VALUES ('10','Dev 5 App 1','Dev5App1','1.0','nothing',NULL,1,102,105);
INSERT INTO `Application` (id, description, name, version, whatsNew, applicationpublishdate, state, category_id, developer_ID) VALUES ('4028b88159e858c60159e97351da0000', 'New version', 'Visual Studio Code', '1.8.1', 'This', null, 1, 108, 101);
INSERT INTO `Application` (id, description, name, version, whatsNew, applicationpublishdate, state, category_id, developer_ID) VALUES ('4028b88159e858c60159e9801baa0001', 'gimp', 'Gimp', '2.8.18', 'new', null, 1, 102, 101);
INSERT INTO `Application` (id, description, name, version, whatsNew, applicationpublishdate, state, category_id, developer_ID) VALUES ('4028b88159e858c60159e9a6d5970002', '7.3.1', 'Notepad++', '7.3.1', '7.3.1', null, 1, 101, 101);

INSERT INTO `Installer` (id, downloadUrl, expressInstallCommand, launchCommand, os, platform, uninstallCommand, application_id, applicationUpdate_id) VALUES (107, 'https://az764295.vo.msecnd.net/stable/ee428b0eead68bf0fb99ab5fdc4439be227b6281/VSCodeSetup-1.8.1.exe', '', '%ProgramFiles(x86)\\Microsoft VS Code\\Code.exe', 0, 1, '%ProgramFiles(x86)%\\Microsoft VS Code\\unins000.exe', '4028b88159e858c60159e97351da0000', null);
INSERT INTO `Installer` (id, downloadUrl, expressInstallCommand, launchCommand, os, platform, uninstallCommand, application_id, applicationUpdate_id) VALUES (108, null, null, null, 0, 0, null, '4028b88159e858c60159e97351da0000', null);
INSERT INTO `Installer` (id, downloadUrl, expressInstallCommand, launchCommand, os, platform, uninstallCommand, application_id, applicationUpdate_id) VALUES (109, null, null, null, 1, 1, null, '4028b88159e858c60159e97351da0000', null);
INSERT INTO `Installer` (id, downloadUrl, expressInstallCommand, launchCommand, os, platform, uninstallCommand, application_id, applicationUpdate_id) VALUES (110, null, null, null, 2, 1, null, '4028b88159e858c60159e97351da0000', null);
INSERT INTO `Installer` (id, downloadUrl, expressInstallCommand, launchCommand, os, platform, uninstallCommand, application_id, applicationUpdate_id) VALUES (111, null, null, null, 2, 0, null, '4028b88159e858c60159e97351da0000', null);
INSERT INTO `Installer` (id, downloadUrl, expressInstallCommand, launchCommand, os, platform, uninstallCommand, application_id, applicationUpdate_id) VALUES (118, 'http://www.mirrorservice.org/sites/ftp.gimp.org/pub/gimp/v2.8/windows/gimp-2.8.18-setup.exe', null, '%ProgramFiles%\\GIMP 2\\bin\\gimp-2.8.exe', 0, 1, '%ProgramFiles%\\GIMP 2\\uninst\\unins000.exe', '4028b88159e858c60159e9801baa0001', null);
INSERT INTO `Installer` (id, downloadUrl, expressInstallCommand, launchCommand, os, platform, uninstallCommand, application_id, applicationUpdate_id) VALUES (119, null, null, null, 0, 0, null, '4028b88159e858c60159e9801baa0001', null);
INSERT INTO `Installer` (id, downloadUrl, expressInstallCommand, launchCommand, os, platform, uninstallCommand, application_id, applicationUpdate_id) VALUES (120, null, null, null, 1, 1, null, '4028b88159e858c60159e9801baa0001', null);
INSERT INTO `Installer` (id, downloadUrl, expressInstallCommand, launchCommand, os, platform, uninstallCommand, application_id, applicationUpdate_id) VALUES (121, null, null, null, 2, 1, null, '4028b88159e858c60159e9801baa0001', null);
INSERT INTO `Installer` (id, downloadUrl, expressInstallCommand, launchCommand, os, platform, uninstallCommand, application_id, applicationUpdate_id) VALUES (122, null, null, null, 2, 0, null, '4028b88159e858c60159e9801baa0001', null);
INSERT INTO `Installer` (id, downloadUrl, expressInstallCommand, launchCommand, os, platform, uninstallCommand, application_id, applicationUpdate_id) VALUES (129, 'https://notepad-plus-plus.org/repository/7.x/7.3.1/npp.7.3.1.Installer.exe', null, '%ProgramFiles(x86)%\\Notepad++\\notepad++.exe', 0, 1, '%ProgramFiles(x86)%\\Notepad++\\uninstall.exe', '4028b88159e858c60159e9a6d5970002', null);
INSERT INTO `Installer` (id, downloadUrl, expressInstallCommand, launchCommand, os, platform, uninstallCommand, application_id, applicationUpdate_id) VALUES (130, null, null, null, 0, 0, null, '4028b88159e858c60159e9a6d5970002', null);
INSERT INTO `Installer` (id, downloadUrl, expressInstallCommand, launchCommand, os, platform, uninstallCommand, application_id, applicationUpdate_id) VALUES (131, null, null, null, 1, 1, null, '4028b88159e858c60159e9a6d5970002', null);
INSERT INTO `Installer` (id, downloadUrl, expressInstallCommand, launchCommand, os, platform, uninstallCommand, application_id, applicationUpdate_id) VALUES (132, null, null, null, 2, 1, null, '4028b88159e858c60159e9a6d5970002', null);
INSERT INTO `Installer` (id, downloadUrl, expressInstallCommand, launchCommand, os, platform, uninstallCommand, application_id, applicationUpdate_id) VALUES (133, null, null, null, 2, 0, null, '4028b88159e858c60159e9a6d5970002', null);

--To create 5 Active Featured applications
--And, create 4 Inactive Featured applications
INSERT INTO `FeaturedApplication` (`id`,`createBy`,`creationDate`,`lastUpdateBy`,`lastUpdateDate`,`featureAppState`,`unFeatureDate`,`application_id`) VALUES (101,'maintainer','2017-01-14 08:04:28','maintainer','2017-01-14 08:04:28',0,NULL,'1');
INSERT INTO `FeaturedApplication` (`id`,`createBy`,`creationDate`,`lastUpdateBy`,`lastUpdateDate`,`featureAppState`,`unFeatureDate`,`application_id`) VALUES (102,'maintainer','2017-01-14 08:04:29','maintainer','2017-01-14 08:04:29',0,NULL,'2');
INSERT INTO `FeaturedApplication` (`id`,`createBy`,`creationDate`,`lastUpdateBy`,`lastUpdateDate`,`featureAppState`,`unFeatureDate`,`application_id`) VALUES (103,'maintainer','2017-01-14 08:04:29','maintainer','2017-01-14 08:04:29',0,NULL,'3');
INSERT INTO `FeaturedApplication` (`id`,`createBy`,`creationDate`,`lastUpdateBy`,`lastUpdateDate`,`featureAppState`,`unFeatureDate`,`application_id`) VALUES (104,'maintainer','2017-01-14 08:04:29','maintainer','2017-01-14 08:04:29',0,NULL,'4');
INSERT INTO `FeaturedApplication` (`id`,`createBy`,`creationDate`,`lastUpdateBy`,`lastUpdateDate`,`featureAppState`,`unFeatureDate`,`application_id`) VALUES (105,'maintainer','2017-01-14 08:04:29','maintainer','2017-01-14 08:04:29',0,NULL,'5');
INSERT INTO `FeaturedApplication` (`id`,`createBy`,`creationDate`,`lastUpdateBy`,`lastUpdateDate`,`featureAppState`,`unFeatureDate`,`application_id`) VALUES (106,'maintainer','2017-01-14 08:04:28','maintainer','2017-01-14 08:04:28',1,NULL,'6');
INSERT INTO `FeaturedApplication` (`id`,`createBy`,`creationDate`,`lastUpdateBy`,`lastUpdateDate`,`featureAppState`,`unFeatureDate`,`application_id`) VALUES (107,'maintainer','2017-01-14 08:04:29','maintainer','2017-01-14 08:04:29',1,NULL,'7');
INSERT INTO `FeaturedApplication` (`id`,`createBy`,`creationDate`,`lastUpdateBy`,`lastUpdateDate`,`featureAppState`,`unFeatureDate`,`application_id`) VALUES (108,'maintainer','2017-01-14 08:04:29','maintainer','2017-01-14 08:04:29',1,NULL,'8');
INSERT INTO `FeaturedApplication` (`id`,`createBy`,`creationDate`,`lastUpdateBy`,`lastUpdateDate`,`featureAppState`,`unFeatureDate`,`application_id`) VALUES (109,'maintainer','2017-01-14 08:04:29','maintainer','2017-01-14 08:04:29',1,NULL,'9');

INSERT INTO `Review` (`id`,`createBy`,`creationDate`,`lastUpdateBy`,`lastUpdateDate`,`description`,`featured`,`title`,`application_id`,`consumer_ID`) VALUES (101,'consumer1@usm.com','2016-12-12 00:00:00','consumer5@usm.com','2016-12-12 00:00:00','Test Description1',1,'Test review1','3',106);
INSERT INTO `Review` (`id`,`createBy`,`creationDate`,`lastUpdateBy`,`lastUpdateDate`,`description`,`featured`,`title`,`application_id`,`consumer_ID`) VALUES (102,'consumer2@usm.com','2016-12-12 00:00:00','consumer4@usm.com','2016-12-12 00:00:00','Test Description2',0,'Test review2','3',107);
INSERT INTO `Review` (`id`,`createBy`,`creationDate`,`lastUpdateBy`,`lastUpdateDate`,`description`,`featured`,`title`,`application_id`,`consumer_ID`) VALUES (103,'consumer3@usm.com','2016-12-12 00:00:00','consumer6@usm.com','2016-12-12 00:00:00','Test Description3',1,'Test review3','3',108);
