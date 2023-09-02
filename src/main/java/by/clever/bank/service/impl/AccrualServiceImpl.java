package by.clever.bank.service.impl;

import by.clever.bank.service.AccrualService;
import by.clever.bank.service.constant.ConfigurationParam;
import by.clever.bank.service.exception.ServiceException;
import by.clever.bank.transactionmanager.AccountTransactionManager;
import by.clever.bank.transactionmanager.TransactionManagerFactory;
import by.clever.bank.transactionmanager.exception.TransactionManagerException;
import org.yaml.snakeyaml.Yaml;

import java.io.InputStream;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Map;

/**
 * class for specified daemon treads in service
 */
public class AccrualServiceImpl implements AccrualService {

    private int monthValue = LocalDateTime.now().getMonthValue();
    private final static AccountTransactionManager transactionManager = TransactionManagerFactory.getInstance().getAccountTransactionManager();
    private volatile boolean isChargeAccrualAvailable = false;

    @Override
    public void checkNecessity() {
        int currentMonthValue = LocalDateTime.now().getMonthValue();

        if (currentMonthValue != monthValue) {
            monthValue = currentMonthValue;
            isChargeAccrualAvailable = true;
        }
    }

    /**
     *
     * @throws ServiceException
     */
    @Override
    public void chargeAccrual() throws ServiceException {
        try {
            if (isChargeAccrualAvailable){
                BigDecimal percentage = readPercentage();
                transactionManager.chargeAccrual(percentage);
                isChargeAccrualAvailable = false;
            }
        } catch (TransactionManagerException e) {
            throw new ServiceException(e);
        }
    }

    private BigDecimal readPercentage() {

        Yaml yaml = new Yaml();
        InputStream inputStream = this.getClass()
                .getClassLoader()
                .getResourceAsStream("percentage.yml");
        Map<String, Integer> mapYML = yaml.load(inputStream);

        return new BigDecimal(mapYML.get(ConfigurationParam.PERCENTAGES_VALUE));
    }
}
