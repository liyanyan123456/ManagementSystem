package com.wms.service.impl;

import com.wms.entity.Transaction;
import com.wms.mapper.TransactionMapper;
import com.wms.service.TransactionService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author liyanyan
 * @since 2024-12-17
 */
@Service
public class TransactionServiceImpl extends ServiceImpl<TransactionMapper, Transaction> implements TransactionService {

}
