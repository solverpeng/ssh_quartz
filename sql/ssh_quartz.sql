/*
SQLyog Ultimate v11.25 (64 bit)
MySQL - 5.6.14-log : Database - ssh_quartz
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`ssh_quartz` /*!40100 DEFAULT CHARACTER SET utf8 */;

USE `ssh_quartz`;

/*Table structure for table `task` */

DROP TABLE IF EXISTS `task`;

CREATE TABLE `task` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `task_name` varchar(255) NOT NULL COMMENT '任务名称',
  `start_time` datetime DEFAULT NULL COMMENT '任务开始时间',
  `end_time` datetime DEFAULT NULL COMMENT '任务结束时间',
  `interval_time` int(10) DEFAULT NULL COMMENT '任务间隔时间',
  `interval_unit` varchar(50) DEFAULT NULL COMMENT '任务间隔单位',
  `operate_class` varchar(255) DEFAULT NULL COMMENT '执行类全类名',
  `cron_expression` varchar(255) DEFAULT NULL COMMENT 'cron表达式',
  `last_execute_time` datetime DEFAULT NULL COMMENT '最后一次执行时间',
  `status` int(1) DEFAULT NULL COMMENT '任务状态，0正常，1停用',
  `remark` varchar(255) DEFAULT NULL COMMENT '备注',
  `deleted` tinyint(1) DEFAULT NULL COMMENT '是否删除,0正常，1删除',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8;

/*Data for the table `task` */

insert  into `task`(`id`,`task_name`,`start_time`,`end_time`,`interval_time`,`interval_unit`,`operate_class`,`cron_expression`,`last_execute_time`,`status`,`remark`,`deleted`) values (1,'定时任务1','2017-02-21 00:00:00','2018-01-01 00:00:00',1,'S','com.solverpeng.job.TargetJob',NULL,NULL,1,'定时任务1@',1),(2,'定时任务2','2017-02-21 12:51:12','2017-03-15 17:51:17',2,'S','com.solverpeng.job.TargetJob2',NULL,NULL,0,'定时任务2',0),(3,'定时任务3','2017-02-21 17:52:49','2017-02-23 17:53:00',3,'S','com.solverpeng.job.TargetJob3',NULL,NULL,0,'定时任务3',0),(4,'定时任务4','2017-02-21 17:52:53','2017-02-24 17:53:03',4,'S','com.solverpeng.job.TargetJob4',NULL,NULL,0,'定时任务4',0),(5,'定时任务5','2017-02-21 17:52:55','2017-02-23 17:53:05',5,'S','com.solverpeng.job.TargetJob5',NULL,NULL,0,'定时任务5',0),(6,'定时任务6','2017-02-20 17:52:57','2017-02-23 17:53:07',5,'S','com.solverpeng.job.TargetJob6',NULL,NULL,0,'定时任务6',0),(7,'定时任务7','2017-02-20 17:52:59','2017-02-24 17:53:09',6,'S','com.solverpeng.job.TargetJob7',NULL,NULL,0,'定时任务7',0);

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
