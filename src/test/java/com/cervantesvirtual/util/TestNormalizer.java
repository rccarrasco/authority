package com.cervantesvirtual.util;

import junit.framework.TestCase;
import com.cervantesvirtual.util.Normalizer;

public class TestNormalizer extends TestCase {
	public void testNormalizer() {
		String input = "Jose María de   Íñigo";
		String output = "jose iñigo";
		assertEquals(output, Normalizer.normalize(input));
	}
}
