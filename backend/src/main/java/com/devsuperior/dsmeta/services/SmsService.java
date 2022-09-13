package com.devsuperior.dsmeta.services;

import com.devsuperior.dsmeta.entities.Sale;
import com.devsuperior.dsmeta.repositories.SaleRepository;
import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.text.DecimalFormat;

@Service
public class SmsService {

    @Value("${twilio.sid}")
    private String twilioSid;

    @Value("${twilio.key}")
    private String twilioKey;

    @Value("${twilio.phone.to}")
    private String twilioPhoneTo;
    @Value("${twilio.ms.service.id}")
    private String twilioMsId;
    @Autowired
    private SaleRepository saleRepository;

    public void sendSms(Long saleId) {
        // Find your Account Sid and Token at twilio.com/console
        Sale sale = saleRepository.findById(saleId).get();
        String date = sale.getDate().getMonthValue() + "/" + sale.getDate().getYear();

        String msg = sale.getSellerName() + " é destaque em " + date
                + " com total de R$ " + sale.getAmount();

        Twilio.init(twilioSid, twilioKey);

        PhoneNumber to = new PhoneNumber(twilioPhoneTo);

        Message message = Message.creator(
                        to,
                        twilioMsId,
                        msg)
                .create();
        System.out.println(message.getSid());
    }
}

