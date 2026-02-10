package com.generator;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class Generator {

    private static final String PACKAGE_NAME = "com/pivovarit/forc";
    private static final String API_FILE_NAME = "ForComprehension.java";
    private static final String TEST_FILE_NAME = "ForComprehensionTest.java";

    private static final int ARITY = 8;

    void main(String[] args) throws IOException {
        var dir = Files.createDirectories(Path.of(args[0], PACKAGE_NAME));
        var testDir = Files.createDirectories(Path.of(args[1], PACKAGE_NAME));

        for (int i = 1; i <= ARITY; i++) {
            Files.writeString(dir.resolve("Function%d.java".formatted(i)), header() + FunctionTypeGenerator.generate(i));
        }

        Files.writeString(dir.resolve(API_FILE_NAME), header() + ForComprehensionGenerator.generate(ARITY));
        Files.writeString(testDir.resolve(TEST_FILE_NAME), header() + ForComprehensionTestGenerator.generate(ARITY));
    }

    private static String header() {
        return """
          /*
           * Copyright 2014-2026 Grzegorz Piwowarek, https://4comprehension.com/
           *
           * Licensed under the Apache License, Version 2.0 (the "License");
           * you may not use this file except in compliance with the License.
           * You may obtain a copy of the License at
           *
           * https://www.apache.org/licenses/LICENSE-2.0
           *
           * Unless required by applicable law or agreed to in writing, software
           * distributed under the License is distributed on an "AS IS" BASIS,
           * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
           * See the License for the specific language governing permissions and
           * limitations under the License.
           */
          """;
    }
}
