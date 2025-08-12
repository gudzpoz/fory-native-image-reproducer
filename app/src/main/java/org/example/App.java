package org.example;

import org.apache.fory.Fory;

import java.util.AbstractCollection;
import java.util.Arrays;
import java.util.Iterator;

public class App {
    private static final Fory FORY = Fory.builder()
        .registerGuavaTypes(false)
        .build();

    static {
        FORY.register(App.class, true);
    }

    final int[] ints = new int[10];

    public static void main(String[] args) {
        FORY.reset();
        byte[] bytes = FORY.serialize(new App());
        FORY.reset();
        App main = (App) FORY.deserialize(bytes);
        System.out.println(Arrays.toString(main.ints));
    }
}
