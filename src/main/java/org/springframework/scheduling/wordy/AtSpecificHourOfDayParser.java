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

public class AtSpecificHourOfDayParser implements WordyToCronEvaluator {
    private static final Pattern AT_TIME_PATTERN = Pattern.compile("^at ([0-9]{1,2}):([0-9]{2})( (am|pm))?$", Pattern.CASE_INSENSITIVE);
    private static final Pattern AT_HOUR_PATTERN = Pattern.compile("at\\s+([0-9]{1,2})\\s+(am|pm)", Pattern.CASE_INSENSITIVE);


    public void evaluate(String wordyExpression, CronBuilder cron) {
        String minutes = "0";
        String hour = "0";

        boolean foundMatch = false;
        String cleanedExpression = wordyExpression.trim().replaceAll("\\s{2,}", " ");
        Matcher matcher = AT_HOUR_PATTERN.matcher(cleanedExpression);
        if (matcher.find()) {
            hour = adjustHours(matcher.group(1), matcher.group(2));
            foundMatch = true;
        } else {
            matcher = AT_TIME_PATTERN.matcher(cleanedExpression);
            if (matcher.find()) {
                hour = matcher.group(1);
                minutes = matcher.group(2).replaceAll("0([0-9])", "$1");
                String sideOfDayStr = matcher.group(4);

                if (Integer.parseInt(hour) > 12 && sideOfDayStr != null) {
                    throw new BadWordyExpressionException("Please do not provide AM or PM when using military time");
                }

                hour = adjustHours(hour, sideOfDayStr);
                foundMatch = true;
            }
        }

        if (foundMatch) {
            if (minutes.length() == 3) {
                throw new BadWordyExpressionException("The minutes can not be 3 digits");
            } else if (cleanedExpression.toLowerCase().contains("every")) {
                throw new BadWordyExpressionException("The 'at' syntax does not allow an 'every' definition.");
            }
            cron.second("0").minute(minutes).hour(hour);
        }
    }

    private String adjustHours(String hour, String sideOfDayStr) {
        if (sideOfDayStr == null) {
            return hour;
        }

        int hours = Integer.parseInt(hour);
        SideOfDay sideOfDay = SideOfDay.valueOf(sideOfDayStr.toUpperCase());
        return String.valueOf(sideOfDay.convertToMilitaryHours(hours));
    }
}
