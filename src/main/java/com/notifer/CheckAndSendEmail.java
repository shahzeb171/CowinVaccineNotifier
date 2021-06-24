package com.notifer;

import java.io.IOException;
import java.util.List;

import com.pojos.Center;
import com.pojos.Session;

public class CheckAndSendEmail {
	public void send() throws InterruptedException, IOException {
		APIFetcher fetcher = new APIFetcher();
		while(true) {
			List<Center> active =  fetcher.getActiveCenters();
			if(active.size()!=0) {
				String message="";
				for(Center center : active) {
					message+="Center Block Name: "+center.getBlock_name()+"\n";
					message+="Center Address: "+center.getAddress()+"\n";
					message+="Center Name: "+center.getName()+"\n";
					message+="Center Pincode: "+center.getPincode()+"\n";
					for(Session session : center.getSessions()) {
						message+="Vaccine : "+session.getVaccine()+"\n";
						message+="Availability : "+session.getAvailable_capacity()+"\n";
						message+="Date : "+session.getDate()+"\n";
						message+="Age Limit : "+session.getMin_age_limit()+"+\n";
						message+="\n";
					}
					message+="\n";
				}
				message+="Go to : https://selfregistration.cowin.gov.in/"+"\n";
				SendMail sendMail = new SendMail();
				sendMail.doSendEmail("***recepient_email***",
						message);
				System.out.println("Message "+message);
			}
			System.out.println("In CheckAndSend");
			Thread.sleep(1000*4);
		}
		
	}
}
