package com.teoneag.examples;

import com.teoneag.Command;
import com.teoneag.Shell;

public class Example {
    @Command(name = "hello", description = "Prints hello + <name>")
    public void otherName(String name) {
        System.out.println("Hello " + name);
    }

    @Command(description = "Adds two numbers <a> and <b> and prints the result")
    public void add(int a, int b) {
        System.out.println(a + b);
    }

    @Command(name = "exit", description = "Exits the shell", stop = true)

    public static void main(String[] args) {
        Shell shell = new Shell(Example.class);
        shell.start();
    }
}
