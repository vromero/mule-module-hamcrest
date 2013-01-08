
This module provides a library of specialized [Hamcrest](http://code.google.com/p/hamcrest/) matchers to match rules against MuleMessages.

# Hamcrest
> [Hamcrest](http://code.google.com/p/hamcrest/) is a framework for writing matcher objects allowing 'match' rules to be defined declaratively. There are a number of situations where matchers are invaluble, such as UI validation, or data filtering, but it is in the area of writing flexible tests that matchers are most commonly used.

## Hamcrest Resources
* [Official site](http://code.google.com/p/hamcrest/).
* [Official tutorial](http://code.google.com/p/hamcrest/wiki/Tutorial).
* [Hamcrest usage](http://edgibbs.com/junit-4-with-hamcrest/) examples.
* [Tutorial](http://www.planetgeek.ch/2012/03/07/create-your-own-matcher/) for custom matchers.

# Installation
This module is intented to be used as test scoped dependency.
Add the expresion evaluator's maven repo to your pom.xml:

	<repositories>
	    <repository>
	        <id>muleforge-releases</id>
	        <name>MuleForge Snapshot Repository</name>
	        <url>http://repository.mulesoft.org/releases/</url>
	        <layout>default</layout>
	    </repository>
	</repositories>

Add the module as a dependency to your project. This can be done by adding the following at the top of the dependencies element in the pom.xml file of the application:

	<dependency>
	    <groupId>org.mule.modules</groupId>
	    <artifactId>mule-module-hamcrest</artifactId>
	    <version>1.0-SNAPSHOT</version>
		<scope>test</scope>
	</dependency>
	
	<dependency>
		<groupId>junit</groupId>
		<artifactId>junit-dep</artifactId>
		<version>4.9</version>
		<scope>test</scope>
		<exclusions>
			<exclusion>
				<groupId>org.hamcrest</groupId>
				<artifactId>hamcrest-core</artifactId>
			</exclusion>
		</exclusions>
	</dependency>
	
Exclude any transient dependency to junit (junit have a dependency to the legacy hamcrest version 1.1), by excluding the junit dependency of mule-core and  mule-test-functional.

	<dependency>
		<groupId>org.mule</groupId>
		<artifactId>mule-core</artifactId>
		<version>${mule.version}</version>
		<exclusions>
			<exclusion>
				<groupId>junit</groupId>
				<artifactId>junit</artifactId>
			</exclusion>
		</exclusions>
	</dependency>

	<dependency>
		<groupId>org.mule.tests</groupId>
		<artifactId>mule-tests-functional</artifactId>
		<version>${mule.version}</version>
		<exclusions>
			<exclusion>
				<groupId>junit</groupId>
				<artifactId>junit</artifactId>
			</exclusion>
		</exclusions>
	</dependency>

Using this plugin we can make sure we are not using any old hamcrest version:

	<plugin>
		<artifactId>maven-enforcer-plugin</artifactId>
		<version>1.0.1</version>
		<executions>
			<execution>
				<id>only-junit-dep-is-used</id>
				<goals>
					<goal>enforce</goal>
				</goals>
				<configuration>
					<rules>
						<bannedDependencies>
							<excludes>
								<exclude>junit:junit</exclude>
							</excludes>
						</bannedDependencies>
					</rules>
				</configuration>
			</execution>
		</executions>
	</plugin>

# Usage

# Examples

## MuleMessage

### Comparisons
```java
import static org.hamcrest.Matchers.*;
import static org.mule.module.hamcrest.message.MessageMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;


// Checks that messageA have the same outbound properties than messageB
assertThat(messageA, hasSameOutboundPropertiesThan(messageB));
		
// Checks that messageA have the same outbound properties than messageB
assertThat(messageA, hasSameAttachmentsThan(messageB));
		
// Checks that messageA have the same properties, attachments and payload than messageB
assertThat(messageA, hasSameExternalValuesThan(messageB));
```

### Properties and Variables
```java
import static org.hamcrest.Matchers.*;
import static org.mule.module.hamcrest.message.MessageMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;

// more stuff here

// Check if the property "aPropertyKey" exists in any scope
assertThat(message, hasPropertyInAnyScope("aPropertyKey"));

// Find the property "aPropertyKey" in any scope with value "aValue" 
assertThat(message, hasPropertyInAnyScope("aPropertyKey", is("aValue")));

// Find the property "aPropertyKey" in any scope with a non case sensitive value of "aValue" 
assertThat(message, hasPropertyInAnyScope("aPropertyKey", equalToIgnoringCase("aValue")));

// Check if the inbound property "aPropertyKey" exists
assertThat(message, hasInboundProperty("anInboundKey"));

// Check if the inbound property "aPropertyKey" exists
assertThat(message, hasInboundProperty("anInboundKey", equalToIgnoringWhiteSpace("this   is   a   Value")));

// Check if the outbound property "anOutboundKey" contains the value "is"
assertThat(message, hasOutboundProperty("anOutboundKey", containsString("is")));

// Check if the session property "aSessionKey" is greater than 2
assertThat(message, hasSessionProperty("aSessionKey", greaterThan(2)));

// Check if the invocation property "anInvocationaKey" is an instance of String
assertThat(message, hasInvocationProperty("anInvocationaKey", is(instanceOf(String.class))));
```

### Attachments
```java
import static org.hamcrest.Matchers.*;
import static org.mule.module.hamcrest.message.MessageMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;

// more stuff here

// Check if the inbound attachment "anInboundAttachment" exists
assertThat(message, hasInboundAttachment("anInboundAttachment"));

// Check if the inbound attachment "anInboundAttachment" is an instance of Orange.class
assertThat(message, hasInboundAttachment("anInboundAttachment", is(instanceOf(Banana.class))));

// Check if the outbound attachment "anOutboundAttachment" is not an instance of Orange.class
assertThat(message, hasOutboundAttachment("anOutboundAttachment", is(not(instanceOf(Banana.class)))));
```

### Payload
```java
import static org.hamcrest.Matchers.*;
import static org.mule.module.hamcrest.message.MessageMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;

// more stuff here

// Checks that the message has a non null payload
assertThat(message, hasPayload());

// Checks if the message has a payload that could be RED, GREEN or BLUE
assertThat(message, hasPayload(isOneOf(Color.RED, Color.GREEN, Color.BLUE)));

// Checks if the message has a payload that is exactly the same instance than Color.BLUE
assertThat(message, hasPayload(is(sameInstance(Color.BLUE))));
```

### ExceptionPayload

```java
import static org.hamcrest.Matchers.*;
import static org.mule.module.hamcrest.message.MessageMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;

// more stuff here

// Checks that the message has a non null exception payload
assertThat(message, not(hasExceptionPayload()));

// Checks that the message don't have an exception payload containing "FATAL"
assertThat(message, not(hasExceptionPayload(containsString("FATAL"))));
```


