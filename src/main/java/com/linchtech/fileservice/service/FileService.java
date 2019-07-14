package com.linchtech.fileservice.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.linchtech.fileservice.entity.po.Movie;
import com.linchtech.fileservice.exceptions.ParameterException;
import com.linchtech.fileservice.mapper.MovieMapper;
import com.linchtech.fileservice.util.AESUtil;
import com.linchtech.fileservice.util.MD5Util;
import lombok.Cleanup;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.DecimalFormat;
import java.util.Date;
import java.util.UUID;

/**
 * @author: 107
 * @date: 2019-07-14 13:08
 * @description:
 **/
@Service
@Slf4j
public class FileService {

    private static final String PICTURE = "/root/picture";
    private static final String KEY = "LinchTechPicture";

    private static String host = "192.168.1.15:9000/";

    @Autowired
    private MovieMapper movieMapper;

    /**
     * 文件上传,按项目目录/日期/文件名分组
     *
     * @return url
     */
    public String upload(MultipartFile file) throws Exception {
        long start = System.currentTimeMillis();
        DecimalFormat df = new DecimalFormat("#.00");

        // 校验重复
        String md5 = MD5Util.md5File(file.getInputStream());
        QueryWrapper<Movie> wrapper = new QueryWrapper<>();
        wrapper.eq("md5", md5);
        Movie movie = movieMapper.selectOne(wrapper);
        if (movie != null) {
            throw ParameterException.build("文件已存在");
        }
        try {
            String uuid = UUID.randomUUID().toString().replace("-", "");
            String fileName = file.getOriginalFilename();
            String suffix = fileName.substring(fileName.indexOf("."));
            File destDir = new File(PICTURE);
            if (!destDir.exists()) {
                destDir.mkdirs();
            }
            String newFileName = destDir + File.separator + uuid;
            // base64编码之后有斜线,影响前端访问,替换掉
            String encrypt = AESUtil.Encrypt(newFileName, KEY).replace("/", "-");
            File newFile = new File(newFileName + suffix);
            @Cleanup OutputStream out = new FileOutputStream(newFile);
            out.write(file.getBytes());
            String url = host + encrypt + suffix;
            double size = file.getSize() / 1024.0 / 1024.0;
            double movieSize = Double.parseDouble(df.format(size));
            Movie build = Movie.builder()
                    .deleteFlag(false)
                    .down(0)
                    .up(0)
                    .gmtCreate(new Date())
                    .image(null)
                    .md5(md5)
                    .size(movieSize)
                    .name(file.getOriginalFilename())
                    .url(url)
                    .build();
            movieMapper.insert(build);
            log.info("上传用时:{}毫秒", System.currentTimeMillis() - start);
            return url;
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return null;
        }
    }

    /**
     * 下载文件
     *
     * @param url 前端直接访问的url
     * @return
     * @throws IOException
     */
    public ResponseEntity<byte[]> download(String url) throws IOException {
        String path;
        try {
            path = AESUtil.Decrypt(url.replace("-", "/").substring(0, url.lastIndexOf(".")), KEY);
        } catch (Exception e) {
            log.warn("url:{}文件解析失败", url);
            throw ParameterException.build("文件获取失败");
        }
        File destFile = new File(path + url.substring(url.indexOf(".")));
        if (!destFile.exists()) {
            log.warn("文件不存在,url:{}", url);
            throw ParameterException.build("文件不存在");
        }
        HttpHeaders headers = new HttpHeaders();
        MediaType mediaType = MediaType.valueOf("video/mp4");
        // if (MediaType.APPLICATION_OCTET_STREAM.equals(mediaType)) {
        //     headers.setContentDispositionFormData("attachment", new String(destFile.getName().getBytes("utf-8"),
        //             "ISO8859-1"));
        // }
        headers.setContentType(mediaType);
        return new ResponseEntity<>(FileCopyUtils.copyToByteArray(destFile), headers, HttpStatus.OK);
    }

    /**
     * 删除文件
     *
     * @param url
     * @return
     */
    public boolean delete(String url) {
        try {
            String path = AESUtil.Decrypt(url.replace("-", "/").substring(0, url.lastIndexOf(".")), KEY);
            File destFile = new File(path + url.substring(url.indexOf(".")));
            return destFile.delete();
        } catch (Exception e) {
            log.warn("url:{}文件解析失败", url);
            throw ParameterException.build("文件获取失败");
        }
    }
}
