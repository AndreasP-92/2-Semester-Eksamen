SELECT * FROM wag_app.org;CREATE TABLE `auth` (
  `auth_id` int NOT NULL AUTO_INCREMENT,
  `auth_role` varchar(255) NOT NULL,
  `fk_userMail` varchar(255) NOT NULL,
  PRIMARY KEY (`auth_id`),
  UNIQUE KEY `auth_id_UNIQUE` (`auth_id`),
  UNIQUE KEY `fk_userMail_UNIQUE` (`fk_userMail`),
  KEY `fk_userMail_idx` (`fk_userMail`),
  KEY `auth_role_idx` (`auth_role`),
  CONSTRAINT `auth_role` FOREIGN KEY (`auth_role`) REFERENCES `authCheck` (`authcheck_role`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `fk_userMail` FOREIGN KEY (`fk_userMail`) REFERENCES `users` (`user_mail`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=99 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `authCheck` (
  `authcheck_id` int NOT NULL AUTO_INCREMENT,
  `authcheck_role` varchar(45) NOT NULL,
  PRIMARY KEY (`authcheck_id`,`authcheck_role`),
  KEY `authcheck_role` (`authcheck_role`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `ganttPhase` (
  `ganttPhase_id` int NOT NULL AUTO_INCREMENT,
  `ganttPhase_name` varchar(225) NOT NULL,
  PRIMARY KEY (`ganttPhase_id`,`ganttPhase_name`),
  KEY `ganttPhase_name` (`ganttPhase_name`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `jobTitle` (
  `jobTitle_id` int NOT NULL AUTO_INCREMENT,
  `jobTitle_name` varchar(255) NOT NULL,
  `fk_orgId` int DEFAULT NULL,
  PRIMARY KEY (`jobTitle_id`,`jobTitle_name`),
  UNIQUE KEY `jobtitle_id_UNIQUE` (`jobTitle_id`),
  KEY `fk_orgIdd_idx` (`fk_orgId`),
  KEY `jobTitle_name_idx` (`jobTitle_name`),
  CONSTRAINT `fk_orgIdd` FOREIGN KEY (`fk_orgId`) REFERENCES `org` (`org_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=20 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `news` (
  `newsfeed_id` int NOT NULL AUTO_INCREMENT,
  `newsfeed_news` varchar(255) NOT NULL,
  `newsfeed_title` varchar(255) NOT NULL,
  `newsfeed_img` mediumblob,
  `newsfeed_datetime` timestamp(4) NOT NULL,
  `fk_orgName` varchar(50) NOT NULL,
  PRIMARY KEY (`newsfeed_id`),
  UNIQUE KEY `newsfeed_id_UNIQUE` (`newsfeed_id`),
  KEY `fk_orgName_idx` (`fk_orgName`),
  CONSTRAINT `fk_orgName` FOREIGN KEY (`fk_orgName`) REFERENCES `org` (`org_name`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=72 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `org` (
  `org_id` int NOT NULL AUTO_INCREMENT,
  `org_name` varchar(50) NOT NULL,
  `org_address` varchar(50) NOT NULL,
  `org_cvr` int NOT NULL,
  PRIMARY KEY (`org_id`,`org_name`),
  UNIQUE KEY `org_id_UNIQUE` (`org_id`),
  UNIQUE KEY `org_name_UNIQUE` (`org_name`),
  UNIQUE KEY `org_cvr_UNIQUE` (`org_cvr`)
) ENGINE=InnoDB AUTO_INCREMENT=26 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `payment` (
  `payment_id` int NOT NULL AUTO_INCREMENT,
  `payment_price` int NOT NULL,
  `payment_date` timestamp(4) NOT NULL,
  `fk_userId` int NOT NULL,
  PRIMARY KEY (`payment_id`),
  KEY `fk_userIdPayment_idx` (`fk_userId`),
  CONSTRAINT `fk_userIdPayment` FOREIGN KEY (`fk_userId`) REFERENCES `users` (`user_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=19 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `profile` (
  `profile_id` int NOT NULL AUTO_INCREMENT,
  `profile_firstname` varchar(100) NOT NULL,
  `profile_lastname` varchar(100) NOT NULL,
  `profile_address` varchar(100) NOT NULL,
  `profile_phone` int NOT NULL,
  `profile_country` varchar(100) NOT NULL,
  `profile_zip` int NOT NULL,
  `profile_jobTitle` varchar(50) NOT NULL,
  `fk_userId` int NOT NULL,
  PRIMARY KEY (`profile_id`,`profile_firstname`),
  UNIQUE KEY `profile_id_UNIQUE` (`profile_id`),
  KEY `fk_userId_idx` (`fk_userId`),
  KEY `profile_firstname` (`profile_firstname`),
  CONSTRAINT `fk_userId` FOREIGN KEY (`fk_userId`) REFERENCES `users` (`user_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=90 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `project` (
  `project_id` int NOT NULL AUTO_INCREMENT,
  `project_name` varchar(50) NOT NULL,
  `project_desc` varchar(255) NOT NULL,
  `project_duration` int NOT NULL,
  `project_start` date NOT NULL,
  `project_end` date NOT NULL,
  `fk_orgId` int NOT NULL,
  PRIMARY KEY (`project_id`,`project_name`),
  UNIQUE KEY `project_id_UNIQUE` (`project_id`),
  KEY `fk_orgId_idx` (`fk_orgId`),
  KEY `project_name` (`project_name`),
  CONSTRAINT `fk_orgId` FOREIGN KEY (`fk_orgId`) REFERENCES `org` (`org_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=35 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `project_jobTitle` (
  `projectJobTitle_id` int NOT NULL AUTO_INCREMENT,
  `project_id` int NOT NULL,
  `jobTitle_id` int NOT NULL,
  PRIMARY KEY (`projectJobTitle_id`,`jobTitle_id`,`project_id`),
  UNIQUE KEY `projectJobTitle_id_UNIQUE` (`projectJobTitle_id`),
  KEY `fk_projectId_idx` (`project_id`),
  KEY `fk_jobTitleId_idx` (`jobTitle_id`),
  CONSTRAINT `fk_jobTitleIDD` FOREIGN KEY (`jobTitle_id`) REFERENCES `jobTitle` (`jobTitle_id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `fk_projectId` FOREIGN KEY (`project_id`) REFERENCES `project` (`project_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=53 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `supportMessage` (
  `message_id` int NOT NULL AUTO_INCREMENT,
  `message_context` varchar(1000) NOT NULL,
  `message_timestamp` varchar(75) NOT NULL,
  `fk_ticketId` int NOT NULL,
  `fk_profileFirstname` varchar(100) NOT NULL,
  PRIMARY KEY (`message_id`),
  KEY `fk_ticketId_idx` (`fk_ticketId`),
  KEY `fk_profileFirstname_idx` (`fk_profileFirstname`),
  CONSTRAINT `fk_profileFirstname` FOREIGN KEY (`fk_profileFirstname`) REFERENCES `profile` (`profile_firstname`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `fk_ticketId` FOREIGN KEY (`fk_ticketId`) REFERENCES `supportTicket` (`supportTicket_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=37 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `supportTicket` (
  `supportTicket_id` int NOT NULL AUTO_INCREMENT,
  `supportTicket_title` varchar(255) NOT NULL,
  `supportTicket_context` varchar(1000) NOT NULL,
  `supportTicket_timestamp` timestamp(4) NOT NULL,
  `supportTicket_ownerMail` varchar(225) NOT NULL,
  `supportTicket_ownerName` varchar(225) NOT NULL,
  `supportTicket_active` int NOT NULL,
  `supportTicket_taken` int NOT NULL,
  `supportTicket_adminReplied` int NOT NULL,
  `supportTicket_userReplied` int NOT NULL,
  PRIMARY KEY (`supportTicket_id`),
  UNIQUE KEY `ticket_id_UNIQUE` (`supportTicket_id`)
) ENGINE=InnoDB AUTO_INCREMENT=23 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `task` (
  `task_id` int NOT NULL AUTO_INCREMENT,
  `task_name` varchar(100) NOT NULL,
  `task_desc` varchar(225) NOT NULL,
  `task_duration` int NOT NULL,
  `task_start` date NOT NULL,
  `task_end` date NOT NULL,
  `task_processEnd` int NOT NULL,
  `task_processStart` int NOT NULL,
  `fk_projectName` varchar(225) NOT NULL,
  `fk_profileName` varchar(225) NOT NULL,
  `fk_ganttPhaseName` varchar(225) NOT NULL,
  `fk_jobTitleName` varchar(225) NOT NULL,
  PRIMARY KEY (`task_id`),
  UNIQUE KEY `gantt_id_UNIQUE` (`task_id`),
  KEY `fk_projectName` (`fk_projectName`),
  KEY `fk_profileName_idx` (`fk_profileName`),
  KEY `fk_ganttPhaseName_idx` (`fk_ganttPhaseName`),
  KEY `fk_jobTitleName_idx` (`fk_jobTitleName`),
  CONSTRAINT `fk_ganttPhaseName` FOREIGN KEY (`fk_ganttPhaseName`) REFERENCES `ganttPhase` (`ganttPhase_name`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `fk_jobTitleName` FOREIGN KEY (`fk_jobTitleName`) REFERENCES `jobTitle` (`jobTitle_name`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `fk_profileName` FOREIGN KEY (`fk_profileName`) REFERENCES `profile` (`profile_firstname`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `fk_projectName` FOREIGN KEY (`fk_projectName`) REFERENCES `project` (`project_name`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=24 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `ticket_user` (
  `ticketUser_id` int NOT NULL AUTO_INCREMENT,
  `ticket_id` int NOT NULL,
  `user_id` int NOT NULL,
  PRIMARY KEY (`ticketUser_id`,`ticket_id`,`user_id`),
  KEY `user_id_idx` (`user_id`),
  KEY `ticket_id_idx` (`ticket_id`),
  CONSTRAINT `ticket_id` FOREIGN KEY (`ticket_id`) REFERENCES `supportTicket` (`supportTicket_id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `user_id` FOREIGN KEY (`user_id`) REFERENCES `users` (`user_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=32 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `users` (
  `user_id` int NOT NULL AUTO_INCREMENT,
  `user_mail` varchar(255) NOT NULL,
  `user_password` varchar(65) NOT NULL,
  `user_enabled` tinyint(1) NOT NULL,
  `fk_orgId` int DEFAULT NULL,
  PRIMARY KEY (`user_id`,`user_mail`),
  UNIQUE KEY `user_id_UNIQUE` (`user_id`),
  UNIQUE KEY `user_mail_UNIQUE` (`user_mail`),
  KEY `fk_orgId_idx` (`fk_orgId`),
  CONSTRAINT `fk_orgIds` FOREIGN KEY (`fk_orgId`) REFERENCES `org` (`org_id`) ON DELETE SET NULL ON UPDATE SET NULL
) ENGINE=InnoDB AUTO_INCREMENT=119 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
