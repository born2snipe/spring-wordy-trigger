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
    private static final Pattern AT_PATTERN = Pattern.compile("at ([0-9]{1,2}) (am|pm)", Pattern.CASE_INSENSITIVE);


    public boolean isMatch(String wordyExpression) {
        return AT_PATTERN.matcher(wordyExpression).find();
    }

    public String parse(String wordyExpression) {
        StringBuilder cronExpression = new StringBuilder();
        Matcher matcher = AT_PATTERN.matcher(wordyExpression);
        if (matcher.find()) {
            int hour = Integer.parseInt(matcher.group(1));
            SideOfDay sideOfDay = SideOfDay.valueOf(matcher.group(2).toUpperCase());
            cronExpression.append("0 0 ");
            cronExpression.append(hour + sideOfDay.hourOffset);
            cronExpression.append(" * * ?");
        }

        return cronExpression.toString();
    }

    private static enum SideOfDay {
        AM(0), PM(12);

        int hourOffset;

        private SideOfDay(int hourOffset) {
            this.hourOffset = hourOffset;
        }
    }

}
