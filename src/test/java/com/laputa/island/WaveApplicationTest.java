package com.laputa.island;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class WaveApplicationTest {
	private static final Logger logger = LoggerFactory.getLogger(WaveApplicationTest.class);

	@Test
	public void mainTest() {
		logger.info("Performing mainTest");
		String[] stringArray = new String[0];
		WaveApplication.main(stringArray);
	}
}
