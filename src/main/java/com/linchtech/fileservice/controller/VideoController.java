package com.linchtech.fileservice.controller;

import com.linchtech.fileservice.entity.vo.MovieVO;
import com.linchtech.fileservice.entity.VideoForm;
import com.linchtech.fileservice.service.MoviveService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.util.List;

/**
 * @author 107
 * @date 2018-12-30 10:54
 **/
@RestController
@RequestMapping("/video")
@Log4j2
public class VideoController {

    @Autowired
    private MoviveService moviveService;

    @GetMapping("/list")
    public List<MovieVO> getVideo() {
        return moviveService.getList();
    }

    @PostMapping("/rate")
    public void rate(@RequestBody VideoForm form) {
        moviveService.rate(form);
    }

    @GetMapping("/delete/{id}")
    public String delete(@RequestParam("id") Long id) throws Exception {
        moviveService.delete(id);
        log.info("删除成功");
        return "ok";
    }

    @GetMapping("/shutdown")
    public String shut() throws IOException, InterruptedException {
        String[] cmd = {"/bin/sh", "-c", "shutdown now"};
        Process process = Runtime.getRuntime().exec(cmd);
        LineNumberReader br = new LineNumberReader(new InputStreamReader(
                process.getInputStream()));
        StringBuffer sb = new StringBuffer();
        String line;
        while ((line = br.readLine()) != null) {
            System.out.println(line);
            sb.append(line).append("\n");
        }
        System.out.println(sb.toString());
        int i = process.waitFor();
        if (i != 0) {
            log.error("关机命令执行失败");
        } else {
            log.info("关机成功");
        }
        return "ok";
    }

    @GetMapping("/cancle")
    public String cancle() throws IOException {
        System.out.println("开始关机");
        Runtime.getRuntime().exec("shutdown now");
        return "ok";
    }

}
