package com.shubham.onlinetest.controller;

import com.shubham.onlinetest.model.dto.ProblemDTO;
import com.shubham.onlinetest.model.dto.ProblemSummeryDTO;
import com.shubham.onlinetest.service.ProblemService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

import static com.shubham.onlinetest.controller.RestApi.VERSION;

@RestController
@RequestMapping(value = VERSION + "problems")
public class ProblemController {
    private final ProblemService problemService;

    public ProblemController(ProblemService problemService) {
        this.problemService = problemService;
    }

    @RequestMapping(
            path = "",
            method = RequestMethod.GET
    )
    public List<ProblemSummeryDTO> getAllProblems() {
        return problemService.getAllProblemSummery();
    }

    @RequestMapping(
            path = "/{id}",
            method = RequestMethod.GET
    )
    public ProblemDTO getProblemById(@PathVariable UUID id) {
        return problemService.getProblemInfoById(id);
    }
}
