import java.sql.Date;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Задача:
 * Написать функцию, определяющую ближайшую дату отправки списка в страховую с условием, что отправка осуществляется
 * 1, 10 и 20 числа каждого месяца в 18:00. Если дата отправки попадает на рабочий/праздничный день -
 * то отправка осуществляется в предыдущий рабочий день.
 *
 * дата запроса == текущему времени.
 *
 * Можно использовать функцию:
 * public getVacCheck(java.sql.Date modDate); - проверяет дату, является ли она рабочей.
 * если выходной - возвращает ближайший рабочий день следующий за выходными. Возвращает переменную типа java.sql.Date
 */
public class SecondTask {
    public static void main(String[] args) {
        Timestamp nextSendDate = getNextSendDate();
        if (nextSendDate != null) {
            System.out.println("Ближайшая дата отправки: " + nextSendDate);
        } else {
            System.out.println("Не удалось определить дату отправки.");
        }
    }

    public static Date getVacCheck(Date modDate) {
        // логика проверка даты
        return modDate;
    }

    public static Timestamp getNextSendDate() {
        LocalDateTime now = LocalDateTime.now();
        List<LocalDateTime> potentialDates = new ArrayList<>();

        // добавляем даты 1, 10 и 20 числа текущего и следующего месяца
        addSendDatesForMonth(potentialDates, now.toLocalDate());
        addSendDatesForMonth(potentialDates, now.plusMonths(1).toLocalDate());

        // проверяем даты на рабочие дни
        List<LocalDateTime> validDates = new ArrayList<>();
        for (LocalDateTime dateTime : potentialDates) {
            LocalDate date = dateTime.toLocalDate();
            Date sqlDate = Date.valueOf(date);
            Date checkedDate = getVacCheck(sqlDate); // проверяем, является ли день рабочим
            if (!checkedDate.toLocalDate().isAfter(date)) {
                validDates.add(LocalDateTime.of(checkedDate.toLocalDate(), LocalTime.of(18, 0)));
            }
        }

        // ищем ближайшую дату, которая больше текущего времени
        for (LocalDateTime dateTime : validDates) {
            if (dateTime.isAfter(now)) {
                return Timestamp.valueOf(dateTime);
            }
        }

        // если дата не найдена, возвращаем null
        return null;
    }

    private static void addSendDatesForMonth(List<LocalDateTime> potentialDates, LocalDate monthStart) {
        int year = monthStart.getYear();
        int month = monthStart.getMonthValue();
        potentialDates.add(LocalDateTime.of(year, month, 1, 18, 0));
        potentialDates.add(LocalDateTime.of(year, month, 10, 18, 0));
        potentialDates.add(LocalDateTime.of(year, month, 20, 18, 0));
    }
}
