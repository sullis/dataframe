

package org.jetbrains.kotlinx.dataframe;

import com.intellij.testFramework.TestDataPath;
import org.jetbrains.kotlin.test.util.KtTestUtil;
import org.jetbrains.kotlin.test.TestMetadata;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.regex.Pattern;

/** This class is generated by {@link org.jetbrains.kotlinx.dataframe.GenerateTestsKt}. DO NOT MODIFY MANUALLY */
@SuppressWarnings("all")
@TestMetadata("testData/box")
@TestDataPath("$PROJECT_ROOT")
public class ExplainerBlackBoxCodegenTestGenerated extends AbstractExplainerBlackBoxCodegenTest {
    @Test
    public void testAllFilesPresentInBox() throws Exception {
        KtTestUtil.assertAllTestsPresentByMetadataWithExcluded(this.getClass(), new File("testData/box"), Pattern.compile("^(.+)\\.kt$"), null, true);
    }

    @Test
    @TestMetadata("any.kt")
    public void testAny() throws Exception {
        runTest("testData/box/any.kt");
    }

    @Test
    @TestMetadata("df.kt")
    public void testDf() throws Exception {
        runTest("testData/box/df.kt");
    }

    @Test
    @TestMetadata("test.kt")
    public void testTest() throws Exception {
        runTest("testData/box/test.kt");
    }
}
