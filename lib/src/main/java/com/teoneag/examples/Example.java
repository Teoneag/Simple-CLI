package com.teoneag.examples;

import com.teoneag.Shell;
import com.teoneag.annotations.Command;
import com.teoneag.annotations.Param;

public class Example {
    @Command(name = "hello", description = "Prints hello + <name>")
    public void test(
        @Param(name = "name", description = "name to print")
        String name) {
        System.out.println("Hello " + name);
    }

    public static void main(String[] args) {
        Shell shell = new Shell(Example.class);
        shell.start();
    }
}
