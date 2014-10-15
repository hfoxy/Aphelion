package me.hfox.aphelion.utils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Utils {

    public static <T> List<T> asList(T[] array) {
        List<T> list = new ArrayList<>();
        Collections.addAll(list, array);
        return list;
    }

    public static <T> String join(T[] array, String join) {
        return join(array, join, array.length);
    }

    public static <T> String join(T[] array, String join, int limit) {
        String string = "";
        for (int i = 0; i < array.length && i < limit; i++) {
            String line = array[i].toString();
            if (i > 0) {
                string = string + join;
            }

            string = string + line;
        }

        return string;
    }

    public static <T> String join(List<T> list, String join) {
        return join(list, join, list.size());
    }

    public static <T> String join(List<T> list, String join, int limit) {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < list.size() && i < limit; i++) {
            builder.append(list.get(i).toString());

            int pos = i + 1;
            boolean last = pos < list.size() && pos < limit;
            if (!last) {
                builder.append(join);
            }
        }

        return builder.toString();
    }

}
