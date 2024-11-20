import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * ������:
 * �������� �������, ������������ ��������� ��������� ��������� (�� ����� 99 999.99).
 * ������� �������� ���������� ���� bigDecimal
 */
public class ThirdTask {
    private static final String[] UNITS = {
            "", "����", "���", "���", "������", "����", "�����", "����", "������", "������"
    };
    private static final String[] TEENS = {
            "������", "�����������", "����������", "����������", "������������",
            "����������", "�����������", "����������", "������������", "������������"
    };
    private static final String[] TENS = {
            "", "", "��������", "��������", "�����", "���������", "����������",
            "���������", "�����������", "���������"
    };
    private static final String[] HUNDREDS = {
            "", "���", "������", "������", "���������", "�������", "��������",
            "�������", "���������", "���������"
    };

    private static final String[][] FORMS = {
            {"�����", "�����", "������"},
            {"�������", "�������", "������"}
    };

    public static String convert(BigDecimal amount) {
        if (amount.compareTo(BigDecimal.ZERO) == 0) {
            return "���� ������ ���� ������";
        }

        BigDecimal roundedAmount = amount.setScale(2, RoundingMode.DOWN);
        long rubles = roundedAmount.longValue();
        int kopecks = roundedAmount.remainder(BigDecimal.ONE).movePointRight(2).intValue();

        if (rubles > 99_999) {
            throw new IllegalArgumentException("����� ������ ���� ������ 100 000 ������.");
        }

        return convertRubles(rubles) + " " + convertPart(kopecks, FORMS[1]);
    }

    private static String convertRubles(long rubles) {
        StringBuilder result = new StringBuilder();

        // ������
        if (rubles >= 1_000) {
            result.append(convertPart(rubles / 1_000, new String[]{"������", "������", "�����"})).append(" ");
            rubles %= 1_000;
        }

        // �������
        result.append(convertPart(rubles, FORMS[0]));
        return result.toString().trim();
    }

    private static String convertPart(long number, String[] forms) {
        if (number == 0) {
            return "���� " + forms[2];
        }

        StringBuilder result = new StringBuilder();

        // �����
        if (number >= 100) {
            result.append(HUNDREDS[(int) (number / 100)]).append(" ");
            number %= 100;
        }

        // ������� � ����� �� 10 �� 19
        if (number >= 10 && number <= 19) {
            result.append(TEENS[(int) (number - 10)]).append(" ");
        } else {
            // �������
            if (number >= 20) {
                result.append(TENS[(int) (number / 10)]).append(" ");
                number %= 10;
            }
            // �������
            if (number > 0) {
                if (forms == FORMS[0] && number == 1) {
                    result.append("���� ");
                } else if (forms == FORMS[0] && number == 2) {
                    result.append("��� ");
                } else {
                    result.append(UNITS[(int) number]).append(" ");
                }
            }
        }

        result.append(getCorrectForm(number, forms));
        return result.toString().trim();
    }

    private static String getCorrectForm(long number, String[] forms) {
        number %= 100;
        if (number >= 11 && number <= 19) {
            return forms[2];
        }

        number %= 10;
        if (number == 1) {
            return forms[0];
        } else if (number >= 2 && number <= 4) {
            return forms[1];
        } else {
            return forms[2];
        }
    }

    public static void main(String[] args) {
        BigDecimal amount = new BigDecimal("12345.67");
        BigDecimal amount2 = new BigDecimal("843.09");
        BigDecimal amount3 = new BigDecimal("99909.12");
        BigDecimal amount4 = new BigDecimal("0.59");
        System.out.println(convert(amount));
        System.out.println(convert(amount2));
        System.out.println(convert(amount3));
        System.out.println(convert(amount4));
    }
}

