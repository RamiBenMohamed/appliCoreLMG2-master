package com.lmg.controller;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


import com.lmg.config.coreSNSlmg;


@Component
public class MainController {
	  private static final Logger LOGGER = LoggerFactory.getLogger(MainController.class);
         private static final String message = "Hello";
         int j=0;
	@Autowired
	coreSNSlmg ms;
	//@PostConstruct
        public void writeMessageTopic() {
		
        	for (int i=0;i<3;i++){
        		ms.publier(message+j);
        		j++;
        		LOGGER.info("je suis entrain d'envoyer vers le topic" +j+ "dfdf");
        		System.out.println("---------------------------"+j);
        		i--;
        	}
        		
        	
        }
}