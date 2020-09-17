package com.listen;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.UUID;

@Component
public class SendEmail {

    @Autowired
    JavaMailSender javaMailSender;
    @Value("${spring.mail.username}")
    public String from;

    @Autowired
    RedisTemplate redisTemplate;

    @RabbitListener(queues = "email-project")
    public void sendMail(Map map){
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setFrom(from);
        Object email = map.get("email");
        simpleMailMessage.setTo(email.toString());
        simpleMailMessage.setSubject("注册的验证码");
        UUID uuid = UUID.randomUUID();
        String s = uuid.toString();
        String substring = s.substring(s.length() - 4, s.length());
//        int text = (int)Math.random()*10000;
//        simpleMailMessage.setText(""+text);
        simpleMailMessage.setText(substring);
        try{
            javaMailSender.send(simpleMailMessage);
            //redisTemplate.opsForValue().set(email.toString(),""+text);
            redisTemplate.opsForValue().set(email.toString(),substring);
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }
}
