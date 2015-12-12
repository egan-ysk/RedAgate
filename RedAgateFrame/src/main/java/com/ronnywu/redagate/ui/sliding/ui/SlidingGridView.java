package com.ronnywu.redagate.ui.sliding.ui;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.widget.GridView;

import com.ronnywu.redagate.ui.sliding.core.SlidingWatch;

/**
 * 支持上拉加载,下拉刷新的GridView.
 * 注:当前控件依赖于 FreeSlidingLayout 衍生控件使用.
 *
 * @author ronnywu
 * @date 2015-12-10
 * @time 11:17:38
 */
public class SlidingGridView extends GridView implements SlidingWatch {

    public SlidingGridView(Context context) {
        super(context);
    }

    public SlidingGridView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public SlidingGridView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public SlidingGridView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
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
        // 没有item的时候也可以下拉刷新
        // 滑到顶部了
        return getCount() == 0 || getFirstVisiblePosition() == 0 && getChildAt(0).getTop() >= 0;
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
        if (getCount() == 0) {
            // 没有item的时候也可以上拉加载
            return true;
        } else if (getLastVisiblePosition() == (getCount() - 1)) {
            // 滑到底部了
            if (getChildAt(getLastVisiblePosition() - getFirstVisiblePosition()) != null
                    && getChildAt(
                    getLastVisiblePosition()
                            - getFirstVisiblePosition()).getBottom() <= getMeasuredHeight())
                return true;
        }
        return false;
    }
}
