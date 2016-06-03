package com.collector.gazelle.main;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.collector.gazelle.config.GzConfig;
import com.collector.gazelle.config.RemoteSource;
import com.collector.gazelle.config.RemoteSourceConfig;
import com.collector.gazelle.config.ServiceConfiguration;
import com.collector.gazelle.kafka.GzQueue;
import com.collector.gazelle.kafka.GzQueueKafkaImpl;
import com.collector.gazelle.remoteconnect.DispatcherThread;
import com.collector.gazelle.remoteconnect.RemoteConnection;
import com.collector.gazelle.resources.EventResource;
import com.fasterxml.jackson.dataformat.yaml.snakeyaml.Yaml;
import com.yammer.dropwizard.Service;
import com.yammer.dropwizard.config.Bootstrap;
import com.yammer.dropwizard.config.Environment;
import com.yammer.dropwizard.views.ViewBundle;


public class GzMain extends Service<ServiceConfiguration> {

	private GzConfig gzConfig = new GzConfig();
	private GzQueue queue = null;
	
	public static void main(String args[]) throws Exception{
		new GzMain().run(args);
	}
	
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
		Yaml yaml = new Yaml();
		System.out.println(configuration.getConfigPath());
    	InputStream in = null;
    	try{
    	in = new FileInputStream(configuration.getConfigPath());
    	}catch(FileNotFoundException e){
    		e.printStackTrace();
    	}
    	RemoteSourceConfig remoteSourceConfiguration = yaml.loadAs(in, RemoteSourceConfig.class);
		List<RemoteSource> remoteSourceList = remoteSourceConfiguration.getSourceList();
		startDispatcherThread(remoteSourceList);
		
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
	
public void startDispatcherThread(List<RemoteSource> remoteSourceList){
		
		if(remoteSourceList.size()>0){
	    	ExecutorService executor = Executors.newFixedThreadPool(5);
	    	for(int i=0;i<remoteSourceList.size();i++){
	    		RemoteConnection remoteConnection = new RemoteConnection(
	    				(RemoteSource)remoteSourceList.get(i));
			    Runnable dispatcher=new DispatcherThread(remoteConnection);
			    System.out.println("dispatcher");
			    executor.execute(dispatcher);
		    }
	    	executor.shutdown();
        }
	}
	
	
}
