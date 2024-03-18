package com.lpenaud.bouts.finance;

import java.math.BigInteger;
import java.util.regex.Pattern;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class RibIbanUtils {

    private static final Pattern WORD_CHAR_ONLY = Pattern.compile("[A-Za-z]");

    private static final BigInteger NINETY_SEVEN = BigInteger.valueOf(97);

    public static String frenchIban(final Rib rib) {
        final var iban = rib.getBank() + rib.getCounter() + rib.getAccountNumber() + rib.getKey();
        final var result = WORD_CHAR_ONLY.matcher(iban + "152700")
                        .replaceAll(m -> Integer.toString(Integer.parseInt(m.group(), 36)));
        final var modulo = new BigInteger(result).mod(NINETY_SEVEN);
        return String.format("FR%02d%s", 98 - modulo.intValue(), iban);
    }
}
