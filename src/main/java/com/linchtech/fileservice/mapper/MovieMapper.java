package com.linchtech.fileservice.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.linchtech.fileservice.entity.vo.MovieVO;
import com.linchtech.fileservice.entity.po.Movie;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author 107
 * @date 2019-01-26 15:05
 **/
@Mapper
@Repository
public interface MovieMapper extends BaseMapper<Movie> {

    List<MovieVO> getMovieList(List<String> list);
}
