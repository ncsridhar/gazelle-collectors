package com.collector.gazelle.config;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.yammer.dropwizard.config.Configuration;

public class RemoteSourceConfig extends Configuration{
	
	
	private List<RemoteSource> sourceList;

	 public void setSourceList(List<RemoteSource> sourceList) {
		this.sourceList = sourceList;
	}

	@JsonProperty("sourceList")
	public List<RemoteSource> getSourceList() {
		return sourceList;
	}
}
