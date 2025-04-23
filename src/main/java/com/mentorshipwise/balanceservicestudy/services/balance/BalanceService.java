package com.mentorshipwise.balanceservicestudy.services.balance;

import com.mentorshipwise.balanceservicestudy.dtos.request.balance.BalanceRequest;
import com.mentorshipwise.balanceservicestudy.exceptions.balance.BalanceExceptions;
import com.mentorshipwise.balanceservicestudy.exceptions.user.UserExceptions;
import com.mentorshipwise.balanceservicestudy.models.Balance;
import com.mentorshipwise.balanceservicestudy.models.Beneficiary;
import com.mentorshipwise.balanceservicestudy.models.Transfer;
import com.mentorshipwise.balanceservicestudy.repository.BalanceRepository;
import com.mentorshipwise.balanceservicestudy.repository.BeneficiaryRepository;
import com.mentorshipwise.balanceservicestudy.repository.TransferRepository;
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
    private final BeneficiaryRepository beneficiaryRepository;
    private final TransferRepository transferRepository;

    public Balance getBalanceDetailsById(String balanceId) {
        return balanceRepository.findById(balanceId)
                .orElseThrow(() -> new BalanceExceptions.BalanceNotFoundException(null));
    }

    public List<Balance> getAllUserBalances(String userId) {
        List<Balance> balances = balanceRepository.findByUserId(userId);
        if (balances.isEmpty()) {
            throw new BalanceExceptions.BalanceIsEmpty();
        }
        return balances;
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
        Balance balance = balanceRepository
                .findByIdAndUserId(req.getBalanceId(), req.getUserId())
                .orElseThrow(() -> new BalanceExceptions.BalanceNotFoundException(null));

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

    public Transfer sendFromBalanceToBalance(BalanceRequest.Send req) {
        Balance senderBalance = getBalanceOrThrow(req.getUserId(), req.getCurrency());

        Beneficiary beneficiary = beneficiaryRepository.findById(req.getBeneficiaryId())
                .orElseThrow(() -> new UserExceptions.UserNotFoundException("Beneficiary"));

        String beneficiaryId = beneficiary.getBeneficiaryId();

        Balance beneficiaryBalance = balanceRepository
                .findByCurrencyAndUserId(req.getCurrency(), beneficiaryId)
                .orElseGet(() -> {
                    Balance newBalance = Balance.builder()
                            .userId(beneficiaryId)
                            .currency(req.getCurrency())
                            .available(BigDecimal.ZERO)
                            .createdAt(Instant.now())
                            .updatedAt(Instant.now())
                            .build();
                    return balanceRepository.save(newBalance);
                });

        BigDecimal minAmount = currencyRulesService.getMinimumAmount(req.getCurrency());
        if (req.getAmount().compareTo(minAmount) < 0) {
            throw new BalanceExceptions.MinimumAmountException(req.getCurrency(), minAmount);
        }

        if (senderBalance.getAvailable().compareTo(req.getAmount()) < 0) {
            throw new BalanceExceptions.InsufficientFundsException(req.getCurrency(), req.getAmount());
        }

        senderBalance.setAvailable(senderBalance.getAvailable().subtract(req.getAmount()));
        senderBalance.setUpdatedAt(Instant.now());

        beneficiaryBalance.setAvailable(beneficiaryBalance.getAvailable().add(req.getAmount()));
        beneficiaryBalance.setUpdatedAt(Instant.now());

        balanceRepository.save(senderBalance);
        balanceRepository.save(beneficiaryBalance);

        Transfer transfer = Transfer.builder()
                .transferId(req.getTransferId())
                .senderUserId(req.getUserId())
                .senderBalanceId(senderBalance.getId())
                .beneficiaryId(beneficiaryId)
                .beneficiaryBalanceId(beneficiaryBalance.getId())
                .currency(req.getCurrency())
                .amount(req.getAmount())
                .description(req.getDescription())
                .createdAt(Instant.now())
                .build();

        return transferRepository.save(transfer);
    }

    public void deleteBalanceById(String balanceId) {
        Balance balance = balanceRepository.findById(balanceId)
                .orElseThrow(() -> new BalanceExceptions.BalanceNotFoundException(null));

        if (balance.getAvailable().compareTo(BigDecimal.ZERO) > 0) {
            throw new BalanceExceptions.BalanceNotEmptyException();
        }

        balanceRepository.deleteById(balanceId);
    }

    public List<Transfer> getTransferHistory(String userId) {
        return transferRepository.findAllBySenderUserIdOrBeneficiaryId(userId, userId);
    }
}
