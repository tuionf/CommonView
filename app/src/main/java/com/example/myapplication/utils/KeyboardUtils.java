package com.example.myapplication.utils; // Adjust package if necessary

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.os.IBinder;

public class KeyboardUtils {

    /**
     * Hides the software keyboard.
     *
     * @param activity The current activity.
     */
    public static void hideKeyboard(Activity activity) {
        if (activity == null) {
            return;
        }
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        //Find the currently focused view, so we can grab the correct window token from it.
        View view = activity.getCurrentFocus();
        //If no view currently has focus, create a new one, just so we can grab a window token from it
        if (view == null) {
            view = new View(activity);
        }
        if (imm != null) {
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    /**
     * Hides the software keyboard from a specific view's window.
     *
     * @param context Context
     * @param view    The view that currently has focus or is associated with the keyboard.
     */
    public static void hideKeyboardFromView(Context context, View view) {
        if (context == null || view == null) {
            return;
        }
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm != null) {
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }
    
    /**
     * Hides the software keyboard from a specific window token.
     * Useful when the view might not be available or a direct window token is.
     *
     * @param context Context
     * @param windowToken The window token of the window that is making the request.
     */
    public static void hideKeyboard(Context context, IBinder windowToken) {
        if (context == null || windowToken == null) {
            return;
        }
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm != null) {
            imm.hideSoftInputFromWindow(windowToken, 0);
        }
    }


    /**
     * Shows the software keyboard and requests focus for the given view.
     *
     * @param context Context
     * @param view    The view (e.g., EditText) to focus and show the keyboard for.
     */
    public static void showKeyboard(Context context, View view) {
        if (context == null || view == null) {
            return;
        }
        if (view.requestFocus()) {
            InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
            if (imm != null) {
                imm.showSoftInput(view, InputMethodManager.SHOW_IMPLICIT);
            }
        }
    }
    
    /**
     * Toggles the software keyboard. If it's visible, it will be hidden. If it's hidden, it will be shown.
     * Note: Showing the keyboard reliably often requires a target view. This method is more for simple toggle
     * if the context is right, but `showKeyboard(Context, View)` is generally preferred for showing.
     *
     * @param context Context
     */
    public static void toggleKeyboard(Context context) {
        if (context == null) {
            return;
        }
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm != null) {
            imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);
        }
    }
}
