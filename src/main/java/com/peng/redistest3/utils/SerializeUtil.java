package com.peng.redistest3.utils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;


/**
 * 序列化和反序列化是通过对象输入/输出流完成的
 */
public class SerializeUtil {
    /*
     * 序列化
     * */
    public static byte[] serizlize(Object object){
        ObjectOutputStream oos = null;
        ByteArrayOutputStream baos = null;
        try {
            baos = new ByteArrayOutputStream();
            //输出目的地：字节数组（字节数组输出流底层是一个字节数组）
            oos = new ObjectOutputStream(baos);
            oos.writeObject(object);
            byte[] bytes = baos.toByteArray();
            return bytes;
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            try {
                if(baos != null){
                    baos.close();
                }
                if (oos != null) {
                    oos.close();
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
        return null;
    }
    /*
     * 反序列化
     * */
    public static Object deserialize(byte[] bytes){
        ByteArrayInputStream bais = null;
        ObjectInputStream ois = null;
        try{
            //输入来源：字节数组（字节数组输入流底层是一个字节数组）
            bais = new ByteArrayInputStream(bytes);
            ois = new ObjectInputStream(bais);
            return ois.readObject();
        }catch(Exception e){
            e.printStackTrace();
        }finally {
            try {

            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
        return null;
    }
}