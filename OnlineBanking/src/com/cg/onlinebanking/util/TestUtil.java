package com.cg.onlinebanking.util;

import java.nio.charset.Charset;
import java.util.Random;

public class TestUtil {

	public static String stringGenerator() {
    byte[] array = new byte[9]; // length is bounded by 9
    new Random().nextBytes(array);
    String generatedString = new String(array, Charset.forName("UTF-8"));
 
    return generatedString;
}
	
}
