package com.mentorshipwise.balanceservicestudy.controllers;

import com.mentorshipwise.balanceservicestudy.dtos.request.balance.BalanceRequest;
import com.mentorshipwise.balanceservicestudy.dtos.response.ApiResponse;
import com.mentorshipwise.balanceservicestudy.models.Balance;
import com.mentorshipwise.balanceservicestudy.models.Transfer;
import com.mentorshipwise.balanceservicestudy.services.balance.BalanceService;
import com.mentorshipwise.balanceservicestudy.utils.ResponseUtil;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/users/{userId}/balances")
@RequiredArgsConstructor
public class BalanceController {

    private final BalanceService balanceService;

    //TODO Create endpoints to create a balance, top-up a balance and spend from the balance
    @GetMapping
    public ResponseEntity<ApiResponse<List<Balance>>> listAllBalancesByUserId(
            @PathVariable String userId) {
        List<Balance> balances = balanceService.getAllUserBalances(userId);
        return ResponseEntity.ok(ResponseUtil.success("Balances retrieved successfully", balances));
    }

    @PostMapping
    public ResponseEntity<ApiResponse<Balance>> createBalance(
            @PathVariable String userId,
            @Valid @RequestBody BalanceRequest.Create balance) {
        balance.setUserId(userId);
        Balance createdBalance = balanceService.createBalance(balance);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ResponseUtil.success("Balance created successfully", createdBalance));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<Optional<Balance>>> getBalanceDetails(
            @PathVariable String id) {
        Optional<Balance> balance = Optional.ofNullable(balanceService.getBalanceDetailsById(id));
        return ResponseEntity.ok(ResponseUtil.success("Balance:", balance));
    }

    @PostMapping("/{balanceId}/top-up")
    public ResponseEntity<ApiResponse<Balance>> topUpBalance(
            @PathVariable String userId,
            @PathVariable String balanceId,
            @RequestBody @Valid BalanceRequest.Add balance) {
        balance.setUserId(userId);
        balance.setBalanceId(balanceId);

        Balance topUpBalance = balanceService.addToBalance(balance);
        return ResponseEntity.ok(ResponseUtil.success("Balance topped up!", topUpBalance));
    }

    @PostMapping("/spend")
    public ResponseEntity<ApiResponse<Balance>> spendFromBalance(
            @PathVariable String userId,
            @RequestBody @Valid BalanceRequest.Spend balance) {
        balance.setUserId(userId);
        Balance spentBalance = balanceService.spendFromBalance(balance);
        return ResponseEntity.ok(ResponseUtil.success("Balance spent successfully", spentBalance));
    }

    @PostMapping("/send-to-balance")
    public ResponseEntity<ApiResponse<Transfer>> sendToBeneficiaryBalance(
            @PathVariable String userId,
            @RequestBody @Valid BalanceRequest.Send req) {
        req.setUserId(userId);
        Transfer transfer = balanceService.sendFromBalanceToBalance(req);
        return ResponseEntity.ok(ResponseUtil.success("Transfer successful", transfer));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Balance>> closeBalance(@PathVariable String id) {
        balanceService.deleteBalanceById(id);
        return ResponseEntity.ok(ResponseUtil.success("Balance closed", null));
    }

    @GetMapping("/transfers")
    public ResponseEntity<ApiResponse<List<Transfer>>> getTransferHistory(
            @PathVariable String userId) {
        List<Transfer> transfers = balanceService.getTransferHistory(userId);
        return ResponseEntity.ok(ResponseUtil.success("Transfer history fetched successfully", transfers));
    }

}
