package com.example.moviesmanager;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.clearText;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.contrib.DrawerMatchers.isOpen;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

import androidx.test.espresso.contrib.DrawerActions;
import androidx.test.espresso.contrib.NavigationViewActions;
import androidx.test.espresso.contrib.RecyclerViewActions;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;
import androidx.test.rule.ActivityTestRule;

import com.example.moviesmanager.views.ui.MainActivity;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class MainActivityTest {
    @Rule
    public ActivityTestRule<MainActivity> activityActivityTestRule =
            new ActivityTestRule<MainActivity>(MainActivity.class);

    @Test
    public void probarBuscarPeliculaObtenerDetalles() throws InterruptedException {
        onView(withId(R.id.nav_busqueda)).perform(click());
        onView(withId(R.id.editText_movie_title)).perform(clearText(),typeText("Dune"));
        onView(withId(R.id.button_search)).perform(click());
        Thread.sleep(1000);
        onView(withId(R.id.peliculasRecyclerView)).perform(RecyclerViewActions.actionOnItemAtPosition(0,click()));
        Thread.sleep(1000);
        onView(withId(R.id.titleTextViewDetalle)).check(matches(withText("Dune")));
        onView(withId(R.id.duracionTextViewDetalle)).check(matches(withText("155 min")));
    }

    @Test
    public void probarNavBar(){
        onView(withId(R.id.drawer_layout)).perform(DrawerActions.open());
        onView(withId(R.id.drawer_layout)).check(matches(isOpen()));
        onView(withId(R.id.nav_view))
                .perform(NavigationViewActions.navigateTo(R.id.nav_configuracion));
        onView(withId(R.id.textViewConfigTitulo)).check(matches(withText("Perfil de Usuario")));
    }

}
