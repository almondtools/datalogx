package net.amygdalum.datalogx;

import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Set;

public class SetConstant extends Constant {

	private Set<Constant> values;

	private SetConstant(Collection<Constant> values) {
		this.values = new LinkedHashSet<>(values);
	}

	public static SetConstant set(Collection<Constant> values) {
		return new SetConstant(values);
	}
	
	public Set<Constant> getValues() {
		return values;
	}

	@Override
	public <T> T apply(TermVisitor<T> visitor) {
		return visitor.visitSetConstant(this);
	}
	
	@Override
	public String toString() {
		StringBuilder buffer = new StringBuilder();
		buffer.append('{');
		Iterator<Constant> viterator = values.iterator();
		if (viterator.hasNext()) {
			buffer.append(viterator.next());
		}
		while (viterator.hasNext()) {
			buffer.append(',').append(viterator.next());
		}
		buffer.append('}');
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
		SetConstant that = (SetConstant) obj;
		return this.values.equals(that.values);
	}

}
