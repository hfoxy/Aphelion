package me.hfox.aphelion.command;

import me.hfox.aphelion.Aphelion;
import me.hfox.aphelion.exception.CommandException;
import me.hfox.aphelion.utils.Utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class CommandContext<U> {

    private static String defJoin = " ";

    private Aphelion<U> aphelion;
    private String label;
    private String[] args;
    private char[] flags;
    private Map<Character, String> values;

    public CommandContext(Aphelion<U> aphelion, String label, String[] args) {
        this.aphelion = aphelion;
        this.label = label;
        this.args = args;
    }

    public CommandContext(Aphelion<U> aphelion, String label, String[] args, char[] textFlags, char[] flags) throws CommandException {
        this(aphelion, label, args);

        List<String> arguments = new ArrayList<>();
        this.values = new HashMap<>();
        for (int i = 0; i < args.length; i++) {
            String arg = args[i];
            if (arg.matches("^-[a-zA-Z]+$")) {
                char[] values = arg.substring(1).toCharArray();
                for (char value : values) {
                    if (!contains(flags, value) && !contains(textFlags, value)) {
                        throw new CommandException("'" + value + "' is not a valid flag");
                    }

                    String text = null;
                    if (contains(textFlags, value)) {
                        text = args[i + 1];
                        i++;
                    }

                    this.values.put(value, text);
                }
            } else {
                arguments.add(arg);
            }
        }

        this.args = arguments.toArray(new String[arguments.size()]);

        int i = 0;
        this.flags = new char[values.size()];
        for (Entry<Character, String> entry : values.entrySet()) {
            this.flags[i] = entry.getKey();
            i++;
        }
    }

    public String getLabel() {
        return label;
    }

    public String[] getArguments() {
        return args;
    }

    public String[] args() {
        return args;
    }

    public char[] getFlags() {
        return flags;
    }

    public String getFlag(char flag) {
        return values.get(flag);
    }

    public String getFlag(char flag, String def) {
        return values.containsKey(flag) ? values.get(flag) : def;
    }

    public boolean hasFlag(char flag) {
        return values.containsKey(flag);
    }

    public int length() {
        return args.length;
    }

    public String getJoinedString(int start, int end, String join) {
        if(start >= args.length) {
            throw new IndexOutOfBoundsException("Start point (" + start + ") is exceeds array end point");
        }

        if(end >= args.length) {
            throw new IndexOutOfBoundsException("End point (" + end + ") is exceeds array end point");
        }

        int i2 = 0;
        String[] strings = new String[args.length - start];
        for(int i = start; i < args.length; i++, i2++) {
            strings[i2] = args[i];
        }

        return Utils.join(strings, join);
    }

    public String getJoinedString(int start, int end) {
        return getJoinedString(start, end, defJoin);
    }

    public String getJoinedString(int start, String join) {
        return getJoinedString(start, args.length - 1, join);
    }

    public String getJoinedString(int start) {
        return getJoinedString(start, defJoin);
    }

    public String getJoinedString(String join) {
        return getJoinedString(0, join);
    }

    public String getJoinedString() {
        return getJoinedString(0, defJoin);
    }

    public String getString(int position) {
        return args[position];
    }

    public int getInteger(int position) {
        return Integer.parseInt(args[position]);
    }

    public double getDouble(int position) {
        return Double.parseDouble(args[position]);
    }

    public float getFloat(int position) {
        return Float.parseFloat(args[position]);
    }

    public boolean getBoolean(int position) {
        if (args[position].equalsIgnoreCase("true") || args[position].equalsIgnoreCase("yes")) {
            return true;
        } else if (args[position].equalsIgnoreCase("false") || args[position].equalsIgnoreCase("no")) {
            return false;
        } else {
            throw new IllegalArgumentException("Boolean must either be true/false/yes/no");
        }
    }

    public boolean hasPermission(U sender, String perm) {
        return aphelion.hasPermission(sender, perm);
    }

    private static <E> boolean contains(E[] array, E item) {
        for (E entry : array) {
            if (entry.equals(item)) {
                return true;
            }
        }

        return false;
    }

    private static boolean contains(char[] array, char item) {
        Character[] characters = new Character[array.length];
        for (int i = 0; i < array.length; i++) {
            characters[i] = array[i];
        }

        return contains(characters, item);
    }

}
