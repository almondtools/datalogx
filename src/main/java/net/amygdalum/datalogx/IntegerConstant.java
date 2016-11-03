package net.amygdalum.datalogx;

import java.math.BigInteger;


public class IntegerConstant extends Constant {

	private BigInteger value;

	private IntegerConstant(BigInteger value) {
		this.value = value;
	}
	
	public static IntegerConstant intg(BigInteger value) {
		return new IntegerConstant(value);
	}
	
	public static IntegerConstant intg(String value) {
		return new IntegerConstant(new BigInteger(value));
	}
	
	public static IntegerConstant intg(long value) {
		return new IntegerConstant(BigInteger.valueOf(value));
	}
	
	public static IntegerConstant intgL(String literal) {
		return new IntegerConstant(new BigInteger(literal));
	}

	public BigInteger getValue() {
		return value;
	}

	@Override
	public <T> T apply(TermVisitor<T> visitor) {
		return visitor.visitIntegerConstant(this);
	}

	@Override
	public String toString() {
		return String.valueOf(value);
	}

	@Override
	public int hashCode() {
		return value.hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if (!super.equals(obj)) {
			return false;
		}
		IntegerConstant that = (IntegerConstant) obj;
		return this.value.equals(that.value);
	}
}
