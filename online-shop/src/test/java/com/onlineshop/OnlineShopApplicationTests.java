package com.onlineshop;

import com.onlineshop.Utils.IdCreater;
import com.onlineshop.Utils.NameContains;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
@Slf4j
@SpringBootTest
class OnlineShopApplicationTests {

    @Test
    void contextLoads() {
        IdCreater idCreater = new IdCreater();
        Long id = idCreater.createId(NameContains.USER_ID);
        log.info(id.toString());
    }

}
