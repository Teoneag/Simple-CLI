package com.teoneag;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.stream.Collectors;

public class Command {
    private final Method method;
    private final String description;

    public Command(Method method) {
        this.method = method;
        this.description = makeCommandDescription(method);
    }

    public static String makeCommandName(Method method) {
        String name = method.getAnnotation(com.teoneag.annotations.Command.class).name();
        if (!name.isEmpty()) return name;
        return method.getName();
    }

    public static String makeCommandDescription(Method method) {
        String description = method.getAnnotation(com.teoneag.annotations.Command.class).description();
        if (!description.isEmpty()) return description;
        return method.getName() + '(' +
            Arrays.stream(method.getParameterTypes())
                .map(Class::getSimpleName).collect(Collectors.joining(", ")) +
            ") -> " + method.getReturnType().getSimpleName();
    }

    public Method getMethod() {
        return method;
    }

    public String getDescription() {
        return description;
    }
}
