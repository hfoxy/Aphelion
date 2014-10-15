package me.hfox.aphelion.utils.reflection;

import java.lang.reflect.Field;

public class SimpleField {

    private SimpleObject parent;
    private Field field;

    public SimpleField(SimpleObject parent, Field field) {
        this.parent = parent;
        this.field = field;
        this.field.setAccessible(true);
    }

    public Class<?> result() {
        return field.getType();
    }

    public Object value() {
        return value(result());
    }

    public <T> T value(Class<T> result) {
        try {
            return (T) field.get(parent.getObject());
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        return null;
    }

    public SimpleObject object() {
        return new SimpleObject(value());
    }

    public boolean set(Object value) {
        return set(result(), value);
    }

    public <T> boolean set(Class<T> result, Object value) {
        try {
            field.set(parent.getObject(), (T) value);
            return true;
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        return false;
    }

    public SimpleField resultSet(Object value) {
        return resultSet(result(), value);
    }

    public <T> SimpleField resultSet(Class<T> result, Object value) {
        if (set(result, value)) {
            return this;
        } else {
            return null;
        }
    }

    public SimpleMethod method(String name, Class<?>... params) {
        if (!Object.class.isAssignableFrom(result())) {
            return null;
        }

        try {
            return new SimpleMethod(object(), result().getDeclaredMethod(name, params));
        } catch (NoSuchMethodException e) {
            return null;
        }
    }

    public SimpleField field(String name) {
        if (!Object.class.isAssignableFrom(result())) {
            return null;
        }

        try {
            return new SimpleField(object(), result().getDeclaredField(name));
        } catch (NoSuchFieldException e) {
            return null;
        }
    }

}
