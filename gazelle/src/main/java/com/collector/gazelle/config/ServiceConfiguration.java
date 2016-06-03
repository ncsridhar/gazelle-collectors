package com.collector.gazelle.config;


import com.fasterxml.jackson.annotation.JsonProperty;
import com.yammer.dropwizard.config.Configuration;


public class ServiceConfiguration extends Configuration {

    private AppConfig appconfig;
    String configPath;
    
    @JsonProperty("appconfig")
    public AppConfig getAppconfig(){
        return appconfig;
    }
    
    @JsonProperty("configPath")
    public String getConfigPath() {
		return configPath;
	}
}
