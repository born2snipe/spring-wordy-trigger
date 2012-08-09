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

import org.springframework.scheduling.quartz.CronTriggerBean;

public class WordyTriggerBean extends CronTriggerBean {
    private String expression;

    @Override
    public void afterPropertiesSet() throws Exception {
        if (isBlank(expression) && isBlank(getCronExpression())) {
            throw new IllegalArgumentException("No expression given");
        } else if (!isBlank(expression) && !isBlank(getCronExpression())) {
            throw new IllegalArgumentException("You provided a CRON expression and a Wordy expression. Please pick one or the other.");
        }

        if (!isBlank(expression)) {
            WordyExpression wordyExpression = new WordyExpression(expression);
            super.setCronExpression(wordyExpression.toCron());
        }

        super.afterPropertiesSet();
    }

    public void setExpression(String expression) {
        this.expression = expression;
    }

    private boolean isBlank(String expression) {
        return expression == null || expression.trim().length() == 0;
    }
}
