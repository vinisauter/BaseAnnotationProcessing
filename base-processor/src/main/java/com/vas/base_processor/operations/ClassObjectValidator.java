package com.vas.base_processor.operations;

import com.vas.base_annotation.CaseFormat;
import com.vas.base_annotation.ClassObject;
import com.vas.base_annotation.Ignore;
import com.vas.base_processor.Utils;
import com.vas.base_processor.exceptions.AnnotationException;

import java.text.MessageFormat;
import java.util.Arrays;
import java.util.Set;

import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;

import static javax.lang.model.element.ElementKind.CLASS;
import static javax.lang.model.element.Modifier.PRIVATE;

/**
 * Created by user on 04/10/2018.
 */

public class ClassObjectValidator {
    public static void validateElementClass(Element elementBase) throws AnnotationException {
        // get annotation of the specified type if such an annotation is present, else null.
        ClassObject annotation = elementBase.getAnnotation(ClassObject.class);
        String value = annotation.value();
        CaseFormat caseFormat = annotation.columnCaseFormat();
        if (elementBase.getKind() != CLASS) {
            throw new AnnotationException("Can only be applied to class.");
        }
        TypeElement typeElement = (TypeElement) elementBase;
        boolean isObject = Utils.instanceOf(typeElement, "java.lang.Object");

        if (elementBase.getModifiers().contains(PRIVATE)) {
            throw new AnnotationException(MessageFormat.format("{0} {1} may not be applied to private classes. ({2})", ClassObject.class.getSimpleName(), typeElement.getQualifiedName(), elementBase.getSimpleName()));
        }

        for (Element elementEnclosed : elementBase.getEnclosedElements()) {
            if (elementEnclosed.getKind() == ElementKind.FIELD) {
                validateElementField(elementEnclosed);
            }
        }
    }

    public static void validateElementField(Element elementEnclosed) throws AnnotationException {
        Ignore ignore = elementEnclosed.getAnnotation(Ignore.class);
        if (ignore == null) {
            ElementKind fieldKind = elementEnclosed.getKind();
            Set<Modifier> fieldModifiers = elementEnclosed.getModifiers();
            System.out.printf(MessageFormat.format(
                    "\n    EnclosedElement {0} {1} {2} {3} {4} {5}",
                    fieldKind,
                    ignore != null ? "ignore" : " - ",
                    Arrays.toString(fieldModifiers.toArray()),
                    elementEnclosed.getSimpleName().toString(),
                    elementEnclosed.asType(),
                    elementEnclosed.asType().getKind().isPrimitive() ? "primitive" : ""
            ));
        }
    }
}
