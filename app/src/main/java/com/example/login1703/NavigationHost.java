package com.example.login1703;

import androidx.fragment.app.Fragment;

import com.google.android.gms.maps.MapFragment;

public interface NavigationHost {
    void navigateTo(Fragment fragment, boolean addToBackstack);
    void navigateMenu(Fragment fragment, boolean addToBackstack);
    void navigateMenuAdd(Fragment fragment, boolean addToBackstack);
}
