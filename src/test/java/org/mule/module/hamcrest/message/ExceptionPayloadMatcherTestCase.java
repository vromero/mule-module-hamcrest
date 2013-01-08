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
import static org.junit.Assert.assertThat;
import static org.mule.module.hamcrest.message.ExceptionPayloadMatcher.hasExceptionPayload;

import org.junit.Before;
import org.junit.Test;
import org.mule.DefaultMuleEvent;
import org.mule.DefaultMuleMessage;
import org.mule.MessageExchangePattern;
import org.mule.api.ExceptionPayload;
import org.mule.api.MuleMessage;
import org.mule.message.DefaultExceptionPayload;
import org.mule.tck.junit4.FunctionalTestCase;

public class ExceptionPayloadMatcherTestCase extends FunctionalTestCase
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
	}
	
	@Test
    public void testNotHasPayload() throws Exception
    {
    	assertThat(message, not(hasExceptionPayload()));
    }
	
	@Test
    public void testHasPayload() throws Exception
    {
    	ExceptionPayload exceptionPayload = new DefaultExceptionPayload(new Exception());
    	message.setExceptionPayload(exceptionPayload);
    	assertThat(message, hasExceptionPayload(exceptionPayload));
    }

	@Test
    public void testHasPayloadWithMatcher() throws Exception
    {
    	ExceptionPayload exceptionPayload = new DefaultExceptionPayload(new Exception());
    	ExceptionPayload otherExceptionPayload = new DefaultExceptionPayload(new IllegalAccessException());
    	message.setExceptionPayload(exceptionPayload);
    	assertThat(message, hasExceptionPayload(is(not(otherExceptionPayload))));
    }
	
	@Test
    public void testDescriptionAndMismatch() throws Exception
    {
		ExceptionPayloadMatcher<?> p = new ExceptionPayloadMatcher<String>(is("payload"));
		DescriptionAssertor.assertDescription("a MuleMessage with an exception payload is \"payload\"", p);
		DescriptionAssertor.assertMismatchDescription("was a MuleMessage with an exception payload null", p, message);
    }
	
	
}
