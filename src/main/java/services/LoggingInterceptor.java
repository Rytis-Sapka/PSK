package services;

import javax.interceptor.AroundInvoke;
import javax.interceptor.Interceptor;
import javax.interceptor.InvocationContext;
import java.io.Serializable;

@Interceptor
@LogInterceptor
public class LoggingInterceptor implements Serializable {
    @AroundInvoke
    public Object logInterceptor(InvocationContext context) throws Exception {
        try {
            return context.proceed();
        } finally {
            String methodName = context.getMethod().getName();
            String className = context.getTarget().getClass().getName();
            System.out.printf("Method %s in class %s was invoked\n", methodName, className);
        }
    }
}