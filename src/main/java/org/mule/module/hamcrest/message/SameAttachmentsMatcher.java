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

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.hamcrest.Description;
import org.hamcrest.Factory;
import org.hamcrest.Matcher;
import org.hamcrest.core.AllOf;
import org.junit.internal.matchers.TypeSafeMatcher;
import org.mule.api.MuleMessage;

public class SameAttachmentsMatcher extends TypeSafeMatcher<MuleMessage> {

	private enum AttachmentScope {INBOUND, OUTBOUND};
	
	private final AttachmentScope scope;
	
	private final MuleMessage value;
	
	SameAttachmentsMatcher(AttachmentScope scope, MuleMessage value) {
		super();
		this.scope = scope;
		this.value = value;
	}
	
	@Override
	public boolean matchesSafely(MuleMessage message) {
		Set<String> propertyNames = compareAttachmentNames(value, message, scope);
		if (propertyNames == null) {
			return false;
		}
		
		try {
			return compareAttachmentValues(value, message, propertyNames, scope);
		} catch (IOException e) {
			return false;
		}
	}
	
	private Set<String> compareAttachmentNames(MuleMessage left, MuleMessage right, AttachmentScope scope) {
		Set<String> leftPropertyNames = getAttachmentNamesForScope(left, scope);
		Set<String> rightPropertyNames = getAttachmentNamesForScope(right, scope);
		return leftPropertyNames.equals(rightPropertyNames) ? leftPropertyNames : null;
	}
	
	private Set<String> getAttachmentNamesForScope(MuleMessage message, AttachmentScope scope) {
		if (scope == AttachmentScope.INBOUND) {
			return message.getInboundAttachmentNames();
		} else {
			return message.getOutboundAttachmentNames();
		}
	}

	private boolean compareAttachmentValues(MuleMessage left, MuleMessage right, Set<String> propertyNames, AttachmentScope scope) throws IOException {
		for (String propertyName : propertyNames) {
			if (getAttachmentValueForScope(left, scope, propertyName) != getAttachmentValueForScope(right, scope, propertyName)) {
				return false;
			}
		}
		return true;
	}
	
	private Object getAttachmentValueForScope(MuleMessage message, AttachmentScope scope, String key) throws IOException {
		if (scope == AttachmentScope.INBOUND) {
			return message.getInboundAttachment(key).getContent();
		} else {
			return message.getOutboundAttachment(key).getContent();
		}
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
	public static <T> Matcher<MuleMessage> hasSameInboundAttachmentsThan(MuleMessage message) {
		return new SameAttachmentsMatcher(AttachmentScope.INBOUND, message);
	}
	
	@Factory
	public static <T> Matcher<MuleMessage> hasSameOutboundAttachmentsThan(MuleMessage message) {
		return new SameAttachmentsMatcher(AttachmentScope.OUTBOUND, message);
	}

	@Factory
	public static <T> Matcher<MuleMessage> hasSameAttachmentsThan(MuleMessage message) {
		List<Matcher<? super MuleMessage>> allScopeMatchers = new ArrayList<Matcher<? super MuleMessage>>(2);
		
		allScopeMatchers.add(new SameAttachmentsMatcher(AttachmentScope.INBOUND, message));
		allScopeMatchers.add(new SameAttachmentsMatcher(AttachmentScope.OUTBOUND, message));
		
		return new AllOf<MuleMessage>(allScopeMatchers);
	}
	
}
