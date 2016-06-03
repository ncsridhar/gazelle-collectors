package com.collector.gazelle.remoteconnect;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import com.collector.gazelle.dto.AuditInfo;
import com.collector.gazelle.dto.GzEvent;
import com.collector.gazelle.dto.RoutingInfo;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.SftpException;

public class WorkerThread implements Runnable{
    private String fileName;
    private RemoteConnection connection = null;
    //private ChannelSftp sftp = null;
    
    public WorkerThread(String fileName,RemoteConnection connection){
       this.fileName = fileName;
       this.connection = connection;
    }
    
    public void run() {
          // TODO Auto-generated method stub
          System.out.println("reading fileName"+fileName);
          try {
          	    readFileContent(fileName);
          	  //move files to a different location once the files are read
          } 
          catch (IOException e) {
          // TODO Auto-generated catch block
          e.printStackTrace();
          }
          catch(SftpException sft){
        	  sft.printStackTrace();
          }
     }

    public void readFileContent(String file) throws IOException, SftpException{
    	ChannelSftp sftp = connection.remoteConnect();
    	InputStream stream = sftp.get(file);
        BufferedReader br = new BufferedReader(
                                new InputStreamReader(stream));
        String contentLine = br.readLine();
        while(contentLine!=null){
            //System.out.println(contentLine);
            contentLine = br.readLine();
            //each line passed to parse
         }
        
     }
}

