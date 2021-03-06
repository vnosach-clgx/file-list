package com.root;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import picocli.CommandLine;

import java.nio.file.Path;
import java.util.concurrent.Callable;
import java.util.concurrent.ForkJoinPool;

@Slf4j
@CommandLine.Command(name = "counts", mixinStandardHelpOptions = true, version = "counts 1.0",
        description = "Prints the folder and file counts and size")
public class FileList implements Callable<Integer> {

    @CommandLine.Parameters(index = "0", description = "The path in which calculate")
    private Path path;

    @CommandLine.Option(names = {"-r", "--recursive"})
    @Getter
    private boolean isRecursive;

    @Override
    public Integer call() {
        ConsoleAnimation consoleAnimation = ConsoleAnimation.startAnimated();

        FileServiceTask fileServiceTask = new FileServiceTask(path, isRecursive);
        Result result = ForkJoinPool.commonPool().invoke(fileServiceTask);

        consoleAnimation.stop();
        System.out.println("Total files count: " + result.getFileCount());
        System.out.println("Total folders count: " + result.getFolderCount());
        System.out.println("Total files size: " + result.getFilesSize());
        return 0;
    }

}
