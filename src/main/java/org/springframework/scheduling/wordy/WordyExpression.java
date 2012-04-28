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

public class WordyExpression {
    private static final Pattern EVERY_PATTERN = Pattern.compile("every ([0-9]+) (hour|minute|second)", Pattern.CASE_INSENSITIVE);
    private static final Pattern AT_PATTERN = Pattern.compile("at ([0-9]{1,2}) (am|pm)", Pattern.CASE_INSENSITIVE);

    private String expression;

    public WordyExpression(String expression) {
        this.expression = expression;
    }

    public String toCron() {
        try {
            String cronExpression = "";

            if (isMatch(EVERY_PATTERN, expression)) {
                cronExpression = buildFromEveryExpression();
            } else {
                cronExpression = buildFromAtExpression();
            }

            if (cronExpression.trim().length() == 0) {
                throw new BadWordyExpressionException(expression);
            }

            return cronExpression;
        } catch (Exception e) {
            throw new BadWordyExpressionException(expression, e);
        }
    }

    private String buildFromAtExpression() {
        StringBuilder cronExpression = new StringBuilder();
        Matcher matcher = AT_PATTERN.matcher(expression);
        if (matcher.find()) {
            int hour = Integer.parseInt(matcher.group(1));
            SideOfDay sideOfDay = SideOfDay.valueOf(matcher.group(2).toUpperCase());
            cronExpression.append("0 0 ");
            cronExpression.append(hour + sideOfDay.hourOffset);
            cronExpression.append(" * * ?");
        }

        return cronExpression.toString();
    }

    private String buildFromEveryExpression() {
        StringBuilder cronExpression = new StringBuilder();
        Matcher matcher = EVERY_PATTERN.matcher(expression);
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

    private boolean isMatch(Pattern pattern, String wordyExpression) {
        return pattern.matcher(wordyExpression).find();
    }

    private static enum SideOfDay {
        AM(0), PM(12);

        int hourOffset;

        private SideOfDay(int hourOffset) {
            this.hourOffset = hourOffset;
        }
    }

    private static enum Unit {
        SECOND(0), MINUTE(1), HOUR(2);

        int cronPosition;

        private Unit(int cronPosition) {
            this.cronPosition = cronPosition;
        }
    }
}
