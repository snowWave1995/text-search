package com.snowwave.textsearch.model;


import lombok.Data;

import java.util.List;

/**
 * Created by zhangfuqiang on 2018/5/25.
 */
@Data
public class SearchDTO {
    private int total;
    private List<TextDTO> textDTOList;
}
