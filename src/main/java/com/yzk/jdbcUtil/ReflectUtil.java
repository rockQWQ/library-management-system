package com.yzk.jdbcUtil;

import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.Map;

public class ReflectUtil {

    /**
     * 用于将HasmMapk中的数据映射到第二个参数指定的Class类中(JavaBean)
     * @param map 存储键值对的数据
     * @param cls
     * @return  返回第二个参数Class产生对象
     */
    public  static Object setMapValueToBean(Map<String,Object> map, Class cls){
        Object object = null;
        try {
            object = cls.newInstance();
        } catch (InstantiationException e) {
            System.out.println("无法为"+cls.getName()+"创建对象,请检查传入Class是否为接口或抽象类");
            return  null;
        } catch (IllegalAccessException e) {
            e.printStackTrace();
            System.out.println("无法为"+cls.getName()+"类创建对象，因为此类没有无参构造方法");
            return null;
        }


        //反射..
        //2通过返回动态调用对象的方法 （通过反射动态调用对象的方法  前提是你要获得被调用的方法对应的Method对象）
        Method[] methods = cls.getDeclaredMethods();//获得反射所代表的那个类的所有方法
        for(Method m:methods) {

            //将rs(hashMap)中的数据放到DeptBean相关属性中，肯定要调用setXXX方法
            //1.判断方法名是否以set开头,是set开头的方法取出set之后的子串，将首字母转为小写 ，
            //      处理完成后的字符串做HashMap 的key,从hashMap中取出对应的值
            //       然后用m.invoke调用方法将值传入到setXX方法中

            String methodName = m.getName();//得到方法名称
            if (methodName.startsWith("set")) {//判断方法名是否以set开头
                methodName = methodName.substring(3, 4).toLowerCase() + methodName.substring(4).toLowerCase();
                //  System.out.println("处理后的方法名:" + methodName);
                //根据方法名从rs(HashMap)中取出对应的值
                Object values = map.get(methodName);

                Class[] paramsTypes = m.getParameterTypes();//得到方法的形参类型列表

                if (paramsTypes.length == 1) {//只处理形参列表为1个参数的set方法
                    String firstParameterTypeName = paramsTypes[0].getName();//得到方法的第一个参数的类型名称(类名)


                    if (values != null) {
                        try {

                            String valuesType = values.getClass().getName();//map中取出的值的类名

                            if (valuesType.equals(firstParameterTypeName)) {//map中取出的值类型与方法的形参类型完全一致，直接将map的值做为invoke方法参数传入
                                m.invoke(object, values);//  invoke方法的第一个参数传入Class所表示的类的对象(被new 出来的对象)
                                //第二个参数传入方法需要的实参 （public void setDeptno(Integer deptno) ）
                            } else if (firstParameterTypeName.equals("java.lang.Integer")) {//判断方法的形参类型为Integer时，尽可能的将值的类型转换为Integer类型
                                //值不管是String还是double还是byte等,统一转换为String类型
                                String s = String.valueOf(values);

                                //验证map中取出的values转换为字符串后，用正则验证字符中否由全数字组成,是则执行if语句
                                if (s.matches("[0-9]+")) {
                                    m.invoke(object, Integer.parseInt(s));
                                } else {
                                    throw new RuntimeException("map key:" + methodName + " 的值"
                                            + values + "无法转换为Integer类型，无法为set" + methodName + "方法传值,请检查");
                                }

                            } else if (firstParameterTypeName.equals("float") || firstParameterTypeName.equals("java.lang.Float")) {
                                String s = String.valueOf(values);//  (String)values;    //把Map取出来的值转换成字符串

                                //验证map中取出的values转换为字符串后，用正则验证字符中否由全数字组成,是则执行if语句
                                if (s.matches("[0-9]+([.][0-9]+)?")) {
                                    m.invoke(object, Float.parseFloat(s));
                                } else {
                                    throw new RuntimeException("map key:" + methodName + " 的值"
                                            + values + "无法转换为Float类型，无法为set" + methodName + "方法传值,请检查");
                                }

                            } else if (firstParameterTypeName.equals("double") || firstParameterTypeName.equals("java.lang.Double")) {
                                String s = String.valueOf(values);//  (String)values;    //把Map取出来的值转换成字符串

                                //验证map中取出的values转换为字符串后，用正则验证字符中否由全数字组成,是则执行if语句
                                if (s.matches("[0-9]+([.][0-9]+)?")) {
                                    m.invoke(object, Double.parseDouble(s));
                                } else {
                                    throw new RuntimeException("map key:" + methodName + " 的值"
                                            + values + "无法转换为Double类型，无法为set" + methodName + "方法传值,请检查");
                                }

                            } else if (firstParameterTypeName.equals("java.util.Date")) {
                                //把字符串转换为Date类型   yyyy-MM-dd    yyyy-MM-dd HH:mm:ss
                                //用正则校验字符的格式是否为 :  yyyy-MM-dd    yyyy-MM-dd HH:mm:ss
                                String s = String.valueOf(values);

                                if (s.matches("[0-9]{4}[-](([0][1-9])|[1][0-2])[-][0-9]{2}")) {//验证年月日格式
                                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                                    java.util.Date d = sdf.parse(s);//把字符串转换为日期类型

                                    m.invoke(object, d);

                                } else {
                                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                                    java.util.Date d = sdf.parse(s);//把字符串转换为日期类型
                                    m.invoke(object, d);
                                }
                            } else {
                                // System.out.println("map中取出的值的类名:" + valuesType + "方法的参数类型为:" + firstParameterTypeName);
                                throw new RuntimeException("map中取出的值的类名:" + valuesType + "方法的参数类型为:"
                                        + firstParameterTypeName + " 请完善此if else代码");
                            }
                        } catch (Exception e) {
                            //  e.printStackTrace();
                            System.err.println("属性(方法):" + methodName + "赋值失败,原因:" + e.getMessage());
                        }

                    }else{
                        System.out.println("根据处理后的方法名:"+methodName+"从map中取不到相应的值");
                        //递归
                        System.out.println("方法的形参类型为:"+firstParameterTypeName);
                        if (firstParameterTypeName.startsWith("com.bjpoernode.Bean")) {//判断setXXX方法的形参数据是com.bean开头时，才递归调用

                            try {
                                //递归调用自身，根据 方法的形参类型，将map中的值再映射到方法的形参对应的javaBean
                                Object returVal = setMapValueToBean(map, Class.forName(firstParameterTypeName));
                                //setDeptBean(returVal)
                                m.invoke(object, returVal);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }

            }
        }

        return  object;
    }
}

