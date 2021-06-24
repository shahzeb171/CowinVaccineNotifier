package com.pojos;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class Session {
	String session_id;
	String vaccine;
	String date;
	int available_capacity_dose1;
	int available_capacity_dose2;
	int min_age_limit;
}
