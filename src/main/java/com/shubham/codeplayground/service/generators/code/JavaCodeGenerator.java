package com.shubham.codeplayground.service.generators.code;

import com.shubham.codeplayground.model.dto.CodeStubDTO;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component("JAVA_CODE_GENERATOR")
public class JavaCodeGenerator implements CodeGenerator{
    @Override
    public String generateDriverCode(CodeStubDTO stub) {
            StringBuilder sb = new StringBuilder();

            sb.append("import java.util.*;\n\n");
            sb.append("public class Driver {\n\n");
            sb.append("    public static String runTests() {\n");
            sb.append("        StringBuilder resultLog = new StringBuilder();\n");
            sb.append("        int testNum = 1;\n\n");
            sb.append("        for (String line : TestCases.getAll()) {\n");
            sb.append("            try {\n");
            sb.append("                TestCase test = TestCaseParser.parseLine(line);\n");
            sb.append("                List<Object> inputs = test.getInput();\n\n");

            // ---- Generate parameter casting from stub ----
            for (int i = 0; i < stub.getParameters().size(); i++) {
                CodeStubDTO.ParameterDTO p = stub.getParameters().get(i);
                String type = p.getType();
                boolean isArray = p.isArray();

                String paramType = type + (isArray ? "[]" : "");
                String castExpr;

                if (isArray) {
                    switch (type) {
                        case "int": castExpr = "toIntArray(inputs.get(" + i + "))"; break;
                        case "double": castExpr = "toDoubleArray(inputs.get(" + i + "))"; break;
                        case "float": castExpr = "toFloatArray(inputs.get(" + i + "))"; break;
                        case "boolean": castExpr = "toBooleanArray(inputs.get(" + i + "))"; break;
                        case "String": castExpr = "toStringArray(inputs.get(" + i + "))"; break;
                        default: castExpr = "(" + paramType + ") inputs.get(" + i + ")"; break;
                    }
                } else {
                    switch (type) {
                        case "int": castExpr = "((Number) inputs.get(" + i + ")).intValue()"; break;
                        case "double": castExpr = "((Number) inputs.get(" + i + ")).doubleValue()"; break;
                        case "float": castExpr = "((Number) inputs.get(" + i + ")).floatValue()"; break;
                        case "boolean": castExpr = "(Boolean) inputs.get(" + i + ")"; break;
                        case "String": castExpr = "(String) inputs.get(" + i + ")"; break;
                        default: castExpr = "(" + paramType + ") inputs.get(" + i + ")"; break;
                    }
                }

                sb.append("                " + paramType + " " + p.getName() + " = " + castExpr + ";\n");
            }

            sb.append("\n");
            // ---- Method call ----
            String returnType = stub.getReturnType() + (stub.isReturnisArray() ? "[]" : "");
            String expectedCast = getExpectedCast(stub.getReturnType(), stub.isReturnisArray());

            sb.append("                " + returnType + " actual = Solution." + stub.getFunctionName() + "(");
            for (int i = 0; i < stub.getParameters().size(); i++) {
                if (i > 0) sb.append(", ");
                sb.append(stub.getParameters().get(i).getName());
            }
            sb.append(");\n");

            sb.append("                " + returnType + " expected = " + expectedCast + ";\n\n");

            // ---- Validation ----
            sb.append("                if (equalsValue(actual, expected)) {\n");
            sb.append("                    resultLog.append(\"Test \").append(testNum).append(\": PASSED\\n\");\n");
            sb.append("                } else {\n");
            sb.append("                    resultLog.append(\"Test \").append(testNum)\n");
            sb.append("                             .append(\": FAILED | Expected=\").append(expected)\n");
            sb.append("                             .append(\" but got=\").append(actual).append(\"\\n\");\n");
            sb.append("                }\n");
            sb.append("            } catch (Exception e) {\n");
            sb.append("                resultLog.append(\"Test \").append(testNum)\n");
            sb.append("                         .append(\": ERROR -> \").append(e.getMessage()).append(\"\\n\");\n");
            sb.append("            }\n");
            sb.append("            testNum++;\n");
            sb.append("        }\n\n");
            sb.append("        return resultLog.toString();\n");
            sb.append("    }\n\n");

            // === Utility methods ===
            sb.append(getUtilityMethods());

            sb.append("}\n");
            return sb.toString();
        }

        private static String getExpectedCast(String type, boolean isArray) {
            if (isArray) return "(" + type + "[]) test.getExpected()";
            switch (type) {
                case "int": return "((Number) test.getExpected()).intValue()";
                case "double": return "((Number) test.getExpected()).doubleValue()";
                case "float": return "((Number) test.getExpected()).floatValue()";
                case "boolean": return "(Boolean) test.getExpected()";
                case "String": return "(String) test.getExpected()";
                default: return "(" + type + ") test.getExpected()";
            }
        }

        private static String getUtilityMethods() {
            return
                    "    private static int[] toIntArray(Object obj) {\n" +
                            "        if (obj instanceof int[]) return (int[]) obj;\n" +
                            "        if (obj instanceof List) {\n" +
                            "            List<?> list = (List<?>) obj;\n" +
                            "            int[] arr = new int[list.size()];\n" +
                            "            for (int i = 0; i < list.size(); i++) arr[i] = ((Number) list.get(i)).intValue();\n" +
                            "            return arr;\n" +
                            "        }\n" +
                            "        throw new IllegalArgumentException(\"Cannot convert to int[]\");\n" +
                            "    }\n\n" +
                            "    private static boolean equalsValue(Object a, Object b) {\n" +
                            "        if (a == null && b == null) return true;\n" +
                            "        if (a == null || b == null) return false;\n" +
                            "        if (a.getClass().isArray() && b.getClass().isArray()) {\n" +
                            "            if (a instanceof int[] && b instanceof int[]) return Arrays.equals((int[]) a, (int[]) b);\n" +
                            "            if (a instanceof double[] && b instanceof double[]) return Arrays.equals((double[]) a, (double[]) b);\n" +
                            "            if (a instanceof float[] && b instanceof float[]) return Arrays.equals((float[]) a, (float[]) b);\n" +
                            "            if (a instanceof boolean[] && b instanceof boolean[]) return Arrays.equals((boolean[]) a, (boolean[]) b);\n" +
                            "            if (a instanceof String[] && b instanceof String[]) return Arrays.equals((String[]) a, (String[]) b);\n" +
                            "            return Arrays.deepEquals((Object[]) a, (Object[]) b);\n" +
                            "        }\n" +
                            "        return a.equals(b);\n" +
                            "    }\n";
        }

    @Override
    public String generateCodeStubString(CodeStubDTO codeStub) {
        StringBuilder sb = new StringBuilder();
        sb.append("import java.util.*;\n\n");
        sb.append("class Solution {\n");

        // Build return type
        String returnType = codeStub.getReturnType() + (codeStub.isReturnisArray() ? "[]" : "");

        // Build parameters
        List<String> params = new ArrayList<>();
        for (CodeStubDTO.ParameterDTO p : codeStub.getParameters()) {
            String paramType = p.getType() + (p.isArray() ? "[]" : "");
            params.add(paramType + " " + p.getName());
        }

        // Build method signature
        sb.append("    public ").append(returnType).append(" ")
                .append(codeStub.getFunctionName()).append("(")
                .append(String.join(", ", params))
                .append(") {\n");

        // Method body placeholder
        sb.append("        // Write your code here\n");

        // Default return placeholder
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
