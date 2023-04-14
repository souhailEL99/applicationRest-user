/**
 * Cette classe est un aspect Spring qui permet de donner des informations sur le temps d'exécution de chaque méthode de la classe UserService 
 * et aussi sur leurs valeurs de retour.
 * @Component pour que Spring puisse l'instancier et la gérer en tant que composant.
 */
package com.example.utilisateur.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class TraceAspect {

    private static final Logger logger = LoggerFactory.getLogger(TraceAspect.class);

    /**
     * Surveille toutes les méthodes de la classe UserService lors de l'exécution de
     * cet aspect.
     */
    @Pointcut("within(com.example.utilisateur.restservice.UserService)")
    public void userControllerMethods() {
    }

    /**
     * Cette méthode est appelée autour de chaque méthode dans l'ensemble des
     * méthodes dans "userControllerMethods".
     * Permet d'enregistrer le temps d'exécution mesuré dans les logs et l'afficher
     * dans la console.
     * 
     * @param joinPoint l'objet ProceedingJoinPoint contenant des informations sur
     *                  la méthode interceptée.
     * @return le résultat de la méthode interceptée.
     * @throws Throwable si une exception se produit pendant l'exécution de la
     *                   méthode.
     */
    @Around("userControllerMethods()")
    public Object logExecutionTime(ProceedingJoinPoint joinPoint) throws Throwable {
        long startTime = System.currentTimeMillis();
        Object result = joinPoint.proceed();
        long elapsedTime = System.currentTimeMillis() - startTime;
        logger.info("Method {} execution time: {} ms", joinPoint.getSignature().getName(), elapsedTime);
        System.out.println("Method " + joinPoint.getSignature().getName() + "execution time: " + elapsedTime + " ms");
        return result;
    }

    /**
     * Cette méthode est appelée après l'exécution de chaque méthode dans l'ensemble
     * des méthodes dans "userControllerMethods".
     * Permet d'enregistrer la valeur retournée par la méthode dans les logs et
     * l'afficher dans la console.
     * 
     * @param joinPoint l'objet JoinPoint contenant des informations sur la méthode
     *                  interceptée.
     * @param result    le résultat de la méthode interceptée.
     */
    @AfterReturning(pointcut = "userControllerMethods()", returning = "result")
    public void logReturnValue(JoinPoint joinPoint, Object result) {
        logger.info("The method {} returned: {}", joinPoint.getSignature().getName(), result);
        System.out.println("The method " + joinPoint.getSignature().getName() + "returned: " + result);
    }
}
