DROP TABLE IF EXISTS tb_user;
DROP TABLE IF EXISTS tb_location;
DROP TABLE IF EXISTS tb_emergency;

CREATE TABLE tb_user (
	user_id			CHAR(28) PRIMARY KEY,
	phone			CHAR(20) NOT NULL,
	name			VARCHAR(50) NOT NULL,
	notif_api_key	CHAR(36) NOT NULL
);

CREATE TABLE tb_location (
	location_id		BIGINT AUTO_INCREMENT PRIMARY KEY,
	latitude		DOUBLE NOT NULL,
	longitude		DOUBLE NOT NULL,
	addr			VARCHAR(255),
	city			VARCHAR(50),
	stat			VARCHAR(50),
	country			VARCHAR(50),
	postal_code		CHAR(8)
);

CREATE TABLE tb_emergency(
	emergency_id	BIGINT AUTO_INCREMENT PRIMARY KEY,
	user_id			CHAR(28) NOT NULL REFERENCES tb_user (user_id),
	location_id		BIGINT REFERENCES tb_location (location_id),
	imei			CHAR(15) NOT NULL,
	status			CHAR(10) NOT NULL,
	start_time		TIMESTAMP DEFAULT current_timestamp NOT NULL,
	end_time		TIMESTAMP NULL
);