/*
Navicat MySQL Data Transfer

Source Server         : MySQL
Source Server Version : 50716
Source Host           : localhost:3306
Source Database       : agricul

Target Server Type    : MYSQL
Target Server Version : 50716
File Encoding         : 65001

Date: 2017-08-13 15:09:31
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `administrator`
-- ----------------------------
DROP TABLE IF EXISTS `administrator`;
CREATE TABLE `administrator` (
  `AdminID` int(10) NOT NULL AUTO_INCREMENT,
  `UserName` varchar(20) NOT NULL DEFAULT '',
  `Password` varchar(20) NOT NULL,
  `Authority` varchar(20) DEFAULT NULL,
  `Email` varchar(50) NOT NULL,
  `Phone` varchar(20) NOT NULL,
  `RegisTime` datetime NOT NULL,
  `Depict` text,
  PRIMARY KEY (`AdminID`),
  UNIQUE KEY `UserName` (`UserName`)
) ENGINE=InnoDB AUTO_INCREMENT=24 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of administrator
-- ----------------------------
INSERT INTO `administrator` VALUES ('1', 'NongXueYuan', '123456', 'farmowner', '18765013999@163.com', '18765013999', '2017-05-10 21:28:31', '这是农场主，拥有两个农场！');
INSERT INTO `administrator` VALUES ('2', 'GuangXi', '123456', 'administrator', '123454324@qq.com', '187526432445', '2017-05-04 21:31:26', null);
INSERT INTO `administrator` VALUES ('20', 'cxy', '123456', 'farmowner', '18782305888@163.com', '18782305888', '2017-06-09 21:36:52', '测试用户1');
INSERT INTO `administrator` VALUES ('21', 'chengxiangyang', '123456', 'farmowner', '16399267@163.com', '18888686998', '2017-06-10 00:53:47', '测试用户2');
INSERT INTO `administrator` VALUES ('22', 'test', '123456', 'administrator', '178806321@163.com', '18783706298', '2017-06-15 11:09:49', '测试用户一');
INSERT INTO `administrator` VALUES ('23', 'lzd', '123456', 'farmowner', 'fdaslfsal', 'fdsalfsdal', '2017-06-15 14:42:25', 'sdflkagsdl');

-- ----------------------------
-- Table structure for `admin_base`
-- ----------------------------
DROP TABLE IF EXISTS `admin_base`;
CREATE TABLE `admin_base` (
  `AdminID` varchar(20) NOT NULL DEFAULT '',
  `BaseID` varchar(20) CHARACTER SET gbk NOT NULL DEFAULT '',
  PRIMARY KEY (`AdminID`,`BaseID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of admin_base
-- ----------------------------
INSERT INTO `admin_base` VALUES ('1', 'base_101');
INSERT INTO `admin_base` VALUES ('1', 'base_102');
INSERT INTO `admin_base` VALUES ('2', 'base_101');
INSERT INTO `admin_base` VALUES ('2', 'base_102');
INSERT INTO `admin_base` VALUES ('2', 'base_103');
INSERT INTO `admin_base` VALUES ('2', 'base_104');
INSERT INTO `admin_base` VALUES ('2', 'base_105');
INSERT INTO `admin_base` VALUES ('20', 'base_101');
INSERT INTO `admin_base` VALUES ('20', 'base_102');
INSERT INTO `admin_base` VALUES ('21', 'base_101');
INSERT INTO `admin_base` VALUES ('21', 'base_102');
INSERT INTO `admin_base` VALUES ('21', 'base_103');
INSERT INTO `admin_base` VALUES ('22', 'base_101');
INSERT INTO `admin_base` VALUES ('22', 'base_102');
INSERT INTO `admin_base` VALUES ('22', 'base_103');
INSERT INTO `admin_base` VALUES ('22', 'base_104');
INSERT INTO `admin_base` VALUES ('22', 'base_105');
INSERT INTO `admin_base` VALUES ('23', 'base_101');
INSERT INTO `admin_base` VALUES ('23', 'base_102');

-- ----------------------------
-- Table structure for `base`
-- ----------------------------
DROP TABLE IF EXISTS `base`;
CREATE TABLE `base` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `BaseID` varchar(20) NOT NULL DEFAULT '',
  `BaseName` varchar(40) NOT NULL,
  `BaseImage` varchar(50) DEFAULT NULL,
  `BaseAddre` varchar(60) DEFAULT NULL,
  `AreaCount` int(11) DEFAULT NULL,
  `BaseArea` varchar(10) DEFAULT NULL,
  `Longitude` varchar(12) DEFAULT NULL,
  `Dimension` varchar(12) DEFAULT NULL,
  `Country` varchar(30) DEFAULT NULL,
  `Province` varchar(30) DEFAULT NULL,
  `City` varchar(20) DEFAULT NULL,
  `County` varchar(40) DEFAULT NULL,
  PRIMARY KEY (`ID`,`BaseID`),
  UNIQUE KEY `BaseName` (`BaseName`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of base
-- ----------------------------
INSERT INTO `base` VALUES ('1', 'base_101', '农学院水果种植基地', '/agriculture/vue/assets/img/farm/base_101.jpg', '广西大学农学院', '2', '100亩', '108.053624', '22.362322', '中国', '广西壮族自治区', '南宁市', '西乡塘区');
INSERT INTO `base` VALUES ('2', 'base_102', '武鸣水果种植基地', '/agriculture/vue/assets/img/farm/base_102.jpg', '广西南宁市武鸣区', '5', '1000亩', '107.594328', '22.582326', '中国', '广西壮族自治区', '南宁市', '武鸣区');
INSERT INTO `base` VALUES ('3', 'base_103', '西乡塘种植基地', '/agriculture/vue/assets/img/farm/base_103.jpg', '广西南宁市西乡塘区', '3', '682亩', '107.492623', '22.462403', '中国', '广西壮族自治区', '南宁市', '西乡塘区');
INSERT INTO `base` VALUES ('4', 'base_104', '广西大学种植基地', '/agriculture/vue/assets/img/farm/base_104.jpg', '广西南宁市西乡塘区', '1', '125亩', '108.053622', '22.350623', '中国', '广西壮族自治区', '南宁市', '西乡塘区');
INSERT INTO `base` VALUES ('11', 'base_105', '测试农场一', '/agriculture/vue/assets/img/farm/base_105.jpg', '广西南宁西乡塘区大学东路100号', '3', '680亩', '108.283672', '22.5968242', '中国', '广西壮族自治区', '南宁', '西乡塘区');

-- ----------------------------
-- Table structure for `environment`
-- ----------------------------
DROP TABLE IF EXISTS `environment`;
CREATE TABLE `environment` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `SensorID` varchar(20) NOT NULL DEFAULT '',
  `AirTemperature` float DEFAULT NULL,
  `AirHumidity` float DEFAULT NULL,
  `SoilTemperature` float DEFAULT NULL,
  `SoilHumidity` float DEFAULT NULL,
  `Illumination` float DEFAULT NULL,
  `RealTime` datetime NOT NULL DEFAULT '0000-00-00 00:00:00',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=77 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of environment
-- ----------------------------
INSERT INTO `environment` VALUES ('1', '010101', '32.3806', '62.7294', '23.4854', '28.5914', '1449.98', '2017-04-09 11:03:00');
INSERT INTO `environment` VALUES ('2', '010201', '31.7348', '68.4273', '25.1666', '20.9142', '1328.37', '2017-04-09 11:03:00');
INSERT INTO `environment` VALUES ('3', '010301', '38.1304', '64.5426', '29.0385', '24.3132', '7742.22', '2017-04-09 11:03:00');
INSERT INTO `environment` VALUES ('4', '010401', '32.3876', '65.7528', '23.7128', '12.3933', '1420.81', '2017-04-09 11:03:00');
INSERT INTO `environment` VALUES ('5', '010101', '32.5142', '62.3063', '23.5656', '28.6416', '1449.98', '2017-04-09 11:06:00');
INSERT INTO `environment` VALUES ('6', '010201', '31.8016', '69.0957', '25.307', '21.4156', '1352.46', '2017-04-09 11:06:00');
INSERT INTO `environment` VALUES ('7', '010301', '42.0743', '64.6986', '29.0585', '24.263', '7794.79', '2017-04-09 11:06:00');
INSERT INTO `environment` VALUES ('8', '010401', '33.1234', '68.3725', '24.0735', '13.1448', '1420.81', '2017-04-09 11:06:00');
INSERT INTO `environment` VALUES ('9', '010101', '32.9151', '60.48', '23.4654', '28.5079', '1445.6', '2017-04-09 11:09:00');
INSERT INTO `environment` VALUES ('10', '010201', '31.9687', '67.4804', '25.3471', '22.1509', '1352.46', '2017-04-09 11:09:00');
INSERT INTO `environment` VALUES ('11', '010301', '41.5395', '61.5571', '23.6045', '9.80916', '48202.9', '2017-04-09 11:09:00');
INSERT INTO `environment` VALUES ('12', '010401', '32.5883', '65.6079', '22.8711', '12.744', '1418.62', '2017-04-09 11:09:00');
INSERT INTO `environment` VALUES ('13', '010101', '32.9151', '61.7829', '23.4854', '28.5748', '5147.01', '2017-04-09 11:12:00');
INSERT INTO `environment` VALUES ('14', '010201', '32.0689', '68.717', '25.2267', '25.6271', '6550.49', '2017-04-09 11:12:00');
INSERT INTO `environment` VALUES ('15', '010301', '42.5756', '62.2144', '23.5042', '9.72562', '43855.4', '2017-04-09 11:12:00');
INSERT INTO `environment` VALUES ('16', '010401', '32.1535', '69.6433', '22.851', '12.8108', '9892.32', '2017-04-09 11:12:00');
INSERT INTO `environment` VALUES ('17', '010101', '32.9486', '63.2305', '23.4253', '28.5246', '1443.41', '2017-04-09 11:15:00');
INSERT INTO `environment` VALUES ('18', '010201', '32.0689', '69.118', '25.1866', '22.2178', '1348.08', '2017-04-09 11:15:00');
INSERT INTO `environment` VALUES ('19', '010301', '42.9098', '63.3284', '23.7449', '9.90944', '5171', '2017-04-09 11:15:00');
INSERT INTO `environment` VALUES ('20', '010401', '32.2538', '70.1227', '22.7508', '12.8442', '1423', '2017-04-09 11:15:00');
INSERT INTO `environment` VALUES ('21', '010101', '33.4163', '61.46', '23.5456', '28.5246', '1445.6', '2017-04-09 11:18:00');
INSERT INTO `environment` VALUES ('22', '010201', '32.2026', '66.2439', '25.3471', '23.2038', '1352.46', '2017-04-09 11:18:00');
INSERT INTO `environment` VALUES ('23', '010301', '41.6397', '59.3514', '23.6045', '9.87601', '4684.78', '2017-04-09 11:18:00');
INSERT INTO `environment` VALUES ('24', '010401', '32.4545', '72.0178', '22.9513', '13.0446', '1418.62', '2017-04-09 11:18:00');
INSERT INTO `environment` VALUES ('25', '010101', '33.3495', '62.2617', '23.5857', '28.5748', '1445.6', '2017-04-09 11:21:00');
INSERT INTO `environment` VALUES ('26', '010201', '32.1024', '67.4024', '25.4273', '22.2512', '1348.08', '2017-04-09 11:21:00');
INSERT INTO `environment` VALUES ('27', '010301', '42.0408', '59.9641', '23.6246', '9.8259', '1419.28', '2017-04-09 11:21:00');
INSERT INTO `environment` VALUES ('28', '010401', '32.6886', '69.6322', '22.8911', '12.9778', '1420.81', '2017-04-09 11:21:00');
INSERT INTO `environment` VALUES ('29', '010101', '33.6502', '59.4667', '23.5656', '28.5413', '1449.98', '2017-04-09 11:24:00');
INSERT INTO `environment` VALUES ('30', '010201', '32.3698', '64.8737', '25.307', '22.3515', '1350.27', '2017-04-09 11:24:00');
INSERT INTO `environment` VALUES ('31', '010301', '42.4085', '58.7944', '23.6246', '9.94287', '1425.86', '2017-04-09 11:24:00');
INSERT INTO `environment` VALUES ('32', '010401', '32.6886', '66.7226', '22.9713', '13.0613', '1416.43', '2017-04-09 11:24:00');
INSERT INTO `environment` VALUES ('33', '010101', '34.0511', '59.3887', '23.5255', '28.5413', '1445.6', '2017-04-09 11:27:00');
INSERT INTO `environment` VALUES ('34', '010201', '32.7374', '66.3775', '25.3672', '22.1677', '1354.65', '2017-04-09 11:27:00');
INSERT INTO `environment` VALUES ('35', '010301', '43.1437', '58.438', '23.9052', '10.0097', '1419.28', '2017-04-09 11:27:00');
INSERT INTO `environment` VALUES ('36', '010401', '33.2572', '70.9365', '23.1116', '13.1615', '1423', '2017-04-09 11:27:00');
INSERT INTO `environment` VALUES ('37', '010101', '34.7528', '58.6426', '23.6659', '28.6249', '1454.36', '2017-04-09 11:30:00');
INSERT INTO `environment` VALUES ('38', '010201', '32.9714', '65.6423', '25.307', '22.5019', '1356.84', '2017-04-09 11:30:00');
INSERT INTO `environment` VALUES ('39', '010301', '42.609', '55.5527', '23.785', '10.0097', '1423.67', '2017-04-09 11:30:00');
INSERT INTO `environment` VALUES ('40', '010401', '33.8593', '62.6203', '22.9913', '13.0613', '1420.81', '2017-04-09 11:30:00');
INSERT INTO `environment` VALUES ('41', '010101', '35.1537', '55.1237', '23.6659', '28.6416', '1443.41', '2017-04-09 11:33:00');
INSERT INTO `environment` VALUES ('42', '010201', '33.2722', '63.2138', '25.6078', '22.669', '1359.03', '2017-04-09 11:33:00');
INSERT INTO `environment` VALUES ('43', '010301', '42.9433', '53.1911', '23.7449', '9.95955', '1430.24', '2017-04-09 11:33:00');
INSERT INTO `environment` VALUES ('44', '010401', '34.4947', '60.1901', '23.1516', '13.0613', '1420.81', '2017-04-09 11:33:00');
INSERT INTO `environment` VALUES ('45', '010101', '35.4209', '55.313', '23.726', '28.6583', '1447.79', '2017-04-09 11:36:00');
INSERT INTO `environment` VALUES ('46', '010201', '33.339', '65.1299', '25.5677', '22.8027', '1350.27', '2017-04-09 11:36:00');
INSERT INTO `environment` VALUES ('47', '010301', '43.946', '53.7481', '23.9052', '10.0765', '1401.77', '2017-04-09 11:36:00');
INSERT INTO `environment` VALUES ('48', '010401', '35.565', '60.5691', '23.1516', '13.1281', '1423', '2017-04-09 11:36:00');
INSERT INTO `environment` VALUES ('49', '010101', '34.9197', '54.8008', '23.8062', '28.7251', '48256.3', '2017-04-09 11:39:00');
INSERT INTO `environment` VALUES ('50', '010201', '32.8377', '63.3586', '25.5276', '41.2531', '47089.8', '2017-04-09 11:39:00');
INSERT INTO `environment` VALUES ('51', '010301', '43.5449', '53.7035', '23.7047', '9.92613', '39779.6', '2017-04-09 11:39:00');
INSERT INTO `environment` VALUES ('52', '010401', '35.6988', '59.8111', '23.0915', '13.0947', '48036', '2017-04-09 11:39:00');
INSERT INTO `environment` VALUES ('53', '010101', '34.1514', '57.8297', '23.8263', '28.6416', '48387.8', '2017-04-09 11:42:00');
INSERT INTO `environment` VALUES ('54', '010201', '32.5369', '65.6758', '25.7281', '39.0471', '47411.8', '2017-04-09 11:42:00');
INSERT INTO `environment` VALUES ('55', '010301', '43.0769', '53.3916', '23.7047', '9.94287', '41139.7', '2017-04-09 11:42:00');
INSERT INTO `environment` VALUES ('56', '010401', '35.6319', '58.1501', '22.9913', '12.9778', '48123.6', '2017-04-09 11:42:00');
INSERT INTO `environment` VALUES ('57', '010101', '33.5834', '56.95', '23.7661', '28.5914', '39296.3', '2017-04-09 11:45:00');
INSERT INTO `environment` VALUES ('58', '010201', '32.1358', '65.5977', '25.5677', '30.8915', '28613.1', '2017-04-09 11:45:00');
INSERT INTO `environment` VALUES ('59', '010301', '42.4419', '54.2939', '23.785', '9.99298', '24827.5', '2017-04-09 11:45:00');
INSERT INTO `environment` VALUES ('60', '010401', '34.7623', '58.975', '23.0113', '13.0613', '41407.7', '2017-04-09 11:45:00');
INSERT INTO `environment` VALUES ('61', '010101', '33.0153', '58.5869', '23.7862', '28.6081', '42165.4', '2017-04-09 11:48:00');
INSERT INTO `environment` VALUES ('62', '010201', '31.7348', '66.489', '25.6078', '32.5795', '31901', '2017-04-09 11:48:00');
INSERT INTO `environment` VALUES ('63', '010301', '41.573', '54.4833', '23.7248', '9.95955', '30346.7', '2017-04-09 11:48:00');
INSERT INTO `environment` VALUES ('64', '010401', '34.5951', '58.3396', '23.1316', '13.0947', '44776.6', '2017-04-09 11:48:00');
INSERT INTO `environment` VALUES ('65', '010101', '33.2158', '60.6359', '23.7661', '28.792', '48223.5', '2017-04-09 11:51:00');
INSERT INTO `environment` VALUES ('66', '010201', '31.6679', '68.5387', '25.4273', '37.7101', '48174.1', '2017-04-09 11:51:00');
INSERT INTO `environment` VALUES ('67', '010301', '42.2748', '54.3162', '23.7047', '9.94287', '47287.4', '2017-04-09 11:51:00');
INSERT INTO `environment` VALUES ('68', '010401', '35.0967', '62.0406', '23.4122', '13.0947', '7604.79', '2017-04-09 11:51:00');
INSERT INTO `environment` VALUES ('69', '010101', '33.6502', '59.2551', '23.8263', '28.9758', '48241', '2017-04-09 11:54:00');
INSERT INTO `environment` VALUES ('70', '010201', '31.835', '68.3828', '25.4073', '37.6934', '44218.1', '2017-04-09 11:54:00');
INSERT INTO `environment` VALUES ('71', '010301', '42.1745', '53.2691', '23.7047', '9.94287', '38559.7', '2017-04-09 11:54:00');
INSERT INTO `environment` VALUES ('72', '010401', '35.7323', '57.6038', '23.2719', '13.1949', '48047', '2017-04-09 11:54:00');
INSERT INTO `environment` VALUES ('73', '010101', '33.4831', '57.7406', '23.8464', '28.9591', '48324.2', '2017-04-09 12:10:00');
INSERT INTO `environment` VALUES ('74', '010201', '31.835', '67.4247', '25.4874', '33.3148', '36218.4', '2017-04-09 12:10:00');
INSERT INTO `environment` VALUES ('75', '010301', '42.5756', '53.3359', '23.805', '9.99298', '31428.6', '2017-04-09 12:10:00');
INSERT INTO `environment` VALUES ('76', '010401', '35.1637', '56.333', '23.1316', '13.1281', '48016.3', '2017-04-09 12:10:00');

-- ----------------------------
-- Table structure for `fruit`
-- ----------------------------
DROP TABLE IF EXISTS `fruit`;
CREATE TABLE `fruit` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `FruitID` varchar(20) NOT NULL DEFAULT '',
  `Name` varchar(20) DEFAULT NULL,
  `Feature` text,
  `MainValue` text,
  `Depict` text,
  `maxAirTemp` float DEFAULT NULL,
  `minAirTemp` float DEFAULT NULL,
  `maxAirHumi` float DEFAULT NULL,
  `minAirHumi` float DEFAULT NULL,
  `maxLight` float DEFAULT NULL,
  `minLight` float DEFAULT NULL,
  `maxSoilTemp` float DEFAULT NULL,
  `minSoilTemp` float DEFAULT NULL,
  `maxSoilHumi` float DEFAULT NULL,
  `minSoilHumi` float DEFAULT NULL,
  PRIMARY KEY (`ID`,`FruitID`),
  UNIQUE KEY `Name` (`Name`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of fruit
-- ----------------------------
INSERT INTO `fruit` VALUES ('1', 'fruit_101', '桂味荔枝', '桂味荔枝植株高大，500年生树高16米，冠幅13米，主干周径2.3米，树皮灰褐色，较平滑，枝条疏散细长，易折断，略向上举。小叶2～3对，对生、间有互生，长椭圆形，较疏生，叶色淡绿，\r\n有光泽，长7～9厘米，宽2.5～3.8厘米，边缘向内卷，先端短尖。', null, null, '39', '-2', '60', '15', '12000', '2000', '25', '5', '60', '14');
INSERT INTO `fruit` VALUES ('2', 'fruit_102', '草莓荔枝', '荔枝的一种。该品种树冠圆头形，树姿开张；主干灰褐色、表皮质地光滑。嫩梢黄绿色；枝梢节密度中等；皮孔竖长形、密度中等而小', null, null, '38', '-2', '60', '15', '1200', '200', '25', '5', '60', '15');
INSERT INTO `fruit` VALUES ('3', 'fruit_103', '妃子笑荔枝', '妃子笑是精选的荔枝，荔枝看上去个大、饱满、颜色对比特别明显，经常是一颗荔枝上红一块绿一块的，别看整体颜色发绿，其实很甜，核很小。果实近圆形或卵圆形，果中大，单果重 23.5～31.5克，果皮淡红色、薄；果肉白蜡色，肉厚，质爽脆，多汁，味清甜带香；可食率77.1%～82.5%，可溶性固形物 17.1%～20.5%，酸含量0.23～0.34克/100毫升。', null, null, '35', '-2', '60', '15', '1200', '200', '25', '5', '60', '15');
INSERT INTO `fruit` VALUES ('4', 'fruit_104', '鸡嘴荔', '树冠半圆头形，枝条粗壮，较硬，幼树枝条稍直立，壮年树冠较开张。树干灰褐色，表面稍粗糙。叶片中等大长椭圆形，长12.5厘米，宽3.9厘米，深绿色，有光泽，嫩叶紫红色，小叶3~4对，一般3对。小叶柄较短（4毫米）。叶面平展，叶缘平整，叶尖渐尖，叶基楔形。', null, null, '35', '-2', '60', '15', '1200', '200', '25', '5', '60', '15');

-- ----------------------------
-- Table structure for `relation`
-- ----------------------------
DROP TABLE IF EXISTS `relation`;
CREATE TABLE `relation` (
  `BaseID` varchar(20) NOT NULL DEFAULT '',
  `FruitID` varchar(20) NOT NULL DEFAULT '',
  `AreaID` int(11) NOT NULL,
  `SensorID` varchar(20) NOT NULL DEFAULT '',
  PRIMARY KEY (`BaseID`,`FruitID`,`AreaID`,`SensorID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of relation
-- ----------------------------
INSERT INTO `relation` VALUES ('base_101', 'fruit_101', '1', '010101');
INSERT INTO `relation` VALUES ('base_101', 'fruit_102', '2', '010102');
INSERT INTO `relation` VALUES ('base_102', 'fruit_101', '2', '010202');
INSERT INTO `relation` VALUES ('base_102', 'fruit_102', '1', '010201');
INSERT INTO `relation` VALUES ('base_103', 'fruit_103', '1', '010301');
INSERT INTO `relation` VALUES ('base_104', 'fruit_103', '2', '010402');
INSERT INTO `relation` VALUES ('base_104', 'fruit_104', '1', '010401');
INSERT INTO `relation` VALUES ('base_105', 'fruit_101', '1', '010501');
INSERT INTO `relation` VALUES ('base_105', 'fruit_102', '2', '010502');
INSERT INTO `relation` VALUES ('base_105', 'fruit_103', '3', '010503');
