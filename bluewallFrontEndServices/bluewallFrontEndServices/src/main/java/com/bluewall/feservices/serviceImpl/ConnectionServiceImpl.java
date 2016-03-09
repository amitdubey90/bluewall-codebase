package com.bluewall.feservices.serviceImpl;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Service;

import com.bluewall.feservices.service.ConnectionService;

@ComponentScan({ "com.bluewall.feservices.daoImpl" })
@Service
public class ConnectionServiceImpl implements ConnectionService {

}
