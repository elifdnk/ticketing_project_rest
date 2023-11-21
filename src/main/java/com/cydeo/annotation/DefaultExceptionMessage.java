package com.cydeo.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)  //Indicates that the relevant annotation can only be applied to methods.
@Retention(RetentionPolicy.RUNTIME) //this annotation say : will be retained and accessible during runtime in Java.
public @interface DefaultExceptionMessage {

    String defaultMessage() default "";//if we want to customize this message, DefaultExceptionMessageDTO is throw.

}

//we try to create one annotation,we can pu this annotation top of the methods, and we can say if there is any error happens show whatever this annotation is holding.