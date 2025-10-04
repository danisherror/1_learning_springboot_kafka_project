package net.javaguides.springboot;

import com.launchdarkly.eventsource.ConnectStrategy;
import com.launchdarkly.eventsource.EventSource;
import com.launchdarkly.eventsource.background.BackgroundEventHandler;
import com.launchdarkly.eventsource.background.BackgroundEventSource;
import okhttp3.Headers;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.time.Duration;

@Service
public class WikimediaChangesProducer {

    private static final Logger logger = LoggerFactory.getLogger(WikimediaChangesProducer.class);

    private final KafkaTemplate<String, String> kafkaTemplate;

    public WikimediaChangesProducer(KafkaTemplate<String, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendMessage() throws InterruptedException {
        String topic = "wikimedia_recentchange";
        String url = "https://stream.wikimedia.org/v2/stream/recentchange";

        BackgroundEventHandler eventHandler = new WikimediaChangesHandler(kafkaTemplate, topic);

        // ✅ Create headers
        Headers headers = new Headers.Builder()
                .add("User-Agent", "SpringBootKafkaClient/1.0 (danish.mahajan@example.com)")
                .build();

        // ✅ Create ConnectStrategy (immutable)
        ConnectStrategy connectStrategy = ConnectStrategy.http(URI.create(url))
                .headers(headers);

        // ✅ EventSource builder
        EventSource.Builder eventSourceBuilder = new EventSource.Builder(connectStrategy)
                .reconnectTime(Duration.ofSeconds(3)); // reconnectTime set here, not on ConnectStrategy

        // ✅ Wrap in BackgroundEventSource
        BackgroundEventSource.Builder builder = new BackgroundEventSource.Builder(eventHandler, eventSourceBuilder);
        BackgroundEventSource eventSource = builder.build();

        // Start streaming
        eventSource.start();

        // Keep alive for 10 minutes
        Thread.sleep(10 * 60 * 1000);

        // Close gracefully
        eventSource.close();
    }
}
