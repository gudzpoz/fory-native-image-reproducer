package org.example;

import org.apache.fory.Fory;

import java.nio.charset.StandardCharsets;
import java.util.*;

public class App {
    private static final Fory FORY = Fory.builder()
        .registerGuavaTypes(false)
        .build();

    static {
        FORY.register(App.class, true);
    }

    final byte[] bytes = "01234567890".getBytes(StandardCharsets.UTF_8);
    final short[] shorts = new short[]{0xF01, 0xF02, 0xF03, 0xF04, 0xF05, 0xF06, 0xF07, 0xF08};
    final int[] ints = new int[]{0xF0F0F01, 0xF0F0F02, 0xF0F0F03, 0xF0F0F04};
    final long[] longs = new long[]{0x0FFF_0000_FFFF_0001L, 0x0FFF_0000_FFFF_0002L};
    final Object[] objects = {"A", "B"};

    public static void main(String[] args) {
        FORY.reset();
        byte[] bytes = FORY.serialize(new App());
        System.out.println(Arrays.toString(bytes));
        FORY.reset();
        App main = (App) FORY.deserialize(bytes);
        System.out.println(Arrays.toString(main.bytes));
        System.out.println(Arrays.toString(main.shorts));
        System.out.println(Arrays.toString(main.ints));
        System.out.println(Arrays.toString(main.longs));
        System.out.println(Arrays.toString(main.objects));
    }
}
