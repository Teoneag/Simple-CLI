package com.teoneag;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Shell {
    private final String shellName;
    private Object commandObject = null;
    private final Map<String, Command> commands = new HashMap<>();
    private final DefaultCommands defaultCommands = new DefaultCommands(commands);

    public Shell(Class<?> clazz) {
        this(clazz, clazz.getSimpleName());
    }

    /**
     * Creates a new shell with the given class and name
     * @param clazz the class to get the commands from
     * @param shellName the name of the shell
     */
    public Shell(Class<?> clazz, String shellName) {
        this.shellName = shellName;
        addCommands(DefaultCommands.class);
        addCommands(clazz);
        try {
            commandObject = clazz.getDeclaredConstructor().newInstance();
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException |
                 NoSuchMethodException ignored) {
        }
    }

    /**
     * Starts the shell
     */
    public void start() {
        String typeHelp = ". Type 'help' for the list of commands.";
        System.out.println("Welcome to " + shellName + typeHelp);

        Scanner userInput = new Scanner(System.in);

        while (true) {
            String line = userInput.nextLine();
            String[] parts = line.split("\\s+");
            Command command = commands.get(parts[0]);

            if (command == null) {
                System.out.println("Command not found: '" + parts[0] + "'" + typeHelp);
                continue;
            }

            Object[] args = convertArgs(parts, command.getMethod().getParameters());
            if (args == null) continue;

            try {
                if (command.getMethod().getDeclaringClass().equals(DefaultCommands.class)) {
                    command.getMethod().invoke(defaultCommands, args);
                } else {
                    command.getMethod().invoke(commandObject, args);
                }
            } catch (IllegalAccessException | InvocationTargetException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private Object[] convertArgs(String[] parts, Parameter[] parameters) {
        if (parameters.length != parts.length - 1) {
            System.out.println("Invalid number of arguments for command '" + parts[0] + "'");
            return null;
        }
        Object[] args = new Object[parameters.length];
        for (int i = 0; i < parameters.length; i++) {
            try {
                args[i] = TypeConverter.convert(parts[i + 1], parameters[i].getType());
            } catch (IllegalArgumentException e) {
                System.out.println("Wrong param type for command '" + parts[0] + "', argument nr " + i
                    + ". Expected type " + parameters[i].getType().getSimpleName());
                return null;
            }
        }
        return args;
    }

    private void addCommands(Class<?> clazz) {
        for (Method method : clazz.getDeclaredMethods()) {
            if (!method.isAnnotationPresent(CommandAnnotation.class)) continue;

            if (method.getReturnType() != void.class) {
                throw new RuntimeException("Command method must return void: " + method);
            }

            for (Parameter param : method.getParameters()) {
                if (!TypeConverter.isSupported(param.getType())) {
                    throw new RuntimeException("Unsupported param type: " + param.getType() + ", in method: " + method);
                }
            }
            Command command = new Command(method);
            commands.put(Command.makeCommandName(method), command);
        }
    }
}
