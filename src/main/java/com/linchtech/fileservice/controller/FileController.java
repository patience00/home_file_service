package com.linchtech.fileservice.controller;

import com.linchtech.fileservice.service.FileService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * @author: 107
 * @date: 2019-07-14 16:17
 * @description:
 **/
@Controller
@Slf4j
public class FileController {

    @Autowired
    private FileService fileService;

    @GetMapping("/{url}")
    public ResponseEntity<byte[]> downloadFile(@PathVariable("url") String url) throws IOException {
        return fileService.download(url);
    }

    @PostMapping(value = "/upload", consumes = "multipart/form-data")
    @ResponseBody
    public ResultVO uploadFile(@RequestParam MultipartFile file) throws Exception {
        return ResultVO.ok(fileService.upload(file));
    }
}
