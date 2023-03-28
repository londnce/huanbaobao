package com.wsf.huanbaobao.config;

import lombok.Data;
import org.springframework.stereotype.Component;

//支付宝沙箱支付配置类
@Data
@Component
public class AlipayConfig {
    //自己的appId
    public static String appId = "2021000122606821";
    //应用私有秘钥
    public static String appPrivateKey = "MIIEvQIBADANBgkqhkiG9w0BAQEFAASCBKcwggSjAgEAAoIBAQCW1iyYdV5+NYN3YHPN3JNlu3RvY5xDl47TuoW4Jg/DGkrqNpfkTKJit48cce8idIsRiWSGI5yzuOi+XqbzGrJ6wAWmjJByn+E5i6LP7nM2bTfmkcgv+Pe38CLXecapail6G63zsz8RXDTHXiUz4skCGmy92fI0gs04CWwnzRwDPzPj+GZTtyV8micB4DfV8TRp0ndL7PUVHuu7xOjdaFUoVwpmYzoRopqC1XX/ROu52MZS9TLYUOoDwMqEMjs83JJYljK9/tmkVAUEEZswFwqnIeVmUJPKpvLfR+1EUX4CfRn5l3oLM23cOXV/NIbq+AxJJSU1/D/Mx80KLg0Ow+DfAgMBAAECggEAIv67x/9rFfMKdlbdZ0SmufKc1/8PUStHit36ijfibkyaVu6lnOUew7J8tIfGiILrhP0QYoK+p3/eF9TT/RETmaEVboesN7vaQESiWIWlG1qJnqfx/xdPOdCv9LgfHE0yJE9ps2HB3yA6aorwLLKQGjnRQtbe98nlHPwqqK1Qs3vIgYi8CfZcUMTpHrchLLDRBgf+XvrKccsGXOra5EC2fZgUu+0yD0Kl+ZM+fBNblUEP10JgieXE6xbYEVWRY2IS+ZN9TTMXPOvXe4XD8ObYRvXHhB1r7Kuipn83eKEBX/R6W6Fs81ulbABHoe7njJajr5sQUNX3Bf2PH84090AAUQKBgQDumRJVanUmEQeibzN8vMeO2+qE4Q8l/R2hRcVYAVWHcIJDtm7jNt+IcES9KZtdH/5I2EqguwmIYiEuKJpD4NLEjLExGM9SKRmwsoqnGhkhXYESlTbvCfZpu8F/4cPDWsu9VGH5w+YTL1D8Xmc0FXoytdKo08Jm9QSLhAho2OrnmQKBgQCh1nyTSCRYB2QVoFMyIsbJhREJyXw3klk1cyqE52TvykY/wkpodTm/YmaP7ucq86669f2PxPuRxPv5OayP4f/DZbjyexIgDdst24yV/e+dMIpwj3SYEr3ZF+j9NJ2OMLpl2mY7sKN1ycmvbCdKQX64hWtfI+i7F+Z1k5gIbkR3NwKBgCH1x5ZAwMvaQKvuB/YJKJ48zvR61B0kGcOKmrwb9ICCkwfwP3g3pzlUQXRrJ6Hpm9ficTzPYlv3G6HmRY7DwTwYil/imB+pme58ex+tp0YIeQacFmWPlXMXNkNtbt+vXLBmAkv6hhTU90H0bdwL8oilOIavHUxqM2toL4o81bKBAoGAfqp/ak8GkxeF0B9hvlXVvJg3k69tV5ll1R3EY60wJTXt8a1By6wkfJSprXHQ4J9FXPN3d/U26QqCJLhtRVgfdHHDZZXJw+jUaXny+Np9lkwNrre5C+VvTeEW7EcoIcglu95KMeG4rlI3VuORhN7qLcXPGfHjD7xPFitkzQRw9dUCgYEAjV/00PigAsiKD3U/fnWVexqbj6/hczPRgtGDtEYWz3Eyd2LB9p3OdbxxKaVk/EfeIEjT65yKDlpIKeFb9lwzKniItUuHUAEC74LYne3fC9PDIlxKGuZG/CfmXZzGCS9tz0V0BzT794QYO1JOZk1NZjSzpxNBpcXihxrWJfPW71M=";
    //支付宝公钥
    public static String alipayPublicKey = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAj98bEMZtwlyihxAp+sq9sAj9g35m+hBm5AmqfSqXqN3zxV0g1F/L4Dg+pUCfCx71nLFZHrZ80RGh1ge4rBQ8kUW0VvRE4xLfL9+xr3fMVd9GCq6kYhV2OLmS2MZwv0z9Wf1WbHQFs+398syxjRNtpDeANkogXbhxwiYyuYtuq8vzjOwTgEGUeLZmVaFstUIW3Dn+gJ0AR5HTvfCU4szUvmvfsbZpdOCC+jpEQUOJHwsoNawwuzf0qec0HPInRXRVB++kZXQkDRvYZZ4155sW3JrqOKi24Gv4s0z4hMHwNwrLGbhXiQc65UW0or1KFLSwk4nFexVImKSSrPpLnjcXYwIDAQAB";
    //异步回调地址
    public static String notifyUrl = "http://3r5arf.natappfree.cc/alipay/notifyNotice";
    //同步回调地址
    public static String returnUrl = "http://3r5arf.natappfree.cc/alipay/returnNotice";
    //推荐使用这个秘钥
    public static String signType = "RSA2";
    //使用的编码格式
    public static String charset = "utf-8";
    //支付宝默认网关
    public static String gatewayUrl = "https://openapi.alipaydev.com/gateway.do";

}
