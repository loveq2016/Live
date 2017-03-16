package org.live.module.capture.util;

import android.content.Context;
import android.graphics.PixelFormat;
import android.view.Gravity;
import android.view.WindowManager;

import org.live.common.listener.NoDoubleClickListener;
import org.live.module.capture.service.CaptureService;
import org.live.module.capture.view.impl.CaptureFABView;
import org.live.module.capture.view.impl.CaptureFragment;

/**
 * 系统WindowManager工具类
 * Created by KAM on 2017/3/11.
 */

public class WindowManagerUtil {
    /**
     * 录屏浮窗视图
     */
    public static CaptureFABView captureFABView = null;

    /**
     * 录屏浮窗视图参数
     */
    private static WindowManager.LayoutParams captureFABViewParams = null;

    /**
     * 用于控制在屏幕上添加或移除悬浮窗
     */
    private static WindowManager windowManager = null;

    /**
     * 创建录屏悬浮窗视图，初始位置为屏幕的右部中间位置。
     *
     * @param context 必须为应用程序的Context.
     * @param fabOnClickListener
     */
    public static void createCaptureFABView(Context context, NoDoubleClickListener fabOnClickListener) {
        WindowManager windowManager = getWindowManager(context);
        int screenWidth = windowManager.getDefaultDisplay().getWidth();
        int screenHeight = windowManager.getDefaultDisplay().getHeight();
        if (captureFABView == null) {
            captureFABView = new CaptureFABView(context, fabOnClickListener);
            if (captureFABViewParams == null) {
                captureFABViewParams = new WindowManager.LayoutParams();
                captureFABViewParams.type = WindowManager.LayoutParams.TYPE_PHONE;
                captureFABViewParams.format = PixelFormat.RGBA_8888;
                captureFABViewParams.flags = WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL
                        | WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE;
                captureFABViewParams.gravity = Gravity.LEFT | Gravity.TOP;
                captureFABViewParams.width = CaptureFABView.viewWidth;
                captureFABViewParams.height = CaptureFABView.viewHeight;
                captureFABViewParams.x = screenWidth;
                captureFABViewParams.y = screenHeight / 2;
            }
            captureFABView.setCaptureFABViewParams(captureFABViewParams);
            windowManager.addView(captureFABView, captureFABViewParams); // 创建浮窗
        }
    }

    /**
     * 获取WindowManager
     *
     * @param context
     * @return
     */
    private static WindowManager getWindowManager(Context context) {
        if (windowManager == null) {
            windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        }
        return windowManager;
    }

    /**
     * 将悬浮窗从屏幕上移除。
     *
     * @param context 必须为应用程序的Context.
     */
    public static void removeCaptureFABView(Context context) {
        if (captureFABView != null) {
            WindowManager windowManager = getWindowManager(context);
            windowManager.removeView(captureFABView);
            captureFABView = null;
        }
    }
}
