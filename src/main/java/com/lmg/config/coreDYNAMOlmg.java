package com.lmg.config;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Component;

import com.amazonaws.ClientConfiguration;
import com.amazonaws.Protocol;
import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.regions.Region;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClient;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;

import com.lmg.coreConfiguration;
@Configuration
@DependsOn("coreConfiguration")
@Component
public class coreDYNAMOlmg {
	
	 private static final Logger LOGGER = LoggerFactory.getLogger(coreJMSlmg.class);
	    
	    @Autowired
	    coreConfiguration LaConfig;
	    
	    AmazonDynamoDBClient aMDclient;
		DynamoDB dynamoDB;
	    

    	/*
    	 * On recupere un credential temporaire pour l'accès à l'environnement SAND-BOX
    	 * 
    	 */
	    
	    @PostConstruct
	    public void init() {
	    	
       
        try {
        	 LOGGER.info("Création du client DynamoDB OK ...........................................");
             AWSCredentialsProvider AWSP = LaConfig.getCredentialProvider();
         	ClientConfiguration cC = LaConfig.getClientConfiguration();
             cC.setProtocol(Protocol.HTTPS);
		 aMDclient = new AmazonDynamoDBClient(AWSP,cC);
	
		 aMDclient.setRegion(Region.getRegion(Regions.EU_CENTRAL_1));
		
		 dynamoDB  = new DynamoDB(aMDclient);
		 LOGGER.info("initialisation du dynamo DB .....OK ..............");
		
        }
        catch ( Exception e)
        {
            LOGGER.info("Echec de l'initialisation du Dynamo Db..............");
            LOGGER.info("Service Name : " + aMDclient.getServiceName());
            LOGGER.info("Excpetion : " + e.getMessage());
        }
        
    	
        
      
	    }
	    public DynamoDB getDynamoDb(){
	    	return dynamoDB;
	    }
        
        
        //
        // On modifie la configuration du client pour repasser en HTTP
        //
    

}
