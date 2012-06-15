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

public class BetweenTimeParser implements WordyToCronParser {
    private static final Pattern PATTERN = Pattern.compile("between ([0-9]{1,2}(\\s*(am|pm))?(\\-| and )[0-9]{1,2})(\\s*(pm|am))? every ([0-9]{1,2}) (minute|second|hour)", Pattern.CASE_INSENSITIVE);

    public boolean isMatch(String wordyExpression) {
        return wordyExpression.toLowerCase().contains("between");
    }

    public String parse(String wordyExpression) {
        CronBuilder cron = new CronBuilder();
        Matcher matcher = PATTERN.matcher(wordyExpression);
        if (matcher.find()) {

            int firstSideOfDayGroup = 2;
            int secondSideOfDayGroup = 5;
            int intervalGroup = 7;
            int intervalUnitGroup = 8;
            int timeRangeGroup = 1;

            SideOfDay firstSideOfDay = parseSideOfDay(matcher, firstSideOfDayGroup);
            SideOfDay secondSideOfDay = parseSideOfDay(matcher, secondSideOfDayGroup);
            if (firstSideOfDay != null && secondSideOfDay == null) {
                throw new BadWordyExpressionException("If you want to use the syntax providing (AM|PM) please provide for both hours (1 am and 10 pm) or just supply for the trailing hour (1 and 10 pm|1-10pm)");
            }

            if (firstSideOfDay == null) {
                firstSideOfDay = secondSideOfDay;
            }
            TimeUnit unit = TimeUnit.valueOf(matcher.group(intervalUnitGroup).toUpperCase());
            String interval = matcher.group(intervalGroup);


            cron.second("0");
            if (unit == TimeUnit.SECOND) {
                cron.interval(interval);
            }


            if (unit == TimeUnit.MINUTE) {
                cron.minute("0").interval(interval);
            } else if (unit != TimeUnit.SECOND) {
                cron.minute("0");
            }

            String hourRange = calculateHourRange(matcher.group(timeRangeGroup), firstSideOfDay, secondSideOfDay);
            cron.hour(hourRange);
            if (unit == TimeUnit.HOUR) {
                cron.interval(interval);
            }
            return cron.toString();
        }
        throw new BadWordyExpressionException("Could not parse the expression. :(");
    }

    private SideOfDay parseSideOfDay(Matcher matcher, int group) {
        return matcher.group(group) == null ? null : SideOfDay.valueOf(matcher.group(group).toUpperCase().trim());
    }

    private String calculateHourRange(String hourRange, SideOfDay firstSideOfDay, SideOfDay secondSideOfDay) {
        String cleanedHourRange = hourRange.toLowerCase().replaceAll(" and ", "-").replaceAll("[a-zA-Z]", "");
        if (secondSideOfDay == null) {
            return cleanedHourRange;
        }

        String[] hours = cleanedHourRange.split("-");
        String newRange = String.valueOf(firstSideOfDay.convertToMilitaryHours(Integer.parseInt(hours[0].trim())));
        newRange += "-";
        newRange += String.valueOf(secondSideOfDay.convertToMilitaryHours(Integer.parseInt(hours[1])));

        return newRange;
    }


}
