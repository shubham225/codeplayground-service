package com.shubham.codeplayground.service.generators.problem;

import com.shubham.codeplayground.model.dto.CreateProblemDTO;
import com.shubham.codeplayground.model.dto.FileDTO;
import com.shubham.codeplayground.model.entity.CodeSnippet;
import com.shubham.codeplayground.model.entity.Testcase;
import com.shubham.codeplayground.model.entity.problem.CodingProblem;
import com.shubham.codeplayground.model.entity.problem.Problem;
import com.shubham.codeplayground.model.enums.Language;
import com.shubham.codeplayground.repository.CodingProblemRepository;
import com.shubham.codeplayground.service.CodeSnippetService;
import com.shubham.codeplayground.service.StorageService;
import com.shubham.codeplayground.service.TestcaseService;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Component("CODING")
public class CodingProblemGenerator implements ProblemGenerator {
    private final TestcaseService testcaseService;
    private final CodeSnippetService codeSnippetService;
    private final CodingProblemRepository codingProblemRepository;
    private final StorageService storageService;

    public CodingProblemGenerator(TestcaseService testcaseService,
                                  CodeSnippetService codeSnippetService,
                                  CodingProblemRepository codingProblemRepository,
                                  StorageService storageService) {
        this.testcaseService = testcaseService;
        this.codeSnippetService = codeSnippetService;
        this.codingProblemRepository = codingProblemRepository;
        this.storageService = storageService;
    }

    @Override
    public Problem generate(CreateProblemDTO createProblemDTO) {
        // TODO: for now only first testcase is read, read all array of Testcase files in future
        UUID fileId = createProblemDTO.getTestcases().stream().map(FileDTO::getId).findFirst().get();
        List<Testcase> testcases = testcaseService.parseTestcasesFromFile(fileId);
        String[] languages = createProblemDTO.getLanguages();

        // TODO: for now only first solution is read, read all array of Solutions files in future
        UUID solutionFileId = createProblemDTO.getSolutions().stream().map(FileDTO::getId).findFirst().get();
        String solution = storageService.getFileContentsAsString(solutionFileId);
        List<CodeSnippet> codeSnippets = new ArrayList<>();

        for (String s : languages) {
            Language language = Language.valueOf(s);
            CodeSnippet snippet = codeSnippetService.generateSnippet(language, createProblemDTO.getCodeStub(), solution);
            if (codeSnippetService.validate(snippet, testcases)) {
                codeSnippets.add(snippet);
            }
        }

        CodingProblem codingProblem = getCodingProblem(createProblemDTO, testcases, codeSnippets);

        codingProblem = codingProblemRepository.save(codingProblem);

        return codingProblem;
    }

    private CodingProblem getCodingProblem(CreateProblemDTO createProblemDTO,
                                           List<Testcase> testcases,
                                           List<CodeSnippet> codeSnippets) {
        CodingProblem codingProblem = new CodingProblem();
        // TODO: insert details in coding object
        codingProblem.setTitle(createProblemDTO.getTitle());
        codingProblem.setDescriptionMd(createProblemDTO.getDescriptionMd());
        codingProblem.setDifficulty(createProblemDTO.getDifficulty());
        codingProblem.setMaxExecutionTime(createProblemDTO.getMaxExecutionTime());
        codingProblem.setTestCases(testcases);
        codingProblem.setCodeSnippets(codeSnippets);
        codingProblem.setIsActive(true);

        return codingProblem;
    }
}
