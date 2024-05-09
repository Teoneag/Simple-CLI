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

            Parameter[] parameters = command.getMethod().getParameters();
            if (parameters.length != parts.length - 1) {
                System.out.println("Invalid number of arguments for command '" + parts[0] + "'" + typeHelp);
                continue;
            }

            Object[] args = new Object[parameters.length];
            for (int i = 0; i < parameters.length; i++) {
                // Todo convert the type of the pars
                args[i] = parts[i + 1];
            }
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

    private void addCommands(Class<?> clazz) {
        for (Method method : clazz.getDeclaredMethods()) {
            if (!method.isAnnotationPresent(com.teoneag.annotations.Command.class)) {
                continue;
            }
            Command command = new Command(method);
            commands.put(Command.makeCommandName(method), command);
        }
    }
}
