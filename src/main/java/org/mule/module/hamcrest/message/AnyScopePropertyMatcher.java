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

import org.hamcrest.Description;
import org.hamcrest.Factory;
import org.hamcrest.Matcher;
import org.hamcrest.core.IsNull;
import org.junit.internal.matchers.TypeSafeMatcher;
import org.mule.api.MuleMessage;

public class AnyScopePropertyMatcher extends TypeSafeMatcher<MuleMessage> {

	private final Matcher<?> matcher;
	
	private final String key;
	
	AnyScopePropertyMatcher(String key, Matcher<?> matcher) {
		super();
		this.key = key;
		this.matcher = matcher;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean matchesSafely(MuleMessage message) {
		return matcher.matches(message.findPropertyInAnyScope(key, null));
	}

	/**
	 * {@inheritDoc}
	 */
	public void describeTo(Description description) {
		description.appendText("a MuleMessage with a property in any scope: ").appendDescriptionOf(matcher);
	}

	@Factory
	public static <T> Matcher<MuleMessage> hasPropertyInAnyScope(String key) {
		return new AnyScopePropertyMatcher(key, IsNull.notNullValue());
	}
	
	@Factory
	public static <T> Matcher<MuleMessage> hasPropertyInAnyScope(String key, Matcher<? super T> matcher) {
		return new AnyScopePropertyMatcher(key, matcher);
	}
	
}