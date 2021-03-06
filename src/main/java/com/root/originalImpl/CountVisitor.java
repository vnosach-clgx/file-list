package com.root.originalImpl;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.List;

@Slf4j
class CountVisitor extends SimpleFileVisitor<Path> {

    @Getter
    private final Result result = new Result();
    private final List<FileServiceTask> walks;
    private final Path dir;
    private final boolean isRecursive;

    CountVisitor(List<FileServiceTask> walks, Path dir, boolean isRecursive) {
        this.walks = walks;
        this.dir = dir;
        this.isRecursive = isRecursive;
    }

    @Override
    public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) {
        result.addFolderCount(1);
        if (!dir.equals(this.dir)) {
            FileServiceTask w = new FileServiceTask(dir, isRecursive);
            w.fork();
            walks.add(w);
            return FileVisitResult.SKIP_SUBTREE;
        } else {
            return FileVisitResult.CONTINUE;
        }
    }

    @Override
    public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) {
        File visitFile = file.toFile();
        if (visitFile.isDirectory()) result.addFolderCount(1);
        if (visitFile.isFile()) {
            result.addFileCount(1);
            result.addFileSize(visitFile.length());
        }
        return FileVisitResult.CONTINUE;
    }

    @Override
    public FileVisitResult visitFileFailed(Path file, IOException exc) {
        log.warn("fail visit {}", file);
        return FileVisitResult.CONTINUE;
    }
}
