package com.skt.task.management;

import org.junit.Test;

import static org.junit.Assert.assertTrue;

/**
 * test class for spring boot main class
 */
public class ManagementApplicationTest {
    @Test
    public void main() {
        ManagementApplication.main(new String[] {});
        assertTrue(true);
    }
}