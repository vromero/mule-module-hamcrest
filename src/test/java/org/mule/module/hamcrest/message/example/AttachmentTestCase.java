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
import static org.mule.module.hamcrest.MessageMatchers.*;

import static org.junit.Assert.assertThat;

import org.junit.Before;
import org.junit.Test;
import org.mule.DefaultMuleMessage;
import org.mule.api.MuleMessage;
import org.mule.tck.junit4.FunctionalTestCase;
import org.mule.tck.testmodels.fruit.Banana;
import org.mule.tck.testmodels.fruit.Orange;

public class AttachmentTestCase extends FunctionalTestCase
{
	
	private MuleMessage message;
	
	@Override
	protected String getConfigResources() {
		return "hamcrest-functional-test-config.xml";
	}
	
	@Before
	public void doSetup() throws Exception {
		message = new DefaultMuleMessage(null, muleContext);
		Banana banana = new Banana();
		Orange orange = new Orange();
    	message.addOutboundAttachment("anInboundAttachment", banana, "application/vnd.mule.banana");
		message = message.createInboundMessage();
		message.addOutboundAttachment("anOutboundAttachment", orange, "application/vnd.mule.orange");
	}

	@Test
    public void testAttachmentExamples() throws Exception
    {
    	// Check if the inbound attachment "anInboundAttachment" exists
    	assertThat(message, hasInboundAttachment("anInboundAttachment"));
    	
    	// Check if the inbound attachment "anInboundAttachment" is an instance of Orange.class
    	assertThat(message, hasInboundAttachment("anInboundAttachment", is(instanceOf(Banana.class))));
    	
    	// Check if the outbound attachment "anOutboundAttachment" is not an instance of Orange.class
    	assertThat(message, hasOutboundAttachment("anOutboundAttachment", is(not(instanceOf(Banana.class)))));
    }
	
}
