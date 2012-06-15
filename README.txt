Attempt to simplify setting up a timer trigger using just words.

Example expressions:

    Wordy Exp.           Cron Exp.
    ================    ==================
    every 10 minutes                   => 0 0/10 * * * ?
    every 5 seconds                    => 0/5 * * * * ?
    at 10 pm                           => 0 0 22 * * ?
    at 12:00 am                        => 0 0 0 * * ?
    at 12:00 pm                        => 0 0 12 * * ?
    at 23:43                           => 0 43 23 * * ?
    between 0-10 every 10 minutes      => 0 0/10 0-10 * * ?
    between 0 and 10 every 10 minutes  => 0 0/10 0-10 * * ?


Example bean configuration:

     <bean id="trigger" class="org.springframework.scheduling.wordy.WordyTriggerBean">
        <property name="expression" value="every 1 second"/>
        <property name="jobDetail" ref="jobDetail"/>
    </bean>

Available units for the "every" syntax:
    second
    minute
    hour

