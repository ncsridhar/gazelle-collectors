# Gazelle - project manifesto

Manifesto: Gazelle is a set of libraries and an opinionated framework for conducting a data pipeline. The data pipeline has primarily the 5 objectives. 
- Ingest
- Transform
- Persist
- Process
- Serve 
and all of this happens at web scale. 

The project has the following features:
- Highly configurable (data sources, sinks, storage options)
- Works out of the box (with defaults)
- Easily Extensible
- Provides an elegant and easy to use serving layer
- Provides auditing of data
- Provides statistics of the data
- Provides central monitoring capabilities, by each module
- A reference implementation to clone and adopt

----------------------

## Gazelle collectors:

Gazelle collectors are the "Ingest" part of the story above. This project is an extensible self-contained program that can do the following things:

- stands up a dropwizard web server
- exposes a web service end point called "event" which can accept a generic event
- routes the received event to Kafka queue
- The collector allows for custom parsers and transformers, per event type

a reference usage is provided in tests folder, called TestMain.java


======================
Benchmarking:
======================

I used "wrk" to benchmark this on my macbook air with 8gb ram and 2 ghz i7 processor. Kafka and zookeeper running locally. And several other processes running on my laptop simultaneously. <br>
<br>
Result: I can process 50TB of data per day per node with (<100M events per day). Since this solution scales out seamlessly with additional nodes, its quite a practical answer to Big data real time event processing.<br>
<br>
wrk -t12 -c400 -d3s -s post.lua  http://127.0.0.1:8080/event<br>
<br>
post.lua script:<br>
------------------<br>
wrk.method = "POST"<br>
wrk.body   = [[{"routingInfo":{"topicName":"x","eventType":"y"},"jsonPayload":"{name:something}","auditInfo":{"sentBy":"sridhar","createdAt":100}}]]<br>
wrk.headers["Content-Type"] = "application/json"<br>


The results are quite impressive:<br>
Running 3s test @ http://127.0.0.1:8080/event<br>
  12 threads and 400 connections<br>
  Thread Stats   Avg      Stdev     Max   +/- Stdev<br>
    Latency   112.78ms  173.90ms   1.05s    85.87%<br>
    Req/Sec   118.03    114.76   584.00     85.67%<br>
  3598 requests in 3.07s, 375.96KB read<br>
  Socket errors: connect 155, read 294, write 0, timeout 0<br>

