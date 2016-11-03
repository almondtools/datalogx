package net.amygdalum.datalogx;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

public class ListConstant extends Constant {

	private List<Constant> values;

	private ListConstant(Collection<Constant> values) {
		this.values = new ArrayList<>(values);
	}

	public static ListConstant list(Collection<Constant> values) {
		return new ListConstant(values);
	}
	
	public List<Constant> getValues() {
		return values;
	}

	@Override
	public <T> T apply(TermVisitor<T> visitor) {
		return visitor.visitListConstant(this);
	}
	
	@Override
	public String toString() {
		StringBuilder buffer = new StringBuilder();
		buffer.append('[');
		Iterator<Constant> viterator = values.iterator();
		if (viterator.hasNext()) {
			buffer.append(viterator.next());
		}
		while (viterator.hasNext()) {
			buffer.append(',').append(viterator.next());
		}
		buffer.append(']');
		return buffer.toString();
	}

	@Override
	public int hashCode() {
		return 3 + values.hashCode() * 11;
	}

	@Override
	public boolean equals(Object obj) {
		if (!super.equals(obj)) {
			return false;
		}
		ListConstant that = (ListConstant) obj;
		return this.values.equals(that.values);
	}

}
