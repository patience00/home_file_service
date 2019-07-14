package com.linchtech.fileservice.service;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.linchtech.fileservice.entity.vo.MovieVO;
import com.linchtech.fileservice.entity.VideoForm;
import com.linchtech.fileservice.entity.po.Movie;
import com.linchtech.fileservice.mapper.MovieMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * @author 107
 * @date 2019-01-26 18:20
 **/
@Service
@Slf4j
public class MoviveService {

    @Autowired
    private MovieMapper movieMapper;
    @Autowired
    private FileService fileService;

    public List<MovieVO> getList() {
        DecimalFormat df = new DecimalFormat("#.00");
        QueryWrapper<Movie> wrapper = new QueryWrapper<>();
        wrapper.eq("delete_flag", false);
        wrapper.orderByDesc("up");
        List<Movie> movies = movieMapper.selectList(wrapper);
        List<MovieVO> list = new ArrayList<>();
        for (Movie movie : movies) {
            MovieVO movieVO = new MovieVO();
            BeanUtils.copyProperties(movie, movieVO);
            double down = movie.getDown() == null ? 0 : movie.getDown();
            double up = movie.getUp() == null ? 0 : movie.getUp();
            double rate;
            if (up + down < 1) {
                rate = 0;
            } else {
                rate = up / (up + down);
            }
            double percent = Double.parseDouble(df.format(rate * 100));
            movieVO.setRate(percent + "");
            list.add(movieVO);
        }
        return list;
    }

    public void rate(VideoForm form) {
        Movie movie = movieMapper.selectById(form.getId());
        if (form.getDownOrUp() == 0) {
            int down = movie.getDown() == null ? 0 : movie.getDown();
            movie.setDown(down + 1);
        }
        if (form.getDownOrUp() == 1) {
            int up = movie.getUp() == null ? 0 : movie.getUp();
            movie.setUp(up + 1);
        }
        movieMapper.updateById(movie);
    }

    public void delete(Long id) throws Exception {
        Movie movie = movieMapper.selectById(id);
        String url = movie.getUrl();
        String path = url.substring(url.lastIndexOf("/"));
        log.info("path:{}", path);
        fileService.delete(path);
        movie.setDeleteFlag(true);
        movieMapper.updateById(movie);
    }

}
