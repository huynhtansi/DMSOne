package com.htsi.dmsone.di.base;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import javax.inject.Scope;

/**
 * Created by htsi.
 * Since: 9/22/16 on 2:00 PM
 * Project: DMSOne
 */
@Scope
@Retention(RetentionPolicy.RUNTIME)
public @interface PerActivity {}