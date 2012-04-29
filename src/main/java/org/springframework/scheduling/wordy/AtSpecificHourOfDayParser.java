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

public class AtSpecificHourOfDayParser implements WordyToCronParser {
    private static final Pattern AT_TIME_PATTERN = Pattern.compile("^at ([0-9]{1,2}):([0-9]{2})( (am|pm))?$", Pattern.CASE_INSENSITIVE);
    private static final Pattern AT_HOUR_PATTERN = Pattern.compile("at ([0-9]{1,2}) (am|pm)", Pattern.CASE_INSENSITIVE);


    public boolean isMatch(String wordyExpression) {
        return AT_HOUR_PATTERN.matcher(wordyExpression).find()
                || AT_TIME_PATTERN.matcher(wordyExpression).find();
    }

    public String parse(String wordyExpression) {
        StringBuilder cronExpression = new StringBuilder();
        String minutes = "0";
        String hour = "0";

        Matcher matcher = AT_HOUR_PATTERN.matcher(wordyExpression);
        if (matcher.find()) {
            hour = adjustHours(matcher.group(1), matcher.group(2));
        } else {
            matcher = AT_TIME_PATTERN.matcher(wordyExpression);
            if (matcher.find()) {
                hour = matcher.group(1);
                minutes = matcher.group(2).replaceAll("0([0-9])", "$1");
                String sideOfDayStr = matcher.group(4);

                if (Integer.parseInt(hour) > 12 && sideOfDayStr != null) {
                    throw new IllegalArgumentException("Please do not provide AM or PM when using military time");
                }

                hour = adjustHours(hour, sideOfDayStr);
            }
        }

        if (minutes.length() == 3) {
            throw new IllegalArgumentException("The minutes can not be 3 digits");
        }

        cronExpression.append("0 ");
        cronExpression.append(minutes).append(" ").append(hour).append(" ");
        cronExpression.append("* * ?");

        return cronExpression.toString();
    }

    private String adjustHours(String hour, String sideOfDayStr) {
        if (sideOfDayStr == null) {
            return hour;
        }

        int hours = Integer.parseInt(hour);
        SideOfDay sideOfDay = SideOfDay.valueOf(sideOfDayStr.toUpperCase());

        if (hours == 12) {
            switch (sideOfDay) {
                case AM:
                    return "0";
                case PM:
                    return "12";
            }
        }

        hours += sideOfDay.hourOffset;
        hour = String.valueOf(hours);
        return hour;
    }

    private static enum SideOfDay {
        AM(0), PM(12);

        int hourOffset;

        private SideOfDay(int hourOffset) {
            this.hourOffset = hourOffset;
        }
    }

}
