package com.harry.service.consumer;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.util.Optional;

/**
 * @author harry
 * @create 2019-03-11 16:50
 **/
@Component
public class OrdersConsumer {

    private final static Logger logger = LoggerFactory.getLogger(OrdersConsumer.class);

    @KafkaListener(topics = {"orders"})
    public void ordersConsumer(ConsumerRecord<?, ?> record){
        Optional<?> message = Optional.ofNullable(record.value());
        logger.warn(">>>>>kafka"+message);
        if(message.isPresent()){
            Object o = message.get();
            logger.info(">>>>>consumer:"+message);
        }

    }

}
