package com.rahul.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.rahul.model.Stats;
import com.rahul.service.CoronaVirusDataService;

@Controller
public class HomeController {

	@Autowired
	CoronaVirusDataService coronavirus;
	
	@GetMapping("/")
	public String home(Model model) {
		
		List<Stats> allStats = coronavirus.getAllStats();
		int totalReportedCases = allStats.stream().mapToInt(stat -> stat.getLatestTotalCases()).sum();
		int totalNewCases = allStats.stream().mapToInt(stat -> stat.getDiffFromPrevDay()).sum();
		model.addAttribute("locationStats" , coronavirus.getAllStats());
		model.addAttribute("totalReportedCases" ,totalReportedCases );
		model.addAttribute("totalNewCases" ,totalNewCases );
		
		return "home";
	}
}
