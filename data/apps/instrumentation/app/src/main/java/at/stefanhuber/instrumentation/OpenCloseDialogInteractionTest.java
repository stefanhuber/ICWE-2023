package at.stefanhuber.instrumentation;

import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;

import org.junit.Test;
import org.junit.runner.RunWith;

import at.stefanhuber.instrumentation.interactions.ClickInteraction;
import at.stefanhuber.instrumentation.interactions.Interaction;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class OpenCloseDialogInteractionTest {

    @Test
    public void startInteraction() {
        Interaction interaction = new ClickInteraction(0.5, 0.15);
        interaction.setExactDuration(1500);
        
        for (int i = 0; i < 5; i++) {
            interaction.start();
            interaction.start();
        }
    }

}
