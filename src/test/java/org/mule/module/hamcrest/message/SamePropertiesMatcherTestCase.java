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
import static org.mule.module.hamcrest.message.SamePropertiesMatcher.hasSamePropertiesThan;

import org.junit.Before;
import org.junit.Test;
import org.mule.DefaultMuleMessage;
import org.mule.api.MuleMessage;
import org.mule.api.transport.PropertyScope;
import org.mule.tck.junit4.FunctionalTestCase;

public class SamePropertiesMatcherTestCase extends FunctionalTestCase {

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
	public void testHasOutboundPropertyWithValue() throws Exception {
		assertThat(messageA,
				hasSamePropertiesThan(PropertyScope.OUTBOUND, messageB));
	}
	
	@Test
	public void testNotHasOutboundPropertyWithValue() throws Exception {
		assertThat(messageA,
				not(hasSamePropertiesThan(PropertyScope.OUTBOUND, messageC)));
	}
	
	@Test
	public void testhasSamePropertiesThanInAllScopes() throws Exception {
		assertThat(messageA,
				hasSamePropertiesThan(messageB));
	}
	
	@Test
	public void testNotHasSamePropertiesThanInAllScopes() throws Exception {
		assertThat(messageA,
				not(hasSamePropertiesThan(messageC)));
	}
	
}
