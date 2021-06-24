package com.vaccnotifier.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.pojos.Center;
import com.pojos.Centers;
import com.pojos.Session;

@RestController
public class IndexController {

	@Autowired
	RestTemplate restTemplate;
	@Autowired
	JavaMailSender mailSender;

	@GetMapping("/")
	public void checker() throws InterruptedException {
		  String baseurl=
				  "https://cdn-api.co-vin.in/api/v2/appointment/sessions/public/calendarByPin?";

	      Date date = new Date();
		  SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
		  String strDate= formatter.format(date).replace("/","-");

		  String complete=baseurl+"pincode=208001&date="+strDate;
		  while(true) {
		  Centers centers = restTemplate.
				  getForObject(complete, Centers.class);

		  List<Center> activeCenters=new ArrayList<Center>();
		  for(Center center: centers.getCenters()) {
					Center active = new Center();
					active.setAddress(center.getAddress());
					active.setBlock_name(center.getBlock_name());
					active.setCenter_id(center.getCenter_id());
					active.setName(center.getName());
					active.setPincode(center.getPincode());
					List<Session> list = new ArrayList<Session>();
					for(Session session : center.getSessions()) {
						if(session.getMin_age_limit()>=45
								&& 
							session.getAvailable_capacity_dose1()>0) {
							Session sess = new Session();
							sess.setSession_id(session.getSession_id());
							sess.setAvailable_capacity_dose1(session.getAvailable_capacity_dose1());
							sess.setDate(session.getDate());
							sess.setMin_age_limit(session.getMin_age_limit());
							sess.setVaccine(session.getVaccine());

							list.add(sess);
						}
					}
					if(list.size()!=0) {
						active.setSessions(list);
						activeCenters.add(active);
					}
				}
				send(activeCenters);
		  Thread.sleep(1000*30);
		  }
	}
	public void send(List<Center> active)
	{
	if(active.size()!=0) {
		String message="";
		for(Center center : active) {
			message+="Center Block Name: "+center.getBlock_name()+"\n";
			message+="Center Address: "+center.getAddress()+"\n";
			message+="Center Name: "+center.getName()+"\n";
			message+="Center Pincode: "+center.getPincode()+"\n";
			for(Session session : center.getSessions()) {
				message+="Vaccine : "+session.getVaccine()+"\n";
				message+="Dose 1 Availability : "+session.getAvailable_capacity_dose1()+"\n";
				message+="Date : "+session.getDate()+"\n";
				message+="Age Limit : "+session.getMin_age_limit()+"+\n";
				message+="\n";
			}
			message+="\n";
		}
		message+="Go to : https://selfregistration.cowin.gov.in/"+"\n";
		doSendEmail("shahzeb.qamar171@gmail.com",
				message);
	}
	}
	public void doSendEmail(String recipientAddress
			,String message) {

    	String subject = "COWIN SLOT ACTIVE: ";

    	SimpleMailMessage email = new SimpleMailMessage();

        email.setTo(recipientAddress);
        email.setSubject(subject);
        email.setText(message);

        System.out.println(message);

        mailSender.send(email);
    }
}