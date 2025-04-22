package com.mentorshipwise.balanceservicestudy.services.balance;

import com.mentorshipwise.balanceservicestudy.dtos.request.balance.BalanceRequest;
import com.mentorshipwise.balanceservicestudy.exceptions.balance.BalanceExceptions;
import com.mentorshipwise.balanceservicestudy.models.Balance;
import com.mentorshipwise.balanceservicestudy.repository.BalanceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BalanceService {

    private final BalanceRepository balanceRepository;
    private final CurrencyRulesService currencyRulesService;

    public List<Balance> listAllBalances() {
        return balanceRepository.findAll();
    }


    public Balance createBalance(BalanceRequest.Create req) {
        balanceRepository.findByCurrencyAndUserId(req.getCurrency(), req.getUserId()).ifPresent(balance -> {
            throw new BalanceExceptions.BalanceAlreadyExistsException(req.getCurrency());
        });
        Balance newBalance = Balance.builder()
                .userId(req.getUserId())
                .currency(req.getCurrency())
                .available(BigDecimal.ZERO)
                .createdAt(Instant.now())
                .updatedAt(Instant.now())
                .build();

        return balanceRepository.save(newBalance);
    }

    private Balance getBalanceOrThrow(String userId, String currency) {
        return balanceRepository.findByCurrencyAndUserId(currency, userId)
                .orElseThrow(() -> new BalanceExceptions.BalanceNotFoundException(currency));
    }

    public Balance addToBalance(BalanceRequest.Add req) {
        Balance balance = getBalanceOrThrow(req.getUserId(), req.getCurrency());

        BigDecimal updatedAmount = balance.getAvailable().add(req.getAmount());

        balance.setAvailable(updatedAmount);
        balance.setUpdatedAt(Instant.now());

        return balanceRepository.save(balance);
    }

    public Balance spendFromBalance(BalanceRequest.Spend req) {
        Balance balance = getBalanceOrThrow(req.getUserId(), req.getCurrency());

        BigDecimal minAmount = currencyRulesService.getMinimumAmount(req.getCurrency());

        if (balance.getAvailable().compareTo(req.getAmount()) < 0) {
            throw new BalanceExceptions.InsufficientFundsException(req.getCurrency(), req.getAmount());
        }

        if (req.getAmount().compareTo(minAmount) < 0) {
            throw new BalanceExceptions.MinimumAmountException(req.getCurrency(), minAmount);
        }

        balance.setAvailable(balance.getAvailable().subtract(req.getAmount()));
        balance.setUpdatedAt(Instant.now());

        return balanceRepository.save(balance);
    }

    public Balance sendFromBalanceToBalance(BalanceRequest.Send req) {
        Balance balance = getBalanceOrThrow(req.getUserId(), req.getCurrency());


    }

}
