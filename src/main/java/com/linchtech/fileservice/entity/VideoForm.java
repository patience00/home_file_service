package com.linchtech.fileservice.entity;

import lombok.Data;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotNull;

/**
 * @author 107
 * @date 2019-01-27 14:54
 **/
@Data
public class VideoForm {

    @NotNull(message = "ID不能为空")
    private Long id;

    @NotNull(message = "不能为空")
    @Range(min = 0, max = 1)
    private Integer downOrUp;

}
