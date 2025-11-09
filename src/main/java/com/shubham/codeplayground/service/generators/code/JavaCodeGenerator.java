package com.shubham.codeplayground.service.generators.code;

import com.shubham.codeplayground.model.dto.CodeStubDTO;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component("JAVA_CODE_GENERATOR")
public class JavaCodeGenerator implements CodeGenerator{
    @Override
    public String generateDriverCode(CodeStubDTO codeStub) {
        StringBuilder sb = new StringBuilder();

        sb.append("import java.util.*;\n\n");
        sb.append("public class Driver {\n");
        sb.append("\tpublic static void main(String[] args) {\n");
        sb.append("\t\ttry {\n");
        sb.append("\t\t\tint argIndex = 0;\n");
        // ---- Identify and run loop for testcases
        sb.append("\t\t\tint testcases = Integer.parseInt(args[argIndex++]);\n");
        sb.append("\t\t\twhile (testcases > 0) {\n");
        sb.append("\t\t\t\ttestcases--;\n");

        for (CodeStubDTO.ParameterDTO param : codeStub.getParameters()) {
            String type = param.getType().toLowerCase();
            String name = param.getName();
            if (param.isArray()) {
                sb.append(String.format("\t\t\t\tint %sLength = Integer.parseInt(args[argIndex++]);\n", name));
                sb.append(String.format("\t\t\t\t%s[] %s = new %s[%sLength];\n", mapType(type), name, mapType(type), name));
                sb.append(String.format("\t\t\t\tfor (int i = 0; i < %sLength; i++) {\n", name));
                sb.append(String.format("\t\t\t\t\t%s[i] = %s;\n", name, parseValue(type, "args[argIndex++]")));
                sb.append("\t\t\t\t}\n");
            } else {
                sb.append(String.format("\t\t\t\t%s %s = %s;\n", mapType(type), name, parseValue(type, "args[argIndex++]")));
            }
        }

        // ---- Function call ----
        sb.append("\t\t\t\tSolution solution = new Solution();\n");

        boolean hasReturn = codeStub.getReturnType() != null && !"void".equalsIgnoreCase(codeStub.getReturnType());
        if (hasReturn) {
            if (codeStub.isReturnisArray()) {
                sb.append(String.format("\t\t\t\t%s[] result = solution.%s(", mapType(codeStub.getReturnType()), codeStub.getFunctionName()));
            } else {
                sb.append(String.format("\t\t\t\t%s result = solution.%s(", mapType(codeStub.getReturnType()), codeStub.getFunctionName()));
            }
        } else {
            sb.append(String.format("\t\t\t\tsolution.%s(", codeStub.getFunctionName()));
        }

        List<String> paramNames = new ArrayList<>();
        for (CodeStubDTO.ParameterDTO p : codeStub.getParameters()) {
            paramNames.add(p.getName());
        }

        sb.append(String.join(", ", paramNames));
        sb.append(");\n");

        // ---- Print result ----
        if (hasReturn) {
            if (codeStub.isReturnisArray()) {
                sb.append("\t\t\t\tSystem.out.println(Arrays.toString(result));\n");
            } else {
                sb.append("\t\t\t\tSystem.out.println(result);\n");
            }
        }

        sb.append("\t\t\t}\n");
        sb.append("\t\t} catch (Exception e) {\n");
        sb.append("\t\t\te.printStackTrace();\n");
        sb.append("\t\t}\n");
        sb.append("\t}\n");
        sb.append("}\n");

        return sb.toString();
    }

    private static String mapType(String type) {
        return switch (type.toLowerCase()) {
            case "int" -> "int";
            case "float" -> "float";
            case "double" -> "double";
            case "boolean" -> "boolean";
            case "string" -> "String";
            default -> "Object";
        };
    }

    private static String parseValue(String type, String argExpr) {
        return switch (type.toLowerCase()) {
            case "int" -> "Integer.parseInt(" + argExpr + ")";
            case "float" -> "Float.parseFloat(" + argExpr + ")";
            case "double" -> "Double.parseDouble(" + argExpr + ")";
            case "boolean" -> "Boolean.parseBoolean(" + argExpr + ")";
            default -> argExpr;
        };
    }

    @Override
    public String generateCodeStubString(CodeStubDTO codeStub) {
        StringBuilder sb = new StringBuilder();
        sb.append("import java.util.*;\n\n");
        sb.append("class Solution {\n");

        String returnType = codeStub.getReturnType() + (codeStub.isReturnisArray() ? "[]" : "");

        List<String> params = new ArrayList<>();
        for (CodeStubDTO.ParameterDTO p : codeStub.getParameters()) {
            String paramType = p.getType() + (p.isArray() ? "[]" : "");
            params.add(paramType + " " + p.getName());
        }

        sb.append("    public ").append(returnType).append(" ")
                .append(codeStub.getFunctionName()).append("(")
                .append(String.join(", ", params))
                .append(") {\n");

        sb.append("        // Write your code here\n");

        sb.append("        return ").append(getDefaultReturn(codeStub.getReturnType(), codeStub.isReturnisArray())).append(";\n");

        sb.append("    }\n");
        sb.append("}\n");

        return sb.toString();
    }

    private String getDefaultReturn(String type, boolean isArray) {
        if (isArray) return "null";
        return switch (type) {
            case "int" -> "0";
            case "double" -> "0.0";
            case "float" -> "0.0f";
            case "boolean" -> "false";
            default -> "null";
        };
    }
}
