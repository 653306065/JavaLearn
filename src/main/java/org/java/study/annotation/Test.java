package org.java.study.annotation;

import java.lang.annotation.Annotation;
import java.lang.annotation.Documented;
import java.util.List;

public class Test {

    @Person("老师")
    @Person("父亲")
    @Person("儿子")
    @Person("丈夫")
    private List<String> values;

    public static void main(String[] args) throws NoSuchFieldException {
        Persons persons = Test.class.getDeclaredField("values").getAnnotation(Persons.class);
        for (Person person : persons.value()) {
            System.out.println(person.value());
        }
        Annotation[] annotations=  Test.class.getDeclaredField("values").getAnnotations();
        for(Annotation annotation:annotations){
            System.out.println(annotation.annotationType());
        }
    }
}
