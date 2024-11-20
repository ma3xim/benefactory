import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * Çàäà÷à:
 * Íàïèñàòü ôóíêöèş, âîçâğàùàşùóş ïğîïèñíîå íàïèñàíèå ñòîèìîñòè (äî òûñÿ÷ 99 999.99).
 * Âõîäíîé ïàğàìåòğ ïåğåìåííàÿ òèïà bigDecimal
 */
public class ThirdTask {
    private static final String[] UNITS = {
            "", "îäèí", "äâà", "òğè", "÷åòûğå", "ïÿòü", "øåñòü", "ñåìü", "âîñåìü", "äåâÿòü"
    };
    private static final String[] TEENS = {
            "äåñÿòü", "îäèííàäöàòü", "äâåíàäöàòü", "òğèíàäöàòü", "÷åòûğíàäöàòü",
            "ïÿòíàäöàòü", "øåñòíàäöàòü", "ñåìíàäöàòü", "âîñåìíàäöàòü", "äåâÿòíàäöàòü"
    };
    private static final String[] TENS = {
            "", "", "äâàäöàòü", "òğèäöàòü", "ñîğîê", "ïÿòüäåñÿò", "øåñòüäåñÿò",
            "ñåìüäåñÿò", "âîñåìüäåñÿò", "äåâÿíîñòî"
    };
    private static final String[] HUNDREDS = {
            "", "ñòî", "äâåñòè", "òğèñòà", "÷åòûğåñòà", "ïÿòüñîò", "øåñòüñîò",
            "ñåìüñîò", "âîñåìüñîò", "äåâÿòüñîò"
    };

    private static final String[][] FORMS = {
            {"ğóáëü", "ğóáëÿ", "ğóáëåé"},
            {"êîïåéêà", "êîïåéêè", "êîïååê"}
    };

    public static String convert(BigDecimal amount) {
        if (amount.compareTo(BigDecimal.ZERO) == 0) {
            return "íîëü ğóáëåé íîëü êîïååê";
        }

        BigDecimal roundedAmount = amount.setScale(2, RoundingMode.DOWN);
        long rubles = roundedAmount.longValue();
        int kopecks = roundedAmount.remainder(BigDecimal.ONE).movePointRight(2).intValue();

        if (rubles > 99_999) {
            throw new IllegalArgumentException("Ñóììà äîëæíà áûòü ìåíüøå 100 000 ğóáëåé.");
        }

        return convertRubles(rubles) + " " + convertPart(kopecks, FORMS[1]);
    }

    private static String convertRubles(long rubles) {
        StringBuilder result = new StringBuilder();

        // òûñÿ÷è
        if (rubles >= 1_000) {
            result.append(convertPart(rubles / 1_000, new String[]{"òûñÿ÷à", "òûñÿ÷è", "òûñÿ÷"})).append(" ");
            rubles %= 1_000;
        }

        // îñòàòîê
        result.append(convertPart(rubles, FORMS[0]));
        return result.toString().trim();
    }

    private static String convertPart(long number, String[] forms) {
        if (number == 0) {
            return "íîëü " + forms[2];
        }

        StringBuilder result = new StringBuilder();

        // ñîòíè
        if (number >= 100) {
            result.append(HUNDREDS[(int) (number / 100)]).append(" ");
            number %= 100;
        }

        // äåñÿòêè è ÷èñëà îò 10 äî 19
        if (number >= 10 && number <= 19) {
            result.append(TEENS[(int) (number - 10)]).append(" ");
        } else {
            // äåñÿòêè
            if (number >= 20) {
                result.append(TENS[(int) (number / 10)]).append(" ");
                number %= 10;
            }
            // åäèíèöû
            if (number > 0) {
                if (forms == FORMS[0] && number == 1) {
                    result.append("îäèí ");
                } else if (forms == FORMS[0] && number == 2) {
                    result.append("äâà ");
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

