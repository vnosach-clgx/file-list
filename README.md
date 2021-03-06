# File list ForkJoinPool

First implementation has only simple 
```Files.list(path).parallel()``` streams, which can atomic counting every filesystem element. Parallel stream uses ForkJoinPool by default

I want to extend functionality by walking recursively under the filesystem in the result I catch multiple AccessDeniedExceptions on "C:/" directory

Advice to use FileVisitor brought me to original solution with several advantages:
    1. counters result joining after the thread completed;
    2. AccessDeniedExceptions does not present


----------------------------------------------------------------------------------
TASK INPUT:
Create CLI application that scans a specified folder and provides detailed statistics:

File count.
Folder count.
Size (sum of all files size) (similar like Windows context menu Properties). Since the folder may contain huge number of files the scanning process should be executed in a separate thread displaying an informational message with some simple animation like progress bar in CLI (up to you, but I'd like to see that task is in progress).
Once task is done, the statistics should be displayed in the output immediately. Additionally, there should be ability to interrupt the process pressing some reserved key (for instance c). Of course, use Fork-Join Framework for implementation parallel scanning.

Picocli aims to be the easiest way to create rich command line applications that can run on and off the JVM
