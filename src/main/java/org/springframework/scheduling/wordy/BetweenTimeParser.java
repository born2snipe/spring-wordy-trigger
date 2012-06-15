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
    private static final Pattern PATTERN = Pattern.compile("between ([0-9]{1,2}(\\-| and )[0-9]{1,2}) every ([0-9]{1,2}) (minute|second|hour)");

    public boolean isMatch(String wordyExpression) {
        return wordyExpression.contains("between");
    }

    public String parse(String wordyExpression) {

        Matcher matcher = PATTERN.matcher(wordyExpression);
        matcher.find();

        int intervalGroup = 3;
        int intervalUnitGroup = 4;
        int timeRangeGroup = 1;

        TimeUnit unit = TimeUnit.valueOf(matcher.group(intervalUnitGroup).toUpperCase());
        String interval = matcher.group(intervalGroup);

        StringBuilder cron = new StringBuilder();
        if (unit == TimeUnit.SECOND) {
            cron.append("0/").append(interval);
        } else {
            cron.append("0");
        }
        cron.append(" ");

        if (unit == TimeUnit.MINUTE) {
            cron.append("0/").append(interval).append(" ");
        } else {
            if (unit == TimeUnit.SECOND) {
                cron.append("*");
            } else {
                cron.append("0");
            }
            cron.append(" ");
        }

        cron.append(matcher.group(timeRangeGroup).replaceAll(" and ", "-"));
        if (unit == TimeUnit.HOUR) {
            cron.append("/").append(interval);
        }

        cron.append(" * * ?");

        return cron.toString();
    }


}
