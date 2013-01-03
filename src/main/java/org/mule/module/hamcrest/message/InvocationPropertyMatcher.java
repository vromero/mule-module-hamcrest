/*
 * Copyright (c) 2013, Victor Romero. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston,
 * MA 02110-1301  USA
 */
package org.mule.module.hamcrest.message;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import org.hamcrest.Description;
import org.hamcrest.Factory;
import org.hamcrest.Matcher;
import org.hamcrest.StringDescription;
import org.hamcrest.core.IsEqual;
import org.hamcrest.core.IsNull;
import org.junit.Test;
import org.junit.internal.matchers.TypeSafeMatcher;
import org.mule.api.MuleMessage;
import org.mule.api.transport.PropertyScope;

public class InvocationPropertyMatcher extends TypeSafeMatcher<MuleMessage> {

	private final Matcher<?> matcher;
	
	InvocationPropertyMatcher(Matcher<?> matcher) {
		super();
		this.matcher = matcher;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean matchesSafely(MuleMessage message) {
		return matcher.matches(message);
	}

	/**
	 * {@inheritDoc}
	 */
	public void describeTo(Description description) {
		description.appendDescriptionOf(matcher);
	}

	@Factory
	public static <T> Matcher<MuleMessage> hasInvocationProperty(String key) {
		return new PropertyMatcher(PropertyScope.INVOCATION, key, IsNull.notNullValue());
	}
	
	@Factory
	public static <T> Matcher<MuleMessage> hasInvocationProperty(String key, T value) {
		return new PropertyMatcher(PropertyScope.INVOCATION, key, IsEqual.equalTo(value));
	}
	
	@Factory
	public static <T> Matcher<MuleMessage> hasInvocationProperty(String key, Matcher<? super T> matcher) {
		return new PropertyMatcher(PropertyScope.INVOCATION, key, matcher);
	}
	
	@Test
    public void testDescription() throws Exception
    {
		StringDescription description = new StringDescription();
		new InboundAttachmentMatcher("key", is("value")).describeTo(description);
	    assertThat(description.toString(), is("a MuleMessage with an inbound attachment with key \"key\" and value is \"value\""));
    }
	
}
