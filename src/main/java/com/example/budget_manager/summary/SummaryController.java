package com.example.budget_manager.summary;

import com.example.budget_manager.summary.DTO.SummaryResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/summary")
public class SummaryController {
    private final SummaryService summaryService;
    public SummaryController(SummaryService summaryService) {
        this.summaryService = summaryService;
    }

    @GetMapping
    public SummaryResponse getSummary() {
        return summaryService.getSummary();
    }


}
