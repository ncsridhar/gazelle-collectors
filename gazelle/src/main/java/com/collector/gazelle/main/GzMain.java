package com.collector.gazelle.main;

import com.collector.gazelle.config.GzConfig;
import com.collector.gazelle.config.ServiceConfiguration;
import com.collector.gazelle.kafka.GzQueue;
import com.collector.gazelle.kafka.GzQueueKafkaImpl;
import com.collector.gazelle.resources.EventResource;
import com.yammer.dropwizard.Service;
import com.yammer.dropwizard.config.Bootstrap;
import com.yammer.dropwizard.config.Environment;
import com.yammer.dropwizard.views.ViewBundle;


public class GzMain extends Service<ServiceConfiguration> {

	private GzConfig gzConfig = new GzConfig();
	private GzQueue queue = null;
	
	public void start(String[] args) throws Exception{
		new GzMain().run(args);
	}
	
	@Override
	public void initialize(Bootstrap<ServiceConfiguration> bootstrap) {
		// TODO Auto-generated method stub
		bootstrap.setName("Gazelle-Collector-REST-Services");
		bootstrap.addBundle(new ViewBundle());
	}

	@Override
	public void run(ServiceConfiguration configuration, Environment env)
			throws Exception {
        System.out.println("inside run");
		queue = new GzQueueKafkaImpl();
		EventResource eventRes = new EventResource(gzConfig, queue);
		env.addResource(eventRes);
		
	}

	public GzConfig getGzConfig() {
		return gzConfig;
	}

	public void setGzConfig(GzConfig gzConfig) {
		this.gzConfig = gzConfig;
	}
	
	
}
