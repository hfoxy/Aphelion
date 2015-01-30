package me.hfox.aphelion.utils.reflection;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class SimpleMethod {

    private SimpleObject parent;
    private Method method;

    public SimpleMethod(SimpleObject parent, Method method) {
        this.parent = parent;
        this.method = method;
        this.method.setAccessible(true);
    }

    public Class<?> getMethodClass() {
        return method.getDeclaringClass();
    }

    public Method getMethod() {
        return method;
    }

    public Class<?> result() {
        return method.getReturnType();
    }

    public Object value(Object... params) {
        return value(result(), params);
    }

    public Object valueEx(Object... params) throws InvocationTargetException, IllegalAccessException {
        return valueEx(result(), params);
    }

    public <T> T value(Class<T> result, Object... params) {
        try {
            return valueEx(result, params);
        } catch (IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }

        return null;
    }

    @SuppressWarnings("unchecked")
    public <T> T valueEx(Class<T> result, Object... params) throws InvocationTargetException, IllegalAccessException {
        return (T) method.invoke(parent != null ? parent.getObject() : null, params);
    }

    public SimpleObject object(Object... params) {
        return new SimpleObject(value(params));
    }

    public SimpleMethod method(String name, Object[] params, Class<?>... classes) {
        if (!Object.class.isAssignableFrom(result())) {
            return null;
        }

        try {
            return new SimpleMethod(object(params), result().getDeclaredMethod(name, classes));
        } catch (NoSuchMethodException e) {
            return null;
        }
    }

    public SimpleField field(String name, Object... params) {
        if (!Object.class.isAssignableFrom(result())) {
            return null;
        }

        try {
            return new SimpleField(object(params), result().getDeclaredField(name));
        } catch (NoSuchFieldException e) {
            return null;
        }
    }

}
