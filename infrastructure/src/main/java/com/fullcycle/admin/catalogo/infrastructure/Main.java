package com.fullcycle.admin.catalogo.infrastructure;

import com.fullcycle.admin.catalogo.application.UseCase;

public class Main {
    static void main(String[] args) {
        System.out.println("hello world");
        System.out.println(new UseCase().execute());

    }
}