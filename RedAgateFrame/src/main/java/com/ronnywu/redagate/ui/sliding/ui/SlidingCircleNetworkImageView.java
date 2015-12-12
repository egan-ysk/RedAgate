package com.ronnywu.redagate.ui.sliding.ui;

import android.content.Context;
import android.util.AttributeSet;

import com.ronnywu.redagate.ui.image.CircleNetworkImageView;
import com.ronnywu.redagate.ui.sliding.core.SlidingWatch;

/**
 * 对 CircleNetworkImageView 进行扩展,实现 SlidingWatch 接口.
 * 本控件应该依赖 FreeSlidingLayout 使用.
 * 如果单独使用也可以,但是因为缺少 FreeSlidingLayout 控件的支持,本控件将无法实现上拉,下拉的效果,不会出现滑动特效.
 *
 * @author ronnywu
 * @date 2015-12-10
 * @time 11:23:11
 */
public class SlidingCircleNetworkImageView extends CircleNetworkImageView implements SlidingWatch {

    public SlidingCircleNetworkImageView(Context context) {
        super(context);
    }

    public SlidingCircleNetworkImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public SlidingCircleNetworkImageView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
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
        return true;
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
        return true;
    }
}
