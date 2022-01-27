package com.xjc.service.imp;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xjc.mapper.PositionMapper;
import com.xjc.pojo.use.Position;
import com.xjc.service.IPositionService;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author xjc
 * @since 2022-01-04
 */
@Service
public class PositionServiceImpl extends ServiceImpl<PositionMapper, Position> implements IPositionService {

}
