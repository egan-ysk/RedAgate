package com.ronnywu.redagate.ui.sliding.ui;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.widget.ScrollView;

import com.ronnywu.redagate.ui.sliding.core.SlidingWatch;

/**
 * 对 ScrollView 进行上拉加载,下拉刷新的扩展.
 *
 * @author ronnywu
 * @date 2015-12-10
 * @time 11:28:41
 */
public class SlidingScrollView extends ScrollView implements SlidingWatch {

    public SlidingScrollView(Context context) {
        super(context);
    }

    public SlidingScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public SlidingScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public SlidingScrollView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    /**
     * 允许额外视图被下拉.
     * 当内容视图中的所有子视图都无法继续被下拉时,应该允许额外视图被下拉.
     * 也就是说,当内容视图中的所有子视图中,有一个可以继续下拉,
     * 就说明子视图是需要消耗 Touch 事件.这时候应该不允许额外视图被下拉.
     *
     * @return 是否允许下拉.
     */
    @Override
    public boolean extraViewAllowedDrop() {
        return getScrollY() == 0;
    }

    /**
     * 允许额外视图被拉起.
     * 当内容视图中的所有子视图都无法继续被拉起时,应该允许额外视图被上拉.
     * 也就是说,当内容视图中的所有子视图中,有一个可以继续上拉,
     * 就说明子视图是需要消耗 Touch 事件.这时候应该不允许额外视图被上拉.
     *
     * @return 是否允许上拉.
     */
    @Override
    public boolean extraViewAllowedRise() {
        return getScrollY() >= (getChildAt(0).getHeight() - getMeasuredHeight());
    }
}
