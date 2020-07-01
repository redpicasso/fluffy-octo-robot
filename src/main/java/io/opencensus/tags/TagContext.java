package io.opencensus.tags;

import com.google.common.collect.HashMultiset;
import com.google.common.collect.ImmutableMultiset;
import com.google.common.collect.Lists;
import com.google.common.collect.Multiset;
import java.util.Iterator;
import javax.annotation.Nullable;
import javax.annotation.concurrent.Immutable;

@Immutable
public abstract class TagContext {
    protected abstract Iterator<Tag> getIterator();

    public String toString() {
        return "TagContext";
    }

    public boolean equals(@Nullable Object obj) {
        if (!(obj instanceof TagContext)) {
            return false;
        }
        ImmutableMultiset of;
        ImmutableMultiset of2;
        TagContext tagContext = (TagContext) obj;
        Iterator iterator = getIterator();
        Iterator iterator2 = tagContext.getIterator();
        if (iterator == null) {
            of = ImmutableMultiset.of();
        } else {
            of = HashMultiset.create(Lists.newArrayList(iterator));
        }
        Multiset multiset = of;
        if (iterator2 == null) {
            of2 = ImmutableMultiset.of();
        } else {
            of2 = HashMultiset.create(Lists.newArrayList(iterator2));
        }
        return multiset.equals(of2);
    }

    public final int hashCode() {
        Iterator iterator = getIterator();
        int i = 0;
        if (iterator == null) {
            return 0;
        }
        while (iterator.hasNext()) {
            Tag tag = (Tag) iterator.next();
            if (tag != null) {
                i += tag.hashCode();
            }
        }
        return i;
    }
}
