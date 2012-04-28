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

public class BadWordyExpressionException extends RuntimeException {
    public BadWordyExpressionException(String wordyExpression) {
        this(wordyExpression, null);
    }

    public BadWordyExpressionException(String wordyExpression, Throwable cause) {
        super("A problem occurred when trying to parse the following wordy expression [" + wordyExpression + "]", cause);
    }
}
