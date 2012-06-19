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

public class OnDayParser implements WordyToCronEvaluator {
    private static final Pattern PATTERN = Pattern.compile("(^on (.+?) (at|every))|(.+ on (.+$))", Pattern.CASE_INSENSITIVE);

    public void evaluate(String wordyExpression, CronBuilder cron) {
        Matcher matcher = PATTERN.matcher(wordyExpression.trim());
        if (matcher.find()) {
            if (isValidOnDayExpression(wordyExpression)) {
                throw new BadWordyExpressionException("The 'on' syntax requires a 'every' or an 'at' definition");
            }

            String dayValue = matcher.group(2);
            if (dayValue == null) {
                dayValue = matcher.group(5);
            }

            String cleanedValue = cleanTheDayValue(dayValue);
            cron.value(cleanedValue, TimeUnit.DAY_OF_WEEK);
        }
    }

    private String cleanTheDayValue(String originalValue) {
        String cleanedValue = originalValue;
        cleanedValue = cleanedValue.toUpperCase().replaceAll("THRU", "-");
        cleanedValue = cleanedValue.replaceAll("\\s", "");
        return cleanedValue;
    }

    private boolean isValidOnDayExpression(String wordyExpression) {
        String lowercaseExpression = wordyExpression.toLowerCase();
        return !lowercaseExpression.contains("on") && !lowercaseExpression.contains("every");
    }
}
