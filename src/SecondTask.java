import java.sql.Date;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

/**
 * ������:
 * �������� �������, ������������ ��������� ���� �������� ������ � ��������� � ��������, ��� �������� ��������������
 * 1, 10 � 20 ����� ������� ������ � 18:00. ���� ���� �������� �������� �� �������/����������� ���� -
 * �� �������� �������������� � ���������� ������� ����.
 *
 * ���� ������� == �������� �������.
 *
 * ����� ������������ �������:
 * public getVacCheck(java.sql.Date modDate); - ��������� ����, �������� �� ��� �������.
 * ���� �������� - ���������� ��������� ������� ���� ��������� �� ���������. ���������� ���������� ���� java.sql.Date
 */
public class SecondTask {
    public static void main(String[] args) {
        Timestamp nextSendDate = getNextSendDate();
        if (nextSendDate != null) {
            System.out.println("��������� ���� ��������: " + nextSendDate);
        } else {
            System.out.println("�� ������� ���������� ���� ��������.");
        }
    }

    public static Date getVacCheck(Date modDate) {
        // ������ �������� ����
        return modDate;
    }

    public static Timestamp getNextSendDate() {
        LocalDateTime now = LocalDateTime.now();
        List<LocalDateTime> potentialDates = new ArrayList<>();

        // ��������� ���� 1, 10 � 20 ����� �������� � ���������� ������
        addSendDatesForMonth(potentialDates, now.toLocalDate());
        addSendDatesForMonth(potentialDates, now.plusMonths(1).toLocalDate());

        // ��������� ���� �� ������� ���
        List<LocalDateTime> validDates = new ArrayList<>();
        for (LocalDateTime dateTime : potentialDates) {
            LocalDate date = dateTime.toLocalDate();
            Date sqlDate = Date.valueOf(date);
            Date checkedDate = getVacCheck(sqlDate); // ���������, �������� �� ���� �������
            if (!checkedDate.toLocalDate().isAfter(date)) {
                validDates.add(LocalDateTime.of(checkedDate.toLocalDate(), LocalTime.of(18, 0)));
            }
        }

        // ���� ��������� ����, ������� ������ �������� �������
        for (LocalDateTime dateTime : validDates) {
            if (dateTime.isAfter(now)) {
                return Timestamp.valueOf(dateTime);
            }
        }

        // ���� ���� �� �������, ���������� null
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
