readme
======

## Overview

This is a project utilize rabbitmq to asynchronously update the database with the data collected from a websocket stream.

There are 2 repositories involved. One is the producer repo **async-db-update-producer** and the other is the consumer repo **async-db-update-consumer**.

## Data source
[US stock market data from Alpaca websocket stream](https://alpaca.markets/docs/api-documentation/api-v2/streaming/)

## Getting start

1. Preparation

	- Prepare a Alpaca paper account
	- Rename `key.sample.config` file to `key.config` and Specify the API key and secret key in the configuration file
	

2. Run rabbitmq server locally

```
docker run -it --rm --name rabbitmq -p 5672:5672 -p 15672:15672 rabbitmq:3.9-management
```

3. Run DB server

4. Run consumer application

5. Run producer application

