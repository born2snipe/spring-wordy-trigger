/**
 *
 * Copyright to the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in
 * compliance with the License. You may obtain a copy of the License at:
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License is
 * distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and limitations under the License.
 */

package org.springframework.scheduling.wordy;

import java.io.IOException;
import java.io.InputStream;

public class BadWordyExpressionException extends RuntimeException {
    public BadWordyExpressionException(String wordyExpression) {
        this(wordyExpression, null);
    }

    public BadWordyExpressionException(String wordyExpression, Throwable cause) {
        super("Problem: A problem occurred when trying to parse the following wordy expression [" + wordyExpression + "]\n\nInformation:\n" + readReadMeFile() + "\n\n", cause);
    }

    private static String readReadMeFile() {
        StringBuilder builder = new StringBuilder();

        InputStream input = Thread.currentThread().getContextClassLoader().getResourceAsStream("org/springframework/scheduling/wordy/README.txt");
        byte[] buffer = new byte[1024];
        int len = -1;

        try {
            while ((len = input.read(buffer)) != -1) {
                builder.append(new String(buffer, 0, len));
            }
        } catch (IOException e) {
            throw new RuntimeException("A problem occurred while trying to read the README.txt file for friendlier exception messages");
        } finally {
            try {
                input.close();
            } catch (IOException e) {
            }
        }

        return builder.toString();
    }
}
