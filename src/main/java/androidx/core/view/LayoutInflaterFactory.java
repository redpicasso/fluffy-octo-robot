package androidx.core.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;

@Deprecated
public interface LayoutInflaterFactory {
    View onCreateView(View view, String str, Context context, AttributeSet attributeSet);
}
