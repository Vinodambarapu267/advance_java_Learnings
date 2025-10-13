package com.adavance_java.lc_rp_architecture.car;

import com.adavance_java.lc_rp_architecture.Vehicle;

public abstract class Car implements Vehicle {


    @Override
    public void breaks() {
        System.out.println("Car has normal breaks");
    }
}
