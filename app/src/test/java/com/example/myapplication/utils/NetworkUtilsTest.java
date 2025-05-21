package com.example.myapplication.utils; // Adjust package if necessary

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.NetworkCapabilities;
import android.os.Build;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class NetworkUtilsTest {

    @Mock
    Context mockContext;

    @Mock
    ConnectivityManager mockConnectivityManager;

    @Mock
    NetworkInfo mockNetworkInfo;

    @Mock
    NetworkCapabilities mockNetworkCapabilities;

    private void setupMocks(boolean isConnected, boolean isWifi, boolean isMobile, boolean isEthernet) {
        when(mockContext.getSystemService(Context.CONNECTIVITY_SERVICE)).thenReturn(mockConnectivityManager);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            when(mockConnectivityManager.getActiveNetwork()).thenReturn(mockConnectivityManager.getActiveNetwork()); // Dummy active network
            when(mockConnectivityManager.getNetworkCapabilities(mockConnectivityManager.getActiveNetwork())).thenReturn(mockNetworkCapabilities);
            if (isConnected) {
                when(mockNetworkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)).thenReturn(isWifi);
                when(mockNetworkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)).thenReturn(isMobile);
                when(mockNetworkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET)).thenReturn(isEthernet);
            } else {
                 when(mockConnectivityManager.getNetworkCapabilities(mockConnectivityManager.getActiveNetwork())).thenReturn(null);
            }
        } else {
            when(mockConnectivityManager.getActiveNetworkInfo()).thenReturn(mockNetworkInfo);
            when(mockNetworkInfo.isConnectedOrConnecting()).thenReturn(isConnected);
            if (isConnected) {
                when(mockNetworkInfo.isConnected()).thenReturn(true); // If isConnectedOrConnecting is true, isConnected is usually true for tests
                if (isWifi) {
                    when(mockNetworkInfo.getType()).thenReturn(ConnectivityManager.TYPE_WIFI);
                } else if (isMobile) {
                    when(mockNetworkInfo.getType()).thenReturn(ConnectivityManager.TYPE_MOBILE);
                } else if (isEthernet){
                    // TYPE_ETHERNET for older APIs might require different handling or might not be as common
                    // For simplicity, focusing on WiFi and Mobile for pre-M in this mock.
                    // If ethernet is true, we assume it's not WiFi or Mobile for this test setup.
                     when(mockNetworkInfo.getType()).thenReturn(ConnectivityManager.TYPE_ETHERNET);
                }
            }
        }
    }
    
    @Test
    public void isConnected_nullContext_returnsFalse() {
        assertFalse(NetworkUtils.isConnected(null));
    }

    @Test
    public void isConnected_nullConnectivityManager_returnsFalse() {
        when(mockContext.getSystemService(Context.CONNECTIVITY_SERVICE)).thenReturn(null);
        assertFalse(NetworkUtils.isConnected(mockContext));
    }

    @Test
    public void isConnected_connectedToWifi_returnsTrue() {
        setupMocks(true, true, false, false);
        assertTrue(NetworkUtils.isConnected(mockContext));
    }

    @Test
    public void isConnected_connectedToMobile_returnsTrue() {
        setupMocks(true, false, true, false);
        assertTrue(NetworkUtils.isConnected(mockContext));
    }
    
    @Test
    public void isConnected_connectedToEthernet_returnsTrue() {
        // This test is more relevant for Build.VERSION.SDK_INT >= Build.VERSION_CODES.M
        // For older versions, TYPE_ETHERNET might not be handled by the mockNetworkInfo.getType() as robustly
        setupMocks(true, false, false, true);
        assertTrue(NetworkUtils.isConnected(mockContext));
    }

    @Test
    public void isConnected_notConnected_returnsFalse() {
        setupMocks(false, false, false, false);
        assertFalse(NetworkUtils.isConnected(mockContext));
    }

    @Test
    public void isWifiConnected_nullContext_returnsFalse() {
        assertFalse(NetworkUtils.isWifiConnected(null));
    }
    
    @Test
    public void isWifiConnected_connectedToWifi_returnsTrue() {
        setupMocks(true, true, false, false);
        assertTrue(NetworkUtils.isWifiConnected(mockContext));
    }

    @Test
    public void isWifiConnected_connectedToMobile_returnsFalse() {
        setupMocks(true, false, true, false);
        assertFalse(NetworkUtils.isWifiConnected(mockContext));
    }
    
    @Test
    public void isWifiConnected_notConnected_returnsFalse() {
        setupMocks(false, false, false, false);
        assertFalse(NetworkUtils.isWifiConnected(mockContext));
    }

    @Test
    public void isMobileDataConnected_nullContext_returnsFalse() {
        assertFalse(NetworkUtils.isMobileDataConnected(null));
    }

    @Test
    public void isMobileDataConnected_connectedToMobile_returnsTrue() {
        setupMocks(true, false, true, false);
        assertTrue(NetworkUtils.isMobileDataConnected(mockContext));
    }

    @Test
    public void isMobileDataConnected_connectedToWifi_returnsFalse() {
        setupMocks(true, true, false, false);
        assertFalse(NetworkUtils.isMobileDataConnected(mockContext));
    }
    
    @Test
    public void isMobileDataConnected_notConnected_returnsFalse() {
        setupMocks(false, false, false, false);
        assertFalse(NetworkUtils.isMobileDataConnected(mockContext));
    }
}
