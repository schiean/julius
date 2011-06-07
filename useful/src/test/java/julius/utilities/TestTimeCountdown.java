/**
 * Copyright 2011 AJG van Schie
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

package julius.utilities;

import julius.test.BDDTestCase;

public class TestTimeCountdown extends BDDTestCase{

	public void testNormalFlow(){
		given("a countdown of 2 seconds");
			TimeCountdown countdown = TimeCountdown.toSeconds(2);
		when("started");
			countdown.start();
		then("after 1 second, we expect isCompleted is false");
			Sleep.sleep.seconds(1);
			assertFalse(countdown.isCompleted());
		and("after 2 seconds, we expect isCompleted is true" );
			Sleep.sleep.seconds(1);
			Sleep.sleep.milliseconds(10); // some marge
			assertTrue(countdown.isCompleted());
		and("after a restart we expect isCompleted is false");
			countdown.restart();
			Sleep.sleep.seconds(1);
			assertFalse(countdown.isCompleted());
		and("after another 2 seconds, we expect isCompleted to be true again");
			Sleep.sleep.seconds(1);
			Sleep.sleep.milliseconds(10);// some marge
			assertTrue(countdown.isCompleted());
		successFullStory();
		
	}
	
}
