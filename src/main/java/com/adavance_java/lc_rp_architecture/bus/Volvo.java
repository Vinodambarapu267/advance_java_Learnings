package com.adavance_java.lc_rp_architecture.bus;

public class Volvo extends Bus {

    @Override
    public void engine() {
        Class c  =getClass();
        System.out.println(c+" engine capacity is 20KMPh");
    }
}
