package phecda.core;


import java.util.Optional;

import static java.util.ServiceLoader.load;
import static java.util.stream.StreamSupport.stream;

public interface Adaptable {
    default <Adapter> Optional<Adapter> getAdapter(Class<Adapter> adapterType) {
        return stream(load(AdapterProvider.class).spliterator(), false)
                .filter(provider -> provider.isApplicable(this, adapterType)).findFirst()
                .map(provider -> (Adapter) provider.getAdapter(this));
    }
}
