package dkarlsso.commons.annotation;

import dkarlsso.commons.commandaction.CommandAction;
import org.reflections.Reflections;

import java.lang.annotation.Annotation;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.function.Function;

public final class AnnotationFinder {

    public static <E extends Enum, A extends Annotation, I>  Map<E, I> findClassesWithAnnotation(final String packageToSearch,
                                                                                                          Class<A> targetAnnotation,
                                                                                                          Function<A, E> function) {
        final Map<E, I> map = new LinkedHashMap<>();
        final Reflections ref = new Reflections(packageToSearch);
        for (Class<?> cl : ref.getTypesAnnotatedWith(targetAnnotation)) {
            final A findable = cl.getAnnotation(targetAnnotation);
            try {
                final Object object = cl.getConstructor().newInstance();
                map.put(function.apply(findable), (I)object);
            } catch (final Exception e) {
                e.printStackTrace();
            }

        }
        return map;
    }
}
