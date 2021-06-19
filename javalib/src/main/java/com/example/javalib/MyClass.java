package com.example.javalib;

import java.util.*;

public class MyClass {
    public static void main(String[] args) {
        Scanner obj1 = new Scanner(System.in);
        System.out.println("Enter input:");
        int a = obj1.nextInt();
        do{
            int b = a % 10;
            System.out.println(b);
            a=a/10;


        }while (a>0);

    }
}