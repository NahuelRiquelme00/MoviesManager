package com.example.moviesmanager;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.clearText;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;

import com.android21buttons.fragmenttestrule.FragmentTestRule;
import com.example.moviesmanager.views.ui.ConfiguracionFragment;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class ConfiguracionFragmentTestIU {

    @Rule
    public FragmentTestRule<?, ConfiguracionFragment> fragmentTestRule =
            FragmentTestRule.create(ConfiguracionFragment.class);

    @Test
    public void cambiarNombreUsuario(){
        onView(withId(R.id.textViewConfigUsuario)).perform(click());
        onView(withId(R.id.editTextUsuarioNombre)).check(matches((isDisplayed())));
        onView(withId(R.id.editTextUsuarioNombre)).perform(clearText(),typeText("Homero"));
        onView(withId(R.id.buttonADUsuarioAceptar)).perform(click());
        onView(withId(R.id.textViewConfigUsuario)).check(matches(withText("Homero")));
    }

    @Test
    public void cambiarCorreoUsuario(){
        onView(withId(R.id.textViewConfigCorreo)).perform(click());
        onView(withId(R.id.editTextCorreoDireccion)).check(matches((isDisplayed())));
        onView(withId(R.id.editTextCorreoDireccion)).perform(clearText(),typeText("Homero@gmail.com"));
        onView(withId(R.id.buttonADCorreoAceptar)).perform(click());
        onView(withId(R.id.textViewConfigCorreo)).check(matches(withText("Homero@gmail.com")));
    }


}
