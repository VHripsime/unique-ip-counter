package ip_counter.service.data.impl;

import java.util.BitSet;
import java.util.Optional;

import ip_counter.service.data.DataProcessor;
import ip_counter.util.IpUtil;

public class UniqueIpCounter implements DataProcessor<String> {

    private static final long FIRST_UPPER_LIMIT = Integer.MAX_VALUE;
    private static final long SECOND_UPPER_LIMIT = 2 * FIRST_UPPER_LIMIT;

    private BitSet firstIpGroup;  // for 0 - 2,147,483,647 values
    private BitSet secondIpGroup; // for 2,147,483,648 - 4,294,967,294 values
    private BitSet thirdIpGroup;  // for 4,294,967,295 - 4,294,967,296 values

    private long uniqueIpCount;

    public UniqueIpCounter() {
        firstIpGroup = new BitSet(Integer.MAX_VALUE);
        secondIpGroup = new BitSet(Integer.MAX_VALUE);
        thirdIpGroup = new BitSet(2);
    }

    @Override
    public long getResult() {
        return uniqueIpCount;
    }

    @Override
    public void process(String ip) {
        if (ip == null || ip.isBlank()) {
            return;
        }

        Optional<Long> value = IpUtil.toLongValue(ip);
        if (value.isEmpty() || exists(value.get())) {
            return;
        }
        count(value.get());
    }

    private synchronized void count(long value) {
        if (exists(value)) {
            return;
        }
        markAsExisting(value);
        uniqueIpCount++;
    }

    private boolean exists(long value) {
        if (value <= FIRST_UPPER_LIMIT) {
            return firstIpGroup.get((int) value);
        }
        if (value <= SECOND_UPPER_LIMIT) {
            return secondIpGroup.get((int) (value - FIRST_UPPER_LIMIT - 1));
        }
        return thirdIpGroup.get((int) (value - SECOND_UPPER_LIMIT - 1));
    }

    private void markAsExisting(long value) {
        if (value <= FIRST_UPPER_LIMIT) {
            firstIpGroup.set((int) value);
            return;
        }
        if (value <= SECOND_UPPER_LIMIT) {
            secondIpGroup.set((int) (value - FIRST_UPPER_LIMIT - 1));
            return;
        }
        thirdIpGroup.set((int) (value - SECOND_UPPER_LIMIT - 1));
    }
}
