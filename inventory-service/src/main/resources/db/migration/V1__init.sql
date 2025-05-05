CREATE TABLE `inventory`
(
    `id` bigint(20) NOT NULL AUTO_INCREMENT,
    `code` varchar(255),
    `quantity` int(11) DEFAULT NULL,
    PRIMARY KEY (`id`)
);