package com.collector.gazelle.resources;

import javax.validation.Valid;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.collector.gazelle.config.GzConfig;
import com.collector.gazelle.dto.GzEvent;
import com.collector.gazelle.skels.GzParser;
import com.collector.gazelle.skels.GzTransformer;


@Path(value = "/event")
@Produces(MediaType.APPLICATION_JSON)
public class EventResource extends GzResource {

	private static final Logger LOGGER = LoggerFactory
			.getLogger(EventResource.class);
	GzConfig gzConfig = null;
	
	public EventResource(){
	}
	
	public EventResource(GzConfig gzConfig) {
		this.gzConfig = gzConfig;
	}

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	public String createUser(@Valid GzEvent event) throws Exception {
		LOGGER.info("Received a event: {}", event);
		
		String eventType = event.getRoutingInfo().getEventType();
		GzParser parser = gzConfig.getParser(eventType);
		GzTransformer xformer = gzConfig.getTransformer(eventType);
		
		persist(xformer.transform( parser.parse(event)));
		
		return "";

	}
	
	private void persist(GzEvent event){
		
		//TODO: implement writing to Kafka part here
		System.out.println("Event details: " + event.toString());
	}
}
