package com.aprilz.tiny;

import cn.hutool.http.ContentType;
import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONObject;
import com.aprilz.tiny.dto.QyDocument;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

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
        body.set("model", "gpt-3.5-turbo")
                .set("messages", "[{\"role\": \"user\", \"content\": \"Hello!\"}]");

        String result = HttpUtil.createPost("https://open.aprilz.ml/v1/chat/completions")
                .contentType(ContentType.JSON.getValue()).body(body.toString())
                .header("Authorization", "Bearer sk-fZeximHi0eGupzkE5P7GT3BlbkFJGhdXIVJGON35O9ze")
                .execute().body();
        System.out.println(result);
    }
}

