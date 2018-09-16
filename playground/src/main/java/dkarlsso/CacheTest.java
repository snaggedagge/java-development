package dkarlsso;

import dkarlsso.utils.CacheableMethods;

public class CacheTest {


    public static void main(String[] args) throws InterruptedException {

        CacheableMethods cacheableMethods = new CacheableMethods();


        System.out.println("Result " + cacheableMethods.getLol(2,3));
        System.out.println("Result2 " + cacheableMethods.getLol(2,3));


        Thread.sleep(2000);

    }


    }
