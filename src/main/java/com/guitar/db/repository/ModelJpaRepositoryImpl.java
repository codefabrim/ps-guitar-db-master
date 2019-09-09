package com.guitar.db.repository;

public class ModelJpaRepositoryImpl  implements  ModelJpaRepositoryCustom {

    @Override
    public void aCustomeMethod() {
        System.out.println("I' m a custom method");
    }
}
