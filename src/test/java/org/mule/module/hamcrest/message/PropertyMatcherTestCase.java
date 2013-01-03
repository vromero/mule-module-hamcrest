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
import static org.mule.module.hamcrest.message.PropertyMatcher.hasProperty;

import org.hamcrest.StringDescription;
import org.junit.Before;
import org.junit.Test;
import org.mule.DefaultMuleMessage;
import org.mule.api.MuleMessage;
import org.mule.api.transport.PropertyScope;
import org.mule.tck.junit4.FunctionalTestCase;

public class PropertyMatcherTestCase extends FunctionalTestCase {

	private MuleMessage message;
	
	@Override
	protected String getConfigResources() {
		return "hamcrest-functional-test-config.xml";
	}
	
	@Before
	public void doSetup() throws Exception {
		message = new DefaultMuleMessage(null, muleContext);
    	message.setOutboundProperty("aKey", "aValue");
	}

	@Test
	public void testHasOutboundPropertyValueWithMatcher() throws Exception {
		assertThat(message,
				hasProperty(PropertyScope.OUTBOUND, "aKey", is("aValue")));
	}

	@Test
	public void testNotHasOutboundPropertyValue() throws Exception {
		assertThat(
				message,
				not(hasProperty(PropertyScope.OUTBOUND, "AnotherKey", "aValue")));
	}

	@Test
	public void testHasOutboundPropertyWithMatcher() throws Exception {
		assertThat(message, hasProperty(PropertyScope.OUTBOUND, "aKey"));
	}

	@Test
	public void testNotHasOutboundPropertyWithMatcher() throws Exception {
		assertThat(message,
				not(hasProperty(PropertyScope.OUTBOUND, "AnotherKey")));
	}
	
	@Test
    public void testDescription() throws Exception
    {
		StringDescription description = new StringDescription();
		new PropertyMatcher(PropertyScope.OUTBOUND, "key", is("value")).describeTo(description);
	    assertThat(description.toString(), is("a MuleMessage with a property with key \"key\" in scope <outbound> is \"value\""));
    }

}
