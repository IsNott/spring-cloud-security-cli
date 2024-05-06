<configuration scan="true" scanPeriod="60 seconds" debug="false">

    <include resource="org/springframework/boot/logging/logback/defaults.xml" />
    <include resource="org/springframework/boot/logging/logback/console-appender.xml" />

    <root level="info">
        <appender-ref ref="STDOUT" />
    </root>

    <appender name="STDOUT" class="ch.qos.logback.core.ConsoleAppender">
        <withJansi>false</withJansi>
        <encoder>
            <pattern>%white(%d{yyyy-MM-dd HH:mm:ss}) %highlight(%lsn) %green([%thread]) %highlight(%-5level) %boldMagenta(%logger{10}) - %cyan(%msg%n)</pattern>
            <charset>utf8</charset>
        </encoder>
    </appender>


    <logger name="${parent.groupId}.${current.type}" level="info"/>
</configuration>
