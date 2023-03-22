package com.skt.task.microservice;

import org.junit.Test;

import static org.junit.Assert.assertTrue;

/**
 * test class for spring boot main class
 */
public class MicroserviceApplicationTests {
	@Test
	public void main() {
		MicroserviceApplication.main(new String[] {});
		assertTrue(true);
	}
}
