package com.jgw.common_library.utils;


import androidx.databinding.ViewDataBinding;
import androidx.lifecycle.AndroidViewModel;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * Created by jingbin on 2018/12/26.
 */

public class ClassUtils {

    /**
     * 获取泛型ViewModel的class对象
     */
    public static <T> Class<T> getViewModel(Object obj) {
        Class<?> currentClass = obj.getClass();
        Class<T> tClass = getGenericClass(currentClass, AndroidViewModel.class);
        if (tClass == null || tClass == AndroidViewModel.class) {
            return null;
        }
        return tClass;
    }

    public static <T> Class<T> getViewBinding(Object obj) {
        Class<?> currentClass = obj.getClass();
        Class<T> tClass = getGenericClass(currentClass, ViewDataBinding.class);
        if (tClass == null || tClass == ViewDataBinding.class) {
            return null;
        }
        return tClass;
    }

    public static <T> Class<T> getClassBySuperClass(Object obj,Class<T> clazz) {
        Class<?> currentClass = obj.getClass();
        Class<T> tClass = getGenericClass(currentClass, clazz);
        if (tClass == null || tClass == clazz) {
            return null;
        }
        return tClass;
    }

    @SuppressWarnings("SameParameterValue")
    private static <T> Class<T> getGenericClass(Class<?> klass, Class<?> filterClass) {
        Type type = klass.getGenericSuperclass();
        if (!(type instanceof ParameterizedType)) {
            return null;
        }
        ParameterizedType parameterizedType = (ParameterizedType) type;
        Type[] types = parameterizedType.getActualTypeArguments();
        for (Type t : types) {
            Class<T> tClass;
            if (t instanceof Class) {
            //noinspection unchecked
                tClass = (Class<T>) t;
            }else {
                return null;
            }
            if (filterClass.isAssignableFrom(tClass)) {
                return tClass;
            }
        }
        return null;
    }
}
