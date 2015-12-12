package com.ronnywu.redagate.ui.sliding.ui;

import android.content.Context;
import android.util.AttributeSet;

import com.ronnywu.redagate.ui.sliding.core.SlidingWatch;
import com.ronnywu.redagate.web.view.RedAgateWebView;

/**
 * WebView 扩展 SlidingWatch 接口.
 * 基于 FreeSlidingLayout 的支持扩展.
 *
 * @author ronnywu
 * @date 2015-12-10
 * @time 11:31:42
 */
public class SlidingWebView extends RedAgateWebView implements SlidingWatch {

    /**
     * 构造器
     *
     * @param context 上下文
     */
    public SlidingWebView(Context context) {
        super(context);
    }

    /**
     * 构造器
     *
     * @param context 上下文
     * @param attrs   属性集
     */
    public SlidingWebView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    /**
     * 构造器
     *
     * @param context      上下文
     * @param attrs        属性集
     * @param defStyleAttr 默认风格属性集
     */
    public SlidingWebView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
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
        return getContentHeight() * getScale() - (getHeight() + getScrollY()) <= 5;
    }
}
