package com.trainingapps.stockapp.deliveryms;

import static org.mockito.Mockito.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.function.Executable;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.junit.jupiter.api.Assertions.*;

import com.trainingapps.stockapp.deliveryms.constant.DeliveryStatus;
import com.trainingapps.stockapp.deliveryms.dao.IDeliveryRepository;
import com.trainingapps.stockapp.deliveryms.dto.AddDeliveryRequest;
import com.trainingapps.stockapp.deliveryms.dto.DeliveryDetails;
import com.trainingapps.stockapp.deliveryms.entities.Delivery;
import com.trainingapps.stockapp.deliveryms.exceptions.DeliveryNotFoundException;
import com.trainingapps.stockapp.deliveryms.service.DeliveryServiceImpl;
import com.trainingapps.stockapp.deliveryms.util.DeliveryUtil;

@ExtendWith(MockitoExtension.class)
class DeliveryServiceImplUnitTest {

	@Mock
	DeliveryUtil util;

	@Mock
	IDeliveryRepository repository;

	@Spy
	@InjectMocks
	DeliveryServiceImpl service;
	
	/**
	 * scenario : adding delivery details
	 */
	@Test
	public void testAdd_1() {
		AddDeliveryRequest adr = new AddDeliveryRequest();
		adr.setOrderId(109l);
		Delivery delivery =new Delivery(); // mock(Delivery.class);
		delivery.setDeliveryId(1l);
		doReturn(delivery).when(service).newDelivery();
		assertEquals(1,delivery.getDeliveryId());
		when(repository.save(delivery)).thenReturn(delivery); 
		DeliveryDetails details = mock(DeliveryDetails.class);
		when(util.toDetails(delivery)).thenReturn(details);
		DeliveryDetails result = service.add(adr);
		assertEquals(details, result);
		assertEquals(109l,delivery.getOrderId());
//		assertEquals(null,delivery.getDeliveredDate());
//		assertEquals(DeliveryStatus.NOT_DISPATCHED,delivery.getDeliveryStatus());
	}
	
	/**
	 * scenario : find delivery details by order id
	 */
	@Test
	public void testFind_1() {
		long id=109l;
		boolean exist =true;
		Delivery delivery =mock(Delivery.class);
		DeliveryDetails details = mock(DeliveryDetails.class);
		delivery.setOrderId(id);
		when(repository.existsByOrderId(id)).thenReturn(exist);
		when(repository.findDeliveryByOrderId(id)).thenReturn(delivery);
		when(util.toDetails(delivery)).thenReturn(details);
		DeliveryDetails desired =service.findByOrderId(id);
		assertEquals(details, desired);
	}
	
	/**
	 * scenario : delivery details by order id not found
	 */
	@Test
	public void testFind_2() {
		long id=109l;
	Executable executable = () -> {
		boolean exist =false;
		Delivery delivery =mock(Delivery.class);
		DeliveryDetails details = mock(DeliveryDetails.class);
		delivery.setOrderId(109l);
		when(repository.existsByOrderId(id)).thenReturn(exist);
		//when(repository.findDeliveryByOrderId(id)).thenReturn(delivery);
		//when(util.toDetails(delivery)).thenReturn(details);
		DeliveryDetails desired =service.findByOrderId(id);
	};
	    assertThrows(DeliveryNotFoundException.class, executable);
	    //doThrow(null)
	}
	
	/**
	 * scenario : delivery details by order id not found
	 */
	@Test
	public void testFindAllStatus_1() {
		
	}
	
}
