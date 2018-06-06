# README of the billing persistor

## Description

* This module is used to insert and control the insertion of database records to the sb_api_response_summary.
* The module contains of two micro services. processor maintains a queue of records to be inserted. scheduler initiates batch inserts
* Two way model is followed to control batch size and insert frequency without interrupting current insertions
* Mediator should POST data to /processor/produce endpoint
* Configs can be passed either by having application.properties on the same location as the jar or providing the path as -Dconfig.location=/path/to/application.properties

P.S Refer the doc [1] for spring cron generating.

[1] http://docs.spring.io/spring/docs/current/javadoc-api/org/springframework/scheduling/support/CronSequenceGenerator.html