package com.websocket.demo.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author: jason
 * @Date: 2020-12-23
 */
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class MsgDto {
    private Integer userId;
    private Integer toUser;
    private String content;
}
