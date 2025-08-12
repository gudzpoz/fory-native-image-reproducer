package org.example;

import org.apache.fory.Fory;

import java.util.*;

public class App extends AbstractMap<Integer, Integer> {
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

    @Override
    public Set<Entry<Integer, Integer>> entrySet() {
        HashSet<Entry<Integer, Integer>> set = new HashSet<>();
        for (int i = 0; i < ints.length; i++) {
            set.add(new AbstractMap.SimpleEntry<>(i, ints[i]));
        }
        return set;
    }
}
