package com.facebook.jni;

import java.lang.ref.PhantomReference;
import java.lang.ref.ReferenceQueue;
import java.util.concurrent.atomic.AtomicReference;

public class DestructorThread {
    private static DestructorList sDestructorList = new DestructorList();
    private static DestructorStack sDestructorStack = new DestructorStack();
    private static ReferenceQueue sReferenceQueue = new ReferenceQueue();
    private static Thread sThread = new Thread("HybridData DestructorThread") {
        public void run() {
            while (true) {
                try {
                } catch (InterruptedException unused) {
                    Destructor destructor = (Destructor) DestructorThread.sReferenceQueue.remove();
                    destructor.destruct();
                    if (destructor.previous == null) {
                        DestructorThread.sDestructorStack.transferAllToList();
                    }
                    DestructorList.drop(destructor);
                }
            }
        }
    };

    public static abstract class Destructor extends PhantomReference<Object> {
        private Destructor next;
        private Destructor previous;

        abstract void destruct();

        Destructor(Object obj) {
            super(obj, DestructorThread.sReferenceQueue);
            DestructorThread.sDestructorStack.push(this);
        }

        private Destructor() {
            super(null, DestructorThread.sReferenceQueue);
        }
    }

    private static class DestructorList {
        private Destructor mHead = new Terminus();

        public DestructorList() {
            this.mHead.next = new Terminus();
            this.mHead.next.previous = this.mHead;
        }

        public void enqueue(Destructor destructor) {
            destructor.next = this.mHead.next;
            this.mHead.next = destructor;
            destructor.next.previous = destructor;
            destructor.previous = this.mHead;
        }

        private static void drop(Destructor destructor) {
            destructor.next.previous = destructor.previous;
            destructor.previous.next = destructor.next;
        }
    }

    private static class DestructorStack {
        private AtomicReference<Destructor> mHead;

        private DestructorStack() {
            this.mHead = new AtomicReference();
        }

        /* synthetic */ DestructorStack(AnonymousClass1 anonymousClass1) {
            this();
        }

        public void push(Destructor destructor) {
            Destructor destructor2;
            do {
                destructor2 = (Destructor) this.mHead.get();
                destructor.next = destructor2;
            } while (!this.mHead.compareAndSet(destructor2, destructor));
        }

        public void transferAllToList() {
            Destructor destructor = (Destructor) this.mHead.getAndSet(null);
            while (destructor != null) {
                Destructor access$600 = destructor.next;
                DestructorThread.sDestructorList.enqueue(destructor);
                destructor = access$600;
            }
        }
    }

    private static class Terminus extends Destructor {
        private Terminus() {
            super();
        }

        /* synthetic */ Terminus(AnonymousClass1 anonymousClass1) {
            this();
        }

        void destruct() {
            throw new IllegalStateException("Cannot destroy Terminus Destructor.");
        }
    }

    static {
        sThread.start();
    }
}
