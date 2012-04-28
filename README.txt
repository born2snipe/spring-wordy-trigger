Attempt to simplify setting up a timer trigger using just words.

Example expressions:

    Wordy Exp.           Cron Exp.
    ================    ==================
    every 10 minutes => 0 0/10 * * * ?
    every 5 seconds  => 0/5 * * * * ?
    at 10 pm         => 0 0 22 * * ?



Example bean configuration:

    <bean class="org.springframework.scheduling.wordy.WordyTrigger">
        <property name="expression" value="every 10 minutes" />
    </bean>

