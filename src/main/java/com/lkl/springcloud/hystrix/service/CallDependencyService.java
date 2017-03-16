package com.lkl.springcloud.hystrix.service;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.springframework.stereotype.Component;

import java.util.Random;

/**
 * 调用依赖服务，通过hystrix包装调用服务
 * Created by liaokailin on 16/5/1.
 */
@Component
public class CallDependencyService {

    private Random random = new Random();
    private static final Object obj = new Object();
    private int randomInt = 0;

    /**
     * 模拟获取用户信息(通过网络调用)
     *
     * @return
     */
    @HystrixCommand(fallbackMethod = "fallback")
    public String mockGetUserInfo() {
        //int randomInt= random.nextInt(10) ;

        synchronized (obj) {
            randomInt++;
        }
        if (randomInt < 8) {  //模拟调用失败情况
            throw new RuntimeException("call dependency service fail.");
        } else {
            synchronized (obj) {
                randomInt = 0;
            }
            return "UserName:liaokailin;number:" + randomInt;
        }
    }

    public String fallback() {
        return "some exception occur call fallback method.";
    }
}
