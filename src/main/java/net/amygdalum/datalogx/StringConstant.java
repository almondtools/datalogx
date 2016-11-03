package net.amygdalum.datalogx;

public class StringConstant extends Constant {

	private String value;

	private StringConstant(String value) {
		this.value = value;
	}
	
	public static StringConstant str(String value) {
		return new StringConstant(value);
	}
	
	public static StringConstant strL(String stringliteral) {
		return new StringConstant(unescape(stringliteral.substring(1, stringliteral.length() -1)).intern());
	}

	private static String unescape(String escaped) {
		StringBuilder buffer = new StringBuilder(escaped);
		for (int i = 0; i < buffer.length(); i++) {
			char c = buffer.charAt(i);
			switch(c) {
			case '\\':
				buffer.deleteCharAt(i);
				char control = buffer.charAt(i);
				switch (control) {
				case 't':
					buffer.setCharAt(i, '\t');
					continue;
				case 'b':
					buffer.setCharAt(i, '\b');
					continue;
				case 'n':
					buffer.setCharAt(i, '\n');
					continue;
				case 'r':
					buffer.setCharAt(i, '\r');
					continue;
				case 'f':
					buffer.setCharAt(i, '\f');
					continue;
				default:
				}
			default:
			}
		}
		return buffer.toString();
	}
	
	private static String escape(String string) {
		StringBuilder buffer = new StringBuilder(string);
		for (int i = 0; i < buffer.length(); i++) {
			char c = buffer.charAt(i);
			switch(c) {
			case '"':
			case '\'':
			case '\\':
				buffer.insert(i, '\\');
				i++;
				continue;
			case '\t':
				buffer.setCharAt(i, 't');
				buffer.insert(i, '\\');
				i++;
				continue;
			case '\b':
				buffer.setCharAt(i, 'b');
				buffer.insert(i, '\\');
				i++;
				continue;
			case '\n':
				buffer.setCharAt(i, 'n');
				buffer.insert(i, '\\');
				i++;
				continue;
			case '\r':
				buffer.setCharAt(i, 'r');
				buffer.insert(i, '\\');
				i++;
				continue;
			case '\f':
				buffer.setCharAt(i, 'f');
				buffer.insert(i, '\\');
				i++;
				continue;
			default:
			}
		}
		return buffer.toString();
	}

	public String getValue() {
		return value;
	}
	
	@Override
	public <T> T apply(TermVisitor<T> visitor) {
		return visitor.visitStringConstant(this);
	}

	@Override
	public String toString() {
		return new StringBuilder()
		.append('\'')
		.append(escape(value))
		.append('\'')
		.toString();
	}

	@Override
	public int hashCode() {
		return 5 + ((value == null) ? 0 : value.hashCode()) * 7;
	}

	@Override
	public boolean equals(Object obj) {
		if (!super.equals(obj)) {
			return false;
		}
		StringConstant that = (StringConstant) obj;
		return this.value == null && that.value == null
			|| this.value != null && that.value != null && this.value.equals(that.value);
	}

}
