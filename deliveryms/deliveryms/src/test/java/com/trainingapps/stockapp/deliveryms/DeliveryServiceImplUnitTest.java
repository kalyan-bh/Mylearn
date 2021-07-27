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
import com.trainingapps.stockapp.deliveryms.dao.IDeliveryRepository;
import com.trainingapps.stockapp.deliveryms.dto.AddDeliveryRequest;
import com.trainingapps.stockapp.deliveryms.dto.DeliveryDetails;
import com.trainingapps.stockapp.deliveryms.entities.Delivery;
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
	
	@Test
	public void testAdd_1() {
		Delivery delivery=mock(Delivery.class);
		AddDeliveryRequest adr=new AddDeliveryRequest();
		adr.setOrderId(109l);
		doReturn(delivery).when(service).newDelivery();
		DeliveryDetails details=mock(DeliveryDetails.class);
		when(repository.save(delivery)).thenReturn(delivery);
		when(util.toDetails(delivery)).thenReturn(details);
		DeliveryDetails result=service.add(adr);
		assertEquals(details,result);
	}

}
