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

import java.util.Arrays;

import static junit.framework.Assert.assertTrue;

public class TimeUnitTest {
    @Test
    public void valuesReversed_shouldTheValuesInReverseOrder() {
        TimeUnit[] expectedResult = {TimeUnit.DAY_OF_WEEK, TimeUnit.HOUR, TimeUnit.MINUTE, TimeUnit.SECOND};
        assertTrue(Arrays.deepEquals(expectedResult, TimeUnit.valuesReversed()));
    }

}
