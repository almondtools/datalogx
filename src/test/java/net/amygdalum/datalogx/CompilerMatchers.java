package net.amygdalum.datalogx;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;

import net.amygdalum.datalogx.Assert;
import net.amygdalum.datalogx.Fact;
import net.amygdalum.datalogx.Retract;
import net.amygdalum.datalogx.Rule;
import net.amygdalum.datalogx.Statement;

public final class CompilerMatchers {

	private CompilerMatchers() {
	}
	
	public static Matcher<Statement> ruleAssertion() {
		return new TypeSafeMatcher<Statement>() {

			@Override
			public void describeTo(Description description) {
				description.appendText("should be instance of Assert and should assert a Rule");
			}

			@Override
			protected boolean matchesSafely(Statement item) {
				if (!(item instanceof Assert)) {
					return false;
				}
				if (!(item.getClause() instanceof Rule)) {
					return false;
				}
				return true;
			}
		};
	}

	public static Matcher<Statement> factAssertion() {
		return new TypeSafeMatcher<Statement>() {

			@Override
			public void describeTo(Description description) {
				description.appendText("should be instance of Assert and should assert a Fact");
			}

			@Override
			protected boolean matchesSafely(Statement item) {
				if (!(item instanceof Assert)) {
					return false;
				}
				if (!(item.getClause() instanceof Fact)) {
					return false;
				}
				return true;
			}
		};
	}

	public static Matcher<Statement> ruleRetraction() {
		return new TypeSafeMatcher<Statement>() {

			@Override
			public void describeTo(Description description) {
				description.appendText("should be instance of Retract and should assert a Rule");
			}

			@Override
			protected boolean matchesSafely(Statement item) {
				if (!(item instanceof Retract)) {
					return false;
				}
				if (!(item.getClause() instanceof Rule)) {
					return false;
				}
				return true;
			}
		};
	}

	public static Matcher<Statement> factRetraction() {
		return new TypeSafeMatcher<Statement>() {

			@Override
			public void describeTo(Description description) {
				description.appendText("should be instance of Retract and should assert a Fact");
			}

			@Override
			protected boolean matchesSafely(Statement item) {
				if (!(item instanceof Retract)) {
					return false;
				}
				if (!(item.getClause() instanceof Fact)) {
					return false;
				}
				return true;
			}
		};
	}

}
