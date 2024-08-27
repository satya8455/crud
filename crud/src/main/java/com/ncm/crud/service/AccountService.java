package com.ncm.crud.service;

import com.ncm.crud.dto.UserVo;
import com.ncm.crud.entity.Account;

public interface AccountService {
	Account save1(UserVo user);
	boolean authenticate(Account user);
}
