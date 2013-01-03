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
import static org.mule.module.hamcrest.message.InboundPropertyMatcher.hasInboundProperty;

import org.junit.Before;
import org.junit.Test;
import org.mule.DefaultMuleMessage;
import org.mule.api.MuleMessage;
import org.mule.api.transport.PropertyScope;
import org.mule.tck.junit4.FunctionalTestCase;

public class InboundPropertyMatcherTestCase extends FunctionalTestCase {

	private MuleMessage message;
	
	@Override
	protected String getConfigResources() {
		return "hamcrest-functional-test-config.xml";
	}
	
	@Before
	public void doSetup() throws Exception {
		message = new DefaultMuleMessage(null, muleContext);
		message.setProperty("aKey", "aValue", PropertyScope.INBOUND);
	}
	
	@Test
	public void testHasInboundProperty() throws Exception {
		assertThat(message, hasInboundProperty("aKey"));
	}
	
	@Test
	public void testHasInboundPropertyWithMatcher() throws Exception {
		assertThat(message, hasInboundProperty("aKey", is("aValue")));
	}

	@Test
	public void testHasInboundPropertyWithValue() throws Exception {
		assertThat(message, hasInboundProperty("aKey", "aValue"));
	}
	
}
