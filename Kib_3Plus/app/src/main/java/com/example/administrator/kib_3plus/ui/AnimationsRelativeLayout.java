package com.example.administrator.kib_3plus.ui;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.ViewTreeObserver;
import android.widget.RelativeLayout;

/**
 * Created by cui on 2017/6/16.
 */

public class AnimationsRelativeLayout extends RelativeLayout {

    private float yFraction = 0;
    private float xFraction = 0;

    public AnimationsRelativeLayout(Context context)
    {
        super(context);
    }

    public AnimationsRelativeLayout(Context context, AttributeSet attrs)
    {
        super(context, attrs);
    }

    public AnimationsRelativeLayout(Context context, AttributeSet attrs, int defStyle)
    {
        super(context, attrs, defStyle);
    }

    private ViewTreeObserver.OnPreDrawListener preDrawListener = null;

    public void setYFraction(float fraction)
    {
        this.yFraction = fraction;
        if (getHeight() == 0)
        {
            if (preDrawListener == null)
            {
                preDrawListener = new ViewTreeObserver.OnPreDrawListener()
                {
                    @Override
                    public boolean onPreDraw()
                    {
                        getViewTreeObserver().removeOnPreDrawListener(preDrawListener);
                        setYFraction(yFraction);
                        return true;
                    }
                };
                getViewTreeObserver().addOnPreDrawListener(preDrawListener);
            }
            return;
        }
        float translationY = getHeight() * fraction;
        Log.v("translationY set", translationY + " ");
        setTranslationY(translationY);
    }

    /** * 支持XFraction属性动画，以下都类似 */
    public void setXFraction(float fraction)
    {
        this.xFraction = fraction;
        if (getWidth() == 0)
        {
            if (preDrawListener == null)
            {
                preDrawListener = new ViewTreeObserver.OnPreDrawListener()
                {
                    @Override
                    public boolean onPreDraw()
                    {
                        getViewTreeObserver().removeOnPreDrawListener(preDrawListener);
                        setXFraction(xFraction);
                        return true;
                    }
                };
                getViewTreeObserver().addOnPreDrawListener(preDrawListener);
            }
            return;
        }
        float translationX = getWidth() * fraction;
        setTranslationX(translationX);
    }

    public void setGlide(float fraction)
    {
        float translationX = getWidth() * fraction;
        setTranslationX(translationX);
        setRotationY(90 * fraction);
        setPivotX(0);
    }

    public void setGlideBack(float fraction)
    {
        float translationX = getWidth() * fraction;
        setTranslationX(translationX);
        setRotationY(90 * fraction);
        setPivotX(0);
        setPivotY(getHeight() / 2);
    }
}
