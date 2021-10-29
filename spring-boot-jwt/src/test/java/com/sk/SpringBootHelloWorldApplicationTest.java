package com.sk;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import static org.junit.Assert.assertEquals;

@RunWith(MockitoJUnitRunner.class)
public class SpringBootHelloWorldApplicationTest {

	@Test
	public void test1() {
		assertEquals("result", "result");
	}
	
	
}
