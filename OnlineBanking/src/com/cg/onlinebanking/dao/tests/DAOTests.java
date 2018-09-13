package com.cg.onlinebanking.dao.tests;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({ AddUserTest.class, ViewAllTxnsTest.class, GetAccountNumberTest.class, GetMiniStatementTest.class, GetUserByNameTest.class })
public class DAOTests {

}
