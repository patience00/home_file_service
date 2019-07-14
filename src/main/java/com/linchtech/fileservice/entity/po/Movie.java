package com.linchtech.fileservice.entity.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * @author 107
 * @date 2018-12-30 12:39
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@TableName("file_list")
public class Movie implements Serializable {
    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    private String url;
    private String image;
    private String name;
    private Double size;
    private Integer up;
    private Integer down;
    @TableField("gmt_create")
    private Date gmtCreate;

    @TableField("gmt_modified")
    private Date gmtModified;
    @TableField("delete_flag")
    private Boolean deleteFlag;

    @TableField("md5")
    private String md5;

}
