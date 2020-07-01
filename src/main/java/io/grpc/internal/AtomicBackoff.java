package io.grpc.internal;

import com.google.common.base.Preconditions;
import java.util.concurrent.atomic.AtomicLong;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.concurrent.ThreadSafe;

@ThreadSafe
public final class AtomicBackoff {
    private static final Logger log = Logger.getLogger(AtomicBackoff.class.getName());
    private final String name;
    private final AtomicLong value = new AtomicLong();

    @ThreadSafe
    public final class State {
        static final /* synthetic */ boolean $assertionsDisabled = false;
        private final long savedValue;

        static {
            Class cls = AtomicBackoff.class;
        }

        private State(long j) {
            this.savedValue = j;
        }

        public long get() {
            return this.savedValue;
        }

        public void backoff() {
            long j = this.savedValue;
            if (AtomicBackoff.this.value.compareAndSet(this.savedValue, Math.max(2 * j, j))) {
                AtomicBackoff.log.log(Level.WARNING, "Increased {0} to {1}", new Object[]{AtomicBackoff.this.name, Long.valueOf(j)});
            }
        }
    }

    public AtomicBackoff(String str, long j) {
        Preconditions.checkArgument(j > 0, "value must be positive");
        this.name = str;
        this.value.set(j);
    }

    public State getState() {
        return new State(this.value.get());
    }
}
