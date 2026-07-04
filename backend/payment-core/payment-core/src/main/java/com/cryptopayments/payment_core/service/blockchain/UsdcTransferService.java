package com.cryptopayments.payment_core.service.blockchain;

import java.math.BigInteger;
import java.util.List;

import org.springframework.stereotype.Service;
import org.web3j.protocol.Web3j;

import com.cryptopayments.payment_core.config.UsdcProperties;

import lombok.RequiredArgsConstructor;

import java.io.IOException;

import org.web3j.abi.EventEncoder;
import org.web3j.abi.TypeReference;
import org.web3j.abi.datatypes.Address;
import org.web3j.abi.datatypes.Event;
import org.web3j.abi.datatypes.generated.Uint256;
import org.web3j.protocol.core.DefaultBlockParameter;
import org.web3j.protocol.core.methods.request.EthFilter;
import org.web3j.protocol.core.methods.response.EthLog;

import org.web3j.protocol.core.methods.response.Log;
import org.web3j.utils.Numeric;

@Service
@RequiredArgsConstructor
public class UsdcTransferService {

        private final Web3j web3j;
        private final UsdcProperties usdcProperties;

        private static final Event TRANSFER_EVENT = new Event(
                        "Transfer",
                        List.of(
                                        new TypeReference<Address>(true) {
                                        },
                                        new TypeReference<Address>(true) {
                                        },
                                        new TypeReference<Uint256>() {
                                        }));

        public List<BlockchainTransfer> getTransfers(
                        BigInteger fromBlock,
                        BigInteger toBlock) {

                try {

                        EthFilter filter = new EthFilter(
                                        DefaultBlockParameter.valueOf(fromBlock),
                                        DefaultBlockParameter.valueOf(toBlock),
                                        usdcProperties.getContract());

                        filter.addSingleTopic(
                                        EventEncoder.encode(TRANSFER_EVENT));

                        EthLog logs = web3j
                                        .ethGetLogs(filter)
                                        .send();

                        return logs.getLogs()
                                        .stream()
                                        .map(logResult -> {

                                                Log log = (Log) logResult.get();

                                                String from = "0x" + log.getTopics().get(1).substring(26);

                                                String to = "0x" + log.getTopics().get(2).substring(26);

                                                BigInteger amount = Numeric.toBigInt(log.getData());

                                                return BlockchainTransfer.builder()
                                                                .from(from)
                                                                .to(to)
                                                                .transactionHash(log.getTransactionHash())
                                                                .blockNumber(log.getBlockNumber())
                                                                .amount(amount)
                                                                .build();

                                        })
                                        .toList();

                } catch (IOException e) {

                        throw new RuntimeException(e);

                }
        }
}