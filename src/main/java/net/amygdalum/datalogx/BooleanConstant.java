package net.amygdalum.datalogx;

public class BooleanConstant extends Constant {

	private boolean value;

	private BooleanConstant(boolean value) {
		this.value = value;
	}

	public static BooleanConstant bool(boolean value) {
		return new BooleanConstant(value);
	}

	public static BooleanConstant boolL(String literal) {
		return new BooleanConstant(Boolean.parseBoolean(literal));
	}

	public boolean getValue() {
		return value;
	}
	
	@Override
	public <T> T apply(TermVisitor<T> visitor) {
		return visitor.visitBooleanConstant(this);
	}

	@Override
	public String toString() {
		return String.valueOf(value);
	}

	@Override
	public int hashCode() {
		return value ? 7 : 17; 
	}

	@Override
	public boolean equals(Object obj) {
		if (!super.equals(obj)) {
			return false;
		}
		BooleanConstant that = (BooleanConstant) obj;
		return this.value == that.value;
	}

}
