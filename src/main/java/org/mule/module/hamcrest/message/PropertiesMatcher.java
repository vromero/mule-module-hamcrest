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

import static org.hamcrest.collection.IsCollectionContaining.hasItem;

import org.hamcrest.Description;
import org.hamcrest.Factory;
import org.hamcrest.Matcher;
import org.junit.internal.matchers.TypeSafeMatcher;
import org.mule.api.MuleMessage;
import org.mule.api.transport.PropertyScope;

public class PropertiesMatcher extends TypeSafeMatcher<MuleMessage> {

	private final Matcher<?> matcher;
	
	private final PropertyScope scope;
	
	PropertiesMatcher(PropertyScope scope, Matcher<?> matcher) {
		super();
		this.scope = scope;
		this.matcher = matcher;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean matchesSafely(MuleMessage message) {
		return matcher.matches(message.getPropertyNames(scope));
	}

	/**
	 * {@inheritDoc}
	 */
	public void describeTo(Description description) {
		description.appendText("a MuleMessage with property names for scope ")
		.appendValue(scope)
		.appendText(" ")
		.appendDescriptionOf(matcher);
	}
	
	@Factory
	public static <T> Matcher<MuleMessage> hasProperties(PropertyScope scope, T value) {
		return new PropertiesMatcher(scope, hasItem(value));
	}
	
	@Factory
	public static <T> Matcher<MuleMessage> hasProperties(PropertyScope scope, Matcher<? super T> matcher) {
		return new PropertiesMatcher(scope, matcher);
	}
	
}
