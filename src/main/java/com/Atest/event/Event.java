package com.Atest.event;

/**
 * Created by 10539 on 2017/9/19.
 */
public interface Event {
    Object getSource() ;

    Object getTarget();

    //接口中不能用static 修饰方法，only public & abstract ,测试版本jdk7
   // static void test1();
    void test2();

    //常量申明，在接口中定义的所有常量值都是公共的，静态的和最终的。 再次，您可以省略这些修饰符。
    public static  final String aa ="213";
    String aa1 ="213";


    int getType();

    Object getData();


}