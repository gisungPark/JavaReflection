package com.cos.reflect.filter;

import org.junit.jupiter.api.Test;

class DispatcherTest {

    @Test
    public void 키값을세터로바꾸기() throws Exception{
        //given
        String key = "username";
        String firstKey = "set";
        String upperKey = key.substring(0, 1).toUpperCase();
        String remainKey = key.substring(1);

        //then
        System.out.println(firstKey);
        System.out.println(upperKey);
        System.out.println(remainKey);
        System.out.println(firstKey+upperKey+remainKey);
     }

}