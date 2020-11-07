package com.alican.component;

import com.alican.service.EmailService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;

/**
 * @author ahatun
 */
@Component
public class ServerControlJob {

    @Value("${server.url}")
    private String serverURL;

    private final RestTemplate restTemplate;
    private final EmailService emailService;

    public ServerControlJob(RestTemplate restTemplate, EmailService emailService) {
        this.restTemplate = restTemplate;
        this.emailService = emailService;
    }

    @Scheduled(fixedDelay = 300000)
    public void checkApplications() {
        try {
            restTemplate.exchange(serverURL, HttpMethod.GET, new HttpEntity<>(new HttpHeaders()), String.class);
        } catch (HttpClientErrorException e) {
            if (e.getStatusCode() != HttpStatus.UNAUTHORIZED) {
                emailService.sendSimpleMail();
            }
        } catch (ResourceAccessException e) {
            emailService.sendSimpleMail();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

