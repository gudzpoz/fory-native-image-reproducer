package org.example;

import org.apache.fory.Fory;

public class App {
    private static final Fory FORY = Fory.builder()
            .withRefTracking(true)
            .registerGuavaTypes(false)
            .build();

    static {
        FORY.register(AppInnerImpl.class, true);
        FORY.register(App.class, true);
    }

    AppInner inner = new AppInnerImpl("test");

    public static void main(String[] args) {
        FORY.reset();
        App from = new App();
        byte[] bytes = FORY.serialize(from);
        FORY.reset();
        App to = (App) FORY.deserialize(bytes);
        assert to.inner.toString().equals("test");
    }

    private static abstract class AppInner {
        private final String s;

        AppInner(String s) {
            this.s = s;
        }

        @Override
        public String toString() {
            return s;
        }
    }

    private static class AppInnerImpl extends AppInner {
        AppInnerImpl(String s) {
            super(s);
        }
    }
}
