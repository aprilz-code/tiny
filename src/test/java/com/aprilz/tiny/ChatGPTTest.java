package com.aprilz.tiny;

import cn.hutool.http.ContentType;
import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONObject;
import com.aprilz.tiny.dto.ChatGPTMessage;
import com.unfbx.chatgpt.OpenAiStreamClient;
import com.unfbx.chatgpt.entity.billing.BillingUsage;
import com.unfbx.chatgpt.entity.billing.CreditGrantsResponse;
import com.unfbx.chatgpt.entity.billing.Subscription;
import com.unfbx.chatgpt.entity.chat.ChatCompletion;
import com.unfbx.chatgpt.entity.chat.Message;
import com.unfbx.chatgpt.entity.completions.Completion;
import com.unfbx.chatgpt.function.KeyRandomStrategy;
import com.unfbx.chatgpt.interceptor.OpenAILogger;
import com.unfbx.chatgpt.sse.ConsoleEventSourceListener;
import lombok.extern.slf4j.Slf4j;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import org.junit.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.net.InetSocketAddress;
import java.net.Proxy;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collections;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

@Slf4j
@SpringBootTest
public class ChatGPTTest {

//    @Test
//    public void test1() {
//        JSONObject body = new JSONObject();
//        body.set("model", "gpt-3.5-turbo")
//        .set("messages", "[{\"role\": \"user\", \"content\": \"Hello!\"}]");
//
//        String result = HttpUtil.createPost("https://api.openai.com/v1/chat/completions")
//                .contentType(ContentType.JSON.getValue()).body(body.toString())
//                .header("Authorization", "Bearer sk-3OqTkzGPFDsX5xURfv6Chr4vDUtfT2hif3UcbAyssctMGwBh")
//                .execute().body();
//        System.out.println(result);
//    }



    private OpenAiStreamClient client;

