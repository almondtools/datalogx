package net.amygdalum.datalogx;

import java.math.BigDecimal;
import java.math.BigInteger;

public class TestHelper {

	private TestHelper() {
	}
	
	public static BigInteger bi(long l) {
		return BigInteger.valueOf(l);
	}

	public static BigDecimal bd(long l, int i) {
		return BigDecimal.valueOf(l, i);
	}

	public static BigDecimal bd(long l) {
		return BigDecimal.valueOf(l);
	}
}
