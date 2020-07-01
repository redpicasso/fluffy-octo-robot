package com.google.common.util.concurrent;

import com.google.common.annotations.Beta;
import com.google.common.annotations.GwtCompatible;
import com.google.common.annotations.GwtIncompatible;
import com.google.common.base.Preconditions;
import com.google.errorprone.annotations.CanIgnoreReturnValue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.concurrent.locks.Condition;

@GwtCompatible(emulated = true)
@Beta
public final class Uninterruptibles {
    @GwtIncompatible
    public static void awaitUninterruptibly(CountDownLatch countDownLatch) {
        Object obj = null;
        while (true) {
            try {
                countDownLatch.await();
                if (obj != null) {
                    Thread.currentThread().interrupt();
                    return;
                }
                return;
            } catch (InterruptedException unused) {
                obj = 1;
            } catch (Throwable th) {
                if (obj != null) {
                    Thread.currentThread().interrupt();
                }
            }
        }
    }

    @GwtIncompatible
    @CanIgnoreReturnValue
    public static boolean awaitUninterruptibly(CountDownLatch countDownLatch, long j, TimeUnit timeUnit) {
        long nanoTime;
        Object obj = null;
        try {
            j = timeUnit.toNanos(j);
            nanoTime = System.nanoTime() + j;
            while (true) {
                countDownLatch = countDownLatch.await(j, TimeUnit.NANOSECONDS);
                if (obj != null) {
                    Thread.currentThread().interrupt();
                }
                return countDownLatch;
            }
        } catch (InterruptedException unused) {
            obj = 1;
            j = nanoTime - System.nanoTime();
        } catch (Throwable th) {
            if (obj != null) {
                Thread.currentThread().interrupt();
            }
        }
    }

    @GwtIncompatible
    public static boolean awaitUninterruptibly(Condition condition, long j, TimeUnit timeUnit) {
        long nanoTime;
        Object obj = null;
        try {
            j = timeUnit.toNanos(j);
            nanoTime = System.nanoTime() + j;
            while (true) {
                condition = condition.await(j, TimeUnit.NANOSECONDS);
                if (obj != null) {
                    Thread.currentThread().interrupt();
                }
                return condition;
            }
        } catch (InterruptedException unused) {
            obj = 1;
            j = nanoTime - System.nanoTime();
        } catch (Throwable th) {
            if (obj != null) {
                Thread.currentThread().interrupt();
            }
        }
    }

    @GwtIncompatible
    public static void joinUninterruptibly(Thread thread) {
        Object obj = null;
        while (true) {
            try {
                thread.join();
                if (obj != null) {
                    Thread.currentThread().interrupt();
                    return;
                }
                return;
            } catch (InterruptedException unused) {
                obj = 1;
            } catch (Throwable th) {
                if (obj != null) {
                    Thread.currentThread().interrupt();
                }
            }
        }
    }

    @GwtIncompatible
    public static void joinUninterruptibly(Thread thread, long j, TimeUnit timeUnit) {
        long nanoTime;
        Preconditions.checkNotNull(thread);
        Object obj = null;
        try {
            j = timeUnit.toNanos(j);
            nanoTime = System.nanoTime() + j;
            while (true) {
                TimeUnit.NANOSECONDS.timedJoin(thread, j);
                if (obj != null) {
                    Thread.currentThread().interrupt();
                    return;
                }
                return;
            }
        } catch (InterruptedException unused) {
            obj = 1;
            j = nanoTime - System.nanoTime();
        } catch (Throwable th) {
            if (obj != null) {
                Thread.currentThread().interrupt();
            }
        }
    }

    @CanIgnoreReturnValue
    public static <V> V getUninterruptibly(Future<V> future) throws ExecutionException {
        Object obj = null;
        while (true) {
            try {
                Future future2 = future2.get();
                if (obj != null) {
                    Thread.currentThread().interrupt();
                }
                return future2;
            } catch (InterruptedException unused) {
                obj = 1;
            } catch (Throwable th) {
                if (obj != null) {
                    Thread.currentThread().interrupt();
                }
            }
        }
    }

    @GwtIncompatible
    @CanIgnoreReturnValue
    public static <V> V getUninterruptibly(Future<V> future, long j, TimeUnit timeUnit) throws ExecutionException, TimeoutException {
        long nanoTime;
        Object obj = null;
        try {
            j = timeUnit.toNanos(j);
            nanoTime = System.nanoTime() + j;
            while (true) {
                Future future2 = future2.get(j, TimeUnit.NANOSECONDS);
                if (obj != null) {
                    Thread.currentThread().interrupt();
                }
                return future2;
            }
        } catch (InterruptedException unused) {
            obj = 1;
            j = nanoTime - System.nanoTime();
        } catch (Throwable th) {
            if (obj != null) {
                Thread.currentThread().interrupt();
            }
        }
    }

    @GwtIncompatible
    public static <E> E takeUninterruptibly(BlockingQueue<E> blockingQueue) {
        Object obj = null;
        while (true) {
            try {
                BlockingQueue blockingQueue2 = blockingQueue2.take();
                if (obj != null) {
                    Thread.currentThread().interrupt();
                }
                return blockingQueue2;
            } catch (InterruptedException unused) {
                obj = 1;
            } catch (Throwable th) {
                if (obj != null) {
                    Thread.currentThread().interrupt();
                }
            }
        }
    }

    @GwtIncompatible
    public static <E> void putUninterruptibly(BlockingQueue<E> blockingQueue, E e) {
        Object obj = null;
        while (true) {
            try {
                blockingQueue.put(e);
                if (obj != null) {
                    Thread.currentThread().interrupt();
                    return;
                }
                return;
            } catch (InterruptedException unused) {
                obj = 1;
            } catch (Throwable th) {
                if (obj != null) {
                    Thread.currentThread().interrupt();
                }
            }
        }
    }

    @GwtIncompatible
    public static void sleepUninterruptibly(long j, TimeUnit timeUnit) {
        long nanoTime;
        Object obj = null;
        try {
            j = timeUnit.toNanos(j);
            nanoTime = System.nanoTime() + j;
            while (true) {
                TimeUnit.NANOSECONDS.sleep(j);
                if (obj != null) {
                    Thread.currentThread().interrupt();
                    return;
                }
                return;
            }
        } catch (InterruptedException unused) {
            obj = 1;
            j = nanoTime - System.nanoTime();
        } catch (Throwable th) {
            if (obj != null) {
                Thread.currentThread().interrupt();
            }
        }
    }

    @GwtIncompatible
    public static boolean tryAcquireUninterruptibly(Semaphore semaphore, long j, TimeUnit timeUnit) {
        return tryAcquireUninterruptibly(semaphore, 1, j, timeUnit);
    }

    @GwtIncompatible
    public static boolean tryAcquireUninterruptibly(Semaphore semaphore, int i, long j, TimeUnit timeUnit) {
        long nanoTime;
        Object obj = null;
        try {
            j = timeUnit.toNanos(j);
            nanoTime = System.nanoTime() + j;
            while (true) {
                semaphore = semaphore.tryAcquire(i, j, TimeUnit.NANOSECONDS);
                if (obj != null) {
                    Thread.currentThread().interrupt();
                }
                return semaphore;
            }
        } catch (InterruptedException unused) {
            obj = 1;
            j = nanoTime - System.nanoTime();
        } catch (Throwable th) {
            if (obj != null) {
                Thread.currentThread().interrupt();
            }
        }
    }

    private Uninterruptibles() {
    }
}
