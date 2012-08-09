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

import org.junit.Before;
import org.junit.Test;

import static junit.framework.Assert.*;
import static org.springframework.scheduling.wordy.TimeUnit.*;

public class CronBuilderTest {

    private CronBuilder builder;

    @Before
    public void setUp() throws Exception {
        builder = new CronBuilder();
    }

    @Test
    public void isSet_shouldReturnFalseIfTheValueIsNotSet_dayOfWeek() {
        assertFalse(builder.isSet(DAY_OF_WEEK));
    }

    @Test
    public void isSet_shouldReturnTrueIfTheValueWasSet_dayOfWeek() {
        builder.value("1", DAY_OF_WEEK);
        assertTrue(builder.isSet(DAY_OF_WEEK));
    }

    @Test
    public void isSet_shouldReturnFalseIfTheValueWasSetToAQuestionMark_dayOfWeek() {
        builder.value("?", DAY_OF_WEEK);
        assertFalse(builder.isSet(DAY_OF_WEEK));
    }

    @Test
    public void isSet_shouldReturnFalseIfTheValueIsNotSet_second() {
        assertFalse(builder.isSet(SECOND));
    }

    @Test
    public void isSet_shouldReturnTrueIfTheValueWasSet_second() {
        builder.value("1", SECOND);
        assertTrue(builder.isSet(SECOND));
    }

    @Test
    public void isSet_shouldReturnFalseIfTheValueIsNotSet_minute() {
        assertFalse(builder.isSet(MINUTE));
    }

    @Test
    public void isSet_shouldReturnTrueIfTheValueWasSet_minute() {
        builder.value("1", MINUTE);
        assertTrue(builder.isSet(MINUTE));
    }

    @Test
    public void isSet_shouldReturnFalseIfTheValueIsNotSet_hour() {
        assertFalse(builder.isSet(HOUR));
    }

    @Test
    public void isSet_shouldReturnTrueIfTheValueWasSet_hour() {
        builder.value("1", HOUR);
        assertTrue(builder.isSet(HOUR));
    }

    @Test
    public void shouldAllowSpecifyingAValueForDayOfWeek() {
        assertEquals("* * * ? * 1", builder.value("1", DAY_OF_WEEK).toString());
    }

    @Test
    public void shouldAllowSpecifyingAnIntervalForAHour() {
        assertEquals("* * 1/3 * * ?", builder.value("1", HOUR).interval("3", HOUR).toString());
    }

    @Test
    public void shouldAllowSpecifyingAnIntervalForAMinute() {
        assertEquals("* 1/13 * * * ?", builder.value("1", MINUTE).interval("13", MINUTE).toString());
    }

    @Test
    public void shouldAllowSpecifyingAnIntervalForASecond() {
        assertEquals("10/1 * * * * ?", builder.value("10", SECOND).interval("1", SECOND).toString());
    }

    @Test
    public void shouldAllowSpecifyingTheExactHour() {
        assertEquals("* * 1 * * ?", builder.value("1", HOUR).toString());
    }

    @Test
    public void shouldAllowSpecifyingTheExactSecond() {
        assertEquals("1 * * * * ?", builder.value("1", SECOND).toString());
    }

    @Test
    public void shouldAllowSpecifyingTheExactMinute() {
        assertEquals("* 1 * * * ?", builder.value("1", MINUTE).toString());
    }

    @Test(expected = IllegalStateException.class)
    public void toString_shouldBlowUpIfNothingWasEverSetOnTheBuilder() {
        builder.toString();
    }


}
