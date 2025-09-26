package org.example;

import org.apache.fory.Fory;
import org.apache.fory.memory.MemoryBuffer;
import org.apache.fory.serializer.Serializer;

public class App {
    private static final Fory FORY = Fory.builder()
            .withRefTracking(true)
            .registerGuavaTypes(false)
            .build();

    static {
        FORY.registerSerializer(Custom.class, new CustomSerializer(FORY));
        FORY.register(App.class, true);
        FORY.ensureSerializersCompiled();
    }

    private final Custom inner = new Custom(1);

    public static void main(String[] args) {
        FORY.reset();
        App from = new App();
        byte[] bytes = FORY.serialize(from);
        FORY.reset();
        App to = (App) FORY.deserialize(bytes);
        assert to.inner.i() == 42;
    }

    private record Custom(int i) {
    }
    private static final class CustomSerializer extends Serializer<Custom> {
        public CustomSerializer(Fory fory) {
            super(fory, Custom.class);
        }
        @Override
        public void write(MemoryBuffer buffer, Custom value) {
        }
        @Override
        public Custom read(MemoryBuffer buffer) {
            return new Custom(42);
        }
    }
}
