package com.xjc.service.imp;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xjc.mapper.PoliticsStatusMapper;
import com.xjc.pojo.PoliticsStatus;
import com.xjc.service.IPoliticsStatusService;
import org.springframework.stereotype.Service;

/**
 * @Author : XJC
 * @Description :政治面貌
 * @create : 2022/1/10 10:57
 */
@Service
public class PoliticsStatusService extends ServiceImpl<PoliticsStatusMapper, PoliticsStatus>
        implements IPoliticsStatusService {
}