    @BeforeEach
    public void before() {
        //国内访问需要做代理，国外服务器不需要
        Proxy proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress("127.0.0.1", 7890));
        HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor(new OpenAILogger());
        //！！！！千万别再生产或者测试环境打开BODY级别日志！！！！
        //！！！生产或者测试环境建议设置为这三种级别：NONE,BASIC,HEADERS,！！！
        httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.HEADERS);
        OkHttpClient okHttpClient = new OkHttpClient
                .Builder()
                .proxy(proxy)
                .addInterceptor(httpLoggingInterceptor)
                .connectTimeout(30, TimeUnit.SECONDS)
                .writeTimeout(30, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .build();
        client = OpenAiStreamClient.builder()
                .apiKey(Arrays.asList("sk-3OqTkzGPFDsX5xURfv6Chr4vDUtfT2hif3UcbAyssctMGwBh"))
                //自定义key的获取策略：默认KeyRandomStrategy
//                .keyStrategy(new KeyRandomStrategy())
                .keyStrategy(new FirstKeyStrategy())
                .okHttpClient(okHttpClient)
                //自己做了代理就传代理地址，没有可不不传（(关注公众号回复：openai ，获取免费的测试代理地址)）
              //  .apiHost("https://**********/")
                .build();
    }

    @Test
    public void subscription() {
        Subscription subscription = client.subscription();
        log.info("用户名：{}", subscription.getAccountName());
        log.info("用户总余额（美元）：{}", subscription.getHardLimitUsd());
        log.info("更多信息看Subscription类");
    }

    @Test
    public void billingUsage() {
        LocalDate start = LocalDate.of(2023, 3, 7);
        BillingUsage billingUsage = client.billingUsage(start, LocalDate.now());
        log.info("总使用金额（美分）：{}", billingUsage.getTotalUsage());
        log.info("更多信息看BillingUsage类");
    }
    @Test
    public void creditGrants() {
        CreditGrantsResponse creditGrantsResponse = client.creditGrants();
        log.info("账户总余额（美元）：{}", creditGrantsResponse.getTotalGranted());
        log.info("账户总使用金额（美元）：{}", creditGrantsResponse.getTotalUsed());
        log.info("账户总剩余金额（美元）：{}", creditGrantsResponse.getTotalAvailable());
    }

    @Test
    public void chatCompletions() {
        ConsoleEventSourceListener eventSourceListener = new ConsoleEventSourceListener();
        Message message = Message.builder().role(Message.Role.USER).content("random one word！").build();
        ChatCompletion chatCompletion = ChatCompletion
                .builder()
                .model(ChatCompletion.Model.GPT_3_5_TURBO.getName())
                .temperature(0.2)
                .maxTokens(2048)
                .messages(Collections.singletonList(message))
                .stream(true)
                .build();
        client.streamChatCompletion(chatCompletion, eventSourceListener);
        CountDownLatch countDownLatch = new CountDownLatch(1);
        try {
            countDownLatch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void completions() {
        System.setProperty("https.protocols", "TLSv1.2,TLSv1.1,SSLv3");
        ConsoleEventSourceListener eventSourceListener = new ConsoleEventSourceListener();
        Completion q = Completion.builder()
                .prompt("我想申请转专业，从计算机专业转到会计学专业，帮我完成一份两百字左右的申请书")
                .stream(true)
                .build();
        client.streamCompletions(q, eventSourceListener);
        CountDownLatch countDownLatch = new CountDownLatch(1);
        try {
            countDownLatch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

//    public static void main(String[] args) {
////        JSONObject body = new JSONObject();
////        ChatGPTMessage message = new ChatGPTMessage();
////        message.setRole("user");
////        message.setContent("Hello!");
////        body.set("model", "gpt-3.5-turbo")
////                .set("messages", Arrays.asList(message));
////
////        //System.setProperty("https.protocols", "TLSv1.2,TLSv1.1,SSLv3");
////        System.setProperty("proxyHost", "127.0.0.1"); // PROXY_HOST：代理的IP地址
////        System.setProperty("proxyPort",  "7890"); // PROXY_PORT：代理的端口号
////
////
////        String result = HttpUtil.createPost("https://api.openai.com/v1/chat/completions")
////               // .setProxy(new Proxy(Proxy.Type.HTTP,new InetSocketAddress("127.0.0.1",7890)))
////                .contentType(ContentType.JSON.getValue()).body(body.toString())
////                .header("Authorization", "Bearer sk-3OqTkzGPFDsX5xURfv6Chr4vDUtfT2hif3UcbAyssctMGwBh")
////                .execute().body();
////        // return message
////        // {"id":"chatcmpl-76EwRpvOVdOjbDkchhOOcfey2dgQk",
////        // "object":"chat.completion",
////        // "created":1681721759,
////        // "model":"gpt-3.5-turbo-0301",
////        // "usage":{"prompt_tokens":10,"completion_tokens":10,"total_tokens":20},
////        // "choices":[{"message":{"role":"assistant","content":"Hello there! How can I assist you today?"},
////        // "finish_reason":"stop",
////        // "index":0}]}
////        System.out.println(result);
//
//        //国内访问需要做代理，国外服务器不需要
////        Proxy proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress("127.0.0.1", 7890));
////        HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor(new OpenAILogger());
////        //！！！！千万别再生产或者测试环境打开BODY级别日志！！！！
////        //！！！生产或者测试环境建议设置为这三种级别：NONE,BASIC,HEADERS,！！！
////        httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.HEADERS);
////        OkHttpClient okHttpClient = new OkHttpClient
////                .Builder()
////                .proxy(proxy)//自定义代理
////                .addInterceptor(httpLoggingInterceptor)//自定义日志
////                .connectTimeout(30, TimeUnit.SECONDS)//自定义超时时间
////                .writeTimeout(30, TimeUnit.SECONDS)//自定义超时时间
////                .readTimeout(30, TimeUnit.SECONDS)//自定义超时时间
////                .build();
////        OpenAiStreamClient client = OpenAiStreamClient.builder()
////                .apiKey(Arrays.asList("sk-3OqTkzGPFDsX5xURfv6Chr4vDUtfT2hif3UcbAyssctMGwBh"))
////                //自定义key的获取策略：默认KeyRandomStrategy
////                .keyStrategy(new KeyRandomStrategy())
////                //.keyStrategy(new FirstKeyStrategy())
////                .okHttpClient(okHttpClient)
////                //自己做了代理就传代理地址，没有可不不传
//////                .apiHost("https://自己代理的服务器地址/")
////                .build();
//
//    }
}

