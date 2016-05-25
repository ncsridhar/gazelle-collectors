# gazelle-collectors

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



