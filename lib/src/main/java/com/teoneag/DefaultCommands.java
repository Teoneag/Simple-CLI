package com.teoneag;

import com.teoneag.annotations.Command;

import java.util.Map;

public class DefaultCommands {
    private final Map<String, com.teoneag.Command> commands;

    public DefaultCommands(Map<String, com.teoneag.Command> commands) {
        this.commands = commands;
    }

    @Command(description = "Prints the available commands")
    public void help() {
        System.out.println("These are the available commands:");
        for (String command : commands.keySet()) {
            System.out.println("    " + command + " : " + commands.get(command).getDescription());
        }
    }

    @Command(description = "Exits the program")
    public void exit() {
        System.out.println("Thank you for using Simple-CLI. Goodbye!");
        System.exit(0);
    }
}
