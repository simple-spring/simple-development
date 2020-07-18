package com.spring.simple.development.demo.cassandra.repository;

import com.spring.simple.development.demo.cassandra.table.DemoCassandraDo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * demo
 */
@Service
public class CassandraDemo {

    @Autowired
    private DemoRepository demoRepository;

    public DemoCassandraDo getDemoCassandraDo() {
        return demoRepository.findById("1").get();
    }

}
