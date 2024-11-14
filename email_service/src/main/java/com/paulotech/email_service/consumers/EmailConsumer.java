package com.paulotech.email_service.consumers;

import com.paulotech.email_service.dtos.EmailRecordDTO;
import com.paulotech.email_service.models.EmailModel;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.BeanUtils;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Component
public class EmailConsumer {

    @RabbitListener(queues = "${broker.queue.email.name}" )
    public void listenEmailQueue(@Payload EmailRecordDTO emailRecordDTO){
        var emailModel = new EmailModel();
        BeanUtils.copyProperties(emailRecordDTO , emailModel);
    }
}
