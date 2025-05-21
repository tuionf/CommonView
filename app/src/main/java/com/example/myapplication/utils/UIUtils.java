package com.example.myapplication.utils; // Adjust package if necessary

import android.content.Context;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.widget.Toast;
import android.os.Handler;
import android.os.Looper;

public class UIUtils {

    /**
     * Shows a short Toast message.
     * Ensures the Toast is shown on the UI thread.
     *
     * @param context Context
     * @param message The message to show.
     */
    public static void showToast(final Context context, final String message) {
        if (context == null || message == null) {
            return;
        }
        // Ensure Toast is shown on the main thread
        if (Looper.myLooper() == Looper.getMainLooper()) {
            Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
        } else {
            new Handler(Looper.getMainLooper()).post(() -> Toast.makeText(context, message, Toast.LENGTH_SHORT).show());
        }
    }

    /**
     * Shows a long Toast message.
     * Ensures the Toast is shown on the UI thread.
     *
     * @param context Context
     * @param message The message to show.
     */
    public static void showLongToast(final Context context, final String message) {
        if (context == null || message == null) {
            return;
        }
        // Ensure Toast is shown on the main thread
        if (Looper.myLooper() == Looper.getMainLooper()) {
            Toast.makeText(context, message, Toast.LENGTH_LONG).show();
        } else {
            new Handler(Looper.getMainLooper()).post(() -> Toast.makeText(context, message, Toast.LENGTH_LONG).show());
        }
    }

    /**
     * Converts density-independent pixels (dp) to pixels (px).
     *
     * @param context Context
     * @param dp      The value in dp.
     * @return The value in px.
     */
    public static int dpToPx(Context context, float dp) {
        if (context == null) {
            return (int) dp; // Fallback or throw exception
        }
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, displayMetrics);
    }

    /**
     * Converts pixels (px) to density-independent pixels (dp).
     *
     * @param context Context
     * @param px      The value in px.
     * @return The value in dp.
     */
    public static float pxToDp(Context context, float px) {
        if (context == null) {
            return px; // Fallback or throw exception
        }
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        return px / displayMetrics.density;
    }
}
