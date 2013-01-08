package org.mule.module.hamcrest.message;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.StringDescription;
import org.junit.Assert;

public abstract class DescriptionAssertor {
	
    public static void assertDescription(String expected, Matcher<?> matcher) {
        Description description = new StringDescription();
        description.appendDescriptionOf(matcher);
        Assert.assertEquals("Expected description", expected, description.toString().trim());
    }

    public static <T> void assertMismatchDescription(String expected, Matcher<? super T> matcher, T arg) {
        Assert.assertFalse("Precondtion: Matcher should not match item.", matcher.matches(arg));
        Description description = new StringDescription();
        matcher.describeMismatch(arg, description);
        Assert.assertEquals("Expected mismatch description", expected, description.toString().trim());
    }

}
