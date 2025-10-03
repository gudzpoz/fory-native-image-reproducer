package org.example;

import org.apache.fory.Fory;

import java.util.Objects;

public class App {
    private static final Fory FORY = Fory.builder()
            .withRefTracking(true)
            .registerGuavaTypes(false)
            .build();

    static {
        FORY.register(ConcreteObject.class);
        // The following commented-out line will result in a different error:
        //     AbstractObject doesn't support serialization
        //FORY.register(AbstractObject.class);
        FORY.register(AbstractObject[].class);
        FORY.register(App.class);
        FORY.ensureSerializersCompiled();
    }

    public static void assertEquals(Object a, Object b) {
        if (!Objects.equals(a, b)) {
            new AssertionError(a + " != " + b).printStackTrace(System.out);
        }
    }

    AbstractObject[] objects = new ConcreteObject[]{new ConcreteObject()};

    public static void main(String[] args) {
        try {
            FORY.reset();
            App from = new App();
            byte[] bytes = FORY.serialize(from);
            FORY.reset();
            App to = (App) FORY.deserialize(bytes);
            assertEquals(from.objects[0].i(), to.objects[0].i());
        } catch (Exception e) {
            e.printStackTrace(System.err);
        }
    }

    public abstract static class AbstractObject {
        public abstract int i();
    }
    public static class ConcreteObject extends AbstractObject {
        public int i = 42;
        @Override
        public int i() {
            return i;
        }
    }
}
