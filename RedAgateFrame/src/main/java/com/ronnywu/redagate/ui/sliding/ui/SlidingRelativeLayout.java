package com.ronnywu.redagate.ui.sliding.ui;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.widget.RelativeLayout;

import com.ronnywu.redagate.ui.sliding.core.SlidingWatch;

/**
 * RelativeLayout 扩展 SlidingWatch 接口.
 * 支持 FreeSlidingLayout 控件的相对布局.
 * <p/>
 * 主要针对内部嵌套扩展了 SlidingWatch 接口的 View
 * 主要功能:
 * 如果没有设置内嵌 SlidingWatch 接口. 直接允许上拉和下拉.
 * 否则 需要根据 SlidingWatch 接口的值判断是否允许下拉.
 *
 * @author ronnywu
 * @date 2015-12-10
 * @time 11:46:40
 */
public class SlidingRelativeLayout extends RelativeLayout implements SlidingWatch {

    /**
     * 布局中内嵌的 SlidingWatch 接口.
     */
    SlidingWatch slidingWatch;

    public SlidingRelativeLayout(Context context) {
        super(context);
        slidingRelativeLayoutInitView(context);
    }

    public SlidingRelativeLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        slidingRelativeLayoutInitView(context);
    }

    public SlidingRelativeLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        slidingRelativeLayoutInitView(context);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public SlidingRelativeLayout(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        slidingRelativeLayoutInitView(context);
    }

    /**
     * 初始化支持 FreeSlidingLayout 控件的相对布局.
     *
     * @param context
     */
    private void slidingRelativeLayoutInitView(Context context) {

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
        // 如果没有设置内嵌 SlidingWatch 接口. 直接允许上拉和下拉.
        // 否则 需要根据 SlidingWatch 接口的值判断是否允许下拉.
        return slidingWatch == null || slidingWatch.extraViewAllowedDrop();
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
        // 如果没有设置内嵌 SlidingWatch 接口. 直接允许上拉和下拉.
        // 否则 需要根据 SlidingWatch 接口的值判断是否允许下拉.
        return slidingWatch == null || slidingWatch.extraViewAllowedRise();
    }

    /**
     * 获取内嵌的 SlidingWatch 接口.
     *
     * @return SlidingWatch 接口.
     */
    public SlidingWatch getSlidingWatch() {
        return slidingWatch;
    }

    /**
     * 设置内置的 SlidingWatch 接口.
     *
     * @param slidingWatch SlidingWatch 接口.
     */
    public void setSlidingWatch(SlidingWatch slidingWatch) {
        this.slidingWatch = slidingWatch;
    }
}
