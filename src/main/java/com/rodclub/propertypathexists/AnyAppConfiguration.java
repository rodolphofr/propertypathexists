package com.rodclub.propertypathexists;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;

import com.rodclub.propertypathexists.annotation.ConditionalOnPropertyPathExist;

@Configuration
@ConditionalOnPropertyPathExist("spring.cloud.stream.rabbit")
public class AnyAppConfiguration {
	private static final Logger LOG = LoggerFactory.getLogger(AnyAppConfiguration.class);
	
	public AnyAppConfiguration() {
		LOG.debug("AnyAppConfiguration instantiated");
	}
}
