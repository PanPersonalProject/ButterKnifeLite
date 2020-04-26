package pan.lib.butterknife_compiler;


import com.google.auto.service.AutoService;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeSpec;

import java.io.IOException;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.logging.Logger;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Messager;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;
import javax.tools.Diagnostic;

import pan.lib.butterknife_annotation.BindView;

/**
 * Author:         pan qi
 * CreateDate:     2020/4/24 15:31
 */

/*
 * AutoService自动生成注册注解处理器
 * 生成在:butterknife_compiler/build/classes/java/main/META-INF/services/javax.annotation.processing.Processor
 */
@AutoService(Processor.class)
@SupportedSourceVersion(SourceVersion.RELEASE_8)
public class ButterKnifeProcessor extends AbstractProcessor {
    private Messager messager;
    private ProcessingEnvironment environment;

    @Override
    public synchronized void init(ProcessingEnvironment processingEnv) {
        super.init(processingEnv);
        environment = processingEnv;
        messager = processingEnv.getMessager();
        messager.printMessage(Diagnostic.Kind.NOTE, "初始化");

    }

    @Override
    public boolean process(Set<? extends TypeElement> set, RoundEnvironment roundEnvironment) {
        Set<? extends Element> elementsSet = roundEnvironment.getElementsAnnotatedWith(BindView.class);
        if (elementsSet == null) return true;
        for (Element element : elementsSet) {
            messager.printMessage(Diagnostic.Kind.NOTE, element.toString());
        }


        MethodSpec main = MethodSpec.methodBuilder("main")
                .addModifiers(Modifier.PUBLIC, Modifier.STATIC)
                .returns(void.class)
                .addParameter(String[].class, "args")
                .addStatement("$T.out.println($S)", System.class, "Hello, JavaPoet!")
                .build();

        TypeSpec helloWorld = TypeSpec.classBuilder("HelloWorld")
                .addModifiers(Modifier.PUBLIC, Modifier.FINAL)
                .addMethod(main)
                .build();

//        String packageName = elementUtils.getPackageOf(enclosingElement).getQualifiedName().toString();

        try {
            //代码的文件会生成在 app/build/generated/source/kapt
            JavaFile.builder("pan.lib.butterknifelite", helloWorld)
                    .addFileComment("编译时生成的xxxx_ViewBinding文件")
                    .build().writeTo(environment.getFiler());
        } catch (IOException e) {
            e.printStackTrace();
        }


        return true;
    }

    @Override
    public Set<String> getSupportedAnnotationTypes() {
        Set<String> annotations = new LinkedHashSet<>();
        annotations.add(BindView.class.getCanonicalName());
        return annotations;
    }

}
