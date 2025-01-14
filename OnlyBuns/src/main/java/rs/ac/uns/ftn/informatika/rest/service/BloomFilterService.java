package rs.ac.uns.ftn.informatika.rest.service;

import org.springframework.stereotype.Component;
import java.util.BitSet;
import java.util.concurrent.locks.ReentrantLock;

@Component
public class BloomFilterService {
    private final BitSet bitSet;
    private final int bitSetSize;
    private final int[] hashSeeds;
    private final ReentrantLock lock;

    public BloomFilterService() {
        this.bitSetSize = 10000;
        this.bitSet = new BitSet(bitSetSize);
        this.hashSeeds = generateRandomSeeds(7);
        this.lock = new ReentrantLock();
    }

    private int[] generateRandomSeeds(int numberOfHashFunctions) {
        int[] seeds = new int[numberOfHashFunctions];
        for (int i = 0; i < numberOfHashFunctions; i++) {
            seeds[i] = i * 31 + 7;
        }
        return seeds;
    }

    public void add(String username) {
        lock.lock();
        try {
            for (int seed : hashSeeds) {
                int hash = hash(username, seed);
                bitSet.set(hash);
            }
        } finally {
            lock.unlock();
        }
    }

    public boolean mightContain(String username) {
        for (int seed : hashSeeds) {
            int hash = hash(username, seed);
            if (!bitSet.get(hash)) {
                return false;
            }
        }
        return true;
    }

    private int hash(String element, int seed) {
        int hash = 0;
        for (char c : element.toCharArray()) {
            hash = seed * hash + c;
        }
        return Math.abs(hash % bitSetSize);
    }
}

