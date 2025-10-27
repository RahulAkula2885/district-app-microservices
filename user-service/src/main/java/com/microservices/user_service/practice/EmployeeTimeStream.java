package com.microservices.user_service.practice;

import java.time.Duration;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.stream.Collectors;

public class EmployeeTimeStream {

    static class LogEntry {
        int empId;
        LocalTime time;
        String status;

        public LogEntry(int empId, String time, String status) {
            this.empId = empId;
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("h:mm:ssa", Locale.ENGLISH);
            this.time = LocalTime.parse(time.toUpperCase(), formatter);
            this.status = status;
        }

        @Override
        public String toString() {
            return "LogEntry{" +
                    "empId=" + empId +
                    ", time=" + time +
                    ", status='" + status + '\'' +
                    '}';
        }
    }

    static void main() {
        List<LogEntry> logs = List.of(
                new LogEntry(1, "9:00:00AM", "IN"),
                new LogEntry(1, "10:30:00AM", "OUT"),
                new LogEntry(1, "10:40:00AM", "IN"),
                new LogEntry(1, "1:00:00PM", "OUT"),
                new LogEntry(1, "2:00:00PM", "IN"),
                new LogEntry(1, "4:00:00PM", "OUT"),
                new LogEntry(1, "4:30:00PM", "IN"),
                new LogEntry(1, "7:00:00PM", "OUT"),

                new LogEntry(2, "9:00:00AM", "IN"),
                new LogEntry(2, "10:30:00AM", "OUT"),
                new LogEntry(2, "10:40:00AM", "IN"),
                new LogEntry(2, "1:00:00PM", "OUT"),
                new LogEntry(2, "2:00:00PM", "IN"),
                new LogEntry(2, "4:00:00PM", "OUT"),
                new LogEntry(2, "4:30:00PM", "IN"),
                new LogEntry(2, "7:00:00PM", "OUT")
        );

        /**
         * Step 1:- Group by employeeId
         * */
        Map<Integer, List<LogEntry>> groupedLogs = logs.stream()
                //.sorted(Comparator.comparing(a -> a.time))
                .collect(Collectors.groupingBy(a -> a.empId));

        System.out.println(groupedLogs);

        /**
         * Step 2:- Process each employee
         * */

        groupedLogs.forEach((empId, empLogs) -> {

            Iterator<LogEntry> iterator = empLogs.iterator();

            while (iterator.hasNext()) {
                LogEntry inLog = iterator.next();
                System.out.println("In Log:- " + inLog);
                // if (!"IN".equalsIgnoreCase(inLog.status) || !iterator.hasNext()) continue;
                if (!"IN".equalsIgnoreCase(inLog.status)) continue;

                LogEntry outLog = iterator.next();
                if (!"OUT".equalsIgnoreCase(outLog.status)) continue;

                System.out.println("Out Log:- " + outLog);

                Duration duration = Duration.between(inLog.time, outLog.time);
                long hours = duration.toHours();
                long minutes = duration.toMinutes() % 60;

                long min = duration.toMinutes();

                System.out.println("EmpId:- " + empId + " --> " + hours + "hrs " + minutes + "=== " + min);
            }

        });
    }

}