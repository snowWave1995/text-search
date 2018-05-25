package com.snowwave.textsearch.model;

import lombok.Data;

/**
 * Created by zhangfuqiang on 2018/5/25.
 */
@Data
public class TextDTO {
    private String id;
    private String type;
    private String title;
    private String author;
    private String update_time;
    private String TextContent;
}
