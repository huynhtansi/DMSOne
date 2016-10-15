package com.htsi.dmsone.utils;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.transition.Transition;
import android.transition.TransitionValues;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by htsi.
 * Since: 10/8/16 on 5:05 PM
 * Project: DMSOne
 */

public class Elevation extends Transition {

    private static final String PROP_ELEVATION = "ourstreets:elevation";

    private static final String[] PROPERTIES = {PROP_ELEVATION};

    /**
     * Constructs an Elevation object with no target objects.
     */
    public Elevation() {
        super();
    }

    /**
     * Perform inflation from XML and apply a class-specific base style
     * from a theme attribute or style resource.
     *
     * @param context The Context the transition is running in, through which it can
     * access the current theme, resources, etc.
     * @param attrs The attributes of the XML tag that is inflating the transition.
     */
    public Elevation(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public void captureStartValues(TransitionValues transitionValues) {
        captureValues(transitionValues);
    }

    @Override
    public void captureEndValues(TransitionValues transitionValues) {
        captureValues(transitionValues);
    }

    private void captureValues(TransitionValues transitionValues) {
        transitionValues.values.put(PROP_ELEVATION, transitionValues.view.getElevation());
    }

    @Override
    public String[] getTransitionProperties() {
        return PROPERTIES;
    }

    @Override
    public Animator createAnimator(ViewGroup sceneRoot, TransitionValues startValues,
                                   TransitionValues endValues) {
        if (startValues == null && endValues == null) {
            return null; // nothing to do here
        }

        boolean isEntering = startValues == null;

        TransitionValues values = isEntering ? endValues : startValues;
        View target = values.view;

        float targetElevation = (float) values.values.get(PROP_ELEVATION);
        float startElevation = isEntering ? 0f : targetElevation;
        float endElevation = isEntering ? targetElevation : 0f;

        //noinspection ResourceType
        target.setElevation(startElevation);

        return ObjectAnimator.ofFloat(target, View.TRANSLATION_Z, endElevation);
    }

}
