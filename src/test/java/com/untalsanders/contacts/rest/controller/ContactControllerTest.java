package com.untalsanders.contacts.rest.controller;

import org.springframework.boot.actuate.autoconfigure.wavefront.WavefrontProperties;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK, classes = WavefrontProperties.Application.class)
class ContactControllerTest {

}
