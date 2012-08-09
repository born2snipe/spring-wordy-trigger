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

package integration;

import org.junit.Test;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.scheduling.wordy.WordyTrigger;

import static org.junit.Assert.assertTrue;

public class CreateSchedulerTest {
    @Test
    public void shouldSupportTheNewFormatForTriggersInSpring() throws InterruptedException {
        StubJob task = new StubJob();

        ThreadPoolTaskScheduler scheduler = new ThreadPoolTaskScheduler();
        scheduler.afterPropertiesSet();
        scheduler.schedule(task, new WordyTrigger("every 1 second"));

        Thread.sleep(3000L);

        assertTrue("the task should have been invoked at some point", task.numberOfInvocations > 2);
    }

}
