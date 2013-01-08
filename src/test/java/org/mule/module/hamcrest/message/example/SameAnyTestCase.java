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

import static org.mule.module.hamcrest.message.MessageMatchers.*;

import static org.junit.Assert.assertThat;

import org.junit.Before;
import org.junit.Test;
import org.mule.DefaultMuleMessage;
import org.mule.api.MuleMessage;
import org.mule.tck.junit4.FunctionalTestCase;

public class SameAnyTestCase extends FunctionalTestCase
{
	
	private MuleMessage messageA;
	
	private MuleMessage messageB;
	
	private MuleMessage messageC;
	
	@Override
	protected String getConfigResources() {
		return "hamcrest-functional-test-config.xml";
	}
	
	@Before
	public void doSetup() throws Exception {
		messageA = new DefaultMuleMessage(null, muleContext);
    	messageA.setOutboundProperty("aKey", "aValue");
    	
    	messageB = new DefaultMuleMessage(null, muleContext);
    	messageB.setOutboundProperty("aKey", "aValue");
    	
    	messageC = new DefaultMuleMessage(null, muleContext);
    	messageC.setOutboundProperty("weird", "value");
	}
	
	@Test
    public void testPropertiesExamples() throws Exception
    {
		// Checks that messageA have the same outbound properties than messageB
		assertThat(messageA, hasSameOutboundPropertiesThan(messageB));
		
		// Checks that messageA have the same outbound properties than messageB
		assertThat(messageA, hasSameAttachmentsThan(messageB));
		
		// Checks that messageA have the same properties, attachments and payload than messageB
		assertThat(messageA, hasSameExternalValuesThan(messageB));
    }
	
}
