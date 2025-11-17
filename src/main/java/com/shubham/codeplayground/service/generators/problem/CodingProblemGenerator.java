package com.shubham.codeplayground.service.generators.problem;

import com.shubham.codeplayground.exception.TestCaseNotFoundException;
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
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Component("CODING")
@RequiredArgsConstructor
public class CodingProblemGenerator implements ProblemGenerator {
    private final TestcaseService testcaseService;
    private final CodeSnippetService codeSnippetService;
    private final CodingProblemRepository codingProblemRepository;
    private final StorageService storageService;

    @Override
    public Problem generate(CreateProblemDTO createProblemDTO) {
        String[] languages = createProblemDTO.getLanguages();
        List<Testcase> testcases = testcaseService.parseTestcasesFromFileDtos(createProblemDTO.getTestcases());

        /*
        * TODO: for now solution file is optional and only first solution is read,
        *  read all array of Solutions files in future
        */
        UUID solutionFileId = createProblemDTO.getSolutions().stream()
                                    .map(FileDTO::getId).findFirst().orElse(null);

        String solution = "";
        if (solutionFileId != null) solution = storageService.getDatabaseFileContentsAsString(solutionFileId);

        List<CodeSnippet> codeSnippets = new ArrayList<>();

        for (String s : languages) {
            try {
                Language language = Language.valueOf(s.toUpperCase());
                CodeSnippet snippet = codeSnippetService.generateSnippet(language, createProblemDTO.getCodeStub(), solution);

                if (codeSnippetService.validate(snippet, testcases)) {
                    codeSnippets.add(snippet);
                }
            } catch (IllegalArgumentException e) {
                System.out.println("Language '" + s +"' not supported, skipping the code generation: " + e.getMessage());
            }
        }

        CodingProblem codingProblem = getCodingProblem(createProblemDTO, testcases, codeSnippets);
        return codingProblemRepository.save(codingProblem);
    }

    private CodingProblem getCodingProblem(CreateProblemDTO createProblemDTO,
                                           List<Testcase> testcases,
                                           List<CodeSnippet> codeSnippets) {
        CodingProblem codingProblem = new CodingProblem();

        codingProblem.setTitle(createProblemDTO.getTitle());
        codingProblem.setDescriptionMd(createProblemDTO.getDescriptionMd());
        codingProblem.setDifficulty(createProblemDTO.getDifficulty());
        codingProblem.setMaxExecutionTime(createProblemDTO.getMaxExecutionTime());
        codingProblem.addAllTestcases(testcases);
        codingProblem.addAllCodeSnippets(codeSnippets);
        codingProblem.setIsActive(true);

        return codingProblem;
    }
}
