package com.adavance_java.lc_rp_architecture.bus;

import com.adavance_java.lc_rp_architecture.Vehicle;

import java.util.Scanner;

public abstract class Bus implements Vehicle {
    public void breaks(){
        System.out.println("Bus has normal Breaks");
    }


}
