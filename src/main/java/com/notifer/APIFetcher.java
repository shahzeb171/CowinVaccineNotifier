package com.notifer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.google.gson.Gson;
import com.pojos.Center;
import com.pojos.Centers;
import com.pojos.Session;

public class APIFetcher {
	int response;
		String fetch() throws IOException {
		Date date = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
		String strDate= formatter.format(date).replace("/","-");
		
		URL url = new URL("https://cdn-api.co-vin.in/api/v2/appointment/sessions/public/calendarByDistrict?district_id=664&date="+strDate);
		HttpURLConnection con = (HttpURLConnection) url.openConnection();
		con.setRequestProperty("User-Agent", "Mozilla");
		con.setRequestProperty("Content-Type", "application/json");
		con.connect();
		
		response = con.getResponseCode();
		String res="";
		if(response != 200)
			res+="CONNECTION FAILED "+response;
		else {
			BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream()));
			StringBuilder inline=new StringBuilder("");
			int cp;
			while((cp=br.read())!=-1) {
			    inline.append((char)cp);
			  // System.out.println(cp+" "+inline.toString());
			}
			con.disconnect();
			res=inline.toString();
		}
		return res;
	}
	List<Center> getActiveCenters() throws InterruptedException, IOException{
		String s  = fetch();
		Thread.sleep(1000);
		List<Center> activeCenters = new ArrayList();
		if(response== 200) {
		Gson g = new Gson();
		Centers centers = g.fromJson(s,Centers.class);

		for(Center center: centers.getCenters()) {
			Center active = new Center();
			active.setAddress(center.getAddress());
			active.setBlock_name(center.getBlock_name());
			active.setCenter_id(center.getCenter_id());
			active.setName(center.getName());
			active.setPincode(center.getPincode());
			List<Session> list = new ArrayList<Session>();
			for(Session session : center.getSessions()) {
				if(session.getMin_age_limit()==18
						&& 
					session.getAvailable_capacity()>0) {
					System.out.println("In Sess ");
					Session sess = new Session();
					sess.setSession_id(session.getSession_id());
					sess.setAvailable_capacity(session.getAvailable_capacity());
					sess.setDate(session.getDate());
					sess.setMin_age_limit(session.getMin_age_limit());
					sess.setVaccine(session.getVaccine());
					
					list.add(sess);
					System.out.println("List "+list);
				}
			}
			if(list.size()!=0) {
				active.setSessions(list);
				activeCenters.add(active);
			}
		}
		}
		System.out.println("active "+activeCenters);
		return activeCenters;
	}
}
