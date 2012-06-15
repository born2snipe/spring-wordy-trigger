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


public class CronBuilder {
    private boolean initialized = false;
    private String secondValue, minuteValue, hourValue;
    private TimeUnit currentUnit;

    @Override
    public String toString() {
        if (secondValue == null || minuteValue == null || hourValue == null)
            throw new IllegalStateException("Please set something on the builder");

        StringBuilder cron = new StringBuilder();
        cron.append(secondValue).append(" ");
        cron.append(minuteValue).append(" ");
        cron.append(hourValue).append(" ");
        cron.append("* * ?");

        return cron.toString();
    }

    public CronBuilder minute(String value) {
        initialize();
        currentUnit = TimeUnit.MINUTE;
        minuteValue = value;
        return this;
    }

    public CronBuilder second(String value) {
        initialize();
        currentUnit = TimeUnit.SECOND;
        secondValue = value;
        return this;
    }

    public CronBuilder hour(String value) {
        initialize();
        currentUnit = TimeUnit.HOUR;
        hourValue = value;
        return this;
    }

    public CronBuilder interval(String intervalValue) {
        if (currentUnit == null) {
            throw new IllegalStateException("Please invoke a time unit method first");
        }

        String interval = "/" + intervalValue;
        switch (currentUnit) {
            case HOUR:
                hourValue += interval;
                break;
            case MINUTE:
                minuteValue += interval;
                break;
            case SECOND:
                secondValue += interval;
                break;
        }
        return this;
    }

    private void initialize() {
        if (!initialized) {
            secondValue = "*";
            minuteValue = "*";
            hourValue = "*";
            initialized = true;
        }
    }
}
