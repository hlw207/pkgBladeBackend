package org.example.types;

public class ProcessResult<T> {
    private final T output;
    private final int exitCode;

    public ProcessResult(T output, int exitCode) {
        this.output = output;
        this.exitCode = exitCode;
    }

    public T getOutput() { return output; }
    public int getExitCode() { return exitCode; }

    @Override
    public String toString() {
        return "Exit Code: " + exitCode + "\nOutput:\n" + output;
    }
}
