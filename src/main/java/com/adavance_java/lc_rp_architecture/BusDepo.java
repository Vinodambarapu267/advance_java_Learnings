package com.adavance_java.lc_rp_architecture;
import java.sql.*;
import com.adavance_java.lc_rp_architecture.bike.HondaShine;
import com.adavance_java.lc_rp_architecture.bus.RedBus;
import com.adavance_java.lc_rp_architecture.car.Ford;

public class BusDepo {
    public static void main(String[] args) {
        Driver d = new Driver();
        d.drive(new RedBus());
        System.out.println("----------------------------------------------------");
        d.drive(new Ford());
        System.out.println("----------------------------------------------------");
        d.drive(new HondaShine());
    }
}
