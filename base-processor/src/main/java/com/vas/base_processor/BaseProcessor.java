package com.vas.base_processor;

import com.vas.base_annotation.ClassObject;
import com.vas.base_annotation.Ignore;
import com.vas.base_processor.exceptions.AnnotationException;
import com.vas.base_processor.operations.ClassObjectValidator;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Filer;
import javax.annotation.processing.Messager;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import javax.lang.model.util.Elements;

public class BaseProcessor extends AbstractProcessor {
    private Filer filer;
    private Messager messager;
    private Elements elements;
    private SourceVersion sourceVersion;

    @Override
    public synchronized void init(ProcessingEnvironment processingEnvironment) {
        super.init(processingEnvironment);
        filer = processingEnvironment.getFiler();
        messager = processingEnvironment.getMessager();
        elements = processingEnvironment.getElementUtils();
        sourceVersion = processingEnvironment.getSourceVersion();
    }

    @Override
    public boolean process(Set<? extends TypeElement> set, RoundEnvironment roundEnvironment) {
        System.out.printf("\n-----------PROCESS_START-----------");
        for (Element element : roundEnvironment.getElementsAnnotatedWith(ClassObject.class)) {
            System.out.printf("\n--CLASS_OBJECT--" + element);
            try {
                ClassObjectValidator.validateElementClass(element);
            } catch (AnnotationException ae) {
                ae.printStackTrace();
            }
        }
        for (Element element : roundEnvironment.getRootElements()) {
            System.out.printf("\n--ROOT_ELEMENT--" + element);
        }
        System.out.printf("\n------------PROCESS_END------------\n");
        return true;
    }

    @Override
    public Set<String> getSupportedAnnotationTypes() {
        Set<String> types = new HashSet<>();
        Collections.addAll(types,
                ClassObject.class.getCanonicalName(),
                Ignore.class.getCanonicalName()
        );
        return types;
    }

    @Override
    public SourceVersion getSupportedSourceVersion() {
        SourceVersion sVersion = SourceVersion.latestSupported();
        System.out.printf("\nSupportedSourceVersion:" + sVersion + " \n");
        return sVersion;
    }

}
