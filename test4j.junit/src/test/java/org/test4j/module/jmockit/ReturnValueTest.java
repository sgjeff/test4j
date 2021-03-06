package org.test4j.module.jmockit;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import mockit.Mocked;

import org.junit.Test;
import org.test4j.fortest.beans.ComplexObject;
import org.test4j.junit.Test4J;
import org.test4j.module.inject.annotations.Inject;

@SuppressWarnings("unchecked")
public class ReturnValueTest extends Test4J {

    public SomeService   someService = new SomeService();

    @Mocked
    @Inject(targets = "someService")
    public SomeInterface someInterface;

    @Test
    public void testMock() {
        new Expectations() {
            {
                someInterface.someCall(the.string().isEqualTo("darui.wu").wanted(),
                        the.collection().sizeEq(0).wanted(List.class), the.map().any().wanted(HashMap.class));
                result = ComplexObject.instance();

            }
        };
        String result = this.someService.call("darui.wu");
        want.string(result).contains("name=");
    }

    @Test(expected = RuntimeException.class)
    public void testMock_ThrowException() {
        new Expectations() {
            {
                someInterface.someCall(the.string().isEqualTo("darui.wu").wanted(),
                        the.collection().sizeEq(0).wanted(List.class), the.map().any().wanted(HashMap.class));
                thenThrow(new RuntimeException("test exception"));

            }
        };
        this.someService.call("darui.wu");
    }

    @Test
    public void testMock_CatchThrowException() {
        new Expectations() {
            {
                someInterface.someCall(the.string().isEqualTo("darui.wu").wanted(),
                        the.collection().sizeEq(0).wanted(List.class), the.map().any().wanted(HashMap.class));
                thenThrow(new RuntimeException("test exception"));

            }
        };
        try {
            this.someService.call("darui.wu");
        } catch (RuntimeException e) {
            want.string(e.getMessage()).isEqualTo("test exception");
        }
    }

    @Test
    public void testThrowException() throws InterruptedException, IOException {
        new Expectations() {
            {
                someInterface.someCallException();
                maxTimes = -1;
                thenThrow(new IOException("test exception"));

            }
        };
        this.someService.callThrowException("darui.wu");
    }

    @Test
    public void factualInvoke() {
        SomeInterface si = new SomeInterfaceImpl();
        ComplexObject so = si.someCall("darui.wu", null, null);
        want.object(so).propertyEq("name", "I am a test");
    }

    public static class SomeService {
        private SomeInterface someInterface;

        public String call(String name) {
            List<String> list = new ArrayList<String>();
            HashMap<String, String> map = new HashMap<String, String>();
            ComplexObject co = this.someInterface.someCall(name, list, map);
            return co.toString();
        }

        public String callThrowException(String name) throws InterruptedException {
            try {
                this.someInterface.someCallException();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return "";
        }
    }

    public static interface SomeInterface {
        public ComplexObject someCall(String name, List<?> list, HashMap<String, String> map);

        public void someCallException() throws IOException;
    }

    public static class SomeInterfaceImpl implements SomeInterface {
        @Override
        public ComplexObject someCall(String name, List<?> list, HashMap<String, String> map) {
            return ComplexObject.instance();
        }

        @Override
        public void someCallException() throws IOException {
        }
    }
}
