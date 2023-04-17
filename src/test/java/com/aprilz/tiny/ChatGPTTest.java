package com.aprilz.tiny;

import cn.hutool.http.ContentType;
import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONObject;
import com.aprilz.tiny.dto.ChatGPTMessage;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;

@SpringBootTest
public class ChatGPTTest {

    @Test
    public void test1() {
        JSONObject body = new JSONObject();
        body.set("model", "gpt-3.5-turbo")
        .set("messages", "[{\"role\": \"user\", \"content\": \"Hello!\"}]");

        String result = HttpUtil.createPost("https://api.openai.com/v1/chat/completions")
                .contentType(ContentType.JSON.getValue()).body(body.toString())
                .header("Authorization", "Bearer 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")
                .execute().body();
        System.out.println(result);
    }

    public static void main(String[] args) {
        JSONObject body = new JSONObject();
        ChatGPTMessage message = new ChatGPTMessage();
        message.setRole("user");
        message.setContent("Hello!");
        body.set("model", "gpt-3.5-turbo")
                .set("messages", Arrays.asList(message));

        String result = HttpUtil.createPost("https://open.aprilz.ml/v1/chat/completions")
                .contentType(ContentType.JSON.getValue()).body(body.toString())
                .header("Authorization", "Bearer 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")
                .execute().body();
        // return message
        // {"id":"chatcmpl-76EwRpvOVdOjbDkchhOOcfey2dgQk",
        // "object":"chat.completion",
        // "created":1681721759,
        // "model":"gpt-3.5-turbo-0301",
        // "usage":{"prompt_tokens":10,"completion_tokens":10,"total_tokens":20},
        // "choices":[{"message":{"role":"assistant","content":"Hello there! How can I assist you today?"},
        // "finish_reason":"stop",
        // "index":0}]}
        System.out.println(result);
    }
}

