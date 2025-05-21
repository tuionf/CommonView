package com.example.myapplication.utils; // Adjust package if necessary

import android.content.Context;
import android.content.res.Resources;
import android.util.DisplayMetrics;
import android.widget.Toast;
import android.os.Looper;
import android.os.Handler;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.robolectric.annotation.Config;
import org.robolectric.shadows.ShadowLooper;


import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class UIUtilsTest {

    @Mock
    Context mockContext;

    @Mock
    Resources mockResources;

    @Mock
    DisplayMetrics mockDisplayMetrics;
    
    @Mock
    Toast mockToast; // For verifying makeText is called

    // It's tricky to test Looper.myLooper() == Looper.getMainLooper() without Robolectric.
    // For Mockito-only tests, we might need to refactor UIUtils to inject a Looper checker or assume main thread.
    // For now, let's assume we can mock static Looper for the test.

    @Before
    public void setUp() {
        when(mockContext.getResources()).thenReturn(mockResources);
        when(mockResources.getDisplayMetrics()).thenReturn(mockDisplayMetrics);
    }

    @Test
    public void dpToPx_validContextAndDp_returnsCorrectPx() {
        mockDisplayMetrics.density = 2.0f; // Example density (mdpi = 1, hdpi = 1.5, xhdpi = 2)
        // TypedValue.applyDimension will be called, which uses displayMetrics.density
        // So, for dpToPx(context, 10f), if density is 2.0, it should be 10 * 2.0 = 20
        assertEquals(20, UIUtils.dpToPx(mockContext, 10f));
        
        mockDisplayMetrics.density = 3.0f;
        assertEquals(30, UIUtils.dpToPx(mockContext, 10f));
    }
    
    @Test
    public void dpToPx_nullContext_returnsDpAsInt() {
        assertEquals(10, UIUtils.dpToPx(null, 10f));
    }

    @Test
    public void pxToDp_validContextAndPx_returnsCorrectDp() {
        mockDisplayMetrics.density = 2.0f;
        assertEquals(5f, UIUtils.pxToDp(mockContext, 10f), 0.01f);

        mockDisplayMetrics.density = 3.0f;
        assertEquals(10f / 3.0f, UIUtils.pxToDp(mockContext, 10f), 0.01f);
    }
    
    @Test
    public void pxToDp_nullContext_returnsPx() {
         assertEquals(10f, UIUtils.pxToDp(null, 10f), 0.01f);
    }

    @Test
    public void showToast_nullContext_doesNotCrash() {
        try (MockedStatic<Toast> mockedToast = Mockito.mockStatic(Toast.class)) {
            UIUtils.showToast(null, "message");
            mockedToast.verifyNoInteractions(); // Should not attempt to make a toast
        }
    }

    @Test
    public void showToast_nullMessage_doesNotCrash() {
         try (MockedStatic<Toast> mockedToast = Mockito.mockStatic(Toast.class)) {
            UIUtils.showToast(mockContext, null);
            mockedToast.verifyNoInteractions(); // Should not attempt to make a toast
        }
    }
    
    // Testing methods that post to Handler is more complex and often requires Robolectric
    // or more involved static mocking of Looper and Handler.
    // For now, we'll verify that Toast.makeText is called.
    // This test assumes it's running on a thread that is not Looper.getMainLooper().
    // To properly test the Looper interaction, Robolectric is better.
    @Test
    public void showToast_validArgs_callsToastMakeText() {
        // This setup is simplified. A full test would involve ShadowLooper from Robolectric.
        try (MockedStatic<Toast> mockedStaticToast = Mockito.mockStatic(Toast.class);
             MockedStatic<Looper> mockedStaticLooper = Mockito.mockStatic(Looper.class)) {

            Looper mockMainLooper = mock(Looper.class);
            mockedStaticLooper.when(Looper::getMainLooper).thenReturn(mockMainLooper);
            
            // Case 1: Current looper is the main looper
            mockedStaticLooper.when(Looper::myLooper).thenReturn(mockMainLooper);
            mockedStaticToast.when(() -> Toast.makeText(any(Context.class), anyString(), anyInt())).thenReturn(mockToast);
            UIUtils.showToast(mockContext, "Test Message");
            mockedStaticToast.verify(() -> Toast.makeText(mockContext, "Test Message", Toast.LENGTH_SHORT));
            verify(mockToast).show();

            // Case 2: Current looper is not the main looper (requires Handler.post)
            // This part is harder to test reliably with Mockito alone without deeper Handler mocking.
            // We'll trust the Handler().post part and just ensure it doesn't crash.
            // Or, if possible, mock the Handler.
            Looper mockWorkerLooper = mock(Looper.class);
            mockedStaticLooper.when(Looper::myLooper).thenReturn(mockWorkerLooper); // Different from main

            // To test the Handler.post branch, we'd ideally execute the posted Runnable.
            // Robolectric's ShadowLooper.idle() would do this.
            // For now, just ensure it attempts to make the toast.
            UIUtils.showToast(mockContext, "Test Message Background");
            // Verification might happen twice if the static mock isn't reset, let's be specific.
             mockedStaticToast.verify(() -> Toast.makeText(mockContext, "Test Message Background", Toast.LENGTH_SHORT), atLeastOnce());
        }
    }
    
    @Test
    public void showLongToast_validArgs_callsToastMakeTextWithLongDuration() {
         try (MockedStatic<Toast> mockedStaticToast = Mockito.mockStatic(Toast.class);
              MockedStatic<Looper> mockedStaticLooper = Mockito.mockStatic(Looper.class)) {
            Looper mockMainLooper = mock(Looper.class);
            mockedStaticLooper.when(Looper::getMainLooper).thenReturn(mockMainLooper);
            mockedStaticLooper.when(Looper::myLooper).thenReturn(mockMainLooper); // Assume on main thread for simplicity

            mockedStaticToast.when(() -> Toast.makeText(any(Context.class), anyString(), anyInt())).thenReturn(mockToast);
            
            UIUtils.showLongToast(mockContext, "Test Long Message");
            
            mockedStaticToast.verify(() -> Toast.makeText(mockContext, "Test Long Message", Toast.LENGTH_LONG));
            verify(mockToast).show();
        }
    }
}
