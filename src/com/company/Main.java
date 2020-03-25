package com.company;

import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.List;

public class Main {

    public static void main(String[] args) {
        Object[] objects = {new Class1(), new Class2(), new Class3(), new Class4(), new Class5(), new Class6()};
        getDeprecatedObjects(objects);
    }

    public static void getDeprecatedObjects(Object[] objects) {
        List<Object> objectsDeprecated = new ArrayList<>();
        List<Object> objectsList = new ArrayList<>();

        for (int i = 0; i < objects.length; i++) {
            Annotation annotation = objects[i].getClass().getAnnotation(Deprecated.class);
            if (annotation != null) {
                objectsDeprecated.add(objects[i]);
            } else {
                objectsList.add(objects[i]);
            }
        }

        changeClass(objectsDeprecated, objectsList);
    }

    public static void changeClass(List<Object> objectsDeprecated, List<Object> objects) {
        for (Object o : objectsDeprecated) {
            Class<?> clazz = o.getClass().getSuperclass();
            if (clazz != Object.class) {
                for (Object obj : objects) {
                    Class<?> superclass = obj.getClass().getSuperclass();
                    if (superclass.equals(clazz)) {
                        String clazzSimpleName = obj.getClass().getSimpleName();
                        String objectSimpleName = o.getClass().getSimpleName();
                        System.out.println(objectSimpleName + " устаревший, его можно заменить на " + clazzSimpleName);
                    }
                }
            } else {
                Class<?>[] type = o.getClass().getInterfaces();
                if (type != null) {
                    for (Object obj : objects) {
                        Class<?>[] interfaces = obj.getClass().getInterfaces();
                        if (interfaces != null) {
                            for (Object t : type) {
                                for (Object c : interfaces) {
                                    if (t.equals(c)) {
                                        String objectK = obj.getClass().getSimpleName();
                                        String objectI = o.getClass().getSimpleName();
                                        System.out.println(objectI + " устаревший, его можно заменить на " + objectK);
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

