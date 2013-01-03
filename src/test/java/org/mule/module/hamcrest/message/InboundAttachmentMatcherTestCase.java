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
import static org.junit.Assert.assertThat;
import static org.mule.module.hamcrest.message.InboundAttachmentMatcher.hasInboundAttachment;

import org.hamcrest.StringDescription;
import org.junit.Before;
import org.junit.Test;
import org.mule.DefaultMuleMessage;
import org.mule.api.MuleMessage;
import org.mule.tck.junit4.FunctionalTestCase;
import org.mule.tck.testmodels.fruit.Banana;

public class InboundAttachmentMatcherTestCase extends FunctionalTestCase
{
	
	private Banana banana;
	
	private MuleMessage inboundMessage;
	
	@Before
	public void doSetup() throws Exception {
		MuleMessage message = new DefaultMuleMessage(null, muleContext);
		banana = new Banana();
    	message.addOutboundAttachment("anAttachment", banana, "application/vnd.mule.banana");
		inboundMessage = message.createInboundMessage();
	}
	
	@Override
	protected String getConfigResources() {
		return "hamcrest-functional-test-config.xml";
	}
	
	@Test
    public void testHasInboundProperty() throws Exception
    {
    	assertThat(inboundMessage, hasInboundAttachment("anAttachment"));
    }
	
	@Test
    public void testHasInboundPropertyWithMatcher() throws Exception
    {
    	assertThat(inboundMessage, hasInboundAttachment("anAttachment", is(banana)));
    }
	
	@Test
    public void testHasInboundPropertyWithValue() throws Exception
    {
    	assertThat(inboundMessage, hasInboundAttachment("anAttachment"));
    }
	
	@Test
    public void testDescription() throws Exception
    {
		StringDescription description = new StringDescription();
		new InboundAttachmentMatcher("key", is("value")).describeTo(description);
	    assertThat(description.toString(), is("a MuleMessage with an inbound attachment with key \"key\" and value is \"value\""));
    }
	
}
