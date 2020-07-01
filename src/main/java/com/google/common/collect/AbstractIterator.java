package com.google.common.collect;

import com.google.common.annotations.GwtCompatible;
import com.google.common.base.Preconditions;
import com.google.errorprone.annotations.CanIgnoreReturnValue;
import java.util.NoSuchElementException;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

@GwtCompatible
public abstract class AbstractIterator<T> extends UnmodifiableIterator<T> {
    @NullableDecl
    private T next;
    private State state = State.NOT_READY;

    /* renamed from: com.google.common.collect.AbstractIterator$1 */
    static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$com$google$common$collect$AbstractIterator$State = new int[State.values().length];

        /* JADX WARNING: Failed to process nested try/catch */
        /* JADX WARNING: Missing block: B:5:0x001f, code:
            return;
     */
        static {
            /*
            r0 = com.google.common.collect.AbstractIterator.State.values();
            r0 = r0.length;
            r0 = new int[r0];
            $SwitchMap$com$google$common$collect$AbstractIterator$State = r0;
            r0 = $SwitchMap$com$google$common$collect$AbstractIterator$State;	 Catch:{ NoSuchFieldError -> 0x0014 }
            r1 = com.google.common.collect.AbstractIterator.State.DONE;	 Catch:{ NoSuchFieldError -> 0x0014 }
            r1 = r1.ordinal();	 Catch:{ NoSuchFieldError -> 0x0014 }
            r2 = 1;
            r0[r1] = r2;	 Catch:{ NoSuchFieldError -> 0x0014 }
        L_0x0014:
            r0 = $SwitchMap$com$google$common$collect$AbstractIterator$State;	 Catch:{ NoSuchFieldError -> 0x001f }
            r1 = com.google.common.collect.AbstractIterator.State.READY;	 Catch:{ NoSuchFieldError -> 0x001f }
            r1 = r1.ordinal();	 Catch:{ NoSuchFieldError -> 0x001f }
            r2 = 2;
            r0[r1] = r2;	 Catch:{ NoSuchFieldError -> 0x001f }
        L_0x001f:
            return;
            */
            throw new UnsupportedOperationException("Method not decompiled: com.google.common.collect.AbstractIterator.1.<clinit>():void");
        }
    }

    private enum State {
        READY,
        NOT_READY,
        DONE,
        FAILED
    }

    protected abstract T computeNext();

    protected AbstractIterator() {
    }

    @CanIgnoreReturnValue
    protected final T endOfData() {
        this.state = State.DONE;
        return null;
    }

    @CanIgnoreReturnValue
    public final boolean hasNext() {
        Preconditions.checkState(this.state != State.FAILED);
        int i = AnonymousClass1.$SwitchMap$com$google$common$collect$AbstractIterator$State[this.state.ordinal()];
        if (i == 1) {
            return false;
        }
        if (i != 2) {
            return tryToComputeNext();
        }
        return true;
    }

    private boolean tryToComputeNext() {
        this.state = State.FAILED;
        this.next = computeNext();
        if (this.state == State.DONE) {
            return false;
        }
        this.state = State.READY;
        return true;
    }

    @CanIgnoreReturnValue
    public final T next() {
        if (hasNext()) {
            this.state = State.NOT_READY;
            T t = this.next;
            this.next = null;
            return t;
        }
        throw new NoSuchElementException();
    }

    public final T peek() {
        if (hasNext()) {
            return this.next;
        }
        throw new NoSuchElementException();
    }
}