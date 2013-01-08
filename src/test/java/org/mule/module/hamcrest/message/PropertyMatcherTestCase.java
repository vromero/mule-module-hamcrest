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
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mule.module.hamcrest.message.PropertyMatcher.*;

import org.junit.Before;
import org.junit.Test;
import org.mule.DefaultMuleEvent;
import org.mule.DefaultMuleMessage;
import org.mule.MessageExchangePattern;
import org.mule.api.MuleMessage;
import org.mule.api.transport.PropertyScope;
import org.mule.tck.junit4.FunctionalTestCase;

public class PropertyMatcherTestCase extends FunctionalTestCase {

	private MuleMessage message;
	
	@Override
	protected String getConfigResources() {
		return "hamcrest-functional-test-config.xml";
	}
	
	@Before
	public void doSetup() throws Exception {
		message = new DefaultMuleMessage(null, muleContext);
		new DefaultMuleEvent(message, MessageExchangePattern.ONE_WAY, getTestService());
		
		message.setProperty("anInboundKey", "aValue", PropertyScope.INBOUND);
    	message.setProperty("anOutboundKey", "aValue", PropertyScope.OUTBOUND);
    	message.setProperty("anInvocationaKey", "aValue", PropertyScope.INVOCATION);
    	message.setProperty("aSessionKey", "aValue", PropertyScope.SESSION);
	}

	
	@Test
	public void testHasScopedPropertyValueWithMatcher() throws Exception {
		assertThat(message,
				hasProperty(PropertyScope.OUTBOUND, "anOutboundKey", is("aValue")));
	}

	@Test
	public void testNotHasScopedPropertyValue() throws Exception {
		assertThat(
				message,
				not(hasProperty(PropertyScope.OUTBOUND, "AnotherKey", "aValue")));
	}

	@Test
	public void testHasScopedPropertyWithMatcher() throws Exception {
		assertThat(message, hasProperty(PropertyScope.OUTBOUND, "anOutboundKey"));
	}

	@Test
	public void testNotHasScopedPropertyWithMatcher() throws Exception {
		assertThat(message,
				not(hasProperty(PropertyScope.OUTBOUND, "AnotherKey")));
	}
	
	
	@Test
	public void testHasInboundProperty() throws Exception {
		assertThat(message, hasInboundProperty("anInboundKey"));
	}
	
	@Test
	public void testHasInboundPropertyWithMatcher() throws Exception {
		assertThat(message, hasInboundProperty("anInboundKey", is("aValue")));
	}

	@Test
	public void testHasInboundPropertyWithValue() throws Exception {
		assertThat(message, hasInboundProperty("anInboundKey", "aValue"));
	}
	

	@Test
    public void testHasOutboundProperty() throws Exception
    {
    	assertThat(message, hasOutboundProperty("anOutboundKey"));
    }

	@Test
    public void testHasOutboundPropertyWithValue() throws Exception
    {
    	assertThat(message, hasOutboundProperty("anOutboundKey", "aValue"));
    }

	@Test
    public void testHasOutboundPropertyWithMatcher() throws Exception
    {
    	assertThat(message, hasOutboundProperty("anOutboundKey", is("aValue")));
    }
	
	
	@Test
    public void testHasInvocationProperty() throws Exception
    {
    	assertThat(message, hasInvocationProperty("anInvocationaKey"));
    }
	
	@Test
    public void testHasInvocationPropertyWithValue() throws Exception
    {
    	assertThat(message, hasInvocationProperty("anInvocationaKey", "aValue"));
    }

	@Test
    public void testHasInvocationPropertyWithMatcher() throws Exception
    {
    	assertThat(message, hasInvocationProperty("anInvocationaKey", is("aValue")));
    }
	
	
	@Test
    public void testHasSessionPropertyWithMatcher() throws Exception
    {
    	assertThat(message, hasSessionProperty("aSessionKey", is("aValue")));
    }
	
	@Test
    public void testHasSessionProperty() throws Exception
    {
    	assertThat(message, hasSessionProperty("aSessionKey", "aValue"));
    }
	
	
	@Test
    public void testHasAnyScopeProperty() throws Exception
    {
    	assertThat(message, hasPropertyInAnyScope("anOutboundKey"));
    	assertThat(message, hasPropertyInAnyScope("anInvocationaKey"));
    	assertThat(message, hasPropertyInAnyScope("aSessionKey"));
    	assertThat(message, hasPropertyInAnyScope("anInboundKey"));
    	
    	assertThat(message, not(hasPropertyInAnyScope("iDontExist")));
    }
	
	@Test
    public void testHasAnyScopePropertyWithMatcher() throws Exception
    {
    	assertThat(message, hasPropertyInAnyScope("anOutboundKey", is("aValue")));
    	assertThat(message, hasPropertyInAnyScope("anInvocationaKey", is("aValue")));
    	assertThat(message, hasPropertyInAnyScope("aSessionKey", is("aValue")));
    	assertThat(message, hasPropertyInAnyScope("anInboundKey", is("aValue")));
    	
    	assertThat(message, not(hasPropertyInAnyScope("iDontExist", is("andNeitherHaveValue"))));
    }
	
	
	@Test
    public void testDescriptionAndMismatch() throws Exception
    {
		PropertyMatcher p = new PropertyMatcher(PropertyScope.OUTBOUND, "anOutboundKey", is("anOutboundKeyValue"));
		DescriptionAssertor.assertDescription("a MuleMessage which property with key \"anOutboundKey\" in scope <outbound> is \"anOutboundKeyValue\"", p);
		DescriptionAssertor.assertMismatchDescription("was a MuleMessage which property with key \"anOutboundKey\" in scope <outbound> has the value \"aValue\"", p, message);
    }
	
}
