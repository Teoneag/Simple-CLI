package com.teoneag;

import java.util.Map;

class DefaultCommands {
    private final Map<String, CommandInfo> commands;

    public DefaultCommands(Map<String, CommandInfo> commands) {
        this.commands = commands;
    }

    /**
     * Prints the available commands
     */
    @Command(description = "Prints the available commands")
    public void help() {
        System.out.println("These are the available commands:");
        for (String command : commands.keySet()) {
            System.out.println("    " + command + " : " + commands.get(command).getDescription());
        }
    }

    /**
     * Exits the program
     */
    @Command(description = "Exits the program")
    public void exit() {
        System.out.println("Thank you for using Simple-CLI. Goodbye!");
        System.exit(0);
    }
}
