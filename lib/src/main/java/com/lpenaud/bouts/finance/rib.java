package com.lpenaud.bouts.finance;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@EqualsAndHashCode
public class Rib {

    /**
     * RIB Key
     */
    private String key;

    /**
     * Bank identifier
     */
    private String bank;

    /**
     * Counter identifier
     */
    private String counter;

    /**
     * Account number
     */
    private String accountNumber;

    /**
     * IBAN
     */
    private String iban;
}
