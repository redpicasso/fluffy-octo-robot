package com.google.firebase.components;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/* compiled from: com.google.firebase:firebase-common@@19.0.0 */
class CycleDetector {

    /* compiled from: com.google.firebase:firebase-common@@19.0.0 */
    private static class ComponentNode {
        private final Component<?> component;
        private final Set<ComponentNode> dependencies = new HashSet();
        private final Set<ComponentNode> dependents = new HashSet();

        ComponentNode(Component<?> component) {
            this.component = component;
        }

        void addDependency(ComponentNode componentNode) {
            this.dependencies.add(componentNode);
        }

        void addDependent(ComponentNode componentNode) {
            this.dependents.add(componentNode);
        }

        Set<ComponentNode> getDependencies() {
            return this.dependencies;
        }

        void removeDependent(ComponentNode componentNode) {
            this.dependents.remove(componentNode);
        }

        Component<?> getComponent() {
            return this.component;
        }

        boolean isRoot() {
            return this.dependents.isEmpty();
        }

        boolean isLeaf() {
            return this.dependencies.isEmpty();
        }
    }

    /* compiled from: com.google.firebase:firebase-common@@19.0.0 */
    private static class Dep {
        private final Class<?> anInterface;
        private final boolean set;

        private Dep(Class<?> cls, boolean z) {
            this.anInterface = cls;
            this.set = z;
        }

        public boolean equals(Object obj) {
            if (!(obj instanceof Dep)) {
                return false;
            }
            Dep dep = (Dep) obj;
            if (dep.anInterface.equals(this.anInterface) && dep.set == this.set) {
                return true;
            }
            return false;
        }

        public int hashCode() {
            return ((this.anInterface.hashCode() ^ 1000003) * 1000003) ^ Boolean.valueOf(this.set).hashCode();
        }
    }

    CycleDetector() {
    }

    static void detect(List<Component<?>> list) {
        Set<ComponentNode> toGraph = toGraph(list);
        Set roots = getRoots(toGraph);
        int i = 0;
        while (!roots.isEmpty()) {
            ComponentNode componentNode = (ComponentNode) roots.iterator().next();
            roots.remove(componentNode);
            i++;
            for (ComponentNode componentNode2 : componentNode.getDependencies()) {
                componentNode2.removeDependent(componentNode);
                if (componentNode2.isRoot()) {
                    roots.add(componentNode2);
                }
            }
        }
        if (i != list.size()) {
            List arrayList = new ArrayList();
            for (ComponentNode componentNode3 : toGraph) {
                if (!(componentNode3.isRoot() || componentNode3.isLeaf())) {
                    arrayList.add(componentNode3.getComponent());
                }
            }
            throw new DependencyCycleException(arrayList);
        }
    }

    private static Set<ComponentNode> toGraph(List<Component<?>> list) {
        ComponentNode componentNode;
        Map hashMap = new HashMap(list.size());
        for (Component component : list) {
            componentNode = new ComponentNode(component);
            for (Class dep : component.getProvidedInterfaces()) {
                Dep dep2 = new Dep(dep, component.isValue() ^ true);
                if (!hashMap.containsKey(dep2)) {
                    hashMap.put(dep2, new HashSet());
                }
                Set set = (Set) hashMap.get(dep2);
                if (set.isEmpty() || dep2.set) {
                    set.add(componentNode);
                } else {
                    throw new IllegalArgumentException(String.format("Multiple components provide %s.", new Object[]{dep}));
                }
            }
        }
        for (Set<ComponentNode> it : hashMap.values()) {
            for (ComponentNode componentNode2 : it) {
                for (Dependency dependency : componentNode2.getComponent().getDependencies()) {
                    if (dependency.isDirectInjection()) {
                        Set<ComponentNode> set2 = (Set) hashMap.get(new Dep(dependency.getInterface(), dependency.isSet()));
                        if (set2 != null) {
                            for (ComponentNode componentNode3 : set2) {
                                componentNode2.addDependency(componentNode3);
                                componentNode3.addDependent(componentNode2);
                            }
                        }
                    }
                }
            }
        }
        Set hashSet = new HashSet();
        for (Set addAll : hashMap.values()) {
            hashSet.addAll(addAll);
        }
        return hashSet;
    }

    private static Set<ComponentNode> getRoots(Set<ComponentNode> set) {
        Set<ComponentNode> hashSet = new HashSet();
        for (ComponentNode componentNode : set) {
            if (componentNode.isRoot()) {
                hashSet.add(componentNode);
            }
        }
        return hashSet;
    }
}
