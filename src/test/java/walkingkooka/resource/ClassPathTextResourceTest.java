/*
 * Copyright 2019 Miroslav Pokorny (github.com/mP1)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */

package walkingkooka.resource;

import org.junit.jupiter.api.Test;
import walkingkooka.reflect.ClassTesting2;
import walkingkooka.reflect.JavaVisibility;

import static org.junit.jupiter.api.Assertions.assertThrows;

public final class ClassPathTextResourceTest implements ClassTesting2<ClassPathTextResource> {

    @Test
    public void testText() {
        final ClassPathTextResource loader = ClassPathTextResource.with(ClassPathTextResourceTest.class.getSimpleName() + "/testLoad.txt", this.getClass());
        this.checkEquals("ABC123", loader.text());
    }

    @Test
    public void testTextFails() {
        assertThrows(TextResourceException.class, () -> ClassPathTextResource.with(ClassPathTextResourceTest.class.getSimpleName() + "/???.txt", this.getClass()).text());
    }

    // ClassTesting.....................................................................................................

    @Override
    public Class<ClassPathTextResource> type() {
        return ClassPathTextResource.class;
    }

    @Override
    public JavaVisibility typeVisibility() {
        return JavaVisibility.PUBLIC;
    }
}
