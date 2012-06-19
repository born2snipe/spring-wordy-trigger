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
    public static final String DEFAULT_VALUE = "*";
    private boolean initialized = false;
    private String secondValue, minuteValue, hourValue;
    private String secondIntervalValue, minuteIntervalValue, hourIntervalValue;

    @Override
    public String toString() {
        if (!initialized)
            throw new IllegalStateException("Please set something on the builder");

        StringBuilder cron = new StringBuilder();
        appendValue(cron, secondValue, secondIntervalValue);
        appendValue(cron, minuteValue, minuteIntervalValue);
        appendValue(cron, hourValue, hourIntervalValue);
        cron.append("* * ?");

        return cron.toString();
    }

    private void appendValue(StringBuilder cron, String value, String intervalValue) {
        cron.append(value);
        if (intervalValue.length() > 0) {
            cron.append("/").append(intervalValue);
        }
        cron.append(" ");
    }

    public CronBuilder minute(String value) {
        initialize();
        minuteValue = value;
        return this;
    }

    public CronBuilder second(String value) {
        initialize();
        secondValue = value;
        return this;
    }

    public CronBuilder hour(String value) {
        initialize();
        hourValue = value;
        return this;
    }

    public CronBuilder value(String value, TimeUnit timeUnit) {
        initialize();
        switch (timeUnit) {
            case HOUR:
                hourValue = value;
                break;
            case MINUTE:
                minuteValue = value;
                break;
            case SECOND:
                secondValue = value;
                break;
        }
        return this;
    }

    public CronBuilder interval(String value, TimeUnit timeUnit) {
        switch (timeUnit) {
            case HOUR:
                hourIntervalValue = value;
                break;
            case MINUTE:
                minuteIntervalValue = value;
                break;
            case SECOND:
                secondIntervalValue = value;
                break;
        }
        return this;
    }

    public boolean isSet(TimeUnit timeUnit) {
        switch (timeUnit) {
            case SECOND:
                return wasSet(secondValue);
            case MINUTE:
                return wasSet(minuteValue);
        }
        return wasSet(hourValue);
    }

    private void initialize() {
        if (!initialized) {
            secondValue = DEFAULT_VALUE;
            minuteValue = DEFAULT_VALUE;
            hourValue = DEFAULT_VALUE;
            secondIntervalValue = "";
            minuteIntervalValue = "";
            hourIntervalValue = "";
            initialized = true;
        }
    }

    private boolean wasSet(String value) {
        return !DEFAULT_VALUE.equals(value) && value != null;
    }
}
