package com.scy.component.mylibrary;

import android.annotation.TargetApi;
import android.os.Build;

import com.scy.component.mylibrary.annotation.ServiceId;
import com.scy.component.mylibrary.model.Parameters;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import androidx.annotation.RequiresApi;

public class Registry {
    private static volatile Registry instance;
    /**
     * 服务表
     */
    private ConcurrentHashMap<String, Class<?>> mServices = new ConcurrentHashMap<>();

    /**
     * 方法表
     */
    private ConcurrentHashMap<Class<?>, Map<String, Method>> mMethods = new ConcurrentHashMap<>();
    /**
     * 类id：实例对象
     */
    private Map<String, Object> mObjects = new HashMap<>();

    private Registry(){}

    public static Registry getInstance(){
        if (instance == null){
            synchronized (Registry.class){
                if (instance == null){
                    instance = new Registry();
                }
            }
        }
        return instance;
    }

    /**
     * 做两张表
     * 1、服务表 Class的标记 ：Class<？>
     * 2、方法表 Class<?> : ["getLocation":Method,"setLocation":Method]
     * @param clazz
     */
//    @TargetApi(Build.VERSION_CODES.O)
//    public void regist(Class<?> clazz){
//        //通过反射获得类的标记
//        ServiceId annotation = clazz.getAnnotation(ServiceId.class);
//        //注册服务表
//        mServices.putIfAbsent(annotation.value(), clazz);
//        //注册服务对应的方法表
//        mMethods.putIfAbsent(clazz, new HashMap<String, Method>());
//        Map<String, Method> methodMap = mMethods.get(clazz);
//        Arrays.stream(clazz.getMethods())
//                .forEach(method ->
//                    methodMap.put(String.join("", method.getName(), Arrays.stream(method.getParameterTypes()).map(p -> p.getName()).collect( Collectors.joining(",", "(", ")"))), method)
//                );
//
//    }
    public void regist(Class<?> clazz) {
        /**
         * 注册服务表
         */
        //通过反射获得类的标记
        ServiceId annotation = clazz.getAnnotation(ServiceId.class);
        mServices.putIfAbsent(annotation.value(), clazz);

        /**
         * 注册服务对应的方法表
         */
        mMethods.putIfAbsent(clazz, new HashMap<String, Method>());
        Map<String, Method> methods = mMethods.get(clazz);
        //class中所有的方法
        for (Method method : clazz.getMethods()) {
            StringBuilder sb = new StringBuilder();
            sb.append(method.getName());
            sb.append("(");
            Class<?>[] parameterTypes = method.getParameterTypes();
            if (parameterTypes.length!=0){
                sb.append(parameterTypes[0].getName());
            }
            for (int i = 1; i < parameterTypes.length; i++) {
                sb.append(",").append(parameterTypes[i].getName());
            }
            sb.append(")");
            methods.put(sb.toString(), method);
        }
    }

    public  Class<?> getService(String serviceId){
        Class<?> aClass = mServices.get(serviceId);
        return aClass;
    }

//    @TargetApi(Build.VERSION_CODES.O)
//    public Method getMethod(Class<?> clazz, String methodName, Parameters[] parameters) {
//        String collect = Arrays.stream(parameters).map(p -> p.getType()).collect(Collectors.joining(",", "(", ")"));
//        return mMethods.get(clazz).get(collect);
//
//    }

    public Method getMethod2(Class<?> clazz, String methodName, Parameters[] parameters) {
        Map<String, Method> methods = mMethods.get(clazz);

        StringBuilder sb = new StringBuilder();
        sb.append(methodName);
        sb.append("(");
        if (parameters.length!=0){
            sb.append(parameters[0].getType());
        }
        for (int i = 1; i < parameters.length; i++) {
            sb.append(",").append(parameters[i].getType());
        }
        sb.append(")");

        Method method = methods.get(sb.toString());
        return method;
    }


    public void putObject(String serviceId, Object object){
        mObjects.put(serviceId, object);
    }
    public Object getObject(String serviceId){
        return mObjects.get(serviceId);
    }
}
