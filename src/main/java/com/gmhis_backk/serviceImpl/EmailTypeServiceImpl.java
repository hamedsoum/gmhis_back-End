package com.gmhis_backk.serviceImpl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.gmhis_backk.service.EmailTypeService;

@Service
@Transactional(rollbackFor = Exception.class,propagation = Propagation.REQUIRED)
public class EmailTypeServiceImpl implements EmailTypeService {

}
