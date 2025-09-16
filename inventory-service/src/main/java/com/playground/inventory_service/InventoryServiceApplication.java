package com.playground.inventory_service;

import com.playground.inventory_service.model.Inventory;
import com.playground.inventory_service.repository.InventoryRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;

import java.util.List;

@SpringBootApplication
@EnableDiscoveryClient
public class InventoryServiceApplication  {

	public static void main(String[] args) {
		SpringApplication.run(InventoryServiceApplication.class, args);
	}

	@Bean
	public CommandLineRunner loadData(InventoryRepository inventoryRepository) throws Exception {
      return args -> {
		  Inventory inventory1 = new Inventory("iphone-12",100);
		  Inventory inventory2 = new Inventory("iphone-13",100);
          Inventory inventory3 = new Inventory("iphone-14",100);
          Inventory inventory4 = new Inventory("iphone-15",100);

          inventoryRepository.saveAll(List.of(inventory1, inventory2,inventory3,inventory4));
	  };
	}
}
