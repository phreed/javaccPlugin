package ca.coglinc.gradle.plugins.javacc;

import java.io.File;
import java.util.Map;

import org.gradle.api.file.RelativePath;
import org.javacc.jjtree.JJTree;

public class CompileJJTreeTask extends AbstractJavaccTask {
    public static final String TASK_NAME_VALUE = "compileJJTree";
    public static final String TASK_DESCRIPTION_VALUE = "Compiles JJTree files into JavaCC files";

    private static final String DEFAULT_INPUT_DIRECTORY = File.separator + "src" + File.separator + "main" + File.separator + "jjtree";
    private static final String DEFAULT_OUTPUT_DIRECTORY = File.separator + "generated" + File.separator + "jjtree";

    public CompileJJTreeTask() {
        super(CompileJJTreeTask.DEFAULT_INPUT_DIRECTORY, CompileJJTreeTask.DEFAULT_OUTPUT_DIRECTORY, "**/*.jjt");
    }

    @Override
    protected void augmentArguments(File inputDirectory, RelativePath inputRelativePath, Map<String, String> arguments) {
        arguments.put("JJTREE_OUTPUT_DIRECTORY", inputRelativePath.getFile(getOutputDirectory()).getParentFile().getAbsolutePath());
    }

    @Override
    protected String getProgramName() {
        return "JJTree";
    }

    @Override
    protected void invokeCompiler(String[] arguments) throws Exception {
        int errorCode = new JJTree().main(arguments);
        if (errorCode != 0) {
            throw new IllegalStateException("JJTree failed with error code: [" + errorCode + "]");
        }
    }
}
