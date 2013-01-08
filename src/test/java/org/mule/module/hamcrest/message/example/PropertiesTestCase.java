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
package org.mule.module.hamcrest.message.example;

import static org.hamcrest.Matchers.*;
import static org.mule.module.hamcrest.message.MessageMatchers.*;

import static org.junit.Assert.assertThat;

import org.junit.Before;
import org.junit.Test;
import org.mule.DefaultMuleEvent;
import org.mule.DefaultMuleMessage;
import org.mule.MessageExchangePattern;
import org.mule.api.MuleMessage;
import org.mule.api.transport.PropertyScope;
import org.mule.tck.junit4.FunctionalTestCase;

public class PropertiesTestCase extends FunctionalTestCase
{
	
	private MuleMessage message;
	
	@Override
	protected String getConfigResources() {
		return "hamcrest-functional-test-config.xml";
	}
	
	@Before
	public void doSetup() throws Exception {
		message = new DefaultMuleMessage(null, muleContext);
    	new DefaultMuleEvent(message, MessageExchangePattern.ONE_WAY, getTestService());

    	message.setProperty("aPropertyKey", "aValue", PropertyScope.OUTBOUND);
    	message.setProperty("anInboundKey", "this is a Value", PropertyScope.INBOUND);
    	message.setProperty("anOutboundKey", "this is a Value", PropertyScope.OUTBOUND);
    	message.setProperty("anInvocationaKey", "aValue", PropertyScope.INVOCATION);
    	message.setProperty("aSessionKey", 3, PropertyScope.SESSION);
	}
	
	@Test
    public void testPropertiesExamples() throws Exception
    {
		// Check if the property "aPropertyKey" exists in any scope
    	assertThat(message, hasPropertyInAnyScope("aPropertyKey"));

    	// Find the property "aPropertyKey" in any scope with value "aValue" 
    	assertThat(message, hasPropertyInAnyScope("aPropertyKey", is("aValue")));

    	// Find the property "aPropertyKey" in any scope with a non case sensitive value of "aValue" 
    	assertThat(message, hasPropertyInAnyScope("aPropertyKey", equalToIgnoringCase("aValue")));

    	// Check if the inbound property "aPropertyKey" exists
    	assertThat(message, hasInboundProperty("anInboundKey"));
    	
    	// Check if the inbound property "aPropertyKey" exists
    	assertThat(message, hasInboundProperty("anInboundKey", equalToIgnoringWhiteSpace("this   is   a   Value")));
    	
    	// Check if the outbound property "anOutboundKey" contains the value "is"
    	assertThat(message, hasOutboundProperty("anOutboundKey", containsString("is")));
    	
    	// Check if the session property "aSessionKey" is greater than 2
    	assertThat(message, hasSessionProperty("aSessionKey", greaterThan(2)));
    	
    	// Check if the invocation property "anInvocationaKey" is an instance of String
    	assertThat(message, hasInvocationProperty("anInvocationaKey", is(instanceOf(String.class))));
    }
	
	@Test
    public void testAttachmentExamples() throws Exception
    {
		// Check if the property "aPropertyKey" exists in any scope
    	assertThat(message, hasPropertyInAnyScope("aPropertyKey"));

    	// Find the property "aPropertyKey" in any scope with value "aValue" 
    	assertThat(message, hasPropertyInAnyScope("aPropertyKey", is("aValue")));

    	// Find the property "aPropertyKey" in any scope with a non case sensitive value of "aValue" 
    	assertThat(message, hasPropertyInAnyScope("aPropertyKey", equalToIgnoringCase("aValue")));

    	// Check if the inbound property "aPropertyKey" exists
    	assertThat(message, hasInboundProperty("anInboundKey"));
    	
    	// Check if the inbound property "aPropertyKey" exists
    	assertThat(message, hasInboundProperty("anInboundKey", equalToIgnoringWhiteSpace("this   is   a   Value")));
    	
    	// Check if the outbound property "anOutboundKey" contains the value "is"
    	assertThat(message, hasOutboundProperty("anOutboundKey", containsString("is")));
    	
    	// Check if the session property "aSessionKey" is greater than 2
    	assertThat(message, hasSessionProperty("aSessionKey", greaterThan(2)));
    	
    	// Check if the invocation property "anInvocationaKey" is an instance of String
    	assertThat(message, hasInvocationProperty("anInvocationaKey", is(instanceOf(String.class))));
    }
	
}
