<div align="center">
<pre>
███████╗██╗███╗   ███╗██████╗ ██╗     ███████╗     ██████╗██╗     ██╗
██╔════╝██║████╗ ████║██╔══██╗██║     ██╔════╝    ██╔════╝██║     ██║
███████╗██║██╔████╔██║██████╔╝██║     █████╗      ██║     ██║     ██║
╚════██║██║██║╚██╔╝██║██╔═══╝ ██║     ██╔══╝      ██║     ██║     ██║
███████║██║██║ ╚═╝ ██║██║     ███████╗███████╗    ╚██████╗███████╗██║
╚══════╝╚═╝╚═╝     ╚═╝╚═╝     ╚══════╝╚══════╝     ╚═════╝╚══════╝╚═╝
</pre>
<div align="right">

A simple library for an interactive CLI.

By [Teodor Neagoe](https://github.com/Teoneag)

</div>
<img src="gifs/Simple-CLI Preview.gif" alt="Simple-CLI"/>
</div>

## Getting Started

### Prerequisites

- Os: Windows


## Download

### 1. Clone the repository

```bash
git clone https://github.com/Teoneag/Simple-CLI
```

### 2. Build & run

To build it run
```bash
./gradlew build
```

And then to run it run
```bash
java -cp build/libs/Simple-CLI-1.0-SNAPSHOT.jar com.teoneag.Main
```

Or you can run it directly from gradle (but IO is a bit slower)

```bash
./gradlew run -q --console=plain
```

Or you can use IntelliJ IDEA to run it. (open the project and run the Main class)

## Usage

This is an example of how to use the library:

```java
class mainMenu {
    // optional name - default: method name
    // optional description - default: method name (args) -> return type
    @Command(name = "hello", description = "Prints hello + <name>")
    public void hello(
        @Param(name = "name", description = "name to print")
        String name) {
        System.out.println("Hello " + name);
    }

    public static void main(String[] args) {
        Shell shell = new Shell(mainMenu.class);
        shell.start();
    }
}
```

## Plan -> Actual: 

Chronological order. Planned time -> actual time
- read similar projects (cliche) + write task + plan: 20m -> 40m
- make annotations: 5m -> 5m
- Write the Shell class: constructor + start method: 1h -> 2h
- check parameter types: 20 min -> 40m
- test it + write tests: 30m
- document it: 15m

## ToDo

- fix deprecated gradle features
- github description