package org.example;

import org.apache.fory.Fory;

import java.util.Objects;

public class App {
    private static final Fory FORY = Fory.builder()
            .withRefTracking(true)
            .registerGuavaTypes(false)
            .build();

    static {
        FORY.register(Custom1.class, true);
        FORY.register(Custom2.class, true);
        FORY.register(App.class, true);
        FORY.ensureSerializersCompiled();
    }

    private final Custom1 inner = new Custom1(42);
    private final Custom2 field = new Custom2(43);

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
        assertEquals(from.inner.i, to.inner.i);
        assertEquals(from.field.i, to.field.i);
    }

    private record Custom1(int i) {
    }
    private static final class Custom2 {
        final int i;
        Custom2(int i) {
            this.i = i;
        }
    }
}
