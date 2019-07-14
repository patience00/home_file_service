package com.linchtech.fileservice.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author 107
 * @date 2019-02-28 20:57
 **/
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MovieForm {

    private List<String> ids;
}
