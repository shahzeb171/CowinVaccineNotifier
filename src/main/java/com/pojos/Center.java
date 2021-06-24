package com.pojos;

import java.util.List;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class Center {
	String center_id;
	String address;
	String block_name;
	String name;
	String pincode;
	List<Session> sessions;	
}
