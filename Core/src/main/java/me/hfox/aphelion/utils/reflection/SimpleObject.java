package me.hfox.aphelion.utils.reflection;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class SimpleObject {

    private Class<?> type;
    private Object object;

    public SimpleObject(Object object) {
        this(object, object.getClass());
    }

    public SimpleObject(Class<?> type) {
        this(null, type);
    }

    public SimpleObject(Object object, Class<?> type) {
        this.object = object;
        this.type = type;
    }

    public Object getObject() {
        return object;
    }

    public SimpleMethod method(String name, Class<?>... params) {
        Method method = null;

        Class<?> cls = type;
        do {
            try {
                method = cls.getDeclaredMethod(name, params);
            } catch (NoSuchMethodException ignored) {
            }

            cls = cls.getSuperclass();
        } while (method == null && cls != Object.class);

        return method == null ? null : new SimpleMethod(this, method);
    }

    public SimpleField field(String name) {
        Field field = null;

        Class<?> cls = type;
        do {
            try {
                field = cls.getDeclaredField(name);
            } catch (NoSuchFieldException ignored) {
            }

            cls = cls.getSuperclass();
        } while (field == null && cls != Object.class);

        return field == null ? null : new SimpleField(this, field);
    }

}
