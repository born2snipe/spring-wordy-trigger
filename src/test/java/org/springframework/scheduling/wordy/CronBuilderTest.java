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

import static junit.framework.Assert.assertEquals;

public class CronBuilderTest {

    private CronBuilder builder;

    @Before
    public void setUp() throws Exception {
        builder = new CronBuilder();
    }

    @Test
    public void shouldAllowSpecifyingAnIntervalForAHour() {
        assertEquals("* * 1/3 * * ?", builder.hour("1").interval("3").toString());
    }

    @Test
    public void shouldAllowSpecifyingAnIntervalForAMinute() {
        assertEquals("* 1/13 * * * ?", builder.minute("1").interval("13").toString());
    }

    @Test
    public void shouldAllowSpecifyingAnIntervalForASecond() {
        assertEquals("10/1 * * * * ?", builder.second("10").interval("1").toString());
    }

    @Test
    public void shouldAllowSpecifyingTheExactHour() {
        assertEquals("* * 1 * * ?", builder.hour("1").toString());
    }

    @Test
    public void shouldAllowSpecifyingTheExactSecond() {
        assertEquals("1 * * * * ?", builder.second("1").toString());
    }

    @Test
    public void shouldAllowSpecifyingTheExactMinute() {
        assertEquals("* 1 * * * ?", builder.minute("1").toString());
    }


    @Test(expected = IllegalStateException.class)
    public void interval_shouldBlowUpIfNoUnitMethodWasInvokedPrior() {
        builder.interval("10");
    }

    @Test(expected = IllegalStateException.class)
    public void toString_shouldBlowUpIfNothingWasEverSetOnTheBuilder() {
        builder.toString();
    }


}
