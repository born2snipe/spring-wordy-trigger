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

public class EverySoOftenParser implements WordyToCronEvaluator {
    private static final Pattern EVERY_PATTERN = Pattern.compile("every ([0-9]+) (hour|minute|second)", Pattern.CASE_INSENSITIVE);

    public void evaluate(String wordyExpression, CronBuilder cron) {
        Matcher matcher = EVERY_PATTERN.matcher(wordyExpression);
        if (matcher.find()) {
            String unitSize = matcher.group(1);
            TimeUnit unit = TimeUnit.valueOf(matcher.group(2).toUpperCase());

            if (!cron.isSet(unit)) {
                cron.value("0", unit);
            }
            cron.interval(unitSize, unit);
            for (TimeUnit timeUnit : TimeUnit.valuesReversed()) {
                if (timeUnit.ordinal() < unit.ordinal()) {
                    cron.value("0", timeUnit);
                }
            }
        }
    }
}
