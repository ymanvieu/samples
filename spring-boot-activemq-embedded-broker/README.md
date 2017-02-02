#Spring-boot ActiveMQ embedded broker

Example of a Spring-boot project with JMS and an embedded ActiveMQ broker with TCP transport and JSON message serialization with Jackson.

####Producer (embed the broker)
Send current time every 5s (Spring @Scheduled) and send a message in the 'current-time' topic via the auto-configured JmsTemplate.
 
####Receiver
Listen to the same topic (Spring @JmsListener) and prints messages upon reception.