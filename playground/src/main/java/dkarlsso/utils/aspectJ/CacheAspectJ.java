package dkarlsso.utils.aspectJ;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;

import java.util.HashMap;
import java.util.Map;

@Aspect
public class CacheAspectJ {

    private Map<String, Object> cache = new HashMap<String, Object>();


    /**
     * Pointcut for all methods annotated with <code>@Cacheable</code>
     */
    @Pointcut("execution(@Cacheable * *.*(..))")
    @SuppressWarnings("unused")
    private void cache() {
    }

    @Around("cache()")
    public Object aroundCachedMethods(final ProceedingJoinPoint joinPoint)
            throws Throwable {
        System.out.println("Executing aspect");

        final StringBuilder keyBuff = new StringBuilder();

        keyBuff.append(joinPoint.getTarget().getClass().getName());
        keyBuff.append(".").append(joinPoint.getSignature().getName());

        keyBuff.append("(");
        for (final Object arg : joinPoint.getArgs()) {
            keyBuff.append(arg.getClass().getSimpleName() + "=" + arg + ";");
        }
        keyBuff.append(")");
        final String key = keyBuff.toString();

        System.out.println("Key " + key);
        Object result = cache.get(key);
        if (result == null) {
            result = joinPoint.proceed();
            System.out.println("Fetching result");
            cache.put(key, result);
        } else {
            System.out.println("Result in cache");
        }

        return result;
    }
}
