package phecda.core;

import java.lang.reflect.ParameterizedType;

import static java.util.Arrays.asList;

public interface AdapterProvider<Target extends Adaptable, Adapter> {
    Adapter getAdapter(Target target);

    default boolean isApplicable(Adaptable target, Class<?> adapterType) {

        return asList(getClass().getGenericInterfaces()).stream()
                .filter(type -> type instanceof ParameterizedType && ((ParameterizedType) type).getRawType().equals(AdapterProvider.class))
                .findFirst().map(provider -> {
                    ParameterizedType superClass = (ParameterizedType) provider;
                    Class<?> expectedTargetType = (Class) superClass.getActualTypeArguments()[0];
                    Class<?> expectedAdapterType = (Class) superClass.getActualTypeArguments()[1];
                    return expectedTargetType.isAssignableFrom(target.getClass()) && expectedAdapterType.equals(adapterType);
                }).orElse(false);
    }
}
