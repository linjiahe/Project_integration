package com.blockchain.commune;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.data.mongo.MongoDataAutoConfiguration;
import org.springframework.boot.autoconfigure.mongo.MongoAutoConfiguration;
import org.springframework.scheduling.annotation.EnableScheduling;

@MapperScan(basePackages = {"com.blockchain.commune.mapper","com.blockchain.commune.customemapper"})

@SpringBootApplication(exclude = {MongoAutoConfiguration.class, MongoDataAutoConfiguration.class})

@EnableScheduling
public class CommuneApplication {

	public static void main(String[] args) {
		SpringApplication.run(CommuneApplication.class);
	}
}
