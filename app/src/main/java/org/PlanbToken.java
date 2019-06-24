package org;

import org.web3j.abi.EventEncoder;
import org.web3j.abi.FunctionEncoder;
import org.web3j.abi.TypeReference;
import org.web3j.abi.datatypes.Address;
import org.web3j.abi.datatypes.Bool;
import org.web3j.abi.datatypes.Event;
import org.web3j.abi.datatypes.Function;
import org.web3j.abi.datatypes.Type;
import org.web3j.abi.datatypes.Utf8String;
import org.web3j.abi.datatypes.generated.Uint256;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.DefaultBlockParameter;
import org.web3j.protocol.core.RemoteCall;
import org.web3j.protocol.core.methods.request.EthFilter;
import org.web3j.protocol.core.methods.response.Log;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.tx.Contract;
import org.web3j.tx.TransactionManager;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import rx.Observable;
import rx.functions.Func1;

public class PlanbToken extends Contract {
    private static final String BINARY = "60806040526004361061004c576000357c0100000000000000000000000000000000000000000000000000000000900463ffffffff16806370a0823114610051578063a9059cbb146100a8575b600080fd5b34801561005d57600080fd5b50610092600480360381019080803573ffffffffffffffffffffffffffffffffffffffff16906020019092919050505061010d565b6040518082815260200191505060405180910390f35b3480156100b457600080fd5b506100f3600480360381019080803573ffffffffffffffffffffffffffffffffffffffff16906020019092919080359060200190929190505050610125565b604051808215151515815260200191505060405180910390f35b60006020528060005260406000206000915090505481565b6000816000803373ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff168152602001908152602001600020541015151561017457600080fd5b6000808473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16815260200190815260200160002054826000808673ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16815260200190815260200160002054011015151561020157600080fd5b816000803373ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16815260200190815260200160002060008282540392505081905550816000808573ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1681526020019081526020016000206000828254019250508190555060019050929150505600a165627a7a7230582072bfeb015e367f8017b6b07a84edbc7e8741a803de4bbdef4b1abb305ec219330029\n";

    public static final String FUNC_NAME = "name";

    public static final String FUNC_APPROVE = "approve";

    public static final String FUNC_ALLOCATETOKEN = "allocateToken";

    public static final String FUNC_ISFUNDING = "isFunding";

    public static final String FUNC_TOTALSUPPLY = "totalSupply";

    public static final String FUNC_TRANSFERFROM = "transferFrom";

    public static final String FUNC_TOKENRAISED = "tokenRaised";

    public static final String FUNC_DECIMALS = "decimals";

    public static final String FUNC_NEWCONTRACTADDR = "newContractAddr";

    public static final String FUNC_TOKENEXCHANGERATE = "tokenExchangeRate";

    public static final String FUNC_STOPFUNDING = "stopFunding";

    public static final String FUNC_SETMIGRATECONTRACT = "setMigrateContract";

    public static final String FUNC_VERSION = "version";

    public static final String FUNC_TOKENMIGRATED = "tokenMigrated";

    public static final String FUNC_BALANCEOF = "balanceOf";

    public static final String FUNC_CURRENTSUPPLY = "currentSupply";

    public static final String FUNC_STARTFUNDING = "startFunding";

    public static final String FUNC_MIGRATE = "migrate";

    public static final String FUNC_SYMBOL = "symbol";

    public static final String FUNC_DECREASESUPPLY = "decreaseSupply";

    public static final String FUNC_CHANGEOWNER = "changeOwner";

    public static final String FUNC_ETHFUNDDEPOSIT = "ethFundDeposit";

    public static final String FUNC_TRANSFER = "transfer";

    public static final String FUNC_INCREASESUPPLY = "increaseSupply";

    public static final String FUNC_SETTOKENEXCHANGERATE = "setTokenExchangeRate";

    public static final String FUNC_FUNDINGSTARTBLOCK = "fundingStartBlock";

    public static final String FUNC_ALLOWANCE = "allowance";

