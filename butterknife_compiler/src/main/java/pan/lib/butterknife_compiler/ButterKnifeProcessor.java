package pan.lib.butterknife_compiler;


import com.google.auto.service.AutoService;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeSpec;

import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

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
    private Map<String, BindViewModel> map = new HashMap<>();  //注解分类 一个class对应多个注解

    @Override
    public synchronized void init(ProcessingEnvironment processingEnv) {
        super.init(processingEnv);
        environment = processingEnv;
        messager = processingEnv.getMessager();
    }

    @Override
    public boolean process(Set<? extends TypeElement> set, RoundEnvironment roundEnvironment) {
        Set<? extends Element> elementsSet = roundEnvironment.getElementsAnnotatedWith(BindView.class);  //返回所有被注解了@BindView的元素的列表
        if (set.isEmpty()) return true;

        classifyElements(elementsSet);

        generatingJavaClass();

        return true;
    }


    /*
     * 区分注解该属于哪个类
     * */
    private void classifyElements(Set<? extends Element> elementsSet) {
        for (Element element : elementsSet) {
            String className = getFullClassName(element);
            BindViewModel bindViewModel = map.get(className);
            if (bindViewModel == null) {
                bindViewModel = new BindViewModel(element, environment.getElementUtils());
                map.put(className, bindViewModel);
            } else {
                bindViewModel.addBindView(element);
            }
        }
    }

    /**
     * 获取注解属性的完整类名
     */
    private String getFullClassName(Element element) {
        TypeElement typeElement = (TypeElement) element.getEnclosingElement();
        String packageName = environment.getElementUtils().getPackageOf(typeElement).getQualifiedName().toString();
        return packageName + "." + typeElement.getSimpleName().toString();
    }


    //使用java poet生成绑定视图代码
    private void generatingJavaClass() {
        map.forEach((classPath, bindViewModel) -> {

            //JavaPoet获得class的重要的方法
            ClassName activityClass = ClassName.get(bindViewModel.getPackageName(), bindViewModel.getTopClassName());


            MethodSpec.Builder constructorBuilder = MethodSpec.constructorBuilder()
                    .addModifiers(Modifier.PUBLIC)
                    .addParameter(activityClass, "activity")
                    .addStatement("this.activity=activity");

            bindViewModel.getBindViewList().forEach(element -> {
                BindView bindView = element.getAnnotation(BindView.class);//获得注解对象
                constructorBuilder.addStatement("activity.$L=activity.findViewById($L)", element.getSimpleName(), bindView.value());
            });


            TypeSpec classType = TypeSpec.classBuilder(bindViewModel.getTopClassName() + "$ViewBinding")
                    .addModifiers(Modifier.PUBLIC, Modifier.FINAL)
                    .addMethod(constructorBuilder.build())
                    .addField(activityClass, "activity", Modifier.PRIVATE)
                    .build();

            try {
                JavaFile.builder(bindViewModel.getPackageName(), classType)
                        .build().writeTo(environment.getFiler());
            } catch (IOException e) {
                e.printStackTrace();
            }
        });


    }

    private void printLog(String log) {
        messager.printMessage(Diagnostic.Kind.ERROR, log);
    }

    @Override
    public Set<String> getSupportedAnnotationTypes() {
        Set<String> annotations = new LinkedHashSet<>();
        annotations.add(BindView.class.getCanonicalName());
        return annotations;
    }


}
