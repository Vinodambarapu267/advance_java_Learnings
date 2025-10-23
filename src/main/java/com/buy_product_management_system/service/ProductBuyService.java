package com.buy_product_management_system.service;

import com.buy_product_management_system.bean.User_Account;

import java.sql.Blob;

public interface ProductBuyService {

    public int addCustomer(User_Account acc);

    public void fetch_details(int user_id);

    public void deleteUser(int user_id);

    public void add_money(int user_id, double amount);

    public double purchaseProduct(int user_id, double Price);

    public double viewWalletBalance(int user_id);

    public void updatePhoto(int user_id, Blob photo);
}
