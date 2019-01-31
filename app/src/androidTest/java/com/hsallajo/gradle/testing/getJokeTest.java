package com.hsallajo.gradle.testing;

import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.hsallajo.gradle.makeajoke.MainActivity;
import com.hsallajo.gradle.makeajoke.R;

import org.hamcrest.Description;
import org.hamcrest.TypeSafeMatcher;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.withId;

@RunWith(AndroidJUnit4.class)
public class getJokeTest {

    @Rule
    public ActivityTestRule<MainActivity> mActivityActivityTRestRule
            = new ActivityTestRule<>(MainActivity.class);

    @Test
    public void clickButton_loadsNonEmptyJokeString(){

        onView(withId(R.id.btn_tell_joke))
                .perform(click());

        onView(withId(R.id.tv_joke_content))
                .check(matches(new JokeTextMatcher()));

    }

    public class JokeTextMatcher extends TypeSafeMatcher<View> {

        @Override
        protected boolean matchesSafely(View item) {

            String s = ((TextView) item).getText().toString();
            return !TextUtils.isEmpty(s);

        }

        @Override
        public void describeTo(Description description) {
        }
    }
}
