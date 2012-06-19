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

import java.util.Arrays;
import java.util.Comparator;

public enum TimeUnit {
    SECOND, MINUTE, HOUR, DAY_OF_WEEK;

    public static TimeUnit[] valuesReversed() {
        TimeUnit[] temp = Arrays.copyOf(values(), values().length);
        Arrays.sort(temp, new Comparator<TimeUnit>() {
            public int compare(TimeUnit timeUnit, TimeUnit timeUnit1) {
                return new Integer(timeUnit1.ordinal()).compareTo(timeUnit.ordinal());
            }
        });
        return temp;
    }
}
