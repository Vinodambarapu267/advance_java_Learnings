package com.jdbc.lab_tasks.product_management_system.exceptions;

public class ProductNotExistException extends Exception{
    public ProductNotExistException(String message) {
        super(message);
    }
}
