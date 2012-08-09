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

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static junit.framework.Assert.assertEquals;

public class WordyTriggerBeanTest {

    private WordyTriggerBean triggerBean;

    @Before
    public void setUp() throws Exception {
        triggerBean = new WordyTriggerBean();
        triggerBean.setName("name");
    }

    @Test
    public void shouldAllowACronExpressionToBeProvidedWithoutAWordyExpression() throws Exception {
        triggerBean.setCronExpression("0 0 0 * * ?");
        triggerBean.afterPropertiesSet();
    }

    @Test
    public void shouldBlowUpIfBothACronAndWordyExpressionIsProvided() throws Exception {
        triggerBean.setCronExpression("0 0 0 * * ?");
        triggerBean.setExpression("every 10 minutes");
        verifyFailure("You provided a CRON expression and a Wordy expression. Please pick one or the other.");
    }

    @Test
    public void shouldBlowUpIfAEmptyWordyExpressionAndNoCronExpressionIsProvided() throws Exception {
        triggerBean.setExpression("");
        verifyFailure("No expression given");
    }

    @Test
    public void shouldBlowUpIfANullWordyExpressionAndNoCronExpressionIsProvided() throws Exception {
        triggerBean.setExpression(null);
        verifyFailure("No expression given");
    }

    private void verifyFailure(String expectedMessage) throws Exception {
        try {
            triggerBean.afterPropertiesSet();
            Assert.fail();
        } catch (IllegalArgumentException e) {
            assertEquals(expectedMessage, e.getMessage());
        }
    }

}
