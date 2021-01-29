package com.aitem.connect.mapper;

import com.aitem.connect.model.AddressDetailModel;
import com.aitem.connect.request.AddressDetailRequest;
import lombok.Data;

import java.io.Serializable;


@Data
public class AddressDetailMapper implements Serializable {
	
	private static final long serialVersionUID = 4583705071842668449L;

	// TODO : replace with mapper
	public static AddressDetailModel getAddressDetailModel(AddressDetailRequest addressDetailRequest) {
		AddressDetailModel model = new AddressDetailModel();
		model.setFirstName(addressDetailRequest.getFirstName());
		model.setLastName(addressDetailRequest.getLastName());
		model.setPhone(addressDetailRequest.getPhone());
		model.setEmail(addressDetailRequest.getEmail());
		model.setStreetAddress1(addressDetailRequest.getStreetAddress1());
		model.setStreetAddress(addressDetailRequest.getStreetAddress());
		model.setCity(addressDetailRequest.getCity());
		model.setState(addressDetailRequest.getState());
		model.setZip(addressDetailRequest.getZip());
		return model;
	}
}
