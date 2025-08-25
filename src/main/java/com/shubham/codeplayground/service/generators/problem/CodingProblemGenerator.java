package com.shubham.codeplayground.service.generators.problem;

import com.shubham.codeplayground.model.dto.CreateProblemDTO;
import com.shubham.codeplayground.model.entity.CodeSnippet;
import com.shubham.codeplayground.model.entity.Testcase;
import com.shubham.codeplayground.model.entity.problem.CodingProblem;
import com.shubham.codeplayground.model.entity.problem.Problem;
import com.shubham.codeplayground.model.enums.Language;
import com.shubham.codeplayground.repository.CodingProblemRepository;
import com.shubham.codeplayground.service.CodeSnippetService;
import com.shubham.codeplayground.service.TestcaseService;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component("CODING")
public class CodingProblemGenerator implements ProblemGenerator {
    private final TestcaseService testcaseService;
    private final CodeSnippetService codeSnippetService;
    private final CodingProblemRepository codingProblemRepository;

    public CodingProblemGenerator(TestcaseService testcaseService, CodeSnippetService codeSnippetService, CodingProblemRepository codingProblemRepository) {
        this.testcaseService = testcaseService;
        this.codeSnippetService = codeSnippetService;
        this.codingProblemRepository = codingProblemRepository;
    }

    @Override
    public Problem generate(CreateProblemDTO createProblemDTO) {
        List<Testcase> testcases = testcaseService.parseTestcasesFromBuffer("");
        String[] languages = createProblemDTO.getLanguages();

        String solution = "";
        List<CodeSnippet> codeSnippets = new ArrayList<>();

        for (String s : languages) {
            Language language = Language.valueOf(s);
            CodeSnippet snippet = codeSnippetService.generateSnippet(language, createProblemDTO.getCodeStub(), solution);
            if (codeSnippetService.validate(snippet, testcases)) {
                codeSnippets.add(snippet);
            }
        }

        CodingProblem codingProblem = new CodingProblem();
        // TODO: insert details in coding object
        codingProblem.setTestCases(testcases);
        codingProblem.setCodeSnippets(codeSnippets);

        codingProblem = codingProblemRepository.save(codingProblem);

        return codingProblem;
    }
}
