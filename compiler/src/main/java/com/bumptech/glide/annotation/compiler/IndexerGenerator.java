package com.bumptech.glide.annotation.compiler;

import com.bumptech.glide.annotation.GlideModule;
import com.squareup.javapoet.AnnotationSpec;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.TypeSpec;
import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.List;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;

/**
 * Generates an empty class with an annotation containing the class names of one or more
 * LibraryGlideModules and/or one or more GlideExtensions.
 *
 * <p>We use a separate class so that LibraryGlideModules and GlideExtensions written in libraries
 * can be bundled into an AAR and later retrieved by the annotation processor when it processes the
 * AppGlideModule in an application.
 *
 * <p>The output file generated by this class with a LibraryGlideModule looks like this:
 * <pre>
 * <code>
 *  {@literal @com.bumptech.glide.annotation.compiler.Index(}
 *      modules = "com.bumptech.glide.integration.okhttp3.OkHttpLibraryGlideModule"
 *  )
 *  public class Indexer_GlideModule_com_bumptech_glide_integration_okhttp3_OkHttpLibraryGlideModule
 *  {
 *  }
 * </code>
 * </pre>
 *
 * <p>The output file generated by this class with a GlideExtension looks like this:
 * <pre>
 * <code>
 *  {@literal @com.bumptech.glide.annotation.compiler.Index(}
 *      extensions = "com.bumptech.glide.integration.gif.GifOptions"
 *  )
 *  public class Indexer_GlideExtension_com_bumptech_glide_integration_gif_GifOptions {
 *  }
 * </code>
 * </pre>
 * </p>
 */
final class IndexerGenerator {
  private static final String INDEXER_NAME_PREFIX = "GlideIndexer_";
  private final ProcessorUtil processorUtil;

  IndexerGenerator(ProcessorUtil processorUtil) {
    this.processorUtil = processorUtil;
  }

  TypeSpec generate(List<TypeElement> types) {
    List<TypeElement> modules =  new ArrayList<>();
    List<TypeElement> extensions = new ArrayList<>();
    for (TypeElement element : types) {
      if (processorUtil.isLibraryGlideModule(element)) {
        modules.add(element);
      } else {
        throw new IllegalArgumentException("Unrecognized type: " + element);
      }
    }
    if (!modules.isEmpty() && !extensions.isEmpty()) {
      throw new IllegalArgumentException("Given both modules and extensions, expected one or the "
          + "other. Modules: " + modules + " Extensions: " + extensions);
    }
    return generate(types, GlideModule.class);
  }

  private static TypeSpec generate(List<TypeElement> libraryModules,
      Class<? extends Annotation> annotation) {
    AnnotationSpec.Builder annotationBuilder =
        AnnotationSpec.builder(Index.class);

    String value = getAnnotationValue(annotation);
    for (TypeElement childModule : libraryModules) {
      annotationBuilder.addMember(value, "$S", ClassName.get(childModule).toString());
    }

    StringBuilder indexerName = new StringBuilder(
        INDEXER_NAME_PREFIX + annotation.getSimpleName() + "_");
    for (TypeElement element : libraryModules) {
      indexerName.append(element.getQualifiedName().toString().replace(".", "_"));
      indexerName.append("_");
    }
    indexerName = new StringBuilder(indexerName.substring(0, indexerName.length() - 1));

    return TypeSpec.classBuilder(indexerName.toString())
        .addAnnotation(annotationBuilder.build())
        .addModifiers(Modifier.PUBLIC)
        .build();
  }

  private static String getAnnotationValue(Class<? extends Annotation> annotation) {
    if (annotation == GlideModule.class) {
      return "modules";
    } else {
      throw new IllegalArgumentException("Unrecognized annotation: " + annotation);
    }
  }
}
