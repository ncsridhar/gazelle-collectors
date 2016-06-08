package com.collector.gazelle.remoteconnect;

import java.util.List;
import java.util.TimerTask;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.collector.gazelle.config.RemoteSource;

public class TimerSchedulerTask extends TimerTask{

	private List remoteSourceList;
	
	public TimerSchedulerTask(){
		
	}
	
	public TimerSchedulerTask(List remoteSourceList){
		this.remoteSourceList = remoteSourceList;
	}
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
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


