package androidx.lifecycle;

import androidx.annotation.RestrictTo;
import androidx.annotation.RestrictTo.Scope;
import java.util.HashMap;
import java.util.Map;

@RestrictTo({Scope.LIBRARY_GROUP_PREFIX})
public class MethodCallsLogger {
    private Map<String, Integer> mCalledMethods = new HashMap();

    @RestrictTo({Scope.LIBRARY_GROUP_PREFIX})
    public boolean approveCall(String str, int i) {
        Integer num = (Integer) this.mCalledMethods.get(str);
        int i2 = 0;
        int intValue = num != null ? num.intValue() : 0;
        if ((intValue & i) != 0) {
            i2 = 1;
        }
        this.mCalledMethods.put(str, Integer.valueOf(i | intValue));
        return i2 ^ 1;
    }
}
