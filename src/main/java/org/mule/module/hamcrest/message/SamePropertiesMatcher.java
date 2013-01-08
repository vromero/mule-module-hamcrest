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

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.hamcrest.Description;
import org.hamcrest.Factory;
import org.hamcrest.Matcher;
import org.hamcrest.core.AllOf;
import org.junit.internal.matchers.TypeSafeMatcher;
import org.mule.api.MuleMessage;
import org.mule.api.transport.PropertyScope;

public class SamePropertiesMatcher extends TypeSafeMatcher<MuleMessage> {

	private final PropertyScope scope;
	
	private final MuleMessage value;
	
	SamePropertiesMatcher(PropertyScope scope, MuleMessage value) {
		super();
		this.scope = scope;
		this.value = value;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean matchesSafely(MuleMessage message) {
		Set<String> propertyNames = comparePropertyNames(value, message, scope);
		if (propertyNames == null) {
			return false;
		}
		
		return comparePropertyValues(value, message, propertyNames, scope);
	}
	
	public Set<String> comparePropertyNames(MuleMessage left, MuleMessage right, PropertyScope scope) {
		Set<String> leftPropertyNames = left.getPropertyNames(scope);
		Set<String> rightPropertyNames = right.getPropertyNames(scope);
		return leftPropertyNames.equals(rightPropertyNames) ? leftPropertyNames : null;
	}

	public boolean comparePropertyValues(MuleMessage left, MuleMessage right, Set<String> propertyNames, PropertyScope scope) {
		for (String propertyName : propertyNames) {
			if (left.getProperty(propertyName, scope) != right.getProperty(propertyName, scope)) {
				return false;
			}
		}
		return true;
	}

	/**
	 * {@inheritDoc}
	 */
	public void describeTo(Description description) {
		description.appendText("a MuleMessage with same properties in scope ")
		.appendValue(scope)
		.appendText(" than ")
		.appendValue(value);
	}

	@Factory
	public static <T> Matcher<MuleMessage> hasSamePropertiesThan(PropertyScope scope, MuleMessage message) {
		return new SamePropertiesMatcher(scope, message);
	}
	
	@Factory
	public static <T> Matcher<MuleMessage> hasSameInboundPropertiesThan(MuleMessage message) {
		return new SamePropertiesMatcher(PropertyScope.INBOUND, message);
	}
	
	@Factory
	public static <T> Matcher<MuleMessage> hasSameOutboundPropertiesThan(MuleMessage message) {
		return new SamePropertiesMatcher(PropertyScope.OUTBOUND, message);
	}

	@Factory
	public static <T> Matcher<MuleMessage> hasSameInvocationPropertiesThan(MuleMessage message) {
		return new SamePropertiesMatcher(PropertyScope.INVOCATION, message);
	}

	@Factory
	public static <T> Matcher<MuleMessage> hasSameSessionPropertiesThan(MuleMessage message) {
		return new SamePropertiesMatcher(PropertyScope.SESSION, message);
	}
	
	@Factory
	public static <T> Matcher<MuleMessage> hasSamePropertiesThan(MuleMessage message) {
		List<Matcher<? super MuleMessage>> allScopeMatchers = new ArrayList<Matcher<? super MuleMessage>>(4);
		
		allScopeMatchers.add(new SamePropertiesMatcher(PropertyScope.INBOUND, message));
		allScopeMatchers.add(new SamePropertiesMatcher(PropertyScope.OUTBOUND, message));
		allScopeMatchers.add(new SamePropertiesMatcher(PropertyScope.INVOCATION, message));
		allScopeMatchers.add(new SamePropertiesMatcher(PropertyScope.SESSION, message));
		
		return new AllOf<MuleMessage>(allScopeMatchers);
	}
	
}
