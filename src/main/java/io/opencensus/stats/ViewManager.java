package io.opencensus.stats;

import io.opencensus.stats.View.Name;
import java.util.Set;
import javax.annotation.Nullable;

public abstract class ViewManager {
    public abstract Set<View> getAllExportedViews();

    @Nullable
    public abstract ViewData getView(Name name);

    public abstract void registerView(View view);
}
