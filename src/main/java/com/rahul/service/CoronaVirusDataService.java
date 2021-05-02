package com.rahul.service;

import java.io.*;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.rahul.model.Stats;

@Service
public class CoronaVirusDataService {

	private static String VIRUS_DATA_URL = "https://raw.githubusercontent.com/CSSEGISandData/COVID-19/master/csse_covid_19_data/csse_covid_19_time_series/time_series_covid19_confirmed_global.csv";
	
	private List<Stats> allStats  = new ArrayList<>();
	
	public List<Stats> getAllStats() {
		return allStats;
	}

	public void setAllStats(List<Stats> allStats) {
		this.allStats = allStats;
	}

	
	@PostConstruct
	@Scheduled(cron = "* * 1 * * *")
	public void  fetchVirusData() {
		
		try {
			List<Stats> newStat = new ArrayList<>();
			
			HttpClient httpclient = HttpClient.newHttpClient();
			HttpRequest request = HttpRequest.newBuilder()
			.uri(URI.create(VIRUS_DATA_URL))
			.build();
			
			HttpResponse<String> httpresponse = httpclient.send(request, HttpResponse.BodyHandlers.ofString());
			//System.out.println("Http Response"+ httpresponse.body());
			
			StringReader reader = new StringReader(httpresponse.body());
			
			Iterable<CSVRecord> records = CSVFormat.DEFAULT.withFirstRecordAsHeader().parse(reader);
			for (CSVRecord record : records) {
				Stats stat = new Stats();
			    stat.setState(record.get("Province/State"));
			    stat.setCountry("Country/Region");
			    int latestcases = Integer.parseInt(record.get(record.size()-1));
			    int diffFromPrevDay = Integer.parseInt(record.get(record.size()-2));
			    stat.setLatestTotalCases(latestcases);
			    stat.setDiffFromPrevDay(latestcases-diffFromPrevDay);
			 //   System.out.println(stat);
			    newStat.add(stat);
			}
			
			this.setAllStats(newStat);
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
