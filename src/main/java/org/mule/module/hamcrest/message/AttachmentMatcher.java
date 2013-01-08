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

import javax.activation.DataHandler;

import org.hamcrest.Description;
import org.hamcrest.Factory;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeDiagnosingMatcher;
import org.hamcrest.core.IsEqual;
import org.hamcrest.core.IsNull;
import org.mule.api.MuleMessage;

public class AttachmentMatcher extends TypeSafeDiagnosingMatcher<MuleMessage> {

	private enum AttachmentScope {INBOUND, OUTBOUND};

	private final Matcher<?> matcher;
	
	private final String key;
	
	private final AttachmentScope scope;
	
	AttachmentMatcher(String key, Matcher<?> matcher) {
		this(key, AttachmentScope.INBOUND, matcher);
	}
	
	AttachmentMatcher(String key, AttachmentScope scope, Matcher<?> matcher) {
		super();
		this.key = key;
		this.matcher = matcher;
		this.scope = scope;
	}

	@Override
	public boolean matchesSafely(MuleMessage message, Description mismatchDescription) {
		boolean matches;
		Object attachment;
		
		try {
			attachment = getAttachmentForScope(key, scope, message);
			matches = matcher.matches(attachment);
		} catch (IOException e) {
			mismatchDescription.appendText(" was a MuleMessage with an unreachable attachment in scope ")
			  .appendValue(scope)
			  .appendText(" with key ")
			  .appendValue(key)
			  .appendText(" because of exception  ")
			  .appendValue(e);
			return false;
		}
		
		if (!matches) {
			mismatchDescription.appendText(" was a MuleMessage with an attachment in scope ")
			  .appendValue(scope)
			  .appendText(" with key ")
			  .appendValue(key)
			  .appendText(" and value ")
			  .appendValue(attachment);
		}
		
		return matches;
	}

	private Object getAttachmentForScope(String attachmentKey, AttachmentScope attachmentScope, MuleMessage message) throws IOException {
		if (attachmentScope == AttachmentScope.INBOUND) {
			DataHandler attachment = message.getInboundAttachment(key);
			return attachment != null ? attachment.getContent() : null;
		} else if (attachmentScope == AttachmentScope.OUTBOUND){
			DataHandler attachment = message.getOutboundAttachment(key);
			return attachment != null ? attachment.getContent() : null;
		} else {
			throw new UnsupportedOperationException("Unknown attachment scope " + attachmentScope.name());
		}
	}
	
	public void describeTo(Description description) {
		description.appendText("a MuleMessage with an inbound attachment in scope ")
		.appendValue(scope)
		.appendText(" with key ")
		.appendValue(key)
		.appendText(" and value ")
		.appendDescriptionOf(matcher);
	}

	@Factory
	public static <T> Matcher<MuleMessage> hasInboundAttachment(String key) {
		return new AttachmentMatcher(key, AttachmentScope.INBOUND, IsNull.notNullValue());
	}
	
	@Factory
	public static <T> Matcher<MuleMessage> hasInboundAttachment(String key, T message) {
		return new AttachmentMatcher(key, AttachmentScope.INBOUND, IsEqual.equalTo(message));
	}
	
	@Factory
	public static <T> Matcher<MuleMessage> hasInboundAttachment(String key, Matcher<? super T> matcher) {
		return new AttachmentMatcher(key, AttachmentScope.INBOUND, matcher);
	}
	
	
	@Factory
	public static <T> Matcher<MuleMessage> hasOutboundAttachment(String key) {
		return new AttachmentMatcher(key, AttachmentScope.OUTBOUND, IsNull.notNullValue());
	}
	
	@Factory
	public static <T> Matcher<MuleMessage> hasOutboundAttachment(String key, T value) {
		return new AttachmentMatcher(key, AttachmentScope.OUTBOUND, IsEqual.equalTo(value));
	}
	
	@Factory
	public static <T> Matcher<MuleMessage> hasOutboundAttachment(String key, Matcher<? super T> matcher) {
		return new AttachmentMatcher(key, AttachmentScope.OUTBOUND, matcher);
	}
	
}
