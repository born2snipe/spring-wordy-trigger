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
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static junit.framework.Assert.assertTrue;

@ContextConfiguration("classpath:contexts/job-config.xml")
@RunWith(SpringJUnit4ClassRunner.class)
public class IntegrationTest {
    @Autowired
    StubJob job;

    @Test
    public void shouldInvokeTheJobEverySecond() throws InterruptedException {
        long start = System.currentTimeMillis();

        while (System.currentTimeMillis() - start < 2500L)
            Thread.sleep(100L);

        assertTrue(job.numberOfInvocations > 1);
    }

}
