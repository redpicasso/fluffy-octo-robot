package com.google.firebase.database;

import com.google.firebase.annotations.PublicApi;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@PublicApi
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
/* compiled from: com.google.firebase:firebase-database@@17.0.0 */
public @interface ThrowOnExtraProperties {
}
