package dkarlsso.commons.annotation;

import dkarlsso.commons.commandaction.CommandAction;
import dkarlsso.commons.commandaction.CommandActionException;
import dkarlsso.commons.model.CommonsException;
import org.reflections.Reflections;
import org.reflections.scanners.MethodAnnotationsScanner;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;
import java.util.function.Function;

public final class AnnotationFinder {

    private static final List<Object> allInstantiatedObjects = new ArrayList<>();

    public static <E, A extends Annotation>  List<E> findClassesWithAnnotation(final String packageToSearch,
                                                                               final Class<A> targetAnnotation) throws CommonsException {
        final List<E> list = new ArrayList<>();
        final Reflections ref = new Reflections(packageToSearch);
        for (final Class<?> cl : ref.getTypesAnnotatedWith(targetAnnotation)) {
            try {
                final Object object = cl.getConstructor().newInstance();
                allInstantiatedObjects.add(object);
                list.add((E)object);
            } catch (final Exception e) {
                throw new CommonsException("Could not create objects on all annotated classes: " + e.getMessage(), e);
            }
        }
        return list;
    }

    public static <E extends Enum, A extends Annotation, I>  Map<E, I> findClassesWithAnnotation(final String packageToSearch,
                                                                                                          Class<A> targetAnnotation,
                                                                                                          Function<A, E> function) throws CommonsException {
        final Map<E, I> map = new LinkedHashMap<>();
        final Reflections ref = new Reflections(packageToSearch);
        for (Class<?> cl : ref.getTypesAnnotatedWith(targetAnnotation)) {
            final A annotation = cl.getAnnotation(targetAnnotation);
            try {
                final Object object = cl.getConstructor().newInstance();
                allInstantiatedObjects.add(object);
                map.put(function.apply(annotation), (I)object);
            } catch (final Exception e) {
                throw new CommonsException("Could not create objects on all annotated classes: " + e.getMessage(), e);
            }

        }
        return map;
    }


    public static <E extends Enum, A extends Annotation>  Map<E, CommandAction> findMethodsWithAnnotation(final String packageToSearch,
                                                                                                 Class<A> targetAnnotation,
                                                                                                 Function<A, E> function) throws CommonsException {

        final Map<Class, Object> declaredClassMap = new HashMap<>();
        final Map<E, CommandAction> map = new LinkedHashMap<>();
        final Reflections ref = new Reflections(packageToSearch, new MethodAnnotationsScanner());

        for (final Method method : ref.getMethodsAnnotatedWith(targetAnnotation)) {
            try {
                final A findable = method.getAnnotation(targetAnnotation);

                final Class declaringClass = method.getDeclaringClass();

                final Object objectContainingMethod;
                if(!declaredClassMap.containsKey(declaringClass)) {
                    objectContainingMethod = method.getDeclaringClass().getConstructor().newInstance();
                    allInstantiatedObjects.add(objectContainingMethod);
                    declaredClassMap.put(declaringClass, objectContainingMethod);
                }
                else {
                    objectContainingMethod = declaredClassMap.get(declaringClass);
                }

                final CommandAction commandAction = () -> {
                    try {
                        method.invoke(objectContainingMethod);
                    } catch (final IllegalAccessException | InvocationTargetException e) {
                        throw new CommandActionException("Could not invoke method: " + e.getMessage(), e);
                    }
                };

                final E mapKey = function.apply(findable);
                map.put(mapKey, commandAction);
            } catch (final Exception e) {
                throw new CommonsException("Could not create objects and methods on all annotated methods: " + e.getMessage(), e);
            }
        }
        return map;
    }

    public static List<Object> getAllInstantiatedObjects() {
        final List<Object> objects = new ArrayList<>(allInstantiatedObjects);
        allInstantiatedObjects.clear();
        return objects;
    }

}
