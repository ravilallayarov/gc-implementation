package org.example;

import java.util.Arrays;
import java.util.Deque;
import java.util.LinkedList;
import java.util.List;


public class StackInfo {
  Deque<Frame> stack = new LinkedList<>();

  public void push(String methodName, ApplicationBean... parameters) {
    stack.push(new Frame(methodName, Arrays.asList(parameters)));
  }

  public Frame pop() {
    return stack.pop();
  }

  public Deque<Frame> getStack() {
    return stack;
  }

  public class Frame {
    String methodName;
    List<ApplicationBean> parameters;

    public String getMethodName() {
      return methodName;
    }

    public List<ApplicationBean> getParameters() {
      return parameters;
    }

    public Frame(String methodName, List<ApplicationBean> parameters) {
      this.methodName = methodName;
      this.parameters = parameters;
    }

    @Override
    public String toString() {
      return "Frame{" +
              "methodName='" + methodName + '\'' +
              ", parameters=" + parameters +
              '}';
    }
  }
}

