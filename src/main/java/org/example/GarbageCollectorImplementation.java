package org.example;


import java.util.ArrayList;
import java.util.Deque;
import java.util.List;
import java.util.Map;


public class GarbageCollectorImplementation implements GarbageCollector {
    @Override
    public List<ApplicationBean> collect(HeapInfo heap, StackInfo stack) {
        Map<String, ApplicationBean> beans = heap.getBeans();
        Deque<StackInfo.Frame> frames = stack.getStack();
        List<ApplicationBean> collectApplicationBeans = new ArrayList<>(beans.values().stream().distinct().toList());
        for (StackInfo.Frame frame : frames) {
            List<ApplicationBean> list = frame.parameters;
            for (ApplicationBean applicationBean : list) {
                List<ApplicationBean> childrenApplicationBeans = getChildrenApplicationBeans(applicationBean, new ArrayList<>());
                for (ApplicationBean app : childrenApplicationBeans) {
                    if (beans.containsValue(app)) {
                        collectApplicationBeans.remove(app);
                    }
                }
            }
        }
        return collectApplicationBeans;
    }

    private List<ApplicationBean> getChildrenApplicationBeans(ApplicationBean applicationBean, List<ApplicationBean> applications) {
        List<ApplicationBean> applicationBeans = new ArrayList<>();
        applicationBeans.add(applicationBean);
        applications.add(applicationBean);
        if (!applicationBean.getFieldValues().isEmpty()) {
            applicationBean.getFieldValues().forEach((key, value) -> {
                if(applications.contains(value)) {
                    return;
                }
                applicationBeans.addAll(getChildrenApplicationBeans(value, applications));
            });
        }
        return applicationBeans;
    }
}

