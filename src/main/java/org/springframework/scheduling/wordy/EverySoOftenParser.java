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

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class EverySoOftenParser implements WordyToCronParser {
    private static final Pattern EVERY_PATTERN = Pattern.compile("every ([0-9]+) (hour|minute|second)", Pattern.CASE_INSENSITIVE);

    public boolean isMatch(String wordyExpression) {
        return EVERY_PATTERN.matcher(wordyExpression).find();
    }

    public String parse(String wordyExpression) {
        StringBuilder cronExpression = new StringBuilder();
        Matcher matcher = EVERY_PATTERN.matcher(wordyExpression);
        if (matcher.find()) {
            int unitSize = Integer.parseInt(matcher.group(1));
            Unit unit = Unit.valueOf(matcher.group(2).toUpperCase());

            for (int i = 0; i < Unit.values().length; i++) {
                if (i == unit.cronPosition) {
                    cronExpression.append("0/").append(unitSize).append(" ");
                } else if (i < unit.cronPosition) {
                    cronExpression.append("0").append(" ");
                } else {
                    cronExpression.append("*").append(" ");
                }
            }

            cronExpression.append("* * ?");
        }
        return cronExpression.toString();

    }

    private static enum Unit {
        SECOND(0), MINUTE(1), HOUR(2);

        int cronPosition;

        private Unit(int cronPosition) {
            this.cronPosition = cronPosition;
        }
    }

}
