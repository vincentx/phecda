package phecda.core;

import java.lang.reflect.ParameterizedType;
import java.util.Optional;

import static java.util.Arrays.asList;
import static java.util.ServiceLoader.load;
import static java.util.stream.StreamSupport.stream;

public interface Adaptable<Target, Adapter> {
    Adapter getAdapter(Target target);

    default boolean isApplicable(Object target, Class<?> adapterType) {

        return asList(getClass().getGenericInterfaces()).stream()
                .filter(type -> type instanceof ParameterizedType && ((ParameterizedType) type).getRawType().equals(Adaptable.class))
                .findFirst().map(provider -> {
                    ParameterizedType superClass = (ParameterizedType) provider;
                    Class<?> expectedTargetType = (Class) superClass.getActualTypeArguments()[0];
                    Class<?> expectedAdapterType = (Class) superClass.getActualTypeArguments()[1];
                    return expectedTargetType.isAssignableFrom(target.getClass()) && expectedAdapterType.equals(adapterType);
                }).orElse(false);
    }

    public static <Adapter> Optional<Adapter> getAdapter(Object target, Class<Adapter> adapterType) {
        return stream(load(Adaptable.class).spliterator(), false)
                .filter(provider -> provider.isApplicable(target, adapterType)).findFirst()
                .map(provider -> (Adapter) provider.getAdapter(target));
    }
}
