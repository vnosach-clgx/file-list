package com.root;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.nio.file.FileVisitOption;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;
import java.util.concurrent.RecursiveTask;

@Slf4j
class FileServiceTask extends RecursiveTask<Result> {
    private final Path dir;
    private final boolean isRecursive;

    public FileServiceTask(Path dir, boolean isRecursive) {
        this.dir = dir;
        this.isRecursive = isRecursive;
    }

    @Override
    protected Result compute() {
        final List<FileServiceTask> walks = new ArrayList<>();
        CountVisitor visitor = new CountVisitor(walks, dir, isRecursive);
        try {
            if (isRecursive) {
                Files.walkFileTree(dir, EnumSet.noneOf(FileVisitOption.class), Integer.MAX_VALUE, visitor);
            } else {
                Files.walkFileTree(dir, EnumSet.noneOf(FileVisitOption.class), 1, visitor);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        Result result = visitor.getResult();
        for (FileServiceTask w : walks) {
            result.mergeResults(w.invoke());
        }
        return result;
    }

}
