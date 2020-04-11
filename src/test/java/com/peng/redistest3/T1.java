package com.peng.redistest3;

import org.junit.Test;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedHashSet;
import java.util.Set;

public class T1 {

    @Test
    public void t1(){
        Date date=new Date();
        System.out.println(date.toString());
        System.out.println(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(date));
        System.out.println(date.getTime());
    }
    @Test
    public void t2(){
        String tags="1";
        String[] ids=tags.split(",");
        System.out.println(ids.length);
        for(String id:ids){
            System.out.println(id);
        }
    }
    @Test
    public void t3(){
//        Set<String> ids=new HashSet<>();
        Set<String> ids=new LinkedHashSet<>();
        ids.add("6");
        ids.add("1");
        ids.add("4");
        for(String id:ids){
            System.out.println(id);
        }
    }
}
