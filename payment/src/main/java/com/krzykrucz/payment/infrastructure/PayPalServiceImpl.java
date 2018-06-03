package com.krzykrucz.payment.infrastructure;

import com.krzykrucz.payment.domain.payment.PayerId;
import com.krzykrucz.payment.domain.payment.PaymentId;
import com.krzykrucz.payment.domain.payment.paypal.PayPalService;
import com.paypal.api.payments.*;
import com.paypal.base.rest.APIContext;
import com.paypal.base.rest.PayPalRESTException;
import org.joda.money.Money;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

@Service
class PayPalServiceImpl implements PayPalService {

    private static final String PAYMENT_METHOD = "paypal";
    private static final String PAYMENT_INTENT = "sale";

    private final APIContext apiContext;

    @Autowired
    public PayPalServiceImpl(APIContext apiContext) {
        this.apiContext = apiContext;
    }

    @Override
    public Payment createPayment(
            Money paymentValue,
            String description,
            String cancelUrl,
            String successUrl) throws PayPalRESTException {
        final Amount amount = new Amount();
        final String currency3LetterCode = paymentValue.getCurrencyUnit().getCurrencyCode();
        final double roundedMoneyValue = paymentValue.rounded(2, RoundingMode.HALF_UP)
                .getAmount()
                .doubleValue();
        amount.setCurrency(currency3LetterCode);
        amount.setTotal(String.format("%.2f", roundedMoneyValue));

        final Transaction transaction = new Transaction();
        transaction.setDescription(description);
        transaction.setAmount(amount);

        final List<Transaction> transactions = new ArrayList<>();
        transactions.add(transaction);

        final Payer payer = new Payer();
        payer.setPaymentMethod(PAYMENT_METHOD);

        final Payment payment = new Payment();
        payment.setIntent(PAYMENT_INTENT);
        payment.setPayer(payer);
        payment.setTransactions(transactions);
        final RedirectUrls redirectUrls = new RedirectUrls();
        redirectUrls.setCancelUrl(cancelUrl);
        redirectUrls.setReturnUrl(successUrl);
        payment.setRedirectUrls(redirectUrls);

        return payment.create(apiContext);
    }

    @Override
    public Payment executePayment(PaymentId paymentId, PayerId payerId) throws PayPalRESTException {
        Payment payment = new Payment();
        payment.setId(paymentId.getId());
        PaymentExecution paymentExecute = new PaymentExecution();
        paymentExecute.setPayerId(payerId.getId());
        return payment.execute(apiContext, paymentExecute);
    }

}
