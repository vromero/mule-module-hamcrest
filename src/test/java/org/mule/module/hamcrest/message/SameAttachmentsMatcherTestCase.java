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

import static org.hamcrest.CoreMatchers.not;
import static org.junit.Assert.assertThat;
import static org.mule.module.hamcrest.message.SameAttachmentsMatcher.*;

import org.junit.Before;
import org.junit.Test;
import org.mule.DefaultMuleMessage;
import org.mule.api.MuleMessage;
import org.mule.tck.junit4.FunctionalTestCase;
import org.mule.tck.testmodels.fruit.Banana;
import org.mule.tck.testmodels.fruit.Kiwi;

public class SameAttachmentsMatcherTestCase extends FunctionalTestCase {

	private MuleMessage messageA;
	
	private MuleMessage messageB;
	
	private MuleMessage messageC;
	
	@Override
	protected String getConfigResources() {
		return "hamcrest-functional-test-config.xml";
	}
	
	@Before
	public void doSetup() throws Exception {
		Banana banana = new Banana();
		Kiwi kiwi = new Kiwi();
		
		messageA = new DefaultMuleMessage(null, muleContext);
		messageA.addOutboundAttachment("anInboundAttachment", banana, "application/vnd.mule.banana");
		messageA = messageA.createInboundMessage();
		messageA.addOutboundAttachment("anOutboundAttachment", kiwi, "application/vnd.mule.kiwi");
    	
		messageB = new DefaultMuleMessage(null, muleContext);
		messageB.addOutboundAttachment("anInboundAttachment", banana, "application/vnd.mule.banana");
		messageB = messageB.createInboundMessage();
		messageB.addOutboundAttachment("anOutboundAttachment", kiwi, "application/vnd.mule.kiwi");
		
		messageC = new DefaultMuleMessage(null, muleContext);
	}
	
	
	@Test
	public void testHasSameOutboundAttachments() throws Exception {
		assertThat(messageA,
				hasSameOutboundAttachmentsThan(messageB));
	}
	
	@Test
	public void testNotHasSameOutboundAttachments() throws Exception {
		assertThat(messageA,
				not(hasSameOutboundAttachmentsThan(messageC)));
	}
	
	@Test
	public void testHasSameAttachmentsInAllScopes() throws Exception {
		assertThat(messageA,
				hasSameAttachmentsThan(messageB));
	}
	
	@Test
	public void testNotHasSameAttachmentsInAllScopes() throws Exception {
		assertThat(messageA,
				not(hasSameAttachmentsThan(messageC)));
	}
	
}
