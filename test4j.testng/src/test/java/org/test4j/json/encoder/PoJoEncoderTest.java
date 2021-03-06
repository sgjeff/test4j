package org.test4j.json.encoder;

import org.test4j.fortest.beans.Manager;
import org.test4j.json.JSON;
import org.test4j.json.helper.JSONFeature;
import org.test4j.testng.Test4J;
import org.testng.annotations.Test;

@Test
public class PoJoEncoderTest extends Test4J {
	String json = "";

	public void testPoJoEncoder() {
		Manager manager = Manager.mock();
		this.json = JSON.toJSON(manager, JSONFeature.UseSingleQuote);
		want.string(json).contains("Tony Tester");
	}

	@Test(description = "对象有多重继承的情况", dependsOnMethods = "testPoJoEncoder")
	public void testPoJoDecoder() {
		Manager manager = JSON.toObject(json, Manager.class);
		want.object(manager).propertyEq("name", "Tony Tester");
	}
}
