Attempt to simplify setting up a timer trigger using just words.  To help you come up with your own wordy expression
for your task, just answer the following question:

    I want this to run...


Example expressions:

    Wordy Exp.                                              Cron Exp.
    ================                                       ==================
    every 10 minutes                                    => 0 0/10 * * * ?
    every 5 seconds                                     => 0/5 * * * * ?
    at 10 pm                                            => 0 0 22 * * ?
    at 12:00 am                                         => 0 0 0 * * ?
    at 12:00 pm                                         => 0 0 12 * * ?
    at 23:43                                            => 0 43 23 * * ?
    between 0-10 every 10 minutes                       => 0 0/10 0-10 * * ?
    between 0 and 10 every 10 minutes                   => 0 0/10 0-10 * * ?
    between 1 am and 10 pm every 5 minutes              => 0 0/5 1-22 * * ?
    between 1-10 pm every 2 hours                       => 0 0 13-10/2 * * ?
    every 2 hours between 1-10 pm                       => 0 0 13-10/2 * * ?
    on MON-FRI every 10 minutes                         => 0 0/10 * ? * MON-FRI
    every 10 minutes on MON-FRI                         => 0 0/10 * ? * MON-FRI
    on MON thru FRI every 10 minutes                    => 0 0/10 * ? * MON-FRI
    on SUN,TUE,THU,FRI every 10 minutes                 => 0 0/10 * ? * SUN,TUE,THU,FRI
    at 10 pm on MON-FRI                                 => 0 0 22 * ? * MON-FRI
    on WED at 23:00                                     => 0 0 23 * ? * WED
    on MON-FRI between 12 am and 10 pm every 10 minutes => 0 0/10 0-22 ? * MON-FRI

Example bean configuration:

     <bean id="trigger" class="org.springframework.scheduling.wordy.WordyTriggerBean">
        <property name="expression" value="every 1 second"/>
        <property name="jobDetail" ref="jobDetail"/>
    </bean>

Available units for the "every" syntax:

    second
    minute
    hour


Available hour ranges for "between" syntax:

    Military time: 0-23|0 and 23
    Time of day: 1-10 pm|1 am and 10 pm


Available days for "on" syntax:

    SUN, MON, TUE, WED, THU, FRI, SAT


Spring Docs for setting up a scheduled task:

    http://static.springsource.org/spring/docs/3.0.5.RELEASE/reference/scheduling.html


[![Build Status](https://secure.travis-ci.org/born2snipe/spring-wordy-trigger.png)](http://travis-ci.org/born2snipe/spring-wordy-trigger)