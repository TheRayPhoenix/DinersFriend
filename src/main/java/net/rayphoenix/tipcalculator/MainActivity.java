package net.rayphoenix.tipcalculator;

import android.content.pm.ActivityInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.math.BigDecimal;
import java.text.DecimalFormat;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        /**
         * Set default service quality to "average"
         */
        final Spinner spnServiceQuality = (Spinner) findViewById(R.id.spnServiceLevel);
        spnServiceQuality.setSelection(2);

        /**
         * Link button from layout to logic
         */
        Button calculateResult = (Button) findViewById(R.id.btnCalculate);

        /********************************
         *        Operate Button        *
         *******************************/
        calculateResult.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Bill bill = new Bill();

                /**
                 * Collect user input
                 */
                try {
                    collectUserDataFor(bill);
                } catch (Exception e) {
                    Toast.makeText(MainActivity.this, "Invalid Entry.\nPlease try again.", Toast.LENGTH_SHORT).show();
                    return;
                }

                /**
                 * Make sure number of people is not zero (Avoid div by zero error
                 */
                if (bill.numberOfPeople().equals(new BigDecimal("0"))) {
                    Toast.makeText(MainActivity.this, "Invalid Entry.\nPlease try again.", Toast.LENGTH_SHORT).show();
                    return;
                }

                /**
                 * Push output to user
                 */
                TextView tvResult = (TextView) findViewById(R.id.tvResult);
                tvResult.setText(buildOutputFrom(bill));
            }
        });


    }

    private void collectUserDataFor(Bill _bill) throws Exception {
        /**
         * Collect user data from all fields
         */
        _bill.setBillTotal(enteredBillTotal());
        _bill.setNumberOfPeople(enteredNumberOfPeople());
        _bill.setServiceQuality(enteredServiceQuality());
    }

    private BigDecimal enteredBillTotal() throws Exception {
        /**
         * Capture total amount on bill
         */
        EditText _etTotalBilled = (EditText) findViewById(R.id.etTotalBilled);
        return new BigDecimal(_etTotalBilled.getText().toString()).setScale(2, BigDecimal.ROUND_DOWN);
    }

    private BigDecimal enteredNumberOfPeople() throws Exception {
        /**
         * Capture number of people
         */
        EditText _etPeopleCount = (EditText) findViewById(R.id.etPeopleCount);
        return new BigDecimal(_etPeopleCount.getText().toString()).setScale(0, BigDecimal.ROUND_DOWN);
    }

    private String enteredServiceQuality() {
        /**
         * Capture service level
         */
        Spinner _spnServiceQuality = (Spinner) findViewById(R.id.spnServiceLevel);
        return _spnServiceQuality.getSelectedItem().toString();
    }

    private String buildOutputFrom(Bill _bill) {
        /**
         * Build output string
         */

        // Define format for currency
        DecimalFormat _dfCurrency = new DecimalFormat("$###,##0.00");

        // Build output that is used every time
        String _outputTipPercentLine = _bill.tipPercent().movePointRight(2) + "% tip for " + _bill.serviceQualiity().toLowerCase() + " service.";
        String _outputTipAmountLine = "Tip Amount: " + _dfCurrency.format((_bill.tipValue()));
        String _outputTotalLine = "Total (Including tip): " + _dfCurrency.format(_bill.totalWithTip());
        String _outputPerPersonLine = "Cost Per Person: " + _dfCurrency.format(_bill.totalPerPerson());

        // If necessary, build output line for remainder
        String _outputRemainderLine = "";
        if (_bill.remainder().compareTo(BigDecimal.ZERO) != 0) {
            _outputRemainderLine = "with " + _dfCurrency.format( _bill.remainder()) +  " left to be paid.";
        }

        // Return complete output string
        return  _outputTipPercentLine + "\n\n" +
                _outputTipAmountLine + "\n" +
                _outputTotalLine + "\n\n" +
                _outputPerPersonLine + "\n" +
                _outputRemainderLine;
    }
}