package com.github.dariux2016;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class BannerAppStarter {
	public static void main(final String[] args) {
		new SpringApplication(BannerAppStarter.class).run(args);
	}
	
}