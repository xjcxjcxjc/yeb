package com.xjc.exception;


import com.xjc.pojo.use.RespBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;

/**
 * @Author : XJC
 * @Time : 2022/1/5 18:06
 * @Description :
 */
@RestControllerAdvice
public class GlobalException {

    Logger LOGGER= LoggerFactory.getLogger(GlobalException.class);

    @ExceptionHandler(SQLException.class)
    public RespBean mysqlException(SQLException e){
        LOGGER.info(e.getMessage());
        if (e instanceof SQLIntegrityConstraintViolationException){
            return RespBean.error("数据库有相关数据，无法删除");
        }
        return RespBean.error("数据库异常");
    }

}
