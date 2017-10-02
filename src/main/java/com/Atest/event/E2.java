package com.Atest.event;

/**
 * Created by 10539 on 2017/9/19.
 */
public class E2 implements Event{
    public Object getSource() {
        return null;
    }

    public Object getTarget() {
        return null;
    }

    public void test2() {
        System.out.println(aa1);
    }

    public int getType() {
        System.out.println(aa);
        return 0;
    }



    public Object getData() {
        return null;
    }
}
