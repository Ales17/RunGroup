package com.rungroup.web.storage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties(StorageProperties.class)
public class AdditionalConfiguration {

    @Autowired
    private StorageProperties storageProperties;

}