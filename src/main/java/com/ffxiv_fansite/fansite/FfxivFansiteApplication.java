package com.ffxiv_fansite.fansite;

import org.slf4j.MDC;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.lang.management.ManagementFactory;

@SpringBootApplication
public class FfxivFansiteApplication {

	public static void main(String[] args) {
		MDC.put("process_id", String.valueOf(ManagementFactory.getRuntimeMXBean().getPid()));

		SpringApplication.run(FfxivFansiteApplication.class, args);
	}

}
