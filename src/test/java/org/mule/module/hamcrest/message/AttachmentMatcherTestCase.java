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
import static org.mule.module.hamcrest.message.AttachmentMatcher.*;

import org.junit.Before;
import org.junit.Test;
import org.mule.DefaultMuleMessage;
import org.mule.api.MuleMessage;
import org.mule.tck.junit4.FunctionalTestCase;
import org.mule.tck.testmodels.fruit.Banana;

public class AttachmentMatcherTestCase extends FunctionalTestCase
{
	
	private Banana banana;
	
	private MuleMessage message;
	
	@Before
	public void doSetup() throws Exception {
		message = new DefaultMuleMessage(null, muleContext);
		banana = new Banana();
    	message.addOutboundAttachment("anAttachment", banana, "application/vnd.mule.banana");
    	message = message.createInboundMessage();
    	message.addOutboundAttachment("anOutboundAttachment", banana, "application/vnd.mule.banana");
	}
	
	@Override
	protected String getConfigResources() {
		return "hamcrest-functional-test-config.xml";
	}
	
	@Test
    public void testHasInboundProperty() throws Exception
    {
    	assertThat(message, hasInboundAttachment("anAttachment"));
    }
	
	@Test
    public void testHasInboundPropertyWithMatcher() throws Exception
    {
    	assertThat(message, hasInboundAttachment("anAttachment", is(banana)));
    }
	
	@Test
    public void testHasInboundPropertyWithValue() throws Exception
    {
    	assertThat(message, hasInboundAttachment("anAttachment"));
    }
	
	@Test
    public void testHasOutboundProperty() throws Exception
    {
    	assertThat(message, hasOutboundAttachment("anOutboundAttachment"));
    }
	
	@Test
    public void testHasOutboundPropertyWithMatcher() throws Exception
    {
    	assertThat(message, hasOutboundAttachment("anOutboundAttachment", is(banana)));
    }
	
	@Test
    public void testHasOutboundPropertyWithValue() throws Exception
    {
    	assertThat(message, hasOutboundAttachment("anOutboundAttachment"));
    }
	
	@Test
    public void testDescriptionAndMismatch() throws Exception
    {
		AttachmentMatcher p = new AttachmentMatcher("aNonExistentAttachment", not(nullValue()));
		DescriptionAssertor.assertDescription("a MuleMessage with an inbound attachment in scope <INBOUND> with key \"aNonExistentAttachment\" and value not null", p);
		DescriptionAssertor.assertMismatchDescription("was a MuleMessage with an attachment in scope <INBOUND> with key \"aNonExistentAttachment\" and value null", p, message);
    }
	
}
