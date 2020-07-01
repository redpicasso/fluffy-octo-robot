package com.google.common.base;

import com.google.common.annotations.GwtIncompatible;
import java.io.Serializable;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@GwtIncompatible
final class JdkPattern extends CommonPattern implements Serializable {
    private static final long serialVersionUID = 0;
    private final Pattern pattern;

    private static final class JdkMatcher extends CommonMatcher {
        final Matcher matcher;

        JdkMatcher(Matcher matcher) {
            this.matcher = (Matcher) Preconditions.checkNotNull(matcher);
        }

        public boolean matches() {
            return this.matcher.matches();
        }

        public boolean find() {
            return this.matcher.find();
        }

        public boolean find(int i) {
            return this.matcher.find(i);
        }

        public String replaceAll(String str) {
            return this.matcher.replaceAll(str);
        }

        public int end() {
            return this.matcher.end();
        }

        public int start() {
            return this.matcher.start();
        }
    }

    JdkPattern(Pattern pattern) {
        this.pattern = (Pattern) Preconditions.checkNotNull(pattern);
    }

    public CommonMatcher matcher(CharSequence charSequence) {
        return new JdkMatcher(this.pattern.matcher(charSequence));
    }

    public String pattern() {
        return this.pattern.pattern();
    }

    public int flags() {
        return this.pattern.flags();
    }

    public String toString() {
        return this.pattern.toString();
    }
}
