<?xml version="1.0" encoding="UTF-8"?>
<Configuration status="warn" monitorInterval="120">

    <Properties>
        <Property name="logRoot">/data/logs/${projectName}/</Property>
        <Property name="Log4jContextSelector">org.apache.logging.log4j.core.async.AsyncLoggerContextSelector</Property>
    </Properties>

    <Appenders>

        <Console name="Console" target="SYSTEM_OUT">
            <PatternLayout pattern="%msg%xEx%n"/>
        </Console>

        <RollingFile name="WARN_LOG" fileName="${r'${logRoot}'}'}/warn/warn.log"
                     filePattern="${r'${logRoot}'}/warn.%d{yyyy-MM-dd}-%i.log" append="true">
            <Filters>
                <ThresholdFilter level="ERROR" onMatch="DENY" onMismatch="NEUTRAL"/>
                <ThresholdFilter level="WARN" onMatch="ACCEPT" onMismatch="DENY"/>
            </Filters>
            <PatternLayout pattern="%msg%xEx%n"/>
            <Policies>
                <SizeBasedTriggeringPolicy size="10 MB"/>
            </Policies>
            <DefaultRolloverStrategy max="20"/>
        </RollingFile>

        <RollingFile name="ERROR_LOG" fileName="${r'${logRoot}'}/error/error.log"
                     filePattern="${r'${logRoot}'}/error.%d{yyyy-MM-dd}-%i.log" append="true">
            <Filters>
                <ThresholdFilter level="ERROR" onMatch="ACCEPT" onMismatch="DENY"/>
            </Filters>
            <PatternLayout pattern="%msg%xEx%n"/>
            <Policies>
                <SizeBasedTriggeringPolicy size="10 MB"/>
            </Policies>
            <DefaultRolloverStrategy max="20"/>
        </RollingFile>

        <RollingFile name="DEBUG_LOG" fileName="${r'${logRoot}'}/debug/debug.log"
                     filePattern="${r'${logRoot}'}/debug.%d{yyyy-MM-dd}-%i.log" append="true">
            <Filters>
                <ThresholdFilter level="INFO" onMatch="DENY" onMismatch="NEUTRAL"/>
                <ThresholdFilter level="DEBUG" onMatch="ACCEPT" onMismatch="NEUTRAL"/>
            </Filters>
            <PatternLayout pattern="%msg%xEx%n"/>
            <Policies>
                <SizeBasedTriggeringPolicy size="10 MB"/>
            </Policies>
            <DefaultRolloverStrategy max="20"/>
        </RollingFile>

        <RollingFile name="INFO_LOG" fileName="${r'${logRoot}'}/info/info.log"
                     filePattern="${r'${logRoot}'}/info.%d{yyyy-MM-dd}-%i.log" append="true">
            <Filters>
                <ThresholdFilter level="WARN" onMatch="DENY" onMismatch="NEUTRAL"/>
                <ThresholdFilter level="INFO" onMatch="ACCEPT" onMismatch="DENY"/>
            </Filters>
            <PatternLayout pattern="%msg%xEx%n"/>
            <Policies>
                <SizeBasedTriggeringPolicy size="10 MB"/>
            </Policies>
            <DefaultRolloverStrategy max="20"/>
        </RollingFile>
        <!--用户操作日志-->
        <RollingFile name="userActiveLog" fileName="${r'${logRoot}'}/userLogger.log"
                     filePattern="${r'${logRoot}'}/userActiveLog/bak/userLogger.log.%d{yyyy-MM-dd}-%i" append="true">
            <Filters>
                <ThresholdFilter level="WARN" onMatch="DENY" onMismatch="NEUTRAL"/>
                <ThresholdFilter level="INFO" onMatch="ACCEPT" onMismatch="DENY"/>
            </Filters>
            <PatternLayout pattern="%msg%xEx%n"/>
            <Policies>
                <SizeBasedTriggeringPolicy size="10 MB"/>
            </Policies>
            <DefaultRolloverStrategy max="20"/>
        </RollingFile>
        <!-- 错误日志收集-->
        <RollingFile name="errorLogMessageLog" fileName="${r'${logRoot}'}/kafka/errorLogMessageLog.log"
                     filePattern="${r'${logRoot}'}/kafka/errorLogMessage/bak/errorLogMessageLog.log.%d{yyyy-MM-dd-HH_mm}-%i"
                     append="true">
            <Filters>
                <ThresholdFilter level="INFO" onMatch="ACCEPT" onMismatch="DENY"/>
            </Filters>
            <PatternLayout pattern="%msg%xEx%n"/>
            <Policies>
                <SizeBasedTriggeringPolicy size="10 MB"/>
                <CronTriggeringPolicy schedule="0/2 * * * * ?"/>
            </Policies>
        </RollingFile>
    </Appenders>

    <Loggers>
        <AsyncLogger name="userLogger" additivity="FALSE" level="ALL">
            <AppenderRef ref="userActiveLog" level="ALL"/>
        </AsyncLogger>
        <AsyncLogger name="errorLogMessage" additivity="FALSE" level="ALL">
            <AppenderRef ref="errorLogMessageLog" level="ALL"/>
        </AsyncLogger>

        <Root level="INFO">
            <AppenderRef ref="Console"/>
            <AppenderRef ref="ERROR_LOG"/>
            <AppenderRef ref="WARN_LOG"/>
            <AppenderRef ref="INFO_LOG"/>
            <AppenderRef ref="DEBUG_LOG"/>
        </Root>
    </Loggers>

</Configuration>