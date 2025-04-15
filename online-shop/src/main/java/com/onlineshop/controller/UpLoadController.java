package com.onlineshop.controller;

import com.onlineshop.DTO.Result;
import com.onlineshop.Utils.AliOSSUtils;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
@Slf4j
@RestController
public class UpLoadController {
    @Resource
    private AliOSSUtils aliOSSUtils;
    @RequestMapping("/uploadImage")
    public Result upload(MultipartFile image) throws IOException {
        log.info("文件上传");
        String url = aliOSSUtils.upload(image);
        return Result.success(url);
    }
}
