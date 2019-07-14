package com.linchtech.fileservice.entity.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author 107
 * @date 2019-01-27 19:15
 **/
@Data
public class MovieVO  implements Serializable {

    private Long id;
    private String url;
    private String image;
    private String name;
    private Double size;
    private String rate;
    private Date gmtCreate;
    private Date gmtModified;
    private Boolean deleteFlag;

}
