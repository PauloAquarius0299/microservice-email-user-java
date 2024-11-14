package com.paulotech.email_service.consumers;

import com.paulotech.email_service.dtos.EmailRecordDTO;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Component
public class EmailConsumer {

    @RabbitListener(queues = "${broker.queue.email.name}" )
    public void listenEmailQueue(@Payload EmailRecordDTO emailRecordDTO){
        System.out.println(emailRecordDTO.emailTo());
    }
}
