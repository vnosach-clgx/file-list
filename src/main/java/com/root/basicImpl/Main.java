package com.root.basicImpl;

import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Stream;

@Slf4j
public class Main {
    private final boolean isRecursive;
    private final Path path;

    public Main(boolean isRecursive, Path path) {
        this.isRecursive = isRecursive;
        this.path = path;
    }

    public static void main(String... args) throws IOException {
        Path path = Paths.get(".");
        Main fileList = new Main(true, path);
        log.info("Files in folder {}: {}", path.toFile().getAbsolutePath(), fileList.filesCount());
        log.info("Folders in folder {}: {}", path.toFile().getAbsolutePath(), fileList.foldersCount());
        log.info("Files size in folder {}: {}", path.toFile().getAbsolutePath(), fileList.summaryFileSize());
    }

    private long filesCount() throws IOException {
        return pathStream()
                .filter(File::isFile)
                .count();
    }

    private long foldersCount() throws IOException {
        return pathStream()
                .filter(File::isDirectory)
                .count();
    }

    private long summaryFileSize() throws IOException {
        return pathStream()
                .filter(File::isFile)
                .map(File::length)
                .reduce(Long::sum)
                .orElse(0L);
    }

    private Stream<File> pathStream() throws IOException {
        Stream<Path> pathStream = isRecursive ? Files.walk(path) : Files.list(path);
        return pathStream.parallel()
                .map(Path::toFile);
    }
}
