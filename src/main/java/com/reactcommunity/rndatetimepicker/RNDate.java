package com.reactcommunity.rndatetimepicker;

import android.os.Bundle;
import java.util.Calendar;

public class RNDate {
    private Calendar now = Calendar.getInstance();

    public RNDate(Bundle bundle) {
        if (bundle != null) {
            String str = "value";
            if (bundle.containsKey(str)) {
                set(bundle.getLong(str));
            }
        }
    }

    public void set(long j) {
        this.now.setTimeInMillis(j);
    }

    public int year() {
        return this.now.get(1);
    }

    public int month() {
        return this.now.get(2);
    }

    public int day() {
        return this.now.get(5);
    }

    public int hour() {
        return this.now.get(11);
    }

    public int minute() {
        return this.now.get(12);
    }
}
