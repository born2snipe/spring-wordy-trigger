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

import org.junit.Test;

import static junit.framework.Assert.assertEquals;

public class WordyExpressionTest {
    @Test
    public void everyExpression_shouldHandleHour() {
        assertEquals("0 0 0/1 * * ?", wordyToCron("every 1 hour"));
    }

    @Test
    public void everyExpression_shouldHandleHour_plural() {
        assertEquals("0 0 0/3 * * ?", wordyToCron("every 3 hours"));
    }

    @Test
    public void everyExpression_shouldHandleMinute() {
        assertEquals("0 0/1 * * * ?", wordyToCron("every 1 minute"));
    }

    @Test
    public void everyExpression_shouldHandleMinute_plural() {
        assertEquals("0 0/2 * * * ?", wordyToCron("every 2 minutes"));
    }

    @Test
    public void everyExpression_shouldHandleSecond() {
        assertEquals("0/1 * * * * ?", wordyToCron("every 1 second"));
    }

    @Test
    public void everyExpression_shouldHandleSecond_plural() {
        assertEquals("0/1 * * * * ?", wordyToCron("every 1 seconds"));
    }

    @Test
    public void everyExpression_shouldMultipleDigitUnitSize() {
        assertEquals("0/100 * * * * ?", wordyToCron("every 100 second"));
    }

    @Test
    public void everyExpression_shouldIgnoreCase() {
        assertEquals("0/1 * * * * ?", wordyToCron("EVERY 1 Second"));
    }

    @Test(expected = BadWordyExpressionException.class)
    public void everyExpression_shouldBlowUpIfABadExpressionIsGiven() {
        wordyToCron("every xx hour");
    }

    @Test
    public void atExpression_shouldHandleGivenTheHourAndTheSideOfTheDay() {
        assertEquals("0 0 1 * * ?", wordyToCron("at 1 am"));
    }

    @Test
    public void atExpression_shouldHandleGivenTheHourAndTheSideOfTheDay_pm() {
        assertEquals("0 0 13 * * ?", wordyToCron("at 1 pm"));

    }

    @Test
    public void atExpression_shouldHandleMultipleDigitHours() {
        assertEquals("0 0 23 * * ?", wordyToCron("at 11 pm"));
    }

    @Test
    public void atExpression_shouldIgnoreCase() {
        assertEquals("0 0 13 * * ?", wordyToCron("AT 1 pM"));
    }

    @Test(expected = BadWordyExpressionException.class)
    public void atExpression_shouldBlowUpIfA3DigitHourIsGiven() {
        wordyToCron("at 111 pm");
    }

    private String wordyToCron(String expression) {
        return new WordyExpression(expression).toCron();
    }

}
