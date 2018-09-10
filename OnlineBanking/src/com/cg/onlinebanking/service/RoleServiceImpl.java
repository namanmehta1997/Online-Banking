package com.cg.onlinebanking.service;

import com.cg.onlinebanking.bean.RoleDTO;
import com.cg.onlinebanking.dao.IRoleDao;
import com.cg.onlinebanking.dao.RoleDaoImpl;
import com.cg.onlinebanking.exceptions.BankingException;

public class RoleServiceImpl implements IRoleService {
	private IRoleDao roleDao = new RoleDaoImpl();

	public String getRole(String username, String password)
			throws BankingException {

		String position = null;
		RoleDTO role = roleDao.getUserByName(username);
		if (role == null) {
			throw new BankingException("No Such UserName");
		} else if (!password.equals(role.getPassword())) {
			throw new BankingException("Password Mismatch");
		} else {
			position = role.getPosition();
		}

		return position;

	}

	@Override
	public String getDefaultPassword(String username,int accountId, String petName)
			throws BankingException {
		return roleDao.getDefaultPassword(username,accountId,petName);
	}
}
