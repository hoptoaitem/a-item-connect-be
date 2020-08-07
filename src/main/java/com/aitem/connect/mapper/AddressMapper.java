package com.aitem.connect.mapper;

import com.aitem.connect.enums.ProfileType;
import com.aitem.connect.model.AddressModel;
import com.aitem.connect.request.AddressRequest;
import lombok.Data;

import java.io.Serializable;


@Data
public class AddressMapper implements Serializable {
	
	private static final long serialVersionUID = 4583705071842668449L;

	// TODO : replace with mapper
	public static AddressModel getAddressModel(AddressRequest addressRequest) {
		AddressModel model = new AddressModel();
		model.setAddressName(addressRequest.getAddressName());
		model.setStreetAddress(addressRequest.getStreetAddress());
		model.setStreetAddress1(addressRequest.getStreetAddress1());
		model.setCity(addressRequest.getCity());
		model.setState(addressRequest.getState());
		model.setZip(addressRequest.getZip());
		return model;
	}
}
