package com.aprilz.tiny.designMode.singleton;

/**
 * @Classname UserSingleton
 * @Description 枚举单例模式
 * @Date 2023/6/24 13:59
 * @Created by aprilz
 */
public class UserSingleton {

    //私有化构造函数
    private UserSingleton() {
    }

    // 定义一个静态枚举类
     enum SingletonEnum {
        //创建一个枚举对象，该对象天生为单例
        INSTANCE;
        private UserSingleton user;

        //私有化枚举的构造函数
        SingletonEnum() {
            user = new UserSingleton();
        }

        public UserSingleton getInstnce() {
            return user;
        }
    }

    //对外暴露一个获取User对象的静态方法
    public static UserSingleton getInstance() {
        return SingletonEnum.INSTANCE.getInstnce();
    }
}
