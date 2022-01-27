package com.xjc.pojo;

import cn.afterturn.easypoi.excel.annotation.Excel;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;
import java.io.Serializable;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalTimeSerializer;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import lombok.experimental.Accessors;

/**
 * <p>
 * 
 * </p>
 *
 * @author xjc
 * @since 2021-12-12
 */
@Data
@EqualsAndHashCode(callSuper = false,of = "name")
@RequiredArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
@TableName("t_joblevel")
@ApiModel(value="Joblevel对象", description="")
public class Joblevel implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "id")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @NonNull
    @Excel(name = "职称名称",width = 30)
    @ApiModelProperty(value = "职称名称")
    private String name;

    @ApiModelProperty(value = "职称等级")
    private String titleLevel;

    @ApiModelProperty(value = "创建时间")
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    private LocalDateTime createDate;

    @ApiModelProperty(value = "是否启用")
    private Boolean enabled;

}
