package com.ccbs.careercoachbookingsystem;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.ccbs.careercoachbookingsystem.mapper")
public class CareerCoachBookingSystemApplication {

	public static void main(String[] args) {
		SpringApplication.run(CareerCoachBookingSystemApplication.class, args);
	}

}
