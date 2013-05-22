package com.amnesty.panicbutton;

import android.app.Application;
import android.content.SharedPreferences;
import android.location.Location;
import android.location.LocationManager;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.shadows.ShadowPreferenceManager;

import static com.amnesty.panicbutton.ApplicationSettings.completeFirstRun;
import static com.amnesty.panicbutton.ApplicationSettings.isFirstRun;
import static junit.framework.Assert.assertNotNull;
import static junit.framework.Assert.assertNull;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

@RunWith(RobolectricTestRunner.class)
public class ApplicationSettingsTest {

    private SharedPreferences sharedPreferences;
    private Application context;

    @Before
    public void setUp() {
        context = Robolectric.application;
        sharedPreferences = ShadowPreferenceManager.getDefaultSharedPreferences(context);
    }

    @Test
    public void shouldReturnTrueWhenOnFirstRun() {
        assertTrue(isFirstRun(context));
    }

    @Test
    public void shouldReturnNullIfThereIsNoLocationStored() {
        assertNull(ApplicationSettings.getCurrentBestLocation(context));
    }

    @Test
    public void shouldSaveTheGivenLocation() {
        ApplicationSettings.setCurrentBestLocation(context, new Location(LocationManager.NETWORK_PROVIDER));
        assertNotNull(ApplicationSettings.getCurrentBestLocation(context));
    }

    @Test
    public void shouldUpdateFirstRunFlagOnCompletion() {
        completeFirstRun(context);
        assertFalse(sharedPreferences.getBoolean("FIRST_RUN", true));
    }

    @Test
    public void shouldReturnFalseWhenFirstRunCompleted() {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean("FIRST_RUN", false);
        editor.commit();
        assertFalse(isFirstRun(context));
    }
}
