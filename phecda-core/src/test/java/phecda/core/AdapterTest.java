package phecda.core;

import org.junit.Test;

import java.util.Date;

import static java.util.Optional.empty;
import static java.util.Optional.of;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class AdapterTest {
    private Adaptable something = new SomethingAdaptable();

    @Test
    public void should_use_first_applicable_adapter_provider() {
        assertThat(something.getAdapter(String.class), is(of("adopt")));
    }

    @Test
    public void should_return_empty_if_cannot_adapt_to_adapter_type() {
        assertThat(something.getAdapter(Date.class), is(empty()));
    }

    public static class SomethingAdaptable implements Adaptable {
    }

    public static class SomethingToAdopt implements AdapterProvider<SomethingAdaptable, String> {

        @Override
        public String getAdapter(SomethingAdaptable somethingAdaptable) {
            return "adopt";
        }
    }

    public static class SomethingToNothing implements AdapterProvider<SomethingAdaptable, String> {

        @Override
        public String getAdapter(SomethingAdaptable somethingAdaptable) {
            return "nothing";
        }
    }
}
