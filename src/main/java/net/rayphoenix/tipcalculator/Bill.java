package net.rayphoenix.tipcalculator;

import java.math.BigDecimal;

class Bill {
    /**********************************************************
     *                 Define Class Variables                 *
     *********************************************************/
    private String _serviceQuality;
    private BigDecimal _billTotal = new BigDecimal("0"),
            _numberOfPeople = new BigDecimal("1");

    /**********************************************************
     *         Methods to set and retrieve variables          *
     *********************************************************/
    void setBillTotal(BigDecimal billTotal) {
        _billTotal = billTotal;
    }

    BigDecimal billTotal() {
        return _billTotal.setScale(2, BigDecimal.ROUND_DOWN);
    }

    void setNumberOfPeople(BigDecimal numberOfPeople) {
        _numberOfPeople = numberOfPeople;
    }

    BigDecimal numberOfPeople() {
        return _numberOfPeople.setScale(0, BigDecimal.ROUND_DOWN);
    }

    void setServiceQuality(String serviceQuality) {
        _serviceQuality = serviceQuality;
    }

    String serviceQualiity() {
        return _serviceQuality;
    }

    /**********************************************************
     *  Methods to calculate values based on class variables  *
     *********************************************************/
    BigDecimal tipPercent() {
        // Turn service level into tip percent
        BigDecimal _tipPercent;
        switch (_serviceQuality) {
            case "Amazing":
                _tipPercent = new BigDecimal(".25");
                break;
            case "Excellent":
                _tipPercent = new BigDecimal(".20");
                break;
            case "Average":
                _tipPercent = new BigDecimal(".15");
                break;
            case "Poor":
                _tipPercent = new BigDecimal(".10");
                break;
            default:
                _tipPercent = new BigDecimal("0");
        }
        return _tipPercent;
    }

    BigDecimal tipValue() {
        return _billTotal.multiply(tipPercent()).setScale(2, BigDecimal.ROUND_DOWN);
    }

    BigDecimal totalWithTip() {
        return _billTotal.add(tipValue()).setScale(2, BigDecimal.ROUND_DOWN);
    }

    BigDecimal totalPerPerson() {
        return totalWithTip().divide(numberOfPeople(), 2, BigDecimal.ROUND_DOWN);
    }

    BigDecimal remainder() {
        BigDecimal _totalPaid = totalPerPerson().multiply(numberOfPeople());
        return totalWithTip().subtract(_totalPaid);
    }
}
