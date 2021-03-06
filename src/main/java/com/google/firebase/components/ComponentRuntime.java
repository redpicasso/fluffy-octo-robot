package com.google.firebase.components;

import com.google.android.gms.common.internal.Preconditions;
import com.google.firebase.events.Publisher;
import com.google.firebase.events.Subscriber;
import com.google.firebase.inject.Provider;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.concurrent.Executor;

/* compiled from: com.google.firebase:firebase-common@@19.0.0 */
public class ComponentRuntime extends AbstractComponentContainer {
    private static final Provider<Set<Object>> EMPTY_PROVIDER = ComponentRuntime$$Lambda$3.lambdaFactory$();
    private final Map<Component<?>, Lazy<?>> components = new HashMap();
    private final EventBus eventBus;
    private final Map<Class<?>, Lazy<?>> lazyInstanceMap = new HashMap();
    private final Map<Class<?>, Lazy<Set<?>>> lazySetMap = new HashMap();

    public ComponentRuntime(Executor executor, Iterable<ComponentRegistrar> iterable, Component<?>... componentArr) {
        this.eventBus = new EventBus(executor);
        List<Component> arrayList = new ArrayList();
        arrayList.add(Component.of(this.eventBus, EventBus.class, Subscriber.class, Publisher.class));
        for (ComponentRegistrar components : iterable) {
            arrayList.addAll(components.getComponents());
        }
        Collections.addAll(arrayList, componentArr);
        CycleDetector.detect(arrayList);
        for (Component component : arrayList) {
            this.components.put(component, new Lazy(ComponentRuntime$$Lambda$1.lambdaFactory$(this, component)));
        }
        processInstanceComponents();
        processSetComponents();
    }

    private void processInstanceComponents() {
        for (Entry entry : this.components.entrySet()) {
            Component component = (Component) entry.getKey();
            if (component.isValue()) {
                Lazy lazy = (Lazy) entry.getValue();
                for (Class put : component.getProvidedInterfaces()) {
                    this.lazyInstanceMap.put(put, lazy);
                }
            }
        }
        validateDependencies();
    }

    private void processSetComponents() {
        Map hashMap = new HashMap();
        for (Entry entry : this.components.entrySet()) {
            Component component = (Component) entry.getKey();
            if (!component.isValue()) {
                Lazy lazy = (Lazy) entry.getValue();
                for (Class cls : component.getProvidedInterfaces()) {
                    if (!hashMap.containsKey(cls)) {
                        hashMap.put(cls, new HashSet());
                    }
                    ((Set) hashMap.get(cls)).add(lazy);
                }
            }
        }
        for (Entry entry2 : hashMap.entrySet()) {
            this.lazySetMap.put((Class) entry2.getKey(), new Lazy(ComponentRuntime$$Lambda$2.lambdaFactory$((Set) entry2.getValue())));
        }
    }

    static /* synthetic */ Set lambda$processSetComponents$1(Set set) {
        Set hashSet = new HashSet();
        for (Lazy lazy : set) {
            hashSet.add(lazy.get());
        }
        return Collections.unmodifiableSet(hashSet);
    }

    public <T> Provider<T> getProvider(Class<T> cls) {
        Preconditions.checkNotNull(cls, "Null interface requested.");
        return (Provider) this.lazyInstanceMap.get(cls);
    }

    public <T> Provider<Set<T>> setOfProvider(Class<T> cls) {
        Lazy lazy = (Lazy) this.lazySetMap.get(cls);
        if (lazy != null) {
            return lazy;
        }
        return EMPTY_PROVIDER;
    }

    public void initializeEagerComponents(boolean z) {
        for (Entry entry : this.components.entrySet()) {
            Component component = (Component) entry.getKey();
            Lazy lazy = (Lazy) entry.getValue();
            if (component.isAlwaysEager() || (component.isEagerInDefaultApp() && z)) {
                lazy.get();
            }
        }
        this.eventBus.enablePublishingAndFlushPending();
    }

    private void validateDependencies() {
        for (Component dependencies : this.components.keySet()) {
            for (Dependency dependency : dependencies.getDependencies()) {
                if (dependency.isRequired()) {
                    if (!this.lazyInstanceMap.containsKey(dependency.getInterface())) {
                        throw new MissingDependencyException(String.format("Unsatisfied dependency for component %s: %s", new Object[]{dependencies, dependency.getInterface()}));
                    }
                }
            }
        }
    }
}
