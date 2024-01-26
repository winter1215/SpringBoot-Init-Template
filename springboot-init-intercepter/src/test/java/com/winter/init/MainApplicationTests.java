package com.winter.init;

import javax.annotation.Resource;

import com.plexpt.chatgpt.ChatGPT;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * 主类测试

 */
@SpringBootTest
class MainApplicationTests {

    @Test
    void contextLoads() {
        ChatGPT chatGPT = ChatGPT.builder()
                .apiKey("")
                .apiHost("https://cfwus02.opapi.win/")
                .build()
                .init();
        String ans = chatGPT.chat("鲁迅为什么暴打周树人");
        System.out.println(ans);
    }

}
