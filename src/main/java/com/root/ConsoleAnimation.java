package com.root;

import org.apache.commons.collections4.queue.CircularFifoQueue;

import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

import static java.util.concurrent.TimeUnit.MILLISECONDS;

public class ConsoleAnimation {

    private final CircularFifoQueue<String> circularFifoQueue = new CircularFifoQueue(List.of("[ \\ ]", "[ | ]", "[ / ]", "[ - ]"));
    private final ScheduledExecutorService executorService = Executors.newScheduledThreadPool(1);

    public static ConsoleAnimation startAnimated() {
        ConsoleAnimation consoleAnimation = new ConsoleAnimation();
        consoleAnimation.start();
        return consoleAnimation;
    }

    public void stop() {
        System.out.println("\r");
        executorService.shutdown();
    }

    public void start() {
        executorService.scheduleAtFixedRate(this::print, 0, 250, MILLISECONDS);
    }

    private void print() {
        String poll = circularFifoQueue.poll();
        System.out.print("\r" + poll + " Analyzing");
        circularFifoQueue.add(poll);
    }

}
