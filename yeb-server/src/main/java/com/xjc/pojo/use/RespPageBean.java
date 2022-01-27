package com.xjc.pojo.use;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @Author : XJC
 * @Description :分页对象
 * @create : 2022/1/9 19:39
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RespPageBean {

    /**
     * 总条数
     */
    private Long total;

    /**
     * 数据
     */
    private List<?> body;

}
