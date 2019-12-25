package goozy.springstudy.service;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.*;

class SequenceGeneratorTest {

    private static int COUNT = 1000;

    @Test
    // Thread Safe 하지 않기 때문에 항상 동일한 결과가 나오지 않는다.
    public void givenUnsafeSequenceGenerator_whenRaceCondition_thenUnexpectedBehavior() throws Exception {
        Set<Integer> uniqueSequences = getUniqueSequences(new SequenceGenerator(), COUNT);
        assertNotEquals(COUNT, uniqueSequences.size());
    }

    @Test
    public void givenSequenceGeneratorUsingMonitor_whenRaceCondition_thenSuccess() throws Exception {
        Set<Integer> uniqueSequences = getUniqueSequences(new SequenceGeneratorUsingMonitor(), COUNT);
        assertEquals(COUNT, uniqueSequences.size());
    }

    @Test
    public void givenSequenceGeneratorUsingSynchronizedMethod_whenRaceCondition_thenSuccess() throws Exception {
        Set<Integer> uniqueSequences = getUniqueSequences(new SequenceGeneratorUsingSynchronizedMethod(), COUNT);
        assertEquals(COUNT, uniqueSequences.size());
    }

    @Test
    public void givenSequenceGeneratorUsingSynchronizedBlock_whenRaceCondition_thenSuccess() throws Exception {
        Set<Integer> uniqueSequences = getUniqueSequences(new SequenceGeneratorUsingSynchronizedBlock(), COUNT);
        assertEquals(COUNT, uniqueSequences.size());
    }

    private Set<Integer> getUniqueSequences(SequenceGenerator generator, int count) throws Exception {
        ExecutorService executor = Executors.newFixedThreadPool(3);
        Set<Integer> uniqueSequences = new LinkedHashSet<>();
        List<Future<Integer>> futures = new ArrayList<>();

        for (int i = 0; i < count; i++) {
            futures.add(executor.submit(generator::getNextSequence));
        }

        for (Future<Integer> future : futures) {
            uniqueSequences.add(future.get());
        }

        executor.awaitTermination(1, TimeUnit.SECONDS);
        executor.shutdown();

        return uniqueSequences;
    }
}