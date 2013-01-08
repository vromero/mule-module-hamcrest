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

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.assertThat;
import static org.mule.module.hamcrest.message.PayloadMatcher.hasPayload;

import org.junit.Before;
import org.junit.Test;
import org.mule.DefaultMuleMessage;
import org.mule.api.MuleMessage;
import org.mule.tck.junit4.FunctionalTestCase;

public class PayloadMatcherTestCase extends FunctionalTestCase
{
	
	private static final String PAYLOAD = "aValue";
	
	private MuleMessage message;
	
	@Override
	protected String getConfigResources() {
		return "hamcrest-functional-test-config.xml";
	}
	
	@Before
	public void doSetup() throws Exception {
		message = new DefaultMuleMessage(PAYLOAD, muleContext);
    	message.setOutboundProperty("aKey", "aValue");
	}
	
	@Test
    public void testHasPayload() throws Exception
    {
    	assertThat(message, hasPayload());
    }
	
	@Test
    public void testHasPayloadWithValue() throws Exception
    {
    	assertThat(message, hasPayload(PAYLOAD));
    }
	
	@Test
    public void testHasPayloadWithMatcher() throws Exception
    {
    	assertThat(message, hasPayload(is(PAYLOAD)));
    }

	@Test
    public void testHasDifferentPayloadWithMatcher() throws Exception
    {
    	assertThat(message, hasPayload(is(not("aDifferentValue"))));
    }
	
	@Test
    public void testDescriptionAndMismatch() throws Exception
    {
		PayloadMatcher<?> p = new PayloadMatcher<Object>(nullValue());
		DescriptionAssertor.assertDescription("a MuleMessage with a payload null", p);
		DescriptionAssertor.assertMismatchDescription("was a MuleMessage with a payload \"aValue\"", p, message);
    }
	
}
