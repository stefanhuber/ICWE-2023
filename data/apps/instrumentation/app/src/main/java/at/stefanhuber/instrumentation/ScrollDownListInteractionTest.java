package at.stefanhuber.instrumentation;

import android.util.Log;

import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;
import org.junit.Test;
import org.junit.runner.RunWith;
import at.stefanhuber.instrumentation.interactions.Interaction;
import at.stefanhuber.instrumentation.interactions.ScrollDownListInteraction;


@RunWith(AndroidJUnit4.class)
@LargeTest
public class ScrollDownListInteractionTest {

    @Test
    public void startInteraction() {
        Interaction interaction = new ScrollDownListInteraction();
        interaction.setExactDuration(7000);
        interaction.start();
    }

}
