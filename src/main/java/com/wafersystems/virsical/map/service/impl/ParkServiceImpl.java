package com.wafersystems.virsical.map.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wafersystems.virsical.map.entity.Park;
import com.wafersystems.virsical.map.mapper.ParkMapper;
import com.wafersystems.virsical.map.service.IParkService;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 园区 服务实现类
 * </p>
 *
 * @author tandk
 * @since 2019-05-07
 */
@Service
public class ParkServiceImpl extends ServiceImpl<ParkMapper, Park> implements IParkService {

}