    public static final String FUNC_TRANSFERETH = "transferETH";

    public static final String FUNC_FUNDINGSTOPBLOCK = "fundingStopBlock";

    public static final Event ALLOCATETOKEN_EVENT = new Event("AllocateToken",
            Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {
            }, new TypeReference<Uint256>() {
            }), Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));


    public static final Event ISSUETOKEN_EVENT = new Event("IssueToken",
            Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {
            }, new TypeReference<Uint256>() {
            }), Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {
    }, new TypeReference<Uint256>() {
    }));


    public static final Event INCREASESUPPLY_EVENT = new Event("IncreaseSupply",
            Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {
            }), Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {
    }, new TypeReference<Uint256>() {
    }));
    ;

    public static final Event DECREASESUPPLY_EVENT = new Event("DecreaseSupply",
            Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {
            }), Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {
    }, new TypeReference<Uint256>() {
    }));
    ;

    public static final Event MIGRATE_EVENT = new Event("Migrate",
            Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {
            }, new TypeReference<Uint256>() {
            }), Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {
    }, new TypeReference<Uint256>() {
    }));
    ;

    public static final Event TRANSFER_EVENT = new Event("Transfer",
            Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {
            }, new TypeReference<Address>() {
            }, new TypeReference<Uint256>() {
            }), Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {
    }, new TypeReference<Uint256>() {
    }));
    ;

    public static final Event APPROVAL_EVENT = new Event("Approval",
            Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {
            }, new TypeReference<Address>() {
            }, new TypeReference<Uint256>() {
            }), Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {
    }, new TypeReference<Uint256>() {
    }));
    ;

    protected PlanbToken(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    protected PlanbToken(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    public RemoteCall<String> name() {
        final Function function = new Function(FUNC_NAME,
                Arrays.<Type>asList(),
                Arrays.<TypeReference<?>>asList(new TypeReference<Utf8String>() {
                }));
        return executeRemoteCallSingleValueReturn(function, String.class);
    }

    public RemoteCall<TransactionReceipt> approve(String _spender, BigInteger _value) {
        final Function function = new Function(
                FUNC_APPROVE,
                Arrays.<Type>asList(new Address(_spender),
                        new Uint256(_value)),
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteCall<TransactionReceipt> allocateToken(String _addr, BigInteger _eth) {
        final Function function = new Function(
                FUNC_ALLOCATETOKEN,
                Arrays.<Type>asList(new Address(_addr),
                        new Uint256(_eth)),
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteCall<Boolean> isFunding() {
        final Function function = new Function(FUNC_ISFUNDING,
                Arrays.<Type>asList(),
                Arrays.<TypeReference<?>>asList(new TypeReference<Bool>() {
                }));
        return executeRemoteCallSingleValueReturn(function, Boolean.class);
    }

    public RemoteCall<BigInteger> totalSupply() {
        final Function function = new Function(FUNC_TOTALSUPPLY,
                Arrays.<Type>asList(),
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {
                }));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteCall<TransactionReceipt> transferFrom(String _from, String _to, BigInteger _value) {
        final Function function = new Function(
                FUNC_TRANSFERFROM,
                Arrays.<Type>asList(new Address(_from),
                        new Address(_to),
                        new Uint256(_value)),
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteCall<BigInteger> tokenRaised() {
        final Function function = new Function(FUNC_TOKENRAISED,
                Arrays.<Type>asList(),
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {
                }));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteCall<BigInteger> decimals() {
        final Function function = new Function(FUNC_DECIMALS,
                Arrays.<Type>asList(),
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {
                }));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteCall<String> newContractAddr() {
        final Function function = new Function(FUNC_NEWCONTRACTADDR,
                Arrays.<Type>asList(),
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {
                }));
        return executeRemoteCallSingleValueReturn(function, String.class);
    }

    public RemoteCall<BigInteger> tokenExchangeRate() {
        final Function function = new Function(FUNC_TOKENEXCHANGERATE,
                Arrays.<Type>asList(),
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {
                }));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteCall<TransactionReceipt> stopFunding() {
        final Function function = new Function(
                FUNC_STOPFUNDING,
                Arrays.<Type>asList(),
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteCall<TransactionReceipt> setMigrateContract(String _newContractAddr) {
        final Function function = new Function(
                FUNC_SETMIGRATECONTRACT,
                Arrays.<Type>asList(new Address(_newContractAddr)),
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteCall<String> version() {
        final Function function = new Function(FUNC_VERSION,
                Arrays.<Type>asList(),
                Arrays.<TypeReference<?>>asList(new TypeReference<Utf8String>() {
                }));
        return executeRemoteCallSingleValueReturn(function, String.class);
    }

    public RemoteCall<BigInteger> tokenMigrated() {
        final Function function = new Function(FUNC_TOKENMIGRATED,
                Arrays.<Type>asList(),
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {
                }));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteCall<BigInteger> balanceOf(String _owner) {
        final Function function = new Function(FUNC_BALANCEOF,
                Arrays.<Type>asList(new Address(_owner)),
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {
                }));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteCall<BigInteger> currentSupply() {
        final Function function = new Function(FUNC_CURRENTSUPPLY,
                Arrays.<Type>asList(),
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {
                }));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteCall<TransactionReceipt> startFunding(BigInteger _fundingStartBlock, BigInteger _fundingStopBlock) {
        final Function function = new Function(
                FUNC_STARTFUNDING,
                Arrays.<Type>asList(new Uint256(_fundingStartBlock),
                        new Uint256(_fundingStopBlock)),
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteCall<TransactionReceipt> migrate() {
        final Function function = new Function(
                FUNC_MIGRATE,
                Arrays.<Type>asList(),
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteCall<String> symbol() {
        final Function function = new Function(FUNC_SYMBOL,
                Arrays.<Type>asList(),
                Arrays.<TypeReference<?>>asList(new TypeReference<Utf8String>() {
                }));
        return executeRemoteCallSingleValueReturn(function, String.class);
    }

    public RemoteCall<TransactionReceipt> decreaseSupply(BigInteger _value) {
        final Function function = new Function(
                FUNC_DECREASESUPPLY,
                Arrays.<Type>asList(new Uint256(_value)),
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteCall<TransactionReceipt> changeOwner(String _newFundDeposit) {
        final Function function = new Function(
                FUNC_CHANGEOWNER,
                Arrays.<Type>asList(new Address(_newFundDeposit)),
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteCall<String> ethFundDeposit() {
        final Function function = new Function(FUNC_ETHFUNDDEPOSIT,
                Arrays.<Type>asList(),
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {
                }));
        return executeRemoteCallSingleValueReturn(function, String.class);
    }

    public RemoteCall<TransactionReceipt> transfer(String _to, BigInteger _value) {
        final Function function = new Function(
                FUNC_TRANSFER,
                Arrays.<Type>asList(new Address(_to),
                        new Uint256(_value)),
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteCall<TransactionReceipt> increaseSupply(BigInteger _value) {
        final Function function = new Function(
                FUNC_INCREASESUPPLY,
                Arrays.<Type>asList(new Uint256(_value)),
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteCall<TransactionReceipt> setTokenExchangeRate(BigInteger _tokenExchangeRate) {
        final Function function = new Function(
                FUNC_SETTOKENEXCHANGERATE,
                Arrays.<Type>asList(new Uint256(_tokenExchangeRate)),
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteCall<BigInteger> fundingStartBlock() {
        final Function function = new Function(FUNC_FUNDINGSTARTBLOCK,
                Arrays.<Type>asList(),
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {
                }));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteCall<BigInteger> allowance(String _owner, String _spender) {
        final Function function = new Function(FUNC_ALLOWANCE,
                Arrays.<Type>asList(new Address(_owner),
                        new Address(_spender)),
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {
                }));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteCall<TransactionReceipt> transferETH() {
        final Function function = new Function(
                FUNC_TRANSFERETH,
                Arrays.<Type>asList(),
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteCall<BigInteger> fundingStopBlock() {
        final Function function = new Function(FUNC_FUNDINGSTOPBLOCK,
                Arrays.<Type>asList(),
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {
                }));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public static RemoteCall<PlanbToken> deploy(Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit, String _ethFundDeposit, BigInteger _currentSupply) {
        String encodedConstructor = FunctionEncoder.encodeConstructor(Arrays.<Type>asList(new Address(_ethFundDeposit),
                new Uint256(_currentSupply)));
        return deployRemoteCall(PlanbToken.class, web3j, credentials, gasPrice, gasLimit, BINARY, encodedConstructor);
    }

    public static RemoteCall<PlanbToken> deploy(Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit, String _ethFundDeposit, BigInteger _currentSupply) {
        String encodedConstructor = FunctionEncoder.encodeConstructor(Arrays.<Type>asList(new Address(_ethFundDeposit),
                new Uint256(_currentSupply)));
        return deployRemoteCall(PlanbToken.class, web3j, transactionManager, gasPrice, gasLimit, BINARY, encodedConstructor);
    }

    public List<AllocateTokenEventResponse> getAllocateTokenEvents(TransactionReceipt transactionReceipt) {
        List<EventValuesWithLog> valueList = extractEventParametersWithLog(ALLOCATETOKEN_EVENT, transactionReceipt);
        ArrayList<AllocateTokenEventResponse> responses = new ArrayList<AllocateTokenEventResponse>(valueList.size());
        for (EventValuesWithLog eventValues : valueList) {
            AllocateTokenEventResponse typedResponse = new AllocateTokenEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse._to = (String) eventValues.getIndexedValues().get(0).getValue();
            typedResponse._value = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public Observable<AllocateTokenEventResponse> allocateTokenEventObservable(EthFilter filter) {
        return web3j.ethLogObservable(filter).map(new Func1<Log, AllocateTokenEventResponse>() {
            @Override
            public AllocateTokenEventResponse call(Log log) {
                EventValuesWithLog eventValues = extractEventParametersWithLog(ALLOCATETOKEN_EVENT, log);
                AllocateTokenEventResponse typedResponse = new AllocateTokenEventResponse();
                typedResponse.log = log;
                typedResponse._to = (String) eventValues.getIndexedValues().get(0).getValue();
                typedResponse._value = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
                return typedResponse;
            }
        });
    }

    public Observable<AllocateTokenEventResponse> allocateTokenEventObservable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(ALLOCATETOKEN_EVENT));
        return allocateTokenEventObservable(filter);
    }

    public List<IssueTokenEventResponse> getIssueTokenEvents(TransactionReceipt transactionReceipt) {
        List<EventValuesWithLog> valueList = extractEventParametersWithLog(ISSUETOKEN_EVENT, transactionReceipt);
        ArrayList<IssueTokenEventResponse> responses = new ArrayList<IssueTokenEventResponse>(valueList.size());
        for (EventValuesWithLog eventValues : valueList) {
            IssueTokenEventResponse typedResponse = new IssueTokenEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse._to = (String) eventValues.getIndexedValues().get(0).getValue();
            typedResponse._value = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public Observable<IssueTokenEventResponse> issueTokenEventObservable(EthFilter filter) {
        return web3j.ethLogObservable(filter).map(new Func1<Log, IssueTokenEventResponse>() {
            @Override
            public IssueTokenEventResponse call(Log log) {
                EventValuesWithLog eventValues = extractEventParametersWithLog(ISSUETOKEN_EVENT, log);
                IssueTokenEventResponse typedResponse = new IssueTokenEventResponse();
                typedResponse.log = log;
                typedResponse._to = (String) eventValues.getIndexedValues().get(0).getValue();
                typedResponse._value = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
                return typedResponse;
            }
        });
    }

    public Observable<IssueTokenEventResponse> issueTokenEventObservable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(ISSUETOKEN_EVENT));
        return issueTokenEventObservable(filter);
    }

    public List<IncreaseSupplyEventResponse> getIncreaseSupplyEvents(TransactionReceipt transactionReceipt) {
        List<EventValuesWithLog> valueList = extractEventParametersWithLog(INCREASESUPPLY_EVENT, transactionReceipt);
        ArrayList<IncreaseSupplyEventResponse> responses = new ArrayList<IncreaseSupplyEventResponse>(valueList.size());
        for (EventValuesWithLog eventValues : valueList) {
            IncreaseSupplyEventResponse typedResponse = new IncreaseSupplyEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse._value = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public Observable<IncreaseSupplyEventResponse> increaseSupplyEventObservable(EthFilter filter) {
        return web3j.ethLogObservable(filter).map(new Func1<Log, IncreaseSupplyEventResponse>() {
            @Override
            public IncreaseSupplyEventResponse call(Log log) {
                EventValuesWithLog eventValues = extractEventParametersWithLog(INCREASESUPPLY_EVENT, log);
                IncreaseSupplyEventResponse typedResponse = new IncreaseSupplyEventResponse();
                typedResponse.log = log;
                typedResponse._value = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
                return typedResponse;
            }
        });
    }

    public Observable<IncreaseSupplyEventResponse> increaseSupplyEventObservable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(INCREASESUPPLY_EVENT));
        return increaseSupplyEventObservable(filter);
    }

    public List<DecreaseSupplyEventResponse> getDecreaseSupplyEvents(TransactionReceipt transactionReceipt) {
        List<EventValuesWithLog> valueList = extractEventParametersWithLog(DECREASESUPPLY_EVENT, transactionReceipt);
        ArrayList<DecreaseSupplyEventResponse> responses = new ArrayList<DecreaseSupplyEventResponse>(valueList.size());
        for (EventValuesWithLog eventValues : valueList) {
            DecreaseSupplyEventResponse typedResponse = new DecreaseSupplyEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse._value = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public Observable<DecreaseSupplyEventResponse> decreaseSupplyEventObservable(EthFilter filter) {
        return web3j.ethLogObservable(filter).map(new Func1<Log, DecreaseSupplyEventResponse>() {
            @Override
            public DecreaseSupplyEventResponse call(Log log) {
                EventValuesWithLog eventValues = extractEventParametersWithLog(DECREASESUPPLY_EVENT, log);
                DecreaseSupplyEventResponse typedResponse = new DecreaseSupplyEventResponse();
                typedResponse.log = log;
                typedResponse._value = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
                return typedResponse;
            }
        });
    }

    public Observable<DecreaseSupplyEventResponse> decreaseSupplyEventObservable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(DECREASESUPPLY_EVENT));
        return decreaseSupplyEventObservable(filter);
    }

    public List<MigrateEventResponse> getMigrateEvents(TransactionReceipt transactionReceipt) {
        List<EventValuesWithLog> valueList = extractEventParametersWithLog(MIGRATE_EVENT, transactionReceipt);
        ArrayList<MigrateEventResponse> responses = new ArrayList<MigrateEventResponse>(valueList.size());
        for (EventValuesWithLog eventValues : valueList) {
            MigrateEventResponse typedResponse = new MigrateEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse._to = (String) eventValues.getIndexedValues().get(0).getValue();
            typedResponse._value = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public Observable<MigrateEventResponse> migrateEventObservable(EthFilter filter) {
        return web3j.ethLogObservable(filter).map(new Func1<Log, MigrateEventResponse>() {
            @Override
            public MigrateEventResponse call(Log log) {
                EventValuesWithLog eventValues = extractEventParametersWithLog(MIGRATE_EVENT, log);
                MigrateEventResponse typedResponse = new MigrateEventResponse();
                typedResponse.log = log;
                typedResponse._to = (String) eventValues.getIndexedValues().get(0).getValue();
                typedResponse._value = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
                return typedResponse;
            }
        });
    }

    public Observable<MigrateEventResponse> migrateEventObservable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(MIGRATE_EVENT));
        return migrateEventObservable(filter);
    }

    public List<TransferEventResponse> getTransferEvents(TransactionReceipt transactionReceipt) {
        List<EventValuesWithLog> valueList = extractEventParametersWithLog(TRANSFER_EVENT, transactionReceipt);
        ArrayList<TransferEventResponse> responses = new ArrayList<TransferEventResponse>(valueList.size());
        for (EventValuesWithLog eventValues : valueList) {
            TransferEventResponse typedResponse = new TransferEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse._from = (String) eventValues.getIndexedValues().get(0).getValue();
            typedResponse._to = (String) eventValues.getIndexedValues().get(1).getValue();
            typedResponse._value = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public Observable<TransferEventResponse> transferEventObservable(EthFilter filter) {
        return web3j.ethLogObservable(filter).map(new Func1<Log, TransferEventResponse>() {
            @Override
            public TransferEventResponse call(Log log) {
                EventValuesWithLog eventValues = extractEventParametersWithLog(TRANSFER_EVENT, log);
                TransferEventResponse typedResponse = new TransferEventResponse();
                typedResponse.log = log;
                typedResponse._from = (String) eventValues.getIndexedValues().get(0).getValue();
                typedResponse._to = (String) eventValues.getIndexedValues().get(1).getValue();
                typedResponse._value = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
                return typedResponse;
            }
        });
    }

    public Observable<TransferEventResponse> transferEventObservable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(TRANSFER_EVENT));
        return transferEventObservable(filter);
    }

    public List<ApprovalEventResponse> getApprovalEvents(TransactionReceipt transactionReceipt) {
        List<EventValuesWithLog> valueList = extractEventParametersWithLog(APPROVAL_EVENT, transactionReceipt);
        ArrayList<ApprovalEventResponse> responses = new ArrayList<ApprovalEventResponse>(valueList.size());
        for (EventValuesWithLog eventValues : valueList) {
            ApprovalEventResponse typedResponse = new ApprovalEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse._owner = (String) eventValues.getIndexedValues().get(0).getValue();
            typedResponse._spender = (String) eventValues.getIndexedValues().get(1).getValue();
            typedResponse._value = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public Observable<ApprovalEventResponse> approvalEventObservable(EthFilter filter) {
        return web3j.ethLogObservable(filter).map(new Func1<Log, ApprovalEventResponse>() {
            @Override
            public ApprovalEventResponse call(Log log) {
                EventValuesWithLog eventValues = extractEventParametersWithLog(APPROVAL_EVENT, log);
                ApprovalEventResponse typedResponse = new ApprovalEventResponse();
                typedResponse.log = log;
                typedResponse._owner = (String) eventValues.getIndexedValues().get(0).getValue();
                typedResponse._spender = (String) eventValues.getIndexedValues().get(1).getValue();
                typedResponse._value = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
                return typedResponse;
            }
        });
    }

    public Observable<ApprovalEventResponse> approvalEventObservable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(APPROVAL_EVENT));
        return approvalEventObservable(filter);
    }

    public static PlanbToken load(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return new PlanbToken(contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    public static PlanbToken load(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return new PlanbToken(contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    public static class AllocateTokenEventResponse {
        public Log log;

        public String _to;

        public BigInteger _value;
    }

    public static class IssueTokenEventResponse {
        public Log log;

        public String _to;

        public BigInteger _value;
    }

    public static class IncreaseSupplyEventResponse {
        public Log log;

        public BigInteger _value;
    }

    public static class DecreaseSupplyEventResponse {
        public Log log;

        public BigInteger _value;
    }

    public static class MigrateEventResponse {
        public Log log;

        public String _to;

        public BigInteger _value;
    }

    public static class TransferEventResponse {
        public Log log;

        public String _from;

        public String _to;

        public BigInteger _value;
    }

    public static class ApprovalEventResponse {
        public Log log;

        public String _owner;

        public String _spender;

        public BigInteger _value;
    }
}
