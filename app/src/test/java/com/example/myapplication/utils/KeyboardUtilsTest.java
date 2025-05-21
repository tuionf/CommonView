package com.example.myapplication.utils; // Adjust package if necessary

import android.app.Activity;
import android.content.Context;
import android.os.IBinder;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class KeyboardUtilsTest {

    @Mock
    Activity mockActivity;

    @Mock
    Context mockContext;

    @Mock
    View mockView;

    @Mock
    InputMethodManager mockInputMethodManager;
    
    @Mock
    IBinder mockWindowToken;

    @Test
    public void hideKeyboard_activityGiven_callsHideSoftInputFromWindow() {
        when(mockActivity.getSystemService(Activity.INPUT_METHOD_SERVICE)).thenReturn(mockInputMethodManager);
        when(mockActivity.getCurrentFocus()).thenReturn(mockView);
        when(mockView.getWindowToken()).thenReturn(mockWindowToken);

        KeyboardUtils.hideKeyboard(mockActivity);

        verify(mockInputMethodManager).hideSoftInputFromWindow(mockWindowToken, 0);
    }

    @Test
    public void hideKeyboard_activityGivenNoFocusedView_createsViewAndCallsHideSoftInput() {
        when(mockActivity.getSystemService(Activity.INPUT_METHOD_SERVICE)).thenReturn(mockInputMethodManager);
        when(mockActivity.getCurrentFocus()).thenReturn(null);
        // We can't easily verify the new View(activity).getWindowToken() part without deeper mocking or Robolectric.
        // We will trust that a non-null token is passed if a view is created.
        // The main check is that hideSoftInputFromWindow is called.

        KeyboardUtils.hideKeyboard(mockActivity);

        verify(mockInputMethodManager).hideSoftInputFromWindow(any(IBinder.class), eq(0));
    }
    
    @Test
    public void hideKeyboard_nullActivity_doesNotCrash() {
        KeyboardUtils.hideKeyboard((Activity) null);
        // No verification needed, just ensure no NullPointerException
    }

    @Test
    public void hideKeyboardFromView_validArgs_callsHideSoftInputFromWindow() {
        when(mockContext.getSystemService(Context.INPUT_METHOD_SERVICE)).thenReturn(mockInputMethodManager);
        when(mockView.getWindowToken()).thenReturn(mockWindowToken);

        KeyboardUtils.hideKeyboardFromView(mockContext, mockView);

        verify(mockInputMethodManager).hideSoftInputFromWindow(mockWindowToken, 0);
    }
    
    @Test
    public void hideKeyboardFromView_nullContext_doesNotCrash() {
        KeyboardUtils.hideKeyboardFromView(null, mockView);
    }

    @Test
    public void hideKeyboardFromView_nullView_doesNotCrash() {
        KeyboardUtils.hideKeyboardFromView(mockContext, null);
    }
    
    @Test
    public void hideKeyboard_contextAndTokenGiven_callsHideSoftInputFromWindow() {
        when(mockContext.getSystemService(Context.INPUT_METHOD_SERVICE)).thenReturn(mockInputMethodManager);

        KeyboardUtils.hideKeyboard(mockContext, mockWindowToken);

        verify(mockInputMethodManager).hideSoftInputFromWindow(mockWindowToken, 0);
    }

    @Test
    public void showKeyboard_validArgsViewRequestsFocus_callsShowSoftInput() {
        when(mockContext.getSystemService(Context.INPUT_METHOD_SERVICE)).thenReturn(mockInputMethodManager);
        when(mockView.requestFocus()).thenReturn(true);

        KeyboardUtils.showKeyboard(mockContext, mockView);

        verify(mockView).requestFocus();
        verify(mockInputMethodManager).showSoftInput(mockView, InputMethodManager.SHOW_IMPLICIT);
    }

    @Test
    public void showKeyboard_validArgsViewFailsFocus_doesNotCallShowSoftInput() {
        when(mockContext.getSystemService(Context.INPUT_METHOD_SERVICE)).thenReturn(mockInputMethodManager);
        when(mockView.requestFocus()).thenReturn(false);

        KeyboardUtils.showKeyboard(mockContext, mockView);

        verify(mockView).requestFocus();
        verify(mockInputMethodManager, never()).showSoftInput(any(View.class), anyInt());
    }
    
    @Test
    public void showKeyboard_nullContext_doesNotCrash() {
        KeyboardUtils.showKeyboard(null, mockView);
    }

    @Test
    public void showKeyboard_nullView_doesNotCrash() {
        KeyboardUtils.showKeyboard(mockContext, null);
    }
    
    @Test
    public void toggleKeyboard_validContext_callsToggleSoftInput() {
        when(mockContext.getSystemService(Context.INPUT_METHOD_SERVICE)).thenReturn(mockInputMethodManager);
        
        KeyboardUtils.toggleKeyboard(mockContext);
        
        verify(mockInputMethodManager).toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);
    }

    @Test
    public void toggleKeyboard_nullContext_doesNotCrash() {
        KeyboardUtils.toggleKeyboard(null);
    }
}
```
