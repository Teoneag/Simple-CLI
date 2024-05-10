package com.teoneag.examples;

import com.teoneag.CommandAnnotation;
import com.teoneag.Shell;

public class Example {
    @CommandAnnotation(name = "hello", description = "Prints hello + <name>")
    public void test(String name) {
        System.out.println("Hello " + name);
    }

    @CommandAnnotation(description = "Adds two numbers <a> and <b> and returns the result")
    public int add(int a, int b) {
        return a + b;
    }

    public static void main(String[] args) {
        Shell shell = new Shell(Example.class);
        shell.start();
    }
}
