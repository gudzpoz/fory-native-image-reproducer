package org.example;

import org.apache.fory.Fory;

import java.util.Objects;

public class App {
    private static final Fory FORY = Fory.builder()
            .withRefTracking(true)
            .registerGuavaTypes(false)
            .build();

    static {
        FORY.register(MyEnum.IMPL.getClass());
        FORY.register(MyEnum.class);
        FORY.register(App.class);
        FORY.ensureSerializersCompiled();
    }

    MyEnum myEnum = MyEnum.IMPL;

    public static void assertEquals(Object a, Object b) {
        if (!Objects.equals(a, b)) {
            new AssertionError(a + " != " + b).printStackTrace(System.out);
        }
    }

    public static void main(String[] args) {
        FORY.reset();
        App from = new App();
        byte[] bytes = FORY.serialize(from);
        FORY.reset();
        App to = (App) FORY.deserialize(bytes);
        assertEquals(42, to.myEnum.i());
    }

    public enum MyEnum {
        IMPL {
            @Override
            public int i() {
                return 42;
            }
        };

        public abstract int i();
    }
}
