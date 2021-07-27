package com.trainingapps.stockapp.deliveryms.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.trainingapps.stockapp.deliveryms.constant.DeliveryStatus;
import com.trainingapps.stockapp.deliveryms.dao.IDeliveryRepository;
import com.trainingapps.stockapp.deliveryms.dto.AddDeliveryRequest;
import com.trainingapps.stockapp.deliveryms.dto.ChangeDeliveryStatus;
import com.trainingapps.stockapp.deliveryms.dto.DeliveryDetails;
import com.trainingapps.stockapp.deliveryms.entities.Delivery;
import com.trainingapps.stockapp.deliveryms.exceptions.DeliveryNotFoundException;
import com.trainingapps.stockapp.deliveryms.util.DeliveryUtil;

/**
 * Service Implementation for Delivery Ms
 * 
 * @author saika
 */
@Transactional
@Service
public class DeliveryServiceImpl implements IDeliveryService {

	@Autowired
	private IDeliveryRepository deliveryRepo;

	@Autowired
	private DeliveryUtil deliveryUtil;

	/**
	 * Adding delivery details
	 */
	@Override
	public DeliveryDetails add(AddDeliveryRequest request) {
		Delivery delivery = newDelivery();//new Delivery();
		delivery.setOrderId(request.getOrderId());
		delivery.setDeliveryStatus(DeliveryStatus.NOT_DISPATCHED);
		delivery = deliveryRepo.save(delivery);
		return deliveryUtil.toDetails(delivery);
	}
	
	public Delivery newDelivery() {
		return new Delivery();
	}

	
	/**
	 * changing delivery status
	 */
	@Override
	public DeliveryDetails changeDeliveryStatus(ChangeDeliveryStatus request) {
		Delivery delivery = fetchDeliveryByOrderId(request.getOrderId());
		delivery.setDeliveryStatus(deliveryUtil.toDeliveryStatus(request.getDeliveryStatus()));
		delivery = deliveryRepo.save(delivery);
		return deliveryUtil.toDetails(delivery);
	}
	
	/**
	 * get delivery details by order id
	 */
	@Override
	public DeliveryDetails findByOrderId(Long orderId) {
		boolean exist=deliveryRepo.existsByOrderId(orderId);
		if(!exist) {
			throw new DeliveryNotFoundException("Delivery not found for order id "+orderId);
		}
		Delivery delivery=deliveryRepo.findDeliveryByOrderId(orderId);
		return deliveryUtil.toDetails(delivery);
	}
	
	/**
	 * find the list of delivery details by delivery status
	 */
	@Override
	public List<DeliveryDetails> findAllDetailsByStatus(@NotNull String status) {
		List<Delivery> details=deliveryRepo.findAllByDeliveryStatus(deliveryUtil.toDeliveryStatus(status));
		return deliveryUtil.toDetailsList(details);
	}
	
	public Delivery fetchDeliveryByOrderId(Long orderId) {
		boolean exist=deliveryRepo.existsByOrderId(orderId);
		if(!exist) {
			throw new DeliveryNotFoundException("Delivery not found for order id "+orderId);
		}
		Delivery delivery=deliveryRepo.findDeliveryByOrderId(orderId);
		return delivery;
	}
	
	public Delivery findById(Long id) {
		Optional<Delivery> optional = deliveryRepo.findById(id);
		if (!optional.isPresent()) {
			throw new DeliveryNotFoundException("Delivery Details not found for id " + id);
		}
		return optional.get();
	}

}
