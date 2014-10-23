package phecda.core;

import org.junit.Test;

import java.util.Date;

import static java.util.Optional.empty;
import static java.util.Optional.of;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static phecda.core.Adaptable.getAdapter;

public class AdapterTest {
    @Test
    public void should_use_first_applicable_adapter_provider() {
        assertThat(getAdapter("5", Integer.class), is(of(5)));
    }

    @Test
    public void should_return_empty_if_cannot_adapt_to_adapter_type() {
        assertThat(getAdapter("5", Date.class), is(empty()));
    }

    public static class StringToInt implements Adaptable<String, Integer> {
        @Override
        public Integer getAdapter(String string) {
            return Integer.parseInt(string);
        }
    }

    public static class StringToNegativeInt implements Adaptable<String, Integer> {
        @Override
        public Integer getAdapter(String string) {
            return -Integer.parseInt(string);
        }
    }
}
