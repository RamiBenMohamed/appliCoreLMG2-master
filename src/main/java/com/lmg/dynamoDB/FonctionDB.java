package com.lmg.dynamoDB;



import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.*;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.amazonaws.services.dynamodbv2.document.Table;
import com.amazonaws.services.dynamodbv2.document.TableCollection;
import com.amazonaws.services.dynamodbv2.model.AttributeDefinition;
import com.amazonaws.services.dynamodbv2.model.CreateTableRequest;
import com.amazonaws.services.dynamodbv2.model.CreateTableResult;
import com.amazonaws.services.dynamodbv2.model.DeleteTableRequest;
import com.amazonaws.services.dynamodbv2.model.DeleteTableResult;
import com.amazonaws.services.dynamodbv2.model.DescribeTableResult;
import com.amazonaws.services.dynamodbv2.model.KeySchemaElement;
import com.amazonaws.services.dynamodbv2.model.KeyType;
import com.amazonaws.services.dynamodbv2.model.ListTablesResult;
import com.amazonaws.services.dynamodbv2.model.ProvisionedThroughput;
import com.amazonaws.services.dynamodbv2.model.TableDescription;
import com.amazonaws.services.dynamodbv2.model.UpdateTableRequest;
import com.amazonaws.services.dynamodbv2.model.UpdateTableResult;
import com.lmg.config.coreDYNAMOlmg;


@Service
@Component
public class FonctionDB {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(FonctionDB.class);
	private static final List<AttributeDefinition> attributeDefinitions = null;
	private static final ProvisionedThroughput provisionedThroughput = null;
	private static final List <KeySchemaElement> keySchemaElements=null;
	@Autowired
	coreDYNAMOlmg dynConf;
	

	public void createTable( CreateTableRequest request){
		Table table;
		try{
			    table = dynConf.getDynamoDb().createTable(request);
				table.getDescription();
				LOGGER.info("**********************table created**************************");
				
			
				
				 System.out.println("Waiting for newTable"
		                 + " to be created...this may take a while...");
		    


		  } catch (Exception e) {
		      System.err.println("CreateTable request failed for table" );
		      System.err.println(e.getMessage());
		  }	
		
		
		
		
		
	}
	@PostConstruct
	public void createTableTest(){
		LOGGER.info("****************************strat creating the table******************************");
		
		String tableName="NewTable";
		
		 try {

		        ArrayList<AttributeDefinition> attributeDefinitions = new ArrayList<AttributeDefinition>();
		        attributeDefinitions.add(new AttributeDefinition()
		                     .withAttributeName("Id")
		                     .withAttributeType("N"));

		        ArrayList<KeySchemaElement> keySchema = new ArrayList<KeySchemaElement>();
		        keySchema.add(new KeySchemaElement()
		                  .withAttributeName("Id")
		                  .withKeyType(KeyType.HASH)); //Partition key

		        CreateTableRequest request = new CreateTableRequest()
		                         .withTableName(tableName)
		                         .withKeySchema(keySchema)
		                         .withAttributeDefinitions(attributeDefinitions)
		                         .withProvisionedThroughput(new ProvisionedThroughput()
		                                    .withReadCapacityUnits(5L)
		                                    .withWriteCapacityUnits(6L));

		        System.out.println("Issuing CreateTable request for  "+tableName );
		        
		        
		        
		        
		        
		
		
		        Table table = dynConf.getDynamoDb().createTable(request);
		table.getDescription();
		LOGGER.info("**********************table created**************************");
		
		
		
		 System.out.println("Waiting for newTable"
                 + " to be created...this may take a while...");
    


  } catch (Exception e) {
      System.err.println("CreateTable request failed for"+tableName );
      System.err.println(e.getMessage());
  }
	}
	

	public void listMyTables() {

	    TableCollection<ListTablesResult> tables = dynConf.getDynamoDb().listTables();
	    Iterator<Table> iterator = tables.iterator();

	    System.out.println("Listing table names");

	    while (iterator.hasNext()) {
	        Table table = iterator.next();
	        System.out.println(table.getTableName());
	    }
	}
	

	
	@PostConstruct
	public void getTableInformation(String tableName) {

	    System.out.println("Describing " + tableName);

	    TableDescription tableDescription = dynConf.getDynamoDb().getTable(tableName).describe();
	    System.out.format("Name: %s:\n" + "Status: %s \n"
	              + "Provisioned Throughput (read capacity units/sec): %d \n"
	              + "Provisioned Throughput (write capacity units/sec): %d \n",
	              tableDescription.getTableName(),
	              tableDescription.getTableStatus(),
	              tableDescription.getProvisionedThroughput().getReadCapacityUnits(),
	              tableDescription.getProvisionedThroughput().getWriteCapacityUnits());
	}

	public  void updateTable(String tableName,ProvisionedThroughput provisionedThroughput) {

	    Table table = dynConf.getDynamoDb().getTable(tableName);
	    System.out.println("Modifying provisioned throughput for " + tableName);

	    try {
	        table.updateTable(provisionedThroughput);

	        table.waitForActive();
	    } catch (Exception e) {
	        System.err.println("UpdateTable request failed for " + tableName);
	        System.err.println(e.getMessage());
	    }
	}
	
	
	

	public void deleteExampleTable(String tableName) {

	    Table table = dynConf.getDynamoDb().getTable(tableName);
	    try {
	        System.out.println("Issuing DeleteTable request for " + tableName);
	        table.delete();

	        System.out.println("Waiting for " + tableName
	                   + " to be deleted...this may take a while...");

	        table.waitForDelete();
	    } catch (Exception e) {
	        System.err.println("DeleteTable request failed for " + tableName);
	        System.err.println(e.getMessage());
	    }
	}
	/*
	public void listTag(){
		ListTagsOfResourceRequest listTagsOfResourceRequest = new ListTagsOfResourceRequest()
                .withResourceArn("arn:aws:dynamodb:us-east-1:123456789012:table/Movies");
        return dynConf.getDynamoDb().listTagsOfResource(listTagsOfResourceRequest);
	}
*/

}