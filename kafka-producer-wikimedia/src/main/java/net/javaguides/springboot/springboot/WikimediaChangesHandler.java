package net.javaguides.springboot.springboot;

import com.launchdarkly.eventsource.MessageEvent;
import com.launchdarkly.eventsource.background.BackgroundEventHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;

public class WikimediaChangesHandler implements BackgroundEventHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(WikimediaChangesHandler.class);
    private final KafkaTemplate<String, String> kafkaTemplate;
    private final String topic;

    public WikimediaChangesHandler(KafkaTemplate<String, String> kafkaTemplate, String topic) {
        this.kafkaTemplate = kafkaTemplate;
        this.topic = topic;
    }

    @Override
    public void onOpen() {
        LOGGER.info("Opened connection to Wikimedia stream...");
    }

    @Override
    public void onClosed() {
        LOGGER.info("Closed connection to Wikimedia stream...");
    }

    @Override
    public void onMessage(String event, MessageEvent messageEvent) {
        LOGGER.info("Event Data -> {}", messageEvent.getData());
        kafkaTemplate.send(topic, messageEvent.getData());
    }

    @Override
    public void onComment(String comment) {
        // Optional: handle comments if needed
    }

    @Override
    public void onError(Throwable throwable) {
        LOGGER.error("Error in Wikimedia stream: {}", throwable.getMessage(), throwable);
    }
}
