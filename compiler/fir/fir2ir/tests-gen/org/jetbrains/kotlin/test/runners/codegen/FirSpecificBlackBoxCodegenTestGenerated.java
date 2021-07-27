/*
 * Copyright 2010-2021 JetBrains s.r.o. and Kotlin Programming Language contributors.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the license/LICENSE.txt file.
 */

package org.jetbrains.kotlin.test.runners.codegen;

import com.intellij.testFramework.TestDataPath;
import org.jetbrains.kotlin.test.util.KtTestUtil;
import org.jetbrains.kotlin.test.TargetBackend;
import org.jetbrains.kotlin.test.TestMetadata;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.regex.Pattern;

/** This class is generated by {@link GenerateNewCompilerTests.kt}. DO NOT MODIFY MANUALLY */
@SuppressWarnings("all")
@TestMetadata("compiler/fir/fir2ir/testData/codegen/box")
@TestDataPath("$PROJECT_ROOT")
public class FirSpecificBlackBoxCodegenTestGenerated extends AbstractFirBlackBoxCodegenTest {
    @Test
    public void testAllFilesPresentInBox() throws Exception {
        KtTestUtil.assertAllTestsPresentByMetadataWithExcluded(this.getClass(), new File("compiler/fir/fir2ir/testData/codegen/box"), Pattern.compile("^(.+)\\.kt$"), null, TargetBackend.JVM_IR, true);
    }

    @Test
    @TestMetadata("sample.kt")
    public void testSample() throws Exception {
        runTest("compiler/fir/fir2ir/testData/codegen/box/sample.kt");
    }

    @Nested
    @TestMetadata("compiler/fir/fir2ir/testData/codegen/box/properties")
    @TestDataPath("$PROJECT_ROOT")
    public class Properties {
        @Test
        public void testAllFilesPresentInProperties() throws Exception {
            KtTestUtil.assertAllTestsPresentByMetadataWithExcluded(this.getClass(), new File("compiler/fir/fir2ir/testData/codegen/box/properties"), Pattern.compile("^(.+)\\.kt$"), null, TargetBackend.JVM_IR, true);
        }

        @Nested
        @TestMetadata("compiler/fir/fir2ir/testData/codegen/box/properties/publicType")
        @TestDataPath("$PROJECT_ROOT")
        public class PublicType {
            @Test
            @TestMetadata("abstractExposingGetters.kt")
            public void testAbstractExposingGetters() throws Exception {
                runTest("compiler/fir/fir2ir/testData/codegen/box/properties/publicType/abstractExposingGetters.kt");
            }

            @Test
            public void testAllFilesPresentInPublicType() throws Exception {
                KtTestUtil.assertAllTestsPresentByMetadataWithExcluded(this.getClass(), new File("compiler/fir/fir2ir/testData/codegen/box/properties/publicType"), Pattern.compile("^(.+)\\.kt$"), null, TargetBackend.JVM_IR, true);
            }

            @Test
            @TestMetadata("propertyOverrides.kt")
            public void testPropertyOverrides() throws Exception {
                runTest("compiler/fir/fir2ir/testData/codegen/box/properties/publicType/propertyOverrides.kt");
            }

            @Test
            @TestMetadata("propertyWithoutOverrides.kt")
            public void testPropertyWithoutOverrides() throws Exception {
                runTest("compiler/fir/fir2ir/testData/codegen/box/properties/publicType/propertyWithoutOverrides.kt");
            }

            @Test
            @TestMetadata("samplePublicPropertyType.kt")
            public void testSamplePublicPropertyType() throws Exception {
                runTest("compiler/fir/fir2ir/testData/codegen/box/properties/publicType/samplePublicPropertyType.kt");
            }
        }
    }
}
