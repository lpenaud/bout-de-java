package com.lpenaud.bouts.finance;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class RibIbanUtilsTest {

    @Test
    void testFrenchIban() {
        // Generated RIB / IBAN, thanks to https://cerpeg.fr/bazar/index.php/cbanque/rib
        final String iban = "FR7617468715093507552624744";
        final Rib rib = new Rib();
        rib.setBank("17468");
        rib.setCounter("71509");
        rib.setAccountNumber("35075526247");
        rib.setKey("44");
        Assertions.assertEquals(iban, RibIbanUtils.frenchIban(rib));
    }

}
