package org.example;

import org.apache.fory.Fory;

import java.util.Objects;

public class App {
    private static final Fory FORY = Fory.builder()
            .withRefTracking(true)
            .registerGuavaTypes(false)
            .build();

    static {
        FORY.register(ConcreteClass.class);
        FORY.register(App.class);
        FORY.ensureSerializersCompiled();
    }

    AbstractClass abstractClass = new ConcreteClass(42);

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
        assertEquals(42, to.abstractClass.i);
    }

    public abstract static class AbstractClass {
        final int i;
        protected AbstractClass(int i) {
            this.i = i;
        }
    }
    private static class ConcreteClass extends AbstractClass {
        private ConcreteClass(int i) {
            super(i);
        }
        // The following works:
//        private ConcreteClass(int i, int j, int k) {
//            super(i, j, k);
//        }
    }
}
