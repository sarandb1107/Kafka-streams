import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.example.BankTransactionsProducer;
import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;


public class BankTransactionsProducerTests {

    @Test
    public void newRandomTransactionsTest() {
        ProducerRecord<String, String> record = BankTransactionsProducer.newRandomTransaction("alien");
        String key = record.key();
        String value = record.value();

        Assert.assertEquals("alien", key);

        ObjectMapper mapper = new ObjectMapper();
        try {
            JsonNode node = mapper.readTree(value);
            Assert.assertEquals("alien", node.get("name").asText());
            Assert.assertTrue("Amount should be less than 100", node.get("amount").asInt() < 100);
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println(value);
    }
}
