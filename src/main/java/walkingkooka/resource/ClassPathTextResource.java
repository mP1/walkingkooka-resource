/*
 * Copyright 2020 Miroslav Pokorny (github.com/mP1)
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

import walkingkooka.reflect.ClassName;
import walkingkooka.text.CharSequences;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.Objects;

/**
 * Loads the text within a file using {@link Class#getResourceAsStream(String)}
 */
public final class ClassPathTextResource implements TextResource {

    /**
     * Factory that creates a new {@link ClassPathTextResource}
     */
    static ClassPathTextResource with(final String filename, final Class<?> klass) {
        return new ClassPathTextResource(
            CharSequences.failIfNullOrEmpty(filename, "filename"),
            Objects.requireNonNull(klass, "class")
        );
    }

    /**
     * Private ctor use factory.
     */
    private ClassPathTextResource(final String filename, final Class<?> klass) {
        this.filename = filename;
        this.klass = klass;
    }

    /**
     * Loads the text file
     */
    @Override
    public String text() throws TextResourceException {
        final String filename = this.filename;

        try {
            final InputStream inputStream = this.klass.getResourceAsStream(filename);
            if (null == inputStream) {
                throw new TextResourceException("Unable to find " + CharSequences.quoteAndEscape(filename));
            }
            final StringBuilder text = new StringBuilder();
            final char[] buffer = new char[4096];

            try (final InputStream inputStream2 = inputStream) {
                try (final InputStreamReader reader = new InputStreamReader(inputStream2, Charset.defaultCharset())) {
                    for (; ; ) {
                        final int count = reader.read(buffer);
                        if (-1 == count) {
                            break;
                        }
                        text.append(buffer, 0, count);
                    }
                }
            }
            return text.toString();
        } catch (final IOException fail) {
            throw new TextResourceException("Failed to read " + CharSequences.quoteAndEscape(filename));
        }
    }

    private final String filename;
    private final Class<?> klass;

    @Override
    public String toString() {
        return ClassName.fromClass(this.klass).nameWithoutPackage() + "/" + this.filename;
    }
}
