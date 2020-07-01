package io.grpc.internal;

import io.grpc.internal.SharedResourceHolder.Resource;

public final class SharedResourcePool<T> implements ObjectPool<T> {
    private final Resource<T> resource;

    private SharedResourcePool(Resource<T> resource) {
        this.resource = resource;
    }

    public static <T> SharedResourcePool<T> forResource(Resource<T> resource) {
        return new SharedResourcePool(resource);
    }

    public T getObject() {
        return SharedResourceHolder.get(this.resource);
    }

    public T returnObject(Object obj) {
        SharedResourceHolder.release(this.resource, obj);
        return null;
    }
}
