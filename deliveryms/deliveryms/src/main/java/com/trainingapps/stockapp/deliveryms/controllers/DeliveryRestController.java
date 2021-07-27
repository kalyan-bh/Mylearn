package com.trainingapps.stockapp.deliveryms.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.trainingapps.stockapp.deliveryms.dto.AddDeliveryRequest;
import com.trainingapps.stockapp.deliveryms.dto.ChangeDeliveryStatus;
import com.trainingapps.stockapp.deliveryms.dto.DeliveryDetails;
import com.trainingapps.stockapp.deliveryms.service.IDeliveryService;

@RequestMapping("/delivery")
@RestController
public class DeliveryRestController {
	
	@Autowired
	private IDeliveryService service;
	
	/**
	 *  /delivery/add
	 */
	@ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/add")
	public DeliveryDetails addDelivery(@RequestBody AddDeliveryRequest request) {
		return service.add(request);
	}
	
	/**
	 *  /delivery/update/status
	 *  
	 */
	@PutMapping("/update/status")
	public DeliveryDetails updateStatus(@RequestBody ChangeDeliveryStatus request) {
		return service.changeDeliveryStatus(request);
	}
	
	/**
	 *  /delivery/get/details/byid/1
	 * 
	 */
	@GetMapping("/get/details/byid/{id}")
	public DeliveryDetails fetchDetailsById(@PathVariable("id") Long id) {
		return service.findByOrderId(id);
	}
	
	@GetMapping("/getAll/{status}")
	public List<DeliveryDetails> fetchAllByStatus(@PathVariable("status") String status){
		return service.findAllDetailsByStatus(status);
	}
}
