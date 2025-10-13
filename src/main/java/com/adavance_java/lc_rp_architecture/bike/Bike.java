package com.adavance_java.lc_rp_architecture.bike;

import com.adavance_java.lc_rp_architecture.Vehicle;

public  abstract class Bike implements Vehicle {

    @Override
    public void breaks() {
        System.out.println("Bike has normal breaks");
    }
}
