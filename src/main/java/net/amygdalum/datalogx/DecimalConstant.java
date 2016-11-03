package net.amygdalum.datalogx;

import java.math.BigDecimal;

public class DecimalConstant extends Constant {

	private BigDecimal value;

	private DecimalConstant(BigDecimal value) {
		this.value = value;
	}

	public static DecimalConstant dec(BigDecimal value) {
		return new DecimalConstant(value);
	}

	public static DecimalConstant dec(String value) {
		return new DecimalConstant(new BigDecimal(value));
	}

	public static DecimalConstant dec(long value, int scale) {
		return new DecimalConstant(BigDecimal.valueOf(value, scale));
	}

	public static DecimalConstant decL(String literal) {
		return new DecimalConstant(new BigDecimal(literal));
	}

	public BigDecimal getValue() {
		return value;
	}

	@Override
	public <T> T apply(TermVisitor<T> visitor) {
		return visitor.visitDecimalConstant(this);
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
		DecimalConstant that = (DecimalConstant) obj;
		return this.value.equals(that.value);
	}

}
