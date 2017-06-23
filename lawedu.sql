/*
Navicat MySQL Data Transfer

Source Server         : local
Source Server Version : 50617
Source Host           : localhost:3306
Source Database       : lawedu

Target Server Type    : MYSQL
Target Server Version : 50617
File Encoding         : 65001

Date: 2017-06-23 10:22:03
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `article`
-- ----------------------------
DROP TABLE IF EXISTS `article`;
CREATE TABLE `article` (
  `id` varchar(255) NOT NULL COMMENT '成果id',
  `name` varchar(255) DEFAULT NULL COMMENT '成果名称',
  `addUser` varchar(255) DEFAULT NULL COMMENT '添加人',
  `status` varchar(255) DEFAULT NULL,
  `addTime` date DEFAULT NULL COMMENT '添加时间',
  `description` varchar(255) DEFAULT NULL COMMENT '描述',
  PRIMARY KEY (`id`),
  KEY `articleuserid` (`addUser`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of article
-- ----------------------------
INSERT INTO `article` VALUES ('article-1705160513216154', '222', 'u-1705091101562789', null, '2017-05-16', '111');

-- ----------------------------
-- Table structure for `ask`
-- ----------------------------
DROP TABLE IF EXISTS `ask`;
CREATE TABLE `ask` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '提问id',
  `fromId` varchar(255) DEFAULT NULL COMMENT '提问学生id',
  `toId` varchar(255) DEFAULT NULL COMMENT '课程id',
  `content` text COMMENT '提问内容',
  `addTime` datetime DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`),
  KEY `ask1` (`toId`) USING BTREE,
  KEY `ask2` (`fromId`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of ask
-- ----------------------------
INSERT INTO `ask` VALUES ('1', 'u-1705251047434764', 'cv-005', '老师讲的挺好的！', '2017-05-26 14:36:43');
INSERT INTO `ask` VALUES ('2', 'u-1705251047434764', 'cv-006', '老师讲的挺好的！', '2017-05-26 14:36:43');
INSERT INTO `ask` VALUES ('3', 'u-1705251047434764', 'cv-007', '老师讲的挺好的！', '2017-05-26 14:36:43');
INSERT INTO `ask` VALUES ('4', 'u-1705251047434764', 'cv-008', '老师讲的挺好的！', '2017-05-26 14:36:43');
INSERT INTO `ask` VALUES ('5', 'u-1705251047434764', 'cv-009', '老师讲的挺好的！', '2017-05-26 14:36:43');
INSERT INTO `ask` VALUES ('6', 'u-1705251047434764', 'cv-010', '老师讲的挺好的！', '2017-05-10 14:41:31');
INSERT INTO `ask` VALUES ('7', 'u-1705270436283295', 'cv-001', '7576', '2017-06-02 16:45:04');
INSERT INTO `ask` VALUES ('8', 'u-1705270442206814', 'cv-001', '老师你好', '2017-06-02 10:24:41');
INSERT INTO `ask` VALUES ('9', 'u-1705270442206814', 'cv-001', '老师 第二章我还不太懂', '2017-06-02 11:32:11');
INSERT INTO `ask` VALUES ('10', 'u-1705270442206814', 'cv-001', '大熊是煞笔', '2017-06-03 21:41:06');

-- ----------------------------
-- Table structure for `assignment`
-- ----------------------------
DROP TABLE IF EXISTS `assignment`;
CREATE TABLE `assignment` (
  `id` varchar(255) NOT NULL COMMENT '作业id',
  `name` varchar(255) DEFAULT NULL COMMENT '作业名称',
  `fromId` varchar(255) DEFAULT NULL COMMENT '学生id',
  `fileUrl` text COMMENT '文件路径',
  `score` varchar(255) DEFAULT NULL COMMENT '作业得分',
  `addTime` datetime DEFAULT NULL COMMENT '添加时间',
  `courseVideoId` varchar(255) DEFAULT NULL COMMENT '视频课程id',
  PRIMARY KEY (`id`),
  KEY `assignment` (`fromId`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of assignment
-- ----------------------------
INSERT INTO `assignment` VALUES ('a-1706010954410583', 'bgp.jpg', '111', '111/170601095441/bgp.jpg', null, '2017-06-01 21:54:40', 'cv-001');

-- ----------------------------
-- Table structure for `category`
-- ----------------------------
DROP TABLE IF EXISTS `category`;
CREATE TABLE `category` (
  `id` varchar(255) NOT NULL COMMENT '分类id',
  `name` varchar(255) DEFAULT NULL COMMENT '类别名称',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of category
-- ----------------------------
INSERT INTO `category` VALUES ('1001', '法学基本理论');
INSERT INTO `category` VALUES ('1002', '宪法');
INSERT INTO `category` VALUES ('1003', '国际法');
INSERT INTO `category` VALUES ('1004', '法制史');
INSERT INTO `category` VALUES ('1005', '法律逻辑学');
INSERT INTO `category` VALUES ('1006', '刑法');
INSERT INTO `category` VALUES ('1007', '民法');
INSERT INTO `category` VALUES ('1008', '商法');
INSERT INTO `category` VALUES ('1009', '经济法');
INSERT INTO `category` VALUES ('1010', '环境保护法');
INSERT INTO `category` VALUES ('1011', '行政法');
INSERT INTO `category` VALUES ('1012', '诉讼法');
INSERT INTO `category` VALUES ('2001', '执法法务');
INSERT INTO `category` VALUES ('2002', '司法法务');
INSERT INTO `category` VALUES ('2003', '立法法务');
INSERT INTO `category` VALUES ('2004', '政府法务');
INSERT INTO `category` VALUES ('2005', '公安实务');
INSERT INTO `category` VALUES ('2006', '法官实务');
INSERT INTO `category` VALUES ('2007', '检察官实务');
INSERT INTO `category` VALUES ('2008', '律师实务公证实务');
INSERT INTO `category` VALUES ('2009', '立法实务');

-- ----------------------------
-- Table structure for `course_practice`
-- ----------------------------
DROP TABLE IF EXISTS `course_practice`;
CREATE TABLE `course_practice` (
  `id` varchar(50) NOT NULL COMMENT '实践课程id',
  `name` varchar(255) DEFAULT NULL COMMENT '课程名',
  `content` longtext COMMENT '课程内容',
  `categoryId` varchar(255) DEFAULT NULL COMMENT '分类id',
  `addTime` datetime DEFAULT NULL COMMENT '发布时间',
  `url` varchar(255) DEFAULT NULL COMMENT '课程图片',
  PRIMARY KEY (`id`),
  KEY `category1` (`categoryId`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of course_practice
-- ----------------------------
INSERT INTO `course_practice` VALUES ('cp-001', '检察官实务第一章', '本课程为法制史第一章，主要讲述了法制史的简介，通常将法律史等同于法制史，但现今中国的法学学科划分认为，法律史大于法制史，而法律史包括制度史和思想史。国内的硕士点和博士点一般是法律史而不是法制史。\r\n20世纪90年代之前，国内硕士点和博士点通常是法制史的学科点，但是随着世纪末和新世纪的到来。各大重点高校和五大政法院校（现为四个）都认识到这个问题，所以纷纷将硕士点改为法律史，方向包括中国法制史、外国法制史（主要是西方法制史）、中国传统法律文化、西方法律思想史（与法理学交叉）等等。\r\n但是，在本科的教学中，依旧是分成中国法制史和外国法制史；而不称为法律史。', '2007', '2017-05-16 09:41:14', 'resources/urlimg/fazhishi.jpg');
INSERT INTO `course_practice` VALUES ('cp-002', '检察官实务第二章', '本课程为法制史第一章，主要讲述了法制史的简介，通常将法律史等同于法制史，但现今中国的法学学科划分认为，法律史大于法制史，而法律史包括制度史和思想史。国内的硕士点和博士点一般是法律史而不是法制史。\r\n20世纪90年代之前，国内硕士点和博士点通常是法制史的学科点，但是随着世纪末和新世纪的到来。各大重点高校和五大政法院校（现为四个）都认识到这个问题，所以纷纷将硕士点改为法律史，方向包括中国法制史、外国法制史（主要是西方法制史）、中国传统法律文化、西方法律思想史（与法理学交叉）等等。\r\n但是，在本科的教学中，依旧是分成中国法制史和外国法制史；而不称为法律史。', '2007', '2017-05-16 09:42:41', 'resources/urlimg/fazhishi.jpg');
INSERT INTO `course_practice` VALUES ('cp-005', '法官实务第一章', '刑法是规定犯罪、刑事责任和刑罚的法律[1]  ，是掌握政权的统治阶级为了维护本阶级政治上的统治和经济上的利益，根据自己的意志，规定哪些行为是犯罪并且应当负何种刑事责任 ，并给予犯罪嫌疑人何种刑事处罚的法律规范的总称。\r\n刑法有广义与狭义刑法之分。广义刑法是一切刑事法律规范的总称，狭义刑法仅指刑法典，在我国即《中华人民共和国刑法》。[1]  与广义刑法、狭义刑法相联系的，刑法还可区分为普通刑法和特别刑法。普通刑法指具有普遍使用效力的刑法，实际上即指刑法典。特别刑法指仅使用于特定的人、时、地、事（犯罪）的刑法。在我国，也叫单行刑法和附属刑法。\r\n2015年8月29日，十二届全国人大常委会十六次会议表决通过刑法修正案（九）。[2]  修改后的刑法自2015年11月1日开始施行。这也是继1997年全面修订刑法后通过的第九个刑法修正案。', '2006', '2017-05-16 09:46:45', 'resources/urlimg/xingfa.jpg');
INSERT INTO `course_practice` VALUES ('cp-006', '法官实务第二章', '刑法是规定犯罪、刑事责任和刑罚的法律[1]  ，是掌握政权的统治阶级为了维护本阶级政治上的统治和经济上的利益，根据自己的意志，规定哪些行为是犯罪并且应当负何种刑事责任 ，并给予犯罪嫌疑人何种刑事处罚的法律规范的总称。\r\n刑法有广义与狭义刑法之分。广义刑法是一切刑事法律规范的总称，狭义刑法仅指刑法典，在我国即《中华人民共和国刑法》。[1]  与广义刑法、狭义刑法相联系的，刑法还可区分为普通刑法和特别刑法。普通刑法指具有普遍使用效力的刑法，实际上即指刑法典。特别刑法指仅使用于特定的人、时、地、事（犯罪）的刑法。在我国，也叫单行刑法和附属刑法。\r\n2015年8月29日，十二届全国人大常委会十六次会议表决通过刑法修正案（九）。[2]  修改后的刑法自2015年11月1日开始施行。这也是继1997年全面修订刑法后通过的第九个刑法修正案。', '2006', '2017-05-16 09:48:00', 'resources/urlimg/xingfa.jpg');
INSERT INTO `course_practice` VALUES ('cp-007', '政府法务第一章', '执法，亦称法律执行，是指国家行政机关依照法定职权和法定程序，行使行政管理职权、履行职责、贯彻和实施法律的活动。执法是实施法律的活动，必须是在这个大前提下发生的由于执行人员本身或其他原因引发的非法操作才能在执法前冠名如：暴力执法、违规执法等。这是因为：首先在现代社会为了避免混乱，大量法律的内容是有关个方面社会生活的组织与管理，从经济到政治，从卫生到教育，从公民的出生到公民的死亡，无不需有法可依，；其次，根据法治原则，为了防止行政专横，行政机关的活动必须严格按照立法机关根据民意和理性事先制定的法律来进行。', '2004', '2017-05-16 09:49:38', 'resources/urlimg/law.jpg');
INSERT INTO `course_practice` VALUES ('cp-008', '政府法务第二章', '执法，亦称法律执行，是指国家行政机关依照法定职权和法定程序，行使行政管理职权、履行职责、贯彻和实施法律的活动。执法是实施法律的活动，必须是在这个大前提下发生的由于执行人员本身或其他原因引发的非法操作才能在执法前冠名如：暴力执法、违规执法等。这是因为：首先在现代社会为了避免混乱，大量法律的内容是有关个方面社会生活的组织与管理，从经济到政治，从卫生到教育，从公民的出生到公民的死亡，无不需有法可依，；其次，根据法治原则，为了防止行政专横，行政机关的活动必须严格按照立法机关根据民意和理性事先制定的法律来进行。', '2004', '2017-05-16 09:50:07', 'resources/urlimg/law.jpg');
INSERT INTO `course_practice` VALUES ('cp-009', '公安实务第一章', '当下，尚不健全的刑事司法制度和公安工作环境造成的冤假错案发生问题日益受到刑事法律工作者和广大社会公众的重视。从公安基础工作规范的角度完善公安侦查工作制度无疑对防止错案的发生起到重大作用。《公安侦查基础工作实务》由马海舰主编，本书分十六章介绍各项公安基础工作的概念、性质、特点、工作内容和要求、当前问题及发展目标等内容，全面收集各地最新的典型工作模式，深入探讨了各项公安基础工作的可行性方法，对公安实务有较好的指导作用，也可以作为高校刑事侦查专业教材使用。 [1] ', '2005', '2017-05-16 09:51:35', 'resources/urlimg/law.jpg');
INSERT INTO `course_practice` VALUES ('cp-010', '公安实务第二章', '当下，尚不健全的刑事司法制度和公安工作环境造成的冤假错案发生问题日益受到刑事法律工作者和广大社会公众的重视。从公安基础工作规范的角度完善公安侦查工作制度无疑对防止错案的发生起到重大作用。《公安侦查基础工作实务》由马海舰主编，本书分十六章介绍各项公安基础工作的概念、性质、特点、工作内容和要求、当前问题及发展目标等内容，全面收集各地最新的典型工作模式，深入探讨了各项公安基础工作的可行性方法，对公安实务有较好的指导作用，也可以作为高校刑事侦查专业教材使用。 [1] ', '2005', '2017-05-16 09:52:01', 'resources/urlimg/law.jpg');

-- ----------------------------
-- Table structure for `course_theory`
-- ----------------------------
DROP TABLE IF EXISTS `course_theory`;
CREATE TABLE `course_theory` (
  `id` varchar(50) NOT NULL COMMENT '课程id',
  `name` varchar(255) DEFAULT NULL COMMENT '课程名称',
  `content` longtext COMMENT '课程内容',
  `categoryId` varchar(255) DEFAULT NULL COMMENT '课程分类（）',
  `addTime` datetime DEFAULT NULL COMMENT '创建时间',
  `url` varchar(255) DEFAULT 'resources/urlimg/law.jpg' COMMENT '课程图片',
  PRIMARY KEY (`id`),
  KEY `category2` (`categoryId`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of course_theory
-- ----------------------------
INSERT INTO `course_theory` VALUES ('ct-001', '法制史第一章', '本课程为法制史第一章，主要讲述了法制史的简介，通常将法律史等同于法制史，但现今中国的法学学科划分认为，法律史大于法制史，而法律史包括制度史和思想史。国内的硕士点和博士点一般是法律史而不是法制史。\n20世纪90年代之前，国内硕士点和博士点通常是法制史的学科点，但是随着世纪末和新世纪的到来。各大重点高校和五大政法院校（现为四个）都认识到这个问题，所以纷纷将硕士点改为法律史，方向包括中国法制史、外国法制史（主要是西方法制史）、中国传统法律文化、西方法律思想史（与法理学交叉）等等。\n但是，在本科的教学中，依旧是分成中国法制史和外国法制史；而不称为法律史。', '1004', '2017-05-16 09:41:14', 'resources/urlimg/fazhishi.jpg');
INSERT INTO `course_theory` VALUES ('ct-002', '法制史第二章', '本课程为法制史第一章，主要讲述了法制史的简介，通常将法律史等同于法制史，但现今中国的法学学科划分认为，法律史大于法制史，而法律史包括制度史和思想史。国内的硕士点和博士点一般是法律史而不是法制史。\r\n20世纪90年代之前，国内硕士点和博士点通常是法制史的学科点，但是随着世纪末和新世纪的到来。各大重点高校和五大政法院校（现为四个）都认识到这个问题，所以纷纷将硕士点改为法律史，方向包括中国法制史、外国法制史（主要是西方法制史）、中国传统法律文化、西方法律思想史（与法理学交叉）等等。\r\n但是，在本科的教学中，依旧是分成中国法制史和外国法制史；而不称为法律史。', '1004', '2017-05-16 09:42:41', 'resources/urlimg/fazhishi.jpg');
INSERT INTO `course_theory` VALUES ('ct-003', '法律逻辑学第一章', '法律逻辑，更早的渊源，可以追溯到智者，可以认为他们是天生的法律人，且以逻辑为主要学术工具。第一个智者普罗塔哥拉，他不仅为一个城邦立法，他还亲自教授学生以逻辑方法（按 罗素 的说法，“智者”差不多就是教授的意思）。一个流传广泛且至今难解的悖论是普罗塔哥拉悖论，这是一个编造的故事，在罗马时代的《阿提卡之夜》里有记载，大意是说，他告他的学生不交学费，但是由于口头合同有约定：“在学生第一次胜诉的时候才交纳欠缴的学费”，结果师生产生了不同解释。其他智者更多的贡献是培养法律方面的学生。稍后，苏格拉底在价值分析方面，对现今的很多 法理学 术语进行了逻辑研究，他使用的问答法影响了美国现代的案例教学法，一般我们不得不称其为苏格拉底教学法。苏格拉底的弟子 柏拉图 贡献了 辩证法 ，其实也不过是定义和划分技术的发展而已，柏拉图对 法学 的贡献显然是 希腊 最伟大的贡献之一。 亚里士多德 完成了 逻辑学 的集大成，同时他也是法学上的伟大贡献者，他的三段论直接产生了近代的司法三段论，他的《修辞学》比较成功地启示了人们对法庭辩论的研究。\r\n真正的法律逻辑，应当是在罗马时期，几乎所有健全的法律概念和法律技术都成就于罗马时代。这是它影响世界的超文化因素。\r\n近代成文法主义非常推崇法律逻辑，但是他们研究的是司法格式，而不是具体的法律技术。\r\n法律逻辑学在中国的兴起，是20世纪70年代末80年代初期的事情。1983年9月，中国法律逻辑研究会正式成立（1993年更名为中国逻辑学会法律逻辑专业委员会）。首任会长是法学家李光灿先生，首任名誉会长是著名法学家张友渔先生。', '1005', '2017-05-16 09:43:38', 'resources/urlimg/luojixue.jpg');
INSERT INTO `course_theory` VALUES ('ct-005', '刑法第一章', '刑法是规定犯罪、刑事责任和刑罚的法律[1]  ，是掌握政权的统治阶级为了维护本阶级政治上的统治和经济上的利益，根据自己的意志，规定哪些行为是犯罪并且应当负何种刑事责任 ，并给予犯罪嫌疑人何种刑事处罚的法律规范的总称。\r\n刑法有广义与狭义刑法之分。广义刑法是一切刑事法律规范的总称，狭义刑法仅指刑法典，在我国即《中华人民共和国刑法》。[1]  与广义刑法、狭义刑法相联系的，刑法还可区分为普通刑法和特别刑法。普通刑法指具有普遍使用效力的刑法，实际上即指刑法典。特别刑法指仅使用于特定的人、时、地、事（犯罪）的刑法。在我国，也叫单行刑法和附属刑法。\r\n2015年8月29日，十二届全国人大常委会十六次会议表决通过刑法修正案（九）。[2]  修改后的刑法自2015年11月1日开始施行。这也是继1997年全面修订刑法后通过的第九个刑法修正案。', '1006', '2017-05-16 09:46:45', 'resources/urlimg/xingfa.jpg');
INSERT INTO `course_theory` VALUES ('ct-006', '刑法第二章', '刑法是规定犯罪、刑事责任和刑罚的法律[1]  ，是掌握政权的统治阶级为了维护本阶级政治上的统治和经济上的利益，根据自己的意志，规定哪些行为是犯罪并且应当负何种刑事责任 ，并给予犯罪嫌疑人何种刑事处罚的法律规范的总称。\r\n刑法有广义与狭义刑法之分。广义刑法是一切刑事法律规范的总称，狭义刑法仅指刑法典，在我国即《中华人民共和国刑法》。[1]  与广义刑法、狭义刑法相联系的，刑法还可区分为普通刑法和特别刑法。普通刑法指具有普遍使用效力的刑法，实际上即指刑法典。特别刑法指仅使用于特定的人、时、地、事（犯罪）的刑法。在我国，也叫单行刑法和附属刑法。\r\n2015年8月29日，十二届全国人大常委会十六次会议表决通过刑法修正案（九）。[2]  修改后的刑法自2015年11月1日开始施行。这也是继1997年全面修订刑法后通过的第九个刑法修正案。', '1006', '2017-05-16 09:48:00', 'resources/urlimg/xingfa.jpg');

-- ----------------------------
-- Table structure for `course_video`
-- ----------------------------
DROP TABLE IF EXISTS `course_video`;
CREATE TABLE `course_video` (
  `id` varchar(50) NOT NULL COMMENT '视频课程id',
  `name` varchar(255) DEFAULT NULL COMMENT '课程名',
  `teacherId` varchar(255) DEFAULT NULL COMMENT '教师id',
  `videoUrl` text COMMENT '视频url',
  `categoryId` varchar(255) DEFAULT NULL COMMENT '视频课程分类',
  `description` text COMMENT '描述',
  `addTime` datetime DEFAULT NULL,
  `assignmentTask` text COMMENT '作业任务',
  `url` varchar(255) DEFAULT 'resources/urlimg/law.jpg' COMMENT '图片',
  PRIMARY KEY (`id`),
  KEY `category` (`categoryId`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of course_video
-- ----------------------------
INSERT INTO `course_video` VALUES ('cv-001', '法制史第一章', 'u-1705091101562789', 'http://o6t8yuguz.bkt.clouddn.com/law.swf', '1004', '本课程为法制史第一章，主要讲述了法制史的简介，通常将法律史等同于法制史，但现今中国的法学学科划分认为，法律史大于法制史，而法律史包括制度史和思想史。国内的硕士点和博士点一般是法律史而不是法制史。\r\n20世纪90年代之前，国内硕士点和博士点通常是法制史的学科点，但是随着世纪末和新世纪的到来。各大重点高校和五大政法院校（现为四个）都认识到这个问题，所以纷纷将硕士点改为法律史，方向包括中国法制史、外国法制史（主要是西方法制史）、中国传统法律文化、西方法律思想史（与法理学交叉）等等。\r\n但是，在本科的教学中，依旧是分成中国法制史和外国法制史；而不称为法律史。', '2017-05-16 09:41:14', '完成课后题第一题', 'resources/urlimg/law.jpg');
INSERT INTO `course_video` VALUES ('cv-002', '法制史第二章', 'u-1705091101562789', 'http://o6t8yuguz.bkt.clouddn.com/law.swf', '1004', '本课程为法制史第一章，主要讲述了法制史的简介，通常将法律史等同于法制史，但现今中国的法学学科划分认为，法律史大于法制史，而法律史包括制度史和思想史。国内的硕士点和博士点一般是法律史而不是法制史。\r\n20世纪90年代之前，国内硕士点和博士点通常是法制史的学科点，但是随着世纪末和新世纪的到来。各大重点高校和五大政法院校（现为四个）都认识到这个问题，所以纷纷将硕士点改为法律史，方向包括中国法制史、外国法制史（主要是西方法制史）、中国传统法律文化、西方法律思想史（与法理学交叉）等等。\r\n但是，在本科的教学中，依旧是分成中国法制史和外国法制史；而不称为法律史。', '2017-05-16 09:42:41', '完成课后题第一题', 'resources/urlimg/law.jpg');
INSERT INTO `course_video` VALUES ('cv-005', '刑法第一章', 'u-1705091101562789', 'http://o6t8yuguz.bkt.clouddn.com/law.swf', '1006', '刑法是规定犯罪、刑事责任和刑罚的法律[1]  ，是掌握政权的统治阶级为了维护本阶级政治上的统治和经济上的利益，根据自己的意志，规定哪些行为是犯罪并且应当负何种刑事责任 ，并给予犯罪嫌疑人何种刑事处罚的法律规范的总称。\r\n刑法有广义与狭义刑法之分。广义刑法是一切刑事法律规范的总称，狭义刑法仅指刑法典，在我国即《中华人民共和国刑法》。[1]  与广义刑法、狭义刑法相联系的，刑法还可区分为普通刑法和特别刑法。普通刑法指具有普遍使用效力的刑法，实际上即指刑法典。特别刑法指仅使用于特定的人、时、地、事（犯罪）的刑法。在我国，也叫单行刑法和附属刑法。\r\n2015年8月29日，十二届全国人大常委会十六次会议表决通过刑法修正案（九）。[2]  修改后的刑法自2015年11月1日开始施行。这也是继1997年全面修订刑法后通过的第九个刑法修正案。', '2017-05-16 09:46:45', '完成课后题第一题', 'resources/urlimg/law.jpg');
INSERT INTO `course_video` VALUES ('cv-006', '刑法第二章', 'u-1705091101562789', 'http://o6t8yuguz.bkt.clouddn.com/law.swf', '1006', '刑法是规定犯罪、刑事责任和刑罚的法律[1]  ，是掌握政权的统治阶级为了维护本阶级政治上的统治和经济上的利益，根据自己的意志，规定哪些行为是犯罪并且应当负何种刑事责任 ，并给予犯罪嫌疑人何种刑事处罚的法律规范的总称。\r\n刑法有广义与狭义刑法之分。广义刑法是一切刑事法律规范的总称，狭义刑法仅指刑法典，在我国即《中华人民共和国刑法》。[1]  与广义刑法、狭义刑法相联系的，刑法还可区分为普通刑法和特别刑法。普通刑法指具有普遍使用效力的刑法，实际上即指刑法典。特别刑法指仅使用于特定的人、时、地、事（犯罪）的刑法。在我国，也叫单行刑法和附属刑法。\r\n2015年8月29日，十二届全国人大常委会十六次会议表决通过刑法修正案（九）。[2]  修改后的刑法自2015年11月1日开始施行。这也是继1997年全面修订刑法后通过的第九个刑法修正案。', '2017-05-16 09:48:00', '完成课后题第一题', 'resources/urlimg/law.jpg');
INSERT INTO `course_video` VALUES ('cv-007', '政府法务第一章', 'u-1705091101562789', 'http://o6t8yuguz.bkt.clouddn.com/law.swf', '2004', '执法，亦称法律执行，是指国家行政机关依照法定职权和法定程序，行使行政管理职权、履行职责、贯彻和实施法律的活动。执法是实施法律的活动，必须是在这个大前提下发生的由于执行人员本身或其他原因引发的非法操作才能在执法前冠名如：暴力执法、违规执法等。这是因为：首先在现代社会为了避免混乱，大量法律的内容是有关个方面社会生活的组织与管理，从经济到政治，从卫生到教育，从公民的出生到公民的死亡，无不需有法可依，；其次，根据法治原则，为了防止行政专横，行政机关的活动必须严格按照立法机关根据民意和理性事先制定的法律来进行。', '2017-05-16 09:49:38', '完成课后题第一题', 'resources/urlimg/law.jpg');
INSERT INTO `course_video` VALUES ('cv-008', '政府法务第二章', 'u-1705091101562789', 'http://o6t8yuguz.bkt.clouddn.com/law.swf', '2004', '执法，亦称法律执行，是指国家行政机关依照法定职权和法定程序，行使行政管理职权、履行职责、贯彻和实施法律的活动。执法是实施法律的活动，必须是在这个大前提下发生的由于执行人员本身或其他原因引发的非法操作才能在执法前冠名如：暴力执法、违规执法等。这是因为：首先在现代社会为了避免混乱，大量法律的内容是有关个方面社会生活的组织与管理，从经济到政治，从卫生到教育，从公民的出生到公民的死亡，无不需有法可依，；其次，根据法治原则，为了防止行政专横，行政机关的活动必须严格按照立法机关根据民意和理性事先制定的法律来进行。', '2017-05-16 09:50:07', '完成课后题第一题', 'resources/urlimg/law.jpg');
INSERT INTO `course_video` VALUES ('cv-009', '公安实务第一章', 'u-1705091101562789', 'http://o6t8yuguz.bkt.clouddn.com/law.swf', '2005', '当下，尚不健全的刑事司法制度和公安工作环境造成的冤假错案发生问题日益受到刑事法律工作者和广大社会公众的重视。从公安基础工作规范的角度完善公安侦查工作制度无疑对防止错案的发生起到重大作用。《公安侦查基础工作实务》由马海舰主编，本书分十六章介绍各项公安基础工作的概念、性质、特点、工作内容和要求、当前问题及发展目标等内容，全面收集各地最新的典型工作模式，深入探讨了各项公安基础工作的可行性方法，对公安实务有较好的指导作用，也可以作为高校刑事侦查专业教材使用。 [1] ', '2017-05-16 09:51:35', '完成课后题第一题', 'resources/urlimg/law.jpg');
INSERT INTO `course_video` VALUES ('cv-010', '公安实务第二章', 'u-1705091101562789', 'http://o6t8yuguz.bkt.clouddn.com/law.swf', '2005', '当下，尚不健全的刑事司法制度和公安工作环境造成的冤假错案发生问题日益受到刑事法律工作者和广大社会公众的重视。从公安基础工作规范的角度完善公安侦查工作制度无疑对防止错案的发生起到重大作用。《公安侦查基础工作实务》由马海舰主编，本书分十六章介绍各项公安基础工作的概念、性质、特点、工作内容和要求、当前问题及发展目标等内容，全面收集各地最新的典型工作模式，深入探讨了各项公安基础工作的可行性方法，对公安实务有较好的指导作用，也可以作为高校刑事侦查专业教材使用。 [1] ', '2017-05-16 09:52:01', '完成课后题第一题', 'resources/urlimg/law.jpg');

-- ----------------------------
-- Table structure for `exam_list`
-- ----------------------------
DROP TABLE IF EXISTS `exam_list`;
CREATE TABLE `exam_list` (
  `id` varchar(255) NOT NULL COMMENT '考试id',
  `name` varchar(255) DEFAULT NULL COMMENT '考试名称',
  `examStatus` varchar(255) DEFAULT NULL COMMENT '考试状态',
  `certificateId` varchar(255) DEFAULT NULL COMMENT '证书id',
  `certificateName` varchar(255) DEFAULT NULL COMMENT '证书名称',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of exam_list
-- ----------------------------
INSERT INTO `exam_list` VALUES ('e001', '一级律师考试', null, 'c001', '一级律师');

-- ----------------------------
-- Table structure for `exam_question`
-- ----------------------------
DROP TABLE IF EXISTS `exam_question`;
CREATE TABLE `exam_question` (
  `id` varchar(255) NOT NULL COMMENT '考试题目id',
  `examId` varchar(255) NOT NULL COMMENT '考试id',
  `no` varchar(255) DEFAULT NULL COMMENT '题号',
  `question` text COMMENT '问题',
  `answera` text COMMENT '答案A',
  `answerb` text COMMENT '答案B',
  `answerc` text COMMENT '答案C',
  `answerd` text COMMENT '答案D',
  `answer` varchar(255) DEFAULT NULL COMMENT '正确答案(A,B,C,D)',
  PRIMARY KEY (`id`),
  KEY `examid` (`examId`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of exam_question
-- ----------------------------
INSERT INTO `exam_question` VALUES ('q-1706021027178244', 'e001', '2', '中国最长的运河是', '大运河', '小运河', '中运河', '京杭大运河', 'd');
INSERT INTO `exam_question` VALUES ('q-1706021027539571', 'e001', '3', '新中国成立于', '1999', '1992', '1949', '1222', 'c');
INSERT INTO `exam_question` VALUES ('q-1706021028246159', 'e001', '4', '河北工业大学位于', '北辰', '南开', '和平', '北京', 'a');
INSERT INTO `exam_question` VALUES ('q-1706021028582856', 'e001', '5', '抗日战争打的是', '美国', '英国', '日本', '法国', 'c');
INSERT INTO `exam_question` VALUES ('q-1706021029001074', 'e001', '6', '我是什么时候答辩', '上午', '下午', '中午', '晚上', 'b');
INSERT INTO `exam_question` VALUES ('q-1706021056176003', 'e001', '7', '我国有多少个民族', '56', '123', '12', '213241', 'a');
INSERT INTO `exam_question` VALUES ('q001', 'e001', '1', '中国的首都是哪里？', '北京', '长沙', '上海', '开封', 'a');

-- ----------------------------
-- Table structure for `info_lawyer`
-- ----------------------------
DROP TABLE IF EXISTS `info_lawyer`;
CREATE TABLE `info_lawyer` (
  `id` varchar(255) NOT NULL COMMENT '律师用户id',
  `userName` varchar(255) DEFAULT NULL COMMENT '律师姓名',
  `url` varchar(255) DEFAULT NULL COMMENT '律师照片',
  `description` varchar(255) DEFAULT NULL COMMENT '介绍',
  `honor` varchar(255) DEFAULT NULL COMMENT '业绩',
  `password` varchar(255) DEFAULT NULL,
  `mobile` varchar(255) DEFAULT NULL,
  `idCard` varchar(255) DEFAULT NULL,
  `checkMark` varchar(255) DEFAULT NULL,
  `addTime` datetime DEFAULT NULL,
  `role` varchar(255) DEFAULT NULL,
  `note` varchar(255) DEFAULT NULL,
  `gender` varchar(255) DEFAULT NULL,
  `birth` date DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of info_lawyer
-- ----------------------------

-- ----------------------------
-- Table structure for `info_teacher`
-- ----------------------------
DROP TABLE IF EXISTS `info_teacher`;
CREATE TABLE `info_teacher` (
  `id` varchar(255) NOT NULL COMMENT '教师id',
  `userName` varchar(255) DEFAULT NULL COMMENT '教师姓名',
  `major` varchar(255) DEFAULT NULL COMMENT '主讲课程',
  `teachLevel` varchar(2) DEFAULT '2' COMMENT '教师职称（0：教授；1：副教授；2：讲师；）',
  `picUrl` text COMMENT '教师照片',
  `honor` text COMMENT '所获荣誉',
  `workList` text COMMENT '著作列表',
  `password` varchar(255) DEFAULT NULL,
  `mobile` varchar(255) DEFAULT NULL,
  `idCard` varchar(255) DEFAULT NULL,
  `checkMark` varchar(255) DEFAULT NULL,
  `addTime` varchar(255) DEFAULT NULL,
  `role` varchar(255) DEFAULT NULL,
  `note` varchar(255) DEFAULT NULL,
  `gender` varchar(255) DEFAULT NULL,
  `birth` date DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of info_teacher
-- ----------------------------
INSERT INTO `info_teacher` VALUES ('u-1705091101562789', '左志伟', '计算机', '2', null, '一等奖', null, null, null, null, null, null, null, null, null, null);
INSERT INTO `info_teacher` VALUES ('u-1706231014406646', '左志伟', null, '2', null, null, null, '123456', '13503139731', '130999999999999999', null, '2017-06-23 10:14:44', '2', null, '0', '2017-06-14');

-- ----------------------------
-- Table structure for `menu`
-- ----------------------------
DROP TABLE IF EXISTS `menu`;
CREATE TABLE `menu` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `menuName` varchar(255) DEFAULT NULL,
  `menuPage` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of menu
-- ----------------------------
INSERT INTO `menu` VALUES ('1', '用户管理', 'user.jsp');
INSERT INTO `menu` VALUES ('2', '课程类别管理', 'courseCategory.jsp');
INSERT INTO `menu` VALUES ('3', '理论课程管理', 'courseTheory.jsp');
INSERT INTO `menu` VALUES ('4', '实践课程管理', 'coursePractice.jsp');
INSERT INTO `menu` VALUES ('5', '视频课程管理', 'courseVideo.jsp');
INSERT INTO `menu` VALUES ('6', '作业管理', 'assignment.jsp');
INSERT INTO `menu` VALUES ('7', '课堂提问管理', 'ask.jsp');
INSERT INTO `menu` VALUES ('8', '考试列表管理', 'exam.jsp');
INSERT INTO `menu` VALUES ('9', '考试试题管理', 'examQuestionList.jsp');
INSERT INTO `menu` VALUES ('10', '考试成绩管理', 'examUserList.jsp');
INSERT INTO `menu` VALUES ('11', '社会咨询管理', 'message.jsp');
INSERT INTO `menu` VALUES ('12', '作品管理', 'article.jsp');
INSERT INTO `menu` VALUES ('13', '项目管理', 'project.jsp');
INSERT INTO `menu` VALUES ('14', '菜单管理', 'menu.jsp');
INSERT INTO `menu` VALUES ('15', '系统日志', 'log.jsp');

-- ----------------------------
-- Table structure for `message`
-- ----------------------------
DROP TABLE IF EXISTS `message`;
CREATE TABLE `message` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '留言',
  `fromId` varchar(255) DEFAULT NULL COMMENT '留言学生id',
  `toId` varchar(255) DEFAULT NULL COMMENT '目标id',
  `content` text COMMENT '留言内容',
  `addTime` datetime DEFAULT NULL COMMENT '留言时间',
  `parentId` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `message1` (`fromId`) USING BTREE,
  KEY `message2` (`toId`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of message
-- ----------------------------

-- ----------------------------
-- Table structure for `news`
-- ----------------------------
DROP TABLE IF EXISTS `news`;
CREATE TABLE `news` (
  `id` varchar(255) NOT NULL DEFAULT '',
  `title` varchar(255) DEFAULT NULL,
  `content` varchar(255) DEFAULT NULL,
  `addtime` date DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of news
-- ----------------------------

-- ----------------------------
-- Table structure for `project`
-- ----------------------------
DROP TABLE IF EXISTS `project`;
CREATE TABLE `project` (
  `id` varchar(255) NOT NULL COMMENT '科研项目id',
  `projectName` varchar(255) DEFAULT NULL COMMENT '项目名称',
  `description` text COMMENT '项目描述',
  `addUser` varchar(255) DEFAULT NULL COMMENT '发起人',
  `beginTime` date DEFAULT NULL COMMENT '开始日期',
  `endTime` date DEFAULT NULL COMMENT '结束日期',
  `addTime` date DEFAULT NULL COMMENT '添加时间',
  `status` varchar(2) DEFAULT '0' COMMENT '0-报名中；1-进行中；2-已结项',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of project
-- ----------------------------
INSERT INTO `project` VALUES ('p-1705251256485304', '111', '111', '111', '2015-11-21', '2015-11-22', '2017-05-25', '1');

-- ----------------------------
-- Table structure for `project_user`
-- ----------------------------
DROP TABLE IF EXISTS `project_user`;
CREATE TABLE `project_user` (
  `id` varchar(255) NOT NULL COMMENT '项目用户id',
  `userId` varchar(255) DEFAULT NULL COMMENT '用户id',
  `projectId` varchar(255) DEFAULT NULL COMMENT '项目id',
  `checkStatus` varchar(2) DEFAULT '0' COMMENT '0-待审核；1-审核通过；2-不通过',
  `checkOpinion` varchar(255) DEFAULT '待审核' COMMENT '审核意见',
  PRIMARY KEY (`id`),
  KEY `projectuserid` (`userId`) USING BTREE,
  KEY `projectid` (`projectId`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of project_user
-- ----------------------------

-- ----------------------------
-- Table structure for `sys_log`
-- ----------------------------
DROP TABLE IF EXISTS `sys_log`;
CREATE TABLE `sys_log` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `userId` varchar(255) DEFAULT NULL,
  `info` varchar(255) DEFAULT NULL,
  `addTime` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of sys_log
-- ----------------------------
INSERT INTO `sys_log` VALUES ('1', 'u-1706231014406646', '注册', '2017-06-23 10:14:44');
INSERT INTO `sys_log` VALUES ('2', 'u-1706231014406646', '登录', '2017-06-23 10:15:00');
INSERT INTO `sys_log` VALUES ('3', '111', '管理员登录', '2017-06-23 10:16:31');

-- ----------------------------
-- Table structure for `user`
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `id` varchar(50) NOT NULL COMMENT '用户id，自增id',
  `userName` varchar(255) DEFAULT NULL COMMENT '用户名',
  `password` varchar(255) DEFAULT NULL COMMENT '密码',
  `mobile` varchar(50) DEFAULT NULL COMMENT '手机号',
  `idCard` varchar(50) DEFAULT NULL COMMENT '身份证号',
  `checkMark` varchar(2) DEFAULT '2' COMMENT '审核标记（0：未审核；1：未通过；2：通过）',
  `addTime` datetime DEFAULT NULL COMMENT '注册时间',
  `role` varchar(2) DEFAULT NULL COMMENT '用户类型（0：管理员；1：学生；2：教师；3：社会人士；4：律师）',
  `note` text COMMENT '备注',
  `gender` varchar(2) DEFAULT '0' COMMENT '性别（0：保密；1：男；2：女）',
  `birth` datetime DEFAULT NULL COMMENT '出生年月',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES ('111', '1111', '111', '111', '111', '2', '2017-05-02 21:22:49', '0', null, '0', '2017-05-23 21:22:55');
INSERT INTO `user` VALUES ('u-1706231014406646', '左志伟', '123456', '13503139731', '130999999999999999', '2', '2017-06-23 10:14:44', '2', null, '0', '2017-06-14 00:00:00');

-- ----------------------------
-- Table structure for `user_exam`
-- ----------------------------
DROP TABLE IF EXISTS `user_exam`;
CREATE TABLE `user_exam` (
  `userId` varchar(255) DEFAULT NULL COMMENT '用户id',
  `examId` varchar(255) DEFAULT NULL COMMENT '考试id',
  `score` varchar(255) DEFAULT NULL COMMENT '分数',
  `id` varchar(50) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `examlistid` (`examId`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of user_exam
-- ----------------------------
INSERT INTO `user_exam` VALUES ('u-1705091101562789', 'e001', '70', '');
