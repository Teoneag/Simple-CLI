package com.teoneag;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.stream.Collectors;

class CommandInfo {
    private final Method method;
    private final String description;

    public CommandInfo(Method method) {
        this.method = method;
        this.description = makeCommandDescription(method);
    }

    /**
     * If the method got a name from the annotation, use it. Otherwise, use the method name.
     * @param method to get the name from
     * @return the name
     */
    public static String makeCommandName(Method method) {
        String name = method.getAnnotation(Command.class).name();
        if (!name.isEmpty()) return name;
        return method.getName();
    }

    /**
     * If the method got a description from the annotation, use it. Otherwise, use the method name and parameters.
     * @param method to get the description from
     * @return the description
     */
    public static String makeCommandDescription(Method method) {
        String description = method.getAnnotation(Command.class).description();
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
